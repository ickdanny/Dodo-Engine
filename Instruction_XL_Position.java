public class Instruction_XL_Position extends Instruction {

//    private double speed;
    public Instruction_XL_Position(Entity owner){
        super(owner);
//        this.speed = speed;
    }
    public Instruction_XL_Position(Entity owner, Behavior next){
        super(owner, next);
//        this.speed = speed;
    }

    @Override
    public boolean doAction(){
        return Math.abs(Player.getThePlayer().getX() - owner.getX()) < 20;
    }
}
