//200 dmg total
public class Bomb_Aba_Wave extends Projectile_Bomb {

    public Bomb_Aba_Wave(Vector initPos){
        super(initPos);
        damage = 200;
        setTrueHitbox(new Hitbox_Circle(this));
    }

    @Override
    protected void baseMovementBehavior(){
        if(tick < 10) {
            size += 40;
        }
        else if(tick > 180) {
            size += 30;
        }
        if(size >= 1500) {
            exists = false;
        }
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = (this.size/500) * 1.05;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("B_waveBomb", offset, rotation, size);
    }

    @Override
    protected void updateSprite(){
        //game slows drastically when trying to draw such a large image, so i need to just cut my losses here
        this.sprite.setRotation(tick);
        if(this.size < 800)
            this.sprite.setSize((this.size/500) * 1.05);
        else
            this.sprite.setSize(0);
    }
}