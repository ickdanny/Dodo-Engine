//hitboxAABB class implements interface
public class Hitbox_AABB implements Hitbox {
    //double xplus, yplus, xminus, yminus
    //these are all in relation to the entity size.
    public double xplus = 1, yplus = 1, xminus = 1, yminus = 1;
    //Entity owner
    public Entity owner;

    public Hitbox_AABB(){}
    public Hitbox_AABB(Entity owner){
        this.owner = owner;
    }
    public Hitbox_AABB(double xplus, double yplus, double xminus, double yminus, Entity owner){
        this.xplus = xplus;
        this.yplus = yplus;
        this.xminus = xminus;
        this.yminus = yminus;
        this.owner = owner;
    }
    //public boolean collidesWith(Hitbox a)
    public boolean collidesWith(Hitbox a) {
        //if AABB do AABB
        if(a instanceof Hitbox_AABB)
            return collidesWithAABB((Hitbox_AABB)a);
        //if Circle do circle
        if(a instanceof Hitbox_Circle)
            return collidesWithCircle((Hitbox_Circle)a);
        //if Line do Line
        if(a instanceof Hitbox_Line)
            return collidesWithLine((Hitbox_Line)a);
        return false;
    }

    //private boolean collidesWithAABB(AABB a)
    //the algorithm
    private boolean collidesWithAABB(Hitbox_AABB a){
        double trueXPlus = xplus * owner.getSize();
        double trueXMinus = xminus * owner.getSize();
        double trueYPlus = yplus * owner.getSize();
        double trueYMinus = yminus * owner.getSize();
        double aTrueXPlus = a.xplus * a.owner.getSize();
        double aTrueXMinus = a.xminus * a.owner.getSize();
        double aTrueYPlus = a.yplus * a.owner.getSize();
        double aTrueYMinus = a.yminus * a.owner.getSize();
        double xGap;
        double yGap;
        if(owner.getX() > a.owner.getX()){
            xGap = trueXMinus + aTrueXPlus;
        }
        else {
            xGap = aTrueXMinus + trueXPlus;
        }
        if(owner.getY() > a.owner.getY()){
            yGap = trueYMinus + aTrueYPlus;
        }
        else{
            yGap = aTrueYMinus + trueYPlus;
        }

        double xDistance = Math.abs(owner.getX() - a.owner.getX());
        double yDistance = Math.abs(owner.getY() - a.owner.getY());
        return(xGap >= xDistance && yGap >= yDistance);
    }
    //private boolean collidesWithCircle(Circle a)
    //the algorithm
    private boolean collidesWithCircle(Hitbox_Circle a){
        //check to see if the circle hits AABB at the sides
        if(this.collidesWithAABB(new Hitbox_AABB(1,0,1,0,a.owner))) {
            return true;
        }
        else if(this.collidesWithAABB(new Hitbox_AABB(0,1,0,1,a.owner))) {
            return true;
        }
        //check to see if the circle hits AABB at the corners
        else{
            if(a.getR() >= Vector.getDistance(a.getCenter(), Vector.add(this.getCenter(), new Vector(xplus * owner.getSize(), yplus * owner.getSize()))))
                return true;
            else if(a.getR() >= Vector.getDistance(a.getCenter(), Vector.add(this.getCenter(), new Vector(xplus * owner.getSize(), yminus * -1 * owner.getSize()))))
                return true;
            else if(a.getR() >= Vector.getDistance(a.getCenter(), Vector.add(this.getCenter(), new Vector(xminus * -1 * owner.getSize(), yplus * owner.getSize()))))
                return true;
            else if(a.getR() >= Vector.getDistance(a.getCenter(), Vector.add(this.getCenter(), new Vector(xminus * -1 * owner.getSize(), yminus * -1 * owner.getSize()))))
                return true;
        }
        return false;
    }
    //private boolean collidesWithLine(Line a)
    //the algorithm
    private boolean collidesWithLine(Hitbox_Line a){
        return a.collidesWith(this);
    }

    //whether or not a is fully inside of this
    public boolean encapsulates(Hitbox_AABB a){
        double trueXPlusPos = this.owner.getPosition().a + (this.owner.getSize() * xplus);
        double trueXMinusPos = this.owner.getPosition().a + (this.owner.getSize() * xminus * -1);
        double trueYPlusPos = this.owner.getPosition().b + (this.owner.getSize() * yplus);
        double trueYMinusPos = this.owner.getPosition().b + (this.owner.getSize() * yminus * -1);
        double aTrueXPlusPos = a.owner.getPosition().a + (a.owner.getSize() * xplus);
        double aTrueXMinusPos = a.owner.getPosition().a + (a.owner.getSize() * xminus * -1);
        double aTrueYPlusPos = a.owner.getPosition().b + (a.owner.getSize() * yplus);
        double aTrueYMinusPos = a.owner.getPosition().b + (a.owner.getSize() * yminus * -1);
        return trueXPlusPos > aTrueXPlusPos && trueXMinusPos < aTrueXMinusPos && trueYPlusPos > aTrueYPlusPos && trueYMinusPos < aTrueYMinusPos;
    }

