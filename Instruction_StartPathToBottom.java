public class Instruction_StartPathToBottom extends Instruction {

    private double speed;
    public Instruction_StartPathToBottom(Entity owner, double speed){
        super(owner);
        this.speed = speed;
    }

    public Instruction_StartPathToBottom(Entity owner, Behavior next, double speed){
        super(owner, next);
        this.speed = speed;
    }

    @Override
    public boolean doAction(){
        Vector p = new Vector(owner.getX(), Driver_Game.getHeight() - owner.getSize());
        Path_Polygon path = new Path_Polygon(p, false, speed);
        owner.newPath(path);
        return true;
    }
}
