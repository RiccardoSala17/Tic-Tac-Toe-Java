package gioco;

public class Utente {

	private String nome;
    private char simbolo;
    private int punteggio;
    
    //costruttore
    public Utente (String nome, int punteggio) {
        this.nome = nome;
        this.punteggio= punteggio;
    }

    public Utente (char simbolo) {
        this.simbolo = simbolo;
    }

    //getters
    public String getNome() {
        return nome;
    }

    public char getSimbolo(){
        return simbolo;
    }

    public int getPunteggio() {
        return punteggio;
    }
    
    //setters
    public void setNome(String nome) {

    	if(nome != null && !nome.isEmpty()) {

			this.nome = nome;
    	}
    }
    
    public void setSimbolo (char simbolo) {

    	if(simbolo != ' ' && simbolo != 0) {

			this.simbolo = simbolo;
    	}
    }
    
    public void setPunteggio(int punteggio) {
    	this.punteggio = punteggio;
    }	
}
