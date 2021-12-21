import java.util.ArrayList;

public class Menu_Option_Start extends Menu_Option {

    public Menu_Option_Start(){
        super("Start");
    }

    @Override
    public void selectAction(){
        ArrayList<Menu_Option> options = new ArrayList<>();
        for(int i = 1; i <= 9; i++){
            options.add(new Menu_Option_Difficulty(i, false));
        }
        options.add(new Menu_Option_Back());
        Driver.add(new Menu(options, new Vector(200 * Driver.getUpdatableSize(), 0), Driver.getWidth(), Driver.getHeight()));
    }
}