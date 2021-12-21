import java.util.ArrayList;
import java.util.Arrays;

public class Enemy_Boss_Lilith extends Enemy_Boss {

    private Vector centerpos= new Vector(850/2, 850/2);
    private double td;

    private EP_Blade[] blades = new EP_Blade[12];

    //                 idle    0
    private SpriteInfo idle2;//1
    private SpriteInfo left; //2
    private SpriteInfo right;//3
    private int spritePos = 0;

//    private boolean phase = false;
    public Enemy_Boss_Lilith(){
//        super(null);
        super(constructBehaviors());
//        super(constructImpassibleField());
//        super(constructDefaults(5));
//        super(constructResolveForEucatastrophe());
    }

    @Override
    public void update() {
        super.update();

        if (spritePos == 1 || spritePos == 2 || spritePos == 3) {
            double xv = velocity.getA();
            if (xv > 0.1) {
                changeSprite(3);
            } else if (xv < -0.1) {
                changeSprite(2);
            } else {
                changeSprite(1);
            }
        }
    }

    @Override
    public void spawnAttack(){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();

        switch(attackName){
            case "default1":
                attackDefault(1, difficulty);
                break;
            case "default2":
                attackDefault(2, difficulty);
                break;
            case "default3":
                attackDefault(3, difficulty);
                break;
            case "default4":
                attackDefault(4, difficulty);
                break;
            case "default5":
                attackDefault(5, difficulty);
                break;

            case "lsSpawn":
                attackLSSpawn();
                break;
            case "roneSpawn":
                attackRONESpawn();
                break;
            case "gosSpawn":
                attackGOSSpawn();
                break;
            case "ifSpawn":
                attackIFSpawn(difficulty);
                break;
            case "reSpawn":
                attackRESpawn();
                break;
            case "reAttack":
                attackREAttack();
                break;
        }
    }

    //1 180 180
    //2 480 480
    //3 600 600
    //4 360 360
    //5 300 300

