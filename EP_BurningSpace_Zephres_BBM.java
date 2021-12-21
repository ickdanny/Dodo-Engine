public class EP_BurningSpace_Zephres_BBM extends EP_BurningSpace_Zephres_MD {

    public EP_BurningSpace_Zephres_BBM(Vector initPos){
        super(initPos);
    }

    @Override
    protected void explode(){
        double baseAngle = Math.random() * 360;
        for(int i = 0; i < 8; i++){
            double angle = baseAngle + (i * ((double)360/8));
            for(int j = 0; j < 3; j++) {
                toSpawn.add(new EP_Orb_Small(this, new Vector(angle, 100 + (100 * j)), "red"));
            }
        }
    }
}
