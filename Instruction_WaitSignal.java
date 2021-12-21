public class Instruction_WaitSignal extends Instruction {

    private boolean signal = false;
    public Instruction_WaitSignal(Entity owner){
        super(owner);
    }
    public Instruction_WaitSignal(Entity owner, Behavior next){
        super(owner, next);
    }

    @Override
    public boolean doAction(){
        return signal;
    }

    public void signalThis(){
        signal = true;
    }
}
