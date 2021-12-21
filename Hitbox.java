//hitbox interface
public interface Hitbox {
    //public abstract boolean collidesWith(hitbox a)
    //check the type of hitbox, and do some algorithm.

    boolean collidesWith(Hitbox a);
    Hitbox_AABB getBoundingAABB();
    void setOwner(Entity owner);
    Entity getOwner();
}