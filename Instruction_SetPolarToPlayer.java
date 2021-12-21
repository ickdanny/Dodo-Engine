public class Instruction_SetPolarToPlayer extends Instruction {
    double speed;
    public Instruction_SetPolarToPlayer(Entity owner, double speed){
        super(owner);
        this.speed = speed;
    }

    public Instruction_SetPolarToPlayer(Entity owner, Behavior next, double speed){
        super(owner, next);
        this.speed = speed;
    }

    @Override
    public boolean doAction(){
        double angle = Vector.getAngle(owner, Player.getThePlayer());
        owner.setPolarVector(new Vector(angle, speed));
        return true;
    }
}