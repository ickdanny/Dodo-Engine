import java.util.ArrayList;

public class Enemy_Boss_XiaoLi extends Enemy_Boss {

    //                          idle|0
    private SpriteInfo left;      //|1
    private SpriteInfo right;     //|2
    private SpriteInfo leftSlash; //|3
    private SpriteInfo rightSlash;//|4
    private SpriteInfo spin;      //|5
    private SpriteInfo thrust;    //|6

    private SpriteInfo idleB;     //|10
    private SpriteInfo spinB;     //|11
    private SpriteInfo thrustB;   //|12
    private int spritePos = 0;
    private int spriteDelay = 10;

    //options = unset, forwardThrust, leftSlash, rightSlash, leftSpin, dualThrust, dualSpin
    private String spearPos = "unset";

    private Vector forwardPos = new Vector(0, 40);
    private Vector leftPos = new Vector(-40, 40);
    private Vector rightPos = new Vector(40, 40);
    private Vector leftSpinPos = new Vector(-20, 5);
    private Vector rightSpinPos = new Vector(20, 5);
    private Vector leftDualPos = new Vector(-15, 40);
    private Vector rightDualPos = new Vector(15, 40);

    private Boolean phase = false;

    private ArrayList<Projectile_Enemy> toDelete = new ArrayList<>();

    public Enemy_Boss_XiaoLi() {
//        super(null);
//        super(new Instruction_ToggleAttack(null, new Instruction_Attack(null, null, "default1", 1, 30), true));
//        super(new Instruction_Attack(null, new Instruction_ToggleAttack(null, null, true), "default1", 1, 30));
//        setNewBehavior(new Instruction_Attack(this, null, "default1", 1, 30));
//        setBehaviorOwnerToThis();
//        super(new Instruction_SetPolar(null, null, new Vector(0, 100)));
//        super(topSpin(true, 2));
//        super(constructDefaults(3));
        super(constructBehaviors());
//        super(constructPortal());
        setBehaviorOwnerToThis();
//        changeSprite(11);
//        spearPos = "dualSpin";
////        spawnSlash(true);
//        spawnSpin(true);
//        spawnSpin(false);
    }

    @Override
    public void update() {
        super.update();
//        System.out.println(followsPath);
//        System.out.println(reloadTimer);
//        System.out.println(behavior);
        //when moving hitbox seems bouncy
        //whatever just ignore
//        if(tick % 50 == 0){
//            spawnSlash(true);
//        }
//        System.out.println(reloadTimer);
//        System.out.println(canAttack);
        //basic movement when not using other sprites

//        System.out.println(behavior.getRootBehavior());
//        System.out.println(polar);

//        System.out.println(behavior.getRootBehavior());
        if (spritePos == 0 || spritePos == 1 || spritePos == 2) {
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
            //if there are more boss phases after this
            if(behavior != null && behavior.getNext() != null){
                clearSpawnBullets();
                Driver_GameLogic.getCurrentLogicDriver().bossClear(false);
                Behavior nextPhase = behavior.getNext();
//                stopAll();
                stop();
                stopAttack();
                canBeDamaged = false;

                ArrayList<Vector> p = new ArrayList<>();
                p.add(new Vector(Driver_Game.getWidth()/2, 200));
                Path_Polygon toMid = new Path_Polygon(p, false, .5*(Vector.getDistance(this, p.get(0))/*/Driver_Game.getUPS()*/));

                Instruction_ToggleCanBeDamaged toggle = new Instruction_ToggleCanBeDamaged(this, nextPhase, true);
                Instruction_Timer timer = new Instruction_Timer(this, toggle, 300);
                Instruction_StartPath sp = new Instruction_StartPath(this, timer, toMid);
                Routine reset = reset(phase);
                reset.setNext(sp);

                setNewBehavior(reset);
                spellName = "";
            }
            //otherwise kill the boss, might be more code to add here later
            else{
                exists = false;
                toSpawn.clear();
                spellName = "";
                Driver_GameLogic.getCurrentLogicDriver().bossClear(true);
                //something signalling the boss died, telling some driver to play dialogue or go to the next level or something
                //any and all dialogue that may or may not occur will be the responsibility of the Driver_Game instance
                Driver_GameLogic.getCurrentLogicDriver().victory();
            }
        }
    }

    @Override
    protected void spawnAttack() {
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();

        switch (attackName) {
            //these are not fired continuously
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

            case "bSpawn":
                attackBSpawn(difficulty);
                break;
            case "nSpawn":
                attackNSpawn(difficulty);
                break;
            case "cSpawn":
                attackCSpawn(difficulty);
                break;
            case "pSpawn":
                attackPSpawn(difficulty);
                break;
        }
    }

