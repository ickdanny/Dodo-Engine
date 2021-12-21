public class Instruction_Timer extends Instruction{
    protected double ticksRemaining;
    protected double baseTicks;
    public Instruction_Timer(Entity owner, double t){
        super(owner);
        ticksRemaining = t;
        baseTicks = t;
    }
    public Instruction_Timer(Entity owner, Behavior next, double t){
        super(owner, next);
        ticksRemaining = t;
        baseTicks = t;
    }

    @Override
    public boolean doAction(){
//        return --ticksRemaining <= 0;
//        ticksRemaining--;
        if(ticksRemaining <=0){
            ticksRemaining = baseTicks;
            return true;
        }
        ticksRemaining--;
        return false;
    }

    public void reset(){
        ticksRemaining = baseTicks;
//        System.out.println("reset " + this + " @ " + System.currentTimeMillis());
    }
}