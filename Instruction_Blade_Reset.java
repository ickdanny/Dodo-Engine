public class Instruction_Blade_Reset extends Instruction {

    private int newPhase;
    public Instruction_Blade_Reset(EP_Blade owner, int newPhase){
        super(owner);
        this.newPhase = newPhase;
    }
    public Instruction_Blade_Reset(EP_Blade owner, Behavior next, int newPhase){
        super(owner, next);
        this.newPhase = newPhase;
    }

    @Override
    public boolean doAction(){
        if(newPhase == 0) {
            ((EP_Blade) owner).resetTarget();
        }
        else if(newPhase == 2){
            ((EP_Blade)owner).rtoOrbit();
        }
        ((EP_Blade)owner).changePhase(newPhase);
        return true;
    }
}
