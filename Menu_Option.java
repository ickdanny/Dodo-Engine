import java.util.ArrayList;

public abstract class Menu_Option {

    protected ArrayList<String> name;

    public Menu_Option(){
        this.name = new ArrayList<>();
        this.name.add("UNNAMED");
    }
    public Menu_Option(String name){
        this.name = new ArrayList<>();
        this.name.add(name);
    }
    public Menu_Option(ArrayList<String> name){
        this.name = name;
    }

    public ArrayList<String> getName(){
        return this.name;
    }

    //override this
    public abstract void selectAction();
}
