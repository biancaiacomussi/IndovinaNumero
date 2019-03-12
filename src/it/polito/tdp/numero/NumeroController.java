package it.polito.tdp.numero;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.numero.model.NumeroModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class NumeroController {
	
	private NumeroModel model;
	
	
	
	
	
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
    	
    
    	//gestione dell'interfaccia
    	//quando inizio una parita devo disabilitare la prima hbox e abilitare la seconda hbox
    	boxControlloPartita.setDisable(true); //disabilito
    	boxControlloTentativo.setDisable(false); //non disabilito -> abilito
    	txtMessaggi.clear();
    	txtRimasti.setText(Integer.toString(model.getTMAX()));
    	
    	//comincio una nuova partita
    	model.newGame();
    }

    @FXML
    void handleProvaTentativo(ActionEvent event) {

    	//leggi il valore del tentativo
    	String ts = txtTentativo.getText();
    	int tentativo;
    	
    	//controlla se è valido il tipo di dato
    	try { //va bene qui e non nel modello perchè si presuppone che il modello riceva i dati giusti
    	tentativo = Integer.parseInt(ts); //può generare NumberFormatException, l'utente non ha inserito un numero
    	} catch (NumberFormatException e) {
    		//la stringa inserita non è un numero valido
    		txtMessaggi.appendText("Non è un numero valido");
    		return;
    	}
    	
    	if(!model.tentativoValido(tentativo)) {
    		txtMessaggi.appendText("Range non valido\n");
    		return;
    	}
    	
    	int risultato = model.tentativo(tentativo);
    	
    	if(risultato==0) {
    		
    		txtMessaggi.appendText("Complimenti, hai indovinato in "+model.getTentativiFatti()+"tentativi\n");
        	boxControlloPartita.setDisable(false);
    		boxControlloTentativo.setDisable(true);
    		
    	} else if (risultato<0){
    		txtMessaggi.appendText("Tentativo troppo BASSO\n");
    	} else {
    		txtMessaggi.appendText("Tentativo troppo ALTO\n");
    	}
    	
    	//Aggiornare interfaccia con n. tentativi rimasti
    	txtRimasti.setText(Integer.toString(model.getTMAX()-model.getTentativiFatti()));
    	
    	
    	if(!model.isInGioco()) {
    		//la partita è finita
    		if(risultato!=0) //ho finito i tentativi
    			txtMessaggi.appendText("Hai perso!");
    			txtMessaggi.appendText(String.format("\nIl numero segreto era: %d", model.getSegreto()));
    			boxControlloPartita.setDisable(false);
        		boxControlloTentativo.setDisable(true);
    	}
    	    	
    }

    @FXML
    void initialize() {
        assert boxControlloPartita != null : "fx:id=\"boxControlloPartita\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtRimasti != null : "fx:id=\"txtRimasti\" was not injected: check your FXML file 'Numero.fxml'.";
        assert boxControlloTentativo != null : "fx:id=\"boxControlloTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtTentativo != null : "fx:id=\"txtTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtMessaggi != null : "fx:id=\"txtMessaggi\" was not injected: check your FXML file 'Numero.fxml'.";

    }
    
    public void setModel(NumeroModel model) {
		this.model = model;
	}

}
