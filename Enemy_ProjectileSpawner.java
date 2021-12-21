public class Enemy_ProjectileSpawner extends Enemy {

    public Enemy_ProjectileSpawner(Vector initPos){
        super(initPos, 0);
        setTrueHitbox(new Hitbox_Circle(this));
        deathRoutine();
    }

    @Override
    protected void deathRoutine(){
//        toSpawn.add(new EP_BurningSpace(new Vector(500,500)));
//
//        toSpawn.add(new EP_Flame(new Vector(500, 500), new Vector(90,-100)));
//        toSpawn.add(new EP_Fragment(new Vector(500, 800), new Vector()));
//        toSpawn.add(new EP_Blade(Vector.add(this, new Vector(0, 100)), this, new Vector(90, 100)));
//        toSpawn.add(new EP_Sword(new Vector(425, 0)));
//        toSpawn.add(new EP_Rock(new Vector(425, 0), new Vector(0, 100)));
//        toSpawn.add(new EP_Pulsar(new Vector(425, 700), new Vector(90, 100)));
        toSpawn.add(new EP_IceShard(new Vector(425, 700), new Vector(45, 30)));

        String b = "blue";
        String g = "grey";
        String l = "lightBlue";
        String o = "orange";
        String r = "red";
        String v = "violet";
        String w = "white";
        String y = "yellow";

        Vector n = new Vector();

        toSpawn.add(new EP_Oval(new Vector(50, 50), n, b));
        toSpawn.add(new EP_Oval(new Vector(100, 50), n, g));
        toSpawn.add(new EP_Oval(new Vector(150, 50), n, l));
        toSpawn.add(new EP_Oval(new Vector(200, 50), n, o));
        toSpawn.add(new EP_Oval(new Vector(250, 50), n, r));
        toSpawn.add(new EP_Oval(new Vector(300, 50), n, v));
        toSpawn.add(new EP_Oval(new Vector(350, 50), n, w));
        toSpawn.add(new EP_Oval(new Vector(400, 50), n, y));

        toSpawn.add(new EP_Orb_Small(new Vector(50, 100), n, b));
        toSpawn.add(new EP_Orb_Small(new Vector(100, 100), n, g));
        toSpawn.add(new EP_Orb_Small(new Vector(150, 100), n, l));
        toSpawn.add(new EP_Orb_Small(new Vector(200, 100), n, o));
        toSpawn.add(new EP_Orb_Small(new Vector(250, 100), n, r));
        toSpawn.add(new EP_Orb_Small(new Vector(300, 100), n, v));
        toSpawn.add(new EP_Orb_Small(new Vector(350, 100), n, w));
        toSpawn.add(new EP_Orb_Small(new Vector(400, 100), n, y));

        toSpawn.add(new EP_Orb_Medium(new Vector(50, 200), n, b));
        toSpawn.add(new EP_Orb_Medium(new Vector(100, 200), n, g));
        toSpawn.add(new EP_Orb_Medium(new Vector(150, 200), n, l));
        toSpawn.add(new EP_Orb_Medium(new Vector(200, 200), n, o));
        toSpawn.add(new EP_Orb_Medium(new Vector(250, 200), n, r));
        toSpawn.add(new EP_Orb_Medium(new Vector(300, 200), n, v));
        toSpawn.add(new EP_Orb_Medium(new Vector(350, 200), n, w));
        toSpawn.add(new EP_Orb_Medium(new Vector(400, 200), n, y));

        toSpawn.add(new EP_Orb_Large(new Vector(50, 300), n, b));
        toSpawn.add(new EP_Orb_Large(new Vector(250, 300), n, g));
        toSpawn.add(new EP_Orb_Large(new Vector(450, 300), n, l));
        toSpawn.add(new EP_Orb_Large(new Vector(650, 300), n, o));
        toSpawn.add(new EP_Orb_Large(new Vector(50, 500), n, r));
        toSpawn.add(new EP_Orb_Large(new Vector(250, 500), n, v));
        toSpawn.add(new EP_Orb_Large(new Vector(450, 500), n, w));
        toSpawn.add(new EP_Orb_Large(new Vector(650, 500), n, y));
    }
}
