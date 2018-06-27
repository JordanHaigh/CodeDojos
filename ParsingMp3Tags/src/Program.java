import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Program {

    private static List<String> genreList = new ArrayList<>();
    private static List<String> info = new ArrayList<>();

    public static void main(String[]args)
    {
        buildGenresList();
        //You can tell if an MP3 file includes ID3 tags by examining the last 128 bytes of the file.
        //If they begin with the characters TAG, you have found a ID3 tag.

        //The format of the tag is as follows:

        //Field      | TAG | song | artist | album | year | comment | genre
        //Byte Width | 3   | 30   | 30     | 30    | 4    | 30      | 1
        //    ------------------------------------------------------------------
        //  128 Bytes

        //A minor change was later made to ID3 tags to allow them to include track numbers, creating ID3v1.1.
        // In that format, if the 29th byte of a comment is null and the 30th is not,
        // the 30th byte is an integer representing the track number.


        //Get argument from user
        if(args.length != 1)
        {
            System.out.println("Missing arguments");
            return;
        }

        if(!args[0].contains(".mp3"))
        {
            System.out.println("Not an MP3 file");
            return;
        }

        System.out.println(args[0]);

        //Create file and path for argument
        File file = new File(args[0]);
        Path path = file.toPath();

        //Try catch used for exceptions possible thrown when reading bytes
        try
        {
            readFile(path);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void readFile(Path path) throws IOException
    {
        byte[] data = Files.readAllBytes(path);
        System.out.println("done");

        int length = data.length;

        int startOfMetaData = length - 128;

        addMetaDataToInfoList(data, startOfMetaData);

        for(String s:  info)
            System.out.println(s);

    }

    public static void addMetaDataToInfoList(byte[] data, int startOfMetaData)
    {
        info.add(buildString(data, startOfMetaData, startOfMetaData+3,false)); //TAG section
        startOfMetaData += 3;
        info.add(buildString(data, startOfMetaData, startOfMetaData+30,false)); //SONG section
        startOfMetaData += 30;
        info.add(buildString(data, startOfMetaData, startOfMetaData+30,false)); //ARTIST section
        startOfMetaData += 30;
        info.add(buildString(data, startOfMetaData, startOfMetaData+30,false)); //ALBUM section
        startOfMetaData += 30;
        info.add(buildString(data, startOfMetaData, startOfMetaData+4,false)); //YEAR section
        startOfMetaData += 4;
        info.add(buildString(data, startOfMetaData, startOfMetaData+30,true)); //COMMENT section
        startOfMetaData += 30;
        String foundGenre = (buildString(data, startOfMetaData, startOfMetaData+1,false)); //GENRE section

        if(!foundGenre.equals(" ")) //Empty genre slot
        {
            int genreNumber = Integer.parseInt(foundGenre);
            String genre= genreList.get(genreNumber);

            info.add(genre);
        }

    }

    public static String buildString(byte[] data, int startIndex, int endIndex, boolean checkingForTrackNum)
    {
        StringBuilder sb = new StringBuilder();

        if(checkingForTrackNum)
        {
            if(data[28] == 0 && data[29] != 0)
            {
                //found track number
                sb.append((char)data[29]).append("\n");
                endIndex = 28;
            }
        }

        for(int i =  startIndex; i < endIndex;i++){
            sb.append((char)data[i]);
        }

        //System.out.println(sb.toString());
        return sb.toString();
    }

    public static void buildGenresList()
    {
        genreList.add("Blues");
        genreList.add("ClassicRock");
        genreList.add("Country");
        genreList.add("Dance");
        genreList.add("Disco");
        genreList.add("Funk");
        genreList.add("Grunge");
        genreList.add("HipHop");
        genreList.add("Jazz");
        genreList.add("Metal");
        genreList.add("NewAge");
        genreList.add("Oldies");
        genreList.add("Other");
        genreList.add("Pop");
        genreList.add("RandB");
        genreList.add("Rap");
        genreList.add("Reggae");
        genreList.add("Rock");
        genreList.add("Techno");
        genreList.add("Industrial");
        genreList.add("Alternative");
        genreList.add("Ska");
        genreList.add("DeathMetal");
        genreList.add("Pranks");
        genreList.add("Soundtrack");
        genreList.add("EuroTechno");
        genreList.add("Ambient");
        genreList.add("TripHop");
        genreList.add("Vocal");
        genreList.add("JazzANDFunk");
        genreList.add("Fusion");
        genreList.add("Trance");
        genreList.add("Classical");
        genreList.add("Instrumental");
        genreList.add("Acid");
        genreList.add("House");
        genreList.add("Game");
        genreList.add("SoundClip");
        genreList.add("Gospel");
        genreList.add("Noise");
        genreList.add("AlternRock");
        genreList.add("Bass");
        genreList.add("Soul");
        genreList.add("Punk");
        genreList.add("Space");
        genreList.add("Meditative");
        genreList.add("InstrumentalPop");
        genreList.add("InstrumentalRock");
        genreList.add("Ethnic");
        genreList.add("Ethnic");
        genreList.add("Gothic");
        genreList.add("TechnoIndustrial");
        genreList.add("Electronic");
        genreList.add("PopFolk");
        genreList.add("Eurodance");
        genreList.add("Dream");
        genreList.add("SouthernRock");
        genreList.add("Comedy");
        genreList.add("Cult");
        genreList.add("Gangsta");
        genreList.add("Top40");
        genreList.add("ChristianRap");
        genreList.add("PopFunk");
        genreList.add("Jungle");
        genreList.add("NativeAmerican");
        genreList.add("Cabaret");
        genreList.add("NewWave");
        genreList.add("Psychadelic");
        genreList.add("Rave");
        genreList.add("Showtunes");
        genreList.add("Trailer");
        genreList.add("LoFi");
        genreList.add("Tribal");
        genreList.add("AcidPunk");
        genreList.add("AcidJazz");
        genreList.add("Polka");
        genreList.add("Retro");
        genreList.add("Musical");
        genreList.add("RockandRoll");
        genreList.add("HardRock");
        genreList.add("Folk");
        genreList.add("FolkRock");
        genreList.add("NationalFolk");
        genreList.add("Swing");
        genreList.add("FastFusion");
        genreList.add("Bebob");
        genreList.add("Latin");
        genreList.add("Revival");
        genreList.add("Celtic");
        genreList.add("Bluegrass");
        genreList.add("Avantgarde");
        genreList.add("GothicRock");
        genreList.add("ProgressiveRock");
        genreList.add("PsychedelicRock");
        genreList.add("SymphonicRock");
        genreList.add("SlowRock");
        genreList.add("BigBand");
        genreList.add("Chorus");
        genreList.add("EasyListening");
        genreList.add("Acoustic");
        genreList.add("Humour");
        genreList.add("Speech");
        genreList.add("Chanson");
        genreList.add("Opera");
        genreList.add("ChamberMusic");
        genreList.add("Sonata");
        genreList.add("Symphony");
        genreList.add("BootyBass");
        genreList.add("Primus");
        genreList.add("PornGroove");
        genreList.add("Satire");
        genreList.add("SlowJam");
        genreList.add("Club");
        genreList.add("Tango");
        genreList.add("Samba");
        genreList.add("Folklore");
        genreList.add("PowerBallad");
        genreList.add("Ballad");
        genreList.add("RhythmicSoul");
        genreList.add("Freestyle");
        genreList.add("Duet");
        genreList.add("PunkRock");
        genreList.add("DrumSolo");
        genreList.add("Acapella");
        genreList.add("EuroHouse");
        genreList.add("DanceHall");
        //why are there so many..
    }
}
