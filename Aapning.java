class Aapning extends HvitRute {

  public Aapning(int kol, int rad, Labyrint lab) {
      super(kol, rad);
      moderLabyrint = lab;
  }

  //lag en metode gaa for hvis første er aapning

    public void gaa() {
        moderLabyrint.leggTilLosning(toString());
    }

    public void gaa(Rute forrige, String r) {
        //System.out.println("utgang: "+toString());
        String t = r+toString();
        //System.out.println(t);
        moderLabyrint.leggTilLosning(t);
    }

    @Override
    public String toString() {
        return("("+(rutensKolonne-1)+", "+(rutensRad-1)+")");
    }
}
