import java.awt.*;

public class Enemy_Boss_Zephres extends Enemy_Boss{

    public Enemy_Boss_Zephres(){
//        super(null);
        super(constructBehaviors());
        this.size = 20;
    }

    @Override
    public void spawnAttack(){
        switch(attackName){
            case "default":
                attackDefault();
                break;
            case "spawnE"://Flame: Explosion
                attackSpawnE();
                break;
            case "spawnC"://Flame: Combustion
                attackSpawnC();
                break;
            case "spawnA"://Flame: Ash
                attackSpawnA();
                break;
            case "spawnIF"://Hell Edict: Impossible Field
                attackSpawnIF();
                break;
            case "spawnAS"://Smothering Burn: Asphyxiation
                attackSpawnAS();
                break;
            case "spawnMD"://Smothering Burn: Mercurial Disposition
                attackSpawnMD();
                break;
            case "spawnRF1"://Smothering Burn: Reign Of Fire
                attackSpawnRF(1);
                break;
            case "spawnRF2":
                attackSpawnRF(2);
                break;
            case "spawnRF3":
                attackSpawnRF(3);
                break;
            case "spawnBBRadial"://Beyond The Boundary
                attackSpawnBBR();
                break;
            case "spawnBBMines":
                attackSpawnBBM();
                break;
            case "spawnBBHoming":
                attackSpawnBBH();
                break;
            case "spawnBBLasers":
                attackSpawnBBL();
        }
    }

    //300 300
    private void attackDefault(){
        //radial
//        if(attackTimer % 2 == 0){
            toSpawn.add(new EP_Orb_Medium(this, new Vector(Math.random() * 360, 100 + (Math.random() * 100)), "red"));
//        }
        //lines
        if(attackTimer == 300){
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            for(int a = 0; a < 360; a += (360/10)){
                double angle = baseAngle + a;
                for(int maxSpeed = 1000; maxSpeed >= 100; maxSpeed -= 100){
                    toSpawn.add(new EP_Flame_Acceleration(this, new Vector(angle, maxSpeed)));
                }
            }
        }
    }

    //600 600
    private void attackSpawnE(){
        //init big
        if(attackTimer == 600){
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            for(double a = 0; a < 360; a += ((double)360/22)){
                double angle = baseAngle + a;
                toSpawn.add(new EP_Orb_Large(this, new Vector(angle, 200), "yellow"));
            }
        }

        //shotgun circles
        if(attackTimer <= 500 && attackTimer > 200 && attackTimer % 50 == 0){
            //30 possible configurations, only allow mid 20
            int numCircles = (int)(Math.random() * 21);
            numCircles += 5;
            int orbsPerCircle = 30 - numCircles;
            //spokes
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            double avgSpeed = 200 + (Math.random() * 50);
            for(int i = 0; i < numCircles; i++){
                double angleAdd = i * ((double)360/numCircles);
                double angle = baseAngle + angleAdd;
                //the circle
//                double avgSpeed = 250 + (Math.random() * 150);

                double circ = orbsPerCircle * 30;
                double rad = circ/(Math.PI * 2);
                Vector mid = Vector.add(Vector.polarToVelocity(new Vector(angle, avgSpeed)), this);
                double offset = Math.random() * 360;
                for(int j = 0; j < orbsPerCircle; j++){
                    //position along circle
//                    double oAngleAdd = i * ((double)360/orbsPerCircle);
                    double oAngleAdd = j * ((double)360/orbsPerCircle);
                    double oAngle = offset + oAngleAdd;
                    Vector gotoPos = Vector.add(mid, Vector.polarToVelocity(new Vector(oAngle, rad)));

                    //individual bullets
                    double speed = Vector.getDistance(this, gotoPos);
                    double setAngle = Vector.getAngle(this, gotoPos);
                    toSpawn.add(new EP_Orb_Medium(this, new Vector(setAngle, speed), "orange"));
                }
            }
        }

        //random radial shotgun
        if(attackTimer == 100){
            for(int i = 0; i < 200; i++){
                double angle = Math.random() * 360;
                double speed = 50 + (Math.random() * 600);
                toSpawn.add(new EP_Orb_Medium(this, new Vector(angle, speed), "red"));
            }
        }
    }

