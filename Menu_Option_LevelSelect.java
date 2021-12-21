import java.util.ArrayList;

//this is only for practice mode
public class Menu_Option_LevelSelect extends Menu_Option {

    private int level, difficulty;
    public Menu_Option_LevelSelect(int level, int diff){
        this.name = new ArrayList<>();
        this.difficulty = diff;
        this.level = level;
        switch(level){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                this.name.add("Stage " + level);
                break;
            default:
                this.name.add("Extra");
                this.name.add("Remove Extra Option Before Finish");
                break;
        }
    }

    @Override
    public void selectAction(){
        //goto shot select
        switch(difficulty){
            case 1:
            case 2:
            case 3:
            case 4:
                ArrayList<Menu_Option> options = new ArrayList<>();
                options.add(new Menu_Option_ShotSelect(level, difficulty, true, "AbaddonA"));
                options.add(new Menu_Option_ShotSelect(level, difficulty, true, "AbaddonB"));
                options.add(new Menu_Option_Back());
                Menu toAdd = new Menu(options, new Vector(200 * Driver.getUpdatableSize(), 0), Driver.getWidth(), Driver.getHeight());
                Driver.add(toAdd);
                break;
            case 5:
            case 6:
            case 7:
            case 8:
                options = new ArrayList<>();
                options.add(new Menu_Option_ShotSelect(level, difficulty, true, "AzraelA"));
                options.add(new Menu_Option_ShotSelect(level, difficulty, true, "AzraelB"));
                options.add(new Menu_Option_Back());
                toAdd = new Menu(options, new Vector(200 * Driver.getUpdatableSize(), 0), Driver.getWidth(), Driver.getHeight());
                Driver.add(toAdd);
                break;
            case 9:
                options = new ArrayList<>();
                options.add(new Menu_Option_ShotSelect(7, difficulty, true, "MiraA"));
                options.add(new Menu_Option_ShotSelect(7, difficulty, true, "MiraB"));
                options.add(new Menu_Option_Back());
                toAdd = new Menu(options, new Vector(200 * Driver.getUpdatableSize(), 0), Driver.getWidth(), Driver.getHeight());
                Driver.add(toAdd);
                break;
        }
    }
}
