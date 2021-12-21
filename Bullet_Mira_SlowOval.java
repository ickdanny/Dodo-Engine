//25 dps + 10 explosion bullets
public class Bullet_Mira_SlowOval extends Projectile_Player {
    public Bullet_Mira_SlowOval(Vector initPos, double damage){
        super(initPos, new Vector(180, 300), 10);
        setTrueHitbox(new Hitbox_Circle(this));
        this.damage = damage;
    }

    @Override
    protected void move(){
        polar.setB(polar.getB() * 1.02);
        super.move();
    }

    @Override
    protected void deathRoutine(){
        for(int i = 0; i < this.damage * 2; i++){
            double angle = Math.random() * 360;
            toSpawn.add(new Bullet_Mira_ExplosionOrb(Vector.add(this, Vector.polarToVelocity(new Vector(angle, size *2))), new Vector(angle, 1000)));
        }
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = .3;
        Vector offset = new Vector(0, -1);
        this.sprite = new SpriteInfo("PP_oval_white", offset, rotation, size);
    }
}
