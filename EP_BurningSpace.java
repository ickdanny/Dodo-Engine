public class EP_BurningSpace extends Projectile_Enemy {

    protected int pos = 1;
    public EP_BurningSpace(Vector initPos){
        super(initPos, new Vector(), 50);
        setTrueHitbox(new Hitbox_Circle(this));
        initSprite();
        this.canBeDamaged = false;
        this.canDamage = false;
    }

    @Override
    public void update(){
        super.update();
        if(tick % 20 == 0){
            if(pos < 6) {
                pos++;
                initSprite();
            }
            else if(pos < 7){
                pos++;
                initSprite();
                this.canDamage = true;
            }
        }
        if(tick > 5*Driver_Game.getUPS())
            this.exists = false;
    }

    @Override
    protected void initSprite(){
        double rotation = tick;
        double size = (this.size * 1.2)/100;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("PE_burningSpace" + pos, offset, rotation, size);
    }

    @Override
    protected void updateSprite(){
        this.sprite.setRotation(tick);
    }
}
