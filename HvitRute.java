class HvitRute extends Rute {
      protected char hvitRute = '.';

      public HvitRute(int kol, int rad) {
          super(kol, rad);
      }

      public char tilTegn() {
          return hvitRute;
      }

      public void gaa(Rute forrige, String r) {
          //System.out.println("andre gaa "+toString());
          String t = r+toString();
          gaaVest(forrige, t);
          gaaOst(forrige, t);
          gaaSyd(forrige, t);
          gaaNord(forrige, t);
      }

      public void gaa() {
          //System.out.println("første gaa "+toString());
          gaaVest(toString());
          gaaOst(toString());
          gaaSyd(toString());
          gaaNord(toString());
      }

      public void gaaVest(Rute forrige, String r) {
          try{
              if(vest!=forrige) {
                  vest.gaa(this, r);
              }
          } catch(NullPointerException e){}
      }

      public void gaaOst(Rute forrige, String r) {
          try{
              if(ost!=forrige) {
                  ost.gaa(this, r);
              }
          } catch(NullPointerException e){}
      }

      public void gaaSyd(Rute forrige, String r) {
          try{
              if(syd!=forrige) {
                  syd.gaa(this, r);
              }
          } catch(NullPointerException e){}
      }

      public void gaaNord(Rute forrige, String r) {
          try{
              if(nord!=forrige) {
                  nord.gaa(this, r);
              }
          } catch(NullPointerException e){}
      }

      public void gaaVest(String r) {
          try{vest.gaa(this, r);}
          catch(NullPointerException e){}
      }

      public void gaaOst(String r) {
          try{ost.gaa(this, r);}
          catch(NullPointerException e){}
      }

      public void gaaSyd(String r) {
          try{syd.gaa(this, r);}
          catch(NullPointerException e){}
      }

      public void gaaNord(String r) {
          try{nord.gaa(this, r);}
          catch(NullPointerException e){}
      }

}
