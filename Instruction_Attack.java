public class Instruction_Attack extends Instruction {
    protected String attackName;
    protected double attackDelay, reloadDelay;
    public Instruction_Attack(Entity owner, String attackName, double attackDelay, double reloadDelay){
        super(owner);
        this.attackName = attackName;
        this.attackDelay = attackDelay;
        this.reloadDelay = reloadDelay;
    }

    public Instruction_Attack(Entity owner, Behavior next, String attackName, double attackDelay, double reloadDelay){
        super(owner, next);
        this.attackName = attackName;
        this.attackDelay = attackDelay;
        this.reloadDelay = reloadDelay;
    }

    //this instruction will FORCE the owner to attack.
    @Override
    public boolean doAction(){
        owner.stopAttack();
        owner.setCanAttack(true);
        owner.setAttackName(attackName);
        owner.setAttackDelay(attackDelay);
        owner.setReloadDelay(reloadDelay);
        return true;
    }
}