public class Enemy_Spider extends Enemy {

    //change the HP of these later
    public Enemy_Spider(Vector initPos, Vector polar){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Spider(Vector initPos, Path path){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Spider(Vector initPos, Vector polar, String attackName){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        this.attackName = attackName;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Spider(Vector initPos, Path path, String attackName){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        this.attackName = attackName;
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Spider(Vector initPos, Behavior behavior){
        super(initPos, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Spider(Vector initPos, Vector polar, Behavior behavior){
        super(initPos, polar, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Spider(Vector initPos, Path path, Behavior behavior){
        super(initPos, path, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Spider(Vector initPos, Vector polar, double initHP){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        this.hp = initHP;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Spider(Vector initPos, Path path, double initHP){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        this.hp = initHP;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Spider(Vector initPos, Vector polar, String attackName, double initHP){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        this.attackName = attackName;
        this.hp = initHP;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Spider(Vector initPos, Path path, String attackName, double initHP){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        this.attackName = attackName;
        this.hp = initHP;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Spider(Vector initPos, Behavior behavior, double initHP){
        super(initPos, behavior, 25);
        this.hp = initHP;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Spider(Vector initPos, Vector polar, Behavior behavior, double initHP){
        super(initPos, polar, behavior, 25);
        this.hp = initHP;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Spider(Vector initPos, Path path, Behavior behavior, double initHP){
        super(initPos, path, behavior, 25);
        this.hp = initHP;
        setTrueHitbox(new Hitbox_Circle(this));
    }

                        //add code here later //later has arrived
    @Override
    protected void spawnAttack(){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
        switch(attackName){
            case "default":
                attackDefault(difficulty);
                break;
            case "solid":
                attackSolid(difficulty);
                break;
            case "homingShot":
                attackHomingShot(difficulty);
                break;
            case "sensingShot":
                attackSensingShot(difficulty);
                break;
            case "homingBullets":
                attackHomingBullets(difficulty);
                break;
        }
    }

    private void attackDefault(int difficulty){
        double angle = Vector.getAngle(this, Player.getThePlayer());

        switch(difficulty){
            case 1:
                toSpawn.add(new EP_Orb_Wiggle(this, new Vector(angle, 250)));
                break;
            case 2:
                toSpawn.add(new EP_Orb_Wiggle(this, new Vector(angle, 300)));
                break;
            case 3:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Wiggle(this, new Vector((i * 10) + angle, 325)));
                }
                break;
            case 4:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Wiggle(this, new Vector((i * 10) + angle, 350)));
                }
                break;
            case 5:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Wiggle(this, new Vector((i * 10) + angle, 400)));
                }
                break;
            case 6:
                for(int i = -2; i < 3; i++){
                    toSpawn.add(new EP_Orb_Wiggle(this, new Vector((i * 8) + angle, 425)));
                }
                break;
            case 7:
                for(int i = -2; i < 3; i++){
                    toSpawn.add(new EP_Orb_Wiggle(this, new Vector((i * 8) + angle, 450)));
                }
                break;
            case 8:
                for(int i = -3; i < 4; i++){
                    toSpawn.add(new EP_Orb_Wiggle(this, new Vector((i * 7) + angle, 500)));
                }
                break;
        }
    }

    //i have no idea why it looks wobbly. Should be straight but ehhh whatever
    private void attackSolid(int difficulty){
        double angle = Vector.getAngle(this, Player.getThePlayer());
        for(int i = 0; i <= difficulty/2; i++){
            toSpawn.add(new EP_Laser_Spider(this, new Vector(angle, 200 + (50 * i) + (difficulty * 50)), 90));
            toSpawn.add(new EP_Laser_Spider(this, new Vector(angle, 200 + (50 * i) + (difficulty * 50)), -90));
        }
    }

    private void attackHomingShot(int difficulty){
        double distance = 50;
//        double angle = Vector.getAngle(this, Player.getThePlayer());
        toSpawn.add(new EP_Orb_Medium(Vector.add(this, new Vector(0, distance)),
                new Vector(Vector.getAngle(Vector.add(this, new Vector(0, distance)), Player.getThePlayer()),
                        500 + (difficulty * 25)), "violet"));
        //extra bullets per side = difficulty
        //on difficulty 1 90/1 = 90
        double angleDiff = (double)90/(difficulty/2);
        double currentAngleOffset = 0;
        for(int i = 0; i < difficulty/2; i++){
            currentAngleOffset += angleDiff;
            toSpawn.add(new EP_Orb_Small(Vector.add(this, Vector.polarToVelocity(new Vector(currentAngleOffset, distance))),
                    new Vector(Vector.getAngle(Vector.add(this, Vector.polarToVelocity(new Vector(currentAngleOffset, distance))), Player.getThePlayer()),
                            500 + (difficulty * 25)), "violet"));
            toSpawn.add(new EP_Orb_Small(Vector.add(this, Vector.polarToVelocity(new Vector(-currentAngleOffset, distance))),
                    new Vector(Vector.getAngle(Vector.add(this, Vector.polarToVelocity(new Vector(-currentAngleOffset, distance))), Player.getThePlayer()),
                            500 + (difficulty * 25)), "violet"));
        }
    }

    //blue
    private void attackSensingShot(int difficulty){
        double angle = Vector.getAngle(this, Player.getThePlayer());
        toSpawn.add(new EP_Orb_Spider_Sensing(this, new Vector(angle, 320 + (difficulty * 50)), 300, 3));
        toSpawn.add(new EP_Orb_Spider_Sensing(this, new Vector(angle + 10, 300 + (difficulty * 50))));
        toSpawn.add(new EP_Orb_Spider_Sensing(this, new Vector(angle - 10, 300 + (difficulty * 50))));
    }

    //red
    private void attackHomingBullets(int difficulty){
        double speed = 200 + (difficulty * 50);
        double distance = 100;
        toSpawn.add(new EP_Orb_Spider_Homing(Vector.add(this, new Vector(0, distance)), new Vector(0, speed)));

        double angleDiff = (double)90/(difficulty/2);
        double currentAngleOffset = 0;
        for(int i = 0; i < difficulty/2; i++){
            currentAngleOffset += angleDiff;
            toSpawn.add(new EP_Orb_Spider_Homing(Vector.add(this, Vector.polarToVelocity(new Vector(currentAngleOffset, distance))),
                    new Vector(0, speed)));
            toSpawn.add(new EP_Orb_Spider_Homing(Vector.add(this, Vector.polarToVelocity(new Vector(-currentAngleOffset, distance))),
                    new Vector(0, speed)));
        }
    }




    @Override
    protected void deathRoutine(){
        toSpawn.add(new Pickup_PowerUpSmall(this));
    }

    //just play it loop instead of on attack (for some strange reason)
    //spider shoots attack the same turn it spawns in. It's outrageous. It's unfair.
    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 2;
        Vector offset = new Vector(0, -5);
        this.sprite = new SpriteInfo("E_spider5", offset, rotation, size);
        this.sprite.setNext(new SpriteInfo("E_spider1", offset, rotation, size,
                new SpriteInfo("E_spider2", offset, rotation, size,
                        new SpriteInfo("E_spider3", offset, rotation, size,
                                new SpriteInfo("E_spider4", offset, rotation, size, this.sprite)))));
    }

    @Override
    protected void updateSprite(){
        if(tick % 40 == 0){
            this.sprite = this.sprite.getNext();
        }
    }
}
