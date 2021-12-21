public class Routine_WaitDeathOrBomb extends Routine {
    private int initLives;
    private int initBombs;
    private boolean firstAction = true;
    private Behavior root;

    public Routine_WaitDeathOrBomb(Entity owner){
        super(owner);
//        initLives = (int)Player.getThePlayer().getHP();
//        initBombs = Player.getThePlayer().getBombs();
    }
    public Routine_WaitDeathOrBomb(Entity owner, Behavior next){
        super(owner, next);
//        initLives = (int)Player.getThePlayer().getHP();
//        initBombs = Player.getThePlayer().getBombs();
    }
    public Routine_WaitDeathOrBomb(Entity owner, Behavior next, Behavior instructions){
        super(owner, next, instructions);
//        initLives = (int)Player.getThePlayer().getHP();
//        initBombs = Player.getThePlayer().getBombs();
        root = instructions;
    }

    @Override
    public boolean doAction(){
        if(!firstAction) {
            int currentLives = (int) Player.getThePlayer().getHP();
            int currentBombs = Player.getThePlayer().getBombs();
            if (currentLives < initLives) {
                initLives = currentLives;
                initBombs = currentBombs;
                firstAction = true;
//            System.out.println("lives");
                instructions = root;
                resetTimers();
                return true;
            }
            if (currentBombs < initBombs) {
                initLives = currentLives;
                initBombs = currentBombs;
                firstAction = true;
//            System.out.println("bombs");
                instructions = root;
                resetTimers();
                return true;
            }
        }
        else{
            initLives = (int)Player.getThePlayer().getHP();
            initBombs = Player.getThePlayer().getBombs();
            firstAction = false;
        }

        while(instructions.doAction()) {
            if(instructions.getNext() != null) {
                instructions = instructions.getNext();
            }
            else{
                return true;
            }
        }
        return false;
    }
}
