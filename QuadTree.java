import java.util.ArrayList;

//quad tree class
public class QuadTree {
    //max objects
    private int MAX_OBJECTS = 8;
    //max levels
    private int MAX_LEVELS = 5;
    //level
    private int level;


    public static boolean bool = false;



    //arraylist of entities
    private ArrayList<Entity> entities = new ArrayList<>();
    //HitboxABBA bounds
    //no i should jus thave doubles

    private double xmin, ymin, xmax, ymax;
    private QuadTree[] nodes = new QuadTree[4];

    //constructor
    public QuadTree(int level, double xmin, double ymin, double xmax, double ymax){
        this.level = level;
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }
    //clear method
    public void clear(){
        entities.clear();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }
    //remove method
    public void remove(Entity toRemove){
        if(entities.contains(toRemove)){
            entities.remove(toRemove);
        }
        else{
            int index = getIndex(toRemove);
            if(index != -1){
                nodes[index].remove(toRemove);
            }
        }
    }
    //split method
    private void split(){
        double xavg = (xmin + xmax)/2;
        double yavg = (ymin + ymax)/2;
        nodes[0] = new QuadTree(level + 1, xmin, ymin, xavg, yavg);
        nodes[1] = new QuadTree(level + 1, xavg, ymin, xmax, yavg);
        nodes[2] = new QuadTree(level + 1, xavg, yavg, xmax, ymax);
        nodes[3] = new QuadTree(level + 1, xmin, yavg, xavg, ymax);
    }
    //getindex method
    private int getIndex(Entity toAdd){
        Hitbox_AABB toTest = toAdd.getTwoFrameHitbox();
        double xavg = (xmin + xmax)/2;
        double yavg = (ymin + ymax)/2;
        if(toTest.encapsulatedBy(xmin, xavg, ymin, yavg)){
            return 0;
        }
        else if(toTest.encapsulatedBy(xavg, xmax, ymin, yavg)){
            return 1;
        }
        else if(toTest.encapsulatedBy(xavg, xmax, yavg, ymax)){
            return 2;
        }
        else if(toTest.encapsulatedBy(xmin, xavg, yavg, ymax)){
            return 3;
        }
        return -1;
    }
    //insert method
    public void insert(Entity toAdd){
        //if there are subnodes
        if (nodes[0] != null) {
            int index = getIndex(toAdd);
            if (index != -1) {
                nodes[index].insert(toAdd);
                return;
            }
        }

        //add to this node
        entities.add(toAdd);
        //split if necessary
        if (entities.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodes[0] == null) {
                split();
            }

            int i = 0;
            //insert everything into subnodes
            //is this right? i++ in else?
            while (i < entities.size()) {
                int index = getIndex(entities.get(i));
                if (index != -1) {
                    nodes[index].insert(entities.remove(i));
                }
                else {
                    i++;
                }
            }
        }
    }
    //retrieve method
    public ArrayList<Entity> retrieve(ArrayList<Entity> baseList, Entity toRetrieve){
        //I hypothesize that this method is not commutative.
        //An object on a higher layer will not retrieve the objects on the lower layers
        //While an object on a lower layer will retrieve all objects from higher layers
        //Since I remove each object from the quadtree after they check collisions
        //If the order is wrong I believe I may miss some collisions

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                //test to see if toRetrieve collides with which nodes
                double xavg = (nodes[i].getXmin() + nodes[i].getXmax())/2;
                double yavg = (nodes[i].getYmin() + nodes[i].getYmax())/2;
                Entity dummyEntity = new Entity(new Vector(xavg, yavg), 1);
                double xdist = xavg - nodes[i].getXmin();
                double ydist = yavg - nodes[i].getYmin();
                Hitbox_AABB dummy = new Hitbox_AABB(xdist, ydist, xdist, ydist, dummyEntity);
                if(toRetrieve.getTwoFrameHitbox().collidesWith(dummy)){
                    nodes[i].retrieve(baseList, toRetrieve);
                }
            }
        }

        //baseList.addAll(entities);
        for(Entity e : entities){
            if(toRetrieve instanceof Player){
                if(!(e instanceof Player) && !(e instanceof Projectile_Player) && !(e instanceof Projectile_Bomb))
                    baseList.add(e);
            }
            else if(toRetrieve instanceof Enemy){
                if(!(e instanceof Enemy) && !(e instanceof Projectile_Enemy))
                    baseList.add(e);
            }
            else if(toRetrieve instanceof Projectile_Enemy){
                if(!(e instanceof Enemy) && !(e instanceof Projectile_Enemy))
                    baseList.add(e);
            }
            else if(toRetrieve instanceof Projectile_Player){
                if(!(e instanceof Player) && !(e instanceof Projectile_Player) && !(e instanceof Projectile_Bomb))
                    baseList.add(e);
            }
            else if(toRetrieve instanceof Projectile_Bomb){
                if(!(e instanceof Player) && !(e instanceof Projectile_Player) && !(e instanceof Projectile_Bomb))
                    baseList.add(e);
            }
        }

        return baseList;
    }

    public QuadTree[] getNodes() {
        return nodes;
    }

    public double getXmin() {
        return xmin;
    }

    public double getYmin() {
        return ymin;
    }

    public double getXmax() {
        return xmax;
    }

    public double getYmax() {
        return ymax;
    }

    public int getLevels(){
        if(nodes[0] != null){
            return nodes[0].getLevels();
        }
        return level;
    }

    public int getSize(){
        int size = entities.size();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                size += nodes[i].getSize();
            }
        }
        return size;
    }

    public ArrayList<Entity> getEntities() {
        ArrayList<Entity> toRet = new ArrayList<>(entities);
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                toRet.addAll(nodes[i].getEntities());
            }
        }
        return toRet;
    }
}