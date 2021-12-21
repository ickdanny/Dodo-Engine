public class Instruction_CancelPath extends Instruction {

    public Instruction_CancelPath(Entity owner){
        super(owner);
    }
    public Instruction_CancelPath(Entity owner, Behavior next){
        super(owner, next);
    }

    @Override
    public boolean doAction(){
        owner.cancelPath();
        return true;
    }
}
