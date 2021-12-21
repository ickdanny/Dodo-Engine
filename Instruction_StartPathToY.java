public class Instruction_StartPathToY extends Instruction {

    private double y;
    private double speed;

    public Instruction_StartPathToY(Entity owner, double y, double speed){
        super(owner);
        this.y = y;
        this.speed = speed;
    }
    public Instruction_StartPathToY(Entity owner, Behavior next, double y, double speed){
        super(owner, next);
        this.y = y;
        this.speed = speed;
    }

    @Override
    public boolean doAction(){
        Vector p = new Vector(owner.getX(), y);
        Path_Polygon path = new Path_Polygon(p, false, speed);
        owner.newPath(path);
        return true;
    }
}
