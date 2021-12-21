public abstract class Projectile_Player extends Projectile {

    public Projectile_Player(Vector initPos, double size){
        super(initPos, size);
    }

    public Projectile_Player(Vector initPos, Vector polar, double size){
        super(initPos, polar, size);
    }

    public Projectile_Player(Vector initPos, Behavior behavior, double size){
        super(initPos, size);
        followsBehavior = true;
        this.behavior = behavior;
    }

    public Projectile_Player(Vector initPos, Vector polar, Behavior behavior, double size){
        super(initPos, polar, size);
        followsBehavior = true;
        this.behavior = behavior;
    }

    @Override
    public void collides(Entity e){
        if(canBeDamaged && e.getCanDamage()){
            if(e instanceof Enemy){
                updateHP(e.getDamage());
            }
        }
    }
}