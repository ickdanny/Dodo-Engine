import java.util.ArrayList;
import java.util.Iterator;

//logic driver class
public class Driver_GameLogic {
    //static logicDriver (only one can exist at a time)
    private static Driver_GameLogic currentLogicDriver;

    private int difficulty;
    private String stageName;
    //tick
    private int tick = 0;
    //gameover boolean
    //private boolean gameOver = false;
    //paused boolean
    private boolean paused = false;
    //containers for entities
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Projectile_Player> projectilePlayers = new ArrayList<>();
    private ArrayList<Projectile_Enemy> projectileEnemies = new ArrayList<>();
    private ArrayList<Projectile_Bomb> projectileBombs = new ArrayList<>();
    private ArrayList<Pickup> pickups = new ArrayList<>();
    private ArrayList<Entity> entities = new ArrayList<>();

    private Entity boardHitbox = new Entity(1);
    private QuadTree quadTree = new QuadTree(0, 0, 0, Driver_Game.getWidth(), Driver_Game.getHeight());

    public boolean defeatRunning = false;
    public boolean victoryRunning = false;

    //bomb stuff
    private int numSpells;
    private boolean inelligible = false;
    private boolean spellRunning = false;

    //constructor(playerCode, inithp, initbombs)
    public Driver_GameLogic(int difficulty, String stageName, String playerName, int initHP, int initBombs, int initPower, int numSpells){
        Player.createPlayer(initHP, initBombs, initPower, playerName);
//        Player.createPlayer(initHP, initBombs, 200, playerName);
        player = Player.getThePlayer();
        entities.add(player);
        //maybe some entityspawner shenanigans
        currentLogicDriver = this;
        boardHitbox.setTrueHitbox(new Hitbox_AABB(Driver_Game.getWidth(), Driver_Game.getHeight(), 0, 0, boardHitbox));
        this.difficulty = difficulty;
        this.stageName = stageName;
        this.numSpells = numSpells;
    }
    //update game
    public void updateGame() {
        //check if game is over (player is dead)
//        System.out.println(tick);

        //player handles defeat
//        if(!player.getExists()){
//            gameOver = true;
//            return;
//        }
        //reset all entities hasUpdated
        for(Entity e : entities){
//            System.out.println(e);
            e.resetUpdate();
        }
//        System.out.println("RU" + System.currentTimeMillis());
        //update all entities
        updateEntities();
//        System.out.println("UE" + System.currentTimeMillis());
        //add new entities
        addEntities(false);
//        System.out.println("AE" + System.currentTimeMillis());
        //check collisions
        collisions();
//        System.out.println("C" + System.currentTimeMillis());
        //remove the dead
        removeDead();
//        System.out.println("RD" + System.currentTimeMillis());
        //tick++
        if(Enemy_Boss.getTheBoss() != null) {
            spellRunning = !Enemy_Boss.getTheBoss().getSpellName().equals("");
        }
        else{
            spellRunning = false;
        }
        tick++;
    }

    //update entities
    private void updateEntities() {
        //for each entity, update.
        //if an entity needs another entity to update first, add it to a list and skip it.
        //something like a while loop, where you copy entities, then have a list of entities that need other
        //entities to update, put them in a temp list, replace the copy of entities with the list, and repeat.
        ArrayList<Entity> cantUpdate = new ArrayList<>();
        ArrayList<Entity> toRemove = new ArrayList<>();
        cantUpdate.addAll(entities);
//        System.out.println("in UE before while" + System.currentTimeMillis());
        while(cantUpdate.size() > 0){
//            System.out.println(cantUpdate.size());
            for(Entity e : cantUpdate){
                if(e.canUpdate() && !e.getHasUpdated()){
                    if(e instanceof Enemy) {
//                        System.out.println("try update " + e);
                    }
                    e.update();
//                    System.out.println("updated " + e);
                    toRemove.add(e);
//                    System.out.println("removed");
                }
                else if(e.getHasUpdated()){
                    toRemove.add(e);
                }
//                System.out.println("Bottom of for");
            }
            cantUpdate.removeAll(toRemove);
            toRemove.clear();
//            System.out.println("bottom of while");
        }
    }

