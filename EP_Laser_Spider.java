import java.awt.*;

public class EP_Laser_Spider extends EP_Laser {

    public EP_Laser_Spider(Vector initPos, Vector polar, double angle){
        super(initPos, polar, 50, Color.magenta);
        ((Hitbox_Line)trueHitbox).angle = angle;
    }

    @Override
    public void update(){
        move();
        spawn();
        tick++;
        updateSprite();
        hasUpdated = true;
    }
}
