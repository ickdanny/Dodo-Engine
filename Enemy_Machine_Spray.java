public class Enemy_Machine_Spray extends Enemy{


    //change the HP of these later
    public Enemy_Machine_Spray(Vector initPos, Vector polar){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Machine_Spray(Vector initPos, Path path){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
    }
    public Enemy_Machine_Spray(Vector initPos, Behavior behavior){
        super(initPos, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Machine_Spray(Vector initPos, Vector polar, Behavior behavior){
        super(initPos, polar, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Machine_Spray(Vector initPos, Path path, Behavior behavior){
        super(initPos, path, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }



    public Enemy_Machine_Spray(Vector initPos, Vector polar, double initHP){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }

    public Enemy_Machine_Spray(Vector initPos, Path path, double initHP){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 1;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }
    public Enemy_Machine_Spray(Vector initPos, Behavior behavior, double initHP){
        super(initPos, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }

    public Enemy_Machine_Spray(Vector initPos, Vector polar, Behavior behavior, double initHP){
        super(initPos, polar, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }

    public Enemy_Machine_Spray(Vector initPos, Path path, Behavior behavior, double initHP){
        super(initPos, path, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }


    //both non default attacks are continuing so we'll set the attackDelay to 200 whereas
    //the default attack is a single spread shot so the attackDelay there is 1
    public Enemy_Machine_Spray(Vector initPos, Vector polar, String attackName){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.attackName = attackName;
    }

    public Enemy_Machine_Spray(Vector initPos, Path path, String attackName){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.attackName = attackName;
    }


    public Enemy_Machine_Spray(Vector initPos, Vector polar, String attackName, double initHP){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
        this.attackName = attackName;
    }

    public Enemy_Machine_Spray(Vector initPos, Path path, String attackName, double initHP){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
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
            case "spray":
                attackSpray(difficulty);
                break;
            case "smart":
                attackSmart(difficulty);
                break;
        }
    }

    private void attackDefault(int difficulty){
        if(attackTimer != 1){
            return;
        }
        int bullets = (difficulty/2) + 1;

        for(int i = 0; i < 8; i++){
            double angle = i * ((double)360/8);
            for(int j = 0; j < bullets; j++){
                toSpawn.add(new EP_Orb_Medium(this, new Vector(angle, 300 + (j * 100)), "red"));
            }
        }
    }

    private void attackSpray(int difficulty){
        if(attackTimer > 50 && attackTimer <= 150){
            if(attackTimer % (15-difficulty) == 0) {
                double angleMustTravel = (double) 360 / 8;
                //goes from 100-1
                int effectiveAttackTimer = (int) attackTimer - 50;
                //starts at 0, then goes to 99
                int angleDistanceAttackTimer = 100 - effectiveAttackTimer;

                double percentage = (double)angleDistanceAttackTimer/100;
                double angle = angleMustTravel * percentage;

                for(int i = 0; i < 8; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * angleMustTravel)+ angle, 500), "red"));
                }
            }
        }

        //speed =/= 400, 600
        else if(attackTimer == 160){
            for(int i = 0; i < 8; i++){
                double angle = i * ((double)360/8);
                toSpawn.add(new EP_Orb_Medium(this, new Vector(angle, 500), "red"));
            }
        }
        else if(attackTimer == 40){
            for(int i = 0; i < 8; i++){
                double angle = i * ((double)360/8);
                toSpawn.add(new EP_Orb_Medium(this, new Vector(angle, 500), "red"));
            }
        }
    }

    private void attackSmart(int difficulty){
//        if(attackTimer % (42 - difficulty) == 0){
        if(attackTimer % 50 == 0){
            double toPlayer = Vector.getAngle(this, Player.getThePlayer());
            double lowerBound = toPlayer - (toPlayer % 45);
            double higherBound = lowerBound + 45;

            double diffLower = Math.abs(toPlayer - lowerBound);
            double diffHigher = Math.abs(toPlayer - higherBound);

            double shotAngle;
            if(diffLower < diffHigher){
                shotAngle = lowerBound;
            }
            else{
                shotAngle = higherBound;
            }

            toSpawn.add(new EP_Oval(this, new Vector(shotAngle, 300 + (20 * difficulty)), "yellow"));
        }
    }

    @Override
    protected void deathRoutine() {
        if (Enemy.willDropPower()) {
            toSpawn.add(new Pickup_PowerUpSmall(this));
        }
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 1;
        Vector offset = new Vector();
        //motherfucking sprite is different between stages wtf
        if(Driver_Game.getCurrentGameDriver().getStageName().contains("2"))
            offset = new Vector(3, -1);
        else if(Driver_Game.getCurrentGameDriver().getStageName().contains("4"))
            offset = new Vector(-1, -1);
        else if(Driver_Game.getCurrentGameDriver().getStageName().contains("5") || Driver_Game.getCurrentGameDriver().getStageName().contains("6"))
            offset = new Vector(-2, -1);
        this.sprite = new SpriteInfo("E_machine_spray", offset, rotation, size);
    }
}
