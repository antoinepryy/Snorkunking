import java.util.ArrayList;

public abstract class Diver {
    protected int finalScore;
    protected int validScore;
    protected static ArrayList<Diver> DIVERLIST = new ArrayList<>();
    protected static ArrayList<Diver> DIVERLISTDISP = new ArrayList<>();
    protected String name;
    protected ArrayList<Chest> chests;
    protected int level;
    protected int cave;



    public int getValidScore() {
        return validScore;
    }

    public static ArrayList<Diver> getDiverList() {
        return DIVERLIST;
    }

    public void setValidScore(int validScore) {
        this.validScore = validScore;
    }

    public static ArrayList<Diver> getDiverListDisp() {
        return DIVERLISTDISP;
    }


    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }


    public Diver(String name) {
        this.finalScore = 0;
        this.validScore = 0;
        this.name=name;
        this.chests = new ArrayList<>();
        this.level=0;
        this.cave=0;
        Diver.DIVERLIST.add(this);
        Diver.DIVERLISTDISP.add(this);



    }

    public int depth() {//profondeur sur l'axe des ordonnées
        if (this.getCave() == 0)
            return 10;
        if (this.getCave() == 1)
            return Main.CAVE1.gethBegin() + 10 + this.getLevel() * 20;
        if (this.getCave() == 2)
            return Main.CAVE2.gethBegin() + 10 + this.getLevel() * 20;
        if (this.getCave() == 3)
            return Main.CAVE3.gethBegin() + 10 + this.getLevel() * 20;
        else{
            return 0;
        }
    }


    public abstract void play();

    public static void sortPlayers(){ // Range les joueurs du plus éloigné au plus près de la surface

        ArrayList<Diver> tmp = new ArrayList<>();

        while (Diver.DIVERLIST.size() != 0){
            int range = 0;
            int maxCave = Diver.DIVERLIST.get(0).getCave();
            int maxLevel = Diver.DIVERLIST.get(0).getLevel();

            for (int i = 0; i < Diver.DIVERLIST.size(); i++){
                if((Diver.DIVERLIST.get(i).getCave()==maxCave && Diver.DIVERLIST.get(i).getLevel()>=maxLevel) || (Diver.DIVERLIST.get(i).getCave()>maxCave)){
                    range = i;
                    maxCave = Diver.DIVERLIST.get(i).getCave();
                    maxLevel = Diver.DIVERLIST.get(i).getLevel();
                }
                else {
                    continue;
                }

            }

            tmp.add(Diver.DIVERLIST.get(range));
            Diver.DIVERLIST.remove(range);


        }

        Player.DIVERLIST = tmp;

    }

    public static void reset(){//Kill les joueurs qui sont dans l'eau qd l'oxygene atteint 0
        for (Diver diver : DIVERLIST){

            if (diver.getCave()!=0){
                if (Main.CAVE3.getLevelsInCave().size()!=0){
                    for (Chest chest : diver.chests) {
                        Main.CAVE3.getLevelsInCave().get(Main.CAVE3.getLevelsInCave().size()-1).getLvlChest().add(chest);
                    }
                }
                else if (Main.CAVE2.getLevelsInCave().size()!=0){
                    for (Chest chest : diver.chests){
                        Main.CAVE2.getLevelsInCave().get(Main.CAVE2.getLevelsInCave().size()-1).getLvlChest().add(chest);
                    }
                }
                else if (Main.CAVE1.getLevelsInCave().size()!=0){
                    for (Chest chest : diver.chests){
                        Main.CAVE1.getLevelsInCave().get(Main.CAVE1.getLevelsInCave().size()-1).getLvlChest().add(chest);
                    }
                }

                diver.getChests().clear();


            }
        diver.setCave(0);
        diver.setLevel(0);

        }
    }



    public String getName() {
        return name;
    }


    public ArrayList<Chest> getChests() {
        return chests;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCave() {
        return cave;
    }

    public void setCave(int cave) {
        this.cave = cave;
    }

    public void getUp(){//Quand un joueur monte
        if (this.getCave()==0){

        }
        else if(this.getLevel()!=0){
            this.setLevel(this.getLevel()-1);
        }

        else {
            this.cave = nextCaveUp();
            if (this.cave == 0) {
                this.level = 0;
                for (int k = 0; k < this.getChests().size(); k++) {
                    this.setValidScore(this.getValidScore() + this.getChests().get(k).getScore());
                }
                this.getChests().clear();
            }
            else {
                this.level = Cave.LISTCAVE.get(this.getCave()-1).getLevelsInCave().size() - 1;
            }
        }
        Main.OXYGEN.up(this.getChests().size());


    }

    public void getDown(){//Quand un joueur descend
        if (this.getCave()==0){
            if (nextCaveDown()==this.getCave()){

            }
            else {
                this.setCave(nextCaveDown());
                this.setLevel(0);
            }

        }
        else if (Cave.LISTCAVE.get(this.getCave()-1).getLevelsInCave().size()-1!=this.getLevel()){
            this.setLevel(this.getLevel()+1);
        }
        else{
            if (nextCaveDown()==this.getCave()){

            }
            else {
                this.setCave(nextCaveDown());
                this.setLevel(0);
            }
        }
        Main.OXYGEN.down(this.getChests().size());


    }

    public int nextCaveUp(){//Cave au dessus
        if (this.getCave()==1){
            return 0;
        }
        else{
            for (int k=this.getCave()-1; k>=1; k--){
                if (Cave.LISTCAVE.get(k-1).getLevelsInCave().size()==0){

                }
                else{
                    return k;
                }

            }

        }

        return 0;
    }

    public int nextCaveDown(){//Cave en dessous
        for(int k = this.getCave()+1; k<4; k++){
            if(Cave.LISTCAVE.get(k-1).getLevelsInCave().size()==0){

            }
            else {
                return k;
            }
        }

        return this.getCave();
    }


    public void take() {//Prendre un coffre
        Chest tmp;

        if (this.getCave()==0){

        }
        else if (this.getCave()==1){
            try{
                tmp = Main.CAVE1.getLevelsInCave().get(this.getLevel()).getLvlChest().get(0);
                this.getChests().add(tmp);
                Main.CAVE1.removeChest(this.getLevel());

            }catch (IndexOutOfBoundsException e){

            }

        }
        else if (this.getCave()==2){
            try{
                tmp = Main.CAVE2.getLevelsInCave().get(this.getLevel()).getLvlChest().get(0);
                this.getChests().add(tmp);
                Main.CAVE2.removeChest(this.getLevel());

            }catch(IndexOutOfBoundsException e){

            }

        }
        else if (this.getCave()==3){
            try{
                tmp = Main.CAVE3.getLevelsInCave().get(this.getLevel()).getLvlChest().get(0);
                this.getChests().add(tmp);
                Main.CAVE3.removeChest(this.getLevel());

            }catch(IndexOutOfBoundsException e){

            }
        }
        Main.OXYGEN.take();

    }

}
