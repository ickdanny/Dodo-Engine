public class Instruction_TimerRandom extends Instruction_Timer {
    private double ticksLowest;
    private double ticksHighest;

    public Instruction_TimerRandom(Entity owner, double ticksLowest, double ticksHighest){
        super(owner, 0);
        this.ticksLowest = ticksLowest;
        this.ticksHighest = ticksHighest;
        ticksRemaining = getNewBaseTick();
    }

    public Instruction_TimerRandom(Entity owner, Behavior next, double ticksLowest, double ticksHighest){
        super(owner, next, 0);
        this.ticksLowest = ticksLowest;
        this.ticksHighest = ticksHighest;
        ticksRemaining = getNewBaseTick();
    }

    @Override
    public boolean doAction(){
        if(ticksRemaining <=0){
//            ticksRemaining = baseTicks;
            ticksRemaining = getNewBaseTick();
            return true;
        }
        ticksRemaining--;
        return false;
    }

    public void reset(){
//        ticksRemaining = baseTicks;
        ticksRemaining = getNewBaseTick();
    }

//    private int getNewBaseTick(){
//        int diff = ticksHighest - ticksLowest;
//        //+ 1, cut decimal
//        double r = Math.random() * (diff + 1);
//        diff = (int)r;
//        return ticksLowest + diff;
//    }
    private double getNewBaseTick(){
        double diff = ticksHighest - ticksLowest;
        double random = Math.random() * diff;
        return ticksLowest + random;
    }
}
