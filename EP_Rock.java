public class EP_Rock extends Projectile_Enemy {

    public EP_Rock(Vector initPos, Vector polar){
        super(initPos, polar, 10);
        setTrueHitbox(new Hitbox_Circle(this));
        initSprite();
    }

    public EP_Rock(Vector initPos, Path path){
        super(initPos, path, 10);
        setTrueHitbox(new Hitbox_Circle(this));
        initSprite();
    }


    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = (this.size * 1.6)/50;
        Vector offset = new Vector(-5, -2);
        this.sprite = new SpriteInfo("PE_rock", offset, rotation, size);
    }

    @Override
    protected void updateSprite(){
        this.sprite.setRotation(-3 * tick);
    }
}
