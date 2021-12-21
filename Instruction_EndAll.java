public class Instruction_EndAll extends Instruction {
    public Instruction_EndAll(Entity owner) {
        super(owner);
    }

    public Instruction_EndAll(Entity owner, Behavior next) {
        super(owner, next);
    }

    //this method ends all behavior (not meaning instructions or routines) of the owner.
    @Override
    public boolean doAction() {
        owner.stopAll();
        return false;
    }
}
