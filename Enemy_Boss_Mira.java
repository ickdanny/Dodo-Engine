import java.awt.*;

public class Enemy_Boss_Mira extends Enemy_Boss {

    private SpriteInfo idle2;
    ;
    public Enemy_Boss_Mira(){
//        super(null);
//        super(constructDefaults(4));
//        super(constructWalkOfTime());
//        super(constructReturnToOrigin());
//        super(constructSuffocatingAir());
//        super(constructUtterNothingness());
        super(constructBehaviors());
        setBehaviorOwnerToThis();
//        if(this.behavior instanceof Routine){
//            ((Routine)this.behavior).setOwner(this);
//        }
//        else if(this.behavior instanceof Instruction)
//        this.getLastBehavior().setNext(new Instruction_SetHP(this, null, 1000));
    }

//    @Override
//    protected void move(){
//        System.out.println("start of move");
//        AABB = trueHitbox.getBoundingAABB();
//        previousPosition.setToThis(this);
//        addToThis(Vector.scalarMultiple(velocity, ((double)1)/ Driver_Game.getUPS()));
//        twoFrameHitbox = AABB.twoFrameAABB(previousPosition);
//        System.out.println("updating b/p");
//        updateBehavior();
//        System.out.println("after b");
//        updatePath();
//        System.out.println("after b/p");
//        if(polar.getA() < 0){
//            polar.addToThis(new Vector(360, 0));
//        }
//        baseMovementBehavior();
//    }

//    @Override
//    protected void updateBehavior(){
//        System.out.println(this.behavior);
//        super.updateBehavior();
//    }
//    public void update(){
//        //move to promised position
//        System.out.println("start of update");
//        move();
//        System.out.println("moved");
//        //maybe spawn some things
//        spawn();
//        //tick
//        tick++;
//        //update the sprite AFTER changing the tick
//        updateSprite();
//        //hasUpdated = true
//        hasUpdated = true;
//    }

    @Override
    protected void spawnAttack(){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
        //laser will be handled by behaviors
        if(attackName.contains("defaultAttack")){
            int version = Integer.parseInt("" +attackName.charAt(attackName.length() - 1));
            attackDefault(difficulty, version);
            return;
        }

        switch(attackName){
            case "wotSpray":
                attackWOTSpray(difficulty);
                break;
            case "rtoSpiral":
                attackRTOSpiral(difficulty);
                break;
            case "saSpawn":
                attackSASpawn(difficulty);
                break;
            //SP only on difficulty 8
            case "spShards":
                attackSPShards();
                break;
            case "spBig":
                attackSPBig();
                break;
            case "spRandom":
                attackSPRandom();
                break;
            case "spLasers":
                attackSPLasers();
        }
    }

