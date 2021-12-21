public class Instruction_Teleport extends Instruction {
    protected Vector newPos;
    public Instruction_Teleport(Entity owner, Vector newPos) {
        super(owner);
        this.newPos = newPos;
    }

    public Instruction_Teleport(Entity owner, Behavior next, Vector newPos) {
        super(owner, next);
        this.newPos = newPos;
    }

    //this instruction teleports an entity to a given position and stops *all* movement.
    @Override
    public boolean doAction(){
        owner.stop();
        owner.setPosition(newPos);
        return true;
    }
}
