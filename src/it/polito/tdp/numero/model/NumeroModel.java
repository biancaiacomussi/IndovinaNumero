package it.polito.tdp.numero.model;

import java.security.InvalidParameterException;

public class NumeroModel {

	private final int NMAX = 100;
	private final int TMAX = 8;
			//ogni volta che si fa un tentativo si dimezza il range possibile

	private int segreto;
	private int tentativiFatti; //rimasti uguale a TMAX -tentativiFatti
	private boolean inGioco = false; //disable di controllo partita deve essere uguale a inGioco
	//controllo tentativi opposto a inGioco
	
	public NumeroModel() {
		inGioco=false;
	}
	
	public void newGame() {
		inGioco = true;
		this.segreto = (int)( Math.random()*NMAX) +1; //Math.random() mi dà numero reale tra 0 e 1
    	//moltiplico per NMAX e prendo la parte intera
    	this.tentativiFatti=0;
	}
	
	/**
	 * Metodo per effettuare un tentativo
	 * 1 se il tentativo è troppo alto
	 * 0 se il numero è indovinato
	 * -1 se il tentativo è troppo basso
	 * 
	 */
	
	public int tentativo(int t) {
		//controllo se la partita è in corso
		if(!inGioco) {
			throw new IllegalStateException("La partita è terminata");
		}
		
		//controllo se l'input è nel range corretto
		if(t<1 || t>NMAX) {
			throw new InvalidParameterException(String.format("Devi inserire un numero " + "tra %d e %d", 1, NMAX));
		} //concatena stringhe
		
		//gestisci tentativo
		this.tentativiFatti++;
		if(this.tentativiFatti == this.TMAX) {
			//la partita è finita perchè ho esaurito i tentativi
			this.inGioco = false;			
		}
		
		if(t == this.segreto) { //se ho indovinato la partita finisce
			this.inGioco = false;
			return 0;			
		}
		
		if(t > this.segreto) {
			return 1;
		}
		
		return -1;
	}
	
	public int getTMAX() {
		return TMAX;
	}

	public int getSegreto() {
		return segreto;
	}

	public int getTentativiFatti() {
		return tentativiFatti;
	}

	public boolean isInGioco() {
		return inGioco;
	}

	public boolean tentativoValido(int t) {
		if(t<1 || t>NMAX) {
			return false;
		} else {
			return true;
		}
	}
}
