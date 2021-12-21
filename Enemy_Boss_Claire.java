import java.util.ArrayList;

public class Enemy_Boss_Claire extends Enemy_Boss {

    private SpriteInfo left;
    private SpriteInfo right;
    private int spritePos = 0;
    public Enemy_Boss_Claire(){
        //super(null);
        super(constructBehaviors());
//        super(constructChaos());
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

    public void airStrike(Vector target){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
        double angle = Vector.getAngle(this, target);
        toSpawn.add(new EP_Orb_Small(this, new Vector(angle, 500 + (20 * difficulty)), "red"));
        if(difficulty >= 3){
            toSpawn.add(new EP_Orb_Small(this, new Vector(angle + 20, 500 + (20 * difficulty)), "red"));
            toSpawn.add(new EP_Orb_Small(this, new Vector(angle - 20, 500 + (20 * difficulty)), "red"));
            if(difficulty >= 5){
                toSpawn.add(new EP_Orb_Small(this, new Vector(angle + 40, 500 + (20 * difficulty)), "red"));
                toSpawn.add(new EP_Orb_Small(this, new Vector(angle - 40, 500 + (20 * difficulty)), "red"));
            }
        }
    }

    @Override
    public void spawnAttack(){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();

        switch(attackName){
            case "default1":
                attackDefault(difficulty, 1);
                break;
            case "default2":
                attackDefault(difficulty, 2);
                break;
            case "default3":
                attackDefault(difficulty, 3);
                break;
            case "default4":
                attackDefault(difficulty, 4);
                break;

            case "esSpawn":
                attackESSpawn(difficulty);
                break;
            case "fSpawn":
                attackFSpawn(difficulty);
                break;
            case "eSpawn":
                attackESpawn(difficulty);
                break;
            case "cSpawn":
                attackCSpawn(difficulty);
                break;
            case "cLasers":
                attackCLaser(difficulty);
                break;
            case "cTP":
                attackCTP();
                break;
        }
    }

    //1, 30
    private void attackDefault(int difficulty, int mode){
        String deathCode;
        switch(mode){
            case 1:
                deathCode = "wiggleShot";
                break;
            case 2:
                deathCode = "airStrike";
                break;
            case 3:
                deathCode = "sideStrike";
                break;
            case 4:
                deathCode = "sideShot";
                break;
            default:
                deathCode = "wiggleShot";
                break;
        }
        for(int i = 0; i < ((difficulty + 1)/2) * 8; i++){
            double angle = Math.random() * 360;
            double speed = 200 + (Math.random() * (difficulty * 25));
            toSpawn.add(new EP_Orb_Claire_Default(this, new Vector(angle, speed), deathCode));
        }
    }

    //120, 120, DOES NOT REPEAT UNLESS THE PLAYER DIES OR THE PLAYER BOMBS
    private void attackESSpawn(int difficulty){
        if(attackTimer % 4 == 0){
            for(int i = 0; i < 1 + (difficulty / 2); i++){
                double angle = Math.random() * 360;
                double speed = 200 + (10 * Math.random() * difficulty);
                toSpawn.add(new EP_Orb_Claire_ES(this, new Vector(angle, speed)));
            }
        }
    }

    //3 seconds lasers, 10 seconds wiggly = 13 seconds * 60 == 780
    //780, 780
    private void attackFSpawn(int difficulty){
        //lasers
        if(attackTimer == 780){
            for(int i = 0; i < difficulty*2; i++){
                Vector initPos;
                //horizontal
                if (Math.random() > .5) {
                    double x = Math.random() * 850;
                    //top
                    if (Math.random() > .5) {
                        initPos = new Vector(x, 0);
                    }
                    //bottom
                    else {
                        initPos = new Vector(x, 1000);
                    }
                }
                //vertical
                else {
                    double y = Math.random() * 1000;
                    //left
                    if (Math.random() > .5) {
                        initPos = new Vector(0, y);
                    }
                    //right
                    else {
                        initPos = new Vector(850, y);
                    }
                }

                Vector cpoint = Vector.add(new Vector(850/2, 1000/2), Vector.polarToVelocity(new Vector(Math.random() * 360, Math.random() * 200)));
                double angle = Vector.getAngle(initPos, cpoint);
                toSpawn.add(new EP_Laser_Claire_F(initPos, angle));
            }
        }

        if(attackTimer % (60 - (difficulty * 2)) == 0 && attackTimer <= 600){
            //120 3
            int diff = 120 - (difficulty * 3);
            int off = tick%120;
            double ratio = (double)off/120;
            double offset = ratio * diff;
            double xpos = offset;
            double ypos = offset;

//            double speed = 300 + (Math.random() * difficulty * 20);
            double speed = 250;
            while(xpos <= 850){
                toSpawn.add(new EP_Orb_Wiggle(new Vector(xpos, -5), new Vector(0, speed)));
                xpos += diff;
            }
            while(ypos <= 1000){
                toSpawn.add(new EP_Orb_Wiggle(new Vector(-5, ypos), new Vector(90, speed)));
                ypos += diff;
            }
        }
    }

    //60, 60
    private void attackESpawn(int difficulty){
        if(attackTimer == 60){
            for(int i = 0; i < difficulty; i++){
                double angle = Math.random() * 360;
                angle -= 180;
                double speed = 100 + (Math.random() * 30 * difficulty);
                toSpawn.add(new EP_Orb_Claire_E_Large(this, new Vector(angle, speed)));
            }
        }
    }

    //120, 120, DOES NOT REPEAT UNLESS THE PLAYER DIES OR THE PLAYER BOMBS
    private void attackCSpawn(int difficulty){
        if(attackTimer % 4 == 0){
            for(int i = 0; i < difficulty/3; i++){
                double angle = Math.random() * 360;
                double speed = 200 + (10 * Math.random() * difficulty);
                toSpawn.add(new EP_Orb_Claire_ES(this, new Vector(angle, speed)));
            }
        }
    }
    //1, 1 (just spawns in the lasers)
    private void attackCLaser(int difficulty){
        double playerAngle = Vector.getAngle(this, Player.getThePlayer());
        double initAngle = playerAngle - 180;
        toSpawn.add(new EP_Laser_Claire_C(this, initAngle, playerAngle, false));
        toSpawn.add(new EP_Laser_Claire_C(this, initAngle, playerAngle, true));
    }
    //1, 1 (just teleport the existing bullets)
    private void attackCTP(){
        ArrayList<EP_Orb_Claire_ES> toTeleport = new ArrayList<>();
        for(Projectile_Enemy pe: Driver_GameLogic.getCurrentLogicDriver().getProjectileEnemies()){
            if(pe instanceof EP_Orb_Claire_ES){
                toTeleport.add((EP_Orb_Claire_ES)pe);
            }
        }
        Vector playerPos = Player.getThePlayer();
        double xlower = playerPos.getA() - 100;
        double xupper = playerPos.getA() + 100;
        double ylower = playerPos.getB() - 100;
        double yupper = playerPos.getB() + 100;
        for(EP_Orb_Claire_ES orb : toTeleport){
            double xtry = Math.random() * 850;
            if(xtry > xlower && xtry < xupper){
                double xnewtry = xtry + 200;
                if(xnewtry > 850){
                    xnewtry = xtry - 200;
                }
                xtry = xnewtry;
            }

            double ytry = Math.random() * 1000;
            if(ytry > ylower && ytry < yupper){
                double ynewtry = ytry + 200;
                if(ynewtry > 1000){
                    ynewtry = ytry - 200;
                }
                ytry = ynewtry;
            }
            orb.setA(xtry);
            orb.setB(ytry);
        }
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 3;
        Vector offset = new Vector(0, -2);
        idle = new SpriteInfo("Boss_Claire_idle1", offset, rotation, size);
        idle.setNext(new SpriteInfo("Boss_Claire_idle2", offset, rotation, size,
                new SpriteInfo("Boss_Claire_idle3", offset, rotation, size,
                        new SpriteInfo("Boss_Claire_idle4", offset, rotation, size, idle))));

//        offset = new Vector(2, -2);
        left = new SpriteInfo("Boss_Claire_left1", offset, rotation, size);
        left.setNext(new SpriteInfo("Boss_Claire_left2", offset, rotation, size,
                new SpriteInfo("Boss_Claire_left3", offset, rotation, size,
                        new SpriteInfo("Boss_Claire_left4", offset, rotation, size, left))));

//        offset = new Vector(-1, -2);
        right = new SpriteInfo("Boss_Claire_right1", offset, rotation, size);
        right.setNext(new SpriteInfo("Boss_Claire_right2", offset, rotation, size,
                new SpriteInfo("Boss_Claire_right3", offset, rotation, size,
                        new SpriteInfo("Boss_Claire_right4", offset, rotation, size, right))));

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
//            return;
        }
    }

