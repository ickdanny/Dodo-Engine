import java.awt.*;

public class EP_Pulsar extends Projectile_Enemy {

    public EP_Pulsar(Vector initPos, Vector polar){
        super(initPos, polar, 30);
        setTrueHitbox(new Hitbox_Circle(this));
        initSprite();
    }

    @Override
    protected void initSprite(){
        double rotation = -polar.getA();
        double size = (this.size * 1.7)/50;
        Vector offset = new Vector(0, 0);
        this.sprite = new SpriteInfo("PE_pulsar1", offset, rotation, size);
        this.sprite.setNext(new SpriteInfo("PE_pulsar2", offset, rotation, size,
                new SpriteInfo("PE_pulsar3", offset, rotation, size,
                        new SpriteInfo("PE_pulsar4", offset, rotation, size, this.sprite))));
    }

    @Override
    protected void updateSprite(){
//        polar.setA(polar.getA() + 1);
        if(tick % 7 == 0)
            this.sprite = this.sprite.getNext();
        this.sprite.setRotation(-polar.getA());
    }

    @Override
    public void update(){
        spawnJet();
        super.update();
    }

    private void spawnJet(){
        if(tick % 5 != 0){
            return;
        }
        double baseAngle = polar.getA();
        if(tick % 10 == 0){
            baseAngle += 90;
        }
        else if(tick % 5 == 0){
            baseAngle -= 90;
        }
        baseAngle = baseAngle + (Math.random() * 60) - 30;
        double speed = 300 + (Math.random() * 200);
        double d = Math.random();
        if(d <= .2){
            toSpawn.add(new EP_Orb_Small(this, new Vector(baseAngle, speed), "white"));
        }
        else if(d <= .4){
            toSpawn.add(new EP_Laser(this, new Vector(baseAngle, speed), 30, Color.white));
        }
        else{
            toSpawn.add(new EP_Orb_Medium(this, new Vector(baseAngle, speed), "white"));
        }
    }
}
