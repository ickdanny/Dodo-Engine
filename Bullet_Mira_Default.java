//30 dps
public class Bullet_Mira_Default extends Projectile_Player {
    public Bullet_Mira_Default(Vector initPos){
        super(initPos, new Vector(180, 3000), 10);
        setTrueHitbox(new Hitbox_AABB(1, 5, 1, 5, this));
        this.damage = (double)6/5;
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 1;
        Vector offset = new Vector(-1, 0);
        this.sprite = new SpriteInfo("PP_iceShard", offset, rotation, size);
    }
}
