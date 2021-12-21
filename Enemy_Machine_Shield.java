public class Enemy_Machine_Shield extends Enemy {

    //change the HP of these later
    public Enemy_Machine_Shield(Vector initPos, Vector polar){
        super(initPos, polar, 25);
//        canAttack = true;
//        continuousAttack = true;
//        attackDelay = 1;
//        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        initShield();
    }

    public Enemy_Machine_Shield(Vector initPos, Path path){
        super(initPos, path, 25);
//        canAttack = true;
//        continuousAttack = true;
//        attackDelay = 1;
//        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        initShield();
    }
    public Enemy_Machine_Shield(Vector initPos, Behavior behavior){
        super(initPos, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        initShield();
    }

    public Enemy_Machine_Shield(Vector initPos, Vector polar, Behavior behavior){
        super(initPos, polar, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        initShield();
    }

    public Enemy_Machine_Shield(Vector initPos, Path path, Behavior behavior){
        super(initPos, path, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        initShield();
    }




    public Enemy_Machine_Shield(Vector initPos, Vector polar, double initHP){
        super(initPos, polar, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        hp = initHP;
        initShield();
    }

    public Enemy_Machine_Shield(Vector initPos, Path path, double initHP){
        super(initPos, path, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        hp = initHP;
        initShield();
    }
    public Enemy_Machine_Shield(Vector initPos, Behavior behavior, double initHP){
        super(initPos, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        hp = initHP;
        initShield();
    }

    public Enemy_Machine_Shield(Vector initPos, Vector polar, Behavior behavior, double initHP){
        super(initPos, polar, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        hp = initHP;
        initShield();
    }

    public Enemy_Machine_Shield(Vector initPos, Path path, Behavior behavior, double initHP){
        super(initPos, path, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        hp = initHP;
        initShield();
    }

    //add code here later



    private void initShield(){
        toSpawn.add(new Enemy_Machine_Shield_Shield(Vector.add(this, new Vector(0, 30)), this));
    }



    @Override
    protected void deathRoutine(){
        toSpawn.add(new Pickup_PowerUpSmall(this));
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 1;
        Vector offset = new Vector(-1, -6);
        this.sprite = new SpriteInfo("E_machine_shield", offset, rotation, size);
    }
}
