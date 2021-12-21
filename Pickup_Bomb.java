public class Pickup_Bomb extends Pickup {

    public Pickup_Bomb(Vector initPos){
        super(initPos, 35);
    }

    @Override
    protected void initSprite(){
        //i have no idea why it's 22. But if it works don't fix it.
        this.sprite = new SpriteInfo("Pickup_bomb", new Vector(), 0, size/33);
    }
}
