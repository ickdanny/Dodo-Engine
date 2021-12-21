//this must be an enemy for player bullets to interact with it (EP would phase thru)
public class Enemy_Machine_Shield_Shield extends Enemy {

    private Enemy_Machine_Shield owner;
    private Vector ownerOffset;

    public Enemy_Machine_Shield_Shield(Vector initPos, Enemy_Machine_Shield owner){
        super(initPos, 5);
        setTrueHitbox(new Hitbox_AABB(13, 1, 13, 2, this));
        this.ownerOffset = Vector.add(initPos, Vector.scalarMultiple(owner, -1));
        this.owner = owner;
        this.canBeDamaged = false;
    }

    @Override
    public void update(){
        if(!owner.getExists()) {
            this.exists = false;
            return;
        }

        Vector toGo = Vector.add(owner, ownerOffset);
        polar = new Vector(Vector.getAngle(this, toGo), Vector.getDistance(this, toGo) * Driver_Game.getUPS());
        velocity = Vector.polarToVelocity(polar);
        super.update();
    }

    @Override
    protected void baseMovementBehavior(){
    }

    @Override
    public boolean canUpdate(){
        return owner.getHasUpdated();
    }


    @Override
    public void collides(Entity e){
        if(owner.getCanBeDamaged() && e.getCanDamage()){
            if(e instanceof Projectile_Player) {
                owner.updateHP(e.getDamage());
            }
        }
        else if(e instanceof Projectile_Bomb && e.getCanDamage()) {
            if (!((Projectile_Bomb)e).alreadyHit(this))
                owner.updateHP(e.getDamage());
        }
    }
}
