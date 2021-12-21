public class Enemy_Boss_Pax extends Enemy_Boss {

    public Enemy_Boss_Pax(){
        super(new Instruction_DriverEndLevel(null, null));
//        initSprite();
    }

    @Override
    protected void initSprite(){
        double rotation = 0;
        double size = 3;
        Vector offset = new Vector(2, 0);
        idle = new SpriteInfo("Boss_Pax_idle1", offset, rotation, size);
        idle.setNext(new SpriteInfo("Boss_Pax_idle2", offset, rotation, size, idle));

        this.sprite = idle;
    }

    @Override
    protected void updateSprite(){
        if(tick % 30 == 0)
            this.sprite = this.sprite.getNext();
    }
}
