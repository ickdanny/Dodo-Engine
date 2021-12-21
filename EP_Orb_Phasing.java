public class EP_Orb_Phasing extends EP_Orb_Small {
    private Vector storedPolar;
    private boolean phased = false;
    public EP_Orb_Phasing(Vector initPos, Vector polar){
        super(initPos, polar, "red");
        storedPolar = polar;
    }

    @Override
    public void update(){
        super.update();
        if(tick % 60 == 0){
            changePhase();
        }
    }

    private void changePhase(){
        //phase out
        if(!phased){
//            canBeDamaged = false;
//            canDamage = false;
//            size = 0;
            phased = true;
            polar = new Vector();
        }
        //phase in
        else{
//            canBeDamaged = true;
//            canDamage = true;
//            size = 6;
            phased = false;
            polar = storedPolar;
        }
    }
}
