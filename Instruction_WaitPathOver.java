public class Instruction_WaitPathOver extends Instruction {
    public Instruction_WaitPathOver(Entity owner){
        super(owner);
    }

    public Instruction_WaitPathOver(Entity owner, Behavior next){
        super(owner, next);
    }

    @Override
    public boolean doAction(){
        return !owner.followsPath;
    }
}
