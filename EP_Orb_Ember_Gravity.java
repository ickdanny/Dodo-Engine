public class EP_Orb_Ember_Gravity extends EP_Orb_Medium {

    public EP_Orb_Ember_Gravity(Vector initPos, Vector initPolar){
        super(initPos, initPolar, "yellow");
    }

    @Override
    protected void baseMovementBehavior(){
        //y coord
        Vector currentVelocity = Vector.polarToVelocity(polar);
        double breakYV = 500;
        double currentYV = currentVelocity.getB();
        double diff = breakYV - currentYV;
        //i believe this should work, perhaps need to subtract instead
        double add = diff/120;
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
