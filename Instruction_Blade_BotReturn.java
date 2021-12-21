public class Instruction_Blade_BotReturn extends Instruction {

    private Vector returnPos;
    public Instruction_Blade_BotReturn(EP_Blade owner, Vector returnPos){
        super(owner);
        this.returnPos = returnPos;
    }
    public Instruction_Blade_BotReturn(EP_Blade owner, Behavior next, Vector returnPos){
        super(owner, next);
        this.returnPos = returnPos;
    }

    @Override
    public boolean doAction(){
        ((EP_Blade)owner).botReturn(returnPos);
        return true;
    }
}
