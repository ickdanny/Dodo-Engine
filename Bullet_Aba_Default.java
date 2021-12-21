//24 dps
public class Bullet_Aba_Default extends Projectile_Player {
    public Bullet_Aba_Default(Vector initPos, Vector polar){
        super(initPos, polar, 35);
        setTrueHitbox(new Hitbox_Circle(this));
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = this.size/100;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("PP_orb_black", offset, rotation, size);
    }
}
