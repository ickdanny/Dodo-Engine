public class EP_Sword extends Projectile_Enemy {

    public EP_Sword(Vector initPos){
        super(initPos, new Vector(0, 1), 40);
        setTrueHitbox(new Hitbox_AABB(.35, .5, .35, .5, this));
        initSprite();
    }

    @Override
    public void update(){
        if(this.polar.getB() < 1000)
            this.polar.setB(this.polar.getB() * 1.1);
        super.update();
//        System.out.println(polar);
    }

    @Override
    public void initSprite(){
        double rotation = 180;
        double size = (this.size * 1.3)/50;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("PE_sword", offset, rotation, size);
    }
}
