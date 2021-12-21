import java.awt.*;
import java.util.ArrayList;

public class Enemy_Boss_Neo extends Enemy_Boss {

    private SpriteInfo left;
    private SpriteInfo right;
    private SpriteInfo attack;

    //0 = idle
    //1 = left
    //2 = right
    //3 = attack
    private int spritePos = 0;

    private Vector dbPos = new Vector(10, 0);
    public Enemy_Boss_Neo(){
        //super(null);
        super(constructBehaviors());
        setBehaviorOwnerToThis();
    }

    @Override
    public void update(){
        super.update();
        if(spritePos != 3) {
            double xv = velocity.getA();
            if (xv > 0.1) {
                changeSprite(2);
            } else if (xv < -0.1) {
                changeSprite(1);
            } else {
                changeSprite(0);
            }
        }
    }

    @Override
    protected void spawnAttack(){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();

        switch(attackName){
            case "defaultAttack1":
                attackDefault1(difficulty);
                break;
            case "defaultAttack2":
                attackDefault2(difficulty);
                break;
            case "defaultAttack3":
                attackDefault3(difficulty);
                break;
            //d4 only diff8
            case "defaultAttack4":
                attackDefault4();
                break;

            case "ctSpawn":
                attackCTSpawn(difficulty);
                break;
            case "cmSpawn":
                attackCMSpawn(difficulty);
                break;
            case "bsShot":
                attackBSShot(difficulty);
                break;
            case "bsLaser":
                attackBSLaser(difficulty);
                break;
            //s only on diff8
            case "sSpawn":
                attackSSpawn();
                break;
        }
    }
    //60, 60
    private void attackDefault1(int difficulty){
        if(attackTimer % (35-difficulty) == 0){
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            double numBullets = 20 + (3 * difficulty);
            double angleAdd = (double)360/numBullets;
            double currentAngle = baseAngle;
            for(int i = 0; i < numBullets; i++){
                toSpawn.add(new EP_Orb_Medium(this, new Vector(currentAngle, 150 + (15 * difficulty)), "red"));
                currentAngle += angleAdd;
            }
        }
        //1 orbit
        if(attackTimer == 60){
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            for(int i = 0; i <= difficulty/3; i++) {
                double angle = baseAngle + ((180 * Math.random()) - 90);
                EP_Orb_Large center = new EP_Orb_Large(this, new Vector(angle, 200), "red");
                EP_Orb_Medium orbital = new EP_Orb_Medium(this, new Vector(), "red");
                orbital.destroyOutsideBounds = false;
                orbital.newOrbitPath(new Path_Orbit(center, new Vector(60, 200), Math.random() * 360));
                toSpawn.add(center);
//                orbital.update();
                toSpawn.add(orbital);
            }
        }
    }
    private void attackDefault2(int difficulty){
        if(attackTimer % (35-difficulty) == 0){
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            double numBullets = 20 + (3 * difficulty);
            double angleAdd = (double)360/numBullets;
            double currentAngle = baseAngle;
            for(int i = 0; i < numBullets; i++){
                toSpawn.add(new EP_Orb_Medium(this, new Vector(currentAngle, 150 + (15 * difficulty)), "orange"));
                currentAngle += angleAdd;
            }
        }
        //2 orbit
        if(attackTimer == 60){
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            for(int i = 0; i <= difficulty/3; i++) {
                double angle = baseAngle + ((180 * Math.random()) - 90);
                EP_Orb_Large center = new EP_Orb_Large(this, new Vector(angle, 200), "orange");
                EP_Orb_Medium orbital = new EP_Orb_Medium(this, new Vector(), "orange");
                orbital.destroyOutsideBounds = false;
                orbital.newOrbitPath(new Path_Orbit(center, new Vector(60, 200), Math.random() * 360));
                EP_Orb_Small last = new EP_Orb_Small(this, new Vector(), "orange");
                last.destroyOutsideBounds = false;
                last.newOrbitPath(new Path_Orbit(orbital, new Vector(120, 50), Math.random() * 360));
                toSpawn.add(center);
//                orbital.update();
                toSpawn.add(orbital);
//                last.update();
                toSpawn.add(last);
            }
        }
    }
    private void attackDefault3(int difficulty){
        if(attackTimer % (35-difficulty) == 0){
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            double numBullets = 20 + (3 * difficulty);
            double angleAdd = (double)360/numBullets;
            double currentAngle = baseAngle;
            for(int i = 0; i < numBullets; i++){
                toSpawn.add(new EP_Orb_Medium(this, new Vector(currentAngle, 150 + (15 * difficulty)), "yellow"));
                currentAngle += angleAdd;
            }
        }
        //2 orbit
        if(attackTimer == 60){
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            for(int i = 0; i <= difficulty/3; i++) {
                double angle = baseAngle + ((180 * Math.random()) - 90);
                EP_Orb_Large center = new EP_Orb_Large(this, new Vector(angle, 200), "yellow");
                EP_Orb_Medium orbital = new EP_Orb_Medium(this, new Vector(), "yellow");
                orbital.destroyOutsideBounds = false;
                orbital.newOrbitPath(new Path_Orbit(center, new Vector(60, 200), Math.random() * 360));
                EP_Orb_Small last = new EP_Orb_Small(this, new Vector(), "yellow");
                last.destroyOutsideBounds = false;
                last.newOrbitPath(new Path_Orbit(orbital, new Vector(120, 50), Math.random() * 360));
                toSpawn.add(center);
                toSpawn.add(orbital);
                toSpawn.add(last);
            }
        }
        //rock shower
        if(attackTimer == 30){
            for(int i = 0; i < difficulty * 5; i++){
                toSpawn.add(new EP_Rock(this, new Vector(Math.random() * 360, 100 + (Math.random() * 100))));
            }
        }
    }
    private void attackDefault4(){
        if(attackTimer % 27 == 0){
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            double numBullets = 20 + (24);
            double angleAdd = (double)360/numBullets;
            double currentAngle = baseAngle;
            for(int i = 0; i < numBullets; i++){
                toSpawn.add(new EP_Orb_Medium(this, new Vector(currentAngle, 150 + (15 * 8)), "blue"));
                currentAngle += angleAdd;
            }
        }
        //2 orbit
        if(attackTimer == 60){
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            for(int i = 0; i <= 3; i++) {
                double angle = baseAngle + ((180 * Math.random()) - 90);
                EP_Orb_Large center = new EP_Orb_Large(this, new Vector(angle, 200), "blue");
                EP_Orb_Medium orbital = new EP_Orb_Medium(this, new Vector(), "blue");
                orbital.destroyOutsideBounds = false;
                orbital.newOrbitPath(new Path_Orbit(center, new Vector(60, 200), Math.random() * 360));
                EP_Orb_Small last = new EP_Orb_Small(this, new Vector(), "blue");
                last.destroyOutsideBounds = false;
                last.newOrbitPath(new Path_Orbit(orbital, new Vector(120, 50), Math.random() * 360));
                toSpawn.add(center);
                toSpawn.add(orbital);
                toSpawn.add(last);
            }
        }
        //rock shower
        if(attackTimer == 30){
            for(int i = 0; i < 40; i++){
                toSpawn.add(new EP_Rock(this, new Vector(Math.random() * 360, 100 + (Math.random() * 100))));
            }
        }
        //laser show
        if(attackTimer == 60){
            for(int i = 0; i < 5; i++){
                Vector spawnPos = Vector.add(this, dbPos);
                double baseAngle = Vector.getAngle(spawnPos, Player.getThePlayer());
                double angle = baseAngle + ((Math.random() * 90) - 45);
                toSpawn.add(new EP_Laser(spawnPos, new Vector(angle, 350), 100, Color.white));
            }
        }
    }

