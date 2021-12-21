public class Bullet_Azr_Halo_Shot extends Projectile_Player {

    public Bullet_Azr_Halo_Shot(Vector initPos, Vector polar){
        super(initPos, polar, 20);
        setTrueHitbox(new Hitbox_Circle(this));
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = this.size/50;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("PP_orb_yellow", offset, rotation, size);
    }
}
