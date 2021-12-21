public class EP_Orb_Neo_CM extends EP_Orb_Large {

    private double angleChangePerTick = .5;
    public EP_Orb_Neo_CM(Vector initPos, Vector polar, String colorCode){
        super(initPos, polar, colorCode);
    }

    @Override
    protected void baseMovementBehavior(){
        polar.setA(polar.getA() + angleChangePerTick);
        angleChangePerTick *= .98;
        super.baseMovementBehavior();
    }
}