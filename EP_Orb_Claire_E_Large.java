public class EP_Orb_Claire_E_Large extends EP_Orb_Large {
    private double angleC;
    public EP_Orb_Claire_E_Large(Vector initPos, Vector polar){
        super(initPos, polar, "blue");
        angleC = Math.random() * 20;
        angleC -= 10;
        toSpawn.add(new EP_Orb_Claire_E_Medium(this));
    }

    @Override
    protected void baseMovementBehavior(){
        polar.a += (angleC/Driver_Game.getUPS());
        super.baseMovementBehavior();
    }
}
