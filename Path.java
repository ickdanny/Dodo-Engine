//path interface
public interface Path {
    //public Boolean isOver
    //returns true if the path is over.
    boolean isOver();
    //public Vector getNextPolar
    //returns the polar vector needed to get to the next point on the path
    Vector getNextPolar(Vector currentPos);
    Path clone();
}