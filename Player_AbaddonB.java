public class Player_AbaddonB extends Player {

    private Vector leftPos = new Vector(-15, 0);
    private Vector rightPos = new Vector(15, 0);
    private Bullet_Aba_Halo[] halo = new Bullet_Aba_Halo[3];

    public Player_AbaddonB(int hp, int bombs, int power) {
        super(4, new Hitbox_Circle(), 60, hp, bombs, power);
        trueHitbox.setOwner(this);
        AABB.setOwner(this);
        twoFrameHitbox.setOwner(this);
        baseSpeed = 650;
        baseFocusSpeed = baseSpeed / 3;
        //initial position, owner, angular velocity
        halo[0] = new Bullet_Aba_Halo(Vector.add(this, Vector.polarToVelocity(new Vector(0, 100))), this, new Vector(-1 * 360 / halo.length, 100));
        halo[1] = new Bullet_Aba_Halo(Vector.add(this, Vector.polarToVelocity(new Vector(120, 100))), this, new Vector(-1 * 360 / halo.length, 100));
        halo[2] = new Bullet_Aba_Halo(Vector.add(this, Vector.polarToVelocity(new Vector(240, 100))), this, new Vector(-1 * 360 / halo.length, 100));
        if (power >= 40) {
            toSpawn.add(halo[0]);
            toSpawn.add(halo[1]);
            toSpawn.add(halo[2]);
        }
    }

    @Override
    public void update() {
        if (power < 40) {
            halo[0].setExists(false);
            halo[1].setExists(false);
            halo[2].setExists(false);
            //purpose of these lines?
            //makes it so when they reappear they don't teleport
            halo[0].update();
            halo[1].update();
            halo[2].update();
        } else if (!halo[0].getExists()) {
            halo[0].setExists(true);
            halo[1].setExists(true);
            halo[2].setExists(true);
            toSpawn.add(halo[0]);
            toSpawn.add(halo[1]);
            toSpawn.add(halo[2]);
        }
        super.update();
    }

    //starts at 60, goes down to 1
    @Override
    protected void spawnAttack() {
        //generate different attacks based on Power, start from highest so that in late game with more bullets shorter retrieval
        if (attackName.equals("default")) {
            //this block finds the target.
            Enemy target = null;
            if (Driver_GameLogic.getCurrentLogicDriver().getEnemies().size() > 0) {
                for (Enemy e : Driver_GameLogic.getCurrentLogicDriver().getEnemies()) {
                    if (e.getCanBeDamaged()) {
                        if (target != null) {
                            if (Vector.getDistance(this, e) < Vector.getDistance(this, target)) {
                                target = e;
                            }
                        } else {
                            target = e;
                        }
                    }

                }
            }

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
        }
    }

    //if enemy is void the homing projectile will just not home in on anything
    private void attack1(Enemy target) {
        if (attackTimer % 30 == 0) {
            toSpawn.add(new Bullet_Aba_Default(this, new Vector(180, 3000)));
        }
    }

    private void attack2(Enemy target) {
        if (attackTimer % 15 == 0) {
            toSpawn.add(new Bullet_Aba_Default(this, new Vector(180, 3000)));
        }
    }

    private void attack3(Enemy target) {
        if (attackTimer % 15 == 0) {
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
    }

    private void attack4(Enemy target) {
        if (attackTimer % 10 == 0) {
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
    }

    private void attack5(Enemy target) {
        if (attackTimer % 10 == 0) {
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
        if (attackTimer % 10 == 0 && attackTimer > 30) {
            halo[0].spawnProjectiles(target);
        }
    }

    private void attack6(Enemy target) {
        if (attackTimer % 10 == 0) {
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
        if (attackTimer % 10 == 0 && attackTimer > 30) {
            halo[0].spawnProjectiles(target);
            halo[1].spawnProjectiles(target);
        }
    }

    private void attack7(Enemy target) {
        if (attackTimer % 10 == 0) {
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
        if (attackTimer % 10 == 0 && attackTimer > 30) {
            halo[0].spawnProjectiles(target);
            halo[1].spawnProjectiles(target);
            halo[2].spawnProjectiles(target);
        }
    }

    private void attack8(Enemy target) {
        if (attackTimer % 10 == 0) {
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
        }
        if (attackTimer % 8 == 0 && attackTimer > 30) {
            halo[0].spawnProjectiles(target);
            halo[1].spawnProjectiles(target);
            halo[2].spawnProjectiles(target);
        }
    }

    private void attack9(Enemy target) {
        if (attackTimer % 10 == 0) {
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(183, 3000)));
            toSpawn.add(new Bullet_Aba_Default(this, new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(177, 3000)));
        }
        if (attackTimer % 6 == 0 && attackTimer > 30) {
            halo[0].spawnProjectiles(target);
            halo[1].spawnProjectiles(target);
            halo[2].spawnProjectiles(target);
        }
    }

    private void attack10(Enemy target) {
        if (attackTimer % 10 == 0) {
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(183, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(177, 3000)));
        }
        if (attackTimer % 4 == 0 && attackTimer > 30) {
            halo[0].spawnProjectiles(target);
            halo[1].spawnProjectiles(target);
            halo[2].spawnProjectiles(target);
        }
    }

    private void attack11(Enemy target) {
        if (attackTimer % 10 == 0) {
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(183, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, leftPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(180, 3000)));
            toSpawn.add(new Bullet_Aba_Default(Vector.add(this, rightPos), new Vector(177, 3000)));
        }
        if (attackTimer % 2 == 0 && attackTimer > 30) {
            halo[0].spawnProjectiles(target);
            halo[1].spawnProjectiles(target);
            halo[2].spawnProjectiles(target);
        }
    }

    @Override
    protected void spawnBomb() {
        toSpawn.add(new Bomb_Aba_Field(this));
    }

    public Bullet_Aba_Halo[] getHalo() {
        return halo;
    }

    @Override
    protected void initSprite() {
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

        idle = new SpriteInfo("Player_AbaddonB_idle1", offsetIdle, rotation, size);
        idle.setNext(new SpriteInfo("Player_AbaddonB_idle2", offsetIdle, rotation, size,
                new SpriteInfo("Player_AbaddonB_idle3", offsetIdle, rotation, size,
                        new SpriteInfo("Player_AbaddonB_idle4", offsetIdle, rotation, size, idle))));

        leftTurn1 = new SpriteInfo("Player_AbaddonB_leftTurn1", offsetLT1, rotation, size);
        leftTurn2 = new SpriteInfo("Player_AbaddonB_leftTurn2", offsetLT2, rotation, size);
        leftTurn3 = new SpriteInfo("Player_AbaddonB_leftTurn3", offsetLT3, rotation, size);
        left = new SpriteInfo("Player_AbaddonB_left1", offsetL, rotation, size);
        left.setNext(new SpriteInfo("Player_AbaddonB_left2", offsetL, rotation, size,
                new SpriteInfo("Player_AbaddonB_left3", offsetL, rotation, size,
                        new SpriteInfo("Player_AbaddonB_left4", offsetL, rotation, size, left))));

        rightTurn1 = new SpriteInfo("Player_AbaddonB_rightTurn1", offsetRT1, rotation, size);
        rightTurn2 = new SpriteInfo("Player_AbaddonB_rightTurn2", offsetRT2, rotation, size);
        rightTurn3 = new SpriteInfo("Player_AbaddonB_rightTurn3", offsetRT3, rotation, size);
        right = new SpriteInfo("Player_AbaddonB_right1", offsetR, rotation, size);
        right.setNext(new SpriteInfo("Player_AbaddonB_right2", offsetR, rotation, size,
                new SpriteInfo("Player_AbaddonB_right3", offsetR, rotation, size,
                        new SpriteInfo("Player_AbaddonB_right4", offsetR, rotation, size, right))));

        this.sprite = idle;


//        double rotation = 0;
//        double size = 4;
//        Vector offset = new Vector(-1.5, -6.8);
//        this.sprite = new SpriteInfo("Player_AbaddonA_idle1", offset, rotation, size);
//        this.sprite.setNext(new SpriteInfo("Player_AbaddonA_idle2", offset, rotation, size));
//        this.sprite.getNext().setNext(new SpriteInfo("Player_AbaddonA_idle3", offset, rotation, size));
//        this.sprite.getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_idle4", offset, rotation, size));
//        this.sprite.getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_leftTurn1", offset, rotation, size));
//        offset = new Vector(-1.4, -6.8);
//        this.sprite.getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_leftTurn2", offset, rotation, size));
//        offset = new Vector(-1.2, -6.8);
//        this.sprite.getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_leftTurn3", offset, rotation, size));
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_left1", offset, rotation, size));
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_left2", offset, rotation, size));
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_left3", offset, rotation, size));
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_left4", offset, rotation, size));
//        offset = new Vector(.8, -6.8);
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_rightTurn1", offset, rotation, size));
//        offset = new Vector(.3, -6.8);
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_rightTurn2", offset, rotation, size));
//        offset = new Vector(.1, -6.8);
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_rightTurn3", offset, rotation, size));
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_right1", offset, rotation, size));
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_right2", offset, rotation, size));
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_right3", offset, rotation, size));
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonA_right4", offset, rotation, size, this.sprite));


//        this.sprite.getNext().setNext(new SpriteInfo("Player_AbaddonB_right3", offset, rotation, size));
//        this.sprite.getNext().getNext().setNext(new SpriteInfo("Player_AbaddonB_right4", offset, rotation, size, this.sprite));

//        Vector offset = new Vector(-1.5, -6.8);
//        this.sprite = new SpriteInfo("Player_AbaddonB_idle1", offset, rotation, size);
//        offset = new Vector(.8, -6.8);
//        this.sprite.setNext(new SpriteInfo("Player_AbaddonB_rightTurn1", offset, rotation, size));
//        offset = new Vector(.3, -6.8);
//        this.sprite.getNext().setNext(new SpriteInfo("Player_AbaddonB_rightTurn2", offset, rotation, size));
//        offset = new Vector(.1, -6.8);
//        this.sprite.getNext().getNext().setNext(new SpriteInfo("Player_AbaddonB_rightTurn3", offset, rotation, size));
//        this.sprite.getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonB_right1", offset, rotation, size, this.sprite));

//        Vector offset = new Vector(-1.5, -6.8);
//        this.sprite = new SpriteInfo("Player_AbaddonB_idle1", offset, rotation, size);
//        this.sprite.setNext(new SpriteInfo("Player_AbaddonB_leftTurn1", offset, rotation, size));
//        offset = new Vector(-1.4, -6.8);
//        this.sprite.getNext().setNext(new SpriteInfo("Player_AbaddonB_leftTurn2", offset, rotation, size));
//        offset = new Vector(-1.2, -6.8);
//        this.sprite.getNext().getNext().setNext(new SpriteInfo("Player_AbaddonB_leftTurn3", offset, rotation, size));
//        this.sprite.getNext().getNext().getNext().setNext(new SpriteInfo("Player_AbaddonB_left1", offset, rotation, size, this.sprite));
//        this.sprite.setNext(new SpriteInfo("Player_AbaddonB_idle2", offset, rotation, size));
//        this.sprite.getNext().setNext(new SpriteInfo("Player_AbaddonB_idle3", offset, rotation, size));
//        this.sprite.getNext().getNext().setNext(new SpriteInfo("Player_AbaddonB_idle4", offset, rotation, size, this.sprite));
    }

//    @Override
//    protected void updateSprite(){
//        if(tick % 20 == 0)
//            this.sprite = this.sprite.getNext();
//    }
}