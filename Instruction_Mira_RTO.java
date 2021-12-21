public class Instruction_Mira_RTO extends Instruction {
    public Instruction_Mira_RTO(Entity owner){
        super(owner);
    }
    public Instruction_Mira_RTO(Entity owner, Behavior next){
        super(owner, next);
    }
    @Override
    public boolean doAction(){
        for(Projectile_Enemy e : Driver_GameLogic.getCurrentLogicDriver().getProjectileEnemies()){
            if(e instanceof EP_Orb_Mira_RTO){
                int time = (int)(Math.random() * 300);
                Instruction_Mira_RTOPath path = new Instruction_Mira_RTOPath(e);
                Instruction_Timer timer = new Instruction_Timer(e, path, time);
                e.setNewBehavior(timer);
            }
        }
        return true;
    }
}
