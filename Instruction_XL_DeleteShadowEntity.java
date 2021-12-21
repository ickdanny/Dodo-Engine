public class Instruction_XL_DeleteShadowEntity extends Instruction{

    public Instruction_XL_DeleteShadowEntity(Enemy_Boss_XiaoLi owner){
        super(owner);
    }
    public Instruction_XL_DeleteShadowEntity(Enemy_Boss_XiaoLi owner, Instruction next){
        super(owner, next);
    }

    @Override
    public boolean doAction(){
        ((Enemy_Boss_XiaoLi)owner).deleteToDelete();
        return true;
    }
}