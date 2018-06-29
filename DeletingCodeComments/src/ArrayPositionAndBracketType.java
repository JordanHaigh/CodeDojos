public class ArrayPositionAndBracketType
{
    private int arrayPosition;
    private BracketTypes BracketType;

    public int getArrayPosition() {
        return arrayPosition;
    }


    public BracketTypes getBracketType() {
        return BracketType;
    }

    public ArrayPositionAndBracketType(int arrayPosition, BracketTypes bracketType) {

        this.arrayPosition = arrayPosition;
        BracketType = bracketType;
    }
}

