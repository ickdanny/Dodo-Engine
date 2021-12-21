public class Instruction_Suicide extends Instruction{
    public Instruction_Suicide(Entity owner) {
        super(owner);
    }

    public Instruction_Suicide(Entity owner, Behavior next) {
        super(owner, next);
    }

    @Override
    public boolean doAction() {
        owner.suicide();
        return false;
    }
}
