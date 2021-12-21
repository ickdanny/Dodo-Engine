public class EP_Orb_Lilith_IF_Small extends EP_Orb_Small {

    private double fspeed;
    public EP_Orb_Lilith_IF_Small(Vector initPos, Vector polar){
        super(initPos, new Vector(polar.getA(), 0), "white");
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