    //200 200
    private void attackSpawnC(){
        //outer
        if(attackTimer == 200){
            //t/b
            for(double i = 0; i <= 850; i+= ((double)850/6)){
                Vector t = new Vector(i, 0);
                Vector b = new Vector(i, 1000);

                EP_Orb_Medium top = new EP_Orb_Medium(t, new Vector(), "red");
                EP_Orb_Medium bot = new EP_Orb_Medium(b, new Vector(), "red");

                Instruction_Timer timer = new Instruction_Timer(null, null, 30);
                Instruction_SetPolar polar = new Instruction_SetPolar(null, null, new Vector(Vector.getAngle(top, Player.getThePlayer()), 300));
                timer.setNext(polar);

                top.setNewBehavior(timer);

                timer = new Instruction_Timer(null, null, 30);
                polar = new Instruction_SetPolar(null, null, new Vector(Vector.getAngle(bot, Player.getThePlayer()), 200));
                timer.setNext(polar);

                bot.setNewBehavior(timer);

                toSpawn.add(top);
                toSpawn.add(bot);
            }
            //l/r
            for(double i = 0; i <= 1000; i+= ((double)1000/8)){
                Vector l = new Vector(0, i);
                Vector r = new Vector(850, i);

                EP_Orb_Medium left = new EP_Orb_Medium(l, new Vector(), "red");
                EP_Orb_Medium right = new EP_Orb_Medium(r, new Vector(), "red");

                Instruction_Timer timer = new Instruction_Timer(null, null, 30);
                Instruction_SetPolar polar = new Instruction_SetPolar(null, null, new Vector(Vector.getAngle(left, Player.getThePlayer()), 300));
                timer.setNext(polar);

                left.setNewBehavior(timer);

                timer = new Instruction_Timer(null, null, 30);
                polar = new Instruction_SetPolar(null, null, new Vector(Vector.getAngle(right, Player.getThePlayer()), 200));
                timer.setNext(polar);

                right.setNewBehavior(timer);

                toSpawn.add(left);
                toSpawn.add(right);
            }
        }
        //explosions
        if(attackTimer <= 120 && attackTimer % 15 == 0){
            Vector spawnPos;
            do {
                spawnPos = new Vector(100 + (Math.random() * 650), 100 + (Math.random() * 800));
            }
            while(Vector.getDistance(Player.getThePlayer(), spawnPos) < 500);

            //1 of each color
            for(int i = 0; i < 12; i++){
                toSpawn.add(new EP_Orb_Small(spawnPos, new Vector(Math.random() * 360, 200 + Math.random() * 200), "yellow"));
                toSpawn.add(new EP_Orb_Small(spawnPos, new Vector(Math.random() * 360, 200 + Math.random() * 200), "orange"));
            }
        }
    }

    //60 60
    private void attackSpawnA(){
        //fall
        double xpos = Math.random() * 850;
        double ypos = Math.random() * 300;
        double speed = 150 + (Math.random() * 150);
        double angle = -20 + (Math.random() * 40);
        Vector spawnPos = new Vector(xpos, ypos);
        Vector polar = new Vector(angle, speed);
        toSpawn.add(new EP_Orb_Zephres_A(spawnPos, polar));
        //orbit
        if(attackTimer % 5 == 0){
            toSpawn.add(new EP_Flame_Ember_Orbit(this));
        }
    }

