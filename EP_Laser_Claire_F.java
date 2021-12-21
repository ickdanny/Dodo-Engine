import java.awt.*;

public class EP_Laser_Claire_F extends EP_Laser{
    public EP_Laser_Claire_F(Vector initPos, double angle){
        super(initPos, new Vector(angle, 0), 1, Color.WHITE);
    }

    @Override
    public void update(){
        if(tick >= 780){
            this.exists = false;
        }
        if(size <= 2000) {
            size += (750 / 60);
        }
        super.update();
    }
}
