public class Instruction_Mira_WOT extends Instruction {
    String state;
    public Instruction_Mira_WOT(Entity owner, String state){
        super(owner);
        this.state = state;
    }
    public Instruction_Mira_WOT(Entity owner, Behavior next, String state){
        super(owner, next);
        this.state = state;
    }
    @Override
    public boolean doAction(){
        for(Projectile_Enemy e : Driver_GameLogic.getCurrentLogicDriver().getProjectileEnemies()){
            if(e instanceof EP_Orb_Mira_WOT){
                ((EP_Orb_Mira_WOT)e).changeState(state);
            }
        }
        return true;
    }
}
