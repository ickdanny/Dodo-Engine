public class EP_ShadowObject_XL_Slash extends Projectile_Enemy implements EP_Unremovable{

    Vector offset;
    Enemy_Boss_XiaoLi owner;
    int lifespan;
    public EP_ShadowObject_XL_Slash(Vector offset, Enemy_Boss_XiaoLi owner, int lifespan){
        super(Vector.add(offset, owner), 20);
        this.offset = offset;
        this.owner = owner;
        this.lifespan = lifespan;
        this.setTrueHitbox(new Hitbox_Circle(this));
        canBeDamaged = false;
        destroyOutsideBounds = false;
    }

    @Override
    public void update(){
        if(tick > lifespan){
            this.exists = false;
            return;
        }
        super.update();
    }

    @Override
    public boolean canUpdate(){
        return owner.hasUpdated;
    }

//    @Override
//    protected void move(){
//        //the owner's current position and future position are locked in, go to future position
//        Vector ownerCurrentPos = owner.clone();
//        Vector ownerPolar = owner.getPolarVector().clone();
//        ownerPolar.setB(ownerPolar.getB() / 60);
//        Vector add = Vector.polarToVelocity(ownerPolar);
//        Vector futurePos = Vector.add(ownerCurrentPos, add);
//        futurePos.addToThis(offset);
//        Vector ourPolar = new Vector(Vector.getAngle(this, futurePos), Vector.getDistance(this, futurePos));
//        ourPolar.setB(ourPolar.getB() * Driver_Game.getUPS());
//        polar = ourPolar;
//        System.out.println(polar);
//        super.move();
//    }

    @Override
    protected void baseMovementBehavior(){
        Vector ownerCurrentPos = owner.clone();
        Vector ownerPolar = owner.getPolarVector().clone();
        ownerPolar.setB(ownerPolar.getB() / 60);
        Vector add = Vector.polarToVelocity(ownerPolar);
        Vector futurePos = Vector.add(ownerCurrentPos, add);
        futurePos.addToThis(offset);
        Vector ourPolar = new Vector(Vector.getAngle(this, futurePos), Vector.getDistance(this, futurePos));
        ourPolar.setB(ourPolar.getB() * Driver_Game.getUPS());
        polar = ourPolar;
        super.baseMovementBehavior();
    }
}
