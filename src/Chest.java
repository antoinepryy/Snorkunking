import java.util.Random;

public class Chest {
    Random rnd = new Random();
    private int score;

    public Chest(int lvl){
        switch (lvl){
            case 1 :
                this.score = rnd.nextInt(3 - 1 + 1) + 1;
                break;
            case 2 :
                this.score = rnd.nextInt(8 - 5 + 1) + 5;
                break;
            case 3 :
                this.score = rnd.nextInt(12 - 8 + 1) + 8;
                break;
        }
    }

    public int getScore(){
        return this.score;
    }
}
