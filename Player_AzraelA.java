public class Player_AzraelA extends Player {

    private Vector topL = Vector.polarToVelocity(new Vector(30 + (60*3), 50));
    private Vector topR = Vector.polarToVelocity(new Vector(30 + (60*2), 50));
    private Vector midL = Vector.polarToVelocity(new Vector(30 + (60*4), 50));
    private Vector midR = Vector.polarToVelocity(new Vector(30 + (60*1), 50));
    private Vector botL = Vector.polarToVelocity(new Vector(30 + (60*5), 50));
    private Vector botR = Vector.polarToVelocity(new Vector(30 + (60*0), 50));
    private Bullet_Azr_Halo[] halo = new Bullet_Azr_Halo[4];


    public Player_AzraelA(int hp, int bombs, int power){
        super(4, new Hitbox_Circle(), 60, hp, bombs, power);
        trueHitbox.setOwner(this);
        AABB.setOwner(this);
        twoFrameHitbox.setOwner(this);
        baseSpeed = 750;
        baseFocusSpeed = baseSpeed / 3;
        halo[0] = new Bullet_Azr_Halo(Vector.add(this, Vector.polarToVelocity(new Vector(0, 100))), this, new Vector(180, 100));
        halo[1] = new Bullet_Azr_Halo(Vector.add(this, Vector.polarToVelocity(new Vector(90, 100))), this, new Vector(180, 100));
        halo[2] = new Bullet_Azr_Halo(Vector.add(this, Vector.polarToVelocity(new Vector(180, 100))), this, new Vector(180, 100));
        halo[3] = new Bullet_Azr_Halo(Vector.add(this, Vector.polarToVelocity(new Vector(270, 100))), this, new Vector(180, 100));

        if(power >= 50) {
            toSpawn.add(halo[0]);
            toSpawn.add(halo[1]);
            toSpawn.add(halo[2]);
            toSpawn.add(halo[3]);
        }
    }

    @Override
    public void update(){
        if(power < 50){
            halo[0].setExists(false);
            halo[1].setExists(false);
            halo[2].setExists(false);
            halo[3].setExists(false);
            halo[0].update();
            halo[1].update();
            halo[2].update();
            halo[3].update();
        }
        else if(!halo[0].getExists()){
            halo[0].setExists(true);
            halo[1].setExists(true);
            halo[2].setExists(true);
            halo[3].setExists(true);
            toSpawn.add(halo[0]);
            toSpawn.add(halo[1]);
            toSpawn.add(halo[2]);
            toSpawn.add(halo[3]);
        }
        if(focused && !halo[0].getFocused()){
            halo[0].stopMovement();
            halo[1].stopMovement();
            halo[2].stopMovement();
            halo[3].stopMovement();
        }
        else if(!focused && halo[0].getFocused()){
            halo[0].startMovement();
            halo[1].startMovement();
            halo[2].startMovement();
            halo[3].startMovement();
        }

        super.update();
    }

    @Override
    protected void spawnAttack() {
        //generate different attacks based on Power, start from highest so that in late game with more bullets shorter retrieval
        if (attackName.equals("default")) {

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
        if(attackTimer % 15 == 0){
            halo[0].spawnProjectiles();
        }
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
        if(attackTimer % 15 == 0){
            halo[0].spawnProjectiles();
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
        if(attackTimer % 15 == 0){
            halo[0].spawnProjectiles();
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
        if(attackTimer % 15 == 0){
            halo[0].spawnProjectiles();
            halo[1].spawnProjectiles();
        }
    }
    private void attack10() {
        if(attackTimer % 15 == 0){
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topL), new Vector(189.5, 2500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topR), new Vector(170.5, 2500), 1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midL), new Vector(193, 2000), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midR), new Vector(167, 2000), 1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, botL), new Vector(205, 1500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, botR), new Vector(155, 1500), 1));
        }
        if(attackTimer % 15 == 0){
            halo[0].spawnProjectiles();
            halo[1].spawnProjectiles();
            halo[2].spawnProjectiles();
        }
    }
    private void attack11() {
        //5 per source = 30 dps
        if(attackTimer % 12 == 0){
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topL), new Vector(189.5, 2500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, topR), new Vector(170.5, 2500), 1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midL), new Vector(193, 2000), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, midR), new Vector(167, 2000), 1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, botL), new Vector(205, 1500), -1));
            toSpawn.add(new Bullet_Azr_Default(Vector.add(this, botR), new Vector(155, 1500), 1));
        }
        if(attackTimer % 15 == 0){
            halo[0].spawnProjectiles();
            halo[1].spawnProjectiles();
            halo[2].spawnProjectiles();
            halo[3].spawnProjectiles();
        }
    }

    @Override
    protected void spawnBomb(){
        Bomb_Azr_Scythe[] scythes = new Bomb_Azr_Scythe[8];
        for(int i = 0; i < scythes.length; i++){
            scythes[i] = new Bomb_Azr_Scythe(this, Vector.polarToVelocity(new Vector(i * 360 / 8, 175)) );
            scythes[i].setLaser(new Bomb_Azr_Scythe_Laser(scythes[i]));
            toSpawn.add(scythes[i]);
            toSpawn.add(scythes[i].getLaser());
        }
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
////        double rotation = 0;
//        double size = 4;
////        Vector offset = new Vector(.4, -7);
////        this.sprite = new SpriteInfo("Player_Azrael_idle", offset, rotation, size);
//        size = 3.9;
////        offset = new Vector(1, -7);
////        this.sprite.setNext(new SpriteInfo("Player_Azrael_rightTurn1", offset, rotation, size));
////        offset = new Vector(1.6, -6.8);
//        size = 3.9;
////        this.sprite.getNext().setNext(new SpriteInfo("Player_Azrael_rightTurn2", offset, rotation, size));
////        offset = new Vector(1.4, -7.4);
//        size = 3.9;
////        this.sprite.getNext().getNext().setNext(new SpriteInfo("Player_Azrael_rightTurn3", offset, rotation, size));
////        offset = new Vector(1.6, -7.4);
//        size = 3.8;
//        this.sprite.getNext().getNext().getNext().setNext(new SpriteInfo("Player_Azrael_right", offset, rotation, size, this.sprite));


//        double rotation = 0;
//        double size = 4;
//        Vector offset = new Vector(.4, -7);
//        this.sprite = new SpriteInfo("Player_Azrael_idle", offset, rotation, size);
//        size = 3.9;
//        offset = new Vector(-.2, -7);
//        this.sprite.setNext(new SpriteInfo("Player_Azrael_leftTurn1", offset, rotation, size));
//        offset = new Vector(-.8, -6.8);
//        size = 3.9;
//        this.sprite.getNext().setNext(new SpriteInfo("Player_Azrael_leftTurn2", offset, rotation, size));
//        offset = new Vector(-.6, -7.4);
//        size = 3.9;
//        this.sprite.getNext().getNext().setNext(new SpriteInfo("Player_Azrael_leftTurn3", offset, rotation, size));
//        offset = new Vector(-1.1, -7.4);
//        size = 3.8;
//        this.sprite.getNext().getNext().getNext().setNext(new SpriteInfo("Player_Azrael_left", offset, rotation, size, this.sprite));
        this.sprite = idle;
    }

//    @Override
//    protected void updateSprite(){
//        if(tick % 20 == 0)
//            this.sprite = this.sprite.getNext();
//    }
}
