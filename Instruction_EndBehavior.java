public class Instruction_EndBehavior extends Instruction{
    public Instruction_EndBehavior(Entity owner){
        super(owner);
    }
    public Instruction_EndBehavior(Entity owner, Behavior next){
        super(owner, next);
    }

    //this instruction ends the instruction chain.
    @Override
    public boolean doAction(){
        owner.cancelBehavior();
        return false;
    }
}
