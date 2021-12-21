public class Routine_GoWaitGo extends Routine {

    public Routine_GoWaitGo(Entity owner, Vector pos1, Vector pos2, double speed1, double speed2, double waitTime){
        super(owner, null);
        Instruction_StartPath exitPath = new Instruction_StartPath(owner, null, new Path_Polygon(pos2, false, speed2));
        Instruction_Timer timer = new Instruction_Timer(owner, exitPath, waitTime);
        Instruction_WaitPathOver pathOver = new Instruction_WaitPathOver(owner, timer);
        Instruction_StartPath first = new Instruction_StartPath(owner, pathOver, new Path_Polygon(pos1, false, speed1));
        setInstructions(first);
    }

    public Routine_GoWaitGo(Entity owner, Behavior next, Vector pos1, Vector pos2, double speed1, double speed2, double waitTime){
        super(owner, next);
        Instruction_StartPath exitPath = new Instruction_StartPath(owner, null, new Path_Polygon(pos2, false, speed2));
        Instruction_Timer timer = new Instruction_Timer(owner, exitPath, waitTime);
        Instruction_WaitPathOver pathOver = new Instruction_WaitPathOver(owner, timer);
        Instruction_StartPath first = new Instruction_StartPath(owner, pathOver, new Path_Polygon(pos1, false, speed1));
        setInstructions(first);
    }
}
