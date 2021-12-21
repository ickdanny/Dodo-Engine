public class Instruction_XL_SetPhase extends Instruction {

    public Instruction_XL_SetPhase(Enemy_Boss_XiaoLi owner){
        super(owner);
    }

    public Instruction_XL_SetPhase(Enemy_Boss_XiaoLi owner, Behavior next){
        super(owner, next);
    }

    @Override
    public boolean doAction(){
        ((Enemy_Boss_XiaoLi)owner).setPhase();
        return true;
    }
}
