//enemy spawner class

import java.lang.reflect.Array;
import java.util.ArrayList;

//static ArrayList<Enemy> getWave(int tick)
//get the enemies that will spawn at this tick.
public class EnemySpawner{

    //this will eventually need to switch on the level, but i'll do that later (never)
    public static ArrayList<Enemy> getWave(String stageName, int tick){
        //spawn some shit
        ArrayList<Enemy> toRet = new ArrayList<>();
//        switch(tick){
//            case 0:
//                toRet.add(new Enemy_Boss_XiaoLi());
////                toRet.add(new Enemy_ProjectileSpawner(new Vector(Driver_Game.getWidth()/2, Driver_Game.getHeight()/2)));
//
//                //wisp
////                toRet.add(new Enemy_Wisp(new Vector(Driver_Game.getWidth()/2, Driver_Game.getHeight()/2), new Vector(0, 0)));
////                toRet.add(new Enemy_Wisp(new Vector(Driver_Game.getWidth()/2, 0), new Vector(28, 120)));
//
//                //machines
////                toRet.add(new Enemy_Machine_Bullet(new Vector(Driver_Game.getWidth()/2, Driver_Game.getHeight()/2), new Vector(0, 0)));
////                toRet.add(new Enemy_Machine_Laser(new Vector(Driver_Game.getWidth()/2 + 100, Driver_Game.getHeight()/2), new Vector(0, 0)));
////                toRet.add(new Enemy_Machine_Shield(new Vector(Driver_Game.getWidth()/2 + 200, Driver_Game.getHeight()/2), new Vector(0, 0)));
////                toRet.add(new Enemy_Machine_Spray(new Vector(Driver_Game.getWidth()/2 - 100, Driver_Game.getHeight()/2), new Vector(0, 0)));
//
//                //spider
////                toRet.add(new Enemy_Spider(new Vector(Driver_Game.getWidth()/2, Driver_Game.getHeight()/2), new Vector(0, 0)));
//
//                //ember
////                toRet.add(new Enemy_Ember(new Vector(Driver_Game.getWidth()/2, Driver_Game.getHeight()/2), new Vector(0, 0)));
//
//
//
//                //4 circles
////                toRet.add(new Enemy_Dummy(new Vector(Driver_Game.getWidth()/2, Driver_Game.getHeight()/2)));
////                toRet.add(new Enemy_Dummy(new Vector(Driver_Game.getWidth()/5, Driver_Game.getHeight()/4)));
////                toRet.add(new Enemy_Dummy(new Vector(Driver_Game.getWidth()/4, Driver_Game.getHeight()/2)));
////                toRet.add(new Enemy_Dummy(new Vector(Driver_Game.getWidth()/2.5, Driver_Game.getHeight()/4)));
//
//                //line
////                toRet.add(new Enemy_Dummy_Line(new Vector(Driver_Game.getWidth()/2, Driver_Game.getHeight()/2)));
//
//                //box
////                toRet.add(new Enemy_Dummy_Box(new Vector(Driver_Game.getWidth()/2, Driver_Game.getHeight()/2)));
//                break;
//        }
        switch(stageName){
            case "Stage1":
                addWave1(tick, toRet);
                break;
            case "Stage2":
                addWave2(tick, toRet);
                break;
            case "Stage3":
                addWave3(tick, toRet);
                break;
            case "Stage4":
                addWave4(tick, toRet);
                break;
            case "Stage5":
                addWave5(tick, toRet);
                break;
            case "Stage6":
                addWave6(tick, toRet);
                break;
            case "StageEX":
                addWaveEX(tick, toRet);
                break;
        }
        return toRet;
    }

