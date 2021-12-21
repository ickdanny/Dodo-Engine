import java.awt.*;

public class Enemy_Machine_Laser extends Enemy {

    private static Vector left = new Vector(-15, 32);
    private static Vector center = new Vector(0, 32);
    private static Vector right = new Vector(15, 32);

    private int d = 0;


    //change the HP of these later
    public Enemy_Machine_Laser(Vector initPos, Vector polar){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Machine_Laser(Vector initPos, Path path){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Machine_Laser(Vector initPos, Behavior behavior){
        super(initPos, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Machine_Laser(Vector initPos, Vector polar, Behavior behavior){
        super(initPos, polar, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Machine_Laser(Vector initPos, Path path, Behavior behavior){
        super(initPos, path, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }




    public Enemy_Machine_Laser(Vector initPos, Vector polar, double initHP){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        hp = initHP;
    }

    public Enemy_Machine_Laser(Vector initPos, Path path, double initHP){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        hp = initHP;
    }
    public Enemy_Machine_Laser(Vector initPos, Behavior behavior, double initHP){
        super(initPos, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        hp = initHP;
    }

    public Enemy_Machine_Laser(Vector initPos, Vector polar, Behavior behavior, double initHP){
        super(initPos, polar, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        hp = initHP;
    }

    public Enemy_Machine_Laser(Vector initPos, Path path, Behavior behavior, double initHP){
        super(initPos, path, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        hp = initHP;
    }






    public Enemy_Machine_Laser(Vector initPos, Vector polar, String attackName){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        if(attackName.equals("sustain")) {
            attackDelay = 1;
        }
        else{
            attackDelay = 200;
        }
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.attackName = attackName;
    }

    public Enemy_Machine_Laser(Vector initPos, Path path, String attackName){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        if(attackName.equals("sustain")) {
            attackDelay = 1;
        }
        else{
            attackDelay = 200;
        }
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.attackName = attackName;
    }


    public Enemy_Machine_Laser(Vector initPos, Vector polar, String attackName, double initHP){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        if(attackName.equals("sustain")) {
            attackDelay = 1;
        }
        else{
            attackDelay = 200;
        }
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
        this.attackName = attackName;
    }

    public Enemy_Machine_Laser(Vector initPos, Path path, String attackName, double initHP){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        if(attackName.equals("sustain")) {
            attackDelay = 1;
        }
        else{
            attackDelay = 200;
        }
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
        this.attackName = attackName;
    }

    //add code here later
    @Override
    protected void spawnAttack(){
        switch(attackName){
            case "default":
                int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
                attackDefault(difficulty);
                break;
            //in this situation the laser class will handle the difficulty settings
            case "sustain":
                attackSustain();
                break;
        }
    }

    //attackDelay = 200
    private void attackDefault(int difficulty){
        if(attackTimer > 50 && attackTimer <= 150){
            if(attackTimer % (20 - difficulty) == 0){
                if(d == 0){
                    toSpawn.add(new EP_Laser(Vector.add(this, right), new Vector(0, 300 + (50 * difficulty)), 50, Color.red.brighter()));
                    d = 1;
                }
                else if(d == 1){
                    toSpawn.add(new EP_Laser(Vector.add(this, center), new Vector(0, 300 + (50 * difficulty)), 50, Color.red.brighter()));
                    d = 2;
                }
                else{
                    toSpawn.add(new EP_Laser(Vector.add(this, left), new Vector(0, 300 + (50 * difficulty)), 50, Color.red.brighter()));
                    d = 0;
                }
            }
        }
        else if(attackTimer == 170){
            toSpawn.add(new EP_Laser(Vector.add(this, left), new Vector(0, 300 + (50 * difficulty)), 50, Color.red.brighter()));
            toSpawn.add(new EP_Laser(Vector.add(this, center), new Vector(0, 300 + (50 * difficulty)), 50, Color.red.brighter()));
            toSpawn.add(new EP_Laser(Vector.add(this, right), new Vector(0, 300 + (50 * difficulty)), 50, Color.red.brighter()));
        }
        else if(attackTimer == 30){
            toSpawn.add(new EP_Laser(Vector.add(this, left), new Vector(0, 300 + (50 * difficulty)), 50, Color.red.brighter()));
            toSpawn.add(new EP_Laser(Vector.add(this, center), new Vector(0, 300 + (50 * difficulty)), 50, Color.red.brighter()));
            toSpawn.add(new EP_Laser(Vector.add(this, right), new Vector(0, 300 + (50 * difficulty)), 50, Color.red.brighter()));
        }
        else if(attackTimer == 1){
            d = 0;
        }
    }

    //attackDelay = 1
    private void attackSustain(){
        toSpawn.add(new EP_Laser_Machine(Vector.add(this, left), this));
        toSpawn.add(new EP_Laser_Machine(Vector.add(this, center), this));
        toSpawn.add(new EP_Laser_Machine(Vector.add(this, right), this));
    }




    @Override
    protected void deathRoutine(){
        if(Enemy.willDropPower()) {
            toSpawn.add(new Pickup_PowerUpSmall(this));
        }
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 1;
        Vector offset = new Vector(1, -4);
        this.sprite = new SpriteInfo("E_machine_laser", offset, rotation, size);
    }
}
