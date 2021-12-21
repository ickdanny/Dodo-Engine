//25 dps per bullet
public class Bullet_Mira_Wave extends Projectile_Player {
    public Bullet_Mira_Wave(Vector initPos, Vector polar){
        super(initPos, polar, 35);
        setTrueHitbox(new Hitbox_Circle(this));
        this.damage = 5;
    }
    public Bullet_Mira_Wave(Vector initPos, Vector polar, boolean outside){
        super(initPos, polar, 35);
        setTrueHitbox(new Hitbox_Circle(this));
        this.damage = 5;
        this.destroyOutsideBounds = outside;
    }

    @Override
    protected void initSprite(){
        double rotation = -polar.getA() + 180;
        double size = 1;
        Vector offset = new Vector(0, 15);
        this.sprite = new SpriteInfo("PP_wave", offset, rotation, size);
    }
}