    private static void addWave1(int tick, ArrayList<Enemy> addTo){
//        switch(tick){
//            case 0:
//                addTo.add(new Enemy_Boss_Mira());
//                break;
//        }

        //BOSS
//        System.out.println("addWave1 " + tick);
//        if(tick == 0){
//            addTo.add(new Enemy_Wisp(new Vector(Driver_Game.getWidth()/2, -40), new Vector(45, 100), "spiral", 3));
//        }
        if(tick == 10000){
            addTo.add(new Enemy_Boss_Mira());
            return;
        }

        //wave 0 - random crossing default wisps
        if(tick >= 180 && tick <= 4600){
//            System.out.println("reached");
            //left side
            if((tick - 120) % 240 == 0){
//                System.out.println("left");
                double initX = -40;
                double initY = randomNum(tick, 50, 400);
                double angle = Vector.getAngle(new Vector(initX, initY), new Vector(890, randomNum(tick*2, 50, 600)));
                addTo.add(new Enemy_Wisp(new Vector(initX, initY), new Vector(angle, 250)));
            }
            //right side
            else if((tick) % 240 == 0){
//                System.out.println("right");
                double initX = 890;
                double initY = randomNum(tick, 50, 400);
                double angle = Vector.getAngle(new Vector(initX, initY), new Vector(-40, randomNum(tick*2, 50, 600)));
                addTo.add(new Enemy_Wisp(new Vector(initX, initY), new Vector(angle, 250)));
            }
        }

        //wave 1 - non-staggered spread
//        if(tick >= 1200 && tick <= 2400){
        if(tick == 1200 || tick == 1800 || tick == 2400){
                for (int initX = 107; initX <= 850; initX += 107) {
                    addTo.add(new Enemy_Wisp(new Vector(initX, -40),
                            new Routine_SingleAttack(null, new Vector(initX, 200), new Vector(initX, -100),
                                    200, 200, "spread", 200, 200), 2));
                }
        }

        //wave 2 - random back
        if(tick >= 1800 && tick <= 2400){
            if(tick % 30 == 0) {
                double initX = randomNum(tick, 50, 800);
                addTo.add(new Enemy_Wisp(new Vector(initX, -40), new Vector(0, 250), "backShot"));
            }
        }

        //wave 3 - randmom side
        if(tick >= 2200 && tick <= 2800){
            if(tick % 30 == 0) {
                double initX = randomNum(tick, 50, 800);
                addTo.add(new Enemy_Wisp(new Vector(initX, -40), new Vector(0, 250), "sideShot"));
            }
        }

        //wave 4 - random back
        if(tick >= 3000 && tick <= 3600){
            if(tick % 30 == 0) {
                double initX = randomNum(tick, 50, 800);
                addTo.add(new Enemy_Wisp(new Vector(initX, -40), new Vector(0, 250), "backShot"));
            }
        }

        //wave 5 - non-staggered advanced
        if(tick == 4000 || tick == 4300 || tick == 4600){
            for (int initX = 284; initX <= 850; initX += 284) {
                addTo.add(new Enemy_Wisp(new Vector(initX, -40),
                        new Routine_SingleAttack(null, new Vector(initX, 200), new Vector(initX, -100),
                                200, 200, "advanced", 200, 200), 2));
            }
        }

        //wave 6 - 2 lasers
        if(tick == 4600){
            addTo.add(new Enemy_Wisp(new Vector(150, -40), new Vector(0, 100), "lasers", 3));
            addTo.add(new Enemy_Wisp(new Vector(700, -40), new Vector(0, 100), "lasers", 3));
        }

        //wave 7 - travelling spirals
        if(tick >= 5000 && tick <= 6000){
            if(tick % 400 == 0){
                addTo.add(new Enemy_Wisp(new Vector(Driver_Game.getWidth()/2, -40), new Vector(45, 100), "spiral", 3));
            }
            else if(((tick + 200) % 400) == 0){
                addTo.add(new Enemy_Wisp(new Vector(Driver_Game.getWidth()/2, -40), new Vector(-45, 100), "spiral", 3));
            }
        }

        //wave 8 - everything at once
        //lasers
        if(tick == 7000){
            addTo.add(new Enemy_Wisp(new Vector(150, -40), new Vector(0, 100), "lasers", 5));
            addTo.add(new Enemy_Wisp(new Vector(700, -40), new Vector(0, 100), "lasers", 5));
        }
        if(tick >= 7000 && tick <= 8200){
            //back
            if(tick % 60 == 0) {
                double initX = randomNum(tick, 50, 800);
                addTo.add(new Enemy_Wisp(new Vector(initX, -40), new Vector(0, 250), "backShot"));
            }
            if(tick % 200 == 0){
                addTo.add(new Enemy_Wisp(new Vector(150, -40), new Vector(0, 250), "sideShot"));
            }
            //side
            else if((tick + 100)% 200 == 0){
                addTo.add(new Enemy_Wisp(new Vector(150, -40), new Vector(0, 250), "sideShot"));
            }
        }
        if(tick >= 7600 && tick <= 8200){
            //advanced
            double initX = randomNum(tick, 50, 800);
            if(tick % 300 == 0){
                addTo.add(new Enemy_Wisp(new Vector(initX, -40),
                        new Routine_SingleAttack(null, new Vector(initX, 200), new Vector(initX, -100),
                                200, 200, "advanced", 200, 200), 6));
            }
            else if((tick + 100) % 300 == 0){
                addTo.add(new Enemy_Wisp(new Vector(initX, -40),
                        new Routine_SingleAttack(null, new Vector(initX, 200), new Vector(initX, -100),
                                200, 200, "spread", 200, 200), 6));
            }
            else if((tick + 200) % 300 == 0){
                addTo.add(new Enemy_Wisp(new Vector(initX, -40),
                        new Routine_SingleAttack(null, new Vector(initX, 200), new Vector(initX, -100),
                                200, 200, "spiral", 200, 200), 6));
            }
        }

        //final wave
        if(tick == 9000){
            addTo.add(new Enemy_Wisp(new Vector(Driver_Game.getWidth()/2, -40), new Vector(25, 100), "spiral", 30));
            addTo.add(new Enemy_Wisp(new Vector(Driver_Game.getWidth()/2, -40), new Vector(-25, 100), "spiral", 30));
        }
    }
    private static void addWave2(int tick, ArrayList<Enemy> addTo){
//        if(tick == 0){
//            addTo.add(new Enemy_Boss_Neo());
//        }
//        switch(tick){
//            case 0:
//                addTo.add(new Enemy_Boss_Neo());
//                break;
//        }
        if(tick == 8000){
            addTo.add(new Enemy_Boss_Neo());
        }
//        if(tick == 1){
//            addTo.add(new Enemy_Machine_Bullet(new Vector(100, 100), new Vector()));
//            addTo.add(new Enemy_Machine_Bullet(new Vector(300, 100), new Vector(), "shotgun"));
//            addTo.add(new Enemy_Machine_Spray(new Vector(500, 100), new Vector()));
//            addTo.add(new Enemy_Machine_Spray(new Vector(700, 100), new Vector(), "spray"));
//            addTo.add(new Enemy_Machine_Spray(new Vector(100, 400), new Vector(), "smart"));
//            addTo.add(new Enemy_Machine_Laser(new Vector(300, 400), new Vector(), "sustain"));
//            addTo.add(new Enemy_Machine_Laser(new Vector(500, 400), new Vector()));
//            addTo.add(new Enemy_Machine_Shield(new Vector(700, 400), new Vector(0, 30), 100));
//        }

        //wave 1
        if(tick >= 180 && tick < 780){
            if(tick % 120 == 0){
                double initY = -40;
                double initX = randomNum(tick, 0, 850);
                double finalY = 1000;
                double finalX = randomNum(tick * 2, 0, 850);
                double angle = Vector.getAngle(new Vector(initX, initY), new Vector(finalX, finalY));
                addTo.add(new Enemy_Wisp(new Vector(initX, initY), new Vector(angle, 200), "spread", 4));
            }
            else if((tick + 60) % 120 == 0){
                double initY = -40;
                double initX = randomNum(tick, 0, 850);
                double finalY = 1000;
                double finalX = randomNum(tick * 2, 0, 850);
                double angle = Vector.getAngle(new Vector(initX, initY), new Vector(finalX, finalY));
                addTo.add(new Enemy_Wisp(new Vector(initX, initY), new Vector(angle, 200), "default", 3));
            }
        }

        //wave 2
        if(tick >= 1000 && tick <= 1800) {
            if (tick == 1000) {
                double initX = Driver_Game.getWidth() / 2;
                addTo.add(new Enemy_Machine_Bullet(new Vector(initX, -40),
                        new Routine_SingleAttack(null, new Vector(initX, 200), new Vector(initX, -100),
                                200, 200, "default", 1, 200), 10));
            }

            else if (tick == 1200) {
                double initX = Driver_Game.getWidth() / 2;
                addTo.add(new Enemy_Machine_Bullet(new Vector(initX, -40),
                        new Routine_SingleAttack(null, new Vector(initX, 200), new Vector(initX, -100),
                                200, 200, "shotgun", 1, 200), 10));
            }

            else if (tick == 1400) {
                double pos1 = Driver_Game.getWidth() / 5;
                double pos2 = (Driver_Game.getWidth() / 5) * 2;
                double pos3 = (Driver_Game.getWidth() / 5) * 3;
                double pos4 = (Driver_Game.getWidth() / 5) * 4;
                addTo.add(new Enemy_Machine_Laser(new Vector(pos1, -40),
                        new Routine_SingleAttack(null, new Vector(pos1, 200), new Vector(pos1, -100),
                                200, 200, "default", 200, 200), 10));

                addTo.add(new Enemy_Machine_Laser(new Vector(pos2, -40),
                        new Routine_SingleAttack(null, new Vector(pos2, 200), new Vector(pos2, -100),
                                200, 200, "default", 200, 200), 10));

                addTo.add(new Enemy_Machine_Laser(new Vector(pos3, -40),
                        new Routine_SingleAttack(null, new Vector(pos3, 200), new Vector(pos3, -100),
                                200, 200, "default", 200, 200), 10));

                addTo.add(new Enemy_Machine_Laser(new Vector(pos4, -40),
                        new Routine_SingleAttack(null, new Vector(pos4, 200), new Vector(pos4, -100),
                                200, 200, "default", 200, 200), 10));
            }

            else if(tick == 1600){
                double initX = Driver_Game.getWidth() / 7;
                double initY = -40;
                for(int i = 1; i <= 6; i++){
                    addTo.add(new Enemy_Machine_Spray(new Vector(initX * i, initY), new Vector(0, 200), "smart", 5));
                }
            }

            else if(tick == 1800){
                double initX = Driver_Game.getWidth() / 7;
                double initY = -40;
                for(int i = 0; i <= 7; i++){
                    addTo.add(new Enemy_Machine_Shield(new Vector(initX * i, initY), new Vector(0, 150), 20));
                }

                double pos1 = 100;
                double pos2 = 750;
                addTo.add(new Enemy_Machine_Spray(new Vector(pos1, -40),
                        new Routine_SingleAttack(null, new Vector(pos1, 200), new Vector(pos1, -100),
                                200, 200, "spray", 200, 200), 10));

                addTo.add(new Enemy_Machine_Spray(new Vector(pos2, -40),
                        new Routine_SingleAttack(null, new Vector(pos2, 200), new Vector(pos2, -100),
                                200, 200, "spray", 200, 200), 10));
            }
        }

        //wave 3
        if(tick >= 2800 && tick <= 3240){
            if(tick == 2800 || tick == 3240){
                double initX = Driver_Game.getWidth() / 2;
                addTo.add(new Enemy_Machine_Bullet(new Vector(initX, -40),
                        new Routine_SingleAttack(null, new Vector(initX, 200), new Vector(initX, -100),
                                200, 200, "default", 1, 200), 10));
            }

            else if(tick >= 3000){
                if(tick % 60 == 0){
                    double pos1 = 100;
                    double pos2 = 750;
                    addTo.add(new Enemy_Machine_Spray(new Vector(pos1, -40), new Vector(0, 200), 5));
                    addTo.add(new Enemy_Machine_Spray(new Vector(pos2, -40), new Vector(0, 200), 5));
                }
            }

            if(tick == 3060){
                double startY = -40;
                double startPos1 = -40;
                double startPos2 = Driver_Game.getWidth() + 40;

                double endY = 50;
                double endPos1 = 300;
                double endPos2 = 550;

                Routine_GoWaitGo r1 = new Routine_GoWaitGo(null, new Vector(endPos1, endY), new Vector(-100, -100), 100, 100, 120);
                Routine_GoWaitGo r2 = new Routine_GoWaitGo(null, new Vector(endPos2, endY), new Vector(950, -100), 100, 100, 120);

                Enemy_Machine_Laser e1 = new Enemy_Machine_Laser(new Vector(startPos1, startY), r1, 20);
                Enemy_Machine_Laser e2 = new Enemy_Machine_Laser(new Vector(startPos2, startY), r2, 20);

                e1.setReloadDelay(200);
                e1.setAttackDelay(1);
                e1.setAttackName("sustain");
                e1.setCanAttack(true);
                e1.setContinuousAttack(true);

                e2.setReloadDelay(200);
                e2.setAttackDelay(1);
                e2.setAttackName("sustain");
                e2.setCanAttack(true);
                e2.setContinuousAttack(true);

                addTo.add(e1);
                addTo.add(e2);
            }

        }

        //wave 4
        if(tick >= 3600 && tick <= 4200){
            if(tick % 30 == 0){
                Vector spawnPos = new Vector(-40, 100);
                Vector polar = new Vector(90, 400);
                addTo.add(new Enemy_Wisp(spawnPos, polar, "default", 3));
            }
        }

        //wave 5
        if(tick >= 4400 && tick <= 4520){
            if(tick == 4400){
                double mid = Driver_Game.getWidth()/2;
                double waitPosY = 150;
                for(int i = -1; i < 2; i++){
                    Routine_GoWaitGo r = new Routine_GoWaitGo(null, new Vector(mid + (i * 100), waitPosY), new Vector(mid + (i * 80), -100), 250, 150, 400);
                    Enemy_Machine_Spray add = new Enemy_Machine_Spray(new Vector(mid + (i*100), -40), r, 10);
                    add.setReloadDelay(200);
                    add.setAttackDelay(200);
                    add.setAttackName("smart");
                    add.setCanAttack(true);
                    add.setContinuousAttack(true);

                    addTo.add(add);
                }
            }

            else if(tick == 4460){
                double mid = Driver_Game.getWidth()/2;
                double waitPosY = 250;
                for(int i = -1; i < 2; i++){
                    Routine_GoWaitGo r = new Routine_GoWaitGo(null, new Vector(mid + (i * 100), waitPosY), new Vector(mid + (i * 80), -100), 250, 150, 290);
                    Enemy_Machine_Spray add = new Enemy_Machine_Spray(new Vector(mid + (i*100), -40), r, 10);
                    add.setReloadDelay(200);
                    add.setAttackDelay(200);
                    add.setAttackName("smart");
                    add.setCanAttack(true);
                    add.setContinuousAttack(true);

                    addTo.add(add);
                }
            }

            else if(tick == 4520){
                double mid = Driver_Game.getWidth()/2;
                double waitPosY = 350;
                for(int i = -1; i < 2; i++){
                    Routine_GoWaitGo r = new Routine_GoWaitGo(null, new Vector(mid + (i * 100), waitPosY), new Vector(mid + (i * 80), -100), 250, 150, 180);
                    Enemy_Machine_Spray add = new Enemy_Machine_Spray(new Vector(mid + (i*100), -40), r, 10);
                    add.setReloadDelay(200);
                    add.setAttackDelay(200);
                    add.setAttackName("smart");
                    add.setCanAttack(true);
                    add.setContinuousAttack(true);

                    addTo.add(add);
                }
            }


        }

        //wave 6
        if(tick >= 4600 && tick <= 5200){
            if(tick % 30 == 0){
                Vector spawnPos = new Vector(890, 100);
                Vector polar = new Vector(-90, 400);
                addTo.add(new Enemy_Wisp(spawnPos, polar, "default", 3));
            }
        }

        //wave 7
        if(tick >= 5400 && tick <= 5460){
            if(tick == 5400){
                double spawnX = -40;
                double spawnYLowest = 150;
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest), new Vector(90, 160), 15));
                addTo.add(new Enemy_Machine_Laser(new Vector(spawnX, spawnYLowest + 80), new Vector(90, 160), 10));
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest + 160), new Vector(90, 160), 10));
            }

            else if(tick == 5430){
                double spawnX = -40;
                double spawnYLowest = 150;
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest), new Vector(90, 160), 15));
                addTo.add(new Enemy_Machine_Laser(new Vector(spawnX, spawnYLowest + 80), new Vector(90, 160), 10));
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest + 160), new Vector(90, 160), 10));
            }

            else if(tick == 5460){
                double spawnX = -40;
                double spawnYLowest = 150;
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest), new Vector(90, 160), 15));
                addTo.add(new Enemy_Machine_Laser(new Vector(spawnX, spawnYLowest + 80), new Vector(90, 160), 10));
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest + 160), new Vector(90, 160), 10));
            }
        }

        //wave 8
        if(tick >= 5600 && tick <= 6200){
            if(tick % 30 == 0){
                Vector spawnPos = new Vector(890, 100);
                Vector polar = new Vector(-75, 400);
                addTo.add(new Enemy_Wisp(spawnPos, polar, "default", 3));
            }
            else if(tick % 30 == 15){
                Vector spawnPos = new Vector(-40, 100);
                Vector polar = new Vector(75, 400);
                addTo.add(new Enemy_Wisp(spawnPos, polar, "default", 3));
            }
        }

        //wave 9
        if(tick >= 6400 && tick <= 6460){
            if(tick == 6400){
                double spawnX = 890;
                double spawnYLowest = 150;
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest), new Vector(-90, 320), "spray", 15));
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest + 80), new Vector(-90, 240), "spray", 10));
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest + 160), new Vector(-90, 160), "spray", 10));
            }

            else if(tick == 6430){
                double spawnX = 890;
                double spawnYLowest = 150;
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest), new Vector(-90, 160), "spray", 15));
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest + 80), new Vector(-90, 320), "spray", 10));
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest + 160), new Vector(-90, 240), "spray", 10));
            }

            else if(tick == 6460){
                double spawnX = 890;
                double spawnYLowest = 150;
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest), new Vector(-90, 240), "spray", 15));
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest + 80), new Vector(-90, 160), "spray", 10));
                addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnYLowest + 160), new Vector(-90, 320), "spray", 10));
            }
        }

        //wave 10
        if(tick == 6800){
            double y = -40;
            double[] x = new double[6];
            x[0] = 100;
            x[1] = 300;
            x[2] = 400;
            x[3] = 450;
            x[4] = 550;
            x[5] = 750;
            for(int i = 0; i < 6; i++){
                Routine_GoWaitGo r = new Routine_GoWaitGo(null, new Vector(x[i], 100), new Vector(x[i], -100), 100, 100, 120);

                Enemy_Machine_Laser e = new Enemy_Machine_Laser(new Vector(x[i], y), r, 20);

                e.setReloadDelay(200);
                e.setAttackDelay(1);
                e.setAttackName("sustain");
                e.setCanAttack(true);
                e.setContinuousAttack(false);

                addTo.add(e);
            }
        }

        //wave 11
        if(tick == 6900){
            double pos1 = 100;
            double pos2 = 750;
            addTo.add(new Enemy_Machine_Spray(new Vector(pos1, -40), new Vector(0, 200), 5));
            addTo.add(new Enemy_Machine_Spray(new Vector(pos2, -40), new Vector(0, 200), 5));
        }

        //wave 12
        if(tick >= 7200 && tick <= 7600){
            if(tick % 30 == 0) {
                ArrayList<Vector> a1 = new ArrayList<>();
                a1.add(new Vector(500, 250));
                a1.add(new Vector(1000, 800));
                ArrayList<Vector> a2 = new ArrayList<>();
                a2.add(new Vector(350, 250));
                a2.add(new Vector(-150, 800));

                Path_Polygon p1 = new Path_Polygon(a1, false, 400);
                Path_Polygon p2 = new Path_Polygon(a2, false, 400);

                addTo.add(new Enemy_Wisp(new Vector(500, -40), p1, 3));
                addTo.add(new Enemy_Wisp(new Vector(350, -40), p2, 3));
            }
        }

    }
    private static void addWave3(int tick, ArrayList<Enemy> addTo){
//        if(tick == 0){
//            addTo.add(new Enemy_Spider(new Vector(500, 200), new Vector(0, 0), "homingBullets"));
//        }
//        switch(tick){
//            case 0:
//                addTo.add(new Enemy_Boss_Claire());
//                break;
//        }

//        //STARTS HERE
//
//        //wave 1
        if(tick >= 300 && tick <= 1000){
            if(tick % 120 == 0){
                Vector spawnPos1 = new Vector(-40, -40);
                Vector spawnPos2 = new Vector(Driver_Game.getWidth() + 40, -40);
                Vector midPos1 = new Vector(Driver_Game.getWidth() - 25, 600);
                Vector midPos2 = new Vector(25, 600);
                Vector endPos1 = new Vector(-40, 1200);
                Vector endPos2 = new Vector(Driver_Game.getWidth() + 40, 1200);

                ArrayList<Vector> points1 = new ArrayList<>();
                points1.add(spawnPos1);
                points1.add(midPos1);
                points1.add(endPos1);
                ArrayList<Vector> points2 = new ArrayList<>();
                points2.add(spawnPos2);
                points2.add(midPos2);
                points2.add(endPos2);

                Path_Polygon path1 = new Path_Polygon(points1, false, 400);
                Path_Polygon path2 = new Path_Polygon(points2, false, 400);

                addTo.add(new Enemy_Wisp(spawnPos1, path1, "default", 4));
                addTo.add(new Enemy_Wisp(spawnPos1, path2, "default", 4));
            }
        }

        //wave 2
        if(tick == 1200){
            double spawny = -10;
            double midy = 200;
            double endy = -60;

            double x1 = 150;
            double x2 = Driver_Game.getWidth()/2;
            double x3 = 700;

            Vector spawnPos1 = new Vector(x1, spawny);
            Vector spawnPos2 = new Vector(x2, spawny);
            Vector spawnPos3 = new Vector(x3, spawny);

            Vector midPos1 = new Vector(x1, midy);
            Vector midPos2 = new Vector(x2, midy);
            Vector midPos3 = new Vector(x3, midy);

            Vector endPos1 = new Vector(x1, endy);
            Vector endPos2 = new Vector(x2, endy);
            Vector endPos3 = new Vector(x3, endy);

            ArrayList<Vector> points1 = new ArrayList<>();
            ArrayList<Vector> points2 = new ArrayList<>();
            ArrayList<Vector> points3 = new ArrayList<>();

            points1.add(spawnPos1);
            points1.add(midPos1);
            points1.add(endPos1);

            points2.add(spawnPos2);
            points2.add(midPos2);
            points2.add(endPos2);

            points3.add(spawnPos3);
            points3.add(midPos3);
            points3.add(endPos3);

            Path_Polygon path1 = new Path_Polygon(points1, false, 160);
            Path_Polygon path2 = new Path_Polygon(points2, false, 160);
            Path_Polygon path3 = new Path_Polygon(points3, false, 160);

            addTo.add(new Enemy_Spider(spawnPos1, path1, "solid", 10));
            addTo.add(new Enemy_Spider(spawnPos2, path2, "default", 10));
            addTo.add(new Enemy_Spider(spawnPos3, path3, "solid", 10));
        }

        //wave 3
        if(tick >= 1500 && tick <= 2000){
            if(tick % 120 == 0) {
                Vector spawnPos1 = new Vector(890, 100);
                Vector polar = new Vector(-75, 300);
                addTo.add(new Enemy_Wisp(spawnPos1, polar, 5));
            }
            else if((tick + 60) % 120 == 0) {
                Vector spawnPos2 = new Vector(890, 300);
                Vector polar = new Vector(-75, 300);
                addTo.add(new Enemy_Wisp(spawnPos2, polar,5));
            }
        }

        //wave 4
        if(tick == 2200){
            double spawny = -10;
            double midy = 200;
            double endy = -60;

            double x1 = 150;
            double x2 = Driver_Game.getWidth()/2;
            double x3 = 700;

            Vector spawnPos1 = new Vector(x1, spawny);
            Vector spawnPos2 = new Vector(x2, spawny);
            Vector spawnPos3 = new Vector(x3, spawny);

            Vector midPos1 = new Vector(x1, midy);
            Vector midPos2 = new Vector(x2, midy);
            Vector midPos3 = new Vector(x3, midy);

            Vector endPos1 = new Vector(x1, endy);
            Vector endPos2 = new Vector(x2, endy);
            Vector endPos3 = new Vector(x3, endy);

            ArrayList<Vector> points1 = new ArrayList<>();
            ArrayList<Vector> points2 = new ArrayList<>();
            ArrayList<Vector> points3 = new ArrayList<>();

            points1.add(spawnPos1);
            points1.add(midPos1);
            points1.add(endPos1);

            points2.add(spawnPos2);
            points2.add(midPos2);
            points2.add(endPos2);

            points3.add(spawnPos3);
            points3.add(midPos3);
            points3.add(endPos3);

            Path_Polygon path1 = new Path_Polygon(points1, false, 160);
            Path_Polygon path2 = new Path_Polygon(points2, false, 160);
            Path_Polygon path3 = new Path_Polygon(points3, false, 160);

            addTo.add(new Enemy_Spider(spawnPos1, path1, "solid", 10));
            addTo.add(new Enemy_Spider(spawnPos2, path2, "homingShot", 10));
            addTo.add(new Enemy_Spider(spawnPos3, path3, "solid", 10));
        }

        //wave 5
        if(tick >= 2500 && tick <= 3000){
            if(tick % 120 == 0) {
                Vector spawnPos1 = new Vector(-40, 100);
                Vector polar = new Vector(75, 300);
                addTo.add(new Enemy_Wisp(spawnPos1, polar, 5));
            }
            else if((tick + 60) % 120 == 0) {
                Vector spawnPos2 = new Vector(-40, 300);
                Vector polar = new Vector(75, 300);
                addTo.add(new Enemy_Wisp(spawnPos2, polar, 5));
            }
        }

        //wave 6
        if(tick == 3200){
            double spawny = -10;
            double midy = 200;
            double endy = -60;

            double x1 = 150;
            double x2 = Driver_Game.getWidth()/2;
            double x3 = 700;

            Vector spawnPos1 = new Vector(x1, spawny);
            Vector spawnPos2 = new Vector(x2, spawny);
            Vector spawnPos3 = new Vector(x3, spawny);

            Vector midPos1 = new Vector(x1, midy);
            Vector midPos2 = new Vector(x2, midy);
            Vector midPos3 = new Vector(x3, midy);

            Vector endPos1 = new Vector(x1, endy);
            Vector endPos2 = new Vector(x2, endy);
            Vector endPos3 = new Vector(x3, endy);

            ArrayList<Vector> points1 = new ArrayList<>();
            ArrayList<Vector> points2 = new ArrayList<>();
            ArrayList<Vector> points3 = new ArrayList<>();

            points1.add(spawnPos1);
            points1.add(midPos1);
            points1.add(endPos1);

            points2.add(spawnPos2);
            points2.add(midPos2);
            points2.add(endPos2);

            points3.add(spawnPos3);
            points3.add(midPos3);
            points3.add(endPos3);

            Path_Polygon path1 = new Path_Polygon(points1, false, 160);
            Path_Polygon path2 = new Path_Polygon(points2, false, 160);
            Path_Polygon path3 = new Path_Polygon(points3, false, 160);

            addTo.add(new Enemy_Spider(spawnPos1, path1, "solid", 10));
            addTo.add(new Enemy_Spider(spawnPos2, path2, "sensingShot", 10));
            addTo.add(new Enemy_Spider(spawnPos3, path3, "solid", 10));
        }

        //wave 7
        if(tick >= 3500 && tick <= 4000){
            if(tick % 120 == 0){
                Vector spawnPos = new Vector(randomNum(tick, 30, 100), -40);
                addTo.add(new Enemy_Wisp(spawnPos, new Vector(0, 200), 5));
            }
            else if((tick + 60) % 120 == 0){
                Vector spawnPos = new Vector(randomNum(tick, 750, 820), -40);
                addTo.add(new Enemy_Wisp(spawnPos, new Vector(0, 200), 5));
            }
        }

        //wave 8
        if(tick == 4200){
            double spawny = -10;
            double midy = 200;
            double endy = -60;

            double x1 = 150;
            double x2 = Driver_Game.getWidth()/2;
            double x3 = 700;

            Vector spawnPos1 = new Vector(x1, spawny);
            Vector spawnPos2 = new Vector(x2, spawny);
            Vector spawnPos3 = new Vector(x3, spawny);

            Vector midPos1 = new Vector(x1, midy);
            Vector midPos2 = new Vector(x2, midy);
            Vector midPos3 = new Vector(x3, midy);

            Vector endPos1 = new Vector(x1, endy);
            Vector endPos2 = new Vector(x2, endy);
            Vector endPos3 = new Vector(x3, endy);

            ArrayList<Vector> points1 = new ArrayList<>();
            ArrayList<Vector> points2 = new ArrayList<>();
            ArrayList<Vector> points3 = new ArrayList<>();

            points1.add(spawnPos1);
            points1.add(midPos1);
            points1.add(endPos1);

            points2.add(spawnPos2);
            points2.add(midPos2);
            points2.add(endPos2);

            points3.add(spawnPos3);
            points3.add(midPos3);
            points3.add(endPos3);

            Path_Polygon path1 = new Path_Polygon(points1, false, 160);
            Path_Polygon path2 = new Path_Polygon(points2, false, 160);
            Path_Polygon path3 = new Path_Polygon(points3, false, 160);

            addTo.add(new Enemy_Spider(spawnPos1, path1, "solid", 10));
            addTo.add(new Enemy_Spider(spawnPos2, path2, "homingBullets", 10));
            addTo.add(new Enemy_Spider(spawnPos3, path3, "solid", 10));
        }

        //wave 9
        if(tick == 4500){
            //path for triple spiral
            double y0 = -40;
            double y1 = 150;
            double y2 = -80;

            double x1 = 80;
            double x2 = Driver_Game.getWidth()/2;
            double x3 = Driver_Game.getWidth() - 80;

            Vector spawnPos1 = new Vector(x1, y0);
            Vector spawnPos2 = new Vector(x2, y0);
            Vector spawnPos3 = new Vector(x3, y0);
            Vector stayPos1 = new Vector(x1, y1);
            Vector stayPos2 = new Vector(x2, y1);
            Vector stayPos3 = new Vector(x3, y1);
            Vector endPos1 = new Vector(x1, y2);
            Vector endPos2 = new Vector(x2, y2);
            Vector endPos3 = new Vector(x3, y2);

            ArrayList<Vector> points1 = new ArrayList<>();
            ArrayList<Vector> points2 = new ArrayList<>();
            ArrayList<Vector> points3 = new ArrayList<>();
            points1.add(spawnPos1);
            points1.add(stayPos1);
            points2.add(spawnPos2);
            points2.add(stayPos2);
            points3.add(spawnPos3);
            points3.add(stayPos3);

            Path_Polygon entry1 = new Path_Polygon(points1, false, 160);
            Path_Polygon entry2 = new Path_Polygon(points2, false, 160);
            Path_Polygon entry3 = new Path_Polygon(points3, false, 160);
            Path_Polygon end1 = new Path_Polygon(endPos1, false, 160);
            Path_Polygon end2 = new Path_Polygon(endPos2, false, 160);
            Path_Polygon end3 = new Path_Polygon(endPos3, false, 160);

            Enemy_Wisp w1 = new Enemy_Wisp(spawnPos1, entry1, 40);
            w1.setNewBehavior(new Instruction_WaitPathOver(w1,
                    new Instruction_Attack(w1,
                            new Instruction_Timer(w1,
                                    new Instruction_StartPath(w1, end1), 500), "spiral", 500, 500)));

            Enemy_Wisp w2 = new Enemy_Wisp(spawnPos2, entry2, 40);
            w2.setNewBehavior(new Instruction_WaitPathOver(w2,
                    new Instruction_Attack(w2,
                            new Instruction_Timer(w2,
                                    new Instruction_StartPath(w2, end2), 500), "spiral", 500, 500)));

            Enemy_Wisp w3 = new Enemy_Wisp(spawnPos3, entry3, 40);
            w3.setNewBehavior(new Instruction_WaitPathOver(w3,
                    new Instruction_Attack(w3,
                            new Instruction_Timer(w3,
                                    new Instruction_StartPath(w3, end3), 500), "spiral", 500, 500)));

            addTo.add(w1);
            addTo.add(w2);
            addTo.add(w3);

            addTo.add(new Enemy_Wisp(new Vector(Driver_Game.getWidth()/3, -40), new Vector(0, 120), "lasers", 10));
            addTo.add(new Enemy_Wisp(new Vector((Driver_Game.getWidth()/3)*2, -40), new Vector(0, 120), "lasers", 10));
        }

        //wave 10
        if(tick >= 5200 && tick <= 6000){
            if(tick % 100 == 0){
                //starts at 1, ends at maximum (6000 - 5200 = 800, 800 / 100 = 9)
                int d = (tick - 5200) / 100;
                d += 1;
                double x = d * (Driver_Game.getWidth()/10);
                double spawny = -10;
                double midy = 200;
                double endy = -60;

                Vector spawnPos = new Vector(x, spawny);
                Vector midPos = new Vector(x, midy);
                Vector endPos = new Vector(x, endy);

                ArrayList<Vector> points = new ArrayList<>();
                points.add(spawnPos);
                points.add(midPos);
                points.add(endPos);

                Path_Polygon path = new Path_Polygon(points, false, 160);

                addTo.add(new Enemy_Spider(spawnPos, path, "homingShot", 8));
            }
        }

        //wave 11
        if(tick == 6100){
            double xd = Driver_Game.getWidth()/10;
            for(int i = 0; i < 9; i++){
                double x = xd + (xd * i);
                //need to do the singleAttack thingy
                Vector initPos = new Vector(x, -40);
                Vector midPos = new Vector(x, 200);
                Vector endPos = new Vector(x, -100);
                double speed = 180;
                addTo.add(new Enemy_Wisp(initPos, new Routine_SingleAttack(null, midPos, endPos, speed, speed, "spread", 200, 200), 6));
            }
        }

        //wave 12
        if(tick >= 6200 && tick <= 7000){
            if(tick % 100 == 0){

                int d = (tick - 6200) / 100;
                d += 1;
                double x = Driver_Game.getWidth() - (d * (Driver_Game.getWidth()/10));
                double spawny = -10;
                double midy = 200;
                double endy = -60;

                Vector spawnPos = new Vector(x, spawny);
                Vector midPos = new Vector(x, midy);
                Vector endPos = new Vector(x, endy);

                ArrayList<Vector> points = new ArrayList<>();
                points.add(spawnPos);
                points.add(midPos);
                points.add(endPos);

                Path_Polygon path = new Path_Polygon(points, false, 80);

                addTo.add(new Enemy_Spider(spawnPos, path, "homingShot", 6));
            }
        }

        //wave 13
        if(tick == 7100){
            double xd = Driver_Game.getWidth()/6;
            double x = xd;
            for(int i = 0; i < 5; i++){
                addTo.add(new Enemy_Wisp(new Vector(x, -40), new Vector(0, 120), "lasers", 6));
                x += xd;
            }
        }

        //wave 14
        if(tick >= 7200 && tick <= 8000){
            if(tick % 200 == 0){
                double xd = Driver_Game.getWidth()/5;
                double x = xd;
                for(int i = 0; i < 4; i++){
                    addTo.add(new Enemy_Spider(new Vector(x, -10), new Vector(0, 300), 4));
                    x += xd;
                }
            }
            if(tick % 60 == 0){
                double d = tick - 7200;
                d /= 60;
                d %= 8;
                double xd = Driver_Game.getWidth() / 9;
                double x = xd * d;
                Vector spawnPos = new Vector(x, -10);
                Vector midPos = new Vector(x, 200);
                Vector endPos = new Vector(x, -100);
                ArrayList<Vector> points = new ArrayList<>();
                points.add(spawnPos);
                points.add(midPos);
                points.add(endPos);
                Path_Polygon path = new Path_Polygon(points, false, 160);
                addTo.add(new Enemy_Spider(spawnPos, path, "homingShot", 6));
            }
            if(tick % 400 == 0){
                double x1 = 50;
                double x2 = Driver_Game.getWidth() - 50;
                Vector spawnPos1 = new Vector(x1, -10);
                Vector spawnPos2 = new Vector(x2, -10);
                Vector midPos1 = new Vector(x1, 400);
                Vector midPos2 = new Vector(x2, 400);
                Vector endPos1 = new Vector(x1, -100);
                Vector endPos2 = new Vector(x2, -100);
                ArrayList<Vector> points1 = new ArrayList<>();
                ArrayList<Vector> points2 = new ArrayList<>();
                points1.add(spawnPos1);
                points1.add(midPos1);
                points1.add(endPos1);
                points2.add(spawnPos2);
                points2.add(midPos2);
                points2.add(endPos2);
                Path_Polygon path1 = new Path_Polygon(points1, false, 160);
                Path_Polygon path2 = new Path_Polygon(points2, false, 160);
                addTo.add(new Enemy_Spider(spawnPos1, path1, "sensingShot", 6));
                addTo.add(new Enemy_Spider(spawnPos2, path2, "sensingShot", 6));
            }
            if(tick == 7800){
                double xd = Driver_Game.getWidth()/4;
                double x = xd;
                for(int i = 0; i < 3; i++){
                    addTo.add(new Enemy_Spider(new Vector(x, -10), new Vector(0, 250), "homingBullets", 6));
                    x += xd;
                }
            }
        }

        //wave 15
        if(tick == 8100){
            double xd = Driver_Game.getWidth()/13;
            double x = xd;
            for(int i = 0; i < 12; i++){
                Vector spawnPos = new Vector(x, -10);
                Vector midPos = new Vector(x, 100);
                Vector endPos = new Vector(x, -100);
                ArrayList<Vector> points = new ArrayList<>();
                points.add(spawnPos);
                points.add(midPos);
                points.add(endPos);
                Path_Polygon path = new Path_Polygon(points, false, 80);
                addTo.add(new Enemy_Spider(spawnPos, path, "solid", 6));
                x += xd;
            }
        }

        //wave 16
        if(tick >= 8200 && tick <= 9000){
            if(tick % 100 == 0){
                if(tick <= 8700) {
                    addTo.add(new Enemy_Spider(new Vector(50, -10), new Vector(0, 300), 4));
                    addTo.add(new Enemy_Spider(new Vector(800, -10), new Vector(0, 300), 4));
                }
                else{
                    addTo.add(new Enemy_Spider(new Vector(50, -10), new Vector(0, 300), "sensingShot", 4));
                    addTo.add(new Enemy_Spider(new Vector(800, -10), new Vector(0, 300), "sensingShot", 4));
                }
            }

            if(tick >= 8400 && tick <= 8460){
                if(tick == 8400){
                    double x1 = Driver_Game.getWidth()/6;
                    Vector spawnPos1 = new Vector(x1, -10);
                    Vector midPos1 = new Vector(x1, 200);
                    Vector endPos1 = new Vector(x1, -100);

                    double x2 = Driver_Game.getWidth() - x1;
                    Vector spawnPos2 = new Vector(x2, -10);
                    Vector midPos2 = new Vector(x2, 200);
                    Vector endPos2 = new Vector(x2, -100);

                    ArrayList<Vector> points1 = new ArrayList<>();
                    points1.add(spawnPos1);
                    points1.add(midPos1);
                    points1.add(endPos1);

                    ArrayList<Vector> points2 = new ArrayList<>();
                    points2.add(spawnPos2);
                    points2.add(midPos2);
                    points2.add(endPos2);

                    Path_Polygon path1 = new Path_Polygon(points1, false, 160);
                    Path_Polygon path2 = new Path_Polygon(points2, false, 160);

                    addTo.add(new Enemy_Spider(spawnPos1, path1, "homingShot", 6));
                    addTo.add(new Enemy_Spider(spawnPos2, path2, "homingShot", 6));
                }
                else if(tick == 8430){
                    double x1 = (Driver_Game.getWidth()/6) * 2;
                    Vector spawnPos1 = new Vector(x1, -10);
                    Vector midPos1 = new Vector(x1, 200);
                    Vector endPos1 = new Vector(x1, -100);

                    double x2 = Driver_Game.getWidth() - x1;
                    Vector spawnPos2 = new Vector(x2, -10);
                    Vector midPos2 = new Vector(x2, 200);
                    Vector endPos2 = new Vector(x2, -100);

                    ArrayList<Vector> points1 = new ArrayList<>();
                    points1.add(spawnPos1);
                    points1.add(midPos1);
                    points1.add(endPos1);

                    ArrayList<Vector> points2 = new ArrayList<>();
                    points2.add(spawnPos2);
                    points2.add(midPos2);
                    points2.add(endPos2);

                    Path_Polygon path1 = new Path_Polygon(points1, false, 160);
                    Path_Polygon path2 = new Path_Polygon(points2, false, 160);

                    addTo.add(new Enemy_Spider(spawnPos1, path1, "homingShot", 6));
                    addTo.add(new Enemy_Spider(spawnPos2, path2, "homingShot", 6));
                }
                else if(tick == 8460){
                    double x = (Driver_Game.getWidth()/6) * 3;
                    Vector spawnPos = new Vector(x, -10);
                    Vector midPos = new Vector(x, 200);
                    Vector endPos = new Vector(x, -100);

                    ArrayList<Vector> points = new ArrayList<>();
                    points.add(spawnPos);
                    points.add(midPos);
                    points.add(endPos);


                    Path_Polygon path = new Path_Polygon(points, false, 160);

                    addTo.add(new Enemy_Spider(spawnPos, path, "homingShot", 6));
                }
            }

            if(tick == 9000){
                double xd = Driver_Game.getWidth()/6;
                double x = xd;
                for(int i = 0; i < 5; i++){
                    addTo.add(new Enemy_Spider(new Vector(x, -10), new Vector(0, 300), "homingBullets", 6));
                    x += xd;
                }
            }
        }

        if(tick >= 9100 && tick <= 9500){
            if(tick % 200 == 0){
                double xd = Driver_Game.getWidth()/5;
                double x = xd;
                for(int i = 0; i < 4; i++){
                    addTo.add(new Enemy_Spider(new Vector(x, -10), new Vector(0, 300), 4));
                    x += xd;
                }
            }
            else if((tick + 100) % 200 == 0){
                double xd = Driver_Game.getWidth()/6;
                double x = xd;
                for(int i = 0; i < 5; i++){
                    addTo.add(new Enemy_Spider(new Vector(x, -10), new Vector(0, 300), 4));
                    x += xd;
                }
            }
        }
//
////        ENDS HERE
////        tick == 10000

        if(tick == 10000){
            addTo.add(new Enemy_Boss_Claire());
        }
    }
    private static void addWave4(int tick, ArrayList<Enemy> addTo){
//        switch(tick){
//            case 0:
//                addTo.add(new Enemy_Boss_XiaoLi());
//                break;
//        }

        //wave 1
        //start

        if(tick == 0){
            Vector corner1 = new Vector(0, 0);
            Vector corner2 = new Vector(850, 0);
            Vector spawnPos1 = new Vector(300, -40);
            Vector spawnPos2 = new Vector(850 - 300, -40);

            double distance1 = Vector.getDistance(spawnPos1, corner1);
            double initAngle1 = Vector.getAngle(corner1, spawnPos1);
            Vector av1 = new Vector(-35, distance1);

            double distance2 = Vector.getDistance(spawnPos2, corner2);
            double initAngle2 = Vector.getAngle(corner2, spawnPos2);
            Vector av2 = new Vector(35, distance2);

            addTo.add(new Enemy_Machine_Laser(spawnPos1, new Path_Orbit(corner1, av1, initAngle1), 100));
            addTo.add(new Enemy_Machine_Laser(spawnPos2, new Path_Orbit(corner2, av2, initAngle2), 100));
        }

        //wave 2
        if(tick == 180){
            Vector pos1 = new Vector(50, -40);
            Vector pos2 = new Vector(800, -40);
            Vector stand1 = new Vector(50, 50);
            Vector stand2 = new Vector(800, 50);
            double speed = 100;
            String attackName = "spiral";
            int d = 180;

            addTo.add(new Enemy_Wisp(pos1, new Routine_SingleAttack(null, stand1, Vector.add(pos1, new Vector(0, -100)), speed, speed, attackName, d, d), 50));
            addTo.add(new Enemy_Wisp(pos2, new Routine_SingleAttack(null, stand2, Vector.add(pos2, new Vector(0, -100)), speed, speed, attackName, d, d), 50));
        }

        //wave 3
        if(tick == 360){
            double a = Driver_Game.getWidth()/6;
            Vector pos1 = new Vector(a, -40);
            Vector pos2 = new Vector(850 - a, -40);
            Vector stand1 = new Vector(a, 50);
            Vector stand2 = new Vector(850 - a, 50);
            double speed = 200;
            String attackName = "spread";
            int d = 200;

            addTo.add(new Enemy_Wisp(pos1, new Routine_SingleAttack(null, stand1, Vector.add(pos1, new Vector(0, -100)), speed, speed, attackName, d, d), 50));
            addTo.add(new Enemy_Wisp(pos2, new Routine_SingleAttack(null, stand2, Vector.add(pos2, new Vector(0, -100)), speed, speed, attackName, d, d), 50));
        }
        if(tick == 380){
            double a = Driver_Game.getWidth()/6;
            Vector pos1 = new Vector(a + a, -40);
            Vector pos2 = new Vector(850 - a - a, -40);
            Vector stand1 = new Vector(a + a, 50);
            Vector stand2 = new Vector(850 - a - a, 50);
            double speed = 200;
            String attackName = "spread";
            int d = 200;

            addTo.add(new Enemy_Wisp(pos1, new Routine_SingleAttack(null, stand1, Vector.add(pos1, new Vector(0, -100)), speed, speed, attackName, d, d), 50));
            addTo.add(new Enemy_Wisp(pos2, new Routine_SingleAttack(null, stand2, Vector.add(pos2, new Vector(0, -100)), speed, speed, attackName, d, d), 50));
        }
        if(tick == 400){
            double a = Driver_Game.getWidth()/6;
            Vector pos1 = new Vector(3 * a, -40);
            Vector stand1 = new Vector(3 * a, 50);
            double speed = 200;
            String attackName = "spread";
            int d = 200;

            addTo.add(new Enemy_Wisp(pos1, new Routine_SingleAttack(null, stand1, Vector.add(pos1, new Vector(0, -100)), speed, speed, attackName, d, d), 50));
        }

        //wave 4
        if(tick == 420){
            Vector pivot1 = new Vector(0, 0);
            Vector pivot2 = new Vector(850, 0);
            Vector spawnPos1 = new Vector(-40, 300);
            Vector spawnPos2 = new Vector(890, 300);

            double distance1 = Vector.getDistance(spawnPos1, pivot1);
            double initAngle1 = Vector.getAngle(pivot1, spawnPos1);
            Vector av1 = new Vector(35, distance1);

            double distance2 = Vector.getDistance(spawnPos2, pivot2);
            double initAngle2 = Vector.getAngle(pivot2, spawnPos2);
            Vector av2 = new Vector(-35, distance2);

            addTo.add(new Enemy_Machine_Laser(spawnPos1, new Path_Orbit(pivot1, av1, initAngle1), 100));
            addTo.add(new Enemy_Machine_Laser(spawnPos2, new Path_Orbit(pivot2, av2, initAngle2), 100));
        }

        //wave 5
        if(tick >= 600 && tick <= 1200){
            if(tick % 30 == 0){
                Vector spawnPos = new Vector(randomNum(tick, 50, 800), -40);
                Vector midPos = new Vector(randomNum(tick + 123, 50, 800), randomNum(tick + 321, 150, 300));
                Vector endPos;
                if(tick % 60 == 0){
                    endPos = new Vector(-100, randomNum(tick + 1, 400, 500));
                }
                else{
                    endPos = new Vector(950, randomNum(tick + 1, 400, 500));
                }
                ArrayList<Vector> points = new ArrayList<>();
                points.add(midPos);
                points.add(endPos);
                Path_Polygon path = new Path_Polygon(points, false, 300);

                addTo.add(new Enemy_Machine_Spray(spawnPos, path, "smart", 7));
            }
        }

        //wave 6
        if(tick >= 1500 && tick <= 2100){
            if(tick == 1500){
                double a = Driver_Game.getWidth()/2;
                Vector pos1 = new Vector(a, -40);
                Vector stand1 = new Vector(a, 50);
                Instruction_StartPath end = new Instruction_StartPath(null, null, new Path_Polygon(Vector.add(pos1, new Vector(0, -100)), false, 200));
                Instruction_ToggleAttack ea = new Instruction_ToggleAttack(null, end, false);
                Instruction_Timer timer = new Instruction_Timer(null, ea, 600);
                Instruction_ToggleAttack t = new Instruction_ToggleAttack(null, timer, true);
                Instruction_Attack atk = new Instruction_Attack(null, t, "advanced", 200, 200);
                Instruction_WaitPathOver wpo = new Instruction_WaitPathOver(null, atk);
                Instruction_StartPath entry = new Instruction_StartPath(null, wpo, new Path_Polygon(stand1, false, 200));
                addTo.add(new Enemy_Wisp(pos1, entry, 300));
            }
            if(tick % 30 == 0){
                double x1 = 50;
                double x2 = 800;
                addTo.add(new Enemy_Wisp(new Vector(x1, -40), new Vector(0, 500), 5));
                addTo.add(new Enemy_Wisp(new Vector(x2, -40), new Vector(0, 500), 5));
            }
        }

        //wave  7
        if(tick >= 2400 && tick <= 3000){
            if(tick % 23 == 0){
                double x = randomNum(tick + 23, 50, 800);
                Vector initPos = new Vector(x, -40);

                double speed = randomNum(tick + 24, 200, 400);
                double d = randomNum(tick * 7, 0, 100);
                String atk;
                if(d >= 50){
                    atk = "default";
                }
                else if(d >= 20) {
                    atk = "backShot";
                }
                else{
                    atk = "sideShot";
                }
                addTo.add(new Enemy_Wisp(initPos, new Vector(0, speed), atk, 5));
            }
        }

        //wave 8
        if(tick >= 3300 && tick <= 3900){
            if(tick % 30 == 0){
                double min = Driver_Game.getWidth()/3;
                double max = min + min;
//                System.out.println(min + " " + max);
//                double x = randomNum(tick + 11, (double)Driver.getWidth()/3, ((double)Driver.getWidth()/3) * 2);
                double x = randomNum(tick + 11, min, max);
                Vector initPos = new Vector(x, 1040);
                double speed = randomNum(tick + 24, 200, 400);
                addTo.add(new Enemy_Wisp(initPos, new Vector(180, speed), 5));
            }
        }

        //wave 9
        if(tick >= 4200 && tick <= 4800){
            if(tick == 4200){
                double a = 100;
                Vector pos1 = new Vector(a, -40);
                Vector stand1 = new Vector(a, 100);
                Instruction_StartPath end = new Instruction_StartPath(null, null, new Path_Polygon(Vector.add(pos1, new Vector(0, -100)), false, 200));
                Instruction_ToggleAttack ea = new Instruction_ToggleAttack(null, end, false);
                Instruction_Timer timer = new Instruction_Timer(null, ea, 600);
                Instruction_ToggleAttack t = new Instruction_ToggleAttack(null, timer, true);
                Instruction_Attack atk = new Instruction_Attack(null, t, "spray", 200, 200);
                Instruction_WaitPathOver wpo = new Instruction_WaitPathOver(null, atk);
                Instruction_StartPath entry = new Instruction_StartPath(null, wpo, new Path_Polygon(stand1, false, 200));
                addTo.add(new Enemy_Machine_Spray(pos1, entry, 300));
            }
            if(tick % 60 == 0){
                Vector initPos = new Vector(100, -40);
                Vector polar = new Vector(0, 400);
                addTo.add(new Enemy_Machine_Spray(initPos, polar, "smart", 7));
            }
            else if((tick + 30) % 60 == 0){
                Vector initPos = new Vector(-40, 100);
                Vector polar = new Vector(90, 400);
                addTo.add(new Enemy_Machine_Spray(initPos, polar, "smart", 7));
            }
        }

        //wave 10
        if(tick >= 5100 && tick <= 5700){
            if(tick == 5100){
                double a = Driver_Game.getWidth()/2;
                Vector pos1 = new Vector(a, -40);
                Vector stand1 = new Vector(a, 50);
                Instruction_StartPath end = new Instruction_StartPath(null, null, new Path_Polygon(Vector.add(pos1, new Vector(0, -100)), false, 200));
                Instruction_ToggleAttack ea = new Instruction_ToggleAttack(null, end, false);
                Instruction_Timer timer = new Instruction_Timer(null, ea, 600);
                Instruction_ToggleAttack t = new Instruction_ToggleAttack(null, timer, true);
                Instruction_Attack atk = new Instruction_Attack(null, t, "default", 200, 200);
                Instruction_WaitPathOver wpo = new Instruction_WaitPathOver(null, atk);
                Instruction_StartPath entry = new Instruction_StartPath(null, wpo, new Path_Polygon(stand1, false, 200));
                addTo.add(new Enemy_Machine_Laser(pos1, entry, 300));
            }
            if(tick % 45 == 0) {
                if (tick <= 5300) {
                    Vector pos1 = new Vector(50, -40);
                    Vector pos2 = new Vector(800, -40);
                    addTo.add(new Enemy_Wisp(pos1, new Vector(0, 300), "spread", 5));
                    addTo.add(new Enemy_Wisp(pos2, new Vector(0, 300), "spread", 5));
                } else if (tick <= 5500) {
                    Vector pos1 = new Vector(50, -40);
                    Vector pos2 = new Vector(800, -40);
                    addTo.add(new Enemy_Wisp(pos1, new Vector(0, 300), "sideShot", 5));
                    addTo.add(new Enemy_Wisp(pos2, new Vector(0, 300), "sideShot", 5));
                }
            }
        }

        //wave 11
        if(tick >= 6000 && tick <= 6600){
            if(tick == 6000){
                double initX = Driver_Game.getWidth() / 7;
                double initY = -40;
                for(int i = 1; i <= 7; i++){
                    addTo.add(new Enemy_Machine_Shield(new Vector(initX * i, initY), new Routine_GoWaitGo(null,
                            new Vector(initX * i, 120), new Vector(initX * i, -200), 300, 300, 500), 100));
                }
            }
            //this structure is better
            if(tick % 30 == 0){
                if(tick % 60 == 0){
                    double speed = randomNum(tick + 12, 300, 500);
                    addTo.add(new Enemy_Machine_Laser(new Vector(-40, 50), new Vector(90, speed), 10));
                }
                else{
                    double speed = randomNum(tick - 12, 300, 500);
                    addTo.add(new Enemy_Machine_Laser(new Vector(890, 50), new Vector(270, speed), 10));
                }
            }
        }

        //wave 12
        if(tick >= 6900 && tick <= 7500){
            if(tick % 60 == 0) {
                Vector ipos1 = new Vector(120, 1040);
                Vector ipos2 = new Vector(850 - 120, 1040);

                //construct the path
                ArrayList<Vector> points1 = new ArrayList<>();
                ArrayList<Vector> points2 = new ArrayList<>();
                double endx = 850 / 2;
                endx -= 25;
                double diameter = endx - ipos1.getA();
                double radius = diameter/2;
                double midx = ipos1.getA() + radius;
                Vector mid = new Vector(midx, radius + 50);
                for(int angle = 270; angle >= 90; angle -= 15) {
                    points1.add(Vector.add(mid, Vector.polarToVelocity(new Vector(angle, radius))));
                }
                points1.add(new Vector(ipos1.getA() + diameter, 1100));
                Path_Polygon path1 = new Path_Polygon(points1, false, 400);
                for(Vector v : points1){
                    double distanceToMid = 425 - v.getA();
                    points2.add(Vector.add(v, new Vector(distanceToMid * 2, 0)));
                }
                Path_Polygon path2 = new Path_Polygon(points2, false, 400);

                addTo.add(new Enemy_Wisp(ipos1, path1, 5));
                addTo.add(new Enemy_Wisp(ipos2, path2, 5));
            }
        }

        //end
        
        //t 8000

        //8k
        if(tick == 8000){
            addTo.add(new Enemy_Boss_XiaoLi());
        }
    }
    private static void addWave5(int tick, ArrayList<Enemy> addTo){
//        switch(tick){
//            case 0:
//                addTo.add(new Enemy_Boss_Pax());
//                break;
//        }
        //opening
        if(tick >= 0 && tick <= 300){
            if(tick % 60 == 0){
                double initY = -40;
                double initX = randomNum((tick * 937) + 12, 50, 800);
//                String attackName;
//                if(randomNum((tick * 12) + 123, 0, 100) > 50){
//                    attackName = "default";
//                }
//                else{
//                    attackName = "shotgun";
//                }
                addTo.add(new Enemy_Machine_Bullet(new Vector(initX, initY),
                        new Routine_SingleAttack(null, new Vector(initX, 50), new Vector(initX, -100),
                                200, 200, "default", 1, 60), 10));
            }
        }
        //1's

        //1/s
        if(tick > 360 && tick < 1800){
//            if(tick < 720) {
//                if (tick % 60 == 0) {
//                    double initX = randomNum(tick + 12, 50, 800);
//                    double initY = -40;
//                    boolean left = randomNum(tick + 23, 0, 100000) < 50000;
//                    double n = randomNum(tick + 92, 0, 100000);
//
//                }
//            }
            double t;
            if(tick < 720){
                t = 60;
            }
            else if(tick < 1200){
                t = 45;
            }
            else if(tick < 1560){
                t = 30;
            }
            else{
                t = 20;
            }
            if(tick % t == 0){
                double initX = randomNum((tick * 274) + 12, 50, 800);
                double initY = -40;
                double finalY = randomNum((tick * 264) + 7, 200, 400);
                double speed = randomNum((tick * 643) + 24, 200, 300);
                double inithp = randomNum(tick + 48, 10, 20);
                boolean left = randomNum((tick * 361373) + 23, 0, 100000) < 50000;
                double n = randomNum((tick * 38472) + 92, 0, 100000);
                if(n < 25000){
                    Instruction_StartPathToY startPathToY = new Instruction_StartPathToY(null, finalY, speed);
                    Instruction_Timer timer = new Instruction_Timer(null, 30);
                    Instruction_Attack attack = new Instruction_Attack(null, "default", 200, 200);
                    Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);
                    Instruction_WaitPathOver wait = new Instruction_WaitPathOver(null);
                    Instruction_SetPolar setPolar;
                    if(left){
                        setPolar = new Instruction_SetPolar(null, new Vector(-90, speed));
                    }
                    else{
                        setPolar = new Instruction_SetPolar(null, new Vector(90, speed));
                    }
                    startPathToY.setNext(timer);
                    timer.setNext(attack);
                    attack.setNext(toggle);
                    toggle.setNext(wait);
                    wait.setNext(setPolar);
                    addTo.add(new Enemy_Machine_Laser(new Vector(initX, initY), startPathToY, inithp));
                }
                else{
                    String attackName;
                    int attackDelay = 200;
                    if(n < 50000){
                        attackName = "default";
                        attackDelay = 1;
                    }
                    else if(n < 75000){
                        attackName = "spray";
                    }
                    else{
                        attackName = "smart";
                    }
                    Instruction_StartPathToY startPathToY = new Instruction_StartPathToY(null, finalY, speed);
                    Instruction_Timer timer = new Instruction_Timer(null, 30);
                    Instruction_Attack attack = new Instruction_Attack(null, attackName, attackDelay, 200);
                    Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);
                    Instruction_WaitPathOver wait = new Instruction_WaitPathOver(null);
                    Instruction_SetPolar setPolar;
                    if(left){
                        setPolar = new Instruction_SetPolar(null, new Vector(-90, speed));
                    }
                    else{
                        setPolar = new Instruction_SetPolar(null, new Vector(90, speed));
                    }
                    startPathToY.setNext(timer);
                    timer.setNext(attack);
                    attack.setNext(toggle);
                    toggle.setNext(wait);
                    wait.setNext(setPolar);
                    addTo.add(new Enemy_Machine_Spray(new Vector(initX, initY), startPathToY, inithp));
                }
            }
        }

        //3's
        if(tick >= 2000 && tick < 4000){
            if(tick >= 2000 && tick <= 2060){
                double spawnY = -40;
                double spawnXLowest = 365;
                double haltYHighest = 360;
                if(tick == 2000) {
                    for (int i = 0; i < 3; i++) {
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        Path path = new Path_Polygon(a, false, 160);
                        addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnY), path, "smart", 15));
                    }
                }
                if(tick == 2030){
                    for (int i = 0; i < 3; i++) {
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 80;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        Path path = new Path_Polygon(a, false, 160);
                        addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnY), path, "smart", 15));
                    }
                }
                if(tick == 2060){
                    Enemy[] enemies = new Enemy[3];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Bullet(new Vector(), new Vector(), "default", 15);
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    for(int i = 0; i < 3; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 160;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        Path path = new Path_Polygon(a, false, 160);
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
            }
            if(tick >= 2500 && tick <= 2560){
                double spawnY = -40;
                double spawnXLowest = 365;
                double haltYHighest = 360;
                if(tick == 2500) {
                    for (int i = 0; i < 3; i++) {
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(-10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        Path path = new Path_Polygon(a, false, 160);
                        addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnY), path, "smart", 15));
                    }
                }
                if(tick == 2530){
                    for (int i = 0; i < 3; i++) {
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 80;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(-10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        Path path = new Path_Polygon(a, false, 160);
                        addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnY), path, "smart", 15));
                    }
                }
                if(tick == 2560){
                    Enemy[] enemies = new Enemy[3];
                    enemies[0] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                    enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[2] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                    for(int i = 0; i < 3; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 160;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(-10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        Path path = new Path_Polygon(a, false, 160);
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
            }
            if(tick >= 3000 && tick <= 3060){
                double spawnY = -40;
                double spawnXLowest = 365;
                double haltYHighest = 360;
                if(tick == 3000) {
                    for (int i = 0; i < 3; i++) {
                    double spawnX = spawnXLowest + (80 * i);
                    double haltY = haltYHighest;
                    Vector v1 = new Vector(spawnX, haltY);
                    Vector v2 = new Vector(10000, haltY);
                    ArrayList<Vector> a = new ArrayList<>();
                    a.add(v1);
                    a.add(v2);
                    Path path = new Path_Polygon(a, false, 160);
                    addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnY), path, "smart", 15));
                }
            }
                if(tick == 3030){
                    Enemy[] enemies = new Enemy[3];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "spray", 15);
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    for(int i = 0; i < 3; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 160;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        Path path = new Path_Polygon(a, false, 160);
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
                if(tick == 3060) {
                    for (int i = 0; i < 3; i++) {
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 160;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        Path path = new Path_Polygon(a, false, 160);
                        addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnY), path, "smart", 15));
                    }
                }
            }
            if(tick >= 3500 && tick <= 3560){
                double spawnY = -40;
                double spawnXLowest1 = 200;
                double spawnXLowest2 = 490;
//                double[] spawnXLowest = {200, 490};
                double haltYHighest = 450;
                if(tick == 3500){
                    for (int i = 0; i < 3; i++) {
                        double spawnX = spawnXLowest1 + (80 * i);
                        double haltY = haltYHighest;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(-10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        Path path = new Path_Polygon(a, false, 160);
                        addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnY), path, "smart", 15));

                        spawnX = spawnXLowest2 + (80 * i);
                        v1 = new Vector(spawnX, haltY);
                        v2 = new Vector(10000, haltY);
                        a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        path = new Path_Polygon(a, false, 160);
                        addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnY), path, "smart", 15));
                    }
                }
                if(tick == 3530) {
                    for (int i = 0; i < 3; i++) {
                        double spawnX = spawnXLowest1 + (80 * i);
                        double haltY = haltYHighest - 80;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(-10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        Path path = new Path_Polygon(a, false, 160);
                        addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnY), path, "default", 15));

                        spawnX = spawnXLowest2 + (80 * i);
                        v1 = new Vector(spawnX, haltY);
                        v2 = new Vector(10000, haltY);
                        a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        path = new Path_Polygon(a, false, 160);
                        addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnY), path, "default", 15));
                    }
                }
                if(tick == 3560) {
                    for (int i = 0; i < 3; i++) {
                        double spawnX = spawnXLowest1 + (80 * i);
                        double haltY = haltYHighest - 160;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(-10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        Path path = new Path_Polygon(a, false, 160);
                        addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnY), path, "smart", 15));

                        spawnX = spawnXLowest2 + (80 * i);
                        v1 = new Vector(spawnX, haltY);
                        v2 = new Vector(10000, haltY);
                        a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        path = new Path_Polygon(a, false, 160);
                        addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnY), path, "smart", 15));
                    }
                }
            }
        }

        //5's
        if(tick >= 4000 && tick < 6000){
//            if(tick % 60 == 0){
//                double initX = randomNum((tick * 274) + 12, 50, 800);
//                double initY = -40;
//                double finalY = randomNum((tick * 264) + 7, 200, 400);
//                double speed = randomNum((tick * 643) + 24, 200, 300);
//                double inithp = randomNum(tick + 48, 10, 20);
//                boolean left = randomNum((tick * 361373) + 23, 0, 100000) < 50000;
//                double n = randomNum((tick * 38472) + 92, 0, 100000);
//                if(n < 25000){
//                    Instruction_StartPathToY startPathToY = new Instruction_StartPathToY(null, finalY, speed);
//                    Instruction_Timer timer = new Instruction_Timer(null, 30);
//                    Instruction_Attack attack = new Instruction_Attack(null, "default", 200, 200);
//                    Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);
//                    Instruction_WaitPathOver wait = new Instruction_WaitPathOver(null);
//                    Instruction_SetPolar setPolar;
//                    if(left){
//                        setPolar = new Instruction_SetPolar(null, new Vector(-90, speed));
//                    }
//                    else{
//                        setPolar = new Instruction_SetPolar(null, new Vector(90, speed));
//                    }
//                    startPathToY.setNext(timer);
//                    timer.setNext(attack);
//                    attack.setNext(toggle);
//                    toggle.setNext(wait);
//                    wait.setNext(setPolar);
//                    addTo.add(new Enemy_Machine_Laser(new Vector(initX, initY), startPathToY, inithp));
//                }
//                else{
//                    String attackName;
//                    int attackDelay = 200;
//                    if(n < 50000){
//                        attackName = "default";
//                        attackDelay = 1;
//                    }
//                    else if(n < 75000){
//                        attackName = "spray";
//                    }
//                    else{
//                        attackName = "smart";
//                    }
//                    Instruction_StartPathToY startPathToY = new Instruction_StartPathToY(null, finalY, speed);
//                    Instruction_Timer timer = new Instruction_Timer(null, 30);
//                    Instruction_Attack attack = new Instruction_Attack(null, attackName, attackDelay, 200);
//                    Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);
//                    Instruction_WaitPathOver wait = new Instruction_WaitPathOver(null);
//                    Instruction_SetPolar setPolar;
//                    if(left){
//                        setPolar = new Instruction_SetPolar(null, new Vector(-90, speed));
//                    }
//                    else{
//                        setPolar = new Instruction_SetPolar(null, new Vector(90, speed));
//                    }
//                    startPathToY.setNext(timer);
//                    timer.setNext(attack);
//                    attack.setNext(toggle);
//                    toggle.setNext(wait);
//                    wait.setNext(setPolar);
//                    addTo.add(new Enemy_Machine_Spray(new Vector(initX, initY), startPathToY, inithp));
//                }
//            }

            //1
            if(tick >= 4000 && tick <= 4120){
                double spawnY = -40;
                //80 difference, mid = 425, - 160 = 265
                double spawnXLowest = 265;
                double haltYHighest = 360;

                if(tick == 4000){

//                    double haltYHighest = 340;
                    for(int i = 0; i < 5; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest;
//                        Path path1 = new Path_Polygon(new Vector(spawnX, haltY), false, 160);
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(spawnX - 200, haltY);
                        Vector v3 = new Vector(10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        a.add(v3);
                        Path path = new Path_Polygon(a, false, 160);
                        addTo.add(new Enemy_Machine_Shield(new Vector(spawnX, spawnY), path, 50));
                    }
                }
                if(tick == 4030){
//                    double haltYHighest = 365;
                    Enemy[] enemies = new Enemy[5];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[4] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 5; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 80;
//                        Path path1 = new Path_Polygon(new Vector(spawnX, haltY), false, 160);
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(spawnX - 200, haltY);
                        Vector v3 = new Vector(10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        a.add(v3);
                        Path path = new Path_Polygon(a, false, 160);
//                        addTo.add(new Enemy_Machine_Shield(new Vector(spawnX, spawnY), path, 100));
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
                if(tick == 4060){
                    Enemy[] enemies = new Enemy[5];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
//                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "spray", 15);
                    enemies[2] = null;
                    enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[4] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 5; i++) {
                        if (enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 160;
                            Vector v1 = new Vector(spawnX, haltY);
                            Vector v2 = new Vector(spawnX - 200, haltY);
                            Vector v3 = new Vector(10000, haltY);
                            ArrayList<Vector> a = new ArrayList<>();
                            a.add(v1);
                            a.add(v2);
                            a.add(v3);
                            Path path = new Path_Polygon(a, false, 160);
                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].newPath(path);
                            addTo.add(enemies[i]);
                        }
                    }
                }
                if(tick == 4090){
                    Enemy[] enemies = new Enemy[5];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[4] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 5; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 240;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(spawnX - 200, haltY);
                        Vector v3 = new Vector(10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        a.add(v3);
                        Path path = new Path_Polygon(a, false, 160);
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
                if(tick == 4120){
                    Enemy[] enemies = new Enemy[5];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                    enemies[2] = new Enemy_Machine_Bullet(new Vector(), new Vector(), "default", 15);
                    enemies[3] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                    enemies[4] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 5; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 320;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(spawnX - 200, haltY);
                        Vector v3 = new Vector(10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        a.add(v3);
                        Path path = new Path_Polygon(a, false, 160);
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
            }

            if(tick >= 5000 && tick <= 5120){
                double spawnY = -40;
                //80 difference, mid = 425, - 160 = 265
                double spawnXLowest = 265;
                double haltYHighest = 360;

                if(tick == 5000){

//                    double haltYHighest = 340;
                    for(int i = 0; i < 5; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest;
//                        Path path1 = new Path_Polygon(new Vector(spawnX, haltY), false, 160);
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(spawnX + 200, haltY);
                        Vector v3 = new Vector(-10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        a.add(v3);
                        Path path = new Path_Polygon(a, false, 160);
                        addTo.add(new Enemy_Machine_Shield(new Vector(spawnX, spawnY), path, 50));
                    }
                }
                if(tick == 5030){
//                    double haltYHighest = 365;
                    Enemy[] enemies = new Enemy[5];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[3] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                    enemies[4] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 5; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 80;
//                        Path path1 = new Path_Polygon(new Vector(spawnX, haltY), false, 160);
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(spawnX + 200, haltY);
                        Vector v3 = new Vector(-10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        a.add(v3);
                        Path path = new Path_Polygon(a, false, 160);
//                        addTo.add(new Enemy_Machine_Shield(new Vector(spawnX, spawnY), path, 100));
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
                if(tick == 5060){
                    Enemy[] enemies = new Enemy[5];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
//                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[2] = null;
                    enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[4] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 5; i++) {
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 160;
                            Vector v1 = new Vector(spawnX, haltY);
                            Vector v2 = new Vector(spawnX + 200, haltY);
                            Vector v3 = new Vector(-10000, haltY);
                            ArrayList<Vector> a = new ArrayList<>();
                            a.add(v1);
                            a.add(v2);
                            a.add(v3);
                            Path path = new Path_Polygon(a, false, 160);
                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].newPath(path);
                            addTo.add(enemies[i]);
                        }
                    }
                }
                if(tick == 5090){
                    Enemy[] enemies = new Enemy[5];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[4] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 5; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 240;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(spawnX + 200, haltY);
                        Vector v3 = new Vector(-10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        a.add(v3);
                        Path path = new Path_Polygon(a, false, 160);
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
                if(tick == 5120){
                    Enemy[] enemies = new Enemy[5];
                    enemies[0] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                    enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[2] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                    enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[4] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);

                    for(int i = 0; i < 5; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 320;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(spawnX + 200, haltY);
                        Vector v3 = new Vector(-10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        a.add(v3);
                        Path path = new Path_Polygon(a, false, 160);
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
            }

//            //2
//            if(tick == 2300){
//
//            }
//
//            //3
//            if(tick == 2600){
//
//            }
//
//            //4
//            if(tick == 2900){
//
//            }
//
//            //5
//            if(tick == 3200){
//
//            }
//
//            //6
//            if(tick == 3500){
//
//            }
        }

        //7's
        if(tick >= 6000 && tick < 8000){
            double spawnY = -40;
            //80 difference, mid = 425, - 160 = 265
            double spawnXLowest = 185;
            double haltYHighest = 500;
            if(tick == 6000){
                for(int i = 0; i < 7; i++){
                    double spawnX = spawnXLowest + (80 * i);
                    double haltY = haltYHighest;
//                        Path path1 = new Path_Polygon(new Vector(spawnX, haltY), false, 160);
                    Vector v1 = new Vector(spawnX, haltY);
                    Vector v2 = new Vector(spawnX - 160, haltY);
                    Vector v3 = new Vector(10000, haltY);
                    ArrayList<Vector> a = new ArrayList<>();
                    a.add(v1);
                    a.add(v2);
                    a.add(v3);
                    Path path = new Path_Polygon(a, false, 160);
                    addTo.add(new Enemy_Machine_Shield(new Vector(spawnX, spawnY), path, 50));
                }
            }
            if(tick == 6030){
//                Enemy[] enemies = new Enemy[7];
//                enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
//                enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
//                enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
//                enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
//                enemies[4] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
//                enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
//                enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
//
//                for(int i = 0; i < 7; i++){
//                    double spawnX = spawnXLowest + (80 * i);
//                    double haltY = haltYHighest - 80;
//                    Vector v1 = new Vector(spawnX, haltY);
//                    Vector v2 = new Vector(spawnX - 160, haltY);
//                    Vector v3 = new Vector(10000, haltY);
//                    ArrayList<Vector> a = new ArrayList<>();
//                    a.add(v1);
//                    a.add(v2);
//                    a.add(v3);
//                    Path path = new Path_Polygon(a, false, 160);
//                    enemies[i].setToThis(new Vector(spawnX, spawnY));
//                    enemies[i].newPath(path);
//                    addTo.add(enemies[i]);
//                }
                for(int i = 0; i < 7; i++){
                    double spawnX = spawnXLowest + (80 * i);
                    double haltY = haltYHighest - 80;
                    Vector v1 = new Vector(spawnX, haltY);
                    Vector v2 = new Vector(spawnX - 160, haltY);
                    Vector v3 = new Vector(10000, haltY);
                    ArrayList<Vector> a = new ArrayList<>();
                    a.add(v1);
                    a.add(v2);
                    a.add(v3);
                    Path path = new Path_Polygon(a, false, 160);
                    addTo.add(new Enemy_Machine_Shield(new Vector(spawnX, spawnY), path, 50));
                }
            }
            if(tick == 6060){
                Enemy[] enemies = new Enemy[7];
                enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[2] = null;
                enemies[3] = null;
                enemies[4] = null;
                enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                for(int i = 0; i < 7; i++){
                    if(enemies[i] != null) {
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 160;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(spawnX - 160, haltY);
                        Vector v3 = new Vector(10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        a.add(v3);
                        Path path = new Path_Polygon(a, false, 160);
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
            }
            if(tick == 6090){
                Enemy[] enemies = new Enemy[7];
                enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                enemies[2] = null;
                enemies[3] = null;
                enemies[4] = null;
                enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                for(int i = 0; i < 7; i++){
                    if(enemies[i] != null) {
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 240;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(spawnX - 160, haltY);
                        Vector v3 = new Vector(10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        a.add(v3);
                        Path path = new Path_Polygon(a, false, 160);
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
            }
            if(tick == 6120){
                Enemy[] enemies = new Enemy[7];
                enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[2] = null;
                enemies[3] = null;
                enemies[4] = null;
                enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                for(int i = 0; i < 7; i++){
                    if(enemies[i] != null) {
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 320;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(spawnX - 160, haltY);
                        Vector v3 = new Vector(10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        a.add(v3);
                        Path path = new Path_Polygon(a, false, 160);
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
            }
            if(tick == 6150){
                Enemy[] enemies = new Enemy[7];
                enemies[0] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[3] = new Enemy_Machine_Bullet(new Vector(), new Vector(), "default", 15);
                enemies[4] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                enemies[6] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);

                for(int i = 0; i < 7; i++){
                    double spawnX = spawnXLowest + (80 * i);
                    double haltY = haltYHighest - 400;
                    Vector v1 = new Vector(spawnX, haltY);
                    Vector v2 = new Vector(spawnX - 160, haltY);
                    Vector v3 = new Vector(10000, haltY);
                    ArrayList<Vector> a = new ArrayList<>();
                    a.add(v1);
                    a.add(v2);
                    a.add(v3);
                    Path path = new Path_Polygon(a, false, 160);
                    enemies[i].setToThis(new Vector(spawnX, spawnY));
                    enemies[i].newPath(path);
                    addTo.add(enemies[i]);
                }
            }
            if(tick == 6180){
                for(int i = 0; i < 7; i++){
                    double spawnX = spawnXLowest + (80 * i);
                    double haltY = haltYHighest - 480;
//                        Path path1 = new Path_Polygon(new Vector(spawnX, haltY), false, 160);
                    Vector v1 = new Vector(spawnX, haltY);
                    Vector v2 = new Vector(spawnX - 160, haltY);
                    Vector v3 = new Vector(10000, haltY);
                    ArrayList<Vector> a = new ArrayList<>();
                    a.add(v1);
                    a.add(v2);
                    a.add(v3);
                    Path path = new Path_Polygon(a, false, 160);
                    addTo.add(new Enemy_Machine_Spray(new Vector(spawnX, spawnY), path, "smart", 15));
                }
            }


            if(tick == 7000){
                for(int i = 0; i < 7; i++){
                    double spawnX = spawnXLowest + (80 * i);
                    double haltY = haltYHighest;
//                        Path path1 = new Path_Polygon(new Vector(spawnX, haltY), false, 160);
                    Vector v1 = new Vector(spawnX, haltY);
                    Vector v2 = new Vector(spawnX + 160, haltY);
                    Vector v3 = new Vector(-10000, haltY);
                    ArrayList<Vector> a = new ArrayList<>();
                    a.add(v1);
                    a.add(v2);
                    a.add(v3);
                    Path path = new Path_Polygon(a, false, 160);
                    addTo.add(new Enemy_Machine_Shield(new Vector(spawnX, spawnY), path, 50));
                }
            }
            if(tick == 7030){
//                Enemy[] enemies = new Enemy[7];
//                enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
//                enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
//                enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
//                enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
//                enemies[4] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
//                enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
//                enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
//
//                for(int i = 0; i < 7; i++){
//                    double spawnX = spawnXLowest + (80 * i);
//                    double haltY = haltYHighest - 80;
//                    Vector v1 = new Vector(spawnX, haltY);
//                    Vector v2 = new Vector(spawnX - 160, haltY);
//                    Vector v3 = new Vector(10000, haltY);
//                    ArrayList<Vector> a = new ArrayList<>();
//                    a.add(v1);
//                    a.add(v2);
//                    a.add(v3);
//                    Path path = new Path_Polygon(a, false, 160);
//                    enemies[i].setToThis(new Vector(spawnX, spawnY));
//                    enemies[i].newPath(path);
//                    addTo.add(enemies[i]);
//                }
                for(int i = 0; i < 7; i++){
                    double spawnX = spawnXLowest + (80 * i);
                    double haltY = haltYHighest - 80;
                    Vector v1 = new Vector(spawnX, haltY);
                    Vector v2 = new Vector(spawnX + 160, haltY);
                    Vector v3 = new Vector(-10000, haltY);
                    ArrayList<Vector> a = new ArrayList<>();
                    a.add(v1);
                    a.add(v2);
                    a.add(v3);
                    Path path = new Path_Polygon(a, false, 160);
                    addTo.add(new Enemy_Machine_Shield(new Vector(spawnX, spawnY), path, 50));
                }
            }
            if(tick == 7060){
                Enemy[] enemies = new Enemy[7];
                enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[2] = null;
                enemies[3] = null;
                enemies[4] = null;
                enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                for(int i = 0; i < 7; i++){
                    if(enemies[i] != null) {
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 160;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(spawnX + 160, haltY);
                        Vector v3 = new Vector(-10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        a.add(v3);
                        Path path = new Path_Polygon(a, false, 160);
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
            }
            if(tick == 7090){
                Enemy[] enemies = new Enemy[7];
                enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                enemies[2] = null;
                enemies[3] = null;
                enemies[4] = null;
                enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                for(int i = 0; i < 7; i++){
                    if(enemies[i] != null) {
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 240;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(spawnX + 160, haltY);
                        Vector v3 = new Vector(-10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        a.add(v3);
                        Path path = new Path_Polygon(a, false, 160);
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
            }
            if(tick == 7120){
                Enemy[] enemies = new Enemy[7];
                enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[2] = null;
                enemies[3] = null;
                enemies[4] = null;
                enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                for(int i = 0; i < 7; i++){
                    if(enemies[i] != null) {
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 320;
                        Vector v1 = new Vector(spawnX, haltY);
                        Vector v2 = new Vector(spawnX + 160, haltY);
                        Vector v3 = new Vector(-10000, haltY);
                        ArrayList<Vector> a = new ArrayList<>();
                        a.add(v1);
                        a.add(v2);
                        a.add(v3);
                        Path path = new Path_Polygon(a, false, 160);
                        enemies[i].setToThis(new Vector(spawnX, spawnY));
                        enemies[i].newPath(path);
                        addTo.add(enemies[i]);
                    }
                }
            }
            if(tick == 7150){
                Enemy[] enemies = new Enemy[7];
                enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                enemies[4] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                for(int i = 0; i < 7; i++){
                    double spawnX = spawnXLowest + (80 * i);
                    double haltY = haltYHighest - 400;
                    Vector v1 = new Vector(spawnX, haltY);
                    Vector v2 = new Vector(spawnX + 160, haltY);
                    Vector v3 = new Vector(-10000, haltY);
                    ArrayList<Vector> a = new ArrayList<>();
                    a.add(v1);
                    a.add(v2);
                    a.add(v3);
                    Path path = new Path_Polygon(a, false, 160);
                    enemies[i].setToThis(new Vector(spawnX, spawnY));
                    enemies[i].newPath(path);
                    addTo.add(enemies[i]);
                }
            }
            if(tick == 7180){
                Enemy[] enemies = new Enemy[7];
                enemies[0] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "spray", 15);
                enemies[4] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                enemies[6] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);

                for(int i = 0; i < 7; i++){
                    double spawnX = spawnXLowest + (80 * i);
                    double haltY = haltYHighest - 480;
                    Vector v1 = new Vector(spawnX, haltY);
                    Vector v2 = new Vector(spawnX + 160, haltY);
                    Vector v3 = new Vector(-10000, haltY);
                    ArrayList<Vector> a = new ArrayList<>();
                    a.add(v1);
                    a.add(v2);
                    a.add(v3);
                    Path path = new Path_Polygon(a, false, 160);
                    enemies[i].setToThis(new Vector(spawnX, spawnY));
                    enemies[i].newPath(path);
                    addTo.add(enemies[i]);
                }
            }
        }

//        //9's
        if(tick >= 8000 && tick < 12000){
            double spawnY = -40;
            //80 difference, mid = 425, - 160 = 265
            double spawnXLowest = 105;
            double haltYHighest = 660;
            if(tick >= 8000 && tick <= 8240){
                if(tick == 8000){
                    int lor = 1;
                    for(int i = 0; i < 9; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest;
//                        Path path1 = new Path_Polygon(new Vector(spawnX, haltY), false, 160);
//                        Vector haltPos = new Vector(spawnX, haltY);
                        Vector endPolar = new Vector(90 * lor, 60);
                        int time = 9000 - tick;

//                        Instruction_StartPathPath path = new Path_Polygon(haltPos, false, 160);
                        Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                        Instruction_Timer timer = new Instruction_Timer(null, time);
                        Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                        spty.setNext(timer);
                        timer.setNext(setPolar);

                        addTo.add(new Enemy_Machine_Shield(new Vector(spawnX, spawnY), spty, 50));
                    }
                }
                if(tick == 8030){
                    int lor = -1;
                    Enemy[] enemies = new Enemy[9];
                    enemies[0] = new Enemy_Machine_Shield(new Vector(), new Vector(), 50);
                    enemies[1] = null;
                    enemies[2] = new Enemy_Machine_Shield(new Vector(), new Vector(), 50);
                    enemies[3] = new Enemy_Machine_Shield(new Vector(), new Vector(), 50);
                    enemies[4] = null;
                    enemies[5] = new Enemy_Machine_Shield(new Vector(), new Vector(), 50);
                    enemies[6] = new Enemy_Machine_Shield(new Vector(), new Vector(), 50);
                    enemies[7] = null;
                    enemies[8] = new Enemy_Machine_Shield(new Vector(), new Vector(), 50);

                    for(int i = 0; i < 9; i++){
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 80;
                            Vector endPolar = new Vector(90 * lor, 60);
                            int time = 9000 - tick;

                            Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                            Instruction_Timer timer = new Instruction_Timer(null, time);
                            Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                            spty.setNext(timer);
                            timer.setNext(setPolar);

                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].setNewBehavior(spty);
                            addTo.add(enemies[i]);
                        }
                    }
                }
                if(tick == 8060){
                    int lor = 1;
                    for(int i = 0; i < 9; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 160;
                        Vector endPolar = new Vector(90 * lor, 60);
                        int time = 9000 - tick;

                        Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                        Instruction_Timer timer = new Instruction_Timer(null, time);
                        Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                        spty.setNext(timer);
                        timer.setNext(setPolar);

                        addTo.add(new Enemy_Machine_Shield(new Vector(spawnX, spawnY), spty, 50));
                    }
                }
                if(tick == 8090){
                    int lor = -1;
                    Enemy[] enemies = new Enemy[9];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[3] = null;
                    enemies[4] = null;
                    enemies[5] = null;
                    enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[7] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[8] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 9; i++){
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 240;
                            Vector endPolar = new Vector(90 * lor, 60);
                            int time = 9000 - tick;

                            Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                            Instruction_Timer timer = new Instruction_Timer(null, time);
                            Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                            spty.setNext(timer);
                            timer.setNext(setPolar);

                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].setNewBehavior(spty);
                            addTo.add(enemies[i]);
                        }
                    }
                }
                if(tick == 8120){
                    int lor = 1;
                    Enemy[] enemies = new Enemy[9];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = null;
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[3] = null;
                    enemies[4] = null;
                    enemies[5] = null;
                    enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[7] = null;
                    enemies[8] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 9; i++){
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 320;
                            Vector endPolar = new Vector(90 * lor, 60);
                            int time = 9000 - tick;

                            Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                            Instruction_Timer timer = new Instruction_Timer(null, time);
                            Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                            spty.setNext(timer);
                            timer.setNext(setPolar);

                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].setNewBehavior(spty);
                            addTo.add(enemies[i]);
                        }
                    }
                }
                if(tick == 8150){
                    int lor = -1;
                    Enemy[] enemies = new Enemy[9];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[3] = null;
                    enemies[4] = null;
                    enemies[5] = null;
                    enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[7] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[8] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 9; i++){
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 400;
                            Vector endPolar = new Vector(90 * lor, 60);
                            int time = 9000 - tick;

                            Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                            Instruction_Timer timer = new Instruction_Timer(null, time);
                            Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                            spty.setNext(timer);
                            timer.setNext(setPolar);

                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].setNewBehavior(spty);
                            addTo.add(enemies[i]);
                        }
                    }
                }
                if(tick == 8180){
                    int lor = 1;
                    Enemy[] enemies = new Enemy[9];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[4] = new Enemy_Machine_Spray(new Vector(), new Vector(), "spray", 15);
                    enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[7] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[8] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 9; i++){
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 480;
                            Vector endPolar = new Vector(90 * lor, 60);
                            int time = 9000 - tick;

                            Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                            Instruction_Timer timer = new Instruction_Timer(null, time);
                            Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                            spty.setNext(timer);
                            timer.setNext(setPolar);

                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].setNewBehavior(spty);
                            addTo.add(enemies[i]);
                        }
                    }
                }
                if(tick == 8210){
                    int lor = -1;
                    Enemy[] enemies = new Enemy[9];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = null;
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[4] = null;
                    enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[7] = null;
                    enemies[8] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 9; i++){
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 560;
                            Vector endPolar = new Vector(90 * lor, 60);
                            int time = 9000 - tick;

                            Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                            Instruction_Timer timer = new Instruction_Timer(null, time);
                            Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                            spty.setNext(timer);
                            timer.setNext(setPolar);

                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].setNewBehavior(spty);
                            addTo.add(enemies[i]);
                        }
                    }
                }
                if(tick == 8240){
                    int lor = 1;
                    Enemy[] enemies = new Enemy[9];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[4] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                    enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[7] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                    enemies[8] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 9; i++){
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 640;
                            Vector endPolar = new Vector(90 * lor, 60);
                            int time = 9000 - tick;

                            Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                            Instruction_Timer timer = new Instruction_Timer(null, time);
                            Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                            spty.setNext(timer);
                            timer.setNext(setPolar);

                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].setNewBehavior(spty);
                            addTo.add(enemies[i]);
                        }
                    }
                }
            }
            if(tick >= 10000 && tick <= 10240){
                if(tick == 10000){
                    int lor = -1;
                    for(int i = 0; i < 9; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest;
//                        Path path1 = new Path_Polygon(new Vector(spawnX, haltY), false, 160);
//                        Vector haltPos = new Vector(spawnX, haltY);
                        Vector endPolar = new Vector(90 * lor, 60);
                        int time = 11000 - tick;

//                        Instruction_StartPathPath path = new Path_Polygon(haltPos, false, 160);
                        Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                        Instruction_Timer timer = new Instruction_Timer(null, time);
                        Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                        spty.setNext(timer);
                        timer.setNext(setPolar);

                        addTo.add(new Enemy_Machine_Shield(new Vector(spawnX, spawnY), spty, 50));
                    }
                }
                if(tick == 10030){
                    int lor = 1;
                    Enemy[] enemies = new Enemy[9];
                    enemies[0] = new Enemy_Machine_Shield(new Vector(), new Vector(), 50);
                    enemies[1] = null;
                    enemies[2] = new Enemy_Machine_Shield(new Vector(), new Vector(), 50);
                    enemies[3] = new Enemy_Machine_Shield(new Vector(), new Vector(), 50);
                    enemies[4] = null;
                    enemies[5] = new Enemy_Machine_Shield(new Vector(), new Vector(), 50);
                    enemies[6] = new Enemy_Machine_Shield(new Vector(), new Vector(), 50);
                    enemies[7] = null;
                    enemies[8] = new Enemy_Machine_Shield(new Vector(), new Vector(), 50);

                    for(int i = 0; i < 9; i++){
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 80;
                            Vector endPolar = new Vector(90 * lor, 60);
                            int time = 11000 - tick;

                            Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                            Instruction_Timer timer = new Instruction_Timer(null, time);
                            Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                            spty.setNext(timer);
                            timer.setNext(setPolar);

                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].setNewBehavior(spty);
                            addTo.add(enemies[i]);
                        }
                    }
                }
                if(tick == 10060){
                    int lor = -1;
                    for(int i = 0; i < 9; i++){
                        double spawnX = spawnXLowest + (80 * i);
                        double haltY = haltYHighest - 160;
                        Vector endPolar = new Vector(90 * lor, 60);
                        int time = 11000 - tick;

                        Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                        Instruction_Timer timer = new Instruction_Timer(null, time);
                        Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                        spty.setNext(timer);
                        timer.setNext(setPolar);

                        addTo.add(new Enemy_Machine_Shield(new Vector(spawnX, spawnY), spty, 50));
                    }
                }
                if(tick == 10090){
                    int lor = 1;
                    Enemy[] enemies = new Enemy[9];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[3] = null;
                    enemies[4] = null;
                    enemies[5] = null;
                    enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[7] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[8] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 9; i++){
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 240;
                            Vector endPolar = new Vector(90 * lor, 60);
                            int time = 11000 - tick;

                            Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                            Instruction_Timer timer = new Instruction_Timer(null, time);
                            Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                            spty.setNext(timer);
                            timer.setNext(setPolar);

                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].setNewBehavior(spty);
                            addTo.add(enemies[i]);
                        }
                    }
                }
                if(tick == 10120){
                    int lor = -1;
                    Enemy[] enemies = new Enemy[9];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[1] = null;
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[3] = null;
                    enemies[4] = null;
                    enemies[5] = null;
                    enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[7] = null;
                    enemies[8] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);

                    for(int i = 0; i < 9; i++){
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 320;
                            Vector endPolar = new Vector(90 * lor, 60);
                            int time = 11000 - tick;

                            Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                            Instruction_Timer timer = new Instruction_Timer(null, time);
                            Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                            spty.setNext(timer);
                            timer.setNext(setPolar);

                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].setNewBehavior(spty);
                            addTo.add(enemies[i]);
                        }
                    }
                }
                if(tick == 10150){
                    int lor = 1;
                    Enemy[] enemies = new Enemy[9];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[3] = null;
                    enemies[4] = null;
                    enemies[5] = null;
                    enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[7] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[8] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 9; i++){
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 400;
                            Vector endPolar = new Vector(90 * lor, 60);
                            int time = 11000 - tick;

                            Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                            Instruction_Timer timer = new Instruction_Timer(null, time);
                            Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                            spty.setNext(timer);
                            timer.setNext(setPolar);

                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].setNewBehavior(spty);
                            addTo.add(enemies[i]);
                        }
                    }
                }
                if(tick == 10180){
                    int lor = -1;
                    Enemy[] enemies = new Enemy[9];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[1] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[4] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                    enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[7] = new Enemy_Machine_Laser(new Vector(), new Vector(), "default", 15);
                    enemies[8] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);

                    for(int i = 0; i < 9; i++){
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 480;
                            Vector endPolar = new Vector(90 * lor, 60);
                            int time = 11000 - tick;

                            Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                            Instruction_Timer timer = new Instruction_Timer(null, time);
                            Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                            spty.setNext(timer);
                            timer.setNext(setPolar);

                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].setNewBehavior(spty);
                            addTo.add(enemies[i]);
                        }
                    }
                }
                if(tick == 10210){
                    int lor = 1;
                    Enemy[] enemies = new Enemy[9];
                    enemies[0] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[1] = null;
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[4] = null;
                    enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[7] = null;
                    enemies[8] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);

                    for(int i = 0; i < 9; i++){
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 560;
                            Vector endPolar = new Vector(90 * lor, 60);
                            int time = 11000 - tick;

                            Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                            Instruction_Timer timer = new Instruction_Timer(null, time);
                            Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                            spty.setNext(timer);
                            timer.setNext(setPolar);

                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].setNewBehavior(spty);
                            addTo.add(enemies[i]);
                        }
                    }
                }
                if(tick == 10240){
                    int lor = -1;
                    Enemy[] enemies = new Enemy[9];
                    enemies[0] = new Enemy_Machine_Bullet(new Vector(), new Vector(), "shotgun", 15);
                    enemies[1] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[2] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[3] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[4] = new Enemy_Machine_Spray(new Vector(), new Vector(), "spray", 15);
                    enemies[5] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[6] = new Enemy_Machine_Spray(new Vector(), new Vector(), "default", 15);
                    enemies[7] = new Enemy_Machine_Spray(new Vector(), new Vector(), "smart", 15);
                    enemies[8] = new Enemy_Machine_Bullet(new Vector(), new Vector(), "shotgun", 15);

                    for(int i = 0; i < 9; i++){
                        if(enemies[i] != null) {
                            double spawnX = spawnXLowest + (80 * i);
                            double haltY = haltYHighest - 640;
                            Vector endPolar = new Vector(90 * lor, 60);
                            int time = 11000 - tick;

                            Instruction_StartPathToY spty = new Instruction_StartPathToY(null, haltY, 160);
                            Instruction_Timer timer = new Instruction_Timer(null, time);
                            Instruction_SetPolar setPolar = new Instruction_SetPolar(null, endPolar);
                            spty.setNext(timer);
                            timer.setNext(setPolar);

                            enemies[i].setToThis(new Vector(spawnX, spawnY));
                            enemies[i].setNewBehavior(spty);
                            addTo.add(enemies[i]);
                        }
                    }
                }
            }
        }
