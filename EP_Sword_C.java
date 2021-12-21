public class EP_Sword_C extends EP_Sword{
    private int t;
    private double speed;

    public EP_Sword_C(Vector initPos, Path_Orbit path, double speed, int t){
        super(initPos);
        newPath(path);
        this.t = t;
        this.speed = speed;
        destroyOutsideBounds = false;
        attackDelay = 1;
        reloadDelay = 6;
        canAttack = true;
        continuousAttack = true;
    }

    @Override
    public void update(){
//        if(tick == (int)((double)t/2)){
//
//        }
        if(tick == t){
            followsPath = false;
            destroyOutsideBounds = true;
            polar = new Vector(0, speed);
            canAttack = false;
            continuousAttack = false;
        }
        super.update();
    }

    @Override
    public void spawnAttack(){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
        double speed = 300 + (Math.random() * difficulty * 30);

        toSpawn.add(new EP_Oval(this, new Vector(Math.random() * 360, speed), "white"));
    }
}