    //add entities
    private void addEntities(boolean spawnDeath) {
        //arraylist toAdd
        ArrayList<Entity> toAdd = new ArrayList<>();
        if(!spawnDeath){
            toAdd.addAll(EnemySpawner.getWave(stageName, tick));
        }
//        else
//            System.out.println("spawndeat");

        //delete nulls
        Iterator<Entity> it = toAdd.iterator();
        while (it.hasNext()) {
            if (it.next() == null) {
                it.remove();
            }
        }
//        toAdd.remove

        //check every entity, add their children to toAdd, clear their children
        for(Entity e : entities){
            toAdd.addAll(e.getToSpawn());
        }


        //sort through toAdd
        ArrayList<Projectile_Player> toAddPP = new ArrayList<>();
        ArrayList<Projectile_Bomb> toAddPB = new ArrayList<>();
        ArrayList<Projectile_Enemy> toAddPE = new ArrayList<>();
        ArrayList<Enemy> toAddE = new ArrayList<>();
        ArrayList<Pickup> toAddP = new ArrayList<>();
        for(Entity e : toAdd){
            if(e instanceof Projectile_Player){
                toAddPP.add((Projectile_Player)e);
            }
            else if(e instanceof Projectile_Bomb){
                toAddPB.add((Projectile_Bomb)e);
            }
            else if(e instanceof Projectile_Enemy){
                toAddPE.add((Projectile_Enemy)e);
            }
            else if(e instanceof Enemy){
                toAddE.add((Enemy)e);
            }
            else if(e instanceof Pickup){
                toAddP.add((Pickup)e);
            }
        }
        //add all
        projectilePlayers.addAll(toAddPP);
        projectileBombs.addAll(toAddPB);
        projectileEnemies.addAll(toAddPE);
        enemies.addAll(toAddE);
        pickups.addAll(toAddP);
        entities.addAll(toAdd);
    }

    //collisions
    private void collisions() {
        quadTree.clear();
        //add everything to the quadtree
        ArrayList<Entity> visibleEntities = new ArrayList<>();
        //add all the entities that collide with the entire board
        for(Entity e : entities){
            if(e.getTwoFrameHitbox() != null) {
                if (e.getTwoFrameHitbox().collidesWith(boardHitbox.getHitbox())) {
                    visibleEntities.add(e);
                }
            }
        }
        //insert to quadtree
        for(Entity e : visibleEntities){
            quadTree.insert(e);
        }

        //tset collisions on each entity
        //possibleCollisions = hitboxes the quad tree returns
        ArrayList<Entity> possibleCollisions = new ArrayList<>();
        for(Entity e : visibleEntities){
            possibleCollisions.clear();
            quadTree.retrieve(possibleCollisions, e);
            //check every collision with entity e
            for(Entity possibleCollision : possibleCollisions){
                if(possibleCollision != e){
                    //test speed and shit
                    //first
                    //both collisions at once, no double dipping because e will be removed from the quadtree afterwards
                    if(checkCollision(e, possibleCollision)){
                        e.collides(possibleCollision);
                        possibleCollision.collides(e);
                    }
                    //make EXTRA sure the player isn't zooming past anyone
                    else if(e instanceof Player){
                        Entity dummy = new Entity(new Vector((e.getX() + e.getPrevPos().getA()) / 2, (e.getY() + e.getPrevPos().getB()) / 2), e.getSize());
                        dummy.setTrueHitbox(e.getHitbox());
                        dummy.setTwoFrameHitbox(e.getTwoFrameHitbox());
                        if(checkCollision(dummy, possibleCollision)){
                            e.collides(possibleCollision);
                            possibleCollision.collides(e);
                        }
                    }
                    else if(possibleCollision instanceof Player){
                        Entity dummyTwo = new Entity(new Vector((possibleCollision.getX() + possibleCollision.getPrevPos().getA())/2,
                                (possibleCollision.getY() + e.getPrevPos().getB()) / 2), e.getSize());
                        dummyTwo.setTrueHitbox(possibleCollision.getHitbox());
                        dummyTwo.setTwoFrameHitbox(possibleCollision.getTwoFrameHitbox());
                        if(checkCollision(e, dummyTwo)){
                            e.collides(possibleCollision);
                            possibleCollision.collides(e);
                        }
                    }
                    //second
                    else if(e.getPolarVector().getB() >= 2000){
                        Entity dummy = new Entity(new Vector((e.getX() + e.getPrevPos().getA()) / 2, (e.getY() + e.getPrevPos().getB()) / 2), e.getSize());
                        dummy.setTrueHitbox(e.getHitbox());
                        dummy.setTwoFrameHitbox(e.getTwoFrameHitbox());
                        if(checkCollision(dummy, possibleCollision)){
                            e.collides(possibleCollision);
                            possibleCollision.collides(e);
                        }
                        //third
                        else if(possibleCollision.getPolarVector().getB() >= 2000){
                            Entity dummyTwo = new Entity(new Vector((possibleCollision.getX() + possibleCollision.getPrevPos().getA())/2,
                                    (possibleCollision.getY() + e.getPrevPos().getB()) / 2), e.getSize());
                            dummyTwo.setTrueHitbox(possibleCollision.getHitbox());
                            dummyTwo.setTwoFrameHitbox(possibleCollision.getTwoFrameHitbox());
                            if(checkCollision(e, dummyTwo)){
                                e.collides(possibleCollision);
                                possibleCollision.collides(e);
                            }
                            //fourth
                            else if(e.getPolarVector().getB() >= 2000){
                                dummy = new Entity(new Vector((e.getX() + e.getPrevPos().getA()) / 2, (e.getY() + e.getPrevPos().getB()) / 2), e.getSize());
                                dummy.setTrueHitbox(e.getHitbox());
                                dummy.setTwoFrameHitbox(e.getTwoFrameHitbox());
                                if(checkCollision(dummy, dummyTwo)){
                                    e.collides(possibleCollision);
                                    possibleCollision.collides(e);
                                }
                            }
                        }
                    }
                }
            }
            //remove e
            quadTree.remove(e);
        }
        //clear the quadtree <- MOVED TO TOP
    }

