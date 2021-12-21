//instruction class implements Behavior
public abstract class Instruction implements Behavior {
    //behavior next (chained, null if no new behavior)
    protected Behavior next;
    //entity theEntity
    protected Entity owner;

    public Instruction(Entity owner){
        this.owner = owner;
    }
    public Instruction(Entity owner, Behavior next){
        this.owner = owner;
        this.next = next;
    }
    //doAction()
    //does something, invokes something, returns something
    @Override
    public abstract boolean doAction();

    //setNext(Behavior next)
    @Override
    public void setNext(Behavior next){
        this.next = next;
    }
    @Override
    public Behavior getNext(){
        return next;
    }
    public Entity getOwner(){
        return owner;
    }
//    @Override
    public void setOwner(Entity owner){
        this.owner = owner;
    }

    @Override
    public Behavior getRootBehavior(){
        return this;
    }
}