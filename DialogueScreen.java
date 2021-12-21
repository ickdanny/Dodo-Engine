import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DialogueScreen implements Updatable, Drawable {

    protected static double sizeFactor = (double)325/2764;
    protected BufferedImage playerProfile;
    protected BufferedImage enemyProfile;
    protected ArrayList<String> data;
    protected int dataPoint = 0;

    protected BufferedImage toRet;

    //if it's a post this should be null
    protected Instruction_WaitSignal waitSignal = null;

    public DialogueScreen(String stageName, String preOrPost, int difficulty, Instruction_WaitSignal waitSignal){
        toRet = new BufferedImage(Driver.getWidth(), Driver.getHeight(), BufferedImage.TYPE_INT_RGB);
        //the name would be something like Stage4Post7 or StageEXPre9
        String fileName = stageName + preOrPost + difficulty;
        File file = new File("./res/Dialogue/" + fileName + ".txt");
        Scanner scanner;
        data = new ArrayList<>();
        try {
            scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                data.add(scanner.nextLine());
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.exit(0);
        }

        String playerName = data.get(dataPoint).substring(1, data.get(dataPoint).length() - 1);
        dataPoint++;
        String enemyName = data.get(dataPoint).substring(1, data.get(dataPoint).length() - 1);
        dataPoint++;

        //each difficulty has only 1 player option
        //get the profiles, get the data
        //profiles = first 2 lines of the text file
        try {
            BufferedImage player = ImageIO.read(new File("./res/Profiles/" + playerName + ".png"));
            playerProfile = new BufferedImage((int)(player.getWidth() * sizeFactor), (int)(player.getHeight() * sizeFactor), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = playerProfile.createGraphics();
            g2d.drawImage(player, 0, 0, (int)(player.getWidth() * sizeFactor), (int)(player.getHeight() * sizeFactor), null);
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        try{
            BufferedImage enemy = ImageIO.read(new File("./res/Profiles/" + enemyName + ".png"));
            enemyProfile = new BufferedImage((int)(enemy.getWidth() * sizeFactor), (int)(enemy.getHeight() * sizeFactor), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = enemyProfile.createGraphics();
            g2d.drawImage(enemy, 0, 0, (int)(enemy.getWidth() * sizeFactor), (int)(enemy.getHeight() * sizeFactor), null);
        }catch(Exception e){
            enemyProfile = null;
        }
        this.waitSignal = waitSignal;
        next();
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
                waitSignal.signalThis();
                break;
            default:
                Driver_Game.getCurrentGameDriver().update(keyPressName);
                break;
        }
    }

    @Override
    public BufferedImage getImage(){
        return toRet;
    }

    @Override
    public Vector getLocation(){
        return new Vector();
    }

    protected void next(){
        //if next data == [end] end this
        //if has waitsignal signal the waitsignal (start the boss fight)
        String nextCommand = data.get(dataPoint);
        dataPoint++;
        if(nextCommand.trim().equals("[END]")){
            //System.out.println("ended"); RUNS ONCE
            Driver.clear(1);
            Driver_Music.playBossTrack(Driver_Game.getCurrentGameDriver().getStageName());
//            System.out.println("yes");
            waitSignal.signalThis();
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

    //player = true = light up player
    //player = false = light up enemy
    protected void drawImage(boolean player, String dialogue){
        BufferedImage toSet = new BufferedImage(Driver.getWidth(), Driver.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = toSet.createGraphics();
        //draw images if they exist (Zephres has no profile)
        //height will always be height of image
        //player will display at x=100+(200 - width)
        //enemy will display at x=872

        //clear
//        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
//        g2d.fillRect(0,0,toSet.getWidth(),toSet.getHeight());
//
//        //reset composite
//        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
//        g2d.setBackground(new Color(0, 0, 0, 0));
//        g2d.setBackground(Color.blue);

        if(!player){
//            System.out.println("Transparent 1");
            float alpha = (float)0.5;
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
            g2d.setComposite(ac);
        }
        g2d.drawImage(playerProfile, 100 + (200 - playerProfile.getWidth()), toSet.getHeight() - playerProfile.getHeight(), null);
        if(player){
//            System.out.println("Transparent 2");
            float alpha = (float)0.5;
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
            g2d.setComposite(ac);
        }
        else{
            float alpha = 1;
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
            g2d.setComposite(ac);
        }
        if(enemyProfile != null){
            g2d.drawImage(enemyProfile, 872, toSet.getHeight() - enemyProfile.getHeight(), null);
        }
        float alpha = 1;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
        g2d.setComposite(ac);

        //code to draw the text
        //may need to wrap around
        g2d.drawImage(getDialogueImage(dialogue), 300, Driver.getHeight() - 150, null);
        this.toRet = toSet;
    }

    private BufferedImage getDialogueImage(String dialogue){
        BufferedImage toRet = new BufferedImage(572, 100, BufferedImage.TYPE_INT_ARGB);
        //code to draw the text, including wraps if needed
        Graphics2D g2d = toRet.createGraphics();
        //g2d.setBackground(new Color(0, 0, 0, 0));
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 20));
        //effective width = 560 = 36 characters per line max
        dialogue = dialogue.trim();
        int fullLength = dialogue.length();
        ArrayList<String> lines = new ArrayList<>();
        int maxLength = 46;
        if(fullLength > maxLength){
            while(dialogue.length() > maxLength) {
                //step 1 - substring maxLength
                String firstLength = dialogue.substring(0, maxLength);
                //step 2 - find last index of space
                int lastSpace = firstLength.lastIndexOf(' ');
                //step 3 - substring 0 - last index
                if(lastSpace != -1) {
                    lines.add(firstLength.substring(0, lastSpace).trim());
                }
                else{
                    lines.add(firstLength.trim());
                }
                //step 4 - write to lines trim()

                //step 5 - set dialogue to substring last index
                if(lastSpace != -1){
                    dialogue = dialogue.substring(lastSpace).trim();
                }
                else{
                    dialogue = dialogue.substring(maxLength);
                }
                //repeat
            }
        }
        lines.add(dialogue.trim());

        int initX = 6;
        for(int i = 0; i < lines.size(); i++){
            g2d.drawString(lines.get(i), initX, (i * 20) + 20);
        }


        return toRet;
    }
}
