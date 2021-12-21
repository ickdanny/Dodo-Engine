//~18 dps
public class Bullet_Aba_Scythe extends Projectile_Player {
    public Bullet_Aba_Scythe(Vector initPos, Vector polar){
        super(initPos, polar, 54);
        this.damage = .7;
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
