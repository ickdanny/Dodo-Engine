import java.awt.*;

public class EP_Laser_Lilith_Default2 extends EP_Laser {

    private double nextAngle;
    private boolean overfill = false;
    public EP_Laser_Lilith_Default2(Vector initPos, double angle){
        super(initPos, new Vector(angle, 0), 1, Color.RED.brighter());
        nextAngle = angle + (360/10);
        if(nextAngle >= 360){
            nextAngle -= 360;
            overfill = true;
        }
    }

    @Override
    public void update(){
        if(tick >= 380){
            this.exists = false;
        }
        if(size <= 2000) {
            size += (750 / 60);
        }
        else{
            double angle = polar.getA();
            if(angle != nextAngle){
                angle += (double)6/60;
                if(angle > nextAngle && !overfill){
                    angle = nextAngle;
                }
                if(angle >= 360){
                    angle -= 360;
                    overfill = false;
                }
                polar.setA(angle);
            }
        }
        super.update();
    }
}
