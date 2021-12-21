import java.util.ArrayList;

public class Menu_Option_Practice extends Menu_Option {

    public Menu_Option_Practice(){
        super("Practice");
    }

    @Override
    public void selectAction(){
        ArrayList<Menu_Option> options = new ArrayList<>();
        //this creates an extra option
        //NEED TO REMOVE BEFORE FINAL
//        for(int i = 1; i <= 9; i++){
        for(int i = 1; i <= 8; i++){
            options.add(new Menu_Option_Difficulty(i, true));
        }
        options.add(new Menu_Option_Back());
        Driver.add(new Menu(options, new Vector(200 * Driver.getUpdatableSize(), 0), Driver.getWidth(), Driver.getHeight()));
    }
}