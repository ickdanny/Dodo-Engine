import java.util.ArrayList;
import java.util.Objects;

//entity class extends vector
public class Entity extends Vector {
    //tick
    protected int tick;
    //exists
    protected boolean exists = true;
    //hasUpdated
    protected boolean hasUpdated = false;

    //speedCoefficient
    protected double speedCoefficient = 1;

    //polar, velocity vectors
    protected Vector polar = new Vector();
    protected Vector velocity = new Vector();
    //size
    protected double size = 0;

    //canDamage
    protected boolean canDamage = false;
    //canBeDamaged
    protected boolean canBeDamaged = false;
    //damage, hp
    protected double damage = 0, hp = 0;

    //followsPath
    protected boolean followsPath = false;
    //Path path (can be polygon or orbit)
    protected Path path;

    //hasBehavior
    protected boolean followsBehavior = false;
    //Behavior behavior
    protected Behavior behavior;

    //canAttack
    protected boolean canAttack = false;
    //reloadTimer, attackTimer
    //reload timer is the length of time a reload takes
    //attack timer is the timer for the duration of the attack

    //for example, reload timer of 10 and attack timer of 3, attacks can happen for 3 ticks
    //but must wait 7 more ticks before resetting
    protected double reloadTimer = 0, attackTimer = 0;
    //reloadDelay, attackDelay
    //these are the values that reloadTimer and attackTimer reset to each iteration
    protected double reloadDelay = 0, attackDelay = 0;
    //String attackName
    protected String attackName = "none";
    //continuousAttack
    protected boolean continuousAttack = false;

    //Vector previousPos <- stores previous position
    protected Vector previousPosition = new Vector();

    //these hitboxes should be done individidually in every constructor
    //AABB hitboxAABB
    protected Hitbox_AABB AABB;
    //Hitbox hitboxTrue
    protected Hitbox trueHitbox;
    //AABB hitboxTwoFrame <- used for high speed collision detection
    protected Hitbox_AABB twoFrameHitbox;

    //ArrayList toSpawn (mostly used for attacks)
    protected ArrayList<Entity> toSpawn = new ArrayList<>();

    protected SpriteInfo sprite = new SpriteInfo();

    //constructor no args
    public Entity(){
        this.a = 0;
        this.b = 0;
        initSprite();
    }
    //constructor size
    public Entity(double size){
        this();
        this.size = size;
        initSprite();
    }
    //constructor position and size args
    public Entity(Vector initPos){
        this();
        this.addToThis(initPos);
        initSprite();
    }
    //uhh... entities w/o size?
    public Entity(Vector initPos, Vector polar){
        this(initPos);
        this.polar = polar;
        velocity = Vector.polarToVelocity(polar);
        initSprite();
    }
    //useful for creating puppet entities
    public Entity(Vector initPos, double size){
        this(initPos);
        this.size = size;
        initSprite();
    }
    //this is the constructor most entities will use
    public Entity(Vector initPos, Vector polar, double size){
        this(initPos, size);
        this.polar = polar;
        velocity = Vector.polarToVelocity(polar);
        initSprite();
    }

    public boolean canUpdate(){
        if(followsPath) {
            if (path instanceof Path_Orbit) {
                return ((Path_Orbit) path).hasCenterUpdated();
            }
        }
        return true;
    }
    //update
    public void update() {
        //move to promised position
//        System.out.println("start of update");
        move();
//        System.out.println("moved");
        //maybe spawn some things
        spawn();
        //tick
        tick++;
        //update the sprite AFTER changing the tick
        updateSprite();
        //hasUpdated = true
        hasUpdated = true;
    }

