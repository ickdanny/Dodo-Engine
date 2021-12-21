//250 dmg total

//i have no idea why it spawns in the wrong position when player is moving
public class Bomb_Azr_BigLaser extends Projectile_Bomb {
    private Player_AzraelB owner;

    public Bomb_Azr_BigLaser(Player_AzraelB initPos) {
        super(Vector.add(initPos, new Vector(0, 80)));
        owner = initPos;
        damage = (double)5/6;
        setTrueHitbox(new Hitbox_AABB(100, 0, 100, 1300, this));
        hitOnce = false;
        size = 1;
        destroyOutsideBounds = false;
    }

    @Override
    public boolean canUpdate() {
        return super.canUpdate() && owner.getHasUpdated();
    }

//    @Override
//    public void update(){
//        Vector toGo = Vector.add(Player.getThePlayer(), new Vector(0, 80));
//        polar = new Vector(Vector.getAngle(this, toGo), Vector.getDistance(this, toGo) * Driver_Game.getUPS());
//        velocity = Vector.polarToVelocity(polar);
//        super.update();
//    }

//    @Override
//    protected void move() {
////        setPolarVector(owner.getPolarVector());
//////        this.velocity = owner.getVelocityVector();
////        super.move();
//    }

    //lives for 5 seconds
    @Override
    protected void baseMovementBehavior() {
//        this.velocity = owner.getVelocityVector();
        Vector toGo = Vector.add(Player.getThePlayer(), new Vector(0, 80));
        polar = new Vector(Vector.getAngle(this, toGo), Vector.getDistance(this, toGo) * Driver_Game.getUPS());
//        velocity = Vector.polarToVelocity(polar);
        super.baseMovementBehavior();
        if (tick > 300) {
            this.exists = false;
        }
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 1.4;
        Vector offset = new Vector(0, 650);
        this.sprite = new SpriteInfo("B_bigLaserBomb1", offset, rotation, size);
        this.sprite.setNext(new SpriteInfo("B_bigLaserBomb2", offset, rotation, size,
                new SpriteInfo("B_bigLaserBomb3", offset, rotation, size, this.sprite)));
    }

    @Override
    protected void updateSprite(){
        if(this.tick % 3 == 0){
            this.sprite = this.sprite.getNext();
        }
    }
}