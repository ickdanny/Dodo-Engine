public class EP_Orb_Zephres_A extends EP_Orb_Small {

    private double fspeed;
    public EP_Orb_Zephres_A(Vector initPos, Vector polar){
        super(initPos, new Vector(polar.getA(), 0), "red");
        fspeed = polar.getB();
        polar.setB(0);
    }

    @Override
    protected void baseMovementBehavior(){
        if(tick <= 60){
            double ratio = (double)tick/60;
            double speed = fspeed * ratio;
            polar.setB(speed);
        }

        super.baseMovementBehavior();
    }
}