    //60, 60
    private void attackCTSpawn(int difficulty){
        changeSprite(3);
        if(attackTimer % (7 - (difficulty/2)) == 0) {
            for (int i = 0; i < 3; i++) {
                double angle = Math.random() * 360;
                Vector off = Vector.polarToVelocity(new Vector(angle, 100));
                Vector spawnPos = Vector.add(this, off);
                toSpawn.add(new EP_Orb_Small(spawnPos, new Vector(Math.random() * 360, 200 + (Math.random() * 100)), "white"));
            }
        }

        if(attackTimer == 60){
            for(int i = 0; i <= difficulty/3; i++) {
                toSpawn.add(new EP_Orb_Neo_CT(this, new Vector(Math.random() * 360, 500)));
            }
        }
    }

    //60, 60
    private void attackCMSpawn(int difficulty){
        changeSprite(3);
        if(attackTimer % (20 - difficulty) == 0){
            String color = null;
            double d = Math.random();
            if(d <= .25){
                color = "red";
            }
            else if(d <= .5){
                color = "orange";
            }
            else if(d <= .75){
                color = "yellow";
            }
            else{
                color = "blue";
            }
            EP_Orb_Neo_CM center = new EP_Orb_Neo_CM(this, new Vector((Math.random() * 180) - 90, 300), color);
            toSpawn.add(center);
            EP_Rock inner = new EP_Rock(this, new Path_Orbit(center, new Vector(150, 125), Math.random() * 360));
            toSpawn.add(inner);
            if(difficulty >= 3){
                toSpawn.add(new EP_Rock(this, new Path_Orbit(center, new Vector(100, 150), Math.random() * 360)));
            }
            if(difficulty >= 6){
                toSpawn.add(new EP_Rock(this, new Path_Orbit(center, new Vector(80, 175), Math.random() * 360)));
            }
            if(difficulty == 8){
                toSpawn.add(new EP_Rock(this, new Path_Orbit(center, new Vector(60, 200), Math.random() * 360)));
            }
        }
    }

