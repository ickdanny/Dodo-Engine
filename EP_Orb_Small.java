public class EP_Orb_Small extends Projectile_Enemy {
    protected String colorCode;
    public EP_Orb_Small(Vector initPos, Vector polar){
        super(initPos, polar, 6);
        setTrueHitbox(new Hitbox_Circle(this));
        initSprite();
    }
    public EP_Orb_Small(Vector initPos, Vector polar, String colorCode){
        this(initPos, polar);
        this.colorCode = colorCode;
        initSprite();
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = (this.size * 1.3)/25;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("PE_small_" + colorCode, offset, rotation, size);
    }

    protected void changeColor(String newColor){
        this.colorCode = newColor;
        initSprite();
    }
}
