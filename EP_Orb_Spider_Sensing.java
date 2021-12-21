public class EP_Orb_Spider_Sensing extends EP_Orb_Medium {

    private double maxDistance = 200;
    private double maxSizeMultiplier = 2;
    private double baseSize;

    public EP_Orb_Spider_Sensing(Vector initPos, Vector polar){
        super(initPos, polar, "blue");
        baseSize = size;
    }

    public EP_Orb_Spider_Sensing(Vector initPos, Vector polar, double md, double sm){
        super(initPos, polar, "blue");
        maxDistance = md;
        maxSizeMultiplier = sm;
        baseSize = size;
    }

    @Override
    protected void baseMovementBehavior(){
        double distance = Vector.getDistance(this, Player.getThePlayer());
        if(distance <= maxDistance){
            //linear scaling - exponential seems unfair also way harder

            //if equal then zero, if small then higher until 1 max
            double ratio = 1 - (distance/maxDistance);
            double sizeMultiplier = (maxSizeMultiplier - 1) * ratio;
            size = baseSize + (baseSize * sizeMultiplier);
        }
        super.baseMovementBehavior();
    }

    @Override
    protected void updateSprite(){
        this.sprite.setSize((this.size * 1.3)/50);
    }
}
