public class Instruction_Boss_SetSpellName extends Instruction {

    protected String spellName;
    public Instruction_Boss_SetSpellName(Entity owner, String spellName){
        super(owner);
        this.spellName = spellName;
    }
    public Instruction_Boss_SetSpellName(Entity owner, Behavior next, String spellName){
        super(owner, next);
        this.spellName = spellName;
    }
    @Override
    public boolean doAction(){
        if(owner instanceof Enemy_Boss){
            ((Enemy_Boss)owner).setSpellName(spellName);
        }
        return true;
    }

    public void setSpellName(String newS){
        this.spellName = newS;
    }
}
