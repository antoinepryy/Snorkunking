



import edu.princeton.cs.introcs.StdDraw;

import java.awt.event.KeyEvent;
import java.util.*;

import java.util.Random;

public class Main {

    public static int X = 1200;
    public static int Y = 700;
    public static Cave CAVE1;
    public static Cave CAVE2;
    public static Cave CAVE3;
    public static Oxygen OXYGEN;



    public static void main(String[] args) {

        int replay;
        do{
            replay = game();
        }while(replay==1);
    }

    public static int game(){
        Random rd = new Random();
        Scanner scan = new Scanner(System.in);
        StdDraw.enableDoubleBuffering(); // Permet un affichage plus fluide
        int nbPLayers;
        System.out.println("Welcome to Snorkunking");
        int huPlay;
        int IAPlay;
        Diver.getDiverListDisp().clear();
        Diver.getDiverList().clear();
        Cave.LISTCAVE.clear();

        do { // Demande les effectifs IA et joueurs
            System.out.println("You can play from 1 to 4 players ");
            System.out.println("How many human players : ");
            huPlay = scan.nextInt();
            System.out.println("How many IA players : ");
            IAPlay = scan.nextInt();
            nbPLayers = huPlay + IAPlay;
        } while (nbPLayers > 4 || nbPLayers < 1);

        for (int k = 1; k <= huPlay; k++) { // Création des joueurs humains
            System.out.println("Player " + k + " name :");
            String tmpName = scan.next();
            new Player(tmpName);
        }
        for (int k = 1; k <= IAPlay; k++) { // Création des joueurs IA
            new IAPlayer("IA " + k);
        }

        // Création des 3 caves
        CAVE1 = new Cave(1);
        CAVE2 = new Cave(2);
        CAVE3 = new Cave(3);
        OXYGEN = new Oxygen(CAVE1, CAVE2, CAVE3); // Création barre oxygène
        StdDraw.setCanvasSize(X, Y);
        StdDraw.setXscale(0, X);
        StdDraw.setYscale(0, Y);
        Cave.refreshCave();
        disp();//Premier affichage


        for (int phase = 1; phase <= 3; phase++) { // Chaque itération de la boucle réprésente une phase
            while (OXYGEN.getReserve() > 0) {//Chaque phase se poursuit tant qu'il reste de l'oxygène
                Diver.sortPlayers();//Trie les joueurs du plus profond au plus proche de la surface
                for (Diver diver : Diver.DIVERLIST) {//Tour de chaque joueur
                    StdDraw.setPenColor(StdDraw.WHITE);//Affichage du joueur qui doit jouer
                    StdDraw.filledRectangle(600,20,50,10);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.text(600,20,"Tour :" +diver.getName());
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.line(550,10,650,10);
                    StdDraw.line(550,30,650,30);
                    StdDraw.line(550, 10,550,30);
                    StdDraw.line(650, 10,650,30);
                    StdDraw.show();
                    if (OXYGEN.getReserve() > 0) {

                        diver.play();
                        disp();
                    }
                    else {
                        continue;
                    }
                }
            }
            for(int k = 0 ; k < Diver.DIVERLISTDISP.size(); k++){ // A chaque fin de bouteille d'oxygène on sauvegarde les scores de chaque joueur
                Diver.DIVERLISTDISP.get(k).setFinalScore(Diver.DIVERLISTDISP.get(k).getFinalScore()+Diver.DIVERLISTDISP.get(k).getValidScore());
                Diver.DIVERLISTDISP.get(k).setValidScore(0);
            }
            Diver.reset(); // A chaque fin de phase les joueurs retournent en haut, ceux qui n'y sont pas déjà perdent leurs coffres
            //On enlève les niveaux sans coffres
            CAVE1.cleanCave();
            CAVE2.cleanCave();
            CAVE3.cleanCave();
            Cave.refreshCave(); //Actualisation graphique
            OXYGEN.fill(); //On remplit l'oxygène
            disp(); //Nouvel affichage pour le prochain tour
        }
        int scoreGagnant = 0;
        int indexGagnant = 0;
        for(int k = 0 ; k < Diver.DIVERLISTDISP.size(); k++){ //Sert à savoir qui a gagné
            if (Diver.getDiverListDisp().get(k).getFinalScore()>scoreGagnant){
                scoreGagnant=Diver.getDiverListDisp().get(k).finalScore;
                indexGagnant = k;
            }
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(600,100, "Le gagnant est " + Diver.DIVERLISTDISP.get(indexGagnant).getName()+" avec un total de " + scoreGagnant +" points !");
        StdDraw.show();

        while (true){
            if (StdDraw.isKeyPressed(KeyEvent.VK_R)){
                return 1;
            }
            else if (StdDraw.isKeyPressed(KeyEvent.VK_Q)){
                return 0;
            }
            else{

            }
        }

    }




    public static void disp(){
        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.filledRectangle(0, 0, X, Y);
        StdDraw.picture(600, 650, "pictBeach.png", 1200, 100); // Image plage
        for (Cave cave : Cave.LISTCAVE){ // Pour chaque cave :
            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            int tmpH = cave.gethBegin();
            int tmph = cave.gethEnd();
            int moy = (tmpH + tmph)/2; //Calcul du centre du rectangle représentant la cave
            StdDraw.filledRectangle(600, 600 - moy , 550,(tmph-tmpH)/2); // On affiche un rectangle
            StdDraw.setPenColor(StdDraw.BLACK);
            for (int k = cave.gethBegin(); k<=cave.gethEnd(); k+=20){ //On trace les niveaux
                StdDraw.line(50, 600-k, 1149, 600-k);
            }
            StdDraw.line(50, 600-cave.gethBegin(), 50, 600-cave.gethEnd());
            StdDraw.line(1149, 600-cave.gethBegin(), 1149, 600-cave.gethEnd());
        }
        Integer scr;
        for (int k = 0; k < Diver.getDiverListDisp().size();k++){
            StdDraw.picture(100+k*200,600-Diver.getDiverListDisp().get(k).depth(),"pictDiver.png",80,40 ); // On affiche les plongeurs
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledRectangle(100+k*200,600+20,20,10);
            StdDraw.setPenColor(StdDraw.BLACK);

            scr = Diver.getDiverListDisp().get(k).getValidScore();
            StdDraw.text(100+k*200,600+20, scr.toString());
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.line(100+k*200-20,630,100+k*200+20,630);//Lignes pour bordure affichage score
            StdDraw.line(100+k*200-20,610,100+k*200+20,610);
            StdDraw.line(100+k*200-20,610,100+k*200-20,630);
            StdDraw.line(100+k*200+20,610,100+k*200+20,630);

        }
        for (Cave cave : Cave.LISTCAVE){ // Affichage des tésors
            for (int k = 0; k<cave.getNbLevel(); k++){
                if (cave.getLevelsInCave().get(k).getLvlChest().size()==0){
                    continue;
                }
                else{
                    StdDraw.picture(1100, 600 - 10 - cave.gethBegin() - k * 20 , "pictTreasure.png", 32, 28);
                }
            }
        }

        StdDraw.setPenColor(StdDraw.PINK);
        StdDraw.filledRectangle(1000, 650, 100, 20);
        StdDraw.setPenColor(StdDraw.WHITE);
        double xOXYGEN = 1000+(((double)OXYGEN.getReserveMax()-(double)OXYGEN.getReserve())/(double)OXYGEN.getReserveMax())*100;
        if (xOXYGEN<=1100){
            StdDraw.filledRectangle(xOXYGEN,650,1100-xOXYGEN, 20);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.line(900,630,1100,630);//Lignes bordures affichage oxygene
            StdDraw.line(900,670,1100,670);
            StdDraw.line(900,670,900,630);
            StdDraw.line(1100,670,1100,630);
        }
        else{
            StdDraw.filledRectangle(xOXYGEN,650,0, 20);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.line(900,630,1100,630);
            StdDraw.line(900,670,1100,670);
            StdDraw.line(900,670,900,630);
            StdDraw.line(1100,670,1100,630);
        }

        StdDraw.show(); //Envoie toutes les modifs visuelles à l'écran
    }
}

