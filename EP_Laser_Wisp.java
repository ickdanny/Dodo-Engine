import java.awt.*;

public class EP_Laser_Wisp extends Projectile_Enemy {

    private Enemy_Wisp owner;
    private double maxSize;
//    private double initAngle
//    private double finalAngle;
    private double angleChange;
    public EP_Laser_Wisp(Enemy_Wisp initPos, Vector initTarget, Vector finalTarget){
        super(initPos, new Vector(), 1);
        maxSize = Vector.getDistance(initPos, initTarget);
        setTrueHitbox(new Hitbox_Line(this, Vector.getAngle(this, initTarget), Color.white));
        double finalAngle = Vector.getAngle(this, finalTarget);
        double initAngle = Vector.getAngle(initPos, initTarget);
//        angleChange = (finalAngle - initAngle)/200;
//        System.out.println(angleChange);
//        angleChange = (initAngle - finalAngle)/200;
//        angleChange = (finalAngle - (initAngle + 360))/200;
//        angleChange = ((Math.max(finalAngle, initAngle)) - (Math.min(finalAngle, initAngle) + 360))/200;
        if(finalAngle > initAngle){
            angleChange = (finalAngle - (initAngle + 360))/200;
        }
        else if(finalAngle < initAngle){
            angleChange = ((finalAngle + 360) - initAngle)/200;
        }
        else{
            angleChange = 0;
        }
        owner = initPos;
    }

    @Override
    public void update(){
        if(tick > 200 || !owner.getExists()){
            this.exists = false;
            return;
        }

        if(size <= maxSize){
            int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
            double growthRate = (2 + ((double)difficulty/5))/Driver_Game.getUPS();
            size += (size * growthRate);
        }

        polar = new Vector(Vector.getAngle(this, owner), Vector.getDistance(this, owner) * Driver_Game.getUPS());
        velocity = Vector.polarToVelocity(polar);
        ((Hitbox_Line)trueHitbox).angle = ((Hitbox_Line)trueHitbox).angle + angleChange;
        super.update();
    }

    @Override
    protected void baseMovementBehavior(){
    }

    @Override
    public boolean canUpdate(){
        return owner.getHasUpdated();
    }
}
