public class Instruction_ToggleCanBeDamaged extends Instruction {

    protected boolean canBeDamaged;
    public Instruction_ToggleCanBeDamaged(Entity owner, boolean canBeDamaged){
        super(owner);
        this.canBeDamaged = canBeDamaged;
    }
    public Instruction_ToggleCanBeDamaged(Entity owner, Behavior next, boolean canBeDamaged){
        super(owner, next);
        this.canBeDamaged = canBeDamaged;
    }
    @Override
    public boolean doAction(){
        owner.canBeDamaged = canBeDamaged;
        return true;
    }
}
