public class Instruction_TeleportRandom extends Instruction {

    private double minX, maxX, minY, maxY, xDiff, yDiff;
    public Instruction_TeleportRandom(Entity owner, double minX, double maxX, double minY, double maxY) {
        super(owner);
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.xDiff = maxX - minX;
        this.yDiff = maxY - minY;
    }

    public Instruction_TeleportRandom(Entity owner, Behavior next, double minX, double maxX, double minY, double maxY) {
        super(owner, next);
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.xDiff = maxX - minX;
        this.yDiff = maxY - minY;
    }

    //this instruction teleports an entity to a random position and stops *all* movement.
    @Override
    public boolean doAction(){
        owner.stop();
        owner.setPosition(new Vector((Math.random() * xDiff) + minX, (Math.random() * yDiff) + minY));
        return true;
    }
}
