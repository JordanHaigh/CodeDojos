import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Program
{
    private static List<String[]> codeSections = new ArrayList<>();
    private static Stack<ArrayPositionAndBracketType> stack = new Stack<>();

    public static void main(String[]args)
    {
        if(args.length != 1)
        {
            System.out.println("Missing filepath argument");
            return;
        }


        String fileToString;
        try
        {
            fileToString = readFile(args[0], StandardCharsets.UTF_8);

            String[] lineSplit = fileToString.split("\r\n");

            for(int i = 0; i < lineSplit.length;i++)
            {
                if(lineSplit[i].contains("{"))
                {
                    ArrayPositionAndBracketType newCombination = new ArrayPositionAndBracketType(i, BracketTypes.OPEN);
                    stack.push(newCombination);

                }
                else if(lineSplit[i].contains("}"))
                {
                    ArrayPositionAndBracketType newCombination = new ArrayPositionAndBracketType(i,BracketTypes.CLOSE);
                    if(stack.peek().getBracketType().equals(BracketTypes.OPEN)) //If the top element of the stack has a open bracket
                       matchBracketPair(lineSplit,i);

                    else
                        stack.push(newCombination);
                }

            }

        }catch(IOException e)        {
            System.out.println(e.getMessage());
        }

    }


    static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    static void matchBracketPair(String[] lineSplit, int currentIndex)
    {
        //no need to put on stack, we can remove top element instead
        ArrayPositionAndBracketType removedCombo = stack.pop();

        //Now we have two integers with our start and end of brackets

        //check if line index we just popped contains more than the single bracket
        //eg. if(somecondition) {       or public class {
        if(lineSplit[removedCombo.getArrayPosition()].trim().length() > 1) //more than the bracket //todo update check to include possible comments
        {
            //found more than the bracket on the same line
            String[] codeSection = new String[currentIndex - removedCombo.getArrayPosition()+1]; //+1 to include closing bracket
            int writer = 0;
            int reader = removedCombo.getArrayPosition();

            while(writer < currentIndex-removedCombo.getArrayPosition()+1)
            {
                codeSection[writer] = lineSplit[reader];
                reader++;
                writer++;
            }

            codeSections.add(codeSection);
        }
        else
        {
            //bracket is on its own line. we will need to include the line above as well
            String[] codeSection = new String[currentIndex - removedCombo.getArrayPosition()+2]; //+2 to include line above and closing bracket
            int writer = 0;
            int reader = removedCombo.getArrayPosition()-1;

            while(writer < currentIndex-removedCombo.getArrayPosition()+2)
            {
                codeSection[writer] = lineSplit[reader];
                reader++;
                writer++;
            }
            codeSections.add(codeSection);
        }
    }

    static String[] cullLineSplit(String[] oldStringArray, int cullFrom, int cullTo)
    {
        String[] newArr = new String[cullTo-cullFrom];
        int index = 0;

        for(int i = 0; i < cullFrom; i++)
        {
            newArr[index] = oldStringArray[i];
            index++;
        }

        for(int i = cullTo+1; i < oldStringArray.length;i++)
        {
            newArr[index] = oldStringArray[i];
            index++;
        }

        return newArr;
    }

}
