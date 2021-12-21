import java.util.ArrayList;

public abstract class Projectile_Bomb extends Projectile {
    protected boolean hitOnce = true;
    protected ArrayList<Enemy> enemiesAlreadyHit = new ArrayList<>();

    public Projectile_Bomb(){
        super();
    }

    public Projectile_Bomb(Vector initPos){
        super(initPos);
    }

    public Projectile_Bomb(Vector initPos, Vector polar, double size){
        super(initPos, polar, size);
    }

    public boolean alreadyHit(Enemy e){
        return enemiesAlreadyHit.contains(e) && hitOnce;
    }

//    @Override
//    public void collides(Entity e){
//        if(e.getCanBeDamaged() && e instanceof Enemy && hitOnce)
//            enemiesAlreadyHit.add((Enemy) e);
//    }

    public void collided(Entity e){
        if(e.getCanBeDamaged() && e instanceof Enemy && hitOnce) {
            enemiesAlreadyHit.add((Enemy) e);
        }
    }
}