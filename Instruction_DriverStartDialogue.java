public class Instruction_DriverStartDialogue extends Instruction{

    private String p;
    private Instruction_WaitSignal ws;
    public Instruction_DriverStartDialogue(Entity owner, String p, Instruction_WaitSignal ws) {
        super(owner);
        this.p = p;
        this.ws = ws;
    }

    public Instruction_DriverStartDialogue(Entity owner, String p, Instruction_WaitSignal ws, Behavior next) {
        super(owner, next);
        this.p = p;
        this.ws = ws;
    }

    @Override
    public boolean doAction() {
        Driver_Game.getCurrentGameDriver().startDialogue(p, ws);
        return true;
    }
}
