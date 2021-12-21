public class EP_Fragment extends Projectile_Enemy implements EP_Unremovable {

    public EP_Fragment(Vector initPos, Vector polar){
        super(initPos, polar, 25);

        //probably good enough?
        //no need unremovable
        canBeDamaged = false;

        setTrueHitbox(new Hitbox_Circle(this));
        initSprite();
    }

    @Override
    protected void spawnAttack(){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
        switch(attackName){
            case "lsSpawn":
                attackLSSpawn(difficulty);
                break;
            case "roneSpawn":
                attackRONESpawn(difficulty);
                break;
            case "gosSpawn":
                attackGOSSpawn(difficulty);
                break;
            case "reSpawn1":
                attackRESpawn(1);
                break;
            case "reSpawn2":
                attackRESpawn(2);
                break;
            case "reSpawn3":
                attackRESpawn(3);
                break;
            case "reSpawn4":
                attackRESpawn(4);
                break;
        }
    }

    //60 60
    private void attackLSSpawn(int difficulty){
//        int t;
//        switch(difficulty){
//            case 7:
//                t = 3;
//                break;
//            case 8:
//                t = 2;
//                break;
//            default:
//                t = 4;
//                break;
//        }
        int num;
        if(difficulty < 7){
            num = 1;
        }
//        else if(difficulty == 7){
//            num = 2;
//        }
        else{
            num = 2;
        }
        for(int i = 0; i < num; i++){
            double angle = Math.random() * 50;
            angle -= 25;
            Vector av = new Vector(angle, 0);
            double speed = Math.random() * 2;
            speed += 2;
            toSpawn.add(new EP_Orb_Lilith_LS(this, av, speed));
        }
    }

    //60 60
    private void attackRONESpawn(int difficulty){
        int t;
        switch(difficulty){
            case 7:
                t = 3;
                break;
            case 8:
                t = 2;
                break;
            default:
                t = 4;
                break;
        }
        if(attackTimer % t == 0){
            double angle = Math.random() * 360;
            double speed = 100 + (Math.random() * 200);
            Vector polar = new Vector(angle, speed);
            toSpawn.add(new EP_Oval(this, polar, "red"));
        }
    }

    //60 60
    private void attackGOSSpawn(int difficulty){
        int t;
        switch(difficulty){
            case 7:
                t = 2;
                break;
            case 8:
                t = 1;
                break;
            case 6:
                t = 3;
                break;
            default:
                t = 4;
                break;
        }
        if(attackTimer % t == 0){
            double angle = Math.random() * 360;
            double speed = 100 + (Math.random() * 200);
            Vector polar = new Vector(angle, speed);
            double freq = 60 + (Math.random() * 120);
            double maxAngleDiff = Math.random() * 20;
            toSpawn.add(new EP_Orb_Lilith_RONE_Wiggle(this, polar, freq, maxAngleDiff));
        }
        if(attackTimer % 30 == 0){
            double angle = Math.random() * 72;
            for(int i = 0; i < 5; i++){
                toSpawn.add(new EP_Oval(this, new Vector(angle, 325), "blue"));
                angle += 72;
            }
        }
    }

    //30 30 for all
    private void attackRESpawn(int version){
        switch(version){
            case 1:
                if(attackTimer == 30) {
                    double angle = Vector.getAngle(this, Player.getThePlayer());
                    for (int i = 0; i < 360; i += 15) {
                        toSpawn.add(new EP_Oval(this, new Vector(angle + i, 100), "red"));
                        toSpawn.add(new EP_Oval(this, new Vector(angle + i, 150), "red"));
                        toSpawn.add(new EP_Oval(this, new Vector(angle + i, 200), "red"));
                    }
                }
                break;
            case 2:
                if(attackTimer == 30) {
                    for (int i = 0; i < 50; i++) {
                        double angle = 360 * Math.random();
                        double speed = 150 + (150 * Math.random());
                        toSpawn.add(new EP_Orb_Medium(this, new Vector(angle, speed), "white"));
                    }
                }
                break;
            case 3:
                if(attackTimer == 30){
                    double angle = Vector.getAngle(this, Player.getThePlayer());
                    for(int i = 0; i < 4; i++) {
                        toSpawn.add(new EP_Orb_Large(this, new Vector(angle + (i * 20), 150), "blue"));
                    }
                }
                break;
            case 4:
                double d = 30 - attackTimer;
                double angleAdd = d * 4;
                double baseAngle = Vector.getAngle(this, Player.getThePlayer());
                for(int i = 0; i < 3; i++){
                    double angle = baseAngle + (120 * i);
                    double sangle = angle + angleAdd;
                    toSpawn.add(new EP_Orb_Small(this, new Vector(sangle, 150), "grey"));
                    toSpawn.add(new EP_Orb_Small(this, new Vector(sangle, 200), "grey"));
                }
                break;
        }
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = (this.size * 1.6)/50;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("PE_fragment1", offset, rotation, size);
        this.sprite.setNext(new SpriteInfo("PE_fragment2", offset, rotation, size,
                new SpriteInfo("PE_fragment3", offset, rotation, size,
                        new SpriteInfo("PE_fragment4", offset, rotation, size,
                                new SpriteInfo("PE_fragment5", offset, rotation, size,
                                        new SpriteInfo("PE_fragment6", offset, rotation, size,
                                                new SpriteInfo("PE_fragment7", offset, rotation, size,
                                                        new SpriteInfo("PE_fragment8", offset, rotation, size, this.sprite))))))));
    }

    @Override
    protected void updateSprite(){
        if(tick % 10 == 0)
            this.sprite = this.sprite.getNext();
        this.sprite.setRotation(tick/3);
    }

    //x is either 0 or 850
    public static Behavior constructREBehavior(double x){
        Instruction_Timer timer1 = new Instruction_Timer(null, 30);
        Instruction_StartPolygonPathRandom path1 = new Instruction_StartPolygonPathRandom(null, 1, x, x, 500, 1000, 1);
        Instruction_WaitPathOver wpo1 = new Instruction_WaitPathOver(null);
        Instruction_Attack attack1 = new Instruction_Attack(null, "reSpawn1", 30, 30);

        Instruction_Timer timer2 = new Instruction_Timer(null, 30);
        Instruction_StartPolygonPathRandom path2 = new Instruction_StartPolygonPathRandom(null, 1, x, x, 0, 500, 1);
        Instruction_WaitPathOver wpo2 = new Instruction_WaitPathOver(null);
        Instruction_Attack attack2 = new Instruction_Attack(null, "reSpawn2", 30, 30);

        Instruction_Timer timer3 = new Instruction_Timer(null, 30);
        Instruction_StartPolygonPathRandom path3 = new Instruction_StartPolygonPathRandom(null, 1, x, x, 500, 1000, 1);
        Instruction_WaitPathOver wpo3 = new Instruction_WaitPathOver(null);
        Instruction_Attack attack3 = new Instruction_Attack(null, "reSpawn3", 30, 30);

        Instruction_Timer timer4 = new Instruction_Timer(null, 30);
        Instruction_StartPolygonPathRandom path4 = new Instruction_StartPolygonPathRandom(null, 1, x, x, 0, 500, 1);
        Instruction_WaitPathOver wpo4 = new Instruction_WaitPathOver(null);
        Instruction_Attack attack4 = new Instruction_Attack(null, "reSpawn4", 30, 30);

        timer1.setNext(path1);
        path1.setNext(wpo1);
        wpo1.setNext(attack1);
        attack1.setNext(timer2);
        timer2.setNext(path2);
        path2.setNext(wpo2);
        wpo2.setNext(attack2);
        attack2.setNext(timer3);
        timer3.setNext(path3);
        path3.setNext(wpo3);
        wpo3.setNext(attack3);
        attack3.setNext(timer4);
        timer4.setNext(path4);
        path4.setNext(wpo4);
        wpo4.setNext(attack4);
        attack4.setNext(timer1);

        return timer1;
    }
}
