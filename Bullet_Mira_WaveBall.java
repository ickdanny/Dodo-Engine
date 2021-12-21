//5-7-9 DPS DOT
//explosion = 4xwave = 20dmg
public class Bullet_Mira_WaveBall extends Projectile_Player {
    public Bullet_Mira_WaveBall(Vector initPos, Vector polar, double size, double damage){
        super(initPos, polar, size);
        setTrueHitbox(new Hitbox_Circle(this));
        this.damage = damage/Driver_Game.getUPS();
    }

    @Override
    public void update(){
        if(!Player.getThePlayer().getFocused()) {
            this.exists = false;
            deathRoutine();
        }
        super.update();
    }

    @Override
    protected void deathRoutine(){
        toSpawn.add(new Bullet_Mira_Wave(this, new Vector(0, 2000), false));
        toSpawn.add(new Bullet_Mira_Wave(this, new Vector(90, 2000), false));
        toSpawn.add(new Bullet_Mira_Wave(this, new Vector(180, 2000), false));
        toSpawn.add(new Bullet_Mira_Wave(this, new Vector(270, 2000), false));
    }

    @Override
    public void collides(Entity e){
        //do nothing
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = this.size / 50;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("PP_waveBall", offset, rotation, size);
    }

    @Override
    protected void updateSprite(){
        this.sprite.setRotation(tick*10);
    }
}
