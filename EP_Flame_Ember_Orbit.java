public class EP_Flame_Ember_Orbit extends EP_Flame {

    private double dplus;
//    public EP_Flame_Ember_Orbit(Enemy_Ember initPos){
public EP_Flame_Ember_Orbit(Vector initPos){
        super(initPos, new Vector());
        Path_Orbit path = new Path_Orbit(initPos, new Vector(-180 + (Math.random() * 360), 0), Math.random() * 360);
        newOrbitPath(path);
        dplus = 2 + (Math.random() * 2);

        Instruction_Timer t = new Instruction_Timer(this, 120 + (Math.random() * 120));
        Instruction_EndPath e = new Instruction_EndPath(this);
        t.setNext(e);
        setNewBehavior(t);
    }

    @Override
    protected void baseMovementBehavior(){
        if(followsPath) {
            ((Path_Orbit) path).setDistance(((Path_Orbit) path).getAVelocity().getB() + dplus);
            ((Path_Orbit)path).setVelocity(((Path_Orbit)path).getAVelocity().getA() * .99);
        }
        super.baseMovementBehavior();
    }
}
