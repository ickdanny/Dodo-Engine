//orbit path class extends interface
public class Path_Orbit implements Path{
    //vector center
    private Vector center;
    //private vector <angular velocity in angle per second, distance from center>
    private Vector AVelocity;
    //current angle
    private double initAngle;
    private double currentAngle;
    //constructor (vector, int)
    //this Vector constructor has the form angle / distance where angle is angles per second
    public Path_Orbit(Vector center, Vector AVelocity, double initAngle){
        this.center = center;
        this.AVelocity = AVelocity;
        currentAngle = initAngle;
        this.initAngle = initAngle;
    }
    //public boolean hasCenterUpdated (if center is an entity return if the entity has update)
    //the structure in entity will look something like (if hasCenterUpdated then getNextPolar, otherwise return)
    public boolean hasCenterUpdated(){
        if(center instanceof Entity && ((Entity)center).getExists()){
            return ((Entity)center).getHasUpdated();
        }
        return true;
    }
    //public vector getNextPolar(Vector currentPos)
    @Override
    public Vector getNextPolar(Vector currentPos) {
        currentAngle += (AVelocity.getA()/ Driver_Game.getUPS());
        if(currentAngle >= 360){
            currentAngle -= 360;
        }
        else if(currentAngle <= -360){
            currentAngle += 360;
        }

        Vector nextPosRelativeToCenter = Vector.polarToVelocity(new Vector(currentAngle, AVelocity.getB()));
        //add the velocity vector of the center
        if(center instanceof Entity){
            nextPosRelativeToCenter.addToThis(Vector.scalarMultiple(((Entity) center).getVelocityVector(), 1/ Driver_Game.getUPS()));
        }
        Vector nextPos = Vector.add(nextPosRelativeToCenter, center);

        //find the vector to that position
        Vector velocityToNextPosition = Vector.polarToVelocity(new Vector(Vector.getAngle(currentPos, nextPos), Vector.getDistance(currentPos, nextPos)));
        //Entity will be adding the velocity vector based on polar and the UPS, so need to add this here
        Vector toRet = Vector.scalarMultiple(velocityToNextPosition, Driver_Game.getUPS());
        toRet = Vector.velocityToPolar(toRet);

//        System.out.println(toRet);
        return toRet;
    }

    @Override
    public boolean isOver(){
        if(center instanceof Entity){
            return !((Entity)center).getExists();
        }
        return false;
    }

    public void setDistance(double distance){
        AVelocity.setB(distance);
    }

    public void setVelocity(double velocity){
        AVelocity.setA(velocity);
    }

    public Vector getAVelocity(){
        return this.AVelocity;
    }

    public Path_Orbit clone(){
        try{
            super.clone();
        }
        catch(CloneNotSupportedException e) {
            return new Path_Orbit(center, AVelocity.clone(), initAngle);
        }

        return null;
    }

    //important to note, this kind of path will teleport entities to their next point regardless of their speed.
    //When using this always create a path with the current distance
}
//clockwise