public abstract class Pickup_PowerUp extends Pickup {

    protected int power;
    public Pickup_PowerUp(Vector initPos, double size, int power){
        super(initPos, size);
        this.power = power;
    }

    public int getPower(){
        return power;
    }

    @Override
    protected void initSprite(){
        //i have no idea why it's 22. But if it works don't fix it.
        this.sprite = new SpriteInfo("Pickup_power", new Vector(), 0, size/33);
    }
}