    //1, 1
    private void attackDefault(int difficulty, int version) {
//        System.out.println("s's");
        switch (version) {
            //ovals
            case 1:
                for (int i = 0; i < difficulty; i++) {
//                    double angle = Math.random() * 360;
//                    double speed = 300 + (20 * difficulty * Math.random());
//                    toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(angle, speed), "orange"));
                    ArrayList<Vector> spawnPos = spearPos(spearPos);
                    for(Vector v : spawnPos){
//                        v = Vector.add(this, v);
                        double angle = Math.random() * 360;
                        double speed = 300 + (20 * difficulty * Math.random());
//                        double speed = 0;
                        toSpawn.add(new EP_Oval(v, new Vector(angle, speed), "orange"));
//                        System.out.println("se");
                    }
                }
                break;
            //semicircle ovals
            case 2:
                double speed = 500 + (15 * difficulty);
//                double bAngle = Vector.getAngle(spearPos(spearPos), Player.getThePlayer());
//                toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(bAngle, speed), "orange"));
//                if (difficulty >= 3) {
//                    toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(bAngle + 20, speed), "orange"));
//                    toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(bAngle - 20, speed), "orange"));
//                    if (difficulty >= 5) {
//                        toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(bAngle + 40, speed), "orange"));
//                        toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(bAngle - 40, speed), "orange"));
//                        if (difficulty >= 7) {
//                            toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(bAngle + 60, speed), "orange"));
//                            toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(bAngle - 60, speed), "orange"));
//                        }
//                    }
//                }
                ArrayList<Vector> spawnPos = spearPos(spearPos);
                for(Vector v : spawnPos){
//                    v = Vector.add(this, v);
                    double bAngle = Vector.getAngle(v, Player.getThePlayer());
                    toSpawn.add(new EP_Oval(v, new Vector(bAngle, speed), "orange"));
                    if (difficulty >= 3) {
                        toSpawn.add(new EP_Oval(v, new Vector(bAngle + 20, speed), "orange"));
                        toSpawn.add(new EP_Oval(v, new Vector(bAngle - 20, speed), "orange"));
                        if (difficulty >= 5) {
                            toSpawn.add(new EP_Oval(v, new Vector(bAngle + 40, speed), "orange"));
                            toSpawn.add(new EP_Oval(v, new Vector(bAngle - 40, speed), "orange"));
                            if (difficulty >= 7) {
                                toSpawn.add(new EP_Oval(v, new Vector(bAngle + 60, speed), "orange"));
                                toSpawn.add(new EP_Oval(v, new Vector(bAngle - 60, speed), "orange"));
                            }
                        }
                    }
                }
                break;
            //ovals 2
            case 3:
                for (int i = 0; i < difficulty * 2; i++) {
//                    double angle = Math.random() * 360;
//                    speed = 400 + (20 * difficulty * Math.random());
//                    toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(angle, speed), "white"));
                    spawnPos = spearPos(spearPos);
                    for(Vector v : spawnPos){
//                        v = Vector.add(this, v);
                        double angle = Math.random() * 360;
                        speed = 400 + (20 * difficulty * Math.random());
                        toSpawn.add(new EP_Oval(v, new Vector(angle, speed), "white"));
                    }
                }
                speed = 600 + (15 * difficulty);
//                bAngle = Vector.getAngle(spearPos(spearPos), Player.getThePlayer());
//                toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(bAngle, speed), "white"));
//                if (difficulty >= 3) {
//                    toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(bAngle + 20, speed), "white"));
//                    toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(bAngle - 20, speed), "white"));
//                    if (difficulty >= 5) {
//                        toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(bAngle + 40, speed), "white"));
//                        toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(bAngle - 40, speed), "white"));
//                        if (difficulty >= 7) {
//                            toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(bAngle + 60, speed), "white"));
//                            toSpawn.add(new EP_Oval(spearPos(spearPos), new Vector(bAngle - 60, speed), "white"));
//                        }
//                    }
//                }
                spawnPos = spearPos(spearPos);
                for(Vector v : spawnPos){
//                    v = Vector.add(this, v);
                    double bAngle = Vector.getAngle(v, Player.getThePlayer());
                    toSpawn.add(new EP_Oval(v, new Vector(bAngle, speed), "white"));
                    if (difficulty >= 3) {
                        toSpawn.add(new EP_Oval(v, new Vector(bAngle + 20, speed), "white"));
                        toSpawn.add(new EP_Oval(v, new Vector(bAngle - 20, speed), "white"));
                        if (difficulty >= 5) {
                            toSpawn.add(new EP_Oval(v, new Vector(bAngle + 40, speed), "white"));
                            toSpawn.add(new EP_Oval(v, new Vector(bAngle - 40, speed), "white"));
                            if (difficulty >= 7) {
                                toSpawn.add(new EP_Oval(v, new Vector(bAngle + 60, speed), "white"));
                                toSpawn.add(new EP_Oval(v, new Vector(bAngle - 60, speed), "white"));
                            }
                        }
                    }
                }
                break;
            //laser show
            case 4:
                for(int i = 0; i < difficulty * 4; i++){
                    spawnPos = spearPos(spearPos);
                    for(Vector v : spawnPos) {
//                        v = Vector.add(this, v);
                        toSpawn.add(new EP_Laser_XL_Default4(v, Math.random() * 360));
                    }
                }
        }
    }

    //120, 120
    private void attackBSpawn(int difficulty) {
        if(attackTimer % (10 - difficulty) == 0) {
            //2 revolution per second
            int rpos = (int) attackTimer * 12;
            rpos = rpos % 360;
            double hangle = 160;
            double hangle2 = hangle + 180;
            double d1 = Math.abs(hangle - rpos);
            double d2 = Math.abs(hangle2 - rpos);
            double d3 = Math.abs((hangle + 360) - rpos);
            double d4 = Math.abs((hangle2 - 360) - rpos);
            double d = Math.min(Math.min(d1, d2), Math.min(d3, d4));

            double sScalar = 90 - d;
            double sm = sScalar/90;
            double speed = 300 * (1 + (2 * sm));

            double randomAngleDiff = (Math.random() * 10) - 5;
            double angle = rpos + randomAngleDiff;

            toSpawn.add(new EP_Oval(spearPos("leftSpin").get(0), new Vector(angle, speed), "white"));
        }
//        if(attackTimer == 120){
        int t = 120;
        if(difficulty > 4){
            if(difficulty > 6){
                t = 30;
            }
            else{
                t = 60;
            }
        }
        if(attackTimer % t == 0){
            for(int i = 0; i < 360; i += (12 - difficulty)){
//                double hangle = 160 - 90;
                double hangle = 170;
                hangle += ((Math.random() * 10) - 5);
                double hangle2 = hangle + 180;
                double d1 = Math.abs(hangle - i);
                double d2 = Math.abs(hangle2 - i);
                double d3 = Math.abs((hangle + 360) - i);
                double d4 = Math.abs((hangle2 - 360) - i);
                double d = Math.min(Math.min(d1, d2), Math.min(d3, d4));

                double sScalar = 90 - d;
                double sm = sScalar/90;
                double speed = 200 * (1 + (2 * sm));

                double angle = i + ((Math.random() * 10) - 5);
                toSpawn.add(new EP_Oval(spearPos("leftSpin").get(0), new Vector(angle, speed), "orange"));
            }
        }
    }