    //60, 60
    private void attackBSShot(int difficulty){
        changeSprite(3);
        if(attackTimer % (10 - difficulty) == 0){
            for(int i = 0; i < 2; i++) {
                double baseAngle = Vector.getAngle(this, Player.getThePlayer());
                double angle = baseAngle + (Math.random() * 120) - 60;
                double speed = (difficulty * 75) + (Math.random() * 200);
                if (Math.random() > .25) {
                    toSpawn.add(new EP_Oval(this, new Vector(angle, speed), "white"));
                } else {
                    toSpawn.add(new EP_Orb_Medium(this, new Vector(angle, speed / 3), "white"));
                }
            }
        }
    }

    //go thrice
    //90, 90
    private void attackBSLaser(int difficulty) {
        changeSprite(3);
        //hits at 90, 60, and 30 (not 0)
        if (attackTimer % 30 == 0) {
            Vector spawnPos = Vector.add(this, dbPos);
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            double speed = 500;
            for (int i = 0; i < difficulty; i++) {
                double angle = baseAngle + (Math.random() * 60) - 30;
                toSpawn.add(new EP_Laser(spawnPos, new Vector(angle, speed), 150, Color.white));
            }
        }
    }

    //60, 60
    private void attackSSpawn(){
        changeSprite(3);
        //pulsars
        if(attackTimer == 60){
            double angle = (Math.random() * 45) + 45;
            if(Math.random() > .5){
                angle = 0 - angle;
            }

            toSpawn.add(new EP_Pulsar(this, new Vector(angle, 100 + (Math.random() * 50))));
        }
        //dual symmetric pattern
        if(attackTimer % 5 == 0){
            double fastSpeed = 800;
            double angle = attackTimer * 3;
            for(int i = 0; i < 3; i++){
                toSpawn.add(new EP_Oval(this, new Vector(angle, fastSpeed - (i * 200)), "white"));
                toSpawn.add(new EP_Oval(this, new Vector(0 - angle, fastSpeed - (i * 200)), "white"));
            }
        }
        //shotgun
        if(attackTimer == 1){
            for(int i = 0; i < 8; i++){
                double speed = 500 + (Math.random() * 50);
                double baseAngle = Vector.getAngle(this, Player.getThePlayer());
                double angle = baseAngle + (Math.random() * 10) - 5;
                toSpawn.add(new EP_Orb_Large(this, new Vector(angle, speed), "white"));
            }
        }
    }

