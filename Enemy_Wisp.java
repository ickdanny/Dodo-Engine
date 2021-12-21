public class Enemy_Wisp extends Enemy{

    public Enemy_Wisp(Vector initPos, Vector polar){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Wisp(Vector initPos, Path path){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Wisp(Vector initPos, Vector polar, String attackName){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        this.attackName = attackName;
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Wisp(Vector initPos, Path path, String attackName){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        this.attackName = attackName;
        setTrueHitbox(new Hitbox_Circle(this));
    }

    //it's likely i won't need the below constructors, but i'll keep them here just in case.
    //on second thought it's VERY LIKELY i'll need these lmao
    public Enemy_Wisp(Vector initPos, Behavior behavior){
        super(initPos, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Wisp(Vector initPos, Vector polar, Behavior behavior){
        super(initPos, polar, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }

    public Enemy_Wisp(Vector initPos, Path path, Behavior behavior){
        super(initPos, path, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
    }


    public Enemy_Wisp(Vector initPos, Vector polar, double initHP){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }

    public Enemy_Wisp(Vector initPos, Path path, double initHP){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }

    public Enemy_Wisp(Vector initPos, Vector polar, String attackName, double initHP){
        super(initPos, polar, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        this.attackName = attackName;
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }

    public Enemy_Wisp(Vector initPos, Path path, String attackName, double initHP){
        super(initPos, path, 25);
        canAttack = true;
        continuousAttack = true;
        attackDelay = 200;
        reloadDelay = 200;
        this.attackName = attackName;
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }

    public Enemy_Wisp(Vector initPos, Behavior behavior, double initHP){
        super(initPos, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }

    public Enemy_Wisp(Vector initPos, Vector polar, Behavior behavior, double initHP){
        super(initPos, polar, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }

    public Enemy_Wisp(Vector initPos, Path path, Behavior behavior, double initHP){
        super(initPos, path, behavior, 25);
        setTrueHitbox(new Hitbox_Circle(this));
        this.hp = initHP;
    }

    @Override
    protected void spawnAttack(){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
//        if(attackName.equals("default")){
////            switch(Driver_GameLogic.getCurrentLogicDriver().getDifficulty()){
////                case 1:
////                    return;
////                case 2:
////                    toSpawn.add(new EP_Orb_Small(this, new Vector(0, 200), "white"));
////                    break;
////                case 3:
////                case 4:
////                    for(int i = -1; i < 2; i++){
////                        toSpawn.add(new EP_Orb_Small(this, new Vector(i * 10, 200), "white"));
////                    }
////                    break;
////                case 5:
////                case 6:
////                    for(int i = -1; i < 2; i++){
////                        toSpawn.add(new EP_Orb_Small(this, new Vector(Vector.getAngle(this, Player.getThePlayer()) + (i * 10), 225), "white"));
////                    }
////                    break;
////                case 7:
////                case 8:
////                    for(int i = -2; i < 3; i++){
////                        toSpawn.add(new EP_Orb_Small(this, new Vector(Vector.getAngle(this, Player.getThePlayer()) + (i * 10), 225), "white"));
////                    }
////                    break;
////                case 9:
////                    for(int i = -1; i < 2; i++){
////                        toSpawn.add(new EP_Orb_Small(this, new Vector(Vector.getAngle(this, Player.getThePlayer()) + (i * 10), 250), "white"));
////                    }
////                    break;
////            }
//            attackDefault(difficulty);
//        }
        switch(attackName){
            case "default":
                attackDefault(difficulty);
                break;
            case "advanced":
                attackAdvanced(difficulty);
                break;
            case "lasers":
                attackLasers(difficulty);
                break;
            case "sideShot":
                attackSideShot(difficulty);
                break;
            case "spiral":
                attackSpiral(difficulty);
                break;
            case "backShot":
                attackBackShot(difficulty);
                break;
            case "spread":
                attackSpread(difficulty);
                break;
        }
    }

    //homing shot
    private void attackDefault(int difficulty){
        if(attackTimer != 200 && attackTimer != 100 && attackTimer != 50 && attackTimer != 150){
            return;
        }
        if(difficulty <= 4 && (attackTimer != 200 && attackTimer != 100)){
            return;
        }

        double angle = Vector.getAngle(this, Player.getThePlayer());

        switch(difficulty){
            case 1:
                return;
            case 2:
                toSpawn.add(new EP_Orb_Small(this, new Vector(angle, 200), "white"));
                break;
            case 3:
                toSpawn.add(new EP_Orb_Small(this, new Vector(angle, 250), "white"));
                break;
            case 4:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 10) + angle, 250), "white"));
                }
                break;
            case 5:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 10) + angle, 300), "white"));
                }
                break;
            case 6:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 10) + angle, 325), "white"));
                }
                break;
            case 7:
                for(int i = -2; i < 3; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 8) + angle, 325), "white"));
                }
                break;
            case 8:
                for(int i = -2; i < 3; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 8) + angle, 350), "white"));
                }
                break;
            case 9:
                for(int i = -3; i < 4; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 7) + angle, 350), "white"));
                }
                break;
        }
    }

    //bombing shot
    private void attackAdvanced(int difficulty){
        if(attackTimer % 40 != 0){
            return;
        }
        if(attackTimer < 40){
            return;
        }
        int discriminator = (int)attackTimer % 3;
        int angleOffset = 10 + (10 * discriminator);
        for(int i = 0; i < difficulty; i++){
            toSpawn.add(new EP_Orb_Small(this, new Vector(angleOffset, 100 + (50 * i)), "lightBlue"));
            toSpawn.add(new EP_Orb_Small(this, new Vector(-angleOffset, 100 + (50 * i)), "lightBlue"));
        }
    }

    //lasers
    private void attackLasers(int difficulty){
        if(attackTimer != 200){
            return;
        }

        double y = 100;
        for(int i = 0; i < difficulty; i++){
            y += 50;
        }
        Vector pos1 = new Vector(50, y);
        Vector pos2 = new Vector(-50, y);
        pos1 = Vector.add(this, pos1);
        pos2 = Vector.add(this, pos2);
        toSpawn.add(new EP_Laser_Wisp(this, pos1, pos2));
        toSpawn.add(new EP_Laser_Wisp(this, pos2, pos1));
    }

    //ship cannon style
    private void attackSideShot(int difficulty){
        if(attackTimer != 200 && attackTimer != 100){
            return;
        }
        if(difficulty <= 4 && attackTimer != 200){
            return;
        }

        double angle = 90;
        double angle2 = 270;

        switch(difficulty){
            case 1:
                return;
            case 2:
                toSpawn.add(new EP_Orb_Small(this, new Vector(angle, 200), "white"));
                toSpawn.add(new EP_Orb_Small(this, new Vector(angle2, 200), "white"));
                break;
            case 3:
                toSpawn.add(new EP_Orb_Small(this, new Vector(angle, 250), "white"));
                toSpawn.add(new EP_Orb_Small(this, new Vector(angle2, 250), "white"));
                break;
            case 4:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 10) + angle, 250), "white"));
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 10) + angle2, 250), "white"));
                }
                break;
            case 5:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 10) + angle, 300), "white"));
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 10) + angle2, 300), "white"));
                }
                break;
            case 6:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 10) + angle, 325), "white"));
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 10) + angle2, 325), "white"));

                }
                break;
            case 7:
                for(int i = -2; i < 3; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 8) + angle, 325), "white"));
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 8) + angle2, 325), "white"));

                }
                break;
            case 8:
                for(int i = -2; i < 3; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 8) + angle, 350), "white"));
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 8) + angle2, 350), "white"));

                }
                break;
            case 9:
                for(int i = -3; i < 4; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 7) + angle, 350), "white"));
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 7) + angle2, 350), "white"));

                }
                break;
        }
    }

    //dual spawn points
    private void attackSpiral(int difficulty){
        if(attackTimer % 8 != 0){
            return;
        }

        int numBullets = 1 + ((difficulty - 1) / 3);

        double angle = attackTimer * 1.8;

        for(int i = 0; i < numBullets; i++){
            toSpawn.add(new EP_Oval(this, new Vector(angle, 200 + (200 * i)), "lightBlue"));
            toSpawn.add(new EP_Oval(this, new Vector(angle + 180, 200 + (200 * i)), "lightBlue"));
        }
    }

    //straight up
    private void attackBackShot(int difficulty){
        if(attackTimer != 200 && attackTimer != 100){
            return;
        }
        if(difficulty <= 4 && attackTimer != 200){
            return;
        }

        double angle = 180;

        switch(difficulty){
            case 1:
                return;
            case 2:
                toSpawn.add(new EP_Orb_Small(this, new Vector(angle, 200), "white"));
                break;
            case 3:
                toSpawn.add(new EP_Orb_Small(this, new Vector(angle, 250), "white"));
                break;
            case 4:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 10) + angle, 250), "white"));
                }
                break;
            case 5:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 10) + angle, 300), "white"));
                }
                break;
            case 6:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 10) + angle, 325), "white"));
                }
                break;
            case 7:
                for(int i = -2; i < 3; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 8) + angle, 325), "white"));
                }
                break;
            case 8:
                for(int i = -2; i < 3; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 8) + angle, 350), "white"));
                }
                break;
            case 9:
                for(int i = -3; i < 4; i++){
                    toSpawn.add(new EP_Orb_Small(this, new Vector((i * 7) + angle, 350), "white"));
                }
                break;
        }
    }

    //instant spread shot
    private void attackSpread(int difficulty){
        if(attackTimer != 200){
            return;
        }

        double angle = Vector.getAngle(this, Player.getThePlayer());
        int spread = 3;
        switch(difficulty){
            case 9:
            case 8:
                spread = 12;
                break;
            case 7:
            case 6:
                spread = 10;
                break;
            case 5:
                spread = 8;
                break;
            case 4:
                spread = 6;
                break;
            case 3:
            case 2:
                spread = 4;
                break;
        }

        for(int i = 0; i < spread; i++){
            toSpawn.add(new EP_Orb_Medium(this, new Vector(angle + (i * (360/spread)), 200), "white"));
            if(difficulty >= 3){
                toSpawn.add(new EP_Orb_Medium(this, new Vector(angle + (i * (360/spread)), 300), "lightBlue"));
            }
            if (difficulty >= 5) {
                toSpawn.add(new EP_Orb_Medium(this, new Vector(angle + (i * (360/spread)), 400), "blue"));
            }
        }
    }

    //maybe have a SmallEnemy class (maybe called minion?) that has this as the default?
    @Override
    protected void deathRoutine(){
//        toSpawn.add(new Pickup_PowerUpSmall(this));
//        toSpawn.add(new Pickup_Bomb(Vector.add(this, new Vector(100, 0))));
//        toSpawn.add(new Pickup_Life(Vector.add(this, new Vector(200, 0))));
//        toSpawn.add(new Pickup_PowerUpLarge(Vector.add(this, new Vector(-100, 0))));
        if(Enemy.willDropPower()) {
            toSpawn.add(new Pickup_PowerUpSmall(this));
        }
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 1;
        Vector offset = new Vector(-1, 0);
        this.sprite = new SpriteInfo("E_wisp1", offset, rotation, size);
        this.sprite.setNext(new SpriteInfo("E_wisp2", offset, rotation, size,
                new SpriteInfo("E_wisp3", offset, rotation, size,
                        new SpriteInfo("E_wisp4", offset, rotation, size, this.sprite))));
    }

    @Override
    protected void updateSprite(){
        //return new SpriteInfo("E_wisp1", new Vector(-1, 0), tick, 3);
        if(tick % 5 == 0){
            this.sprite = this.sprite.getNext();
        }
    }
}