//        if(tick >= 4000 && tick < 6000){
//
//        }

        //12k
        if(tick == 12000){
            addTo.add(new Enemy_Boss_Pax());
        }


    }
    private static void addWave6(int tick, ArrayList<Enemy> addTo){
//        switch(tick){
//            case 0:
//                addTo.add(new Enemy_Boss_Lilith());
//                break;
//        }
        //6k

        //START
        if(tick >= 300 && tick < 1000){
            if(tick % 60 == 0){
                Vector spawnPos = new Vector(-40, 200);
                Vector polar = new Vector(90, 300);
                addTo.add(new Enemy_Wisp(spawnPos, polar, "default", 10));
            }
        }
        if(tick >= 1200 && tick < 2000){
            if(tick == 1200){
                double[] spawnX = {180, 330, 520, 670};
                for(double x : spawnX){
                    Vector spawnPos = new Vector(x, -40);
//                    Vector stayPos = new Vector(x, 100);
//                    Path path = new Path_Polygon(stayPos, false, 200);
//
//                    Instruction_StartPath
                    Instruction_StartPathToY pathToY = new Instruction_StartPathToY(null, 100, 200);
                    Instruction_WaitPathOver waitPathOver = new Instruction_WaitPathOver(null);
                    Instruction_Attack attack = new Instruction_Attack(null, "sustain", 1, 200);
                    Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);
                    Instruction_Timer timer = new Instruction_Timer(null, 550);
                    Instruction_ToggleAttack end = new Instruction_ToggleAttack(null, false);
                    Instruction_StartPathToY endpath = new Instruction_StartPathToY(null, -100, 200);

                    pathToY.setNext(waitPathOver);
                    waitPathOver.setNext(attack);
                    attack.setNext(toggle);
                    toggle.setNext(timer);
                    timer.setNext(end);
                    end.setNext(endpath);

//                    Enemy_Machine_Laser toAdd = new Enemy_Machine_Laser(spawnPos, pathToY, 100);
                    addTo.add(new Enemy_Machine_Laser(spawnPos, pathToY, 130));
                }
            }
            if(tick == 1260){
                double initX = Driver_Game.getWidth() / 2;
                addTo.add(new Enemy_Machine_Bullet(new Vector(initX, -40),
                        new Routine_SingleAttack(null, new Vector(initX, 200), new Vector(initX, -100),
                                200, 200, "default", 1, 200), 20));
            }
            if(tick >= 1320 && tick < 1900 && tick % 30 == 0){
                double x = randomNum(tick * 1200, 50, 800);
                double y1 = -10;
                double y2 = 100;
                double y3 = -100;
                double speed = 200;

                String attackName;
                if(tick % 60 == 0){
                    attackName = "homingShot";
                }
                else{
                    attackName = "homingBullets";
                }

                Vector spawnPos = new Vector(x, y1);

                Instruction_StartPathToY initPath = new Instruction_StartPathToY(null, y2, speed);
                Instruction_WaitPathOver waitPathOver = new Instruction_WaitPathOver(null);
                Instruction_Attack attack = new Instruction_Attack(null, attackName, 1, 200);
                Instruction_WaitAttackOver waitAttackOver = new Instruction_WaitAttackOver(null);
                Instruction_StartPathToY endPath = new Instruction_StartPathToY(null, y3, speed);

                initPath.setNext(waitPathOver);
                waitPathOver.setNext(attack);
                attack.setNext(waitAttackOver);
                waitAttackOver.setNext(endPath);

                addTo.add(new Enemy_Spider(spawnPos, initPath, 15));
            }
        }
        if(tick >= 2200 && tick <= 3000){
            if(tick == 2200){
                double[] spawnX = {100, 750};
                for(double x : spawnX){
                    Vector spawnPos = new Vector(x, -40);

                    Instruction_StartPathToY pathToY = new Instruction_StartPathToY(null, 100, 200);
                    Instruction_WaitPathOver waitPathOver = new Instruction_WaitPathOver(null);
                    Instruction_Attack attack = new Instruction_Attack(null, "spiral", 200, 200);
                    Instruction_ToggleAttack toggle = new Instruction_ToggleAttack(null, true);
                    Instruction_Timer timer = new Instruction_Timer(null, 550);
                    Instruction_ToggleAttack end = new Instruction_ToggleAttack(null, false);
                    Instruction_StartPathToY endpath = new Instruction_StartPathToY(null, -100, 200);

                    pathToY.setNext(waitPathOver);
                    waitPathOver.setNext(attack);
                    attack.setNext(toggle);
                    toggle.setNext(timer);
                    timer.setNext(end);
                    end.setNext(endpath);

                    addTo.add(new Enemy_Wisp(spawnPos, pathToY, 100));
                }
            }

            if(tick >= 2320 && tick < 2900 && tick % 30 == 0){
                double x = randomNum(tick * 1200, 50, 800);
                double y1 = -10;
                double y2 = 100;
                double y3 = -100;
                double speed = 200;

                String attackName = "sensingShot";

                Vector spawnPos = new Vector(x, y1);

                Instruction_StartPathToY initPath = new Instruction_StartPathToY(null, y2, speed);
                Instruction_WaitPathOver waitPathOver = new Instruction_WaitPathOver(null);
                Instruction_Attack attack = new Instruction_Attack(null, attackName, 1, 200);
                Instruction_WaitAttackOver waitAttackOver = new Instruction_WaitAttackOver(null);
                Instruction_StartPathToY endPath = new Instruction_StartPathToY(null, y3, speed);

                initPath.setNext(waitPathOver);
                waitPathOver.setNext(attack);
                attack.setNext(waitAttackOver);
                waitAttackOver.setNext(endPath);

                addTo.add(new Enemy_Spider(spawnPos, initPath, 15));
            }
        }
        if(tick >= 3200 && tick <= 4000){
            if(tick % 400 == 0){
                for (int initX = 107; initX <= 850; initX += 107) {
                    addTo.add(new Enemy_Wisp(new Vector(initX, -40),
                            new Routine_SingleAttack(null, new Vector(initX, 100), new Vector(initX, -100),
                                    200, 200, "spread", 200, 200), 10));
                }
            }

            if((tick + 50) % 100 == 0 && tick <= 3800){
                double dist = randomNum(tick * 28384, 50, 375);
                double[] x = {425 - dist, 425 + dist};
                for(double d : x){
                    addTo.add(new Enemy_Machine_Spray(new Vector(d, -40),
                            new Routine_SingleAttack(null, new Vector(d, 75), new Vector(d, -100),
                                    200, 200, "default", 1, 200), 15));
                }
            }

            if((tick + 25) %50 == 0 && tick >= 3400 && tick <= 3800){
                double dist = randomNum(tick * 28384, 50, 375);
                double[] x = {425 - dist, 425 + dist};
                for(double d : x){
                    addTo.add(new Enemy_Spider(new Vector(d, -40),
                            new Routine_SingleAttack(null, new Vector(d, 50), new Vector(d, -100),
                                    200, 200, "default", 1, 200), 15));
                }
            }
        }
        //END

        if(tick == 6000){//6k
            addTo.add(new Enemy_Boss_Lilith());
        }
    }
    private static void addWaveEX(int tick, ArrayList<Enemy> addTo){
//        switch(tick){
//            case 0:
//                addTo.add(new Enemy_Boss_Zephres());
//                break;
//        }
        //zephres @ 14k
        if(tick == 14000){
            addTo.add(new Enemy_Boss_Zephres());
        }

        //START

        if(tick == 250){
            double initX = Driver_Game.getWidth()/2;
            double xl = initX-100;
            double xr = initX+100;

            Instruction_StartPathToY ip = new Instruction_StartPathToY(null, 50, 200);
            Instruction_WaitPathOver wp1 = new Instruction_WaitPathOver(null);
            Instruction_Attack a = new Instruction_Attack(null, "laserL", 1, 300);
//            Instruction_WaitAttackOver wa = new Instruction_WaitAttackOver(null);
            Instruction_Timer t = new Instruction_Timer(null, 300);
            Instruction_StartPathToY end = new Instruction_StartPathToY(null, -100, 200);

            ip.setNext(wp1);
            wp1.setNext(a);
            a.setNext(t);
            t.setNext(end);

            addTo.add(new Enemy_Ember(new Vector(xl, -40), ip, 7));

            ip = new Instruction_StartPathToY(null, 50, 200);
            wp1 = new Instruction_WaitPathOver(null);
            a = new Instruction_Attack(null, "laserR", 1, 300);
            t = new Instruction_Timer(null, 300);
            end = new Instruction_StartPathToY(null, -100, 200);

            ip.setNext(wp1);
            wp1.setNext(a);
            a.setNext(t);
            t.setNext(end);

            addTo.add(new Enemy_Ember(new Vector(xr, -40), ip, 7));
        }
//        if(tick == 0){
//            addTo.add(new Enemy_Ember(new Vector(500, 200), new Vector(), "convectionShot"));
//        }
        if(tick >= 600 && tick <= 1200){
            if(tick % 40 == 0){
                Vector spawnPos = new Vector(350, -40);
                addTo.add(new Enemy_Wisp(spawnPos, new Vector(-25, 300), "default", 1));
            }
        }
        if(tick >= 1320 && tick <= 1920){
            if(tick % 40 == 0){
                Vector spawnPos = new Vector(500, -40);
                addTo.add(new Enemy_Wisp(spawnPos, new Vector(25, 300), "default", 1));
            }
        }
        if(tick >= 2000 && tick <= 2400){
            if(tick % 20 == 0){
                Vector spawnPos = new Vector(40 + (Math.random() * 770), -40);
                addTo.add(new Enemy_Wisp(spawnPos, new Vector(0, 300), "backShot", 1));
            }
        }
        if(tick == 2430){
            Instruction_StartPathToY ip = new Instruction_StartPathToY(null, 150, 200);
            Instruction_WaitPathOver wp1 = new Instruction_WaitPathOver(null);
            Instruction_Attack a = new Instruction_Attack(null, "convectionShot", 300, 300);
            Instruction_Timer t = new Instruction_Timer(null, 500);
            Instruction_StartPathToY end = new Instruction_StartPathToY(null, -100, 200);

            ip.setNext(wp1);
            wp1.setNext(a);
            a.setNext(t);
            t.setNext(end);

            addTo.add(new Enemy_Ember(new Vector(425, -40), ip, 40));

            ip = new Instruction_StartPathToY(null, 50, 200);
            wp1 = new Instruction_WaitPathOver(null);
            a = new Instruction_Attack(null, "fireStorm", 1, 300);
            t = new Instruction_Timer(null, 500);
            end = new Instruction_StartPathToY(null, -100, 200);

            ip.setNext(wp1);
            wp1.setNext(a);
            a.setNext(t);
            t.setNext(end);

            addTo.add(new Enemy_Ember(new Vector(200, -40), ip, 20));

            ip = new Instruction_StartPathToY(null, 50, 200);
            wp1 = new Instruction_WaitPathOver(null);
            a = new Instruction_Attack(null, "fireStorm", 1, 300);
            t = new Instruction_Timer(null, 500);
            end = new Instruction_StartPathToY(null, -100, 200);

            ip.setNext(wp1);
            wp1.setNext(a);
            a.setNext(t);
            t.setNext(end);

            addTo.add(new Enemy_Ember(new Vector(650, -40), ip, 20));
        }

        if(tick >= 3200 && tick <= 3800){
            if((tick - 3200) % 30 == 0){
                Vector spawnPos = new Vector(425, -40);
                Vector polar = new Vector(0, 300);
                addTo.add(new Enemy_Wisp(spawnPos, polar, "default", 2));
            }
        }
        if(tick >= 4000 && tick <= 4600){
            if((tick - 4000) % 60 == 0){
                double dist = Driver_Game.getWidth()/22;
                //1 - 11
                int index = ((tick - 4000)/60) + 1;
                double initX1 = dist * index;
                double initX2 = 850 - initX1;

                Instruction_StartPathToY ip = new Instruction_StartPathToY(null, 100, 200);
                Instruction_WaitPathOver wp = new Instruction_WaitPathOver(null);
                Instruction_Timer t1 = new Instruction_Timer(null, 40);
                Instruction_SetPolar sp = new Instruction_SetPolar(null, new Vector(45, 300));

                ip.setNext(wp);
                wp.setNext(t1);
                t1.setNext(sp);

                Enemy_Wisp left = new Enemy_Wisp(new Vector(initX1, -40), new Vector(), "default", 2);
                left.setNewBehavior(ip);
                addTo.add(left);

                ip = new Instruction_StartPathToY(null, 100, 200);
                wp = new Instruction_WaitPathOver(null);
                t1 = new Instruction_Timer(null, 40);
                sp = new Instruction_SetPolar(null, new Vector(-45, 300));

                ip.setNext(wp);
                wp.setNext(t1);
                t1.setNext(sp);

                Enemy_Wisp right = new Enemy_Wisp(new Vector(initX2, -40), new Vector(), "default", 2);
                right.setNewBehavior(ip);
                addTo.add(right);
            }
        }

        if(tick >= 5000 && tick <= 6200){
            //top half
            if(tick % 60 == 0){
                Vector spawnPos = new Vector(50 + (Math.random() * 750), -40);
                Vector midPos = new Vector(50 + (Math.random() * 750), 0);
                String attackName;
                if(Math.random() < .5){
                    midPos.setB(100 + (Math.random() * 100));
                    attackName = "default";
                }
                else{
                    midPos.setB(200 + (Math.random() * 100));
                    attackName = "volcano";
                }
                Vector endPolar = new Vector(90 + (Math.random() * 180), 200 + (Math.random() * 100));
                double initSpeed = 200 + (Math.random() * 100);

                Instruction_StartPath ip = new Instruction_StartPath(null, new Path_Polygon(midPos, false, initSpeed));
                Instruction_WaitPathOver wp = new Instruction_WaitPathOver(null);
                Instruction_Attack a = new Instruction_Attack(null, attackName, 1, 1);
                Instruction_Timer t = new Instruction_Timer(null, 60);
                Instruction_SetPolar sp = new Instruction_SetPolar(null, endPolar);

                ip.setNext(wp);
                wp.setNext(a);
                a.setNext(t);
                t.setNext(sp);

                addTo.add(new Enemy_Ember(spawnPos, ip, 10));
            }
            //bot half
            if(tick % 240 == 0){
                boolean left = tick%480 == 0;

                Vector spawnPos = new Vector(890, 500 + (Math.random() * 450));
                Vector aPos;
                Vector endPolar;
                if(left){
                    spawnPos.setA(-40);
                    aPos = Vector.add(spawnPos, new Vector(90, 0));
                    endPolar = new Vector(-90, 300);
                }
                else{
                    aPos = Vector.add(spawnPos, new Vector(-90, 0));
                    endPolar = new Vector(90, 300);
                }
                double initSpeed = 300;
                Instruction_StartPath ip = new Instruction_StartPath(null, new Path_Polygon(aPos, false, initSpeed));
                Instruction_WaitPathOver wp = new Instruction_WaitPathOver(null);
                Instruction_Attack a = new Instruction_Attack(null, "spread", 200, 200);
                Instruction_Timer t = new Instruction_Timer(null, 60);
                Instruction_SetPolar sp = new Instruction_SetPolar(null, endPolar);

                ip.setNext(wp);
                wp.setNext(a);
                a.setNext(t);
                t.setNext(sp);

                addTo.add(new Enemy_Wisp(spawnPos, ip, 20));
            }
        }

        if(tick >= 6800 && tick <= 8000){
            //top half
            if(tick % 120 == 0){
                Vector spawnPos = new Vector(50 + (Math.random() * 750), -40);
                Vector midPos = new Vector(50 + (Math.random() * 750), 100 + (Math.random() * 200));
                String attackName;
                if(Math.random() < .5){
                    attackName = "fireOrbit";
                }
                else{
                    attackName = "directionalFirestorm";
                }
                Vector endPolar = new Vector(90 + (Math.random() * 180), 200 + (Math.random() * 100));
                double initSpeed = 200 + (Math.random() * 100);

                Instruction_StartPath ip = new Instruction_StartPath(null, new Path_Polygon(midPos, false, initSpeed));
                Instruction_WaitPathOver wp = new Instruction_WaitPathOver(null);
                Instruction_Attack a = new Instruction_Attack(null, attackName, 300, 300);
                Instruction_Timer t = new Instruction_Timer(null, 330);
                Instruction_SetPolar sp = new Instruction_SetPolar(null, endPolar);

                ip.setNext(wp);
                wp.setNext(a);
                a.setNext(t);
                t.setNext(sp);

                addTo.add(new Enemy_Ember(spawnPos, ip, 25));
            }
            //bot half
            if(tick % 240 == 0){
                boolean left = tick%480 == 0;

                Vector spawnPos = new Vector(890, 500 + (Math.random() * 450));
                Vector aPos;
                Vector endPolar;
                if(left){
                    spawnPos.setA(-40);
                    aPos = Vector.add(spawnPos, new Vector(90, 0));
                    endPolar = new Vector(-90, 300);
                }
                else{
                    aPos = Vector.add(spawnPos, new Vector(-90, 0));
                    endPolar = new Vector(90, 300);
                }
                double initSpeed = 300;
                Instruction_StartPath ip = new Instruction_StartPath(null, new Path_Polygon(aPos, false, initSpeed));
                Instruction_WaitPathOver wp = new Instruction_WaitPathOver(null);
                Instruction_Attack a = new Instruction_Attack(null, "spread", 200, 200);
                Instruction_Timer t = new Instruction_Timer(null, 60);
                Instruction_SetPolar sp = new Instruction_SetPolar(null, endPolar);

                ip.setNext(wp);
                wp.setNext(a);
                a.setNext(t);
                t.setNext(sp);

                addTo.add(new Enemy_Wisp(spawnPos, ip, 20));
            }
        }

        if(tick >= 8400 && tick <= 9200){
            if((tick - 8400) % 150 == 0){
                for(int i = 0; i < 2; i++){
                    double initX = 50 + (Math.random() * 750);
                    addTo.add(new Enemy_Ember(new Vector(initX, -40),
                            new Routine_SingleAttack(null, new Vector(initX, 100), new Vector(initX, -100),
                                    200, 200, "default", 1, 1), 10));
                }
                for(int i = 0; i < 2; i++){
                    double initX = 50 + (Math.random() * 750);
                    addTo.add(new Enemy_Ember(new Vector(initX, -40),
                            new Routine_SingleAttack(null, new Vector(initX, 100), new Vector(initX, -100),
                                    200, 200, "volcano", 1, 1), 10));
                }
            }
        }

        if(tick == 9200){
            double dist = Driver_Game.getWidth()/3;
            for(double x = dist; x < Driver_Game.getWidth(); x += dist){
                addTo.add(new Enemy_Ember(new Vector(x, -40),
                        new Routine_SingleAttack(null, new Vector(x, 100), new Vector(x, -100),
                                200, 200, "fireStorm", 1, 1), 10));
            }

            double initX = Driver_Game.getWidth()/2;
            double xl = initX-100;
            double xr = initX+100;

            Instruction_StartPathToY ip = new Instruction_StartPathToY(null, 50, 200);
            Instruction_WaitPathOver wp1 = new Instruction_WaitPathOver(null);
            Instruction_Attack a = new Instruction_Attack(null, "laserL", 1, 300);
            Instruction_Timer t = new Instruction_Timer(null, 300);
            Instruction_StartPathToY end = new Instruction_StartPathToY(null, -100, 200);

            ip.setNext(wp1);
            wp1.setNext(a);
            a.setNext(t);
            t.setNext(end);

            addTo.add(new Enemy_Ember(new Vector(xl, -40), ip, 50));

            ip = new Instruction_StartPathToY(null, 50, 200);
            wp1 = new Instruction_WaitPathOver(null);
            a = new Instruction_Attack(null, "laserR", 1, 300);
            t = new Instruction_Timer(null, 300);
            end = new Instruction_StartPathToY(null, -100, 200);

            ip.setNext(wp1);
            wp1.setNext(a);
            a.setNext(t);
            t.setNext(end);

            addTo.add(new Enemy_Ember(new Vector(xr, -40), ip, 50));
        }

        if(tick >= 9800 && tick < 10500){
            if(tick % 60 == 0){
                Vector spawnPos = new Vector(50 + (Math.random() * 750), -40);
                Vector aPos = new Vector(50 + (Math.random() * 750), 50 + (Math.random() * 200));
                Vector endPolar = new Vector(Vector.getAngle(aPos, spawnPos), 400);

                Instruction_StartPath ip = new Instruction_StartPath(null, new Path_Polygon(aPos, false, 400));
                Instruction_WaitPathOver wp = new Instruction_WaitPathOver(null);
                Instruction_Attack a = new Instruction_Attack(null, "randomShot", 300, 300);
                Instruction_Timer t = new Instruction_Timer(null, 330);
                Instruction_SetPolar sp = new Instruction_SetPolar(null, endPolar);

                ip.setNext(wp);
                wp.setNext(a);
                a.setNext(t);
                t.setNext(sp);

                addTo.add(new Enemy_Ember(spawnPos, ip, 40));
            }
        }

        //ember city
        if(tick >= 10500 && tick <= 13000){
            if(tick == 10500){
                for(int i = 0; i < 5; i++){
                    double x = (i + 1) * (Driver_Game.getWidth()/6);
                    Vector spawnPos = new Vector(x, -40);
                    Vector aPos = new Vector(x, 100);
                    Vector endPolar = new Vector(180, 400);

                    Instruction_StartPath ip = new Instruction_StartPath(null, new Path_Polygon(aPos, false, 400));
                    Instruction_WaitPathOver wp = new Instruction_WaitPathOver(null);
                    Instruction_Attack a = new Instruction_Attack(null, "randomShot", 300, 300);
                    Instruction_Timer t = new Instruction_Timer(null, 330);
                    Instruction_SetPolar sp = new Instruction_SetPolar(null, endPolar);

                    ip.setNext(wp);
                    wp.setNext(a);
                    a.setNext(t);
                    t.setNext(sp);

                    addTo.add(new Enemy_Ember(spawnPos, ip, 40));
                }
            }
            if(tick == 10560){
                double dist = Driver_Game.getWidth()/3;
                for(double x = dist; x < Driver_Game.getWidth(); x += dist){
                    addTo.add(new Enemy_Ember(new Vector(x, -40),
                            new Routine_SingleAttack(null, new Vector(x, 100), new Vector(x, -100),
                                    200, 200, "fireStorm", 1, 1), 10));
                }

                double initX = Driver_Game.getWidth()/2;
                double xl = initX-100;
                double xr = initX+100;

                Instruction_StartPathToY ip = new Instruction_StartPathToY(null, 50, 200);
                Instruction_WaitPathOver wp1 = new Instruction_WaitPathOver(null);
                Instruction_Attack a = new Instruction_Attack(null, "laserL", 1, 300);
                Instruction_Timer t = new Instruction_Timer(null, 300);
                Instruction_StartPathToY end = new Instruction_StartPathToY(null, -100, 200);

                ip.setNext(wp1);
                wp1.setNext(a);
                a.setNext(t);
                t.setNext(end);

                addTo.add(new Enemy_Ember(new Vector(xl, -40), ip, 50));

                ip = new Instruction_StartPathToY(null, 50, 200);
                wp1 = new Instruction_WaitPathOver(null);
                a = new Instruction_Attack(null, "laserR", 1, 300);
                t = new Instruction_Timer(null, 300);
                end = new Instruction_StartPathToY(null, -100, 200);

                ip.setNext(wp1);
                wp1.setNext(a);
                a.setNext(t);
                t.setNext(end);

                addTo.add(new Enemy_Ember(new Vector(xr, -40), ip, 50));
            }
            if(tick == 10800){
                Instruction_StartPathToY ip = new Instruction_StartPathToY(null, 150, 200);
                Instruction_WaitPathOver wp1 = new Instruction_WaitPathOver(null);
                Instruction_Attack a = new Instruction_Attack(null, "convectionShot", 300, 300);
                Instruction_Timer t = new Instruction_Timer(null, 330);
                Instruction_StartPathToY end = new Instruction_StartPathToY(null, -100, 200);

                ip.setNext(wp1);
                wp1.setNext(a);
                a.setNext(t);
                t.setNext(end);

                addTo.add(new Enemy_Ember(new Vector(425, -40), ip, 80));
            }
            if(tick == 10860){
                for(int i = 0; i < 2; i++){
                    double initX = (i + 1) * (Driver_Game.getWidth()/4);
                    addTo.add(new Enemy_Ember(new Vector(initX, -40),
                            new Routine_SingleAttack(null, new Vector(initX, 75), new Vector(initX, -100),
                                    200, 200, "default", 1, 1), 20));
                }
                for(int i = 0; i < 2; i++){
                    double initX = (i + 1) * (Driver_Game.getWidth()/3);
                    addTo.add(new Enemy_Ember(new Vector(initX, -40),
                            new Routine_SingleAttack(null, new Vector(initX, 125), new Vector(initX, -100),
                                    200, 200, "volcano", 1, 1), 20));
                }
            }
            if(tick == 11100){
                addTo.add(new Enemy_Ember(new Vector(50, -40), new Vector(0, 200), "directionalFirestorm", 50));
                addTo.add(new Enemy_Ember(new Vector(800, -40), new Vector(0, 200), "directionalFirestorm", 50));

                Instruction_StartPathToY ip = new Instruction_StartPathToY(null, 150, 200);
                Instruction_WaitPathOver wp1 = new Instruction_WaitPathOver(null);
                Instruction_Attack a = new Instruction_Attack(null, "fireOrbit", 300, 300);
                Instruction_Timer t = new Instruction_Timer(null,  330);
                Instruction_StartPathToY end = new Instruction_StartPathToY(null, -100, 200);

                ip.setNext(wp1);
                wp1.setNext(a);
                a.setNext(t);
                t.setNext(end);

                addTo.add(new Enemy_Ember(new Vector(425, -40), ip, 80));
            }
            if(tick >= 11520 && tick <= 12780){
                if((tick - 11520) % 50 == 0){
                    String attackName;
                    double attackDelay;
                    if((tick - 11520) % 240 == 0){
                        attackName = "randomShot";
                        attackDelay = 300;
                    }
                    else if((tick - 11520) % 240 == 60){
                        attackName = "fireOrbit";
                        attackDelay = 300;
                    }
                    else if((tick - 11520) % 240 == 120){
                        if(Math.random() > .5) {
                            attackName = "default";
                            attackDelay = 1;
                        }
                        else{
                            attackName = "randomShot";
                            attackDelay = 300;
                        }
                    }
                    else{
                        if(Math.random() > .5) {
                            attackName = "volcano";
                            attackDelay = 1;
                        }
                        else{
                            attackName = "randomShot";
                            attackDelay = 300;
                        }
                    }
                    Vector spawnPos = new Vector(50 + (Math.random() * 750), -40);
                    Vector aPos = new Vector(50 + (Math.random() * 750), 50 + (Math.random() * 200));
                    Vector endPolar = new Vector(Vector.getAngle(aPos, spawnPos), 400);

                    Instruction_StartPath ip = new Instruction_StartPath(null, new Path_Polygon(aPos, false, 400));
                    Instruction_WaitPathOver wp = new Instruction_WaitPathOver(null);
                    Instruction_Attack a = new Instruction_Attack(null, attackName, attackDelay, 300);
                    Instruction_Timer t = new Instruction_Timer(null, 330);
                    Instruction_SetPolar sp = new Instruction_SetPolar(null, endPolar);

                    ip.setNext(wp);
                    wp.setNext(a);
                    a.setNext(t);
                    t.setNext(sp);

                    addTo.add(new Enemy_Ember(spawnPos, ip, 40));
                }

                if(tick >= 11580 && tick <= 12480){
                    if((tick - 11580) % 300 == 0){
                        if((tick - 11580) % 600 == 0){
                            double initX = Driver_Game.getWidth()/2;
                            double xl = initX-100;

                            Instruction_StartPathToY ip = new Instruction_StartPathToY(null, 50, 200);
                            Instruction_WaitPathOver wp1 = new Instruction_WaitPathOver(null);
                            Instruction_Attack a = new Instruction_Attack(null, "laserL", 1, 300);
                            Instruction_Timer t = new Instruction_Timer(null, 300);
                            Instruction_StartPathToY end = new Instruction_StartPathToY(null, -100, 200);

                            ip.setNext(wp1);
                            wp1.setNext(a);
                            a.setNext(t);
                            t.setNext(end);

                            addTo.add(new Enemy_Ember(new Vector(xl, -40), ip, 50));
                        }
                        else{
                            double initX = Driver_Game.getWidth()/2;
                            double xr = initX+100;

                            Instruction_StartPathToY ip = new Instruction_StartPathToY(null, 50, 200);
                            Instruction_WaitPathOver wp1 = new Instruction_WaitPathOver(null);
                            Instruction_Attack a = new Instruction_Attack(null, "laserR", 1, 300);
                            Instruction_Timer t = new Instruction_Timer(null, 300);
                            Instruction_StartPathToY end = new Instruction_StartPathToY(null, -100, 200);

                            ip.setNext(wp1);
                            wp1.setNext(a);
                            a.setNext(t);
                            t.setNext(end);

                            addTo.add(new Enemy_Ember(new Vector(xr, -40), ip, 50));
                        }
                    }
                }
            }

            if(tick == 12840) {
                double dist = Driver_Game.getWidth() / 4;
                for (double x = dist; x < Driver_Game.getWidth(); x += dist) {
                    addTo.add(new Enemy_Ember(new Vector(x, -40),
                            new Routine_SingleAttack(null, new Vector(x, 100), new Vector(x, -100),
                                    200, 200, "fireStorm", 1, 1), 30));
                }
                dist = Driver_Game.getWidth() / 3;
                for (double x = dist; x < Driver_Game.getWidth(); x += dist) {
                    addTo.add(new Enemy_Ember(new Vector(x, -40),
                            new Routine_SingleAttack(null, new Vector(x, 150), new Vector(x, -100),
                                    200, 200, "default", 1, 1), 30));
                }
            }

            if (tick == 13000) {
                double dist = Driver_Game.getWidth() / 4;
                for (double x = dist; x < Driver_Game.getWidth(); x += dist) {
                    addTo.add(new Enemy_Ember(new Vector(x, -40),
                            new Routine_SingleAttack(null, new Vector(x, 100), new Vector(x, -100),
                                    200, 200, "volcano", 1, 1), 30));
                }
            }
        }

        //END

    }

    //seed needs to be huge
    private static double randomNum(double seed, double minValue, double maxValue){
        if(seed == 0){
            seed += 1256;
        }
        //arbitrary number
        if(seed < 127){
            seed *= 127;
        }

        double difference = maxValue - minValue;
        double tally = 0;
        int loops = 0;
        int maxLoops = 100;
        for(double i = 1; i < difference; i += (Math.abs((tally%(Math.log(difference))) * 10) + 1)){
            if(loops >= maxLoops){
                break;
            }
            tally += seed % i;
            loops++;
        }
//        System.out.println((tally%difference) + minValue);
        return (tally%difference) + minValue;
    }

//    private static String s5attackName(double seed){
//        double n = randomNum(seed, 0, 100000);
//        String toRet;
//
//        if(n < 25000){
//            toRet = ""
//        }
//    }
}