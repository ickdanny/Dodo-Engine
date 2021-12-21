public class Instruction_StartPathToPlayer extends Instruction {
    private double speed;
    public Instruction_StartPathToPlayer(Entity owner, double speed){
        super(owner);
        this.speed = speed;
    }

    public Instruction_StartPathToPlayer(Entity owner, Behavior next, double speed){
        super(owner, next);
        this.speed = speed;
    }

    @Override
    public boolean doAction(){
        Vector p = Player.getThePlayer().clone();
        Path_Polygon path = new Path_Polygon(p, false, speed);
        owner.newPath(path);
        return true;
    }
}