    //checkCollision, if collision only activate the first entity collision because lmao
    private boolean checkCollision(Entity e, Entity possibleCollision){
        if(e instanceof Pickup && possibleCollision instanceof Player){
            if(e.getTwoFrameHitbox().collidesWith(possibleCollision.getTwoFrameHitbox())) {
                System.out.println("Two frame hit at tick " + tick);
                if (e.getHitboxAABB().collidesWith(possibleCollision.getHitboxAABB())) {
                    System.out.println("AABB hit");
                    if (e.getHitbox().collidesWith(possibleCollision.getHitbox())) {
                        System.out.println("True hit");
                        return true;
                    }
                }
            }
            return false;
        }
        if(e.getTwoFrameHitbox().collidesWith(possibleCollision.getTwoFrameHitbox())) {
            if (e.getHitboxAABB().collidesWith(possibleCollision.getHitboxAABB())) {
                if (e.getHitbox().collidesWith(possibleCollision.getHitbox())) {
                    return true;
                }
            }
        }
        return false;
    }

    //remove the dead
    private void removeDead() {
        //lists of toRemove
        ArrayList<Entity> toRemove = new ArrayList<>();
        ArrayList<Projectile_Player> toRemovePP = new ArrayList<>();
        ArrayList<Projectile_Bomb> toRemovePB = new ArrayList<>();
        ArrayList<Projectile_Enemy> toRemovePE = new ArrayList<>();
        ArrayList<Enemy> toRemoveE = new ArrayList<>();
        ArrayList<Pickup> toRemoveP = new ArrayList<>();
        //go through each entity and add to toremove
        for(Projectile_Player p : projectilePlayers){
            if(!p.exists) {
                toRemove.add(p);
                toRemovePP.add(p);
            }
        }
        for(Projectile_Bomb p : projectileBombs){
            if(!p.exists) {
                toRemove.add(p);
                toRemovePB.add(p);
            }
        }
        for(Projectile_Enemy p : projectileEnemies){
            if(!p.exists) {
                toRemove.add(p);
                toRemovePE.add(p);
            }
        }
        for(Enemy e : enemies){
            if(!e.exists) {
                toRemove.add(e);
                toRemoveE.add(e);
            }
        }
        for(Pickup p : pickups){
            if(!p.exists){
                toRemove.add(p);
                toRemoveP.add(p);
            }
        }
        for(Entity e : entities){
            //if(!e.exists && !entities.contains(e)) {
            if(!e.exists && !toRemove.contains(e)){
                toRemove.add(e);
            }
        }
        //for each entity in toremove, add their toSpawn (for like, spawning pickups and shit)
        addEntities(true);//lol this is lazy as shit
        //THE ABOVE LINE IS BROKEN AHHH IT ADDS IT TWICE
        //it is unclear if it is still broken
        //it is ABSOULYTE STILL BROKEN WTF
        //remove toremove
        projectilePlayers.removeAll(toRemovePP);
        projectileBombs.removeAll(toRemovePB);
        projectileEnemies.removeAll(toRemovePE);
        enemies.removeAll(toRemoveE);
        pickups.removeAll(toRemoveP);
        entities.removeAll(toRemove);
    }

    //playermove(vector)
    public void playerMove(Vector polarUnitVector) {
        //set the player polar vector
        //the following line sets the speed to the current player speed
        player.setPolarVector(polarUnitVector);
    }

    //playerfocus
    public void playerFocus(boolean b) {
        //focus the player
        player.focus(b);
    }

    //playerattack
    public void playerAttack(boolean b) {
        //player attacks
        player.setCanAttack(b);
        player.setContinuousAttack(b);
    }

    //bomb
    public void bomb() {
        //if not paused, if there are no bombs, have the player bomb
        if(!paused && projectileBombs.size() == 0){
            player.bomb();
            if(spellRunning){
                inelligible = true;
            }
        }
    }

    //releasebomb
    public void releaseBomb() {
        //the player can bomb again (regardless of pause state)
        player.setCanBomb(true);
    }