    //move()
    //different basic movements should override this (should stil call super.move())
    //might need to set the two frame hitbox as well
    protected void move() {
        //recalculate AABB
//        System.out.println("start of move");
        AABB = trueHitbox.getBoundingAABB();
        //set the previousPos vector to this pos
        previousPosition.setToThis(this);
        //move to next pos
        //velocity is per second, so divide by the updates per second to get velocity per update
        addToThis(Vector.scalarMultiple(velocity, ((double)1)/ Driver_Game.getUPS()));
        //yup, need to set the two frame hitbox
        twoFrameHitbox = AABB.twoFrameAABB(previousPosition);
        //check behavior and path
//        System.out.println("updating b/p");
        updateBehavior();
//        System.out.println("after b");
        updatePath();
//        System.out.println("after b/p");
        //make sure polar is always positive?
        if(polar.getA() < 0){
            polar.addToThis(new Vector(360, 0));
        }
        //this too
        else if(polar.getA() >= 360){
            polar.addToThis(new Vector(-360, 0));
        }
        //this method does NOTHING unless you OVERRIDE IT
        baseMovementBehavior();
    }
    protected void updateBehavior(){
        if(followsBehavior) {
            while (behavior.doAction()) {
                if (behavior.getNext() != null) {
                    behavior = behavior.getNext();
                } else {
                    followsBehavior = false;
                    break;
                }
            }
        }
    }
    //wait should I need a different updatePath method for like than paths?
    protected void updatePath(){
//        System.out.println("a");
        if(followsPath) {
//            System.out.println("b");
            if (path.isOver()) {
//                System.out.println("problem");
                followsPath = false;
                return;
            }
            //do i need a velocity conversion here
            //answer is no
            polar = path.getNextPolar(this);
        }
    }
    //override this WAIT DON'T
    protected void spawn(){
        if(attackTimer > 0){
            spawnAttack();
            attackTimer--;
        }
        if(reloadTimer > 0){
            reloadTimer--;
        }
        else if(canAttack && reloadTimer <= 0 && attackTimer <= 0){
            attackTimer = attackDelay;
            reloadTimer = reloadDelay;
            canAttack = continuousAttack;
        }
    }
    //override THIS v
    protected void spawnAttack(){
        //some switch on attackName
        //some switch on attackTimer
    }

    //updateHP(double damage)
    //might need to override this for bosses.
    protected void updateHP(double damage){
        if(!canBeDamaged || !exists)
            return;
        hp -= damage;
        if(hp < 0){
            exists = false;
            //putting this here, but might not be smart
            deathRoutine();
        }
    }

    //this method does NOTHING unless you OVERRIDE IT
    //ONLY BASIC FUNCTIONS

    //ignore what i just wrote
    //this method is THE BASIC POLAR TO VELOCITY CONVERTER, AND THE ONLY TIME IT SHOULD BE CALLED.
    //ALL SUBCLASSES SHOULD CALL THIS FOR POLAR TO VELOCITY CONVERSION.
    protected void baseMovementBehavior(){
        //set polar and velocity
        velocity = Vector.polarToVelocity(polar);
    }

    //deathRoutine() //basically deathrattle, mostly used to spawn like... pickups and such
    protected void deathRoutine(){

    }

    //collides(entity e)
    public void collides(Entity e){

    }

