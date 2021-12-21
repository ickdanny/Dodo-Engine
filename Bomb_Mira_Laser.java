import java.awt.*;

public class Bomb_Mira_Laser extends Projectile_Bomb {
    public Bomb_Mira_Laser(Vector initPos, double angle){
        super(initPos);
        damage = .2;
        Color color;
        double number = Math.random();
        if(number < .333){
            color = Color.blue;
        }
        else if(number < .666){
            color = Color.green;
        }
        else{
            color = Color.red;
        }
        setTrueHitbox(new Hitbox_Line(this, angle, color));
        size = 1500;
        destroyOutsideBounds = false;
    }

    @Override
    public void update(){
        if(this.tick > 20){
            this.exists = false;
        }
        super.update();
    }
}