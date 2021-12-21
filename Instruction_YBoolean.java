public class Instruction_YBoolean extends Instruction {

    //true = above | false = below
    private boolean aboveOrBelow;
    private double position;

    public Instruction_YBoolean(Entity owner, boolean a, double p){
        super(owner);
        aboveOrBelow = a;
        position = p;
    }
    public Instruction_YBoolean(Entity owner, Behavior next, boolean a, double p){
        super(owner);
        aboveOrBelow = a;
        position = p;
    }

    @Override
    public boolean doAction(){
        if(aboveOrBelow){
            if(owner.getY() < position){
                return true;
            }
        }
        else{
            if(owner.getY() > position){
                return true;
            }
        }
        return false;
    }
}
