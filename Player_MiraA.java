public class Player_MiraA extends Player {

    public Player_MiraA(int hp, int bombs, int power) {
        super(4, new Hitbox_Circle(), 60, hp, bombs, power);
        trueHitbox.setOwner(this);
        AABB.setOwner(this);
        twoFrameHitbox.setOwner(this);
        baseSpeed = 800;
        baseFocusSpeed = baseSpeed / 3;
    }

    @Override
    protected void spawnAttack() {
        //generate different attacks based on Power, start from highest so that in late game with more bullets shorter retrieval
        if (attackName.equals("default")) {
            if (!focused) {
                if (power >= 50) {
                    if (power >= 100) {
                        if (power >= 150) {
                            if (power == 200) {
                                attack11();
                            } else {
                                attack10();
                            }
                        } else {
                            attack9();
                        }
                    } else if (power >= 80) {
                        attack8();
                    } else if (power >= 65) {
                        attack7();
                    } else {
                        attack6();
                    }
                } else if (power >= 20) {
                    if (power >= 30) {
                        if (power >= 40) {
                            attack5();
                        } else {
                            attack4();
                        }
                    } else {
                        attack3();
                    }
                } else if (power >= 10) {
                    attack2();
                } else {
                    attack1();
                }
            } else {
                if (power >= 50) {
                    if (power >= 100) {
                        if (power >= 150) {
                            if (power == 200) {
                                fattack11();
                            } else {
                                fattack10();
                            }
                        } else {
                            fattack9();
                        }
                    } else if (power >= 80) {
                        fattack8();
                    } else if (power >= 65) {
                        fattack7();
                    } else {
                        fattack6();
                    }
                } else if (power >= 20) {
                    if (power >= 30) {
                        if (power >= 40) {
                            fattack5();
                        } else {
                            fattack4();
                        }
                    } else {
                        fattack3();
                    }
                } else if (power >= 10) {
                    fattack2();
                } else {
                    fattack1();
                }
            }
        }
    }

    private void attack1() {
        if (attackTimer % 30 == 0) {
            toSpawn.add(new Bullet_Mira_Default(this));
        }
    }

    private void attack2() {
        if (attackTimer % 30 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(13, 0))));
        }
    }

    private void attack3() {
        if (attackTimer % 30 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(13, 0))));
        }
        if (attackTimer % 60 == 0) {
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(0, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(45, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(90, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(135, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(180, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(225, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(270, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(315, 2000)));
        }
    }

    private void attack4() {
        if (attackTimer % 20 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(13, 0))));
        }
        if (attackTimer % 60 == 0) {
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(0, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(45, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(90, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(135, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(180, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(225, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(270, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(315, 2000)));
        }
    }

    private void attack5() {
        if (attackTimer % 20 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-26, 0))));
            toSpawn.add(new Bullet_Mira_Default(this));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(26, 0))));
        }
        if (attackTimer % 60 == 0) {
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(0, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(45, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(90, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(135, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(180, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(225, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(270, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(315, 2000)));
        }
    }

    private void attack6() {
        if (attackTimer % 20 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-26, 0))));
            toSpawn.add(new Bullet_Mira_Default(this));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(26, 0))));
        }
        if (attackTimer % 30 == 0) {
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(0, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(45, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(90, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(135, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(180, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(225, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(270, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(315, 2000)));
        }
    }

    private void attack7() {
        if (attackTimer % 15 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-26, 0))));
            toSpawn.add(new Bullet_Mira_Default(this));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(26, 0))));
        }
        if (attackTimer % 30 == 0) {
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(0, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(45, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(90, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(135, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(180, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(225, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(270, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(315, 2000)));
        }
    }

    private void attack8() {
        if (attackTimer % 15 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-39, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(39, 0))));
        }
        if (attackTimer % 30 == 0) {
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(0, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(45, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(90, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(135, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(180, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(225, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(270, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(315, 2000)));
        }
    }

    private void attack9() {
        if (attackTimer % 15 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-39, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(39, 0))));
        }
        if (attackTimer % 20 == 0) {
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(0, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(45, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(90, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(135, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(180, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(225, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(270, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(315, 2000)));
        }
    }

    private void attack10() {
        if (attackTimer % 12 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-39, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(39, 0))));
        }
        if (attackTimer % 15 == 0) {
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(0, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(45, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(90, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(135, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(180, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(225, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(270, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(315, 2000)));
        }
    }

    private void attack11() {
        if (attackTimer % 12 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-52, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-26, 0))));
            toSpawn.add(new Bullet_Mira_Default(this));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(26, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(52, 0))));
        }
        if (attackTimer % 12 == 0) {
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(0, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(45, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(90, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(135, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(180, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(225, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(270, 2000)));
            toSpawn.add(new Bullet_Mira_Wave(this, new Vector(315, 2000)));
        }
    }

    private void fattack1() {
        if (attackTimer % 30 == 0) {
            toSpawn.add(new Bullet_Mira_Default(this));
        }
    }

    private void fattack2() {
        if (attackTimer % 30 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(13, 0))));
        }
    }

    private void fattack3() {
        if (attackTimer % 30 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(13, 0))));
        }
        if (attackTimer % 60 == 0) {
            toSpawn.add(new Bullet_Mira_WaveBall(this, new Vector(180, 750), 50, 5));
        }
    }

    private void fattack4() {
        if (attackTimer % 20 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(13, 0))));
        }
        if (attackTimer % 60 == 0) {
            toSpawn.add(new Bullet_Mira_WaveBall(this, new Vector(180, 750), 50, 5));
        }
    }

    private void fattack5() {
        if (attackTimer % 20 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-26, 0))));
            toSpawn.add(new Bullet_Mira_Default(this));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(26, 0))));
        }
        if (attackTimer % 60 == 0) {
            toSpawn.add(new Bullet_Mira_WaveBall(this, new Vector(180, 750), 50, 5));
        }
    }

    private void fattack6() {
        if (attackTimer % 20 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-26, 0))));
            toSpawn.add(new Bullet_Mira_Default(this));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(26, 0))));
        }
        if (attackTimer % 60 == 0) {
            toSpawn.add(new Bullet_Mira_WaveBall(this, new Vector(180, 750), 60, 7));
        }
    }

    private void fattack7() {
        if (attackTimer % 15 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-26, 0))));
            toSpawn.add(new Bullet_Mira_Default(this));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(26, 0))));
        }
        if (attackTimer % 60 == 0) {
            toSpawn.add(new Bullet_Mira_WaveBall(this, new Vector(180, 750), 60, 7));
        }
    }

    private void fattack8() {
        if (attackTimer % 15 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-39, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(39, 0))));
        }
        if (attackTimer % 60 == 0) {
            toSpawn.add(new Bullet_Mira_WaveBall(this, new Vector(180, 750), 60, 7));
        }
    }

    private void fattack9() {
        if (attackTimer % 15 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-39, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(39, 0))));
        }
        if (attackTimer % 30 == 0) {
            toSpawn.add(new Bullet_Mira_WaveBall(this, new Vector(180, 750), 60, 7));
        }
    }

    private void fattack10() {
        if (attackTimer % 12 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-39, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(13, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(39, 0))));
        }
        if (attackTimer % 30 == 0) {
            toSpawn.add(new Bullet_Mira_WaveBall(this, new Vector(180, 750), 70, 9));
        }
    }

    private void fattack11() {
        if (attackTimer % 12 == 0) {
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-52, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(-26, 0))));
            toSpawn.add(new Bullet_Mira_Default(this));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(26, 0))));
            toSpawn.add(new Bullet_Mira_Default(Vector.add(this, new Vector(52, 0))));
        }
        if (attackTimer % 20 == 0) {
            toSpawn.add(new Bullet_Mira_WaveBall(this, new Vector(180, 750), 70, 9));
        }
    }

    @Override
    protected void spawnBomb() {
        toSpawn.add(new Bomb_Mira_Ripple(this));
    }

    @Override
    protected void initSprite() {
        double rotation = 0;
        double size = 3;
        double yOffset = -9;
        Vector offsetIdle = new Vector(9.3, yOffset);

        Vector offsetLT1 = new Vector(8.5, yOffset);
        Vector offsetLT2 = new Vector(8.5, yOffset);
        Vector offsetLT3 = new Vector(6.8, yOffset);
        Vector offsetL = new Vector(7, yOffset);

        Vector offsetRT1 = new Vector(9.4, yOffset);
        Vector offsetRT2 = new Vector(9.4, yOffset);
        Vector offsetRT3 = new Vector(10.8, yOffset);
        Vector offsetR = new Vector(11.8, yOffset);

        idle = new SpriteInfo("Player_Mira3_idle1", offsetIdle, rotation, size);
        idle.setNext(new SpriteInfo("Player_Mira3_idle2", offsetIdle, rotation, size,
                new SpriteInfo("Player_Mira3_idle3", offsetIdle, rotation, size,
                        new SpriteInfo("Player_Mira3_idle4", offsetIdle, rotation, size, idle))));

        leftTurn1 = new SpriteInfo("Player_Mira3_leftTurn1", offsetLT1, rotation, size);
        leftTurn2 = new SpriteInfo("Player_Mira3_leftTurn2", offsetLT2, rotation, size);
        leftTurn3 = new SpriteInfo("Player_Mira3_leftTurn3", offsetLT3, rotation, size);
        left = new SpriteInfo("Player_Mira3_left1", offsetL, rotation, size);
        left.setNext(new SpriteInfo("Player_Mira3_left2", Vector.add(offsetL, new Vector(2, 0)), rotation, size,
                new SpriteInfo("Player_Mira3_left3", offsetL, rotation, size,
                        new SpriteInfo("Player_Mira3_left4", offsetL, rotation, size, left))));

        rightTurn1 = new SpriteInfo("Player_Mira3_rightTurn1", offsetRT1, rotation, size);
        rightTurn2 = new SpriteInfo("Player_Mira3_rightTurn2", offsetRT2, rotation, size);
        rightTurn3 = new SpriteInfo("Player_Mira3_rightTurn3", offsetRT3, rotation, size);
        right = new SpriteInfo("Player_Mira3_right1", offsetR, rotation, size);
        right.setNext(new SpriteInfo("Player_Mira3_right2", offsetR, rotation, size,
                new SpriteInfo("Player_Mira3_right3", offsetR, rotation, size,
                        new SpriteInfo("Player_Mira3_right4", offsetR, rotation, size, right))));

        this.sprite = idle;
//        double rotation = 0;
//        double size = 4;
////        Vector offset = new Vector(8.5, -20);
////        this.sprite = new SpriteInfo("Player_Mira3_leftTurn1", offset, rotation, size);
////        offset = new Vector(9.4, -20);
////        this.sprite.setNext(new SpriteInfo("Player_Mira3_rightTurn1", offset, rotation, size, this.sprite));
//
//
//
//        Vector offset = new Vector(9.3, -9);
//        this.sprite = new SpriteInfo("Player_Mira3_idle1", offset, rotation, size);
//        this.sprite.setNext(new SpriteInfo("Player_Mira3_idle2", offset, rotation, size, this.sprite));
//        this.sprite.getNext().setNext(new SpriteInfo("Player_Mira3_idle3", offset, rotation, size, this.sprite));
//        this.sprite.getNext().getNext().setNext(new SpriteInfo("Player_Mira3_idle4", offset, rotation, size, this.sprite));
//////
//////
//////
//        offset = new Vector(9.4, -9);
//        this.sprite.getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_rightTurn1", offset, rotation, size, this.sprite));
//        this.sprite.getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_rightTurn2", offset, rotation, size, this.sprite));
//        offset = new Vector(10.8, -9);
//        this.sprite.getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_rightTurn3", offset, rotation, size, this.sprite));
//        offset = new Vector(11.8, -9);
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_right1", offset, rotation, size, this.sprite));
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_right2", offset, rotation, size, this.sprite));
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_right3", offset, rotation, size, this.sprite));
//        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_right4", offset, rotation, size, this.sprite));
//
//
////        offset = new Vector(8.5, -9);
////        this.sprite.getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_leftTurn1", offset, rotation, size, this.sprite));
////        this.sprite.getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_leftTurn2", offset, rotation, size, this.sprite));
////        offset = new Vector(6.8, -9);
////        this.sprite.getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_leftTurn3", offset, rotation, size, this.sprite));
////        offset = new Vector(7, -9);
////        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_left1", offset, rotation, size, this.sprite));
////        offset = new Vector(9, -9);
////        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_left2", offset, rotation, size, this.sprite));
////        offset = new Vector(7, -9);
////        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_left3", offset, rotation, size, this.sprite));
////        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_left4", offset, rotation, size, this.sprite));
//
////        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_rightTurn1", offset, rotation, size, this.sprite));
////        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_rightTurn2", offset, rotation, size, this.sprite));
////        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_rightTurn3", offset, rotation, size, this.sprite));
////        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_right1", offset, rotation, size, this.sprite));
////        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_right2", offset, rotation, size, this.sprite));
////        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_right3", offset, rotation, size, this.sprite));
////        this.sprite.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().setNext(new SpriteInfo("Player_Mira3_right4", offset, rotation, size, this.sprite));

    }

//    @Override
//    protected void updateSprite(){
//        if(this.tick % 30 == 0) {
//            this.sprite = this.sprite.getNext();
//        }
//    }
}