    //120, 180
    private void attackNSpawn(int difficulty) {
        //from 2 to 1 rotations, same ratio as attackTimer/120
        if(attackTimer % (11 - difficulty) == 0){
            double ratio = attackTimer/120;
            double rotations = 1 + (ratio);
            double aVelocity = -360;
            double distance = 100;
            Vector av = new Vector(aVelocity, distance);
            int ticksSpent = (int)(rotations * 60);
            Path_Orbit path = new Path_Orbit(this, av, 180);
            double speed = 300 + (30 * difficulty);
            toSpawn.add(new EP_Oval_XL_N(this, path, speed, ticksSpent));
        }
    }

    //120, 180
    private void attackCSpawn(int difficulty) {
        if(attackTimer % 10 == 0){
            double ratio = attackTimer/120;
            double rotations = 1 + ratio;
            double aVelocity = 360;
            double distance = 100;
            Vector av = new Vector(aVelocity, distance);
            int ticksSpent = (int)(rotations * 60);
            Path_Orbit path = new Path_Orbit(this, av, 180);
            double speed = 500 + (30 * difficulty);
            toSpawn.add(new EP_Sword_C(this, path, speed, ticksSpent));
        }
    }

    //600, 600
    private void attackPSpawn(int difficulty) {
        if(attackTimer % (25 - (difficulty * 2)) == 0){
            toSpawn.add(new EP_Sword(new Vector(Math.random() * 850, -20)));
        }
        if(attackTimer % 100 == 0){
            double a = Math.random() * 180;
            double b = a + 180;
            b %= 360;
            Vector posa = Vector.add(this, Vector.polarToVelocity(new Vector(a, 150)));
            Vector posb = Vector.add(this, Vector.polarToVelocity(new Vector(b, 150)));
            EP_ShadowObject_XL_P soa = new EP_ShadowObject_XL_P(posa, this);
            //i am mad scientist issocool
            EP_ShadowObject_XL_P sob = new EP_ShadowObject_XL_P(posb, this);
            spawnCircle(soa, difficulty, 40 + (int)(attackTimer/6), false);
            spawnCircle(sob, difficulty, 40 + (int)(attackTimer/6), true);
            toSpawn.add(soa);
            toSpawn.add(sob);
        }
    }

    private void spawnCircle(EP_ShadowObject_XL_P center, int difficulty, int size, boolean direction){
        int anglep;
        if(difficulty == 7){
            anglep = 15;
        }
        else{
            anglep = 10;
        }
        for(int angle = 0; angle < 360; angle += anglep){
            Vector initPos = Vector.add(center, Vector.polarToVelocity(new Vector(angle, size)));
//            Vector aVelocity = new Vector(180, 200);
            Vector aVelocity = new Vector(180, size);
            if(direction){
                aVelocity.setA(0 - aVelocity.getA());
            }
            //angle is probably initAngle?
            Path_Orbit path = new Path_Orbit(center, aVelocity, angle);
            toSpawn.add(new EP_Oval_XL_P(initPos, center, path));
        }
    }
//    private int numSpawns(String pos){
//        if(pos.contains("dual")){
//            return 2;
//        }
//        return 1;
//    }


    public void spawnThrust(boolean single){
        if(single) {
            EP_ShadowObject_XL_Thrust toAdd = new EP_ShadowObject_XL_Thrust(forwardPos, this);
            toSpawn.add(toAdd);
            toDelete.add(toAdd);
        }
        else{
            EP_ShadowObject_XL_Thrust toAdd = new EP_ShadowObject_XL_Thrust(leftDualPos, this);
            toSpawn.add(toAdd);
            toDelete.add(toAdd);
            toAdd = new EP_ShadowObject_XL_Thrust(rightDualPos, this);
            toSpawn.add(toAdd);
            toDelete.add(toAdd);
        }
    }

    public void spawnSlash(boolean left){
        Vector offset;
        if(left){
            offset = leftPos;
        }
        else{
            offset = rightPos;
        }
        EP_ShadowObject_XL_Slash toAdd = new EP_ShadowObject_XL_Slash(offset, this, spriteDelay * 4);
        toSpawn.add(toAdd);
        toDelete.add(toAdd);
    }

//    public void spawnSpin(boolean left){
//        Vector offset;
//        if(left){
//            offset = leftSpinPos;
//        }
//        else{
//            offset = rightSpinPos;
//        }
//        EP_ShadowObject_XL_Spin toAdd = new EP_ShadowObject_XL_Spin(offset, this);
//        toSpawn.add(toAdd);
//        toDelete.add(toAdd);
//    }
    public void spawnSpin(boolean single){
        EP_ShadowObject_XL_Spin toAdd = new EP_ShadowObject_XL_Spin(leftSpinPos, this);
        toSpawn.add(toAdd);
        toDelete.add(toAdd);
        if(!single){
            toAdd = new EP_ShadowObject_XL_Spin(rightSpinPos, this);
            toSpawn.add(toAdd);
            toDelete.add(toAdd);
        }
    }

    public void deleteToDelete(){
        for(Projectile_Enemy p : toDelete){
            p.setExists(false);
        }
        toDelete.clear();
    }

    private ArrayList<Vector> spearPos(String pos){
        ArrayList<Vector> toRet = new ArrayList<>();
        switch(pos){
            case "forwardThrust":
                toRet.add(forwardPos.clone());
                break;
            case "leftSlash":
                toRet.add(leftPos.clone());
                break;
            case "rightSlash":
                toRet.add(rightPos.clone());
                break;
            case "leftSpin":
                toRet.add(leftSpinPos.clone());
                break;
            case "dualThrust":
                toRet.add(leftDualPos.clone());
                toRet.add(rightDualPos.clone());
                break;
            case "dualSpin":
                toRet.add(leftSpinPos.clone());
                toRet.add(rightSpinPos.clone());
                break;
        }
//        for(int i = 0; i < toRet.size(); i++){
//            toRet.get(i) = Vector.add(toRet.get(i), this);
//        }
        for(Vector v : toRet){
            v.addToThis(this);
        }
        return toRet;
    }

    public void changeSpearPos(String newPos){
        this.spearPos = newPos;
    }


