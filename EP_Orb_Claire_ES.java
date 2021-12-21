public class EP_Orb_Claire_ES extends EP_Orb_Medium {
    public EP_Orb_Claire_ES(Vector initPos, Vector polar){
        super(initPos, polar, "violet");
        destroyOutsideBounds = false;
    }

    @Override
    protected void baseMovementBehavior(){
        super.baseMovementBehavior();
        //diff = diameter
        double diff = size * 2;
        if(a < 0 - diff){
            a += 850 + (2 * diff);
        }
        else if(a > 850 + diff){
            a -= 850 + (2 * diff);
        }
        if(b < 0 - diff){
            b += 1000 + (2 * diff);
        }
        else if(b > 1000 + diff){
            b -= 1000 + (2 * diff);
        }
    }
}
