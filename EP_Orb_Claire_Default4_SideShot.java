public class EP_Orb_Claire_Default4_SideShot extends EP_Orb_Small {
    private double horizontalMultiplier = 1.0;
    boolean falling = false;
    public EP_Orb_Claire_Default4_SideShot(Vector initPos, Vector polar){
        super(initPos, polar, "violet");
    }

    //bounces off sides
    //kills if bottom
    //horizontalMulitplier approaches 0
    //as it reaches zero, it falls down
    //does not kill EVER top side
    @Override
    protected void baseMovementBehavior(){
        if(falling){
            if(polar.getB() <= 500){
                polar.setB(polar.getB() + 10);
            }
        }

        velocity = Vector.polarToVelocity(polar);

        if(!falling) {
            velocity.setA(velocity.getA() * horizontalMultiplier);
            if (horizontalMultiplier >= 0) {
                horizontalMultiplier -= 0.007;
            } else if (horizontalMultiplier < 0) {
                horizontalMultiplier = 0;
                falling = true;
                polar = new Vector(0, 0);
            }
            Vector nextPos = Vector.add(this, Vector.scalarMultiple(velocity, ((double)1 / Driver_Game.getUPS())));
            if(nextPos.getA() < 0 || nextPos.getA() > 850){
                polar.setA(0 - polar.getA());
            }
        }


        if(b > Driver_Game.getHeight() + (size * 2)){
            this.exists = false;
        }


    }
}
