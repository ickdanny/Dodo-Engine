import java.awt.*;

public class EP_Laser_Claire_C extends EP_Laser {
    private double finalAngle;
    private boolean side;
    public EP_Laser_Claire_C(Vector initPos, double initAngle, double finalAngle, boolean side){
        super(initPos, new Vector(initAngle, 0), 1, Color.WHITE);
        this.finalAngle = finalAngle;
        if(this.finalAngle < 0){
            this.finalAngle += 360;
        }
        else if(this.finalAngle >= 360){
            this.finalAngle -= 360;
        }
        this.side = side;
    }

    @Override
    public void update(){
        if(tick < 30){
            this.size += 50;
//            System.out.println(size);
        }
        if(tick > 60 && Math.abs(polar.getA() - finalAngle) > 30){
            if(side){
                polar.setA(polar.getA() + 1);
                if(polar.getA() < 0){
                    polar.setA(polar.getA() + 360);
                }
                else if(polar.getA() >= 360){
                    polar.setA(polar.getA() - 360);
                }
            }
            else{
                polar.setA(polar.getA() - 1);
                if(polar.getA() < 0){
                    polar.setA(polar.getA() + 360);
                }
                else if(polar.getA() >= 360){
                    polar.setA(polar.getA() - 360);
                }
            }
            ((Hitbox_Line)trueHitbox).angle = polar.getA();
        }
        if(tick > 300){
            this.exists = false;
        }
        super.update();
    }
}
