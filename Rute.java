abstract class Rute {
    protected int rutensKolonne;
    protected int rutensRad;
    protected Labyrint moderLabyrint;
    protected Rute ost;
    protected Rute vest;
    protected Rute nord;
    protected Rute syd;
    protected Character sort = '#';
    protected Character hvit = '.';
    //public Lenkeliste<Rute> vei = new Lenkeliste<Rute>();

    public Rute(int kol, int rad) {
        rutensKolonne = kol;
        rutensRad = rad;
    }

    public String toString() {
        return ("("+(rutensKolonne-1)+", "+(rutensRad-1)+") --> ");
    }

    //returnerer rutens "tegnrepresentasjon".
    //beskrevet ovenfor som . for hvit og
    //# for sort
    abstract public char tilTegn();

    public void settNord(Rute r) {
        nord = r;
    }

    public void settSyd(Rute r) {
        syd = r;
    }

    public void settVest(Rute r) {
        vest = r;
    }

    public void settOst(Rute r) {
        ost = r;
    }

    public void finnUtvei() {
        this.gaa();
    }


    abstract public void gaa();
    abstract public void gaa(Rute r, String t);
}
