public class Routine_Boss_Spell extends Routine_Boss_Attack {

    //true = power, false == drop
    private boolean powerOrDrop;
    private int power;
    //either "life" or "bomb"
    private String drop;
    public Routine_Boss_Spell(Enemy_Boss owner, Behavior next, Behavior instructions, double initHP, String spellName, int power){
        super(owner, next, instructions, initHP);
        ((Instruction_Boss_SetSpellName)this.instructions).setSpellName(spellName);
        powerOrDrop = true;
        this.power = power;
    }

    public Routine_Boss_Spell(Enemy_Boss owner, Behavior next, Behavior instructions, double initHP, String spellName, String drop){
        super(owner, next, instructions, initHP);
        ((Instruction_Boss_SetSpellName)this.instructions).setSpellName(spellName);
        powerOrDrop = false;
        this.drop = drop;
    }

    public boolean isPowerOrDrop(){
        return powerOrDrop;
    }

    public int getPower(){
        return power;
    }

    public String getDrop(){
        return drop;
    }
}