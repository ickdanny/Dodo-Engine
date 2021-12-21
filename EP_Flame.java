public class EP_Flame extends Projectile_Enemy {

    public EP_Flame(Vector initPos, Vector polar){
        super(initPos, polar, 15);
        setTrueHitbox(new Hitbox_Circle(this));
        initSprite();
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = (this.size * 1.3)/45;
        Vector offset = new Vector(0, 12);
        this.sprite = new SpriteInfo("PE_flames1", offset, rotation, size);
        this.sprite.setNext(new SpriteInfo("PE_flames2", offset, rotation, size,
                new SpriteInfo("PE_flames3", offset, rotation, size,
                        new SpriteInfo("PE_flames4", offset, rotation, size, this.sprite))));
    }

    @Override
    protected void updateSprite(){
        if(tick % 10 == 0)
            this.sprite = this.sprite.getNext();
        this.sprite.setRotation(-polar.getA() /*+ 180*/);
    }
}