    @Override
    protected void initSprite() {
        double rotation = 0;
        double size = 3;
        Vector offset = new Vector(2, 0);
        idle = new SpriteInfo("Boss_XiaoLiA_idle1", offset, rotation, size);
        idle.setNext(new SpriteInfo("Boss_XiaoLiA_idle2", offset, rotation, size,
                new SpriteInfo("Boss_XiaoLiA_idle3", offset, rotation, size,
                        new SpriteInfo("Boss_XiaoLiA_idle4", offset, rotation, size, idle))));

        left = new SpriteInfo("Boss_XiaoLiA_left1", offset, rotation, size);
        left.setNext(new SpriteInfo("Boss_XiaoLiA_left2", offset, rotation, size,
                new SpriteInfo("Boss_XiaoLiA_left3", offset, rotation, size,
                        new SpriteInfo("Boss_XiaoLiA_left4", offset, rotation, size, left))));

        right = new SpriteInfo("Boss_XiaoLiA_right1", offset, rotation, size);
        right.setNext(new SpriteInfo("Boss_XiaoLiA_right2", offset, rotation, size,
                new SpriteInfo("Boss_XiaoLiA_right3", offset, rotation, size,
                        new SpriteInfo("Boss_XiaoLiA_right4", offset, rotation, size, right))));

        offset = new Vector(-3, 0);
        leftSlash = new SpriteInfo("Boss_XiaoLiA_leftSlash1", offset, rotation, size);
        leftSlash.setNext(new SpriteInfo("Boss_XiaoLiA_leftSlash2", offset, rotation, size,
                new SpriteInfo("Boss_XiaoLiA_leftSlash3", offset, rotation, size,
                        new SpriteInfo("Boss_XiaoLiA_leftSlash4", offset, rotation, size,
                                new SpriteInfo("Boss_XiaoLiA_leftSlash5", offset, rotation, size, leftSlash)))));

        offset = new Vector(2, 0);
        rightSlash = new SpriteInfo("Boss_XiaoLiA_rightSlash1", offset, rotation, size);
        rightSlash.setNext(new SpriteInfo("Boss_XiaoLiA_rightSlash2", offset, rotation, size,
                new SpriteInfo("Boss_XiaoLiA_rightSlash3", offset, rotation, size,
                        new SpriteInfo("Boss_XiaoLiA_rightSlash4", offset, rotation, size,
                                new SpriteInfo("Boss_XiaoLiA_rightSlash5", offset, rotation, size, rightSlash)))));

        spin = new SpriteInfo("Boss_XiaoLiA_spin1", offset, rotation, size);
        spin.setNext(new SpriteInfo("Boss_XiaoLiA_spin2", offset, rotation, size,
                new SpriteInfo("Boss_XiaoLiA_spin3", offset, rotation, size,
                        new SpriteInfo("Boss_XiaoLiA_spin4", offset, rotation, size, spin))));

        thrust = new SpriteInfo("Boss_XiaoLiA_thrust1", offset, rotation, size);
        thrust.setNext(new SpriteInfo("Boss_XiaoLiA_thrust2", offset, rotation, size,
                new SpriteInfo("Boss_XiaoLiA_thrust3", offset, rotation, size,
                        new SpriteInfo("Boss_XiaoLiA_thrust4", offset, rotation, size, thrust))));

        idleB = new SpriteInfo("Boss_XiaoLiB_idle1", offset, rotation, size);
        idleB.setNext(new SpriteInfo("Boss_XiaoLiB_idle2", offset, rotation, size,
                new SpriteInfo("Boss_XiaoLiB_idle3", offset, rotation, size,
                        new SpriteInfo("Boss_XiaoLiB_idle4", offset, rotation, size, idleB))));

        spinB = new SpriteInfo("Boss_XiaoLiB_spin1", offset, rotation, size);
        spinB.setNext(new SpriteInfo("Boss_XiaoLiB_spin2", offset, rotation, size,
                new SpriteInfo("Boss_XiaoLiB_spin3", offset, rotation, size,
                        new SpriteInfo("Boss_XiaoLiB_spin4", offset, rotation, size, spinB))));

        thrustB = new SpriteInfo("Boss_XiaoLiB_thrust1", offset, rotation, size);
        thrustB.setNext(new SpriteInfo("Boss_XiaoLiB_thrust2", offset, rotation, size,
                new SpriteInfo("Boss_XiaoLiB_thrust3", offset, rotation, size,
                        new SpriteInfo("Boss_XiaoLiB_thrust4", offset, rotation, size, thrustB))));

        this.sprite = idle;
    }

    @Override
    protected void updateSprite() {
        if (tick % spriteDelay == 0)
            this.sprite = this.sprite.getNext();
    }

    public void setSpriteDelay(int newDelay){
        spriteDelay = newDelay;
    }

    public void changeSprite(int newPos) {
        if (newPos == spritePos) {
            return;
        }
        switch (newPos) {
            case 0:
                this.sprite = idle;
                break;
            case 1:
                this.sprite = left;
                break;
            case 2:
                this.sprite = right;
                break;
            case 3:
                this.sprite = leftSlash;
                break;
            case 4:
                this.sprite = rightSlash;
                break;
            case 5:
                this.sprite = spin;
                break;
            case 6:
                this.sprite = thrust;
                break;
            case 10:
                this.sprite = idleB;
                break;
            case 11:
                this.sprite = spinB;
                break;
            case 12:
                this.sprite = thrustB;
                break;
        }
        this.spritePos = newPos;
    }

