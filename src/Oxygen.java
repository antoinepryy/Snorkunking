
public class Oxygen {

    private int reserve;
    private int reserveMax;

    public Oxygen(Cave cave1, Cave cave2, Cave cave3) {
        int lvl1 = cave1.getNbLevel();
        int lvl2 = cave2.getNbLevel();
        int lvl3 = cave3.getNbLevel();
        this.reserve=2*(lvl1+lvl2+lvl3);
        this.reserveMax=2*(lvl1+lvl2+lvl3);
    }

    public void fill() { // remplissage
        this.reserve=this.reserveMax;
    }

    public void take() { // quand un plongeur prend un coffre
        this.reserve-=1;
    }

    public void up(int nbChest) { // quand le plongeur monte
        this.reserve-=(1+nbChest);
    }

    public void down(int nbChest) { // quand le plongeur descend
        this.reserve-=(1+nbChest);
    }

    public int getReserve() {
        return reserve;
    }

    public int getReserveMax() {
        return reserveMax;
    }

}