    private void attackDefault(int version, int difficulty){
        String radialColor;
        double baseSpeed;
        switch(version){
            case 1:
                radialColor = "white";
                baseSpeed = 150;
                break;
            case 2:
                radialColor = "white";
                baseSpeed = 150;
                break;
            case 3:
                radialColor = "grey";
                baseSpeed = 175;
                break;
            case 4:
                radialColor = "grey";
                baseSpeed = 175;
                break;
            case 5:
                radialColor = "red";
                baseSpeed = 200;
                break;
            default:
                radialColor = "red";
                baseSpeed = 200;
                break;
        }
        baseSpeed *= (1 + ((double)difficulty/40));

//        //radial
//        int d;
//        if(difficulty < 7){
//            d = 2;
//        }
////        else if(difficulty == 7){
////            d = 2;
////        }
//        else{
//            d = 1;
//        }
        int num;
//        switch(difficulty){
//            case 5:
//            case 6:
//                num = 1;
//                break;
//            case 7:
//                num = 2;
//                break;
//            case 8:
//                num = 3;
//                break;
//            default:
//                num = 1;
//                break;
//        }
        if(difficulty >= 7){
            num = 2;
        }
        else{
            num = 1;
        }
//        if(attackTimer % d == 0){
        for(int i = 0; i < num; i++) {
            double speed = baseSpeed * (1 + Math.random());
            double angle = 360 * Math.random();
            toSpawn.add(new EP_Orb_Medium(this, new Vector(angle, speed), radialColor));
        }
//        }

        //special

        //version 1 180 180
        if(version == 1){
            if(attackTimer == 180){
                td = Math.random() * 360;
                for(int i = 0; i < 5; i++){
                    double angle = td + (i * (360/5));
                    double speed = baseSpeed;
                    toSpawn.add(new EP_Orb_Large(this, new Vector(angle, speed), "red"));
                }
            }
            else if(attackTimer == 170){
                for(int i = 0; i < 5; i++){
                    double angle = td + (i * (360/5)) + 20;
                    double speed = baseSpeed;
                    toSpawn.add(new EP_Orb_Large(this, new Vector(angle, speed), "red"));
                }
            }
            else if(attackTimer == 160){
                for(int i = 0; i < 5; i++){
                    double angle = td + (i * (360/5)) + 40;
                    double speed = baseSpeed;
                    toSpawn.add(new EP_Orb_Large(this, new Vector(angle, speed), "red"));
                }
            }
            else if(attackTimer == 150){
                for(int i = 0; i < 5; i++){
                    double angle = td + (i * (360/5)) + 60;
                    double speed = baseSpeed;
                    toSpawn.add(new EP_Orb_Large(this, new Vector(angle, speed), "red"));
                }
            }
            else if(attackTimer == 140){
                for(int i = 0; i < 5; i++){
                    double angle = td + (i * (360/5)) + 80;
                    double speed = baseSpeed;
                    toSpawn.add(new EP_Orb_Large(this, new Vector(angle, speed), "red"));
                }
            }
        }

        //version 2 480 480
        if(version == 2){
             if(attackTimer == 480){
                 double angle = Vector.getAngle(this, Player.getThePlayer());
                 for(int i = 0; i < 10; i++){
                     double spawnAngle = angle + (36 * i);
                     toSpawn.add(new EP_Laser_Lilith_Default2(this, spawnAngle));
                 }
             }
        }

        //version 3 600 600
        if(version == 3){
            if(attackTimer % 2 == 0 && attackTimer >= 60) {
                //sin wave 600 long
                double baseAngle = ((double)72/4) * (Math.sin(((2 * Math.PI * attackTimer) / 540)- 60));
                for(int i = 0; i < 5; i++){
                    double angle = baseAngle + (i * (360/5));
                    double speed = 750;
                    toSpawn.add(new EP_Orb_Small(this, new Vector(angle, speed), "white"));
                }
            }
        }

        //version 4 360 360
        if(version == 4){
            if(attackTimer == 360){
                for(int i = 0; i < difficulty * 6; i++){
                    double angle = 360 * Math.random();
                    double speed = 75 + (Math.random() * 125);
                    toSpawn.add(new EP_Orb_Large(this, new Vector(angle, speed), "grey"));
                }
            }
        }

        //version 5
        //300 300
        if(version == 5){
            String c = "white";
            if(attackTimer == 300){
                double r = Math.random() * 10;
                for(double i = r; i < 360 + r; i += 10){
                    toSpawn.add(new EP_Orb_Small(this, new Vector(i, 220), c));
                }
            }
            if(attackTimer <= 300 && attackTimer >= 190 && attackTimer % 10 == 0){
                int index = (int)(300 - attackTimer)/10;
//                System.out.println(attackTimer + " " + index);
                blades[index].botPointTarget();
            }
            if(attackTimer > 20 && attackTimer % 20 == 0){
                double r = Math.random() * 10;
                boolean p = false;
                for(double i = r; i < 360 + r; i += 10){
                    double a = 20 + Math.random() * 70;
                    if(p){
                        a = 0 - a;
                    }

                    EP_Orb_Small toAdd = new EP_Orb_Small(this, new Vector(i, 220), c);
                    Instruction_Timer timer = new Instruction_Timer(null, 60 + (Math.random() * 30));
                    Instruction_SetPolar setPolar = new Instruction_SetPolar(null, new Vector(i + a, 220));
                    timer.setNext(setPolar);
                    toAdd.setNewBehavior(timer);
                    toSpawn.add(toAdd);
                    p = !p;
                }
            }
        }
    }

    //1 1 once
    private void attackLSSpawn(){
        EP_Fragment toSpawn = new EP_Fragment(this, new Vector());
        toSpawn.newPath(new Path_Polygon(centerpos, false, 200));
        Instruction_WaitPathOver waitpath = new Instruction_WaitPathOver(toSpawn, null);
        Instruction_Attack attack = new Instruction_Attack(toSpawn, "lsSpawn", 60, 60);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(toSpawn, true);
        waitpath.setNext(attack);
        attack.setNext(toggle);
        toSpawn.setNewBehavior(waitpath);
        this.toSpawn.add(toSpawn);
    }