    public void setPhase(){
        phase = true;
    }

//    private static Routine_XL_Default constructDefaults(int version){
//        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
//        int attackNum = version;
//        double speed;
//        boolean phase;
//        switch(version){
//            case 1:
//            case 2:
//                speed = 900;
//                phase = false;
//                break;
//            case 3:
//                speed = 600;
//                phase = true;
//                break;
//            case 4:
//                speed = 300;
//                phase = true;
//                break;
//            default:
//                speed = 0;
//                phase = false;
//        }
//        double speedMulti = 1 + (((double)difficulty)/10);
//        speed *= speedMulti;
//
//        int t = 30 - difficulty;
//
//        //default
//        Routine pos1 = position(speed/2);
//        Routine thrust1 = thrust(phase, speed, attackNum);
//        Routine reset1 = reset();
//        Instruction_Timer timer1 = new Instruction_Timer(null, t);
//        Routine slash1 = slash(speed, attackNum);
//        Routine reset2 = reset();
//        Instruction_Timer timer2 = new Instruction_Timer(null, t);
//        Routine slash2 = slash(speed, attackNum);
//        Routine reset3 = reset();
//        Instruction_Timer timer3 = new Instruction_Timer(null, t);
//        Instruction_StartPathToY r1 = new Instruction_StartPathToY(null, 200, speed/2);
//        Instruction_WaitPathOver w1 = new Instruction_WaitPathOver(null);
//        Routine pos2 = position(speed/2);
//        Routine thrust2 = thrust(phase, speed, attackNum);
//        Routine reset4 = reset();
//        Instruction_Timer timer4 = new Instruction_Timer(null, t);
//        Routine slash3 = slash(speed, attackNum);
//        Routine reset5 = reset();
//        Instruction_Timer timer5 = new Instruction_Timer(null, t);
//        Routine slash4 = slash(speed, attackNum);
//        Routine reset6 = reset();
//        Instruction_Timer timer6 = new Instruction_Timer(null, t);
//        Routine slash5 = slash(speed, attackNum);
//        Routine reset7 = reset();
//        Instruction_Timer timer7 = new Instruction_Timer(null, t);
//        Routine slash6 = slash(speed, attackNum);
//        Routine reset8 = reset();
//        Instruction_Timer timer8 = new Instruction_Timer(null, t);
//        Instruction_StartPathToY r2 = new Instruction_StartPathToY(null, 200, speed/2);
//        Instruction_WaitPathOver w2 = new Instruction_WaitPathOver(null);
//
//        pos1.setNext(thrust1);
//        thrust1.setNext(reset1);
//        reset1.setNext(timer1);
//        timer1.setNext(slash1);
//        slash1.setNext(reset2);
//        reset2.setNext(timer2);
//        timer2.setNext(slash2);
//        slash2.setNext(reset3);
//        reset3.setNext(timer3);
//        timer3.setNext(r1);
//        r1.setNext(w1);
//        w1.setNext(pos2);
//        pos2.setNext(thrust2);
//        thrust2.setNext(reset4);
//        reset4.setNext(timer4);
//        timer4.setNext(slash3);
//        slash3.setNext(reset5);
//        reset5.setNext(timer5);
//        timer5.setNext(slash4);
//        slash4.setNext(reset6);
//        reset6.setNext(timer6);
//        timer6.setNext(slash5);
//        slash5.setNext(reset7);
//        reset7.setNext(timer7);
//        timer7.setNext(slash6);
//        slash6.setNext(reset8);
//        reset8.setNext(timer8);
//        timer8.setNext(r2);
//        r2.setNext(w2);
//        w2.setNext(pos1);
//
////        pos1.setNext(thrust1);
////        thrust1.setNext(reset1);
////        reset1.setNext(slash1);
////        slash1.setNext(reset2);
////        reset2.setNext(slash2);
////        slash2.setNext(reset3);
////        reset3.setNext(r1);
////        r1.setNext(w1);
////        w1.setNext(pos2);
////        pos2.setNext(thrust2);
////        thrust2.setNext(reset4);
////        reset4.setNext(slash3);
////        slash3.setNext(reset5);
////        reset5.setNext(slash4);
////        slash4.setNext(reset6);
////        reset6.setNext(slash5);
////        slash5.setNext(reset7);
////        reset7.setNext(slash6);
////        slash6.setNext(reset8);
////        reset8.setNext(r2);
////        r2.setNext(w2);
////        w2.setNext(pos1);
//
////        pos1.setNext(thrust1);
////        thrust1.setNext(slash1);
////        slash1.setNext(slash2);
////        slash2.setNext(r1);
////        r1.setNext(pos2);
////        pos2.setNext(thrust2);
////        thrust2.setNext(slash3);
////        slash3.setNext(slash4);
////        slash4.setNext(slash5);
////        slash5.setNext(slash6);
////        slash6.setNext(r2);
////        r2.setNext(pos1);
//
//        Routine_PlayerYBoolean top = topSpin(!phase, attackNum);
//        top.setNext(reset());
////        return new Routine_XL_Default(null, null, pos1, topSpin(!phase, attackNum), 300);
//        return new Routine_XL_Default(null, null, pos1, top, 300);
//    }