    private static Routine_Boss_Attack constructDefaults(int version){
        Instruction_Timer timer = new Instruction_Timer(null, null, 180);
        Instruction_WaitPathOver pathOver = new Instruction_WaitPathOver(null, timer);
        Instruction_StartPolygonPathRandom path = new Instruction_StartPolygonPathRandom(null, pathOver, 1, 100, Driver_Game.getWidth() - 100, 100, 300, 1.5);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, path, true);
        Instruction_Attack attack = new Instruction_Attack(null, toggle, "default" + version, 1, 30);
        timer.setNext(path);

        double initHP;
        switch(version){
            default:
            case 1:
                initHP = 400;
                break;
            case 2:
                initHP = 410;
                break;
            case 3:
                initHP = 420;
                break;
            case 4:
                initHP = 430;
                break;
        }
        return new Routine_Boss_Attack(null, null, attack, initHP);
    }

    private static Routine_Boss_Spell constructEnclosedSpace(){
        Instruction_Timer afterTimer = new Instruction_Timer(null, null, 360);
        Instruction_WaitDeathOrBomb db = new Instruction_WaitDeathOrBomb(null, afterTimer);
        Instruction_Attack atk = new Instruction_Attack(null, db, "esSpawn", 120, 120);
        afterTimer.setNext(atk);
        double initHP = 250;
        return new Routine_Boss_Spell(null, null, atk, initHP, "New Pattern: Enclosed Space", "bomb");
    }

    private static Routine_Boss_Spell constructFabric(){
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, null, true);
        Instruction_Attack attack = new Instruction_Attack(null, toggle, "fSpawn", 780, 780);
        double initHP = 250;
        return new Routine_Boss_Spell(null, null, attack, initHP, "New Pattern: Fabric of Creation", 15);
    }

    private static Routine_Boss_Spell constructEyes(){
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, null, true);
        Instruction_Attack attack = new Instruction_Attack(null, toggle, "eSpawn", 60, 60);
        double initHP = 250;
        return new Routine_Boss_Spell(null, null, attack, initHP, "New Pattern: Strange Eyes", 15);
    }

    private static Routine_Boss_Spell constructChaos(){
        //first make the secondary loop
        Instruction_Timer endTimer = new Instruction_Timer(null, null, 180);
        Instruction_Attack a3 = new Instruction_Attack(null, endTimer, "cTP", 1, 1);
        Instruction_Timer t2 = new Instruction_Timer(null, a3, 180);
        Instruction_Attack a2 = new Instruction_Attack(null, t2, "cTP", 1, 1);
        Instruction_Timer t1 = new Instruction_Timer(null, a2, 180);
        Instruction_Attack a1 = new Instruction_Attack(null, t1, "cTP", 1, 1);
        Instruction_Timer midTimer = new Instruction_Timer(null, a1, 300);
        Instruction_Attack laser = new Instruction_Attack(null, midTimer, "cLasers", 1, 1);
        endTimer.setNext(laser);

        Instruction_Timer resetTimer = new Instruction_Timer(null, null, 360);
        Routine_WaitDeathOrBomb secondaryLoop = new Routine_WaitDeathOrBomb(null, resetTimer, laser);
        Instruction_Timer loopDelay = new Instruction_Timer(null, secondaryLoop, 180);
        Instruction_Attack phase1 = new Instruction_Attack(null, loopDelay, "cSpawn", 120, 120);
        resetTimer.setNext(phase1);

        double initHP = 400;
        return new Routine_Boss_Spell(null, null, phase1, initHP, "Old Pattern: Nostalgic Chaos", 0);
    }

    private static Routine_Boss_Attack constructBehaviors(){
        Routine_Boss_Attack default1 = constructDefaults(1);

        Routine_Boss_Spell enclosedSpace = constructEnclosedSpace();
        default1.setNext(enclosedSpace);

        Routine_Boss_Attack default2 = constructDefaults(2);
        enclosedSpace.setNext(default2);

        Routine_Boss_Spell fabric = constructFabric();
        default2.setNext(fabric);

        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
        if(difficulty >= 3){
            Routine_Boss_Attack default3 = constructDefaults(3);
            fabric.setNext(default3);

            Routine_Boss_Spell eyes = constructEyes();
            default3.setNext(eyes);

            if(difficulty >= 6){
                Routine_Boss_Attack default4 = constructDefaults(4);
                eyes.setNext(default4);

                Routine_Boss_Spell chaos = constructChaos();
                default4.setNext(chaos);
            }
        }

        return default1;
    }
}