    //1 1 once
    private void attackRONESpawn(){
        Vector topLeft = new Vector(0, 0);
        Vector topRight = new Vector(850, 0);
        Vector botRight = new Vector(850, 1000);
        Vector botLeft = new Vector(0, 1000);

        EP_Fragment tls = new EP_Fragment(topLeft, new Vector());
        EP_Fragment brs = new EP_Fragment(botRight, new Vector());

        ArrayList<Vector> points1 = new ArrayList<>();
        ArrayList<Vector> points2 = new ArrayList<>();

        points1.add(topRight);
        points1.add(botRight);
        points1.add(botLeft);
        points1.add(topLeft);

        points2.add(botLeft);
        points2.add(topLeft);
        points2.add(topRight);
        points2.add(botRight);

        Path_Polygon path1 = new Path_Polygon(points1, true, 850);
        Path_Polygon path2 = new Path_Polygon(points2, true, 850);

        Instruction_Timer initTimer1 = new Instruction_Timer(null, 60);
        Instruction_StartPath startPath1 = new Instruction_StartPath(null, path1);
        Instruction_Attack attack1 = new Instruction_Attack(null, "roneSpawn", 60, 60);
        Instruction_ToggleAttack toggle1 = new Instruction_ToggleAttack(null, true);

        Instruction_Timer initTimer2 = new Instruction_Timer(null, 60);
        Instruction_StartPath startPath2 = new Instruction_StartPath(null, path2);
        Instruction_Attack attack2 = new Instruction_Attack(null, "roneSpawn", 60, 60);
        Instruction_ToggleAttack toggle2 = new Instruction_ToggleAttack(null, true);

        initTimer1.setNext(startPath1);
        startPath1.setNext(attack1);
        attack1.setNext(toggle1);

        initTimer2.setNext(startPath2);
        startPath2.setNext(attack2);
        attack2.setNext(toggle2);

        tls.setNewBehavior(initTimer1);
        brs.setNewBehavior(initTimer2);

        toSpawn.add(tls);
        toSpawn.add(brs);
    }

    //1 1 once
    private void attackGOSSpawn(){
        EP_Fragment toSpawn = new EP_Fragment(this, new Vector());
        toSpawn.newPath(new Path_Polygon(centerpos, false, 200));
        Instruction_WaitPathOver waitpath = new Instruction_WaitPathOver(toSpawn, null);
        Instruction_Attack attack = new Instruction_Attack(toSpawn, "gosSpawn", 60, 60);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(toSpawn, true);
        waitpath.setNext(attack);
        attack.setNext(toggle);
        toSpawn.setNewBehavior(waitpath);
        this.toSpawn.add(toSpawn);
    }

    //300, 300
    private void attackIFSpawn(int difficulty){
        //difficulty per row
        if(attackTimer == 300) {
            double spacing = Driver_Game.getWidth() / (difficulty + 1);
            for (double x = spacing; x < 845; x += spacing) {
                for (double y = spacing; y < 900; y += spacing) {
                    Vector targetPos = new Vector(x, y);
                    toSpawn.add(new EP_Orb_Lilith_IF_Medium(this, targetPos));
                }
            }
        }
//        else if(tick == 300){
//            for(EP_Blade blade : blades){
//                blade.botPointTarget();
//            }
//        }
    }

    //1 1 once
    private void attackRESpawn(){
        EP_Fragment toSpawn = new EP_Fragment(this, new Vector());
        toSpawn.newPath(new Path_Polygon(new Vector(0, 0), false, 200));
        toSpawn.setNewBehavior(new Instruction_WaitPathOver(null, EP_Fragment.constructREBehavior(0)));
        this.toSpawn.add(toSpawn);

        toSpawn = new EP_Fragment(this, new Vector());
        toSpawn.newPath(new Path_Polygon(new Vector(850, 0), false, 200));
        toSpawn.setNewBehavior(new Instruction_WaitPathOver(null, EP_Fragment.constructREBehavior(850)));
        this.toSpawn.add(toSpawn);

        //start rotation
        for(EP_Blade blade : blades){
            blade.changePhase(2);
        }
    }

