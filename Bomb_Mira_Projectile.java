public class Bomb_Mira_Projectile extends Projectile_Bomb {

    private String spriteName;
    public Bomb_Mira_Projectile(Vector initPos, Vector polar, String spriteName){
        super(initPos, polar, 30);
        damage = .2;
        setTrueHitbox(new Hitbox_Circle(this));
        this.spriteName = spriteName;
        initSprite();
    }

    @Override
    protected void initSprite(){
        double rotation = -polar.getA() + 180;
        double size = this.size/50;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo(spriteName, offset, rotation, size);
    }
}