    //360 360
    private void attackSpawnIF(){
        //field
        if(attackTimer == 360){
            double xdist = (double)850/6;
            double ydist = (double)1000/7;
            for(double x = xdist; x < 845; x+=xdist){
                for(double y = ydist; y < 995; y+=ydist){
                    Vector midPos = new Vector(x, y);
                    double offsetAngle = Math.random() * 360;
                    double offsetDist = Math.random() * 100;
                    Vector spawnPos = Vector.add(midPos, Vector.polarToVelocity(new Vector(offsetAngle, offsetDist)));
                    toSpawn.add(new EP_BurningSpace(spawnPos));
                }
            }
        }
        //radial
        if(attackTimer % 30 == 0){
            if(attackTimer % 60 == 0) {
                double bAngle = Vector.getAngle(this, Player.getThePlayer());
                for (int i = 0; i < 20; i++) {
                    double angle = bAngle + (i * ((double) 360 / 20));
                    for (int j = 0; j < 3; j++) {
                        double speed = 200 + (100 * j);
                        toSpawn.add(new EP_Oval(this, new Vector(angle, speed), "red"));
                    }
                }
            }
            else{
                double bAngle = Math.random() * 360;
                for (int i = 0; i < 20; i++) {
                    double angle = bAngle + (i * ((double) 360 / 20));
                    for (int j = 0; j < 2; j++) {
                        double speed = 300 + (100 * j);
                        toSpawn.add(new EP_Oval(this, new Vector(angle, speed), "yellow"));
                    }
                }
                bAngle = Math.random() * 360;
                for (int i = 0; i < 20; i++) {
                    double angle = bAngle + (i * ((double) 360 / 20));
                    for (int j = 0; j < 3; j++) {
                        double speed = 200 + (50 * j);
                        toSpawn.add(new EP_Oval(this, new Vector(angle, speed), "orange"));
                    }
                }
            }
        }
    }

    //480 480
    private void attackSpawnAS(){
        //fireworks
        if(attackTimer == 480){
            for(int i = 0; i < 40; i++){
                double angle = Math.random() * 360;
                double speed = 50 + (Math.random() * 300);
                toSpawn.add(new EP_Orb_Zephres_AS_Medium(this, new Vector(angle, speed)));
            }
        }

        //lines
        if(attackTimer <= 300 && attackTimer >= 60 && attackTimer % 20 == 0){
//            double minAngleDist = 20;
//            double maxAngleDist = 180;
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            //1 - 13
            int index = 14 - (((int)attackTimer/20)-2);
            //0 - 160
            double angleDiffDiff = (Math.pow(.75, index) * 177.7);
            //5 - 180
            double angleDiff = angleDiffDiff + 2;
            double angle1 = baseAngle + angleDiff;
            double angle2 = baseAngle - angleDiff;
            for(int speed = 100; speed <= 1000; speed += 100){
                toSpawn.add(new EP_Oval(this, new Vector(angle1, speed), "orange"));
                toSpawn.add(new EP_Oval(this, new Vector(angle2, speed), "orange"));
            }
        }
    }

    //30 30
    private void attackSpawnMD(){
        //random radial
        if(attackTimer % 2 == 0){
            double angle = Math.random() * 360;
            double speed = 200 + (Math.random() * 300);
            toSpawn.add(new EP_Orb_Medium(this, new Vector(angle, speed), "orange"));
            if(Math.random() > .5){
                angle = Math.random() * 360;
                speed = 200 + (Math.random() * 300);
                toSpawn.add(new EP_Orb_Medium(this, new Vector(angle, speed), "yellow"));
            }
        }
        //follow bs
        if(attackTimer == 30){
//            toSpawn.add(new EP_BurningSpace(Player.getThePlayer()));
            toSpawn.add(new EP_BurningSpace_Zephres_MD(Player.getThePlayer()));
        }
    }

    //60 60 all
    private void attackSpawnRF(int ver){
        if(ver == 1){
            for(int i = 0; i < 3; i++) {
                double angle = Math.random() * 360;
                double speed = 100 + (Math.random() * 200);
                toSpawn.add(new EP_Oval(this, new Vector(angle, speed), "yellow"));
            }
        }
        else if(ver == 2){
            if(attackTimer % 30 == 0){
                double angle = Vector.getAngle(this, Player.getThePlayer());
                for(int i = 0; i < 5; i++){
                    double speed = 100 + (100 * i);
                    toSpawn.add(new EP_Oval(this, new Vector(angle, speed), "orange"));
                }
                for(int i = 0; i < 10; i++){
                    angle = Math.random() * 360;
                    for(int j = 0; j < 5; j++){
                        double speed = 100 + (100 * j);
                        toSpawn.add(new EP_Oval(this, new Vector(angle, speed), "orange"));
                    }
                }
            }
        }
        else if(ver == 3){
            for(int i = 0; i < 3; i++) {
                double angle = Math.random() * 360;
                double speed = 200 + (Math.random() * 500);
                toSpawn.add(new EP_Oval_Zephres_Gravity(this, new Vector(angle, speed)));
            }
        }
    }