    public void playerHit(){
        if(spellRunning){
            inelligible = true;
        }
    }

    public void spellOver(){
        spellRunning = false;
        if(!inelligible){
            numSpells++;
            if(numSpells % 3 == 0){
                Player.getThePlayer().addBomb();
            }
        }
        inelligible = false;
    }

    //getters and setters, including a method to get the current logicDriver

    public static Driver_GameLogic getCurrentLogicDriver() {
        return currentLogicDriver;
    }

    public int getTick() {
        return tick;
    }
    //public boolean getGameOver() {
      //  return gameOver;
    //}
    public boolean getPaused() {
        return paused;
    }

    public Player getPlayer() {
        return player;
    }
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    public ArrayList<Projectile_Player> getProjectilePlayers() {
        return projectilePlayers;
    }
    public ArrayList<Projectile_Enemy> getProjectileEnemies() {
        return projectileEnemies;
    }
    public ArrayList<Projectile_Bomb> getProjectileBombs() {
        return projectileBombs;
    }
    public ArrayList<Pickup> getPickups() {
        return pickups;
    }
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public QuadTree getQuadTree() {
        return quadTree;
    }

    //removes all enemy projectiles that's not unremovable
    public void projectileClear(){

        ArrayList<Entity> toRemoveE = new ArrayList<>();
        for(Entity e : entities){
            if(e instanceof Projectile_Enemy && !(e instanceof EP_Unremovable)){
                toRemoveE.add(e);
            }
        }
        entities.removeAll(toRemoveE);

        ArrayList<Projectile_Enemy> toRemovePE = new ArrayList<>();
        for(Projectile_Enemy e : projectileEnemies){
            if(!(e instanceof EP_Unremovable)){
                toRemovePE.add(e);
            }
        }
        projectileEnemies.removeAll(toRemovePE);
    }

    //when the boss needs to clear the board remove all enemies and projectiles that's not blade
    public void bossClear(boolean trueClear){

        ArrayList<Entity> toRemoveE = new ArrayList<>();
        for(Entity e : entities){
            //use above one since blade is only unremovable here that matters
            if(!trueClear) {
                if ((e instanceof Enemy || e instanceof Projectile_Enemy) && !(e instanceof Enemy_Boss) && !(e instanceof EP_Blade)) {
//            if((e instanceof Enemy || e instanceof Projectile_Enemy) && !(e instanceof Enemy_Boss) && !(e instanceof EP_Unremovable)){
                    toRemoveE.add(e);
                }
            }
            else{
                if(e instanceof Enemy || e instanceof Projectile_Enemy){
                    toRemoveE.add(e);
                }
            }
        }
        entities.removeAll(toRemoveE);

        ArrayList<Projectile_Enemy> toRemovePE = new ArrayList<>();
        for(Projectile_Enemy e : projectileEnemies){
            if(!trueClear) {
                if (!(e instanceof EP_Blade)) {
                    toRemovePE.add(e);
                }
            }
            else{
                toRemovePE.add(e);
            }
        }
        projectileEnemies.removeAll(toRemovePE);

        ArrayList<Enemy> toRemoveEn = new ArrayList<>();
        for(Enemy e : enemies){
            if(!trueClear) {
                if (!(e instanceof Enemy_Boss)) {
                    toRemoveEn.add(e);
                }
            }
            else{
                toRemoveEn.add(e);
            }
        }
        enemies.removeAll(toRemoveEn);
    }

    public void defeat(){
        if(!defeatRunning) {
            defeatRunning = true;
            Runnable defeatRunnable = new Runnable() {
                public void run() {
                    try {
                        Thread.sleep((long) (1500));
                        Driver.clear(1);
                        defeatRunning = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(0);
                    }
                }
            };
            Thread thread = new Thread(defeatRunnable);
            thread.start();
        }
    }

    public void victory(){
        if(!defeatRunning){
            if(!victoryRunning){
                victoryRunning = true;
                Runnable victoryRunnable = new Runnable() {
                    public void run() {
                        try {
                            //theoretically setting the player invulnerable should make it impossible for this to collide
                            //with a possible defeat sequence in the future, since the player would never die
                            Player.getThePlayer().victoryInvulnerable();
                            Thread.sleep((long) (2000));
                            Driver_Game.getCurrentGameDriver().victory();
                            victoryRunning = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.exit(0);
                        }
                    }
                };
                Thread thread = new Thread(victoryRunnable);
                thread.start();
            }
        }
    }

    public int getDifficulty(){
        return difficulty;
    }

    public int getNumSpells(){
        return numSpells;
    }

    public boolean isInelligible(){
        return inelligible;
    }
    public boolean isSpellRunning(){
        return spellRunning;
    }
}