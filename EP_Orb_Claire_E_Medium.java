public class EP_Orb_Claire_E_Medium extends EP_Orb_Medium {
    private EP_Orb_Claire_E_Large owner;

    public EP_Orb_Claire_E_Medium(EP_Orb_Claire_E_Large owner){
        super(Vector.add(owner, Vector.polarToVelocity(new Vector(Vector.getAngle(owner, Player.getThePlayer()), 20))), new Vector(), "red");
        this.owner = owner;
        attackDelay = 60;
        reloadDelay = 60;
        canAttack = true;
        continuousAttack = true;
        destroyOutsideBounds = false;
    }

    @Override
    protected void spawnAttack(){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
//        int d;
//        if(difficulty >= 7){
//            d = 30;
//        }
//        else{
//            d = 60;
//        }
//        if(attackTimer % d == 0){
        if(attackTimer % 60 == 0){
            double angle = Vector.getAngle(this, Player.getThePlayer());
            double speed = 200 + (20 * difficulty);
            toSpawn.add(new EP_Orb_Small(this, new Vector(angle, speed), "red"));
        }
    }

    @Override
    public boolean canUpdate(){
        if(!owner.getExists() || !Player.getThePlayer().getExists()){
            return true;
        }
        return owner.getHasUpdated() && Player.getThePlayer().getHasUpdated();
    }

    @Override
    public void update(){
        if(!owner.getExists()){
            this.exists = false;
            return;
        }
        super.update();
    }

    @Override
    protected void baseMovementBehavior(){
        Vector ownerNewPos = owner;
        double newAngle = Vector.getAngle(ownerNewPos, Player.getThePlayer());
        Vector newPos = Vector.add(ownerNewPos, Vector.polarToVelocity(new Vector(newAngle, 20)));
        polar = new Vector(Vector.getAngle(this, newPos), Vector.getDistance(this, newPos) * Driver_Game.getUPS());
        super.baseMovementBehavior();
    }
}