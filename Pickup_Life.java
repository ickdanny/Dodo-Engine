public class Pickup_Life extends Pickup {

    public Pickup_Life(Vector initPos){
        super(initPos, 35);
    }

    @Override
    protected void initSprite(){
        //i have no idea why it's 22. But if it works don't fix it.
        this.sprite = new SpriteInfo("Pickup_life", new Vector(), 0, size/33);
    }
}
