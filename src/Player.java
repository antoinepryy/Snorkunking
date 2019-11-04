import edu.princeton.cs.introcs.StdDraw;
import java.awt.event.KeyEvent;

public class Player extends Diver {

    public Player(String name) {
        super(name);
    }



    @Override
    public void play() {//Methode play implémentée pour joueur humain
        int k = 0;
        do {
            if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) { // Si joueur descends
                this.getDown();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                k=1;
            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) { // Si joueur monte
                this.getUp();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                k=1;
            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) { // Si joueur prend coffre
                this.take();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                k=1;
            }
        }while(k==0);
    }


}