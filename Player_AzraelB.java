public class Player_AzraelB extends Player {
private Vector leftHandPos = new Vector(-10, 22);
    private Vector rightHandPos = new Vector(10, 22);
    private Bullet_Azr_Laser[][] lasers = new Bullet_Azr_Laser[4][2];

    private Vector topL = Vector.polarToVelocity(new Vector(30 + (60*3), 50));
    private Vector topR = Vector.polarToVelocity(new Vector(30 + (60*2), 50));
    private Vector midL = Vector.polarToVelocity(new Vector(30 + (60*4), 50));
    private Vector midR = Vector.polarToVelocity(new Vector(30 + (60*1), 50));
    private Vector botL = Vector.polarToVelocity(new Vector(30 + (60*5), 50));
    private Vector botR = Vector.polarToVelocity(new Vector(30 + (60*0), 50));

    public Player_AzraelB(int hp, int bombs, int power) {
        super(4, new Hitbox_Circle(), 60, hp, bombs, power);
        trueHitbox.setOwner(this);
        AABB.setOwner(this);
        twoFrameHitbox.setOwner(this);
        baseSpeed = 750;
        baseFocusSpeed = baseSpeed / 3;
        for (int i = 0; i < 4; i++) {
            lasers[i][0] = new Bullet_Azr_Laser(this, leftHandPos);
            lasers[i][1] = new Bullet_Azr_Laser(this, rightHandPos);
            lasers[i][0].setExists(false);
            lasers[i][1].setExists(false);
        }
    }

    @Override
    public void update() {
        super.update();
        leftHandPos.setA(-10 - spritePos * 1.5);
        rightHandPos.setA(10 - spritePos * 1.5);
        //REMOVING THIS and instead rewriting the laser's movement and update

//        //after this update all the lasers have the correct target and velocity
//        //but they may have the wrong base positions
//        //so here we manually update each of their positions
//
//        //the player has already moved to the promised position,
//        //and laser's update method does not call the entity update method,
//        //therefore it will not mess up no matter when we call it


        //this is needed to prevent first frame jumping. The lasers can be updated any number of times because they always
        //set themselves to their correct position.

        //DISREGARD THE ABOVE it's nonsense
        for (int i = 0; i < 4; i++) {
            lasers[i][0].update();
            lasers[i][1].update();
        }
    }

    //in the attack set the targets of the lasers
    @Override
    protected void spawnAttack() {
        //generate different attacks based on Power, start from highest so that in late game with more bullets shorter retrieval
        if (attackName.equals("default")) {

            spawnLasers();
            //this block finds the target.

            Vector[] targets = new Vector[4];
            //a for-each loop doesn't change the reference from the array
            for (int i = 0; i < targets.length; i++) {
                targets[i] = new Vector(this.getA(), this.getB() - 400);
            }

            //if not focused lasers = homing, if focused lasers go to b-350 as above
            if (!focused) {
                for (Enemy e : Driver_GameLogic.getCurrentLogicDriver().getEnemies()) {
                    if (e.canBeDamaged) {
                        double distance = Vector.getDistance(this, e);
                        //if this is enemy 1
                        if (!(targets[0] instanceof Enemy)) {
                            targets[0] = e;
                            targets[1] = e;
                            targets[2] = e;
                            targets[3] = e;
                        }
                        //if this is enemy 2
                        else if (targets[2] == targets[3]) {
                            //if the second enemy is the closest one
                            if (distance < Vector.getDistance(this, targets[0])) {
                                //set the first 3 lasers to the second enemy, leaving the last laser on the first
                                targets[0] = e;
                                targets[1] = e;
                                targets[2] = e;
                            }
                            //otherwise set the last laser to this one
                            else
                                targets[3] = e;
                        }
                        //if this is enemy 3
                        else if (targets[1] == targets[2]) {
                            //if the third enemy is the closest one
                            if (distance < Vector.getDistance(this, targets[0])) {
                                //set the first 2 targets to the third enemy
                                targets[0] = e;
                                targets[1] = e;
                            }
                            //else if the third enemy is closer than the previously second closest
                            //which would always be stored in the fourth target
                            else if (distance < Vector.getDistance(this, targets[3])) {
                                //store in the third target
                                targets[2] = e;
                            }
                            //else if this is the furthest of the 3
                            else {
                                //move the previously furthest one up to the second to last one, and place the last one
                                targets[2] = targets[3];
                                targets[3] = e;
                            }
                        }
                        //if this is enemy 4
                        else if (targets[0] == targets[1]) {
                            //if the fourth enemy is the closest one
                            if (distance < Vector.getDistance(this, targets[0])) {
                                //set the first target to this one
                                targets[0] = e;
                            }
                            //else if the fourth enemy is closer than the previously second closest
                            //which would always be stored in the third target
                            else if (distance < Vector.getDistance(this, targets[2])) {
                                //set the second target to this one
                                targets[1] = e;
                            }
                            //else if the foutth enemy is closer than the previously third closest
                            //which would always be stored in the fourth target
                            else if (distance < Vector.getDistance(this, targets[3])) {
                                //set second target to the third target
                                //set the third target to this
                                targets[1] = targets[2];
                                targets[2] = e;
                            }
                            //this is the furthest of the first 4
                            else {
                                //shift 2 from 3, 3 from 4, set 4
                                targets[1] = targets[2];
                                targets[2] = targets[3];
                                targets[3] = e;
                            }
                        }
                        //if this is an enemy above 4
                        else {
                            if (distance < Vector.getDistance(this, targets[3])) {
                                if (distance < Vector.getDistance(this, targets[2])) {
                                    if (distance < Vector.getDistance(this, targets[1])) {
                                        if (distance < Vector.getDistance(this, targets[0])) {
                                            //if this is the closest
                                            targets[3] = targets[2];
                                            targets[2] = targets[1];
                                            targets[1] = targets[0];
                                            targets[0] = e;
                                            continue;
                                        }
                                        //if this is the second closest
                                        targets[3] = targets[2];
                                        targets[2] = targets[1];
                                        targets[1] = e;
                                        continue;
                                    }
                                    //if this is the third closest
                                    targets[3] = targets[2];
                                    targets[2] = e;
                                    continue;
                                }
                                //if this is the fourth closest
                                targets[3] = e;
                            }

                        }
                    }
                }
            }

            //attacking part
            //set target every tick
            //set each to the closest
            for (int i = 0; i < 4; i++) {
                lasers[i][0].setTarget(targets[0]);
                lasers[i][1].setTarget(targets[0]);
            }
            boolean a = targets[0] == targets[1];
            boolean b = targets[0] == targets[2];
            boolean c = targets[0] == targets[3];
            if(!c & b){
                //there are 2 targets
                lasers[1][0].setTarget(targets[3]);
                lasers[1][1].setTarget(targets[3]);
            }
            else if(!b & a){
                //there are 3 targets
                lasers[1][0].setTarget(targets[2]);
                lasers[1][1].setTarget(targets[2]);
                lasers[2][0].setTarget(targets[3]);
                lasers[2][1].setTarget(targets[3]);
            }
            else if(!a){
                //there are 4 targets
                lasers[1][0].setTarget(targets[1]);
                lasers[1][1].setTarget(targets[1]);
                lasers[2][0].setTarget(targets[2]);
                lasers[2][1].setTarget(targets[2]);
                lasers[3][0].setTarget(targets[3]);
                lasers[3][1].setTarget(targets[3]);
            }

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
        }
    }

    private void attack1() {
        if(attackTimer == 60) {
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topL), new Vector(189.5, 2500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topR), new Vector(170.5, 2500), 1));
        }
    }
    private void attack2() {
        if(attackTimer % 30 == 0) {
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topL), new Vector(189.5, 2500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topR), new Vector(170.5, 2500), 1));
        }
    }
    private void attack3() {
        if(attackTimer % 30 == 0){
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topL), new Vector(189.5, 2500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topR), new Vector(170.5, 2500), 1));
        }
        if(attackTimer == 60){
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midL), new Vector(193, 2000), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midR), new Vector(167, 2000), 1));
        }
    }
    private void attack4() {
        if(attackTimer % 30 == 0){
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topL), new Vector(189.5, 2500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topR), new Vector(170.5, 2500), 1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midL), new Vector(193, 2000), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midR), new Vector(167, 2000), 1));
        }
    }
    private void attack5() {
        if(attackTimer % 30 == 0){
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topL), new Vector(189.5, 2500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topR), new Vector(170.5, 2500), 1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midL), new Vector(193, 2000), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midR), new Vector(167, 2000), 1));
        }
        if(attackTimer == 60){
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, botL), new Vector(205, 1500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, botR), new Vector(155, 1500), 1));
        }
    }
    private void attack6() {
        attack5();
    }
    private void attack7() {
        if(attackTimer % 30 == 0){
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topL), new Vector(189.5, 2500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topR), new Vector(170.5, 2500), 1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midL), new Vector(193, 2000), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midR), new Vector(167, 2000), 1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, botL), new Vector(205, 1500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, botR), new Vector(155, 1500), 1));
        }
    }
    private void attack8() {
        //3 per source
        if(attackTimer % 20 == 0){
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topL), new Vector(189.5, 2500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topR), new Vector(170.5, 2500), 1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midL), new Vector(193, 2000), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midR), new Vector(167, 2000), 1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, botL), new Vector(205, 1500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, botR), new Vector(155, 1500), 1));
        }
    }
    private void attack9() {
        //4 per source
        if(attackTimer % 15 == 0){
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topL), new Vector(189.5, 2500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topR), new Vector(170.5, 2500), 1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midL), new Vector(193, 2000), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midR), new Vector(167, 2000), 1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, botL), new Vector(205, 1500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, botR), new Vector(155, 1500), 1));
        }
    }
    private void attack10() {
        attack9();
    }
    private void attack11() {
        //5 per source = 30 dps
        if(attackTimer % 12 == 0){
            //System.out.println("Volley " + tick);
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topL), new Vector(189.5, 2500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topR), new Vector(170.5, 2500), 1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midL), new Vector(193, 2000), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midR), new Vector(167, 2000), 1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, botL), new Vector(205, 1500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, botR), new Vector(155, 1500), 1));
        }
    }

    private void spawnLasers() {
        if (power >= 50) {
            //if the attack is starting or the laser does not currently exist
            if(attackTimer == 60 && !lasers[0][0].getExists()) {
                toSpawn.add(lasers[0][0]);
                toSpawn.add(lasers[0][1]);
            }
            else if (!lasers[0][0].getExists()) {
                toSpawn.add(lasers[0][0]);
                toSpawn.add(lasers[0][1]);
            }
            lasers[0][0].setExists(true);
            lasers[0][1].setExists(true);
        }
        if (power >= 100) {
            if(attackTimer == 60 && !lasers[1][0].getExists()) {
                toSpawn.add(lasers[1][0]);
                toSpawn.add(lasers[1][1]);
            }
            else if (!lasers[1][0].getExists()) {
                toSpawn.add(lasers[1][0]);
                toSpawn.add(lasers[1][1]);
            }
            lasers[1][0].setExists(true);
            lasers[1][1].setExists(true);
        }
        if (power >= 150) {
            if(attackTimer == 60 && !lasers[2][0].getExists()) {
                toSpawn.add(lasers[2][0]);
                toSpawn.add(lasers[2][1]);
            }
            else if (!lasers[2][0].getExists()) {
                toSpawn.add(lasers[2][0]);
                toSpawn.add(lasers[2][1]);
            }
            lasers[2][0].setExists(true);
            lasers[2][1].setExists(true);
        }
        if (power >= 200) {
            if(attackTimer == 60 && !lasers[3][0].getExists()) {
                toSpawn.add(lasers[3][0]);
                toSpawn.add(lasers[3][1]);
            }
            else if (!lasers[3][0].getExists()) {
                toSpawn.add(lasers[3][0]);
                toSpawn.add(lasers[3][1]);
            }
            lasers[3][0].setExists(true);
            lasers[3][1].setExists(true);
        }

        if (attackTimer == 1 && !continuousAttack) {
            for (int i = 0; i < 4; i++) {
                lasers[i][0].setExists(false);
                lasers[i][1].setExists(false);
            }
        }
    }

    @Override
    protected void spawnBomb(){
        toSpawn.add(new Bomb_Azr_BigLaser(this));
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        Vector offsetIdle = new Vector(.4, -7);

        Vector offsetLT1 = new Vector(-.2, -7);
        Vector offsetLT2 = new Vector(-.8, -6.8);
        Vector offsetLT3 = new Vector(-.6, -7.4);
        Vector offsetL = new Vector(-1.1, -7.4);

        Vector offsetRT1 = new Vector(1, -7);
        Vector offsetRT2 = new Vector(1.6, -6.8);
        Vector offsetRT3 = new Vector(1.4, -7.4);
        Vector offsetR = new Vector(1.6, -7.4);

        idle = new SpriteInfo("Player_Azrael_idle", offsetIdle, rotation, 3);

        leftTurn1 = new SpriteInfo("Player_Azrael_leftTurn1", offsetLT1, rotation, 2.925);
        leftTurn2 = new SpriteInfo("Player_Azrael_leftTurn2", offsetLT2, rotation, 2.925);
        leftTurn3 = new SpriteInfo("Player_Azrael_leftTurn3", offsetLT3, rotation, 2.925);
        left = new SpriteInfo("Player_Azrael_left", offsetL, rotation, 2.85);

        rightTurn1 = new SpriteInfo("Player_Azrael_rightTurn1", offsetRT1, rotation, 2.925);
        rightTurn2 = new SpriteInfo("Player_Azrael_rightTurn2", offsetRT2, rotation, 2.925);
        rightTurn3 = new SpriteInfo("Player_Azrael_rightTurn3", offsetRT3, rotation, 2.925);
        right = new SpriteInfo("Player_Azrael_right", offsetR, rotation, 2.85);

        this.sprite = idle;
    }
}


