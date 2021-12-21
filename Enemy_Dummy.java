public class Enemy_Dummy extends Enemy {

    protected double nextPowerUpSpawnPoint;
    protected int dmgInstances = 1;
    public Enemy_Dummy(Vector initPos){
        super(initPos, 30);
        hp = 1000000;
        setTrueHitbox(new Hitbox_Circle(this));
        nextPowerUpSpawnPoint = hp - 5;
    }

    @Override
    public void updateHP(double damage){
        super.updateHP(damage);
        dmgInstances++;
        if(hp < nextPowerUpSpawnPoint){
            nextPowerUpSpawnPoint -= 5;
            toSpawn.add(new Pickup_PowerUpSmall(this));
        }
    }
}