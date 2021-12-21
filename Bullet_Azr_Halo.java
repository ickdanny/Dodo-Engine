public class Bullet_Azr_Halo extends Projectile_Player {
    private Player owner;
    private double velocity;
    private boolean focused;
    public Bullet_Azr_Halo(Vector initPos, Player owner, Vector aVelocity){
        super(initPos, 20);
        this.velocity = aVelocity.getA();
        this.owner = owner;
        canBeDamaged = false;
        damage = ((double)20)/ Driver_Game.getUPS();
        newOrbitPath(new Path_Orbit(owner, aVelocity, Vector.getAngle(owner, this)));
        setTrueHitbox(new Hitbox_Circle(this));
        destroyOutsideBounds = false;
        this.focused = owner.focused;
    }

    @Override
    public void update(){
        if(!owner.getExists()){
            this.exists = false;
        }
        super.update();
    }

    //the player will call this
    public void spawnProjectiles() {
        if (exists) {
            toSpawn.add(new Bullet_Azr_Halo_Shot(this, new Vector(180, 3000)));
        }
    }

    public void stopMovement(){
        ((Path_Orbit)path).setVelocity(0);
        this.focused = true;
    }

    public void startMovement(){
        ((Path_Orbit)path).setVelocity(velocity);
        this.focused = false;
    }

    public boolean getFocused(){
        return this.focused;
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = this.size/50;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("PP_halo", offset, rotation, size);
    }
}
