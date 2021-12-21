public class EP_Orb_Lilith_IF_Medium extends EP_Orb_Medium {

    private Vector targetPos;
    public EP_Orb_Lilith_IF_Medium(Vector initPos, Vector targetPos){
        super(initPos, new Vector(), "red");
        this.targetPos = targetPos;
        newPath(new Path_Polygon(targetPos, false, newSpeed()));
    }

    @Override
    public void update(){
        if(tick >= 300){
            this.exists = false;
            deathRoutine();
            return;
        }
        super.update();
    }

    @Override
    protected void baseMovementBehavior(){
        ((Path_Polygon)this.path).setSpeed(newSpeed());
        super.baseMovementBehavior();
    }

    private double newSpeed(){
        return 125 + (Vector.getDistance(this, targetPos) / 3);
    }

    @Override
    protected void deathRoutine(){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();

        for(int i = 0; i < difficulty - 2; i++){
            double angle = Math.random() * 360;
            double speed = 100 + (Math.random() * 100);
            Vector polar = new Vector(angle, speed);
            toSpawn.add(new EP_Orb_Lilith_IF_Small(this, polar));
        }
    }
}
