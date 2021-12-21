public class EP_Orb_Wiggle extends EP_Orb_Small {

    private double baseAngle;
    public EP_Orb_Wiggle(Vector initPos, Vector initPolar){
        super(initPos, initPolar, "blue");
        baseAngle = initPolar.getA();
    }

    @Override
    protected void move(){
        double maxAngleDiff = 15;
        //makes the frequency 60 aka 1 set per second
        double m = Math.sin((tick * Math.PI)/30);
//        System.out.println(m);
        //offset of the angle
        double angleChange = m * maxAngleDiff;
        double thisAngle = baseAngle + angleChange;
//        System.out.println(thisAngle);
        polar.setA(thisAngle);
        super.move();
    }
}
