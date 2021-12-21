public class Instruction_XL_SpawnShadowEntity extends Instruction {
    private String name;
    public Instruction_XL_SpawnShadowEntity(Enemy_Boss_XiaoLi owner, String name){
        super(owner);
        this.name = name;
    }
    public Instruction_XL_SpawnShadowEntity(Enemy_Boss_XiaoLi owner, Instruction next, String name){
        super(owner, next);
        this.name = name;
    }

    @Override
    public boolean doAction(){
        Enemy_Boss_XiaoLi xl = ((Enemy_Boss_XiaoLi)owner);
        switch(name){
            case "forwardThrust":
                xl.spawnThrust(true);
                break;
            case "dualThrust":
                xl.spawnThrust(false);
                break;
            case "leftSlash":
                xl.spawnSlash(true);
                break;
            case "rightSlash":
                xl.spawnSlash(false);
                break;
            case "leftSpin":
                xl.spawnSpin(true);
                break;
            case "dualSpin":
//                xl.spawnSpin(true);
                xl.spawnSpin(false);
                break;
        }
        return true;
    }
}
