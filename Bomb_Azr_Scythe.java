//this does 100 damage total appx
public class Bomb_Azr_Scythe extends Projectile_Bomb {
    private Player_AzraelA owner;



    private Bomb_Azr_Scythe_Laser laser;
    private double baseAngle;
    private double trueAngle;

    public Bomb_Azr_Scythe(Player_AzraelA owner, Vector offset){
        super(Vector.add(owner, offset));
        this.owner = owner;
        this.baseAngle = Vector.getAngle(this, owner);
//        System.out.println(baseAngle);
        this.trueAngle = baseAngle;
        damage = .15;
        setTrueHitbox(new Hitbox_Circle(this));
        hitOnce = false;
        size = 30;
        destroyOutsideBounds = false;
    }

    @Override
    public boolean canUpdate() {
        return super.canUpdate() && owner.getHasUpdated();
    }

    @Override
    protected void move(){
        setPolarVector(owner.getPolarVector());
        super.move();
    }

    @Override
    protected void baseMovementBehavior() {
        this.velocity = owner.getVelocityVector();

        if(tick >= 30 && tick < 60){
            spin();
        }
        else if(tick >= 90 && tick < 120){
            spin();
        }
        else if(tick >= 150 && tick < 180){
            spin();
        }
        else if(tick >= 210 && tick < 240){
            spin();
        }
        else{
            laser.setAngle(baseAngle);
            trueAngle = baseAngle;
        }

        if (tick > 300) {
            this.exists = false;
        }
    }

    private void spin(){
        trueAngle += 12;
        laser.setAngle(trueAngle);
    }

    public Bomb_Azr_Scythe_Laser getLaser() {
        return laser;
    }

    public void setLaser(Bomb_Azr_Scythe_Laser laser){
        this.laser = laser;
        this.laser.setAngle(baseAngle);
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = .5;
        Vector offset = new Vector(-13, 0);
        this.sprite = new SpriteInfo("B_bombScythe", offset, rotation, size);
    }

    @Override
    protected void updateSprite(){
        this.sprite.setRotation(-trueAngle + 180);
    }
}