    @Override
    protected void updateHP(double damage){
        if(!canBeDamaged || !exists)
            return;
        hp -= damage;
        if(hp < 0){
            //test for spell
            if(behavior instanceof Routine_Boss_Spell){
                Driver_GameLogic.getCurrentLogicDriver().spellOver();
                dropItems();
            }
            if(behavior != null && behavior.getNext() != null){
                clearSpawnBullets();
                Driver_GameLogic.getCurrentLogicDriver().bossClear(false);
                Behavior nextPhase = behavior.getNext();
                stop();
                stopAttack();
                canBeDamaged = false;

                ArrayList<Vector> p = new ArrayList<>();
                p.add(new Vector(Driver_Game.getWidth()/2, 200));
                Path_Polygon toMid = new Path_Polygon(p, false, .5*(Vector.getDistance(this, p.get(0))/*/Driver_Game.getUPS()*/));

                Instruction_ToggleCanBeDamaged toggle = new Instruction_ToggleCanBeDamaged(this, nextPhase, true);
                Instruction_Timer timer = new Instruction_Timer(this, toggle, 300);
                Instruction_StartPath sp = new Instruction_StartPath(this, timer, toMid);

                setNewBehavior(sp);
                spellName = "";
                //this part resets after each spell
                changeSprite(0);
            }
            else{
                exists = false;
                toSpawn.clear();
                spellName = "";
                Driver_GameLogic.getCurrentLogicDriver().bossClear(true);
                Driver_GameLogic.getCurrentLogicDriver().victory();
            }
        }
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 3;
        Vector offset = new Vector(0, -2);
        idle = new SpriteInfo("Boss_Neo_idle1", offset, rotation, size);
        idle.setNext(new SpriteInfo("Boss_Neo_idle2", offset, rotation, size,
                new SpriteInfo("Boss_Neo_idle3", offset, rotation, size,
                        new SpriteInfo("Boss_Neo_idle4", offset, rotation, size, idle))));

        offset = new Vector(2, -2);
        left = new SpriteInfo("Boss_Neo_left1", offset, rotation, size);
        left.setNext(new SpriteInfo("Boss_Neo_left2", offset, rotation, size,
                new SpriteInfo("Boss_Neo_left3", offset, rotation, size,
                        new SpriteInfo("Boss_Neo_left4", offset, rotation, size, left))));

        offset = new Vector(-1, -2);
        right = new SpriteInfo("Boss_Neo_right1", offset, rotation, size);
        right.setNext(new SpriteInfo("Boss_Neo_right2", offset, rotation, size,
                new SpriteInfo("Boss_Neo_right3", offset, rotation, size,
                        new SpriteInfo("Boss_Neo_right4", offset, rotation, size, right))));

        offset = new Vector(0, -2);
        attack = new SpriteInfo("Boss_Neo_attack1", offset, rotation, size);
        attack.setNext(new SpriteInfo("Boss_Neo_attack2", offset, rotation, size,
                new SpriteInfo("Boss_Neo_attack3", offset, rotation, size, attack)));

        this.sprite = idle;
    }

    @Override
    protected void updateSprite(){
        if(tick % 10 == 0)
            this.sprite = this.sprite.getNext();
    }

    private void changeSprite(int newPos){
        if(newPos == spritePos){
            return;
        }
        if(newPos == 0){
            this.sprite = idle;
            this.spritePos = 0;
            return;
        }
        if(newPos == 1){
            this.sprite = left;
            this.spritePos = 1;
            return;
        }
        if(newPos == 2){
            this.sprite = right;
            this.spritePos = 2;
            return;
        }
        if(newPos == 3){
            this.sprite = attack;
            this.spritePos = 3;
        }
    }

