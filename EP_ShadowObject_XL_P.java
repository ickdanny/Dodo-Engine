public class EP_ShadowObject_XL_P extends Projectile_Enemy {

    public EP_ShadowObject_XL_P(Vector initPos, Vector center){
        super(initPos, 0);
        canBeDamaged = false;
        canDamage = false;
        canAttack = true;
        continuousAttack = true;
        attackDelay = 60;
        reloadDelay = 60;
        newPath(new Path_Orbit(center, new Vector(180, Vector.getDistance(this, center)), Vector.getAngle(center, this)));
        setTrueHitbox(new Hitbox_Circle(this));
    }

//    @Override
//    public void update(){
//
//        Vector av = ((Path_Orbit)this.path).getAVelocity();
//        double d = av.getB();
//        if(d > 0){
//            d -= 0.5;
//            av.setB(d);
//            if(d < 0){
//                stop();
//            }
//        }
//
//    }
    @Override
    public void update(){
        if(tick >= 7*60){
            this.exists = false;
            return;
        }
        super.update();
    }

    //shrinking radius
    @Override
    protected void updatePath(){
        Vector av = ((Path_Orbit)this.path).getAVelocity();
        double d = av.getB();
        if(d > 0){
            d -= 0.5;
            av.setB(d);
            if(d < 0){
                stop();
            }
        }
        super.updatePath();
    }

    //radial attack
    @Override
    protected void spawnAttack(){
        int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
        if(attackTimer % 12 - difficulty == 0) {
            double angle = Math.random() * 360;
            double speed = 400 + (difficulty * 10 * Math.random());
            toSpawn.add(new EP_Oval(this, new Vector(angle, speed), "orange"));
        }
    }
}