    //stop
    //stop movements
    public void stop(){
        velocity.clear();
        polar.clear();
        followsPath = false;
    }
    //stop attack
    public void stopAttack(){
        attackTimer = 0;
        reloadTimer = 0;
        continuousAttack = false;
        canAttack = false;
        attackName = "none";
    }
    //stops movements and behaviors
    public void stopAll(){
        velocity.clear();
        polar.clear();
        followsPath = false;
        followsBehavior = false;
        attackTimer = 0;
        reloadTimer = 0;
        continuousAttack = false;
        canAttack = false;
        attackName = "none";
    }
    //suicide (suicide this)
    public void suicide(){
        exists = false;
        deathRoutine();
    }
    public void newPath(Path path){
        if(path instanceof Path_Orbit){
            newOrbitPath((Path_Orbit) path);
        }
        else if(path instanceof Path_Polygon){
            newPolygonPath((Path_Polygon) path);
        }
    }
    //newOrbitPath(Path_Orbit new) FOR THIS ALWAYS SET THE ORBIT DISTANCE TO WHAT IT IS CURRENTLY.
    //wait maybe the distance starts at the default?
    //wait nope you need to set this NOT HERE BUT IN IN THE PATH INSTANTIATION
    public void newOrbitPath(Path_Orbit path){
        followsPath = true;
        this.path = path;
    }
    //newPolygonPath(Path_Polygon new)
    public void newPolygonPath(Path_Polygon path){
        followsPath = true;
        this.path = path;
    }
    public void cancelPath(){
        followsPath = false;
    }
    public void setSpeedCoefficient(double speedCoefficient){
        this.speedCoefficient = speedCoefficient;
    }
    //setPolarVector(the polar vector)
    //THIS IS THE METHOD TO CALL WHEN YOU WANT TO CHANGE DIRECTION!!
    //POLAR VECTOR AND VELOCITY VECTOR WILL NOT(!!!) UPDATE EACHOTHER.
    public void setPolarVector(Vector polar){
        this.polar = polar;
    }
    //setVelocityVector(the velocity vector)
    public void setVelocityVector(Vector velocity){
        this.velocity = velocity;
    }
    //setPosition(x, y)
    //basically teleportation
    public void setPosition(Vector position){
        this.setToThis(position);
    }
    //setNewBehavior(Behavior new)
    public void setNewBehavior(Behavior newBehavior){
        followsBehavior = true;
        behavior = newBehavior;
//        if(behavior instanceof Instruction){
//            ((Instruction)behavior).setOwner(this);
//        }
        setBehaviorOwnerToThis();
     }
    public void cancelBehavior(){
        followsBehavior = false;
    }
    public Behavior getLastBehavior(){
        if(this.behavior == null){
            return null;
        }
        Behavior toRet = this.behavior;
        int maxChain = 100;
        int numB = 1;
        while(toRet.getNext() != null && numB < maxChain){
            toRet = toRet.getNext();
            numB++;
        }
        return toRet;
    }
    //useful for creating dummy entities
    public void setTrueHitbox(Hitbox hitbox){
        trueHitbox = hitbox;
        AABB = trueHitbox.getBoundingAABB();
        twoFrameHitbox = AABB;
    }
    public void setAABB(Hitbox_AABB hitbox){
        AABB = hitbox;
        twoFrameHitbox = AABB;
    }
    public void setTwoFrameHitbox(Hitbox_AABB hitbox){
        twoFrameHitbox = hitbox;
    }
    public void setCanAttack(boolean canAttack){
        this.canAttack = canAttack;
    }
    //USE THESE METHODS IN INSTRUCTIONS !!!!!!!!!
    public void setAttackName(String attackName){
        this.attackName = attackName;
    }
    public void setAttackDelay(double attackDelay){
        this.attackDelay = attackDelay;
    }
    public void setReloadDelay(double reloadDelay){
        this.reloadDelay = reloadDelay;
    }
    public void setContinuousAttack(Boolean continuousAttack){
        this.continuousAttack = continuousAttack;
    }
    public void setExists(boolean exists) {
        this.exists = exists;
    }
    //resetUpdate() sets hasupdated to false
    //THIS IS IMPORTANT DON'T MISS THIS HERE IT'S REALLY IMPORTANT THAT YOU SEE THIS
    //NO REALLY THIS METHOD IS REALLY IMPORTANT!!! LIKE IT'S HARD TO DESCRIBE
    //HOW IMPORTANT THIS IS SO MAKE SURE TO REMEMBER IT
    public void resetUpdate(){
        hasUpdated = false;
    }