    //wrong parts: the path needs to be random each time, needs a pathOver instruction
    //first part is right
    private static Routine_Boss_Attack constructDefaults(int version){
        Instruction_Timer timer = new Instruction_Timer(null, null, 180);
        Instruction_WaitPathOver pathOver = new Instruction_WaitPathOver(null, timer);
        Instruction_StartPolygonPathRandom path = new Instruction_StartPolygonPathRandom(null, pathOver, 1, 100, Driver_Game.getWidth() - 100, 100, 300, 1.5);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, path, true);
        Instruction_Attack attack = new Instruction_Attack(null, toggle, "defaultAttack" + version, 60, 60);
        timer.setNext(path);

        double initHP;
        switch(version){
            default:
            case 1:
                initHP = 250;
                break;
            case 2:
                initHP = 260;
                break;
            case 3:
                initHP = 270;
                break;
            case 4:
                initHP = 280;
                break;
        }
        return new Routine_Boss_Attack(null, null, attack, initHP);
    }

    private static Routine_Boss_Spell constructCometTrail(){
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, null, true);
        Instruction_Attack attack = new Instruction_Attack(null, toggle, "ctSpawn", 60, 60);
        double initHP = 200;
        return new Routine_Boss_Spell(null, null, attack, initHP, "Wonder: Comet Trail", "life");
    }

    private static Routine_Boss_Spell constructCelestialModel(){
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, null, true);
        Instruction_Attack attack = new Instruction_Attack(null, toggle, "cmSpawn", 60, 60);
        double initHP = 150;
        return new Routine_Boss_Spell(null, null, attack, initHP, "Wonder: Celestial Model", 9);
    }

    private static Routine_Boss_Spell constructBurstSignal(){
        Instruction_Timer endTimer = new Instruction_Timer(null, null, 50);
        Instruction_WaitAttackOver attackOver2 = new Instruction_WaitAttackOver(null, endTimer);
        Instruction_Timer t2 = new Instruction_Timer(null, attackOver2, 1);
        Instruction_Attack laser = new Instruction_Attack(null, t2, "bsLaser", 90, 90);
        Instruction_WaitAttackOver attackOver1 = new Instruction_WaitAttackOver(null, laser);
        Instruction_Timer t1 = new Instruction_Timer(null, attackOver1, 1);
        Instruction_Attack shot = new Instruction_Attack(null, t1, "bsShot", 60, 60);
        endTimer.setNext(shot);
        double initHP = 150;
        return new Routine_Boss_Spell(null, null, shot, initHP, "Wonder: Burst Signal", 11);
    }

    private static Routine_Boss_Spell constructSingularity(){
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, null, true);
        Instruction_Attack attack = new Instruction_Attack(null, toggle, "sSpawn", 60, 60);
        double initHP = 300;
        return new Routine_Boss_Spell(null, null, attack, initHP, "Wonder: Singularity", 0);
    }

    private static Routine_Boss_Attack constructBehaviors(){
        Routine_Boss_Attack default1 = constructDefaults(1);

        Routine_Boss_Spell cometTrail = constructCometTrail();
        default1.setNext(cometTrail);

        Routine_Boss_Attack default2 = constructDefaults(2);
        cometTrail.setNext(default2);

        Routine_Boss_Spell celestialModel = constructCelestialModel();
        default2.setNext(celestialModel);

        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
        if(difficulty >= 4){
            Routine_Boss_Attack default3 = constructDefaults(3);
            celestialModel.setNext(default3);

            Routine_Boss_Spell burstSignal = constructBurstSignal();
            default3.setNext(burstSignal);

            if(difficulty == 8){
                Routine_Boss_Attack default4 = constructDefaults(4);
                burstSignal.setNext(default4);

                Routine_Boss_Spell singularity = constructSingularity();
                default4.setNext(singularity);
            }
        }

        return default1;
    }
}
