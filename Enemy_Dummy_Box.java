public class Enemy_Dummy_Box extends Enemy_Dummy{
    public Enemy_Dummy_Box(Vector initPos){
        super(initPos);
        setTrueHitbox(new Hitbox_AABB(2,1,2,1,this));
    }
}
