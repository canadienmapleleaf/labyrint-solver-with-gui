import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.application.Platform;
import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUILabyrint extends Application {
    Stage primaryStage;
    Labyrint lab;
    private Labyrint l;
    Button[][] rutebeholder;
    BorderPane borderPane = new BorderPane();
    GridPane grid = new GridPane();
    int antRad;
    int antKol;

    /**
    *klasse for å behandle klikk på hvite ruter.
    */
    class KlikkeBehandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            HvitKnapp knapp = ((HvitKnapp)e.getSource());
            int rad = knapp.hentRad();
            int kol = knapp.hentKol();
            for(int i = 0; i<antRad; i++) {
                for(int j = 0; j<antKol; j++) {
                    if(rutebeholder[i][j] instanceof HvitKnapp) {
                        rutebeholder[i][j].setStyle("-fx-background-color: #FFFFFF");
                    }
                }
            }
            finnLosning(rad, kol);
        }
    }
    /**
    *klasse for å behandle klikk på resettknapp
    */
    class ResettBehandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent a) {
            for(int i = 0; i<antRad; i++) {
                for(int j = 0; j<antKol; j++) {
                    if(rutebeholder[i][j] instanceof HvitKnapp) {
                        rutebeholder[i][j].setStyle("-fx-background-color: #FFFFFF");
                    }
                }
            }
        }
    }
    /**
    *klasse for å behandle klikk på stoppknapp.
    */
    class StoppBehandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Platform.exit();
        }
    }

    /**
    *klasse for å behandle trykk på hvite felt.
    *int-variabler for å holde aktuell rad og kolonne.
    */
    class HvitKnapp extends Button {
        private int rad;
        private int kol;

        public HvitKnapp(int rad, int kol) {
            this.kol = kol;
            this.rad = rad;
        }

        public int hentRad() {
            return rad;
        }

        public int hentKol() {
            return kol;
        }
    }

    /**
    *metode som finner løsninger ved hjelp av int rad, int kol
    *@param int rad og int kolonne for aktuell rute
    */
    private void finnLosning(int rad, int kol) {
        Liste<String> listeMedUtveier = lab.finnUtveiFra(kol, rad);
        //int antRad = lab.hentAntRad(); int antKol = lab.hentAntKol();
        //byttet ut while med if etter forslag fra retter
        //får bare en løsning nå
        if(listeMedUtveier.stoerrelse()>0) {
            String utvei = listeMedUtveier.fjern();
            boolean[][] bolly = losningStringTilTabell(utvei, antKol, antRad);
            for(int i = 0; i<antRad; i++) {
                for(int j = 0; j<antKol; j++) {
                    if(bolly[i][j]) {
                        rutebeholder[i][j].setStyle("-fx-background-color: #008000");
                    }
                }
            }
        }
    }
    /**
    *privat labyrint-metode som bruker statisk metode
    *i Labyrint-klasse til å behandle valgt fil.
    */
    private Labyrint lesFraFil() {
        FileChooser fileChooser = new FileChooser();
        File fil = fileChooser.showOpenDialog(primaryStage);
        if(fil!=null) {
            try {
                l = Labyrint.lesFraFil(fil);
                borderPane.setCenter(grid);
            }
            catch(Exception n) {
                System.out.printf("Feil. Kunne ikke lese fra '%s'\n", fil.getPath());
            }
        } else {
            System.out.println("Ingen fil valgt, dessverre.");
        }
        return l;
    }

    /**
    *prekode
    */
    static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
        boolean[][] losning = new boolean[hoyde][bredde];
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
        java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s",""));
        while (m.find()) {
            int x = Integer.parseInt(m.group(1));
            int y = Integer.parseInt(m.group(2));
            losning[y][x] = true;
        }
        return losning;
    }

    /**
    *@param tar inn Stage primaryStage som er vårt "teater"
    *vår GUI-tråd.
    */
    @Override
    public void start(Stage primaryStage) {
        lab = lesFraFil();
        antRad = lab.hentAntRad();
        antKol = lab.hentAntKol();
        this.primaryStage = primaryStage;
        rutebeholder = new Button[antRad][antKol];
        for(int i = 0; i<antRad; i++) {
            for(int j = 0; j<antKol; j++) {
                Rute now = lab.hentRute(j, i);
                if(now instanceof HvitRute) {
                    KlikkeBehandler klikk = new KlikkeBehandler();
                    HvitKnapp hvitFelt = new HvitKnapp(i, j);
                    hvitFelt.setOnAction(klikk);
                    hvitFelt.setStyle("-fx-background-color: #FFF5EE");
                    rutebeholder[i][j] = hvitFelt;
                    grid.add(hvitFelt, j, i);
                }
                else if(now instanceof SvartRute) {
                    Button svartKnapp = new Button();
                    svartKnapp.setStyle("-fx-background-color: #191970");
                    rutebeholder[i][j] = svartKnapp;
                    grid.add(svartKnapp, j, i);
                }
            }
        }

        Button stoppKnapp = new Button("STOPP");
        StoppBehandler stopp = new StoppBehandler();
        stoppKnapp.setOnAction(stopp);

        Button resettKnapp = new Button("RESETT");
        ResettBehandler resetter = new ResettBehandler();
        resettKnapp.setOnAction(resetter);

        grid.setAlignment(Pos.CENTER);
        borderPane.setCenter(grid);
        borderPane.setTop(stoppKnapp);
        borderPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, null, new BorderWidths(0.9))));
        borderPane.setBottom(resettKnapp);
        Scene scene = new Scene(borderPane);

        primaryStage.setTitle("Labyrint");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
