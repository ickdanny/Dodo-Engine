public class EP_ShadowObject_XL_Thrust extends Projectile_Enemy implements EP_Unremovable{

    Vector offset;
    Enemy_Boss_XiaoLi owner;
    public EP_ShadowObject_XL_Thrust(Vector offset, Enemy_Boss_XiaoLi owner){
        super(Vector.add(offset, owner), 20);
        this.offset = offset;
        this.owner = owner;
        this.setTrueHitbox(new Hitbox_Circle(this));
        canBeDamaged = false;
        destroyOutsideBounds = false;
    }

    @Override
    public boolean canUpdate(){
        return owner.hasUpdated;
    }
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

//    @Override
//    protected void move(){
//        //the owner's current position and future position are locked in, go to future position
////        System.out.println(this);
//        Vector ownerCurrentPos = owner.clone();
//        Vector ownerPolar = owner.getPolarVector().clone();
//        ownerPolar.setB(ownerPolar.getB() / 60);
//        Vector add = Vector.polarToVelocity(ownerPolar);
//        Vector futurePos = Vector.add(ownerCurrentPos, add);
//        futurePos.addToThis(offset);
//        Vector ourPolar = new Vector(Vector.getAngle(this, futurePos), Vector.getDistance(this, futurePos));
////        System.out.println(ourPolar);
//        ourPolar.setB(ourPolar.getB() * Driver_Game.getUPS());
////        System.out.println(ourPolar);
//        polar = ourPolar;
//        super.move();
////        System.out.println(polar + " - " + velocity);
//    }
}
