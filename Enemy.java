public abstract class Enemy extends Entity {
    //will i ever need this?
    private static boolean dropPower = true;
    public Enemy(){
        canDamage = true;
        canBeDamaged = true;
        damage = 1;
        attackName = "default";
    }

    public Enemy(double size){
        super(size);
        canDamage = true;
        canBeDamaged = true;
        damage = 1;
        attackName = "default";
    }

    public Enemy(Vector initPos, double size){
        super(initPos, size);
        canDamage = true;
        canBeDamaged = true;
        damage = 1;
        attackName = "default";
    }

    public Enemy(Vector initPos, Vector polar, double size){
        super(initPos, polar, size);
        canDamage = true;
        canBeDamaged = true;
        damage = 1;
        attackName = "default";
    }

    public Enemy(Vector initPos, Path path, double size){
        super(initPos, size);
        followsPath = true;
        this.path = path;
        canDamage = true;
        canBeDamaged = true;
        damage = 1;
        attackName = "default";
    }

    public Enemy(Vector initPos, Behavior behavior, double size){
        super(initPos, size);
        followsBehavior = true;
        this.behavior = behavior;
        canDamage = true;
        canBeDamaged = true;
        damage = 1;
        attackName = "default";
        setBehaviorOwnerToThis();
    }

    public Enemy(Vector initPos, Vector polar, Behavior behavior, double size){
        super(initPos, polar, size);
        followsBehavior = true;
        this.behavior = behavior;
        canDamage = true;
        canBeDamaged = true;
        damage = 1;
        attackName = "default";
        setBehaviorOwnerToThis();
    }

    public Enemy(Vector initPos, Path path, Behavior behavior, double size){
        super(initPos, size);
        followsPath = true;
        this.path = path;
        followsBehavior = true;
        this.behavior = behavior;
        canDamage = true;
        canBeDamaged = true;
        damage = 1;
        attackName = "default";
        setBehaviorOwnerToThis();
    }

    @Override
    protected void baseMovementBehavior(){
        super.baseMovementBehavior();
        if(a < 0 - (size * 2)|| b < 0 - (size * 2) || a > Driver_Game.getWidth() + (size * 2) || b > Driver_Game.getHeight() + (size * 2)) {
            this.exists = false;
        }
    }

    @Override
    public void collides(Entity e){
        if(canBeDamaged && e.getCanDamage()){
            if(e instanceof Projectile_Player) {
                this.updateHP(e.getDamage());
            }
            else if(e instanceof Projectile_Bomb) {
                if (!((Projectile_Bomb)e).alreadyHit(this)) {
                    this.updateHP(e.getDamage());
                    ((Projectile_Bomb)e).collided(this);
                }
            }
        }
    }

    protected static boolean willDropPower(){
        if(dropPower){
            dropPower = false;
            return true;
        }
        else{
            dropPower = true;
            return false;
        }
    }
}