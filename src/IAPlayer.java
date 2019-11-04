
public class IAPlayer extends Diver {

    public IAPlayer(String name) {
        super(name);
    }

    public void play(){//Methode play implémentée pour joueur IA
        if (this.getChests().size()!=0){
            this.getUp();
        }
        else if (this.getCave()==0){
            this.getDown();
        }
        else if (Cave.LISTCAVE.get(this.getCave()-1).getLevelsInCave().get(this.getLevel()).getLvlChest().size()!=0){
            this.take();
        }
        else {
            this.getDown();
        }
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