    private static Routine reset(boolean phase){
        Routine toRet = new Routine(null, null, null);
        Instruction_XL_DeleteShadowEntity delete = new Instruction_XL_DeleteShadowEntity(null);
        Instruction_XL_SetSprite setSprite;
        Instruction_XL_SetSpriteDelay delay;
        if(!phase) {
            setSprite =new Instruction_XL_SetSprite(null, 0);
            delay = new Instruction_XL_SetSpriteDelay(null, 10);
        }
        else{
            setSprite = new Instruction_XL_SetSprite(null, 12);
            delay = new Instruction_XL_SetSpriteDelay(null, 15);
        }
//        Instruction_XL_SetSpear setSpear = new Instruction_XL_SetSpear(null, "forwardThrust");
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, false);
        Instruction_EndPath endPath = new Instruction_EndPath(null);
        delete.setNext(setSprite);
//        setSprite.setNext(setSpear);
//        setSprite.setNext(toggle);
        setSprite.setNext(delay);
        delay.setNext(toggle);
        toggle.setNext(endPath);
        toRet.setInstructions(delete);
        return toRet;
    }

    private static Routine position(boolean phase, double speed){
        Routine toRet = new Routine(null, null, null);
        Routine reset = reset(phase);
        BSplitter_LeftOrRight lor = new BSplitter_LeftOrRight(null);
        Instruction_SetPolar left = new Instruction_SetPolar(null, new Vector(270, speed));
        Instruction_SetPolar right = new Instruction_SetPolar(null, new Vector(90, speed));
        Instruction_XL_Position pos = new Instruction_XL_Position(null);
        reset.setNext(lor);
        lor.setLeft(left);
        lor.setRight(right);
        left.setNext(pos);
        right.setNext(pos);
        toRet.setInstructions(reset);
        return toRet;
    }

    //phase false = 1, true = 2
    private static Routine thrust(boolean phase, double speed, int attackNum){
        Routine toRet = new Routine(null, null, null);
        Instruction_XL_SetSprite setSprite;
        Instruction_XL_SetSpear setSpear;
        Instruction_XL_SpawnShadowEntity spawn;
        if(!phase){
            setSprite = new Instruction_XL_SetSprite(null, 6);
            setSpear = new Instruction_XL_SetSpear(null, "forwardThrust");
            spawn = new Instruction_XL_SpawnShadowEntity(null, "forwardThrust");
        }
        else{
            setSprite = new Instruction_XL_SetSprite(null, 12);
            setSpear = new Instruction_XL_SetSpear(null, "dualThrust");
            spawn = new Instruction_XL_SpawnShadowEntity(null, "forwardThrust");
        }
        Instruction_StartPathToBottom path = new Instruction_StartPathToBottom(null, speed);
        Instruction_WaitPathOver wait = new Instruction_WaitPathOver(null);
        Instruction_Attack attack = new Instruction_Attack(null, "default" + attackNum, 1, 1);
        setSprite.setNext(setSpear);
        setSpear.setNext(spawn);
        spawn.setNext(path);
        path.setNext(wait);
        wait.setNext(attack);
        toRet.setInstructions(setSprite);
        return toRet;
//        Routine toRet = new Routine(null, null, null);
//        Instruction_XL_SetSprite setSprite;
//        Instruction_XL_SetSpear setSpear;
//        Instruction_XL_SpawnShadowEntity spawn;
//        if(!phase){
//            setSprite = new Instruction_XL_SetSprite(null, 6);
//            setSpear = new Instruction_XL_SetSpear(null, "forwardThrust");
//            spawn = new Instruction_XL_SpawnShadowEntity(null, "forwardThrust");
//        }
//        else{
//            setSprite = new Instruction_XL_SetSprite(null, 12);
//            setSpear = new Instruction_XL_SetSpear(null, "dualThrust");
//            spawn = new Instruction_XL_SpawnShadowEntity(null, "forwardThrust");
//        }
//        Instruction_SetPolar setPolar = new Instruction_SetPolar(null, new Vector(0, speed));
//        Instruction_YBoolean stop = new Instruction_YBoolean(null, false, 100);
//        Instruction_SetPolar resetPolar = new Instruction_SetPolar(null, new Vector(0, 0));
//        Instruction_Attack attack = new Instruction_Attack(null, "default" + attackNum, 1, 1);
//        setSprite.setNext(setSpear);
//        setSpear.setNext(spawn);
//        spawn.setNext(setPolar);
//        setPolar.setNext(stop);
//        stop.setNext(resetPolar);
//        resetPolar.setNext(attack);
//        toRet.setInstructions(setSprite);
//        return toRet;
    }

    private static Routine slash(boolean phase, double speed, int attackNum){
        Routine toRet = new Routine(null, null, null);
        BSplitter_LeftOrRight lor = new BSplitter_LeftOrRight(null);

        Instruction_XL_SetSprite setSpriteLeft;
        Instruction_XL_SetSpear setSpearLeft;
        Instruction_XL_SpawnShadowEntity spawnLeft;
        Instruction_XL_SetSprite setSpriteRight;
        Instruction_XL_SetSpear setSpearRight;
        Instruction_XL_SpawnShadowEntity spawnRight;

        if(!phase) {
            setSpriteLeft = new Instruction_XL_SetSprite(null, 3);
            setSpearLeft = new Instruction_XL_SetSpear(null, "leftSlash");
            spawnLeft = new Instruction_XL_SpawnShadowEntity(null, "leftSlash");
            setSpriteRight = new Instruction_XL_SetSprite(null, 4);
            setSpearRight = new Instruction_XL_SetSpear(null, "rightSlash");
            spawnRight = new Instruction_XL_SpawnShadowEntity(null, "rightSlash");
        }
        else{
            setSpriteLeft = new Instruction_XL_SetSprite(null, 12);
            setSpearLeft = new Instruction_XL_SetSpear(null, "dualThrust");
            spawnLeft = new Instruction_XL_SpawnShadowEntity(null, "dualThrust");
            setSpriteRight = new Instruction_XL_SetSprite(null, 12);
            setSpearRight = new Instruction_XL_SetSpear(null, "dualThrust");
            spawnRight = new Instruction_XL_SpawnShadowEntity(null, "dualThrust");
        }

        lor.setLeft(setSpriteLeft);
        lor.setRight(setSpriteRight);
        setSpriteLeft.setNext(setSpearLeft);
        setSpearLeft.setNext(spawnLeft);
        setSpriteRight.setNext(setSpearRight);
        setSpearRight.setNext(spawnRight);
//        double playerX = Player.getThePlayer().getX();
//        boolean left = playerX

//        if(left){
//            setSprite = new Instruction_XL_SetSprite(null, 3);
//            setSpear = new Instruction_XL_SetSpear(null, "leftSlash");
//            spawn = new Instruction_XL_SpawnShadowEntity(null, "leftSlash");
//        }
//        else{
//            setSprite = new Instruction_XL_SetSprite(null, 4);
//            setSpear = new Instruction_XL_SetSpear(null, "rightSlash");
//            spawn = new Instruction_XL_SpawnShadowEntity(null, "rightSlash");
//        }
        Instruction_StartPathToPlayer path = new Instruction_StartPathToPlayer(null, speed);
        Instruction_WaitPathOver wait = new Instruction_WaitPathOver(null);
        Instruction_Attack attack = new Instruction_Attack(null, "default" + attackNum, 1, 1);
        spawnLeft.setNext(path);
        spawnRight.setNext(path);
//        setSprite.setNext(setSpear);
//        setSpear.setNext(spawn);
//        spawn.setNext(path);
        path.setNext(wait);
        wait.setNext(attack);
//        toRet.setInstructions(setSprite);
        toRet.setInstructions(lor);
        return toRet;
    }

    private static Routine_PlayerYBoolean topSpin(boolean single, int attackNum){
        double baseSpeed;
        if(single){
            baseSpeed = 800;
        }
        else{
            baseSpeed = 400;
        }
        Routine_PlayerYBoolean toRet = new Routine_PlayerYBoolean(null, false, 80);
        Routine reset = reset(!single);
        Instruction_XL_SetSprite initSprite;
        if(single){
            initSprite = new Instruction_XL_SetSprite(null, 0);
        }
        else{
            initSprite = new Instruction_XL_SetSprite(null, 12);
        }
        Instruction_StartPath initPath = new Instruction_StartPath(null, null, new Path_Polygon(new Vector(40, 78), false, baseSpeed));
        Instruction_WaitPathOver wait = new Instruction_WaitPathOver(null);
        Instruction_Attack attack = new Instruction_Attack(null, "default" + attackNum, 1, 15);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);
        Instruction_XL_SetSprite setSprite;
        Instruction_XL_SetSpear setSpear;
        Instruction_XL_SpawnShadowEntity spawnSpin;
        if(single){
            setSprite = new Instruction_XL_SetSprite(null, 5);
            setSpear = new Instruction_XL_SetSpear(null, "leftSpin");
            spawnSpin = new Instruction_XL_SpawnShadowEntity(null, null, "leftSpin");
        }
        else{
            setSprite = new Instruction_XL_SetSprite(null, 11);
            setSpear = new Instruction_XL_SetSpear(null, "dualSpin");
            spawnSpin = new Instruction_XL_SpawnShadowEntity(null, null, "dualSpin");
        }
        Instruction_StartPath clearingPath = new Instruction_StartPath(null, null, new Path_Polygon(new Vector(810, 78), false, baseSpeed/2));
        Instruction_WaitPathOver waitC = new Instruction_WaitPathOver(null);
