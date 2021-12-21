//hitboxcircle class implements interface
public class Hitbox_Circle implements Hitbox {
    //Entity owner
    public Entity owner;

    public Hitbox_Circle(){}
    public Hitbox_Circle(Entity owner){
        this.owner = owner;
    }
    //public boolean collidesWith(Hitbox a)
    public boolean collidesWith(Hitbox a) {
        //if AABB do AABB
        if(a instanceof Hitbox_AABB)
            return collidesWithAABB((Hitbox_AABB)a);
        //if Circle do circle
        else if(a instanceof Hitbox_Circle)
            return collidesWithCircle((Hitbox_Circle)a);
        //if Line do Line
        else if(a instanceof Hitbox_Line)
            return collidesWithLine((Hitbox_Line)a);
        return false;
    }
    //private boolean collidesWithAABB(AABB a)
    //the algorithm
    private boolean collidesWithAABB(Hitbox_AABB a){
        //check to see if the circle hits AABB at the sides
        if(a.collidesWith(new Hitbox_AABB(1,0,1,0,owner))) {
            return true;
        }
        else if(a.collidesWith(new Hitbox_AABB(0,1,0,1,owner))) {
            return true;
        }
        //check to see if the circle hits AABB at the corners
        else{
            if(this.getR() >= Vector.getDistance(this.getCenter(), Vector.add(a.getCenter(), new Vector(a.xplus * a.getSize(), a.yplus * a.getSize()))))
                return true;
            else if(this.getR() >= Vector.getDistance(this.getCenter(), Vector.add(a.getCenter(), new Vector(a.xplus * a.getSize(), a.yminus * -1 * a.getSize()))))
                return true;
            else if(this.getR() >= Vector.getDistance(this.getCenter(), Vector.add(a.getCenter(), new Vector(a.xminus * -1 * a.getSize(), a.yplus * a.getSize()))))
                return true;
            else if(this.getR() >= Vector.getDistance(this.getCenter(), Vector.add(a.getCenter(), new Vector(a.xminus * -1 * a.getSize(), a.yminus * -1 * a.getSize()))))
                return true;
        }
        return false;
    }
    //private boolean collidesWithCircle(Circle a)
    //the algorithm
    private boolean collidesWithCircle(Hitbox_Circle a){
        return this.getR() + a.getR() >= Vector.getDistance(this.getCenter(), a.getCenter());
    }
    //private boolean collidesWithLine(Line a)
    //the algorithm
    private boolean collidesWithLine(Hitbox_Line a){
        double circleToEndPoint = Vector.getDistance(this.getCenter(), a.getEndPoint());
        if(this.getR() >= circleToEndPoint)
            return true;
        double circleToCenterPoint = Vector.getDistance(this.getCenter(), a.getCenter());
        if(this.getR() >= circleToCenterPoint)
            return true;

        double cx = this.getCenter().getA();
        double cy = this.getCenter().getB();

        double x1 = a.getCenter().getA();
        double x2 = a.getEndPoint().getA();
        double y1 = a.getCenter().getB();
        double y2 = a.getEndPoint().getB();

        //mysterious dot product magic
        double dot = (((cx - x1)*(x2 - x1)) + ((cy - y1)*(y2 - y1)))/Math.pow(a.getLength(), 2);
        double closeX = x1 + (dot * (x2 - x1));
        double closeY = y1 + (dot * (y2 - y1));
        Vector close = new Vector(closeX, closeY);

        //see if the closest point on the infinite line is within the line segment
        boolean onLine = a.onLine(close);
        if(!onLine){
            return false;
        }

        //check if the closest point is within the circle
        double circleDistance = Vector.getDistance(this.getCenter(), close);
        if(circleDistance <= this.getR()){
            return true;
        }
        return false;
    }
    //public Vector getCenter
    //returns the owner's cartesian vector
    public Vector getCenter(){
        return owner.getPosition();
    }
    //public double getR
    //returns the owner's size
    public double getR(){
        return owner.getSize();
    }
    @Override
    public Hitbox_AABB getBoundingAABB(){
        return new Hitbox_AABB(1, 1, 1, 1, this.owner);
    }
    @Override
    public void setOwner(Entity owner){
        this.owner = owner;
    }
    public Entity getOwner(){
        return owner;
    }

    @Override
    public String toString(){
        return "Belonging to " + owner + " circle";
    }
}