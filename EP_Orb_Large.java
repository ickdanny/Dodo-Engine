public class EP_Orb_Large extends Projectile_Enemy {
    protected String colorCode;
    public EP_Orb_Large(Vector initPos, Vector polar){
        super(initPos, polar, 50);
        setTrueHitbox(new Hitbox_Circle(this));
        initSprite();
    }
    public EP_Orb_Large(Vector initPos, Vector polar, String colorCode){
        this(initPos, polar);
        this.colorCode = colorCode;
        initSprite();
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = (this.size * 1.2)/100;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("PE_large_" + colorCode, offset, rotation, size);
    }

    @Override
    protected void updateSprite(){
        this.sprite.setRotation(-tick);
    }

    protected void changeColor(String newColor){
        this.colorCode = newColor;
        initSprite();
    }
}