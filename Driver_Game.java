//Author: ickdanny
import java.awt.*;
import java.awt.image.BufferedImage;

//driver class
public class Driver_Game implements Updatable, Drawable, Runnable{
    private static Driver_Game currentGameDriver;
    //dimensions
    private final static double WIDTH = 850;
    private final static double HEIGHT = 1000;
    //UPS FPS
    private final static int UPDATES_PER_SECOND = 60;
    private final static int MAX_FRAMES_PER_SECOND = 100;
    private final static int SKIP_TICKS = 1000 / UPDATES_PER_SECOND;
    private final static int MIN_GRAPHICS_SKIP_TICKS = 1000 / MAX_FRAMES_PER_SECOND;
    private final static int MAX_FRAME_SKIP = 5;
//    //game related fields
//    private final static int DIFFICULTY = 9;
//    private final static String FRAME_NAME = "Game";
//    private final static String STAGE_NAME = "Stage4";
//    private static Driver_GameGraphics graphicsDriver;
//    private static Driver_GameLogic logicDriver;
//    //player related values
//    private final static String PLAYER_NAME = "AzraelB";
//    private final static int INIT_LIVES = 100000;
//    private final static int INIT_BOMBS = 100000;
//    private final static int INIT_POWER = 200;

    private String stageName;
    private Driver_GameGraphics graphicsDriver;
    private Driver_GameLogic logicDriver;

    private String playerName;

    private boolean left = false, right = false, up = false, down = false;
    private boolean practice;

    private boolean paused = false;
    private boolean over = false;

    //main method
    //public static void main(String[] args) {
    public Driver_Game(int difficulty, String stageName, String playerName, boolean practice, int initLives, int initBombs, int initPower, int initSpells) {
        //logic driver needs to be passed the stagename for the switch statement in Enemyspawner at a later date we will implement this
        //also the difficulty should be passed for similar reasons
        logicDriver = new Driver_GameLogic(difficulty, stageName, playerName, initLives, initBombs, initPower, initSpells);
        graphicsDriver = new Driver_GameGraphics(playerName, stageName);
        this.stageName = stageName;
        this.playerName = playerName;
        this.practice = practice;
        currentGameDriver = this;
        //doing this here
        Enemy_Boss.resetTheBoss();
//        Driver_Music.playStageTrack(stageName);
    }
    //}


    //public void startGameLoop(){
    public void run(){
//        System.out.println("Started run method " + Thread.currentThread());
        Driver_Music.playStageTrack(stageName);
        long nextGameTick = System.currentTimeMillis();
        long nextGraphicsTick = System.currentTimeMillis();
        int loops;
        double deltaTime;

        //we'll change this condition later
        while(!paused && !over) {
//            System.out.println(paused);
            //if (!paused) {
                loops = 0;
//                System.out.println("Started" + System.currentTimeMillis());
                while (System.currentTimeMillis() > nextGameTick && loops < MAX_FRAME_SKIP) {
//                    System.out.println("While loop started" + System.currentTimeMillis());
                    logicDriver.updateGame();
//                    System.out.println("test");
                    //every time the game updates the background does too
                    graphicsDriver.updateDrawPoint();
                    nextGameTick += SKIP_TICKS;
                    loops++;
//                System.out.println(logicDriver.getTick());
                }

                if (System.currentTimeMillis() > nextGraphicsTick) {
                    deltaTime = (double) (System.currentTimeMillis() + SKIP_TICKS - nextGameTick) / (double) SKIP_TICKS;
                    graphicsDriver.displayGame(deltaTime);
                    Driver.updateImage();
                    nextGraphicsTick += MIN_GRAPHICS_SKIP_TICKS;
                }
//                System.out.println("updated" + System.currentTimeMillis());
//            System.out.println(SwingUtilities.isEventDispatchThread());
            //}
        }
//        System.out.println("Thread ended " + this + " " + Thread.currentThread());
    }

//    //keyaction class
//    private static class KeyAction extends AbstractAction{
//        //string command, booleans for direction
//        private String cmd;
//        private static boolean left = false, right = false, up = false, down = false;
//        //constructor
//        KeyAction(String cmd){
//            this.cmd = cmd;
//        }
//        //switch on command string
//        @Override
//        public void actionPerformed(ActionEvent e){
//            switch(cmd){
//                case "Left":
//                    left = true;
//                    logicDriver.playerMove(movementVector());
//                    break;
//                case "Right":
//                    right = true;
//                    logicDriver.playerMove(movementVector());
//                    break;
//                case "Up":
//                    up = true;
//                    logicDriver.playerMove(movementVector());
//                    break;
//                case "Down":
//                    down = true;
//                    logicDriver.playerMove(movementVector());
//                    break;
//                case "RLeft":
//                    left = false;
//                    logicDriver.playerMove(movementVector());
//                    break;
//                case "RRight":
//                    right = false;
//                    logicDriver.playerMove(movementVector());
//                    break;
//                case "RUp":
//                    up = false;
//                    logicDriver.playerMove(movementVector());
//                    break;
//                case "RDown":
//                    down = false;
//                    logicDriver.playerMove(movementVector());
//                    break;
//                case "Focus":
//                    logicDriver.playerFocus(true);
//                    break;
//                case "RFocus":
//                    logicDriver.playerFocus(false);
//                    break;
//                case "Attack":
//                    logicDriver.playerAttack(true);
//                    break;
//                case "RAttack":
//                    logicDriver.playerAttack(false);
//                    break;
//                case "Bomb":
//                    logicDriver.bomb();
//                    break;
//                case "RBomb":
//                    logicDriver.releaseBomb();
//                    break;
//            }
//        }
//        //method to get unit velocity vector with same angle as the directional vectors together
//        //the Driver_GameLogic will set the speed of the player later.
//        //returns the polar vector btw
//        private Vector movementVector(){
//            Vector toRet = new Vector();
//            if(left)
//                toRet.addToThis(new Vector(-1, 0));
//            if(right)
//                toRet.addToThis(new Vector(1, 0));
//            if(up)
//                toRet.addToThis(new Vector(0, -1));
//            if(down)
//                toRet.addToThis(new Vector(0, 1));
//            toRet = Vector.velocityToPolar(toRet);
//            if(toRet.getB() != 0) {
//                toRet.setB(1);
//            }
//            return toRet;
//        }
//    }

