public class Instruction_ToggleCanDamage extends Instruction {

    protected boolean canDamage;
    public Instruction_ToggleCanDamage(Entity owner, boolean canDamage){
        super(owner);
        this.canDamage = canDamage;
    }
    public Instruction_ToggleCanDamage(Entity owner, Behavior next, boolean canDamage){
        super(owner, next);
        this.canDamage = canDamage;
    }
    @Override
    public boolean doAction(){
        owner.canDamage = canDamage;
        return true;
    }
}
