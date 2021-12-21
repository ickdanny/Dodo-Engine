//100 dmg total
public class Bomb_Aba_Field extends Projectile_Bomb {

    private Player_AbaddonB owner;
    public Bomb_Aba_Field(Player_AbaddonB initPos){
        super(initPos);
        owner = initPos;
        damage = .3;
        setTrueHitbox(new Hitbox_Circle(this));
        hitOnce = false;
    }

    @Override
    public boolean canUpdate() {
        return super.canUpdate() && owner.getHasUpdated();
    }

    @Override
    protected void move(){
        setPolarVector(owner.getPolarVector());
        super.move();
    }

    @Override
    protected void baseMovementBehavior(){
        super.baseMovementBehavior();
        if(tick < 50) {
            size += 10;
        }
        else if(tick > 290){
            size -= 10;
            if(size < 100 && size > 89){
                for(Bullet_Aba_Halo p : owner.getHalo()){
                    ((Path_Orbit)p.getPath()).setDistance(100);
                }
            }
            else if(size <= 0){
                this.exists = false;
            }
        }
        if(size > 100){
            for(Bullet_Aba_Halo p : owner.getHalo()){
                ((Path_Orbit)p.getPath()).setDistance(size);
            }
        }
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = (this.size/500) * 1.04;
        Vector offset = new Vector(0, -1);
        this.sprite = new SpriteInfo("B_fieldBomb", offset, rotation, size);
    }

    @Override
    protected void updateSprite(){
        this.sprite.setRotation(Vector.getAngle(owner, owner.getHalo()[0]));
        this.sprite.setSize((this.size/500) * 1.04);
    }
}
