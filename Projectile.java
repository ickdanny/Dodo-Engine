public abstract class Projectile extends Entity {

    //things that can go out and back in like, maybe homing things, should set this to false in the constructor
    protected boolean destroyOutsideBounds = true;

    public Projectile(){
        canDamage = true;
        canBeDamaged = true;
        damage = 1;
    }

    //some projectiles start off size = 0 so the following two constructors will be useful
    public Projectile(Vector initPos){
        super(initPos);
        canDamage = true;
        canBeDamaged = true;
        damage = 1;
    }

    public Projectile(Vector initPos, Vector polar){
        super(initPos, polar);
        canDamage = true;
        canBeDamaged = true;
        damage = 1;
    }

    public Projectile(Vector initPos, double size){
        super(initPos, size);
        canDamage = true;
        canBeDamaged = true;
        damage = 1;
    }

    //this is the constructor most will use
    public Projectile(Vector initPos, Vector polar, double size){
        super(initPos, polar, size);
        canDamage = true;
        canBeDamaged = true;
        damage = 1;
    }

    @Override
    protected void baseMovementBehavior(){
        super.baseMovementBehavior();
        if(destroyOutsideBounds){
            if(a < 0 - (size * 2)|| b < 0 - (size * 2) || a > Driver_Game.getWidth() + (size * 2) || b > Driver_Game.getHeight() + (size * 2)) {
                this.exists = false;
                deathRoutine();
            }
        }
        else{
            //i dunno if this is big enough but lets just go w/ 1000
            int max = 1000;
            if(a < 0 - max || b < 0 - max || a > Driver_Game.getWidth() + max || b > Driver_Game.getHeight() + max) {
                this.exists = false;
                deathRoutine();
            }
        }
    }
}