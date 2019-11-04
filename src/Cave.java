import java.util.ArrayList;
import java.util.Random;

public class Cave {
    Random rd = new Random();
    public static ArrayList<Cave> LISTCAVE = new ArrayList<>();
    private int nbLevel;
    private int depth;
	private ArrayList<Level> levelsInCave = new ArrayList<>();

	private int hBegin;
	private int hEnd;

    public Cave(int depth) {
        this.depth = depth;
        switch (depth) {
            case 1 : // cave haute
                nbLevel=rd.nextInt(12 - 9 + 1) + 9;
                break;
            case 2 : //cave middle
                nbLevel=rd.nextInt(9 - 6 + 1) + 6;
                break;
            case 3 : //cave low
                nbLevel=rd.nextInt(6 - 3 + 1) + 3;
                break;
        }

        this.init();
        hBegin=20;
        hEnd = hBegin + this.nbLevel*20;
        Cave.LISTCAVE.add(this);

    }

    public int gethBegin() {
        return hBegin;
    }


    public int gethEnd() {
        return hEnd;
    }


    private void init (){
        for (int k = 1 ; k <= this.nbLevel ; k++){
            this.levelsInCave.add(new Level(this.depth));
        }

    }

    public static void refreshCave(){//Actualisation des ordonnées des caves après nettoyage
        Cave.LISTCAVE.get(0).hEnd = Cave.LISTCAVE.get(0).hBegin + 20*Cave.LISTCAVE.get(0).getLevelsInCave().size();
        for (int k = 1; k<3; k++){
            Cave.LISTCAVE.get(k).hBegin = Cave.LISTCAVE.get(k-1).hEnd + 20;
            Cave.LISTCAVE.get(k).hEnd = Cave.LISTCAVE.get(k).hBegin + 20*Cave.LISTCAVE.get(k).getLevelsInCave().size();
        }
    }


    public int getNbLevel() {
        return nbLevel;
    }

    public ArrayList<Level> getLevelsInCave() {
        return levelsInCave;
    }

    public void setLevelsInCave(ArrayList<Level> levelsInCave) {
        this.levelsInCave = levelsInCave;
    }

    public void removeChest(int lvl){
        this.getLevelsInCave().get(lvl).getLvlChest().remove(0);
    }


    public void cleanCave(){//Nettoie les niveaux vides
        ArrayList<Level> tmp = new ArrayList<>();
        for (int k = 0; k< this.getLevelsInCave().size(); k++){
            if (this.getLevelsInCave().get(k).getLvlChest().size()!=0){
                tmp.add(this.getLevelsInCave().get(k));
            }
        }
        this.setLevelsInCave(tmp);
        this.nbLevel = this.getLevelsInCave().size();
    }




}
