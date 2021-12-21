import java.awt.*;

public class EP_Laser_Machine extends Projectile_Enemy {

    private Enemy_Machine_Laser owner;
    private Vector ownerOffset;
    public EP_Laser_Machine(Vector initPos, Enemy_Machine_Laser owner){
        super(initPos, new Vector(), 1);
        setTrueHitbox(new Hitbox_Line(this, 0, Color.red.brighter()));
        this.owner = owner;
        //the vector that when added to owner gives the initial position
        //take the initial position and add to negative owner (subtract owner from initpos)
        this.ownerOffset = Vector.add(initPos, Vector.scalarMultiple(owner, -1));
    }

    @Override
    public void update(){
        if(tick > 200 || !owner.getExists()){
            this.exists = false;
            return;
        }

        if(size <= 1000){
            int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
            double growthRate = (1.5 + ((double)difficulty/5))/Driver_Game.getUPS();
            size += (size * growthRate);
        }

        Vector toGo = Vector.add(owner, ownerOffset);
        polar = new Vector(Vector.getAngle(this, toGo), Vector.getDistance(this, toGo) * Driver_Game.getUPS());
        velocity = Vector.polarToVelocity(polar);
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
