import java.awt.*;

public class EP_Laser_Ember extends EP_Laser {

    private Enemy_Ember owner;
    private boolean left;
    public EP_Laser_Ember(Enemy_Ember owner, boolean left){
        super(owner, new Vector(), 1, Color.ORANGE.brighter());
        double angle;
        if(left){
            angle = 270;
        }
        else{
            angle = 90;
        }
        polar.setA(angle);
        this.left = left;
        this.owner = owner;
    }

    @Override
    public void update(){
        if(!owner.getExists()){
            this.exists = false;
            return;
        }
        super.update();
    }

    @Override
    public void baseMovementBehavior(){
        if(tick > 300){
            this.exists = false;
            return;
        }
        if(size < 2000){
            size *= 1.05;
        }
        else {
            if (polar.getA() != 0) {
                if (left) {
                    polar.setA(polar.getA() + 1);
                    if(polar.getA() >= 360){
                        polar.setA(0);
                    }
                }
                else{
                    polar.setA(polar.getA() - 1);
                    if(polar.getA() <= 0){
                        polar.setA(0);
                    }
                }
            }
        }
        super.baseMovementBehavior();
    }
}