    public void update(String keyPressName){
//        System.out.println(keyPressName);
        switch(keyPressName){
            case "LeftArrow":
                left = true;
                logicDriver.playerMove(movementVector());
                break;
            case "RightArrow":
                right = true;
                logicDriver.playerMove(movementVector());
                break;
            case "UpArrow":
                up = true;
                logicDriver.playerMove(movementVector());
                break;
            case "DownArrow":
                down = true;
                logicDriver.playerMove(movementVector());
                break;
            case "RLeftArrow":
                left = false;
                logicDriver.playerMove(movementVector());
                break;
            case "RRightArrow":
                right = false;
                logicDriver.playerMove(movementVector());
                break;
            case "RUpArrow":
                up = false;
                logicDriver.playerMove(movementVector());
                break;
            case "RDownArrow":
                down = false;
                logicDriver.playerMove(movementVector());
                break;
            case "Shift":
                logicDriver.playerFocus(true);
                break;
            case "RShift":
                logicDriver.playerFocus(false);
                break;
            case "Z Key":
                logicDriver.playerAttack(true);
                break;
            case "RZ Key":
                logicDriver.playerAttack(false);
                break;
            case "X Key":
                logicDriver.bomb();
                break;
            case "RX Key":
                logicDriver.releaseBomb();
                break;
                //and then the case for escape - pause menu
            case "Escape":
                if(!logicDriver.victoryRunning && !logicDriver.defeatRunning) {
                    paused = true;
                    Driver.add(new Menu_Pause());
                }
                break;
        }
    }

