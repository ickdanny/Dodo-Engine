//~12 dps
public class Bullet_Aba_Halo_Homing extends Projectile_Player {

    private Enemy target;
    private double angleCorrectionSpeed = 10;
    private double maxAngleCorrectionSpeed = 50;
    private boolean clockwise;
    public Bullet_Aba_Halo_Homing(Vector initPos, Vector initPolar, Enemy target){
        super(initPos, initPolar, 15);
        this.damage = 0.265;
        this.target = target;
        setTrueHitbox(new Hitbox_Circle(this));
        destroyOutsideBounds = false;
        if(this.target != null) {
            double idealAngle = Vector.getAngle(this, this.target);
            if (idealAngle != polar.getA()) {
                clockwise = idealAngle < polar.getA();
            }
        }
    }

    @Override
    public void update(){
        if(this.tick > 300)
            this.exists = false;
        super.update();
    }

    @Override
    protected void baseMovementBehavior(){
        if(target != null && target.getExists()) {
            double idealAngle = Vector.getAngle(this, target);
            if(idealAngle < 0){
                idealAngle += 360;
            }
            if (idealAngle != polar.getA()) {
                if (!clockwise) {
                    polar.addToThis(new Vector(angleCorrectionSpeed, 0));
                    if(idealAngle < polar.getA()){
                        polar.setA(idealAngle);
                    }
                }
                else {
                    polar.addToThis(new Vector(angleCorrectionSpeed * -1, 0));
                    if(idealAngle > polar.getA()){
                        polar.setA(idealAngle);
                    }
                }
            }
        }
        if(angleCorrectionSpeed <= maxAngleCorrectionSpeed) {
            angleCorrectionSpeed *= 1.05;
        }
        super.baseMovementBehavior();
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = this.size/50;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("PP_orb_yellow", offset, rotation, size);
    }
}