//        Instruction_XL_DeleteShadowEntity delete = new Instruction_XL_DeleteShadowEntity(null);
        reset.setNext(initSprite);
        initSprite.setNext(initPath);
        initPath.setNext(wait);
        wait.setNext(attack);
        attack.setNext(toggle);
        toggle.setNext(setSprite);
        setSprite.setNext(setSpear);
        setSpear.setNext(spawnSpin);
        spawnSpin.setNext(clearingPath);
        clearingPath.setNext(waitC);
//        waitC.setNext(delete);
        toRet.setInstructions(reset);
        return toRet;
    }

//    @Override
//    public void setPolarVector(Vector p){
//        super.setPolarVector(p);
//        System.out.println(polar);
//    }

    private static Routine_XL_Default constructDefaults(int version){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
        int attackNum = version;
        double speed;
        boolean phase;
        switch(version){
            case 1:
            case 2:
                speed = 900;
                phase = false;
                break;
            case 3:
                speed = 600;
                phase = true;
                break;
            case 4:
                speed = 300;
                phase = true;
                break;
            default:
                speed = 0;
                phase = false;
        }
        double speedMulti = 1 + (((double)difficulty)/10);
        speed *= speedMulti;

        int t = 30 - difficulty;

        //default
        //initreset runs once
        Routine initReset = reset(phase);
        //main loop
        Routine pos1 = position(phase, speed/2);
        Routine thrust1 = thrust(phase, speed, attackNum);
        Routine reset1 = reset(phase);
        Instruction_Timer timer1 = new Instruction_Timer(null, t);
        Routine slash1 = slash(phase, speed, attackNum);
        Routine reset2 = reset(phase);
        Instruction_Timer timer2 = new Instruction_Timer(null, t);
        Routine slash2 = slash(phase, speed, attackNum);
        Routine reset3 = reset(phase);
        Instruction_Timer timer3 = new Instruction_Timer(null, t);
        Instruction_StartPathToY r1 = new Instruction_StartPathToY(null, 200, speed/2);
        Instruction_WaitPathOver w1 = new Instruction_WaitPathOver(null);
        Routine pos2 = position(phase, speed/2);
        Routine thrust2 = thrust(phase, speed, attackNum);
        Routine reset4 = reset(phase);
        Instruction_Timer timer4 = new Instruction_Timer(null, t);
        Routine slash3 = slash(phase, speed, attackNum);
        Routine reset5 = reset(phase);
        Instruction_Timer timer5 = new Instruction_Timer(null, t);
        Routine slash4 = slash(phase, speed, attackNum);
        Routine reset6 = reset(phase);
        Instruction_Timer timer6 = new Instruction_Timer(null, t);
        Routine slash5 = slash(phase, speed, attackNum);
        Routine reset7 = reset(phase);
        Instruction_Timer timer7 = new Instruction_Timer(null, t);
        Routine slash6 = slash(phase, speed, attackNum);
        Routine reset8 = reset(phase);
        Instruction_Timer timer8 = new Instruction_Timer(null, t);
        Instruction_StartPathToY r2 = new Instruction_StartPathToY(null, 200, speed/2);
        Instruction_WaitPathOver w2 = new Instruction_WaitPathOver(null);

        if(version != 3) {
            initReset.setNext(pos1);
        }
        else{
            Instruction_XL_SetPhase setPhase = new Instruction_XL_SetPhase(null);
            initReset.setNext(setPhase);
            setPhase.setNext(pos1);
        }
        pos1.setNext(thrust1);
        thrust1.setNext(reset1);
        reset1.setNext(timer1);
        timer1.setNext(slash1);
        slash1.setNext(reset2);
        reset2.setNext(timer2);
        timer2.setNext(slash2);
        slash2.setNext(reset3);
        reset3.setNext(timer3);
        timer3.setNext(r1);
        r1.setNext(w1);
        w1.setNext(pos2);
        pos2.setNext(thrust2);
        thrust2.setNext(reset4);
        reset4.setNext(timer4);
        timer4.setNext(slash3);
        slash3.setNext(reset5);
        reset5.setNext(timer5);
        timer5.setNext(slash4);
        slash4.setNext(reset6);
        reset6.setNext(timer6);
        timer6.setNext(slash5);
        slash5.setNext(reset7);
        reset7.setNext(timer7);
        timer7.setNext(slash6);
        slash6.setNext(reset8);
        reset8.setNext(timer8);
        timer8.setNext(r2);
        r2.setNext(w2);
        w2.setNext(pos1);

//        pos1.setNext(thrust1);
//        thrust1.setNext(reset1);
//        reset1.setNext(slash1);
//        slash1.setNext(reset2);
//        reset2.setNext(slash2);
//        slash2.setNext(reset3);
//        reset3.setNext(r1);
//        r1.setNext(w1);
//        w1.setNext(pos2);
//        pos2.setNext(thrust2);
//        thrust2.setNext(reset4);
//        reset4.setNext(slash3);
//        slash3.setNext(reset5);
//        reset5.setNext(slash4);
//        slash4.setNext(reset6);
//        reset6.setNext(slash5);
//        slash5.setNext(reset7);
//        reset7.setNext(slash6);
//        slash6.setNext(reset8);
//        reset8.setNext(r2);
//        r2.setNext(w2);
//        w2.setNext(pos1);

//        pos1.setNext(thrust1);
//        thrust1.setNext(slash1);
//        slash1.setNext(slash2);
//        slash2.setNext(r1);
//        r1.setNext(pos2);
//        pos2.setNext(thrust2);
//        thrust2.setNext(slash3);
//        slash3.setNext(slash4);
//        slash4.setNext(slash5);
//        slash5.setNext(slash6);
//        slash6.setNext(r2);
//        r2.setNext(pos1);

        Routine_PlayerYBoolean top = topSpin(!phase, attackNum);
        top.setNext(reset(phase));
//        return new Routine_XL_Default(null, null, pos1, topSpin(!phase, attackNum), 300);
        return new Routine_XL_Default(null, null, initReset, top, 300);
    }

    private static Routine_Boss_Spell constructBracelet(){
        Routine reset = reset(false);
        Instruction_XL_SetSprite setSprite = new Instruction_XL_SetSprite(null, 5);
        Instruction_Attack attack = new Instruction_Attack(null, null, "bSpawn", 120, 120);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, null, true);
        reset.setNext(setSprite);
        setSprite.setNext(attack);
        attack.setNext(toggle);

        double initHP = 300;
        return new Routine_Boss_Spell(null, null, reset, initHP, "Defense Technique: Bracelet", 17);
    }

    private static Routine_Boss_Spell constructNecklace(){
        Routine reset1 = reset(false);

        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
        double speed = 900;
        double speedMulti = 1 + (((double)difficulty)/10);
        speed *= speedMulti;

        //probably -1 will be fine
        Routine slash = slash(false, speed, 1);
//        slash.setRepeats(true);
        Routine reset2 = reset(false);
        Instruction_Timer t1 = new Instruction_Timer(null, 5);
        Instruction_XL_SetSprite spinSprite = new Instruction_XL_SetSprite(null, 5);
        Instruction_XL_SpawnShadowEntity spinSE = new Instruction_XL_SpawnShadowEntity(null, "leftSpin");
//        Instruction_Attack attack = new Instruction_Attack(null, "nSpawn", 120, 180);
        Instruction_Attack attack = new Instruction_Attack(null, "nSpawn", 120, 120);
        Instruction_Timer t2 = new Instruction_Timer(null, 120);

        reset1.setRepeats(true);
        slash.setRepeats(true);
        reset2.setRepeats(true);

        reset1.setNext(slash);
        slash.setNext(reset2);
        reset2.setNext(t1);
        t1.setNext(spinSprite);
        spinSprite.setNext(spinSE);
        spinSE.setNext(attack);
        attack.setNext(t2);
        t2.setNext(reset1);

        double initHP = 300;
        return new Routine_Boss_Spell(null, null, reset1, initHP, "Defense Technique: Necklace", 16);
    }

    private static Routine_Boss_Spell constructChoker(){
        Routine reset = reset(true);
        Instruction_XL_SetSprite spinSprite = new Instruction_XL_SetSprite(null, 11);
        Instruction_XL_SpawnShadowEntity spinSE = new Instruction_XL_SpawnShadowEntity(null, "dualSpin");
//        Instruction_Attack attack = new Instruction_Attack(null, "cSpawn", 120, 180);
        Instruction_Attack attack = new Instruction_Attack(null, "cSpawn", 120, 120);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);

        reset.setNext(spinSprite);
        spinSprite.setNext(spinSE);
        spinSE.setNext(attack);
        attack.setNext(toggle);

        double initHP = 300;
        return new Routine_Boss_Spell(null, null, reset, initHP, "Defense Technique: Choker", "life");
    }

    private static Routine_Boss_Spell constructPortal(){
        Routine reset = reset(true);
        Instruction_XL_SetSprite spinSprite = new Instruction_XL_SetSprite(null, 11);
        Instruction_XL_SpawnShadowEntity spinSE = new Instruction_XL_SpawnShadowEntity(null, "dualSpin");
        Instruction_Attack attack = new Instruction_Attack(null, "pSpawn", 600, 600);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);

        reset.setNext(spinSprite);
        spinSprite.setNext(spinSE);
        spinSE.setNext(attack);
        attack.setNext(toggle);

        double initHP = 300;
        return new Routine_Boss_Spell(null, null, reset, initHP, "Spear Art: Portal", 0);
    }

    private static Routine_Boss_Attack constructBehaviors(){
        Routine_Boss_Attack default1 = constructDefaults(1);

        Routine_Boss_Spell bracelet = constructBracelet();
        default1.setNext(bracelet);

        Routine_Boss_Attack default2 = constructDefaults(2);
        bracelet.setNext(default2);

        Routine_Boss_Spell necklace = constructNecklace();
        default2.setNext(necklace);

        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
        if(difficulty > 4){
            Routine_Boss_Attack default3 = constructDefaults(3);
            necklace.setNext(default3);

            Routine_Boss_Spell choker = constructChoker();
            default3.setNext(choker);

            if(difficulty > 6){
                Routine_Boss_Attack default4 = constructDefaults(4);
                choker.setNext(default4);

                Routine_Boss_Spell portal = constructPortal();
                default4.setNext(portal);
            }
        }

        return default1;
    }
}