    //not continuous
    //300 300
    private void attackREAttack(){
        if(attackTimer % 30 == 0){
            double angle = (attackTimer * 17) % 360;
            for(int i = 0; i < 360; i += 120){
                for(int speed = 300; speed >= 200; speed -= 50){
                    Vector polar = new Vector(angle + i, speed);
                    toSpawn.add(new EP_Oval(this, polar, "red"));
                }
            }
        }
        if(attackTimer % 60 == 0){
            double angle = (attackTimer * 2) % 360;
            for(int i = 0; i < 360; i += 72){
                Vector polar = new Vector(angle + i, 200);
                toSpawn.add(new EP_Orb_Medium(this, polar, "white"));
            }
        }

        if(attackTimer == 1){
            for(EP_Blade blade : blades){
                blade.rto();
            }
        }
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 3;
        Vector offset = new Vector(1.2, -3);
        idle = new SpriteInfo("Boss_Lilith1_idle1", offset, rotation, size);
        idle.setNext(new SpriteInfo("Boss_Lilith1_idle2", offset, rotation, size,
                new SpriteInfo("Boss_Lilith1_idle3", offset, rotation, size,
                        new SpriteInfo("Boss_Lilith1_idle4", offset, rotation, size, idle))));

        idle2 = new SpriteInfo("Boss_Lilith2_idle1", offset, rotation, size);
        idle2.setNext(new SpriteInfo("Boss_Lilith2_idle2", offset, rotation, size,
                new SpriteInfo("Boss_Lilith2_idle3", offset, rotation, size,
                        new SpriteInfo("Boss_Lilith2_idle4", offset, rotation, size, idle2))));

        offset = new Vector(-1, -3);
        left = new SpriteInfo("Boss_Lilith2_left1", offset, rotation, size);
        left.setNext(new SpriteInfo("Boss_Lilith2_left2", offset, rotation, size,
                new SpriteInfo("Boss_Lilith2_left3", offset, rotation, size,
                        new SpriteInfo("Boss_Lilith2_left4", offset, rotation, size, left))));

        offset = new Vector(0, -3);
        right = new SpriteInfo("Boss_Lilith2_right1", offset, rotation, size);
        right.setNext(new SpriteInfo("Boss_Lilith2_right2", offset, rotation, size,
                new SpriteInfo("Boss_Lilith2_right3", offset, rotation, size,
                        new SpriteInfo("Boss_Lilith2_right4", offset, rotation, size, right))));

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
        switch(newPos){
            case 0:
                this.sprite = idle;
                break;
            case 1:
                this.sprite = idle2;
                break;
            case 2:
                this.sprite = left;
                break;
            case 3:
                this.sprite = right;
                break;
        }
        this.spritePos = newPos;
    }

    public void phase2(){
        changeSprite(2);
        spawnBlades();
    }

    private void spawnBlades(){
        int angleChange = 360/12;
        int angle = 0;
        for(int i = 0; i < 12; i++){
            double dist = 75;
            Vector spawnPos = Vector.add(this, Vector.polarToVelocity(new Vector(angle, dist)));
            blades[i] = new EP_Blade(spawnPos, this);
            angle += angleChange;
        }
//        for(EP_Blade blade : blades){
//            toSpawn.add(blade);
//        }
        //o k
        toSpawn.addAll(Arrays.asList(blades));
    }

