public class Instruction_XL_SetSprite extends Instruction {
    private int spriteNum;
    public Instruction_XL_SetSprite(Enemy_Boss_XiaoLi owner, int spriteNum){
        super(owner);
        this.spriteNum = spriteNum;
    }
    public Instruction_XL_SetSprite(Enemy_Boss_XiaoLi owner, Instruction next, int spriteNum){
        super(owner, next);
        this.spriteNum = spriteNum;
    }

    @Override
    public boolean doAction(){
        ((Enemy_Boss_XiaoLi)owner).changeSprite(spriteNum);
        return true;
    }
}
