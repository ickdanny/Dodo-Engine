public class EP_Orb_Medium extends Projectile_Enemy {
    protected String colorCode;
    public EP_Orb_Medium(Vector initPos, Vector polar){
        super(initPos, polar, 15);
        setTrueHitbox(new Hitbox_Circle(this));
        initSprite();
    }
    public EP_Orb_Medium(Vector initPos, Vector polar, String colorCode){
        this(initPos, polar);
        this.colorCode = colorCode;
        initSprite();
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = (this.size * 1.3)/50;
        Vector offset = new Vector(-3, 0);
        this.sprite = new SpriteInfo("PE_medium_" + colorCode, offset, rotation, size);
    }

    protected void changeColor(String newColor){
        this.colorCode = newColor;
        initSprite();
    }
}