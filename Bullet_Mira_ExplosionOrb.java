public class Bullet_Mira_ExplosionOrb extends Projectile_Player {
    public Bullet_Mira_ExplosionOrb(Vector initPos, Vector polar){
        super(initPos, polar, 8);
        setTrueHitbox(new Hitbox_Circle(this));
        this.damage = 1;
    }

    @Override
    public void update(){
        if(tick > 150) {
            this.exists = false;
            return;
        }
        super.update();
    }

    @Override
    protected void move(){
        polar.setB(polar.getB() * .93);
        super.move();
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = .15;
        Vector offset = new Vector(-1.2, 0);
        this.sprite = new SpriteInfo("PP_orb_white", offset, rotation, size);
    }
}
