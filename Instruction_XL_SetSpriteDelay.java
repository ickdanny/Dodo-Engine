public class Instruction_XL_SetSpriteDelay extends Instruction {

    private int delay;
    public Instruction_XL_SetSpriteDelay(Enemy_Boss_XiaoLi owner, int delay){
        super(owner);
        this.delay = delay;
    }
    public Instruction_XL_SetSpriteDelay(Enemy_Boss_XiaoLi owner, Behavior next, int delay){
        super(owner, next);
        this.delay = delay;
    }

    @Override
    public boolean doAction(){
        ((Enemy_Boss_XiaoLi)owner).setSpriteDelay(delay);
        return true;
    }
}
