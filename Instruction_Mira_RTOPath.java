public class Instruction_Mira_RTOPath extends Instruction {
    public Instruction_Mira_RTOPath(Entity owner){
        super(owner);
    }
    public Instruction_Mira_RTOPath(Entity owner, Behavior next){
        super(owner, next);
    }
    @Override
    public boolean doAction(){
        if(owner instanceof EP_Orb_Mira_RTO){
            ((EP_Orb_Mira_RTO)owner).startPath();
        }
        return true;
    }
}