    private static Routine_Boss_Attack constructDefaults(int version){
        int rnatimer;
        int hp;
        switch(version){
            case 1:
                rnatimer = 180;
                hp = 300;
                break;
            case 2:
                rnatimer = 480;
                hp = 300;
                break;
            case 3:
                rnatimer = 600;
                hp = 300;
                break;
            case 4:
                rnatimer = 360;
                hp = 300;
                break;
            case 5:
                rnatimer = 300;
                hp = 300;
                break;
            default:
                rnatimer = 0;
                hp = 400;
                break;
        }
        Instruction_Attack attack = new Instruction_Attack(null, "default" + version, rnatimer, rnatimer);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);
        attack.setNext(toggle);

        if(version != 5){
            return new Routine_Boss_Attack(null, null, attack, hp);
        }
        else{
            return new Routine_Boss_Attack(null, null, new Instruction_Lilith_Phase2(null, attack), hp);
        }
    }

    private static Routine_Boss_Spell constructLowstar(){
        Instruction_Attack attack = new Instruction_Attack(null, "lsSpawn", 1, 1);
        int hp = 300;
        return new Routine_Boss_Spell(null, null, attack, hp, "Boundary Introduction: Lowstar", 32);
    }

    private static Routine_Boss_Spell constructRoomOfNoEscape(){
        Instruction_Attack attack = new Instruction_Attack(null, "roneSpawn", 1, 1);
        int hp = 300;
        return new Routine_Boss_Spell(null, null, attack, hp, "Boundary Introduction: Room Of No Escape", 34);
    }

    private static Routine_Boss_Spell constructGlimpseOfSoulscape(){
        Instruction_Attack attack = new Instruction_Attack(null, "gosSpawn", 1, 1);
        int hp = 300;
        return new Routine_Boss_Spell(null, null, attack, hp, "Boundary Introduction: Glimpse Of Soulscape", 35);
    }

    private static Routine_Boss_Spell constructImpassibleField(){
        Instruction_Attack attack = new Instruction_Attack(null, "ifSpawn", 300, 300);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);
        attack.setNext(toggle);
        int hp = 500;
        return new Routine_Boss_Spell(null, null, attack, hp, "Hell Edict: Impassible Field", "life");
    }

    private static Routine_Boss_Spell constructResolveForEucatastrophe(){
        Instruction_Attack initSpawn = new Instruction_Attack(null, "reSpawn", 1, 1);
        Instruction_Timer initTimer = new Instruction_Timer(null, 180);

        //loop
        Instruction_Attack attack = new Instruction_Attack(null, "reAttack", 300, 300);
        Instruction_StartPolygonPathRandom path = new Instruction_StartPolygonPathRandom(null, 1, 100, 750, 100, 300, 4);
        Instruction_Timer timer = new Instruction_Timer(null, 480);

        initSpawn.setNext(initTimer);
        initTimer.setNext(attack);
        attack.setNext(path);
        path.setNext(timer);
        timer.setNext(attack);

        int hp = 1000;
        return new Routine_Boss_Spell(null, null, initSpawn, hp, "Resolve For Eucatastrophe", 0);
    }

    private static Behavior constructBehaviors(){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
        if(difficulty < 5){
            return new Instruction_DriverEndLevel(null);
        }

        Routine_Boss_Attack default1 = constructDefaults(1);
        Routine_Boss_Spell lowstar = constructLowstar();
        Routine_Boss_Attack default2 = constructDefaults(2);
        Routine_Boss_Spell room = constructRoomOfNoEscape();
        Routine_Boss_Attack default3 = constructDefaults(3);
        Routine_Boss_Spell glimpse = constructGlimpseOfSoulscape();
        Routine_Boss_Attack default4 = constructDefaults(4);
        Routine_Boss_Spell field = constructImpassibleField();

        default1.setNext(lowstar);
        lowstar.setNext(default2);
        default2.setNext(room);
        room.setNext(default3);
        default3.setNext(glimpse);
        glimpse.setNext(default4);
        default4.setNext(field);

        if(difficulty == 8){
            Routine_Boss_Attack default5 = constructDefaults(5);
            Routine_Boss_Spell finale = constructResolveForEucatastrophe();

            field.setNext(default5);
            default5.setNext(finale);
//            return default5;
        }

        return default1;
    }
}
