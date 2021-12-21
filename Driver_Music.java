import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import java.io.*;

public class Driver_Music {
    private static String trackName;
    private static Sequencer SEQ;
    private Driver_Music(){}

    public static void playTrack(String trackName, boolean loop){
        if(SEQ == null){
            initSEQ();
        }
        if(trackName.equals(Driver_Music.trackName)){
            return;
        }
        Driver_Music.trackName = trackName;

        try {
            // Obtains the default Sequencer connected to a default device.
//            Sequencer sequencer = MidiSystem.getSequencer();

            // Opens the device, indicating that it should now acquire any
            // system resources it requires and become operational.
//            sequencer.open();

            // create a stream from a file
            File file = new File("./res/Tracks/" + trackName + ".mid");
            InputStream is = new BufferedInputStream(new FileInputStream(file));

            // Sets the current sequence on which the sequencer operates.
            // The stream must point to MIDI file data.
            SEQ.setSequence(is);

            if(loop) {
                SEQ.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            }
            else{
                SEQ.setLoopCount(0);
            }

            SEQ.stop();

            // Starts playback of the MIDI data in the currently loaded sequence.
            SEQ.start();
            System.out.println("Playing track " + trackName);
        }
        catch(FileNotFoundException e){
            //do nothing
            System.out.println("issue " + trackName);
        }
        catch(Exception e){
            System.out.println("other issue");
        }

    }

    public static void playStageTrack(String stageName){
        String stageTrackName;
        switch(stageName){
            case "Stage1":
                stageTrackName = "2_Unchanging_Rooms";
                break;
            case "Stage2":
                stageTrackName = "4_Shangri_La_For_Valhalla";
                break;
            case "Stage3":
                stageTrackName = "6_Empty_Webs";
                break;
            case "Stage4":
                stageTrackName = "8_Tangible_Flesh";
                break;
            case "Stage5":
                stageTrackName = "10_Silent_Trillion";
                break;
            case "Stage6":
                stageTrackName = "11_Cerebral_Melancholy";
                break;
            case "StageEX":
                stageTrackName = "13_Space_Aflame";
                break;
            default:
                stageTrackName = "1_Solitary_Dodecahedron";
        }
        playTrack(stageTrackName, true);
    }

    public static void playBossTrack(String stageName){
        if(stageName.equals("Stage5")){
            return;
        }
        String bossTrackName;
        switch(stageName){
            case "Stage1":
                bossTrackName = "3_The_Future_Has_Yet_To_Arrive";
                break;
            case "Stage2":
                bossTrackName = "5_Dominion_Base";
                break;
            case "Stage3":
                bossTrackName = "7_The_Girl_With_Too_Many_Eyes";
                break;
            case "Stage4":
                bossTrackName = "9_Pierce_The_Heavens";
                break;
            case "Stage6":
                bossTrackName = "12_Theme_For_Kingslayer";
                break;
            case "StageEX":
                bossTrackName = "14_The_One_Burning_Regretfully";
                break;
            default:
                bossTrackName = "1_Solitary_Dodecahedron";
        }
        playTrack(bossTrackName, true);
    }

    private static void initSEQ(){
        try{
            SEQ = MidiSystem.getSequencer();
            SEQ.open();
        }
        catch(Exception e){
            //do nothing
        }
    }
}
