public class Instruction_EndPath extends Instruction {
    public Instruction_EndPath(Entity owner) {
        super(owner);
    }

    public Instruction_EndPath(Entity owner, Behavior next) {
        super(owner, next);
    }

    @Override
    public boolean doAction() {
        owner.cancelPath();
        return true;
    }
}