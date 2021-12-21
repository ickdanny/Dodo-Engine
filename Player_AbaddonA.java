public class Player_AbaddonA extends Player {
    private Vector scythePos = new Vector(40, 42);
    private Vector leftPos = new Vector(-15, 0);
    private Vector rightPos = new Vector(15, 0);

    public Player_AbaddonA(int hp, int bombs, int power){
        super(4, new Hitbox_Circle(), 60, hp, bombs, power);
        trueHitbox.setOwner(this);
        AABB.setOwner(this);
        twoFrameHitbox.setOwner(this);
        baseSpeed = 650;
        baseFocusSpeed = baseSpeed/3;
    }

    @Override
    protected void spawnAttack() {
        //generate different attacks based on Power, start from highest so that in late game with more bullets shorter retrieval
        if (attackName.equals("default")) {
            //this block finds the target.
            Vector target = Vector.add(this, scythePos);
            target.setB(target.getB() - 1000);
            for (Enemy e : Driver_GameLogic.getCurrentLogicDriver().getEnemies()) {
                if (target instanceof Enemy) {
                    if(e.getCanBeDamaged()) {
                        if (Vector.getDistance(this, e) < Vector.getDistance(this, target)) {
                            target = e;
                        }
                    }
                }
                else {
                    target = e;
                }
            }
            if (!focused) {
                if (power >= 50) {
                    if (power >= 100) {
                        if (power >= 150) {
                            if (power == 200) {
                                attack11(target);
                            } else {
                                attack10(target);
                            }
                        } else {
                            attack9(target);
                        }
                    } else if (power >= 80) {
                        attack8(target);
                    } else if (power >= 65) {
                        attack7(target);
                    } else {
                        attack6(target);
                    }
                } else if (power >= 20) {
                    if (power >= 30) {
                        if (power >= 40) {
                            attack5(target);
                        } else {
                            attack4(target);
                        }
                    } else {
                        attack3(target);
                    }
                } else if (power >= 10) {
                    attack2(target);
                } else {
                    attack1(target);
                }
            } else {
                if (power >= 50) {
                    if (power >= 100) {
                        if (power >= 150) {
                            if (power == 200) {
                                fattack11(target);
                            } else {
                                fattack10(target);
                            }
                        } else {
                            fattack9(target);
                        }
                    } else if (power >= 80) {
                        fattack8(target);
                    } else if (power >= 65) {
                        fattack7(target);
                    } else {
                        fattack6(target);
                    }
                } else if (power >= 20) {
                    if (power >= 30) {
                        if (power >= 40) {
                            fattack5(target);
                        } else {
                            fattack4(target);
                        }
                    } else {
                        fattack3(target);
                    }
                } else if (power >= 10) {
                    fattack2(target);
                } else {
                    fattack1(target);
                }
            }
        }
    }

    private void attack1(Vector target){
        if(attackTimer % 30 == 0){
            toSpawn.add(new Bullet_Aba_Default(this, new Vector(180, 3000)));
        }
    }
    private void attack2(Vector target){
        if(attackTimer % 15 == 0){
            toSpawn.add(new Bullet_Aba_Default(this, new Vector(180, 3000)));
        }
    }
    private void attack3(Vector target){
        if(attackTimer % 15 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
    }
    private void attack4(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
    }
    private void attack5(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
        if(attackTimer % 10 == 0 && attackTimer >= 40){
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 20) - 10), 500)));
        }
    }
    private void attack6(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
        if(attackTimer % 10 == 0 && attackTimer >= 40){
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 20) - 10), 500)));
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 20) - 10), 500)));
        }
    }
    private void attack7(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
        if(attackTimer % 10 == 0 && attackTimer >= 30){
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 20) - 10), 500)));
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 20) - 10), 500)));
        }
    }
    private void attack8(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
        if(attackTimer % 5 == 0 && attackTimer >= 30){
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 20) - 10), 500)));
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 20) - 10), 500)));
        }
    }
    private void attack9(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(183, 3000)));
            toSpawn.add(new Bullet_Aba_Default(this, new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(177, 3000)));
        }
        if(attackTimer % 5 == 0 && attackTimer >= 30){
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 20) - 10), 500)));
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 20) - 10), 500)));
        }
    }
    private void attack10(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(183, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(177, 3000)));
        }
        if(attackTimer % 5 == 0 && attackTimer >= 30){
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 20) - 10), 500)));
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 20) - 10), 500)));
        }
    }
    private void attack11(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(183, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(177, 3000)));
        }
        if(attackTimer % 2 == 0 && attackTimer >= 30){
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 20) - 10), 500)));
            if(Math.random() > .5)
                toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 20) - 10), 500)));
        }
    }
    private void fattack1(Vector target){
        if(attackTimer % 30 == 0){
            toSpawn.add(new Bullet_Aba_Default(this, new Vector(180, 3000)));
        }
    }
    private void fattack2(Vector target){
        if(attackTimer % 15 == 0){
            toSpawn.add(new Bullet_Aba_Default(this, new Vector(180, 3000)));
        }
    }
    private void fattack3(Vector target){
        if(attackTimer % 15 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
    }
    private void fattack4(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
    }
    private void fattack5(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
        if(attackTimer % 10 == 0 && attackTimer >= 40){
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 3) - 1.5), 700)));
        }
    }
    private void fattack6(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
        if(attackTimer % 10 == 0 && attackTimer >= 40){
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 3) - 1.5), 700)));
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 3) - 1.5), 700)));
        }
    }
    private void fattack7(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
        if(attackTimer % 10 == 0 && attackTimer >= 30){
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 3) - 1.5), 700)));
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 3) - 1.5), 700)));
        }
    }
    private void fattack8(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
        if(attackTimer % 5 == 0 && attackTimer >= 30){
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 3) - 1.5), 700)));
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 3) - 1.5), 700)));
        }
    }
    private void fattack9(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(183, 3000)));
            toSpawn.add(new Bullet_Aba_Default(this, new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(177, 3000)));
        }
        if(attackTimer % 5 == 0 && attackTimer >= 30){
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 3) - 1.5), 700)));
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 3) - 1.5), 700)));
        }
    }
    private void fattack10(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(183, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(177, 3000)));
        }
        if(attackTimer % 5 == 0 && attackTimer >= 30){
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 3) - 1.5), 700)));
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 3) - 1.5), 700)));
        }
    }
    private void fattack11(Vector target){
        if(attackTimer % 10 == 0){
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(183, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(177, 3000)));
        }
        if(attackTimer % 2 == 0 && attackTimer >= 30) {
            toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 3) - 1.5), 700)));
            if (Math.random() > .5) {
                toSpawn.add(new Bullet_Aba_Scythe(Vector.add(this, scythePos), new Vector(Vector.getAngle(Vector.add(this, scythePos), target) + ((Math.random() * 3) - 1.5), 700)));
            }
        }
    }

    @Override
    protected void spawnBomb(){
        toSpawn.add(new Bomb_Aba_Wave(this));
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 3;
        double yOffset = -6.8;
        Vector offsetIdle = new Vector(-1.5, yOffset);

        Vector offsetLT1 = new Vector(-1.5, yOffset);
        Vector offsetLT2 = new Vector(-1.4, yOffset);
        Vector offsetLT3 = new Vector(-1.2, yOffset);
        Vector offsetL = new Vector(-1.2, yOffset);

        Vector offsetRT1 = new Vector(1, yOffset);
        Vector offsetRT2 = new Vector(.8, yOffset);
        Vector offsetRT3 = new Vector(.8, yOffset);
        Vector offsetR = new Vector(1, yOffset);

        idle = new SpriteInfo("Player_AbaddonA_idle1", offsetIdle, rotation, size);
        idle.setNext(new SpriteInfo("Player_AbaddonA_idle2", offsetIdle, rotation, size,
                new SpriteInfo("Player_AbaddonA_idle3", offsetIdle, rotation, size,
                        new SpriteInfo("Player_AbaddonA_idle4", offsetIdle, rotation, size, idle))));

        leftTurn1 = new SpriteInfo("Player_AbaddonA_leftTurn1", offsetLT1, rotation, size);
        leftTurn2 = new SpriteInfo("Player_AbaddonA_leftTurn2", offsetLT2, rotation, size);
        leftTurn3 = new SpriteInfo("Player_AbaddonA_leftTurn3", offsetLT3, rotation, size);
        left = new SpriteInfo("Player_AbaddonA_left1", offsetL, rotation, size);
        left.setNext(new SpriteInfo("Player_AbaddonA_left2", offsetL, rotation, size,
                new SpriteInfo("Player_AbaddonA_left3", offsetL, rotation, size,
                        new SpriteInfo("Player_AbaddonA_left4", offsetL, rotation, size, left))));

        rightTurn1 = new SpriteInfo("Player_AbaddonA_rightTurn1", offsetRT1, rotation, size);
        rightTurn2 = new SpriteInfo("Player_AbaddonA_rightTurn2", offsetRT2, rotation, size);
        rightTurn3 = new SpriteInfo("Player_AbaddonA_rightTurn3", offsetRT3, rotation, size);
        right = new SpriteInfo("Player_AbaddonA_right1", offsetR, rotation, size);
        right.setNext(new SpriteInfo("Player_AbaddonA_right2", offsetR, rotation, size,
                new SpriteInfo("Player_AbaddonA_right3", offsetR, rotation, size,
                        new SpriteInfo("Player_AbaddonA_right4", offsetR, rotation, size, right))));

        this.sprite = idle;
    }

//    @Override
//    protected void updateSprite(){
//        if(tick % 20 == 0)
//            this.sprite = this.sprite.getNext();
//    }
}