    //attackTimer == 60;
    private void attackDefault(int difficulty, int version){
        if(version <= 0 || version > 4){
            System.out.println("Mira attackDefault invalid version (must be 1-4)");
            System.exit(0);
        }
        if(attackTimer == 60){
            defaultOrbs(difficulty);
        }

        //top only
        if(version == 1){
            if(attackTimer % (10-difficulty) == 0){
                toSpawn.add(new EP_IceShard_TimeRelation(getRandomEdgeVector("top"), new Vector(0,50 + (Math.random() * 250))));
            }
        }
        //top and bottom
        else if(version == 2){
            if(attackTimer % (10-difficulty) == 0){
                double discriminator = Math.random();
                if(discriminator < .5){
                    toSpawn.add(new EP_IceShard_TimeRelation(getRandomEdgeVector("top"), new Vector(0,50 + (Math.random() * 250))));
                }
                else{
                    toSpawn.add(new EP_IceShard_TimeRelation(getRandomEdgeVector("bottom"), new Vector(180,50 + (Math.random() * 250))));
                }
            }
        }
        //four sides
        else if(version == 3){
            if(attackTimer % (10-difficulty) == 0){
                double discriminator = Math.random();
                if(discriminator < .25){
                    toSpawn.add(new EP_IceShard_TimeRelation(getRandomEdgeVector("top"), new Vector(0,50 + (Math.random() * 250))));
                }
                else if(discriminator < .5){
                    toSpawn.add(new EP_IceShard_TimeRelation(getRandomEdgeVector("bottom"), new Vector(180,50 + (Math.random() * 250))));
                }
                else if(discriminator < .75){
                    toSpawn.add(new EP_IceShard_TimeRelation(getRandomEdgeVector("left"), new Vector(90,50 + (Math.random() * 250))));
                }
                else{
                    toSpawn.add(new EP_IceShard_TimeRelation(getRandomEdgeVector("right"), new Vector(270,50 + (Math.random() * 250))));
                }
            }
        }
        //8 sides
        else{
            if(attackTimer % (10-difficulty) == 0){
                double discriminator = Math.random();
                if(discriminator < .125){
                    toSpawn.add(new EP_IceShard_TimeRelation(getRandomEdgeVector("top"), new Vector(0,50 + (Math.random() * 250))));
                }
                else if(discriminator < .25){
                    toSpawn.add(new EP_IceShard_TimeRelation(getRandomEdgeVector("bottom"), new Vector(180,50 + (Math.random() * 250))));
                }
                else if(discriminator < .375){
                    toSpawn.add(new EP_IceShard_TimeRelation(getRandomEdgeVector("left"), new Vector(90,50 + (Math.random() * 250))));
                }
                else if(discriminator < .5){
                    toSpawn.add(new EP_IceShard_TimeRelation(getRandomEdgeVector("right"), new Vector(270,50 + (Math.random() * 250))));
                }
                else if(discriminator < .625){
                    toSpawn.add(new EP_IceShard(getRandomEdgeVector("topLeft"), new Vector(45,50 + (Math.random() * 250))));
                }
                else if(discriminator < .75){
                    toSpawn.add(new EP_IceShard(getRandomEdgeVector("topRight"), new Vector(-45,50 + (Math.random() * 250))));
                }
                else if(discriminator < .875){
                    toSpawn.add(new EP_IceShard(getRandomEdgeVector("bottomLeft"), new Vector(135,50 + (Math.random() * 250))));
                }
                else{
                    toSpawn.add(new EP_IceShard(getRandomEdgeVector("bottomRight"), new Vector(-135,50 + (Math.random() * 250))));
                }
            }
        }
    }
    private void defaultOrbs(int difficulty){
        double angle = Vector.getAngle(this, Player.getThePlayer());
        switch(difficulty){
            case 1:
                toSpawn.add(new EP_Orb_Medium(this, new Vector(angle, 100), "blue"));
                break;
            case 2:
                toSpawn.add(new EP_Orb_Medium(this, new Vector(angle, 120), "blue"));
                break;
            case 3:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Medium(this, new Vector((i * 30) + angle, 120), "blue"));
                }
                break;
            case 4:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Medium(this, new Vector((i * 30) + angle, 140), "blue"));
                }
                break;
            case 5:
                for(int i = -1; i < 2; i++){
                    toSpawn.add(new EP_Orb_Medium(this, new Vector((i * 30) + angle, 150), "blue"));
                }
                break;
            case 6:
                for(int i = -2; i < 3; i++){
                    toSpawn.add(new EP_Orb_Medium(this, new Vector((i * 30) + angle, 150), "blue"));
                }
                break;
            case 7:
                for(int i = -2; i < 3; i++){
                    toSpawn.add(new EP_Orb_Medium(this, new Vector((i * 30) + angle, 170), "blue"));
                }
                break;
            case 8:
                for(int i = -3; i < 4; i++){
                    toSpawn.add(new EP_Orb_Medium(this, new Vector((i * 30) + angle, 170), "blue"));
                }
                break;
        }
    }

    //attackTimer == 300
    //spray for 5 seconds
    //other behaviors handled elsewhere
    //must not be continuousAttack
    private void attackWOTSpray(int difficulty){
        if(attackTimer % (10-difficulty) == 0){
            toSpawn.add(new EP_Orb_Mira_WOT(this, new Vector(Math.random() * 360, (Math.random() * 300) + 300)));
        }
    }

    //attackTimer == 180
    //spiral for 3 seconds
    //other behaivors handled elsewhere
    //must not be continuousAttack
    private void attackRTOSpiral(int difficulty){
        if(attackTimer % (10-difficulty) == 0){
            double angle1 = (tick * 7) % 360;
            double angle2 = (angle1 + 120) % 360;
            double angle3 = (angle2 + 120) % 360;
            toSpawn.add(new EP_Orb_Mira_RTO(this, new Vector(angle1, 200 + (difficulty * 50))));
            toSpawn.add(new EP_Orb_Mira_RTO(this, new Vector(angle2, 200 + (difficulty * 50))));
            toSpawn.add(new EP_Orb_Mira_RTO(this, new Vector(angle3, 200 + (difficulty * 50))));
        }
    }

    //attackTimer == 60
    private void attackSASpawn(int difficulty){
        if(difficulty >= 6 && attackTimer == 60){
            double angle = Vector.getAngle(this, Player.getThePlayer());
            int effectiveDifficulty = difficulty - 6;
            toSpawn.add(new EP_Orb_Small(this, new Vector(angle - 50, 300 - (effectiveDifficulty * 25)), "white"));
            toSpawn.add(new EP_Orb_Small(this, new Vector(angle + 50, 300 - (effectiveDifficulty * 25)), "white"));
            toSpawn.add(new EP_Orb_Medium(this, new Vector(angle - 25, 250 - (effectiveDifficulty * 25)), "lightBlue"));
            toSpawn.add(new EP_Orb_Medium(this, new Vector(angle + 25, 250 - (effectiveDifficulty * 25)), "lightBlue"));
            toSpawn.add(new EP_Orb_Large(this, new Vector(angle, 200 - (effectiveDifficulty * 25)), "blue"));
        }

        if(attackTimer == 60){
            SAEncirclement(difficulty);
        }
//        else if(difficulty >= 7 && attackTimer % 30 == 0){
//            SAEncirclement(difficulty);
//        }
//        else if(difficulty >= 8 && attackTimer % 15 == 0){
//            SAEncirclement(difficulty);
//        }
    }
    private void SAEncirclement(int difficulty){
        double speed = 320 - (difficulty * 15);
        Vector spawnTowards = Player.getThePlayer();
        double initDistance = 500 - (10 * difficulty);
        double initAngleRelation = Vector.getAngle(spawnTowards, new Vector(Driver_Game.getWidth()/2, Driver_Game.getHeight()/2));
//        System.out.println(initAngleRelation);
        for(int i = 1; i < 12; i++){
            Vector initPos = Vector.add(spawnTowards, Vector.polarToVelocity(new Vector(initAngleRelation + (i * ((double)360/12)), initDistance)));
            EP_Orb_Small toAdd = new EP_Orb_Small(initPos, new Vector(Vector.getAngle(initPos, spawnTowards), speed), "white");
            toAdd.destroyOutsideBounds = false;
            Instruction_ToggleDestroyOutsideBounds toggle = new Instruction_ToggleDestroyOutsideBounds(toAdd, true);
            Instruction_Timer timer = new Instruction_Timer(toAdd, toggle, 300);
            toAdd.setNewBehavior(timer);
            toSpawn.add(toAdd);
        }
    }

    //any large attackTimer will work
    private void attackSPShards(){
        if(attackTimer % 2 == 0){
            double projectileAngle = Vector.getAngle(this, Player.getThePlayer());
            double offsetAngle = Math.random() * 360;
            double offsetDistance = Math.random() * 400;
            Vector offset = Vector.polarToVelocity(new Vector(offsetAngle, offsetDistance));
            Vector spawnPos = Vector.add(this, offset);
            double speed = (Math.random() * 300) + 300;
            EP_IceShard_TimeRelation toAdd = new EP_IceShard_TimeRelation(spawnPos, new Vector(projectileAngle, speed));
            toAdd.destroyOutsideBounds = false;
            toSpawn.add(toAdd);
        }
    }

    //this must only fire once
    private void attackSPBig(){
        double angle = Vector.getAngle(this, Player.getThePlayer());
        for(int i = -3; i < 4; i++){
            toSpawn.add(new EP_Orb_Large(this, new Vector((i * 18) + angle, 350), "grey"));
        }
    }

    //any large attackTimer will work here pretty much
    private void attackSPRandom(){
        toSpawn.add(new EP_Orb_Small(this, new Vector((Math.random() * 360), (Math.random() * 200) + 200), "grey"));
        if(attackTimer % 2 == 0){
            toSpawn.add(new EP_Orb_Medium(this, new Vector((Math.random() * 360), (Math.random() * 150) + 150), "grey"));
        }
    }

    //this must only fire once
    private void attackSPLasers(){
        double angle = Vector.getAngle(this, Player.getThePlayer());
        for(int i = 0; i < 20; i++){
            toSpawn.add(new EP_Laser(this, new Vector(angle + (i * (360/20)), 600), 100, Color.red.brighter()));
        }
    }

    //0 == center/no moving
    //1 ==-> right
    //2 ==-> left
    public void attackDefaultLaser(int type){
        switch(type){
            case 0:
                toSpawn.add(new EP_Laser_Mira_SP(new Vector(Driver_Game.getWidth()/2, 0), new Vector()));
                break;
            case 1:
                toSpawn.add(new EP_Laser_Mira_SP(new Vector(Driver_Game.getWidth()/2, 0), new Vector(90, 30)));
                break;
            case 2:
                toSpawn.add(new EP_Laser_Mira_SP(new Vector(Driver_Game.getWidth()/2, 0), new Vector(-90, 30)));
                break;
        }
    }

    private Vector getRandomEdgeVector(String type){
        switch(type){
            case "top":
                double initY = 0;
                double initX = Math.random() * 850;
                return new Vector(initX, initY);
            case "bottom":
                initY = 1000;
                initX = Math.random() * 850;
                return new Vector(initX, initY);
            case "left":
                initX = 0;
                initY = Math.random() * 1000;
                return new Vector(initX, initY);
            case "right":
                initX = 850;
                initY = Math.random() * 1000;
                return new Vector(initX, initY);
            case "topLeft":
                double tester = Math.random() * 1850;
                if(tester >= 1000){
                    return getRandomEdgeVector("top");
                }
                else{
                    return getRandomEdgeVector("left");
                }
            case "topRight":
                tester = Math.random() * 1850;
                if(tester >= 1000){
                    return getRandomEdgeVector("top");
                }
                else{
                    return getRandomEdgeVector("right");
                }
            case "bottomLeft":
                tester = Math.random() * 1850;
                if(tester >= 1000){
                    return getRandomEdgeVector("bottom");
                }
                else{
                    return getRandomEdgeVector("left");
                }
            case "bottomRight":
                tester = Math.random() * 1850;
                if(tester >= 1000){
                    return getRandomEdgeVector("bottom");
                }
                else{
                    return getRandomEdgeVector("right");
                }
        }
        //this should never occur
        return new Vector();
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 3;
        Vector offset = new Vector(0, 0);
        idle = new SpriteInfo("Boss_Mira1_idle1", offset, rotation, size);
        idle.setNext(new SpriteInfo("Boss_Mira1_idle2", offset, rotation, size,
                new SpriteInfo("Boss_Mira1_idle3", offset, rotation, size,
                        new SpriteInfo("Boss_Mira1_idle4", offset, rotation, size, idle))));

        offset = new Vector(1, 1);
        idle2 = new SpriteInfo("Boss_Mira2_idle1", offset, rotation, size);
        idle2.setNext(new SpriteInfo("Boss_Mira2_idle2", offset, rotation, size,
                new SpriteInfo("Boss_Mira2_idle3", offset, rotation, size,
                        new SpriteInfo("Boss_Mira2_idle4", offset, rotation, size, idle2))));
        if(Driver_GameLogic.getCurrentLogicDriver().getDifficulty() <= 4) {
            this.sprite = idle;
        }
        else{
            this.sprite = idle2;
        }
    }

    @Override
    protected void updateSprite(){
        if(tick % 10 == 0)
            this.sprite = this.sprite.getNext();
    }

    private static Routine_Boss_Attack constructDefaults(int version){
        Instruction_ToggleAttack toggleAttack = new Instruction_ToggleAttack(null, null, true);
        Instruction_Attack attack = new Instruction_Attack(null, toggleAttack, "defaultAttack" + version, 60, 60);
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
        if(difficulty >= 3){
            Instruction_Mira_DefaultLaser staticLaser = new Instruction_Mira_DefaultLaser(null, 0);
            Instruction_Timer mainTimer = new Instruction_Timer(null, staticLaser, 600);
            if(difficulty >= 7){
                Instruction_Mira_DefaultLaser rightLaser = new Instruction_Mira_DefaultLaser(null, 1);
                Instruction_Mira_DefaultLaser leftLaser = new Instruction_Mira_DefaultLaser(null, 2);
                Instruction_Timer secondTimer = new Instruction_Timer(null, leftLaser, 600);
                Instruction_Timer thirdTimer = new Instruction_Timer(null, rightLaser, 600);
                leftLaser.setNext(thirdTimer);
                rightLaser.setNext(mainTimer);
                staticLaser.setNext(secondTimer);
                toggleAttack.setNext(mainTimer);
            }
            else{
                staticLaser.setNext(mainTimer);
                toggleAttack.setNext(mainTimer);
            }
        }
        double initHP = 0;
        switch(version){
            case 1:
                initHP = 100;
                break;
            case 2:
                initHP = 120;
                break;
            case 3:
                initHP = 140;
                break;
            case 4:
                initHP = 160;
                break;
        }
        return new Routine_Boss_Attack(null, null, attack, initHP);
    }

    private static Routine_Boss_Spell constructWalkOfTime(){
        Instruction_Timer finalTimer = new Instruction_Timer(null, null, 120);
        Instruction_Mira_WOT freeState = new Instruction_Mira_WOT(null, finalTimer, "free");
        Instruction_Timer orbitTimer = new Instruction_Timer(null, freeState, 300);
        Instruction_Mira_WOT orbitState = new Instruction_Mira_WOT(null, orbitTimer, "orbit");
        Instruction_Timer attackTimer = new Instruction_Timer(null, orbitState, 305);
        Instruction_Attack attack = new Instruction_Attack(null, attackTimer, "wotSpray", 300, 300);
        finalTimer.setNext(attack);
        double initHP = 200;
        return new Routine_Boss_Spell(null, null, attack, initHP, "Command: Walk Of Time", 5);
    }

    private static Routine_Boss_Spell constructReturnToOrigin(){
        Instruction_Timer finalTimer = new Instruction_Timer(null, null, 8 * Driver_Game.getUPS());
        Instruction_Mira_RTO returnToOrigin = new Instruction_Mira_RTO(null, finalTimer);
        Instruction_Timer attackTimer = new Instruction_Timer(null, returnToOrigin, 300);
        Instruction_Attack attack = new Instruction_Attack(null, attackTimer, "rtoSpiral", 180, 180);
        finalTimer.setNext(attack);
        double initHP = 220;
        return new Routine_Boss_Spell(null, null, attack, initHP, "Command: Return To Origin", 5);
    }

    private static Routine_Boss_Spell constructSuffocatingAir(){
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, null, true);
        Instruction_Attack attack = new Instruction_Attack(null, toggle, "saSpawn", 60, 60);
