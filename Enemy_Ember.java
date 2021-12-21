public class Enemy_Ember extends Enemy{

    //change the HP of these later
    public Enemy_Ember(Vector initPos, Vector polar){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
//        setAttackTimes();
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Ember(Vector initPos, Path path){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
//        setAttackTimes();
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Ember(Vector initPos, Vector polar, String attackName){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
//        attackDelay = 1;
//        reloadDelay = 200;
        setAttackTimes(attackName);
        this.attackName = attackName;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Ember(Vector initPos, Path path, String attackName){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
//        attackDelay = 1;
//        reloadDelay = 200;
        setAttackTimes(attackName);
        this.attackName = attackName;
        setTrueHitbox(new Hitbox_Circle(this));
    }


    public Enemy_Ember(Vector initPos, Behavior behavior){
        super(initPos, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Ember(Vector initPos, Vector polar, Behavior behavior){
        super(initPos, polar, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Ember(Vector initPos, Path path, Behavior behavior){
        super(initPos, path, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Ember(Vector initPos, Vector polar, double initHP){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
//        setAttackTimes();
        this.hp = initHP;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Ember(Vector initPos, Path path, double initHP){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
//        setAttackTimes();
        this.hp = initHP;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Ember(Vector initPos, Vector polar, String attackName, double initHP){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
//        attackDelay = 1;
//        reloadDelay = 200;
        setAttackTimes(attackName);
        this.attackName = attackName;
        this.hp = initHP;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Ember(Vector initPos, Path path, String attackName, double initHP){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
//        attackDelay = 1;
//        reloadDelay = 200;
        setAttackTimes(attackName);
        this.attackName = attackName;
        this.hp = initHP;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Ember(Vector initPos, Behavior behavior, double initHP){
        super(initPos, behavior, 25);
        this.hp = initHP;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Ember(Vector initPos, Vector polar, Behavior behavior, double initHP){
        super(initPos, polar, behavior, 25);
        this.hp = initHP;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Ember(Vector initPos, Path path, Behavior behavior, double initHP){
        super(initPos, path, behavior, 25);
        this.hp = initHP;
        setTrueHitbox(new Hitbox_Circle(this));
    }

    //add code here later //later has arrived
    @Override
    protected void spawnAttack(){
        switch(attackName){
            case "default":
                attackDefault();
                break;
            case "volcano":
                attackVolcano();
                break;
            case "fireStorm":
                attackFireStorm();
                break;
            case "fireOrbit":
                attackFireOrbit();
                break;
            case "laserL":
                attackLaser(true);
                break;
            case "laserR":
                attackLaser(false);
                break;
            case "randomShot":
                attackRandomShot();
                break;
            case "directionalFirestorm":
                attackDirectionalFirestorm();
                break;
            case "convectionShot":
                attackConvectionShot();
                break;
        }
    }

    //1
    private void attackDefault(){
        for(int i = 0; i < 20; i++){
            double angle = (Math.random() * 180) - 90;
            double speed = 100 + (Math.random() * 250);
            toSpawn.add(new EP_Orb_Ember_Gravity(this, new Vector(angle, speed)));
        }
    }
    //1
    private void attackVolcano(){
        for(int i = 0; i < 20; i++){
            double angle = (Math.random() * 180) + 90;
            double speed = 100 + (Math.random() * 150);
            toSpawn.add(new EP_Orb_Ember_Gravity(this, new Vector(angle, speed)));
        }
    }
    //1
    private void attackFireStorm(){
        for(int i = -2; i <= 2; i++){
            Vector spawnPos = Vector.add(this, new Vector(i * 60, 30));
            toSpawn.add(new EP_Flame_Acceleration(spawnPos, new Vector(0, 500)));
        }
    }

    //300 300
    private void attackFireOrbit(){
        if(attackTimer % 10 == 0){
            toSpawn.add(new EP_Flame_Ember_Orbit(this));
        }
    }

    //1
    private void attackLaser(boolean left){
        toSpawn.add(new EP_Laser_Ember(this, left));
    }

    //300 300
    private void attackRandomShot(){
        if(attackTimer % 10 == 0) {
            double initAngle = attackTimer * ((double) 6 / 5);
            double sdist = 50;
            for (int i = 0; i < 5; i++) {
                double sangle = initAngle + (i * 360 / 5);
                Vector spawnPos = Vector.add(this, Vector.polarToVelocity(new Vector(sangle, sdist)));
                Vector polar = new Vector(Math.random() * 360, 200 + (Math.random() * 200));
                toSpawn.add(new EP_Orb_Small(spawnPos, polar, "red"));
            }
        }
    }

    //60 60
    private void attackDirectionalFirestorm(){
        if(attackTimer % 10 == 0) {
            double angle = Vector.getAngle(this, Player.getThePlayer());
            Vector spolar = new Vector(Math.random() * 360, 1 + (Math.random() * 70));
            Vector spawnPos = Vector.add(this, Vector.polarToVelocity(spolar));
            Vector polar = new Vector(angle, 500);
            toSpawn.add(new EP_Flame_Acceleration(spawnPos, polar));
        }
    }

    //whatever time u need
    private void attackConvectionShot(){
        if(attackTimer % 10 == 0){
            boolean left = Math.random() < .5;

            double avelocity = 180 + (Math.random() * 180);
            double sec = (double)360/avelocity;
            int ticks = (int)(sec*60);

            Vector rotationC;
            if(left){
                rotationC = new Vector(-100, 0);
                avelocity = 0 - avelocity;
            }
            else{
                rotationC = new Vector(100, 0);
            }
            rotationC = Vector.add(this, rotationC);
            double dist = 100 + ((Math.random() * 10) - 5);

//            EP_Orb_Medium add = new EP_Orb_Medium(this, new Vector(), "orange");
            EP_Flame add = new EP_Flame(this, new Vector());
            add.newPath(new Path_Orbit(rotationC, new Vector(avelocity, dist), Vector.getAngle(rotationC, this)));

            Instruction_Timer t = new Instruction_Timer(add, ticks);
            Instruction_EndPath e = new Instruction_EndPath(add);
            t.setNext(e);

            add.setNewBehavior(t);
            add.destroyOutsideBounds = false;
            toSpawn.add(add);
        }
    }

    @Override
    protected void deathRoutine(){
        toSpawn.add(new Pickup_PowerUpLarge(this));
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 1.5;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("E_ember", offset, rotation, size);
    }

    @Override
    protected void updateSprite(){
        this.sprite.setRotation(this.tick);
    }

    private void setAttackTimes(String a){
        int at;
        int rt;
        switch(a){
            case "default":
                at = 1;
                rt = 200;
                break;
            case "volcano":
                at = 1;
                rt = 200;
                break;
            case "fireStorm":
                at = 1;
                rt = 200;
                break;
            case "fireOrbit":
                at = 300;
                rt = 300;
                break;
            case "laserL":
                at = 1;
                rt = 300;
                break;
            case "laserR":
                at = 1;
                rt = 300;
                break;
            case "randomShot":
                at = 300;
                rt = 300;
                break;
            case "directionalFirestorm":
                at = 60;
                rt = 60;
                break;
            case "convectionShot":
                at = 300;
                rt = 300;
                break;
            default:
                at = 1;
                rt = 200;
        }
        attackDelay = at;
        reloadDelay = rt;
    }
}
