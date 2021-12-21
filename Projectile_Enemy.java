public abstract class Projectile_Enemy extends Projectile {

    public Projectile_Enemy(Vector initPos, double size){
        super(initPos, size);
    }

    public Projectile_Enemy(Vector initPos, Vector polar, double size){
        super(initPos, polar, size);
    }

    public Projectile_Enemy(Vector initPos, Path path, double size){
        super(initPos, size);
        followsPath = true;
        this.path = path;
    }

    public Projectile_Enemy(Vector initPos, Behavior behavior, double size){
        super(initPos, size);
        followsBehavior = true;
        this.behavior = behavior;
    }

    public Projectile_Enemy(Vector initPos, Vector polar, Behavior behavior, double size){
        super(initPos, polar, size);
        followsBehavior = true;
        this.behavior = behavior;
    }

    public Projectile_Enemy(Vector initPos, Path path, Behavior behavior, double size){
        super(initPos, size);
        followsPath = true;
        followsBehavior = true;
        this.path = path;
        this.behavior = behavior;
    }

    @Override
    public void collides(Entity e){
        if(canBeDamaged && e.getCanDamage()) {
            if (e instanceof Player) {
                exists = false;
            }
            else if(e instanceof Projectile_Bomb){
                updateHP(e.getDamage());
                //need to add pickup spawning here
            }
        }
    }
}