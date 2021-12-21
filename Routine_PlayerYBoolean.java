public class Routine_PlayerYBoolean extends Routine {

    //true = above | false = below
    private boolean aboveOrBelow;
    private double position;

    public Routine_PlayerYBoolean(Entity owner, boolean a, double p){
        super(owner);
        aboveOrBelow = a;
        position = p;
    }
    public Routine_PlayerYBoolean(Entity owner, Behavior next, boolean a, double p){
        super(owner, next);
        aboveOrBelow = a;
        position = p;
    }
    public Routine_PlayerYBoolean(Entity owner, Behavior next, Behavior instructions, boolean a, double p){
        super(owner, next, instructions);
//        this.instructions = instructions;
        aboveOrBelow = a;
        position = p;
    }

    @Override
    public boolean doAction(){
        double playerY = Player.getThePlayer().getY();
        //above means y is smaller
        if(aboveOrBelow){
            if(playerY < position){
                return true;
            }
        }
        else{
            if(playerY > position){
                return true;
            }
        }
        return super.doAction();
    }
}
