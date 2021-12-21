public class Enemy_Machine_Bullet extends Enemy {

    //change the HP of these later
    public Enemy_Machine_Bullet(Vector initPos, Vector polar){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Machine_Bullet(Vector initPos, Path path){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Machine_Bullet(Vector initPos, Behavior behavior){
        super(initPos, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Machine_Bullet(Vector initPos, Vector polar, Behavior behavior){
        super(initPos, polar, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Machine_Bullet(Vector initPos, Path path, Behavior behavior){
        super(initPos, path, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Machine_Bullet(Vector initPos, Vector polar, double initHP){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }

    public Enemy_Machine_Bullet(Vector initPos, Path path, double initHP){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }
    public Enemy_Machine_Bullet(Vector initPos, Behavior behavior, double initHP){
        super(initPos, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }

    public Enemy_Machine_Bullet(Vector initPos, Vector polar, Behavior behavior, double initHP){
        super(initPos, polar, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }

    public Enemy_Machine_Bullet(Vector initPos, Path path, Behavior behavior, double initHP){
        super(initPos, path, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }




    public Enemy_Machine_Bullet(Vector initPos, Vector polar, String attackName){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.attackName = attackName;
    }

    public Enemy_Machine_Bullet(Vector initPos, Path path, String attackName){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.attackName = attackName;
    }


    public Enemy_Machine_Bullet(Vector initPos, Vector polar, String attackName, double initHP){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
        this.attackName = attackName;
    }

    public Enemy_Machine_Bullet(Vector initPos, Path path, String attackName, double initHP){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
        this.attackName = attackName;
    }


    //add code here later
    @Override
    protected void spawnAttack(){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
        switch(attackName){
            case "default":
                attackDefault(difficulty);
                break;
            case "shotgun":
                attackShotgun(difficulty);
                break;
        }
    }

    private void attackDefault(int difficulty){
        double baseSpeed = 300;
        boolean reverse = false;
        int spawnNum = 1;
        double anglediff = 10;
        double baseAngle = 0;
        int d = difficulty - 1;
        for(int i = -d; i <= d; i++){
            double speed = baseSpeed + (30 * i);
            double initAngle;
            if(spawnNum % 2 == 1){
                initAngle = baseAngle - (anglediff * (spawnNum/2));
            }
            else{
                initAngle = baseAngle - ((anglediff/2) + (anglediff * ((spawnNum - 1)/2)));
            }
            for(int j = 0; j < spawnNum; j++){
                toSpawn.add(new EP_Orb_Small(this, new Vector(initAngle + (anglediff * j), speed), "grey"));
            }

            if(i == 0){
                reverse = true;
            }
            if(!reverse){
                spawnNum++;
            }
            else{
                spawnNum--;
            }
        }
    }

    private void attackShotgun(int difficulty){
        double baseSpeed = 200;
        for(int i = 0; i < difficulty; i++){
            for(int j = -2; j <= 2; j++){
                toSpawn.add(new EP_Orb_Small(Vector.add(this, new Vector(j * 50, 0)), new Vector(0, baseSpeed + (i * 50)), "grey"));
            }
        }
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
        Vector offset = new Vector(0, -13);
//        this.sprite = new SpriteInfo("E_machine_bullet1", offset, rotation, size);
//        this.sprite.setNext(new SpriteInfo("E_machine_bullet2", offset, rotation, size,
//                new SpriteInfo("E_machine_laser", offset, rotation, size,
//                        new SpriteInfo("E_machine_shield", offset, rotation, size,
//                                new SpriteInfo("E_machine_spray", offset, rotation, size, this.sprite)))));
        this.sprite = new SpriteInfo("E_machine_bullet1", offset, rotation, size);
        //later: change it so that it holds this sprite for a timer after each attack
        this.sprite.setNext(new SpriteInfo("E_machine_bullet2", offset, rotation, size, this.sprite));
    }

    @Override
    protected void updateSprite(){
        //return new SpriteInfo("E_wisp1", new Vector(-1, 0), tick, 3);
        if(tick % 30 == 0){
            this.sprite = this.sprite.getNext();
        }
    }
}
