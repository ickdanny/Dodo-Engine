import java.util.ArrayList;

public class Instruction_StartPolygonPathRandom extends Instruction {

    private double points, minX, maxX, minY, maxY, timeTotal;
    public Instruction_StartPolygonPathRandom(Entity owner, double points, double minX, double maxX, double minY, double maxY, double timeTotal){
        super(owner);
        this.points = points;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.timeTotal = timeTotal;
    }

    public Instruction_StartPolygonPathRandom(Entity owner, Behavior next, double points, double minX, double maxX, double minY, double maxY, double timeTotal){
        super(owner, next);
        this.points = points;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.timeTotal = timeTotal;
    }

    @Override
    public boolean doAction(){
        owner.newPath(constructPath(points, minX, maxX, minY, maxY, timeTotal, owner));
        return true;
    }


    private static Path_Polygon constructPath(double points, double minX, double maxX, double minY, double maxY, double timeTotal, Vector initPos){
        double xDiff = maxX - minX;
        double yDiff = maxY - minY;
        ArrayList<Vector> pathPoints = new ArrayList<>();
        double distanceTotal = 0;
        for(int i = 0; i < points; i++){
            Vector prevPoint;
            if(pathPoints.size() >= 1){
                prevPoint = pathPoints.get(i - 1);
            }
            else if(initPos != null){
                prevPoint = initPos;
            }
            else{
                prevPoint = null;
            }
            double x = minX + (Math.random() * xDiff);
            double y = minY + (Math.random() * yDiff);
            Vector newPoint = new Vector(x, y);
            pathPoints.add(newPoint);
            if(prevPoint != null){
                distanceTotal += Vector.getDistance(prevPoint, newPoint);
            }
        }
        double speed = distanceTotal/timeTotal;
        return new Path_Polygon(pathPoints, false, speed);
    }
}
