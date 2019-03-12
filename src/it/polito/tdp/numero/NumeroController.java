package it.polito.tdp.numero;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class NumeroController {
	
	private final int NMAX = 100;
	private final int TMAX = 8;
			//ogni volta che si fa un tentativo si dimezza il range possibile

	private int segreto;
	private int tentativiFatti; //rimasti uguale a TMAX -tentativiFatti
	private boolean inGioco = false; //disable di controllo partita deve essere uguale a inGioco
	//controllo tentativi opposto a inGioco
	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxControlloPartita;

    @FXML
    private TextField txtRimasti;

    @FXML
    private HBox boxControlloTentativo;

    @FXML
    private TextField txtTentativo;

    @FXML
    private TextArea txtMessaggi;

    @FXML
    void handleNuova(ActionEvent event) {

    	//gestisce l'inizio di una nuova partita
    	
    	
    	//logica del gioco
    	this.segreto = (int)( Math.random()*NMAX); //Math.random() mi dà numero reale tra 0 e 1
    	//moltiplico per NMAX e prendo la parte intera
    	this.tentativiFatti=0;
    	this.inGioco=true;
    	
    	//gestione dell'interfaccia
    	//quando inizio una parita devo disabilitare la prima hbox e abilitare la seconda hbox
    	boxControlloPartita.setDisable(true); //disabilito
    	boxControlloTentativo.setDisable(false); //non disabilito -> abilito
    	txtMessaggi.clear();
    	txtRimasti.setText(Integer.toString(this.TMAX));
    }

    @FXML
    void handleProvaTentativo(ActionEvent event) {

    	//leggi il valore del tentativo
    	String ts = txtTentativo.getText();
    	int tentativo;
    	
    	//controlla se è valido
    	try {
    	tentativo = Integer.parseInt(ts); //può generare NumberFormatException, l'utente non ha inserito un numero
    	} catch (NumberFormatException e) {
    		//la stringa inserita non è un numero valido
    		txtMessaggi.appendText("Non è un numero valido");
    		return;
    	}
    	
    	tentativiFatti++;
    	
    	//controlla se hai indovinato
    	// -> fine partita
    	if(tentativo==segreto) {
    		txtMessaggi.appendText("Complimenti, hai indovinato in "+tentativiFatti+"tentativi\n");
    	
    		boxControlloPartita.setDisable(false);
    		boxControlloTentativo.setDisable(true);
    		this.inGioco=false;
    		return;
    	}
    	//verifica se ha esaurito i tentativi
    	// -> fine partita
    	if(tentativiFatti==TMAX) {
    		txtMessaggi.appendText("Hai perso, il numero segreto era: "+segreto+"\n");
    		
    		boxControlloPartita.setDisable(false);
    		boxControlloTentativo.setDisable(true);
    		this.inGioco=false;
    		return;
    	}
    	//informa se era troppo alto o basso
    	// -> stampa messaggio
    	if(tentativo < segreto) {
    		txtMessaggi.appendText("Tentativo troppo BASSO\n");
    		
    	}else {
    		txtMessaggi.appendText("Tentativo troppo ALTO\n");
    	}
    
    	//aggiornare interfaccia con tentativi rimasti
    	txtRimasti.setText(Integer.toString(TMAX-tentativiFatti));
    }

    @FXML
    void initialize() {
        assert boxControlloPartita != null : "fx:id=\"boxControlloPartita\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtRimasti != null : "fx:id=\"txtRimasti\" was not injected: check your FXML file 'Numero.fxml'.";
        assert boxControlloTentativo != null : "fx:id=\"boxControlloTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtTentativo != null : "fx:id=\"txtTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtMessaggi != null : "fx:id=\"txtMessaggi\" was not injected: check your FXML file 'Numero.fxml'.";

    }
}
