public class Instruction_WaitDeathOrBomb extends Instruction {

    private int initLives;
    private int initBombs;
    boolean firstAction = true;
    public Instruction_WaitDeathOrBomb(Entity owner){
        super(owner);
//        initLives = (int)Player.getThePlayer().getHP();
//        initBombs = Player.getThePlayer().getBombs();
    }
    public Instruction_WaitDeathOrBomb(Entity owner, Behavior next){
        super(owner, next);
//        initLives = (int)Player.getThePlayer().getHP();
//        initBombs = Player.getThePlayer().getBombs();
    }

    @Override
    public boolean doAction(){
        if(firstAction){
            initLives = (int)Player.getThePlayer().getHP();
            initBombs = Player.getThePlayer().getBombs();
            firstAction = false;
            return false;
        }
        int currentLives = (int)Player.getThePlayer().getHP();
        int currentBombs = Player.getThePlayer().getBombs();
        if(currentLives < initLives){
            initLives = currentLives;
            initBombs = currentBombs;
            firstAction = true;
//            System.out.println("lives " + System.currentTimeMillis());
            return true;
        }
        if(currentBombs < initBombs){
            initLives = currentLives;
            initBombs = currentBombs;
            firstAction = true;
//            System.out.println("bombs " + System.currentTimeMillis());
            return true;
        }
        return false;
    }
}
