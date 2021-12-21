public class Instruction_Lilith_Phase2 extends Instruction {

    public Instruction_Lilith_Phase2(Enemy_Boss_XiaoLi owner){
        super(owner);
    }

    public Instruction_Lilith_Phase2(Enemy_Boss_XiaoLi owner, Behavior next){
        super(owner, next);
    }

    @Override
    public boolean doAction(){
        ((Enemy_Boss_Lilith)owner).phase2();
        return true;
    }
}
