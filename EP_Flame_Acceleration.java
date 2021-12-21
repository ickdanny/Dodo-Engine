public class EP_Flame_Acceleration extends EP_Flame {

    private double finalSpeed;
    public EP_Flame_Acceleration(Vector initPos, Vector finalPolar){
        super(initPos, new Vector(finalPolar.getA(), 20));
        finalSpeed = finalPolar.getB();
    }

    @Override
    public void baseMovementBehavior(){
        if(this.polar.getB() < finalSpeed)
            this.polar.setB(this.polar.getB() * 1.025);
        super.baseMovementBehavior();
    }
}