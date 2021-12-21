public class Instruction_StartPath extends Instruction {
    protected Path path;
    public Instruction_StartPath(Entity owner, Path path) {
        super(owner);
        this.path = path;
    }

    public Instruction_StartPath(Entity owner, Behavior next, Path path) {
        super(owner, next);
        this.path = path;
    }

    @Override
    public boolean doAction() {
        owner.newPath(path.clone());
        return true;
    }
}