    //get toSpawn (this clears tospawn after returning(maybe i need to clone, clear, and then return the clone?))
    public ArrayList<Entity> getToSpawn(){
        ArrayList<Entity> toRet = new ArrayList<>(toSpawn);
        toSpawn.clear();
        return toRet;
    }
    //get tick
    public int getTick(){
        return tick;
    }
    //getExists
    public boolean getExists(){
        return exists;
    }
    //getHasUpdated
    public boolean getHasUpdated(){
        return hasUpdated;
    }
    //getPolarVector(angle, speed)
    public Vector getPolarVector(){
        return polar;
    }
    public double getAngle(){
        return polar.a;
    }
    public double getSpeed(){
        return polar.b;
    }
    public double getSpeedCoefficient(){
        return speedCoefficient;
    }
    //getVelocityVector(xv, yv)
    public Vector getVelocityVector(){
        return velocity;
    }
    public double getXV(){
        return velocity.a;
    }
    public double getYV(){
        return velocity.b;
    }
    //getPosition(x, y)
    public Vector getPosition(){
        return new Vector(a, b);
    }
    //getPrevPos(x, y)
    public Vector getPrevPos(){
        return previousPosition;
    }
    //getSize
    public double getSize(){
        return size;
    }
    //getCanDamage
    public boolean getCanDamage(){
        return canDamage;
    }
    //getCanBeDamaged
    public boolean getCanBeDamaged(){
        return canBeDamaged;
    }
    //getDamage
    public double getDamage(){
        return damage;
    }
    //getHP
    public double getHP(){
        return hp;
    }
    //getTwoFrameHitbox
    public Hitbox_AABB getTwoFrameHitbox(){
        return twoFrameHitbox;
    }
    //getAABBHitbox
    public Hitbox_AABB getHitboxAABB(){
        return AABB;
    }
    //getTrueHitbox
    public Hitbox getHitbox(){
        return trueHitbox;
    }
    public double getX(){
        return a;
    }
    public double getY(){
        return b;
    }
    public Path getPath() {
        return path;
    }
    public void setBehaviorOwnerToThis(){
        Behavior toSet = this.behavior;
        int maxLoops = 100;
        int loops = 0;
        while(toSet != null && loops < maxLoops){
            try {
                ((Instruction)toSet).setOwner(this);
            }catch(Exception e){
                //do nothing
            }
            loops++;
            toSet = toSet.getNext();
        }
    }

    protected void initSprite(){

    }
    protected void updateSprite(){

    }

    public void setSpriteInfo(SpriteInfo sprite){
        this.sprite = sprite;
    }
    public SpriteInfo getSpriteInfo(){
        //return new SpriteInfo();
        return sprite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Entity entity = (Entity) o;
        return tick == entity.tick &&
                exists == entity.exists &&
                hasUpdated == entity.hasUpdated &&
                Double.compare(entity.speedCoefficient, speedCoefficient) == 0 &&
                Double.compare(entity.size, size) == 0 &&
                canDamage == entity.canDamage &&
                canBeDamaged == entity.canBeDamaged &&
                Double.compare(entity.damage, damage) == 0 &&
                Double.compare(entity.hp, hp) == 0 &&
                followsPath == entity.followsPath &&
                followsBehavior == entity.followsBehavior &&
                canAttack == entity.canAttack &&
                Double.compare(entity.reloadTimer, reloadTimer) == 0 &&
                Double.compare(entity.attackTimer, attackTimer) == 0 &&
                Double.compare(entity.reloadDelay, reloadDelay) == 0 &&
                Double.compare(entity.attackDelay, attackDelay) == 0 &&
                continuousAttack == entity.continuousAttack &&
                Objects.equals(polar, entity.polar) &&
                Objects.equals(velocity, entity.velocity) &&
                Objects.equals(path, entity.path) &&
                Objects.equals(behavior, entity.behavior) &&
                Objects.equals(attackName, entity.attackName) &&
                Objects.equals(previousPosition, entity.previousPosition) &&
                Objects.equals(AABB, entity.AABB) &&
                Objects.equals(trueHitbox, entity.trueHitbox) &&
                Objects.equals(twoFrameHitbox, entity.twoFrameHitbox) &&
                Objects.equals(toSpawn, entity.toSpawn);
    }
}