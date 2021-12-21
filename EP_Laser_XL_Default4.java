import java.awt.*;

public class EP_Laser_XL_Default4 extends EP_Laser {
    public EP_Laser_XL_Default4(Vector initPos, double angle){
        super(initPos, new Vector(angle, 0), 0, Color.white);
    }

    @Override
    public void update(){
        if(tick <= 15){
            this.size += 5;
        }
        else if(tick == 16){
            this.polar.setB(60 * 5);
        }
        super.update();
    }
}
