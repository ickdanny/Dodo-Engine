import java.awt.*;

//12 dps
//too broken, nerfed to 8 dps
public class Bullet_Azr_Laser extends Projectile_Player {

    private Vector target = new Vector();
    private Vector relationToPlayer;

    public Bullet_Azr_Laser(Vector initPos, Vector relationToPlayer) {
        super(initPos, new Vector(), 1300);
        setTrueHitbox(new Hitbox_Line(this, Color.getHSBColor((float)50/400, (float)77/100, (float)76/100)));
        canBeDamaged = false;
        damage =  ((double)1) / Driver_Game.getUPS();
        destroyOutsideBounds = false;
        this.relationToPlayer = relationToPlayer;
    }

    //called by the player
    public void setTarget(Vector target) {
        this.target = target;
    }

    //interestingly this doesn't call the super update.
    //perhaps the polar is just for deltatime?
    //WE SHOULD UPDATE THIS
    @Override
    public void update() {
//        polar = Player.getThePlayer().getPolarVector();
//        setToThis(Vector.add(Player.getThePlayer(), relationToPlayer));
        Vector toGo = Vector.add(Player.getThePlayer(), relationToPlayer);
        //entity move divides velocity by updates per second
        polar = new Vector(Vector.getAngle(this, toGo), Vector.getDistance(this, toGo) * Driver_Game.getUPS());
        velocity = Vector.polarToVelocity(polar);
        ((Hitbox_Line) trueHitbox).angle = Vector.getAngle(this, target);
//        velocity = Player.getThePlayer().getVelocityVector();
        super.update();
    }

    //DO NOT USE POLAR, IT WILL BE SCREWED UP WITH THE PLAYERS AND WHATNOT
    @Override
    protected void baseMovementBehavior(){
    }

    @Override
    public boolean canUpdate() {
        return Player.getThePlayer().getHasUpdated();
    }
}