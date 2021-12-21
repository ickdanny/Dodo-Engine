public class Instruction_Zephres_BB extends Instruction_Timer {

    //1 = throwing tracer
    //2 = timer
    //3 = path
    private int phase = 1;
    private Vector target;
    public Instruction_Zephres_BB(Enemy_Boss_Zephres owner){
        super(owner, 60);
    }
    public Instruction_Zephres_BB(Enemy_Boss_Zephres owner, Behavior next){
        super(owner, next, 60);
    }

    @Override
    public boolean doAction(){
        if(phase == 1){
            target = new Vector(30 + (Math.random() * 790), 30 + (Math.random() * 940));
            ((Enemy_Boss_Zephres)owner).throwTracer(target);
            phase = 2;
        }
        if(phase == 2) {
            if (ticksRemaining <= 0) {
                ticksRemaining = baseTicks;
                phase = 3;
            }
            else {
                ticksRemaining--;
                return false;
            }
        }
        if(phase == 3){
            Path_Polygon path = new Path_Polygon(target, false, 700);
            owner.newPath(path);
            phase = 1;
            return true;
        }
        return false;
    }

    @Override
    public void reset(){
        ticksRemaining = baseTicks;
        phase = 1;
    }

}
