//cannot be used directly after an attack command - needs a timer
public class Instruction_WaitAttackOver extends Instruction {

    public Instruction_WaitAttackOver(Entity owner){
        super(owner);
    }

    public Instruction_WaitAttackOver(Entity owner, Behavior next){
        super(owner, next);
    }

    @Override
    public boolean doAction(){
        return owner.attackTimer <= 0;
    }
}
