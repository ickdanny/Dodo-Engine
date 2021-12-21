//behavior interface
public interface Behavior {
    //returns true to chain the next behavior
    //    //does action, returns true if the action is completed.
    boolean doAction();
    Behavior getNext();
    void setNext(Behavior a);

    Behavior getRootBehavior();
}
// i probably need to delete this