//200 damage total
public class Bomb_Azr_Scythe_Laser extends Projectile_Bomb {
    private Bomb_Azr_Scythe owner;
    public Bomb_Azr_Scythe_Laser(Bomb_Azr_Scythe owner){
        super(owner);
        this.owner = owner;
        damage = .13;
        setTrueHitbox(new Hitbox_Line(this));
        hitOnce = false;
        size = 1500;
        destroyOutsideBounds = false;
    }

    @Override
    public boolean canUpdate() {
        return super.canUpdate() && owner.getHasUpdated();
    }

    @Override
    public void update(){
        if(!owner.getExists()){
            this.exists = false;
        }
        super.update();
    }

    @Override
    protected void move(){
        setPolarVector(owner.getPolarVector());
        super.move();
    }

    @Override
    protected void baseMovementBehavior() {
        this.velocity = owner.getVelocityVector();
    }

    public void setAngle(double angle){
        ((Hitbox_Line) trueHitbox).angle =angle;
    }
}
