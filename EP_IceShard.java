public class EP_IceShard extends Projectile_Enemy {

    public EP_IceShard(Vector initPos, Vector polar) {
        super(initPos, polar, 5);
        setTrueHitbox(new Hitbox_Circle(this));
        initSprite();
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = (this.size * 1.4)/35;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("PE_iceShard", offset, rotation, size);
    }

    @Override
    protected void updateSprite(){
        this.sprite.setRotation(-polar.getA() + 180);
    }
}