import java.util.ArrayList;

public class Level {
    private ArrayList<Chest> lvlChest = new ArrayList<>();
    private int caveNumber;

    public ArrayList<Chest> getLvlChest() {
        return lvlChest;
    }


    public Level(int caveNbr){
        this.caveNumber = caveNbr;
        this.lvlChest.add(new Chest(this.caveNumber));

    }
}
