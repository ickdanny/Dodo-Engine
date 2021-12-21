public class EP_Oval_Zephres_Gravity extends EP_Oval {

    public EP_Oval_Zephres_Gravity(Vector initPos, Vector initPolar){
        super(initPos, initPolar, "red");
        destroyOutsideBounds = false;
    }

    @Override
    protected void baseMovementBehavior(){
        //y coord
        Vector currentVelocity = Vector.polarToVelocity(polar);
        double breakYV = 600;
        double currentYV = currentVelocity.getB();
        double diff = breakYV - currentYV;
        double add = diff/240;
        double setYV = currentYV + add;

        //x coord
        double currentXV = currentVelocity.getA();
        double setXV = currentXV * .98;
        Vector nextVelocity = new Vector(setXV, setYV);
        Vector setPolar = Vector.velocityToPolar(nextVelocity);
        polar = setPolar;
        super.baseMovementBehavior();
    }
}
