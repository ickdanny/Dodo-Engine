import java.util.ArrayList;

//polygon path class extends interface
public class Path_Polygon implements Path {
    //arraylist of points(vectors); the path
    private ArrayList<Vector> points;
    //private int pathPoint
    private int pathPoint = 0;
    //private boolean pathOver, infinitePathing
    private boolean pathOver = false, infinitePathing;

    private double speed;
    //constructor(infinitePathing, arraylist of vectors(points))
    public Path_Polygon(ArrayList<Vector> points, boolean infinitePathing, double speed){
        this.infinitePathing = infinitePathing;
        this.points = points;
        this.speed = speed;
    }

    public Path_Polygon(Vector point, boolean infinitePathing, double speed){
        this.infinitePathing = infinitePathing;
        this.speed = speed;
        ArrayList<Vector> points = new ArrayList<>();
        points.add(point);
        this.points = points;
    }
    //the order for entity is check if over(if over STOP), then if not over getnextpolar
    //public Boolean isOver
    //returns if path is over
    @Override
    public boolean isOver(){
        return pathOver;
    }
    @Override
    public Vector getNextPolar(Vector currentPos){


        //if we're out of points then stop moving and stop pathing
        if(pathPoint >= points.size()){
            pathOver = true;
            return new Vector();
        }

        Vector nextPoint = points.get(pathPoint).clone();
        Vector goTo = currentPos.clone();
        double remainingDistance = speed/Driver_Game.getUPS();
        double distanceToNextPoint = Vector.getDistance(goTo, nextPoint);

        //this block is for switching points
        while(remainingDistance > distanceToNextPoint){
            //set the frame of reference to the point passed
            goTo = nextPoint;
            remainingDistance -= distanceToNextPoint;
            pathPoint++;
            //testing if we just finished the path
            if(pathPoint >= points.size()){
                //if the path isn't infinite just go to the last point
                if(!infinitePathing){
                    distanceToNextPoint = 0;
                    break;
                }
                //if it is infinite go all the way back to point 1
                else{
                    pathPoint = 0;
                }
            }
            //in infinite path, set the next point to the first point
            //the distance here would be the distance between the new reference point and first point
            nextPoint = points.get(pathPoint).clone();
            distanceToNextPoint = Vector.getDistance(goTo, nextPoint);
        }

        //this would be true if we just reached the break statement
        //this would be the end of a finite loop
        if(remainingDistance > distanceToNextPoint){
            goTo = nextPoint;
        }
        //this is the case that the next point is not going to be reached, in which case we set
        //goto to the point we're going to
        else{
            Vector polar = new Vector(Vector.getAngle(goTo, nextPoint), remainingDistance);
            Vector velocity = Vector.polarToVelocity(polar);
            goTo.addToThis(velocity);
        }

        //now we set the polar vector to the vector that will take us to GoTo
        double trueDistance = Vector.getDistance(currentPos, goTo);
        return new Vector(Vector.getAngle(currentPos, goTo), trueDistance * Driver_Game.getUPS());






//        if(pathPoint >= points.size()){
//            pathOver = true;
//            return new Vector();
//        }
//        Vector nextPoint = points.get(pathPoint).clone();
//        Vector goTo = currentPos.clone();
//        double remainingDistance = speed;
//        double distanceToNextPoint = Vector.getDistance(goTo, nextPoint);
//        while(remainingDistance > distanceToNextPoint){
//            goTo = nextPoint;
//            remainingDistance -= distanceToNextPoint;
//            pathPoint++;
//            if(pathPoint > points.size()){
//                if(infinitePathing){
//                    distanceToNextPoint = 0;
//                    break;
//                }
//                else{
//                    pathPoint = 0;
//                }
//            }
//            nextPoint = points.get(pathPoint).clone();
//            distanceToNextPoint = Vector.getDistance(goTo, nextPoint);
//        }
//        if(remainingDistance > distanceToNextPoint){
//            goTo = nextPoint;
//        }
//        else{
//            Vector polar = new Vector(Vector.getAngle(goTo, nextPoint), remainingDistance);
//            Vector velocity = Vector.polarToVelocity(polar);
//            goTo.addToThis(velocity);
//        }
//        double trueDistance = Vector.getDistance(currentPos, goTo);
//        return new Vector(Vector.getAngle(currentPos, goTo), trueDistance);
    }
    //if the pathpoint is invalid, return a vector <0,0>, set the pathOver to true
    //vector nextPoint = next point clone
    //vector goTo = currentPos clone
    //double remainingDistance = speed
    //double distanceToNextPoint
    //while remainingdistance > distancetonextpoint
    //set goto to next point, set next point further. If no next point(pathpoint is too big) break
    //if(remainigdistance > distancetonextpoint) (this is for when the path is over)
    //set the goTo to the final point
    //else
    //go as far as possible
    //find the angle
    //find the point remainingDistance from goto on the angle
    //add the point to goto (vector)
    //calculate the polar vector, return (conversion from polar to velocity vector will be done by the entity)
    public void setSpeed(double speed){
        this.speed = speed;
    }

    public Path_Polygon clone(){
        try{
            super.clone();
        }
        catch(CloneNotSupportedException e) {
            ArrayList<Vector> newPoints = new ArrayList<>();
            for(Vector v : points){
                newPoints.add(v.clone());
            }
            return new Path_Polygon(newPoints, infinitePathing, speed);
//        super.clone();
        }
        //surely this will never occur
        return null;
    }
}