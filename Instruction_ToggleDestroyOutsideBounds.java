public class Instruction_ToggleDestroyOutsideBounds extends Instruction {
    protected boolean destroyOutsideBounds;
    public Instruction_ToggleDestroyOutsideBounds(Entity owner, boolean destroyOutsideBounds){
        super(owner);
        this.destroyOutsideBounds = destroyOutsideBounds;
    }
    public Instruction_ToggleDestroyOutsideBounds(Entity owner, Behavior next, boolean destroyOutsideBounds){
        super(owner, next);
        this.destroyOutsideBounds = destroyOutsideBounds;
    }
    @Override
    public boolean doAction(){
        if(owner instanceof Projectile){
            ((Projectile)owner).destroyOutsideBounds = this.destroyOutsideBounds;
        }
        return true;
    }
}
