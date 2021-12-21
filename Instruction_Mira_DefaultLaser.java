public class Instruction_Mira_DefaultLaser extends Instruction {
    private int type;
    public Instruction_Mira_DefaultLaser(Entity owner, int type){
        super(owner);
        this.type = type;
    }
    public Instruction_Mira_DefaultLaser(Entity owner, Behavior next, int type){
        super(owner, next);
        this.type = type;
    }
    @Override
    public boolean doAction(){
        if(owner instanceof Enemy_Boss_Mira){
            ((Enemy_Boss_Mira)owner).attackDefaultLaser(type);
        }
        return true;
    }
}
