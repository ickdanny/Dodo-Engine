public class Instruction_ToggleAttack extends Instruction {
    protected boolean continuousAttack;
    public Instruction_ToggleAttack(Entity owner, boolean continuousAttack){
        super(owner);
        this.continuousAttack = continuousAttack;
    }
    public Instruction_ToggleAttack(Entity owner, Behavior next, boolean continuousAttack){
        super(owner, next);
        this.continuousAttack = continuousAttack;
    }
    @Override
    public boolean doAction(){
        owner.setContinuousAttack(continuousAttack);
        return true;
    }
}