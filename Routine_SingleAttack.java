public class Routine_SingleAttack extends Routine {

    //this is pretty awful
    public Routine_SingleAttack(Entity owner, Vector pos1, Vector pos2, double speed1, double speed2, String attackName, double attackDelay, double reloadDelay){
//        super(owner, new Instruction_StartPath(owner,
//                new Instruction_WaitPathOver(owner,
//                        new Instruction_Attack(owner,
//                                new Instruction_Timer(owner,
//                                        new Instruction_WaitAttackOver(owner,
//                                                new Instruction_StartPath(owner, null, new Path_Polygon(pos2, false, speed2)))
//                                        , 10),
//                                attackName, attackDelay, reloadDelay))
//                , new Path_Polygon(pos1, false, speed1)));
        super(owner, null);
        Instruction_StartPath exitPath = new Instruction_StartPath(owner, null, new Path_Polygon(pos2, false, speed2));
        Instruction_WaitAttackOver attackOver = new Instruction_WaitAttackOver(owner, exitPath);
        Instruction_Timer preTimer = new Instruction_Timer(owner, attackOver, 10);
        Instruction_Attack attack = new Instruction_Attack(owner, preTimer, attackName, attackDelay, reloadDelay);
        Instruction_WaitPathOver pathOver = new Instruction_WaitPathOver(owner, attack);
        Instruction_StartPath first = new Instruction_StartPath(owner, pathOver, new Path_Polygon(pos1, false, speed1));
        setInstructions(first);
    }

    public Routine_SingleAttack(Entity owner, Behavior next, Vector pos1, Vector pos2, double speed1, double speed2, String attackName, double attackDelay, double reloadDelay){

        super(owner, next);
        Instruction_StartPath exitPath = new Instruction_StartPath(owner, null, new Path_Polygon(pos2, false, speed2));
        Instruction_WaitAttackOver attackOver = new Instruction_WaitAttackOver(owner, exitPath);
        Instruction_Timer preTimer = new Instruction_Timer(owner, attackOver, 10);
        Instruction_Attack attack = new Instruction_Attack(owner, preTimer, attackName, attackDelay, reloadDelay);
        Instruction_WaitPathOver pathOver = new Instruction_WaitPathOver(owner, attack);
        Instruction_StartPath first = new Instruction_StartPath(owner, pathOver, new Path_Polygon(pos1, false, speed1));
        setInstructions(first);
    }
}
