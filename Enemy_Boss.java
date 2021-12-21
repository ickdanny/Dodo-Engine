import java.util.ArrayList;

//VERY IMPORTANT
//THE BEGINNING INSTRUCTION OF EVERY PHASE MUST BE TO SET THE HP
public class Enemy_Boss extends Enemy{

    //for the hp bar
    protected double maxHP = 0;
//    protected String spellName = "testing testing testing";
    protected String spellName = "";

    protected SpriteInfo idle;
    private static Enemy_Boss theBoss;
    public Enemy_Boss(Behavior b){
        super(new Vector(Driver_Game.getWidth()/2, -100), 50);
        this.canBeDamaged = false;
        this.canDamage = false;
        setTrueHitbox(new Hitbox_Circle(this));
        ArrayList<Vector> p = new ArrayList<>();
        p.add(new Vector(Driver_Game.getWidth()/2, 200));
        newPolygonPath(new Path_Polygon(p, false, 200));
        initSprite();
        theBoss = this;


        Instruction_ToggleCanDamage tcd = new Instruction_ToggleCanDamage(this, b, true);
        Instruction_ToggleCanBeDamaged inv = new Instruction_ToggleCanBeDamaged(this, tcd, true);
        Instruction_WaitSignal ws = new Instruction_WaitSignal(this, inv);
        Instruction_DriverStartDialogue d = new Instruction_DriverStartDialogue(this, "Pre", ws, ws);
        this.setNewBehavior(new Instruction_Timer(this, d, 80));

//        Driver.add(new DialogueScreen(Driver_Game.getCurrentGameDriver().getStageName(), "Pre",
//                Driver_GameLogic.getCurrentLogicDriver().getDifficulty(), ws));
        //Driver_Game.getCurrentGameDriver().startDialogue("Pre", ws);
    }

    @Override
    protected void baseMovementBehavior(){
        velocity = Vector.polarToVelocity(polar);
        Vector nextPos = Vector.add(this, Vector.scalarMultiple(velocity, ((double)1)/ Driver_Game.getUPS()));

        if(nextPos.a < 15){
            double zeroDiff = 0 - this.getA() + 15;
            velocity.a = zeroDiff/Driver_Game.getUPS();
        }
        else if(nextPos.a > Driver_Game.getWidth() - 15){
            double maxDiff = Driver_Game.getWidth() - this.getA() - 15;
            velocity.a = maxDiff/Driver_Game.getUPS();
        }

        if(nextPos.b > Driver_Game.getHeight() - 30){
            double maxDiff = Driver_Game.getHeight() - this.getB() - 30;
            velocity.b = maxDiff/Driver_Game.getUPS();
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
//                toSpawn.clear();
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

                setNewBehavior(sp);
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

    protected void dropItems(){
        boolean mode = ((Routine_Boss_Spell)behavior).isPowerOrDrop();
        //power
        if(mode){
            int power = ((Routine_Boss_Spell)behavior).getPower();
            int num10s = power/10;
            for(int i = 0; i < num10s; i++){
                spawnPower(false);
            }
            power -= (num10s * 10);
            for(int i = 0; i < power; i++){
                spawnPower(true);
            }
        }
        //drop
        else{
            String dropName = ((Routine_Boss_Spell)behavior).getDrop();
            if(dropName.equals("life")){
                toSpawn.add(new Pickup_Life(this));
            }
            else if(dropName.equals("bomb")){
                toSpawn.add(new Pickup_Bomb(this));
            }
        }
    }

    protected void clearSpawnBullets(){
        ArrayList<Entity> toRemove = new ArrayList<>();
        for(Entity e : toSpawn){
            if(!(e instanceof Pickup)){
                toRemove.add(e);
            }
        }
        toSpawn.removeAll(toRemove);
    }

    private void spawnPower(boolean small){
        double angle = Math.random() * 360;
        double dist = 80 + (Math.random() * 80);
        Vector p = new Vector(angle, dist);
        Vector v = Vector.polarToVelocity(p);
        Vector spawnPos = Vector.add(this, v);
        if(small){
            toSpawn.add(new Pickup_PowerUpSmall(spawnPos));
        }
        else{
            toSpawn.add(new Pickup_PowerUpLarge(spawnPos));
        }
    }

    public void setHP(double newHP){
        this.hp = newHP;
        this.maxHP = newHP;
    }

    public double getMaxHP(){
        return this.maxHP;
    }

    public String getSpellName(){
        return this.spellName;
    }
    public void setSpellName(String newS){
        this.spellName = newS;
    }

    public static Enemy_Boss getTheBoss(){
        return theBoss;
    }
    public static void resetTheBoss(){
        theBoss = null;
    }
}
