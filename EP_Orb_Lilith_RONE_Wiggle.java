public class EP_Orb_Lilith_RONE_Wiggle extends EP_Orb_Medium {
    private double baseAngle;
    private double freq;
    private double maxAngleDiff;

    public EP_Orb_Lilith_RONE_Wiggle(Vector initPos, Vector initPolar, double freq, double maxAngleDiff){
        super(initPos, initPolar, "blue");
        baseAngle = initPolar.getA();
        this.freq = freq;
        this.maxAngleDiff = maxAngleDiff;
    }

    //just copied
    @Override
    protected void move(){
        double m = Math.sin((tick * Math.PI)/(freq/2));

        double angleChange = m * maxAngleDiff;
        double thisAngle = baseAngle + angleChange;

        polar.setA(thisAngle);
        super.move();
    }

}
