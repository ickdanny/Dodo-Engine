public class EP_IceShard_TimeRelation extends EP_IceShard {

    public EP_IceShard_TimeRelation(Vector initPos, Vector polar){
        super(initPos, polar);
    }

    @Override
    protected void baseMovementBehavior(){
        super.baseMovementBehavior();
        Vector pv = Player.getThePlayer().getVelocityVector();
        double playerSpeed = Math.sqrt(Math.pow(pv.getA(), 2) + Math.pow(pv.getB(), 2));
        double multiplier = 1 + (playerSpeed/500);
        velocity = Vector.scalarMultiple(velocity, multiplier);
    }
}