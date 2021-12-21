public abstract class Pickup extends Entity {

    public Pickup(Vector initPos, double size){
        super(initPos, size);
        polar = new Vector(180, 600);
        velocity = Vector.polarToVelocity(polar);
        setTrueHitbox(new Hitbox_AABB(1, 1, 1, 1, this));
    }

    @Override
    protected void baseMovementBehavior(){
        if(polar.getB() > -400) {
            polar.setB(polar.getB() - (500 / Driver_Game.getUPS()));
        }
        else{
            polar.setB(-400);
        }
        super.baseMovementBehavior();
        if(a < 0 - (size * 2) || a > Driver_Game.getWidth() + (size * 2) || b > Driver_Game.getHeight() + (size * 2)) {
            this.exists = false;
        }
    }

    @Override
    public void collides(Entity e){
        if(e instanceof Player){
            exists = false;
        }
    }
}