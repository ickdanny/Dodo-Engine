public class DialogueEndScreen extends DialogueScreen {

    public DialogueEndScreen(String stageName, String preOrPost, int difficulty){
        super(stageName, preOrPost, difficulty, null);
    }

    @Override
    public void update(String keyPressName){
        //z to go forwards - next()
        //x to skip entirely
        //other commands go to the Driver_Game instance
        switch(keyPressName){
            case "Z Key":
                next();
                break;
            case "X Key":
                Driver.clear(1);
                Driver_Game.getCurrentGameDriver().victoryEnd();
                //waitSignal.signalThis();
                break;
            default:
                Driver_Game.getCurrentGameDriver().update(keyPressName);
                break;
        }
    }

    @Override
    protected void next(){
        //if next data == [end] end this
        //if has waitsignal signal the waitsignal (start the boss fight)
        String nextCommand = data.get(dataPoint);
        dataPoint++;
        if(nextCommand.trim().equals("[END]")){
            Driver.clear(1);
            //change here later
            //waitSignal.signalThis();
            Driver_Game.getCurrentGameDriver().victoryEnd();
            return;
        }

        //otherwise display the next one
        String speaker = nextCommand.substring(0, 3);
        String dialogue = nextCommand.substring(3);
        if(speaker.contains("P") || speaker.contains("p")){
            drawImage(true, dialogue);
        }
        else if(speaker.contains("E") || speaker.contains("e")){
            drawImage(false, dialogue);
        }
        else{
            System.out.println("Dialogue Error: Neither enemy nor player");
            System.exit(0);
        }
    }
}
