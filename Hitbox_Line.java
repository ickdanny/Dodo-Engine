import java.awt.*;

//line hitbox class implements interface
public class Hitbox_Line implements Hitbox {
    public Color color = Color.white;
    //double angle
    public double angle;
    //length is the size of the entity

    //Entity owner
    public Entity owner;

    public Hitbox_Line(){}
    public Hitbox_Line(Entity owner){
        this.owner = owner;
    }

    public Hitbox_Line(Entity owner, double angle){
        this(owner);
        this.angle = angle;
    }
    public Hitbox_Line(double angle){
        this.angle = angle;
    }

    public Hitbox_Line(Entity owner, Color color){
        this.owner = owner;
        this.color = color;
    }

    public Hitbox_Line(Entity owner, double angle, Color color){
        this(owner, color);
        this.angle = angle;
    }
    public Hitbox_Line(double angle, Color color){
        this.angle = angle;
        this.color = color;
    }
    public boolean collidesWith(Hitbox a) {
        //if AABB do AABB
        if(a instanceof Hitbox_AABB) {
            return collidesWithAABB((Hitbox_AABB) a);
        }
        //if Circle do circle
        if(a instanceof Hitbox_Circle) {
            return collidesWithCircle((Hitbox_Circle) a);
        }
        //if Line do Line
        if(a instanceof Hitbox_Line) {
            return collidesWithLine((Hitbox_Line) a);
        }
        return false;
    }

    //private boolean collidesWithAABB(AABB a)
    //the algorithm
    private boolean collidesWithAABB(Hitbox_AABB a){
        //test endpoint encapsulation first
        //need to also test for the center
        if(a.encapsulates(this.getEndPoint()) || a.encapsulates(this.getCenter())) {
            return true;
        }
        //test the outer bound circle
        //find position vector of the four vertices
        Vector topRight = Vector.add(a.owner.getPosition(), new Vector(a.xplus * a.owner.getSize(), a.yminus * -1 * a.owner.getSize()));
        Vector topLeft = Vector.add(a.owner.getPosition(), new Vector(a.xminus * -1 * a.owner.getSize(), a.yminus * -1 * a.owner.getSize()));
        Vector bottomRight = Vector.add(a.owner.getPosition(), new Vector(a.xplus * a.owner.getSize(), a.yplus * a.owner.getSize()));
        Vector bottomLeft = Vector.add(a.owner.getPosition(), new Vector(a.xminus * -1 * a.owner.getSize(), a.yplus * a.owner.getSize()));
//        Vector trueCenter = Vector.add(a.owner.getPosition(), new Vector((a.xplus - a.xminus) * a.owner.getSize(), (a.yplus - a.yminus) * a.owner.getSize()));
        Vector added = Vector.add(Vector.add(Vector.add(topRight,topLeft),bottomRight),bottomLeft);
        Vector trueCenter = Vector.scalarMultiple(added, .25);

        Hitbox_Line top = new Hitbox_Line(new Entity(topLeft, Vector.getDistance(topLeft, topRight)), Vector.getAngle(topLeft, topRight));
        Hitbox_Line right = new Hitbox_Line(new Entity(topRight, Vector.getDistance(topRight, bottomRight)), Vector.getAngle(topRight, bottomRight));
        Hitbox_Line bot = new Hitbox_Line(new Entity(bottomRight, Vector.getDistance(bottomRight, bottomLeft)), Vector.getAngle(bottomRight, bottomLeft));
        Hitbox_Line left = new Hitbox_Line(new Entity(bottomLeft, Vector.getDistance(bottomLeft, topLeft)), Vector.getAngle(bottomLeft, topLeft));

        return this.collidesWithLine(top) || this.collidesWith(right) || this.collidesWith(bot) || this.collidesWith(left);
    }
    //private boolean collidesWithCircle(Circle a)
    //the algorithm
    private boolean collidesWithCircle(Hitbox_Circle a){
        return a.collidesWith(this);
    }
    //private boolean collidesWithLine(Line a)
    //the algorithm
    private boolean collidesWithLine(Hitbox_Line a){
        double x1 = this.getCenter().getA();
        double y1 = this.getCenter().getB();
        double x2 = this.getEndPoint().getA();
        double y2 = this.getEndPoint().getB();
        double x3 = a.getCenter().getA();
        double y3 = a.getCenter().getB();
        double x4 = a.getEndPoint().getA();
        double y4 = a.getEndPoint().getB();

        //mysterious equation
        double uA = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));
        double uB = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

        if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {
            return true;
        }
        return false;
    }
    //public Vector getCenter
    //returns the owner's cartesian vector
    public Vector getCenter(){
        return owner.getPosition();
    }
    //returns the position vector of the endpoint
    public Vector getEndPoint(){
        Vector adding = Vector.polarToVelocity(new Vector(angle, owner.getSize()));
        Vector pos = getCenter();
        return Vector.add(adding, pos);
    }
    //public double getLength
    //returns the owner's size
    public double getLength(){
        return owner.getSize();
    }
    @Override
    public Hitbox_AABB getBoundingAABB(){
        Vector toEndPoint = Vector.polarToVelocity(new Vector(angle, getLength()));
        double xplus, xminus, yplus, yminus;
        //divide by size since AABB will have size regardless
        if(toEndPoint.a < 0){
            xplus = 0;
            xminus = Math.abs(toEndPoint.a)/owner.getSize();
        }
        else{
            xplus = toEndPoint.a/owner.getSize();
            xminus = 0;
        }
        if(toEndPoint.b < 0){
            yplus = 0;
            yminus = Math.abs(toEndPoint.b)/owner.getSize();
        }
        else{
            yplus = toEndPoint.b/owner.getSize();
            yminus = 0;
        }
        return new Hitbox_AABB(xplus, yplus, xminus, yminus, this.owner);
    }
    @Override
    public void setOwner(Entity owner){
        this.owner = owner;
    }

    @Override
    public String toString(){
        return "Belonging to " + owner + " " + angle;
    }

    public Entity getOwner(){
        return owner;
    }

    public Boolean onLine(Vector point){
        return onLine(this.getCenter(), this.getEndPoint(), point);
    }

    //method of finding if a point is on a line (or within 0.1 units of a line);
    public static Boolean onLine(Vector end1, Vector end2, Vector point){
        double distance1 = Vector.getDistance(end1, point);
        double distance2 = Vector.getDistance(end2, point);
        double length = Vector.getDistance(end1, end2);
        double buffer = 0.1;
        return distance1 + distance2 >= length - buffer && distance1 + distance2 <= length + buffer;
    }
}