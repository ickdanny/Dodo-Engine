public class EP_Orb_Neo_CT extends EP_Orb_Large{

    private boolean spinDirection;
    private double angleChangePerTick = .5;
    public EP_Orb_Neo_CT(Vector initPos, Vector polar){
        super(initPos, polar, "white");
        spinDirection = ((int)System.currentTimeMillis()) % 2 == 0;
    }

    @Override
    public void update(){
        if(tick % 14 - Driver_GameLogic.getCurrentLogicDriver().getDifficulty() == 0){
            double baseAngle = 0 - polar.getA();
            double angle = baseAngle + (25 -(Math.random() * 50));
            toSpawn.add(new EP_Orb_Small(this, new Vector(angle, 200 + (Math.random() * 100)), "white"));
        }
        super.update();
    }

    @Override
    protected void baseMovementBehavior(){
        if(spinDirection){
            polar.setA(polar.getA() + angleChangePerTick);
        }
        else{
            polar.setA(polar.getA() - angleChangePerTick);
        }
        angleChangePerTick *= .98;
        super.baseMovementBehavior();
    }
}