public class Bullet_Aba_Halo extends Projectile_Player {

    private Player_AbaddonB owner;
    public Bullet_Aba_Halo(Vector initPos, Player_AbaddonB owner, Vector aVelocity){
        super(initPos, 20);
        this.owner = owner;
        canBeDamaged = false;
        damage = ((double)20)/ Driver_Game.getUPS();
        newOrbitPath(new Path_Orbit(owner, aVelocity, Vector.getAngle(owner, this)));
        setTrueHitbox(new Hitbox_Circle(this));
        destroyOutsideBounds = false;
    }

    @Override
    public void update(){
        if(!owner.getExists()){
            this.exists = false;
        }
        super.update();
    }

    //the player will call this
    public void spawnProjectiles(Enemy target) {
        if (exists) {
            toSpawn.add(new Bullet_Aba_Halo_Homing(this, new Vector(Vector.getAngle(owner, this), 1800), target));
        }
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = this.size/50;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("PP_halo", offset, rotation, size);
    }
}
