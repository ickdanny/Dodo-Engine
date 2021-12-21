public class EP_BurningSpace_Zephres_MD extends EP_BurningSpace {

    public EP_BurningSpace_Zephres_MD(Vector initPos){
        super(initPos);
    }

    @Override
    public void update(){
        super.update();
        if(tick % 20 == 0){
            if(pos < 6) {
                pos++;
                initSprite();
            }
            else if(pos < 7){
                pos++;
                initSprite();
                this.canDamage = true;
            }
        }
        if(tick > 3.5*Driver_Game.getUPS()) {
            explode();
            this.exists = false;
        }
    }

    protected void explode(){
        double baseAngle = Math.random() * 360;
        for(int i = 0; i < 8; i++){
            double angle = baseAngle + (i * ((double)360/8));
            toSpawn.add(new EP_Orb_Small(this, new Vector(angle, 300), "red"));
        }
    }
}