import java.io.*;
import java.util.*;

class Labyrint {
    protected Rute[][] labyrint;
    protected int kolonner;
    protected int rader;
    protected Lenkeliste<String> losninger = new Lenkeliste<String>();

    private Labyrint(Rute[][] ruter, int kol, int rad) {
        labyrint = ruter;
        kolonner = kol;
        rader = rad;
    }

    public void leggTilLosning(String r) {
        losninger.leggTil(r);
    }

    public static Labyrint lesFraFil(File fil) throws FileNotFoundException {
        //leser første linjen, henter antall kolonner og rader
        Scanner scanner = new Scanner(fil);
        String firstLine = scanner.nextLine();
        String[] dimensjoner = firstLine.split(" ");
        int antRad = Integer.parseInt(dimensjoner[0]);
        int antKol = Integer.parseInt(dimensjoner[1]);
        Character sort = '#';
        Character hvit = '.';

        Rute[][] rutene = new Rute[antRad][antKol];
        Labyrint lab = new Labyrint(rutene, antKol, antRad);

        int teller = 0;
        for(int k = 0; k<antRad; k++) {
            String linje = scanner.nextLine();
            for(int i = 0; i<antKol; i++) {
                if(sort.equals(linje.charAt(i))) {
                    //System.out.println("#");
                    SvartRute t = new SvartRute(i+1, k+1);
                    rutene[k][i] = t;
                }
                else if(hvit.equals(linje.charAt(i))) {
                    //System.out.print(".");
                    rutene[k][i] = new HvitRute(i+1, k+1);
                    //System.out.print(".");
                }
            }
            //System.out.println("\n");
        }
        rutene = aapningSorter(rutene, antRad, antKol, lab);
        rutene = rutePekerSorter(rutene, antRad, antKol);
        return lab;
    }

    //metode for å gi alle nord, øst, syd, vest pekere
    public static Rute[][] rutePekerSorter(Rute[][] r, int hoyde, int bredde) {
        //går nedover rad for rad og gir nord pekere. begynner med rad 1(egt2)
        for(int i = 1; i<hoyde; i++){
            for(int k = 0; k<bredde; k++) {
                r[i][k].settNord(r[i-1][k]);
            }
        }
        //gir syd-pekere med metode som ovenfor
        for(int i = 0; i<hoyde-1; i++) {
            for(int k = 0; k<bredde; k++) {
                r[i][k].settSyd(r[i+1][k]);
            }
        }
        //gir ost pekere
        for(int k = 0; k<bredde-1; k++) {
            for(int i = 0; i<hoyde; i++) {
                r[i][k].settOst(r[i][k+1]);
            }
        }
        //gir vest pekere
        for(int k = 1; k<bredde; k++) {
            for(int i = 0; i<hoyde; i++) {
                r[i][k].settVest(r[i][k-1]);
            }
        }
        return r;
    }

    public int hentAntRad() {
        return rader;
    }

    public int hentAntKol() {
        return kolonner;
    }

    //metode for å sette inn åpning der det skal være
    public static Rute[][] aapningSorter(Rute[][] r, int hoyde, int bredde, Labyrint l) {
        Character white = '.';
        for(int i = 0; i<bredde; i++) {
            if(white.equals(r[0][i].tilTegn())) {
                r[0][i] = new Aapning(i+1, 1, l);
            }
        }
        for(int i = 0;i<bredde; i++) {
            if(white.equals(r[hoyde-1][i].tilTegn())) {
                r[hoyde-1][i] = new Aapning(i+1, hoyde, l);
            }
        }
        for(int i = 0; i<hoyde; i++) {
            if(white.equals(r[i][0].tilTegn())) {
                r[i][0] = new Aapning(1, i+1, l);
            }
        }
        for(int i = 0; i<hoyde; i++) {
            if(white.equals(r[i][bredde-1].tilTegn())) {
                r[i][bredde-1] = new Aapning(bredde, i+1, l);
            }
        }
        return r;
    }

    //printer brett for å se om riktig
    public static void printer(Rute[][] r) {
        for(Rute[] e: r) {
            for(Rute t: e) {
                System.out.print(t.tilTegn());
            }
            System.out.println("\n");
        }
    }

    public Rute hentRute(int kol, int rad) {
        return labyrint[rad][kol];
    }

    public Liste<String> finnUtveiFra(int kol, int rad) {
        losninger = new Lenkeliste<String>();
        Rute r = hentRute(kol, rad);
        r.finnUtvei();
        return losninger;
    }

    //labyrinten skal kunne returneres i et format som enkelt kan
    //skrives ut til terminal underveis.
    public String toString() {
        //skal bruke array til å printe ut labyrint i terminal
        String labyrintUtskrift = "";
        for(Rute[] ar: labyrint) {
            for(Rute r: ar) {
                String e = Character.toString(r.tilTegn());
                labyrintUtskrift += e;
            }
            labyrintUtskrift += "\n";
        }
        return labyrintUtskrift;
    }
}
