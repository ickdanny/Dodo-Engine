public class Instruction_XL_SetSpear extends Instruction {
    private String spearPos;
    public Instruction_XL_SetSpear(Enemy_Boss_XiaoLi owner, String spearPos){
        super(owner);
        this.spearPos = spearPos;
    }
    public Instruction_XL_SetSpear(Enemy_Boss_XiaoLi owner, Instruction next, String spearPos){
        super(owner, next);
        this.spearPos = spearPos;
    }

    @Override
    public boolean doAction(){
        ((Enemy_Boss_XiaoLi)owner).changeSpearPos(spearPos);
        return true;
    }
}
