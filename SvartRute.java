class SvartRute extends Rute {
      protected char sortRute = '#';

      public SvartRute(int kol, int rad) {
          super(kol, rad);
      }

      public char tilTegn() {
          return sortRute;
      }

      public void gaa(Rute forrige, String r){
          //System.out.println("deadend");
      }

      public void gaa() {
          //System.out.println("deadend første");
      }
}