//        toggle.setNext(attack);
        double initHP = 100;
        return new Routine_Boss_Spell(null, null, attack, initHP, "Command: Suffocating Air", 5);
    }

    private static Routine_Boss_Spell constructUtterNothingness(){
        int minX = 100;
        int maxX = 750;
        int minY = 100;
        int maxY = 300;
        Instruction_Timer timerAfter4 = new Instruction_Timer(null, null, 20);
        Instruction_Attack attack4 = new Instruction_Attack(null, timerAfter4, "spLasers", 1, 1);
        Instruction_TeleportRandom tp4 = new Instruction_TeleportRandom(null, attack4, minX, maxX, minY, maxY);

        Instruction_Timer timerAfter3 = new Instruction_Timer(null, tp4, 121);
        Instruction_Attack attack3 = new Instruction_Attack(null, timerAfter3, "spRandom", 120, 120);
        Instruction_TeleportRandom tp3 = new Instruction_TeleportRandom(null, attack3, minX, maxX, minY, maxY);

        Instruction_Timer timerAfter2 = new Instruction_Timer(null, tp3, 20);
        Instruction_Attack attack2 = new Instruction_Attack(null, timerAfter2, "spBig", 1, 1);
        Instruction_TeleportRandom tp2 = new Instruction_TeleportRandom(null, attack2, minX, maxX, minY, maxY);

        Instruction_Timer timerAfter1 = new Instruction_Timer(null, tp2, 121);
        Instruction_Attack attack1 = new Instruction_Attack(null, timerAfter1, "spShards", 120, 120);
        Instruction_TeleportRandom tp1 = new Instruction_TeleportRandom(null, attack1, minX, maxX, minY, maxY);

        timerAfter4.setNext(tp1);

        double initHP = 80;
        return new Routine_Boss_Spell(null, null, tp1, initHP, "Command: Utter Nothingness", 0);
    }

    private static Routine_Boss_Attack constructBehaviors(){
        Routine_Boss_Attack default1 = constructDefaults(1);
        Routine_Boss_Spell walkOfTime = constructWalkOfTime();
        default1.setNext(walkOfTime);
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();

        if(difficulty >= 3){
            Routine_Boss_Attack default2 = constructDefaults(2);
            Routine_Boss_Spell returnToOrigin = constructReturnToOrigin();
            default2.setNext(returnToOrigin);
            walkOfTime.setNext(default2);

            if(difficulty >= 5){
                Routine_Boss_Attack default3 = constructDefaults(3);
                Routine_Boss_Spell suffocatingAir = constructSuffocatingAir();
                default3.setNext(suffocatingAir);
                returnToOrigin.setNext(default3);

                if(difficulty >= 8){
                    Routine_Boss_Attack default4 = constructDefaults(4);
                    Routine_Boss_Spell utterNothingness = constructUtterNothingness();
                    default4.setNext(utterNothingness);
                    suffocatingAir.setNext(default4);
                }
            }
        }
        return default1;
    }
}
