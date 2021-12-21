//Vector Class implements cloneable
public class Vector {
    //public double a
    //public double b
    protected double a = 0;
    protected double b = 0;
    //public Vector(double a, double b)
    //initializes the vector
    public Vector() {
    }
    public Vector(double a, double b){
        this.a = a;
        this.b = b;
    }

    //add method
    public void addToThis(Vector toAdd){
        a += toAdd.a;
        b += toAdd.b;
    }

    //public static Vector add(Vector a, Vector b)
    //adds the two vectors together, doesn't change either and returns the sum.
    public static Vector add(Vector a, Vector b){
        return new Vector(a.a + b.a, a.b + b.b);
    }
    //public static Vector doDotProduct(Vector a, Vector b)
    //multiplies the two vectors together, doesn't change either and returns the product.
    public static double doDotProduct(Vector a, Vector b){
        return (a.a + b.a) + (a.b + b.b);
    }
    public static Vector scalarMultiple(Vector toMultiply, double scalar){
        return new Vector(toMultiply.a * scalar, toMultiply.b * scalar);
    }
    //public static Vector rectangularToPolar(Vector rectangular)
    //takes rectangular velocity vector and returns polar vector
    public static Vector velocityToPolar(Vector velocity){
        return new Vector(getAngle(new Vector(), velocity), getDistance(new Vector(), velocity));
    }
    //public static Vector polarToRectangular(Vector polar)
    //takes polar vector and returns rectangular velocity vector
    public static Vector polarToVelocity(Vector polar){
        return new Vector(Math.sin(Math.toRadians(polar.a)) * polar.b, Math.cos(Math.toRadians(polar.a)) * polar.b);
    }
    //public static double getDistance(Vector a, Vector b)
    public static double getDistance(Vector a, Vector b){
        return Math.sqrt(Math.pow(Math.abs(a.a - b.a), 2) + Math.pow(Math.abs(a.b - b.b), 2));
    }
    //public static double getAngle(Vector a, Vector b)
    public static double getAngle(Vector a, Vector b){
        double toRet = -1 * Math.toDegrees(Math.atan2(a.a - b.a, -1 * (a.b - b.b)));
        if(toRet < 0){
            toRet += 360;
        }
        return toRet;
    }
    //getters and setters

    public double getA() {
        return a;
    }
    public double getB() {
        return b;
    }
    public void setA(double a) {
        this.a = a;
    }
    public void setB(double b) {
        this.b = b;
    }
    public void setToThis(Vector toSet){
        this.a = toSet.a;
        this.b = toSet.b;
    }
    public void clear(){
        a = 0;
        b = 0;
    }

    @Override
    public String toString() {
        return "<"+(int)a+", "+(int)b+">";
    }
    public String superToString(){
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Vector)) return false;
        Vector vector = (Vector) o;
        return vector.a == a && vector.b == b;
    }

    //clone method with try/catch clonenotsupported
    //just return a vector with the same values no problem
    @Override
    public Vector clone(){
//        try {
//            return (Vector) super.clone();
//        }
//        catch(CloneNotSupportedException e){
//            e.printStackTrace();
//            System.exit(0);
//        }
        return new Vector(this.getA(), this.getB());
    }
}