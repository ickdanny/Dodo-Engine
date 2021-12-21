public class EP_Blade extends Projectile_Enemy implements EP_Unremovable{

    //0 = idle
    //1 = bot - path to point
    //2 = orbiting, AV = 120
    //3 = RTO
    private int phase = 0;

    private Vector target = Player.getThePlayer();
    private Vector phase2AV = new Vector(120, 75);

    //really this is going to be private Boss_Lilith owner but we'll cross that bridge when we need to
    private Enemy_Boss_Lilith owner;
//    public EP_Blade(Vector initPos, Enemy owner, Vector aVelocity){
//        super(initPos, 10);
//        this.owner = owner;
//        canBeDamaged = false;
//        newOrbitPath(new Path_Orbit(owner, aVelocity, Vector.getAngle(owner, this)));
//        setTrueHitbox(new Hitbox_Circle(this));
//        destroyOutsideBounds = false;
//        initSprite();
//    }

    public EP_Blade(Vector initPos, Enemy_Boss_Lilith owner){
        super(initPos, 10);
        this.owner = owner;
        canBeDamaged = false;
        setTrueHitbox(new Hitbox_Circle(this));
        destroyOutsideBounds = false;
        initSprite();
    }

    @Override
    protected void baseMovementBehavior(){
        super.baseMovementBehavior();
        if(phase == 3){
            if (a < 0){
                a = 0;
                polar.setB(0);
                velocity = new Vector();
            }
            if (b < 0) {
                b = 0;
                polar.setB(0);
                velocity = new Vector();
            }
            if(a > Driver_Game.getWidth()){
                a = Driver_Game.getWidth();
                polar.setB(0);
                velocity = new Vector();
            }
            if(b > Driver_Game.getHeight()){
                b = Driver_Game.getHeight();
                polar.setB(0);
                velocity = new Vector();
            }
        }
    }

    //this is the one that gets called by lilith
    public void botPointTarget(){
        target = Player.getThePlayer().clone();
        newPath(new Path_Polygon(target, false, 1000));

        Instruction_WaitPathOver wpo = new Instruction_WaitPathOver(this);
        Instruction_Timer t = new Instruction_Timer(this, 80);
        Instruction_Blade_BotReturn r = new Instruction_Blade_BotReturn(this, this.clone());
        wpo.setNext(t);
        t.setNext(r);
        setNewBehavior(wpo);

        changePhase(1);
    }

    public void rto(){
        stop();
        setPolarVector(new Vector(Vector.getAngle(owner, this), 1000));

        Instruction_Timer t = new Instruction_Timer(this, 80);
        Instruction_Blade_RTOReturn r = new Instruction_Blade_RTOReturn(this, this.clone());

        t.setNext(r);
        setNewBehavior(t);

        changePhase(3);
    }

    public void botReturn(Vector returnPos){
        newPath(new Path_Polygon(returnPos, false, 1000));
        behavior.setNext(new Instruction_WaitPathOver(this, new Instruction_Blade_Reset(this, 0)));
//        setNewBehavior(new Instruction_WaitPathOver(this, new Instruction_Blade_Reset(this, 0)));
    }

    public void rtoOrbit(){
        newOrbitPath(new Path_Orbit(owner, phase2AV, Vector.getAngle(owner, this)));
    }

    public void rtoReturn(Vector returnPos){
        newPath(new Path_Polygon(returnPos, false, 1000));
//        System.out.println("init path " + tick);
        //they all start returning at the same time;this should make the distance regular
        behavior.setNext(new Instruction_Timer(this, new Instruction_Blade_Reset(this, 2), 80));
//        setNewBehavior(new Instruction_Timer(this, new Instruction_Blade_Reset(this, 2), 120));
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = (this.size * 2)/35;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("PE_blade", offset, rotation, size);
    }

    @Override
    protected void updateSprite(){
        if(phase == 0 || phase == 1){
            this.sprite.setRotation(-Vector.getAngle(target, this));
        }
        if(phase == 2 || phase == 3) {
            this.sprite.setRotation(-Vector.getAngle(owner, this));
        }
    }

    public void resetTarget(){
        target = Player.getThePlayer();
    }

    public void changePhase(int newPhase){
//        System.out.println("set phase " + newPhase + " " + tick);
        phase = newPhase;
        if(phase == 2){
            rtoOrbit();
        }
    }

}