    public boolean encapsulates(Vector a){
        double trueXPlusPos = this.owner.getPosition().a + (this.owner.getSize() * xplus);
        double trueXMinusPos = this.owner.getPosition().a + (this.owner.getSize() * xminus * -1);
        double trueYPlusPos = this.owner.getPosition().b + (this.owner.getSize() * yplus);
        double trueYMinusPos = this.owner.getPosition().b + (this.owner.getSize() * yminus * -1);
        double aX = a.a;
        double aY = a.b;
        return trueXPlusPos > aX && trueXMinusPos < aX && trueYPlusPos > aY && trueYMinusPos < aY;
    }

    public boolean encapsulatedBy(Hitbox_AABB a){
        return a.encapsulates(this);
    }

    public boolean encapsulatedBy(double xLowBound, double xHighBound, double yLowBound, double yHighBound){
        double trueXPlusPos = this.owner.getPosition().a + (this.owner.getSize() * xplus);
        double trueXMinusPos = this.owner.getPosition().a + (this.owner.getSize() * xminus * -1);
        double trueYPlusPos = this.owner.getPosition().b + (this.owner.getSize() * yplus);
        double trueYMinusPos = this.owner.getPosition().b + (this.owner.getSize() * yminus * -1);
        return xHighBound > trueXPlusPos && xLowBound < trueXMinusPos && yHighBound > trueYPlusPos && yLowBound < trueYMinusPos;
    }

    public Hitbox_AABB twoFrameAABB(Vector previousPosition){
        double trueXPlusPos = this.owner.getPosition().a + (this.owner.getSize() * xplus);
        double trueXMinusPos = this.owner.getPosition().a + (this.owner.getSize() * xminus * -1);
        double trueYPlusPos = this.owner.getPosition().b + (this.owner.getSize() * yplus);
        double trueYMinusPos = this.owner.getPosition().b + (this.owner.getSize() * yminus * -1);
        double lastTrueXPlusPos = previousPosition.a + (this.owner.getSize() * xplus);
        double lastTrueXMinusPos = previousPosition.a + (this.owner.getSize() * xminus * -1);
        double lastTrueYPlusPos = previousPosition.b + (this.owner.getSize() * yplus);
        double lastTrueYMinusPos = previousPosition.b + (this.owner.getSize() * yminus * -1);
        //the bounds of the final hitbox
        double finalTrueXPlusPos = Math.max(trueXPlusPos, lastTrueXPlusPos);
        double finalTrueXMinusPos = Math.min(trueXMinusPos, lastTrueXMinusPos);
        double finalTrueYPlusPos = Math.max(trueYPlusPos, lastTrueYPlusPos);
        double finalTrueYMinusPos = Math.min(trueYMinusPos, lastTrueYMinusPos);
        //convert back into a distance from center (current center)
        double trueXPlusDistance = finalTrueXPlusPos - this.owner.getPosition().a;
        double trueXMinusDistance = finalTrueXMinusPos - this.owner.getPosition().a;
        double trueYPlusDistance = finalTrueYPlusPos - this.owner.getPosition().b;
        double trueYMinusDistance = finalTrueYMinusPos - this.owner.getPosition().b;
        //convert into the simple ratios
        trueXPlusDistance = Math.abs(trueXPlusDistance) / this.owner.getSize();
        trueXMinusDistance = Math.abs(trueXMinusDistance) / this.owner.getSize();
        trueYPlusDistance = Math.abs(trueYPlusDistance) / this.owner.getSize();
        trueYMinusDistance = Math.abs(trueYMinusDistance) / this.owner.getSize();
        return new Hitbox_AABB(trueXPlusDistance, trueYPlusDistance, trueXMinusDistance, trueYMinusDistance, this.owner);
    }
    //public Vector getCenter
    //returns the owner's cartesian vector
    public Vector getCenter(){
        return owner.getPosition();
    }
    //public double getSize
    //returns the owner's size
    public double getSize() {
        return owner.getSize();
    }
    @Override
    public Hitbox_AABB getBoundingAABB(){
        return new Hitbox_AABB(xplus, yplus, xminus, yminus, this.owner);
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
        return "Belonging to " + owner + " " + (Math.abs(xplus) + Math.abs(xminus)) + " by " + (Math.abs(yplus) + Math.abs(yminus));
    }
}