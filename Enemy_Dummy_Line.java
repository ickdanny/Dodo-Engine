public class Enemy_Dummy_Line extends Enemy_Dummy {
    public Enemy_Dummy_Line(Vector initPos){
        super(initPos);
        setTrueHitbox(new Hitbox_Line(this, 45));
        size = 300;
    }
}