    public void startDialogue(String preOrPost, Instruction_WaitSignal ws){
        resetNonMovementInputs();

//        Driver.add(new DialogueScreen(stageName, preOrPost, logicDriver.getDifficulty(), ws));
        Runnable dialogueRunnable = new Runnable() {
            public void run() {
                try {
                    Driver.add(new DialogueScreen(stageName, preOrPost, logicDriver.getDifficulty(), ws));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        };
        Thread thread = new Thread(dialogueRunnable);
        thread.start();
    }

    public void victory(){
        //some code

        //only play victory dialogue for stages 1-4 //also EX
        if(stageName.equals("StageEX")){
            resetAllInputs();

            Runnable dialogueRunnable = new Runnable() {
                public void run() {
                    try {
                        Driver.add(new DialogueEndScreen(stageName, "Post", logicDriver.getDifficulty()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(0);
                    }
                }
            };
            Thread thread = new Thread(dialogueRunnable);
            thread.start();
        }
        else if(Integer.parseInt("" + stageName.charAt(stageName.length() - 1)) <= 4){
            resetAllInputs();

            Runnable dialogueRunnable = new Runnable() {
                public void run() {
                    try {
                        Driver.add(new DialogueEndScreen(stageName, "Post", logicDriver.getDifficulty()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(0);
                    }
                }
            };
            Thread thread = new Thread(dialogueRunnable);
            thread.start();
        }
        else{
            victoryEnd();
        }
        //if practice clear this from the driver and return

        //if final stage play credits
    }

    public void victoryEnd(){
//        System.out.println(stageName);
        //if practice clear this from the driver and return
        if(practice){
            Driver.clear(1);
            Driver_Music.playTrack("1_Solitary_Dodecahedron", true);
            return;
        }
        if(stageName.equals("StageEX")){
            Driver.clear(1);
            Driver_Music.playTrack("1_Solitary_Dodecahedron", true);
            return;
        }
        //if not final stage go to next stage
        if(Integer.parseInt("" + stageName.charAt(stageName.length() - 1)) <= 5) {
            int next = Integer.parseInt("" + stageName.charAt(stageName.length() - 1)) + 1;
            String nextStage = "Stage" + next;
            Driver_Game toAdd = new Driver_Game(logicDriver.getDifficulty(), nextStage, playerName, false,
                    ((int)Player.getThePlayer().getHP()) + 1, Player.getThePlayer().getBombs(), Player.getThePlayer().getPower(), logicDriver.getNumSpells());
            Driver.clear(10);
            Driver.add(toAdd);
        }
        //if final stage play credits
        else{
            //play credits
            CreditsScreen toAdd = new CreditsScreen();
            Driver.clear(10);
            Driver.add(toAdd);
        }
    }
    //some getters and setters for the driver class
    public static int getUPS(){
    return UPDATES_PER_SECOND;
}
    //public int getDifficulty(){
//        return difficulty;
//    }
    public static double getWidth(){
        return WIDTH;
    }
    public static double getHeight(){
        return HEIGHT;
    }

//    public String getPlayerName(){
//        return playerName;
//    }

    //this is used by initSprite for some entities where the offset is different depending on which sprite sheet is in use
    public String getStageName(){
        return stageName;
    }

    public void unpause(){
//        System.out.println(Thread.currentThread());
        if(!paused){
            return;
        }
        paused = false;
        resetAllInputs();
        Thread game = new Thread(this, "Thread-fromUnpause-" + this);
        game.start();
    }

    //is this defeat?
    public void endThis(){
        over = true;
        Driver_Music.playTrack("1_Solitary_Dodecahedron", true);
    }

    private void resetAllInputs(){
        left = false;
        right = false;
        up = false;
        down = false;
        logicDriver.playerAttack(false);
        logicDriver.playerFocus(false);
        logicDriver.releaseBomb();
        logicDriver.playerMove(movementVector());
    }

    private void resetNonMovementInputs(){
        logicDriver.playerAttack(false);
        logicDriver.releaseBomb();
    }

    //LATER WHEN WE ADD IN THE SIDEBAR AND SUCH THIS WILL BE THE METHOD TO COMBINE THEM ALL
    public BufferedImage getImage(){
//        BufferedImage toRet = graphicsDriver.getToDraw();
//        return toRet;
        BufferedImage toRet = new BufferedImage(Driver.getWidth(), Driver.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = toRet.createGraphics();
        //rest of code here
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, toRet.getWidth(), toRet.getHeight());

        BufferedImage gameView = graphicsDriver.getToDraw();
        g2d.drawImage(getTopBar(), 200, 0, null);
        g2d.setColor(Color.white);
//        g2d.fillRect(195, 65, (int)((double)gameView.getWidth()/1.1) + 10, (int)((double)gameView.getHeight()/1.1) + 10);
        g2d.fillRect(196, 66, (int)((double)gameView.getWidth()/1.1) + 9, (int)((double)gameView.getHeight()/1.1) + 9);
        g2d.drawImage(gameView, 200, 70, (int)((double)gameView.getWidth()/1.1), (int)((double)gameView.getHeight()/1.1), null);
//        g2d.drawImage(gameView, 180, 0, null);
        g2d.drawImage(getSideBar(), 1040, 0, null);
        drawStageName(g2d);
        return toRet;
    }

    private BufferedImage getSideBar(){
        BufferedImage toRet = new BufferedImage(400, 1000, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = toRet.createGraphics();
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, toRet.getWidth(), toRet.getHeight());
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 40));

        //draw lives ♡
        g2d.setColor(Color.getHSBColor((float)327/400, (float)24/100, (float)86/100));
        int initX = 0;
        int initY = 150;
        int x = initX;
        int y = initY;
        int lives = (int)logicDriver.getPlayer().getHP();
        if(lives <= 10){
            for(int i = 0; i < lives; i++){
                g2d.drawString("♥", x, y);
                x += 40;
            }
        }
        else{
            for(int i = 0; i < 10; i++){
                g2d.drawString("♥", x, y);
                x += 40;
            }
            lives -= 10;
            x = initX;
            y += 40;
            for(int i = 0; i < lives; i++){
                g2d.drawString("♥", x, y);
                x += 40;
            }
        }
        //draw bombs
        g2d.setColor(Color.getHSBColor((float)129/400, (float)24/100, (float)86/100));
        initX = 0;
        initY = 250;
        x = initX;
        y = initY;
        int bombs = logicDriver.getPlayer().getBombs();
        if(bombs <= 10){
            for(int i = 0; i < bombs; i++){
                g2d.drawString("★", x, y);
                x += 40;
            }
        }
        else{
            for(int i = 0; i < 10; i++){
                g2d.drawString("★", x, y);
                x += 40;
            }
            bombs -= 10;
            x = initX;
            y += 40;
            for(int i = 0; i < bombs; i++){
                g2d.drawString("★", x, y);
                x += 40;
            }
        }
        //draw power
        g2d.setColor(Color.getHSBColor((float)6/400, (float)24/100, (float)86/100));
        g2d.setFont(new Font("Monospaced", Font.BOLD, 100));
        //109 x
        g2d.drawString("" + logicDriver.getPlayer().getPower(), 0, 440);

        //draw captured spells
        g2d.setColor(Color.getHSBColor((float)129/400, (float)24/100, (float)86/100));
        g2d.drawString("" + logicDriver.getNumSpells(), 0, 560);

        g2d.setColor(Color.white);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 30));
        g2d.drawString("Dodecahedron", 95, 700);
        g2d.drawString("At", 185, 750);
        g2d.drawString("Time's", 148, 800);
        g2d.drawString("End", 177, 850);

        return toRet;
    }

    private BufferedImage getTopBar(){

        BufferedImage toRet = new BufferedImage(772, 66, BufferedImage.TYPE_INT_RGB);
        if(Enemy_Boss.getTheBoss() == null){
            return toRet;
        }
        Graphics2D g2d = toRet.createGraphics();
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 20));
        double percentHP;
        //divide by zero
        try{
            percentHP = Enemy_Boss.getTheBoss().hp / Enemy_Boss.getTheBoss().maxHP;
        }catch(Exception e){
            percentHP = 0;
        }
        g2d.drawString("HP", 20, 30);

        String spellName = Enemy_Boss.getTheBoss().getSpellName();
        if(spellName != null && !spellName.equals("")){
            g2d.drawString("\"" + Enemy_Boss.getTheBoss().getSpellName() + "\"", 20, 60);
        }

        if(percentHP > 0.001){
            double lineLength = 712 * percentHP;
            g2d.setStroke(new BasicStroke(5));
            g2d.drawLine(60, 20, 60 + ((int) lineLength), 20);
        }

        return toRet;
    }

