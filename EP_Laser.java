import java.awt.*;

public class EP_Laser extends Projectile_Enemy {

    public EP_Laser(Vector initPos, Vector polar, double size, Color color){
        super(initPos, polar, size);
        setTrueHitbox(new Hitbox_Line(this, polar.getA(), color));
    }

    @Override
    public void update(){
        ((Hitbox_Line)trueHitbox).angle = polar.getA();
        super.update();
    }
}
