public class EP_Orb_Lilith_LS extends EP_Orb_Small {

    private double speed;
    public EP_Orb_Lilith_LS(Vector initPos, Vector av, double speed){
        super(initPos, new Vector(), "red");
        newPath(new Path_Orbit(initPos, av, Math.random() * 360));
        this.speed = speed;
    }

    @Override
    public void baseMovementBehavior(){
        double c = ((Path_Orbit)this.path).getAVelocity().getB();
        ((Path_Orbit)this.path).setDistance(c + speed);
        super.baseMovementBehavior();
    }
}