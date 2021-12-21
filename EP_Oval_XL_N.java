public class EP_Oval_XL_N extends EP_Oval {
    private int t;
    private double speed;
    private Vector target;
    private Enemy_Boss_XiaoLi owner;
    //0 = orbit
    //1 = orient
    //2 = final
    private int phase = 0;
    //true = counterclockwise, false = clockwise
    private boolean direction;
    public EP_Oval_XL_N(Enemy_Boss_XiaoLi owner, Path_Orbit path, double speed, int t){
        super(owner, new Vector(), "orange");
        newPath(path);
        this.owner = owner;
        this.t = t;
        this.speed = speed;
        destroyOutsideBounds = false;
    }

    @Override
    public void update(){
        if(tick == t){
            phase = 1;
            followsPath = false;
            destroyOutsideBounds = true;
            polar.setB(0);
//            target = Player.getThePlayer().clone();
            target = Player.getThePlayer();
            double a = Vector.getAngle(this, target);
            double ad = polar.getA() - a;
            if(ad > 0){
                if(ad > 180){
                    direction = true;
                }
                else{
                    direction = false;
                }
            }
            else{
                if(ad < -180){
                    direction = false;
                }
                else{
                    direction = true;
                }
            }
            polar.setA(Vector.getAngle(owner, this));
        }
        if(phase == 1){
            if(Math.abs(Vector.getAngle(this, target) - polar.getA()) <= 1.1){
                polar.setA(Vector.getAngle(this, target));
                polar.setB(speed);
                phase = 2;
            }
            else{
                if(direction){
                    polar.setA(polar.getA() + 2);
                }
                else{
                    polar.setA(polar.getA() - 2);
                }
            }
        }
        super.update();
    }

    @Override
    protected void updateSprite(){
        if(phase == 0) {
            this.sprite.setRotation(-(Vector.getAngle(owner, this)) /*+ 180*/);
        }
        else{
            this.sprite.setRotation(-polar.getA() + 180);
        }
    }
}