    //n n
    private void attackSpawnBBR(){
        double angle = Math.random() * 360;
        double speed = 50 + (Math.random() * 150);
        toSpawn.add(new EP_Flame(this, new Vector(angle, speed)));
    }

    //1 n
    //try 1 10
    private void attackSpawnBBM(){
        toSpawn.add(new EP_BurningSpace_Zephres_BBM(this));
    }

    //360 360
    private void attackSpawnBBH(){
        if(attackTimer % 60 == 0 && attackTimer != 60){
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            for(int i = 0; i < 10; i++){
                double angle = baseAngle + (i * ((double)360/10));
                for(int j = 0; j < 10; j++){
                    double speed = 50 + (j * 50);
                    toSpawn.add(new EP_Orb_Medium(this, new Vector(angle, speed), "red"));
                }
            }
        }
        else if(attackTimer == 60){
            double baseAngle = Vector.getAngle(this, Player.getThePlayer());
            for(int i = 0; i < 10; i++){
                double angle = baseAngle + (i * ((double)360/10));
                double speed = 550;
                toSpawn.add(new EP_Orb_Large(this, new Vector(angle, speed), "red"));
            }
        }
    }

    //60 60
    private void attackSpawnBBL(){
        for(int i = 0; i < 3; i++) {
            double angle = Math.random() * 360;
            double speed = 400 + (Math.random() * 200);
            toSpawn.add(new EP_Laser(this, new Vector(angle, speed), 50, Color.orange));
        }
    }

    public void throwTracer(Vector target){
        int orbsPerCircle = 10;
        double angle = Vector.getAngle(this, target);
        //the circle

        double avgSpeed = 500;
        double circ = orbsPerCircle * 30;
        double rad = circ/(Math.PI);
        Vector mid = Vector.add(Vector.polarToVelocity(new Vector(angle, avgSpeed)), this);
        double offset = Math.random() * 360;
        for(int j = 0; j < orbsPerCircle; j++){
            //position along circle
            double oAngleAdd = j * ((double)360/orbsPerCircle);
            double oAngle = offset + oAngleAdd;
            Vector gotoPos = Vector.add(mid, Vector.polarToVelocity(new Vector(oAngle, rad)));
            //individual bullets
            double speed = Vector.getDistance(this, gotoPos);
            double setAngle = Vector.getAngle(this, gotoPos);
            toSpawn.add(new EP_Orb_Medium(this, new Vector(setAngle, speed), "yellow"));
        }
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 3;
        Vector offset = new Vector(0, 0);
        idle = new SpriteInfo("Boss_Zephres_idle1", offset, rotation, size);
        idle.setNext(new SpriteInfo("Boss_Zephres_idle2", offset, rotation, size,
                new SpriteInfo("Boss_Zephres_idle3", offset, rotation, size,
                        new SpriteInfo("Boss_Zephres_idle4", offset, rotation, size, idle))));

        this.sprite = idle;
    }

    @Override
    protected void updateSprite(){
        if(tick % 10 == 0)
            this.sprite = this.sprite.getNext();
    }

    private static Routine_Boss_Attack constructDefault(){
        Instruction_Attack attack = new Instruction_Attack(null, "default", 300, 300);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);
        Instruction_TimerRandom tRandom = new Instruction_TimerRandom(null, 60, 300);
        Instruction_StartPolygonPathRandom pRandom = new Instruction_StartPolygonPathRandom(null, 1, 30, 820, 30, 300, 1);

        attack.setNext(toggle);
        toggle.setNext(tRandom);
        tRandom.setNext(pRandom);
        pRandom.setNext(tRandom);

        int hp = 300;