    private void drawStageName(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 30));
        String toDraw = getStageNameDraw();
        char[] array = toDraw.toCharArray();
        int x = 85;
        int y = 92;
        for(char c : array){
            g2d.drawString("" + c, x, y);
            y += 30;
        }
    }

    private String getStageNameDraw(){
        switch(stageName){
            case "Stage1":
                return "Stage 1 - Ordinary Room";
//                break;
            case "Stage2":
                return "Stage 2 - Sandbox";
//                break;
            case "Stage3":
                return "Stage 3 - Spider's Nest";
//                break;
            case "Stage4":
                return "Stage 4 - Forge";
//                break;
            case "Stage5":
                return "Stage 5 - Laboratory Lobby";
//                break;
            case "Stage6":
                return "Stage 6 - Dodecahedron Head";
//                break;
            case "StageEX":
                return "Extra Stage - Hellscape";
//                break;
            default:
                return "No Stage Name";
        }
    }

    public Vector getLocation(){
        return new Vector();
    }

    private Vector movementVector(){
        Vector toRet = new Vector();
        if(left)
            toRet.addToThis(new Vector(-1, 0));
        if(right)
            toRet.addToThis(new Vector(1, 0));
        if(up)
            toRet.addToThis(new Vector(0, -1));
        if(down)
            toRet.addToThis(new Vector(0, 1));
        toRet = Vector.velocityToPolar(toRet);
        if(toRet.getB() != 0) {
            toRet.setB(1);
        }
        return toRet;
    }

    public static Driver_Game getCurrentGameDriver(){
        return currentGameDriver;
    }
    /*!logicDriver.getGameOver() && */
}