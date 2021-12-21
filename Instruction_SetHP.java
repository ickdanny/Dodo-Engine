//MUST BE USED FOR BOSSES TO SET THE HP OF EACH PHASE
public class Instruction_SetHP extends Instruction {

    protected double hp;
    public Instruction_SetHP(Entity owner, double hp){
        super(owner);
        this.hp = hp;
    }
    public Instruction_SetHP(Entity owner, Behavior next, double hp){
        super(owner, next);
        this.hp = hp;
    }
    @Override
    public boolean doAction(){
        owner.hp = hp;
        if(owner instanceof Enemy_Boss){
            ((Enemy_Boss)owner).maxHP = hp;
        }
        return true;
    }
}
