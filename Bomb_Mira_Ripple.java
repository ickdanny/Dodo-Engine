//252 dmg total
public class Bomb_Mira_Ripple extends Projectile_Bomb {

    public Bomb_Mira_Ripple(Vector initPos){
        super(initPos);
        damage = 10;
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Bomb_Mira_Ripple(Vector initPos, double damage){
        super(initPos);
        this.damage = damage;
        setTrueHitbox(new Hitbox_Circle(this));
    }

    @Override
    public void update(){
        if(tick == 30 && this.damage <= 30){
            toSpawn.add(new Bomb_Mira_Ripple(Player.getThePlayer(), damage + 2));
        }
        super.update();
    }

    @Override
    protected void baseMovementBehavior(){
        size += 20;
        if(size >= 1500) {
            exists = false;
        }
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = (this.size/500);
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("B_rippleBomb", offset, rotation, size);
    }

    @Override
    protected void updateSprite(){
        //game slows drastically when trying to draw such a large image, so i need to just cut my losses here
        if(this.size < 800)
            this.sprite.setSize((this.size/500));
        else
            this.sprite.setSize(0);
    }
}