        return new Routine_Boss_Attack(null, null, attack, hp);
    }

    private static Routine_Boss_Spell constructExplosion(){
        Instruction_Attack attack = new Instruction_Attack(null, "spawnE", 600, 600);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);

        attack.setNext(toggle);

        int hp = 600;

        return new Routine_Boss_Spell(null, null, attack, hp, "Flame: Explosion", 21);
    }

    private static Routine_Boss_Spell constructCombustion(){
        Instruction_Attack attack = new Instruction_Attack(null, "spawnC", 200, 200);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);

        attack.setNext(toggle);

        int hp = 400;

        return new Routine_Boss_Spell(null, null, attack, hp, "Flame: Combustion", 23);
    }

    private static Routine_Boss_Spell constructAsh(){
        Instruction_Attack attack = new Instruction_Attack(null, "spawnA", 60, 60);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);

        attack.setNext(toggle);

        int hp = 550;

        return new Routine_Boss_Spell(null, null, attack, hp, "Flame: Ash", "bomb");
    }

    private static Routine_Boss_Spell constructImpossibleField(){
        Instruction_Attack attack = new Instruction_Attack(null, "spawnIF", 360, 360);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);
        Instruction_TimerRandom tRandom = new Instruction_TimerRandom(null, 60, 300);
        Instruction_StartPolygonPathRandom pRandom = new Instruction_StartPolygonPathRandom(null, 1, 30, 820, 30, 300, 1);

        attack.setNext(toggle);
        toggle.setNext(tRandom);
        tRandom.setNext(pRandom);
        pRandom.setNext(tRandom);

        int hp = 400;

        return new Routine_Boss_Spell(null, null, attack, hp, "Hell Edict: Impossible Field", 32);
    }

    private static Routine_Boss_Spell constructAsphyxiation(){
        Instruction_Attack attack = new Instruction_Attack(null, "spawnAS", 480, 480);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);

        attack.setNext(toggle);

        int hp = 750;

        return new Routine_Boss_Spell(null, null, attack, hp, "Smothering Burn: Asphyxiation", 34);
    }

    private static Routine_Boss_Spell constructMercurialDisposition(){
        Instruction_Attack attack = new Instruction_Attack(null, "spawnMD", 30, 30);
        Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);

        attack.setNext(toggle);

        int hp = 300;

        return new Routine_Boss_Spell(null, null, attack, hp, "Smothering Burn: Mercurial Disposition", 35);
    }

    private static Routine_Boss_Spell constructReignOfFire(){
        Instruction_Attack a1 = new Instruction_Attack(null, "spawnRF1", 60, 60);
        Instruction_Timer at1 = new Instruction_Timer(null, 1);
        Instruction_WaitAttackOver wa1 = new Instruction_WaitAttackOver(null);
        Instruction_StartPolygonPathRandom pp1 = new Instruction_StartPolygonPathRandom(null, 1, 30, 820, 30, 300,.3);
        Instruction_WaitPathOver wp1 = new Instruction_WaitPathOver(null);
        Instruction_Timer t1 = new Instruction_Timer(null, 20);

        Instruction_Attack a2 = new Instruction_Attack(null, "spawnRF2", 60, 60);
        Instruction_Timer at2 = new Instruction_Timer(null, 1);
        Instruction_WaitAttackOver wa2 = new Instruction_WaitAttackOver(null);
        Instruction_StartPolygonPathRandom pp2 = new Instruction_StartPolygonPathRandom(null, 1, 30, 820, 30, 300,.3);
        Instruction_WaitPathOver wp2 = new Instruction_WaitPathOver(null);
        Instruction_Timer t2 = new Instruction_Timer(null, 20);

        Instruction_Attack a3 = new Instruction_Attack(null, "spawnRF3", 60, 60);
        Instruction_Timer at3 = new Instruction_Timer(null, 1);
        Instruction_WaitAttackOver wa3 = new Instruction_WaitAttackOver(null);
        Instruction_StartPolygonPathRandom pp3 = new Instruction_StartPolygonPathRandom(null, 1, 30, 820, 30, 300,.3);
        Instruction_WaitPathOver wp3 = new Instruction_WaitPathOver(null);
        Instruction_Timer t3 = new Instruction_Timer(null, 60);

        a1.setNext(at1);
        at1.setNext(wa1);
        wa1.setNext(pp1);
        pp1.setNext(wp1);
        wp1.setNext(t1);
        t1.setNext(a2);
        a2.setNext(at2);
        at2.setNext(wa2);
        wa2.setNext(pp2);
        pp2.setNext(wp2);
        wp2.setNext(t2);
        t2.setNext(a3);
        a3.setNext(at3);
        at3.setNext(wa3);
        wa3.setNext(pp3);
        pp3.setNext(wp3);
        wp3.setNext(t3);
        t3.setNext(a1);

        int hp = 350;

        return new Routine_Boss_Spell(null, null, a1, hp, "Smothering Burn: Reign Of Fire", "life");
    }

    private static Routine_Boss_Spell constructBeyondTheBoundary(){
        Instruction_Attack radial = new Instruction_Attack(null, "spawnBBRadial", 300, 300);
        Instruction_Timer aTimer1 = new Instruction_Timer(null, 1);
        Instruction_WaitAttackOver wao1 = new Instruction_WaitAttackOver(null);
        Instruction_Zephres_BB z = new Instruction_Zephres_BB(null);
        Instruction_Attack mines = new Instruction_Attack(null, "spawnBBMines", 1, 10);
        Instruction_ToggleAttack toggleOn = new Instruction_ToggleAttack(null, true);
        Instruction_WaitPathOver wpo = new Instruction_WaitPathOver(null);
        Instruction_ToggleAttack toggleOff = new Instruction_ToggleAttack(null, false);
        Instruction_Attack homing = new Instruction_Attack(null, "spawnBBHoming", 360, 360);
        Instruction_Timer aTimer2 = new Instruction_Timer(null, 1);
        Instruction_WaitAttackOver wao2 = new Instruction_WaitAttackOver(null);
        Instruction_Attack lasers = new Instruction_Attack(null, "spawnBBLasers", 60, 60);
        Instruction_Timer endTimer = new Instruction_Timer(null, 150);

        radial.setNext(aTimer1);
        aTimer1.setNext(wao1);
        wao1.setNext(z);
        z.setNext(mines);
        mines.setNext(toggleOn);
        toggleOn.setNext(wpo);
        wpo.setNext(toggleOff);
        toggleOff.setNext(homing);
        homing.setNext(aTimer2);
        aTimer2.setNext(wao2);
        wao2.setNext(lasers);
        lasers.setNext(endTimer);
        endTimer.setNext(radial);

        int hp = 1750;

        return new Routine_Boss_Spell(null, null, radial, hp, "Beyond The Boundary", 0);
    }

    private static Routine_Boss_Attack constructBehaviors(){
        Routine_Boss_Attack[] defaults = new Routine_Boss_Attack[7];
        for(int i = 0; i < 7; i++){
            defaults[i] = constructDefault();
        }

        Routine_Boss_Spell explosion = constructExplosion();
        Routine_Boss_Spell combustion = constructCombustion();
        Routine_Boss_Spell ash = constructAsh();
        Routine_Boss_Spell impossibleField = constructImpossibleField();
        Routine_Boss_Spell asphyxiation = constructAsphyxiation();
        Routine_Boss_Spell mercurialDisposition = constructMercurialDisposition();
        Routine_Boss_Spell reignOfFire = constructReignOfFire();
        Routine_Boss_Spell beyondTheBoundary = constructBeyondTheBoundary();

        defaults[0].setNext(explosion);
        explosion.setNext(defaults[1]);
        defaults[1].setNext(combustion);
        combustion.setNext(defaults[2]);
        defaults[2].setNext(ash);
        ash.setNext(defaults[3]);
        defaults[3].setNext(impossibleField);
        impossibleField.setNext(defaults[4]);
        defaults[4].setNext(asphyxiation);
        asphyxiation.setNext(defaults[5]);
        defaults[5].setNext(mercurialDisposition);
        mercurialDisposition.setNext(defaults[6]);
        defaults[6].setNext(reignOfFire);
        reignOfFire.setNext(beyondTheBoundary);

        return defaults[0];
    }
}
