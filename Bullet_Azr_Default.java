//30 dps
public class Bullet_Azr_Default extends Projectile_Player {

    private double baseAdjustment = 4;
    //no good name; the variable that determines how much of the base adjustment is used
    private double x = 1;
    private double direction;
    public Bullet_Azr_Default(Vector initPos, Vector polar, int direction){
        super(initPos, polar, 35);
        setTrueHitbox(new Hitbox_Circle(this));
        this.direction = direction;
        destroyOutsideBounds = false;
    }

    @Override
    protected void move(){
        polar.setA(polar.getA() + (baseAdjustment * x * direction));
        x *= .95;
        super.move();
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = .5;
        Vector offset = new Vector(-13, 0);
        this.sprite = new SpriteInfo("PP_boomerangScythe", offset, rotation, size);
    }

    @Override
    protected void updateSprite(){
        this.sprite.setRotation(tick * 30);
    }


}
