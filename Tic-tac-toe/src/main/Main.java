package main;
import gioco.*;

public class Main {
	
	public static void main(String[] args) {
		
		Utente u1 = new Utente("", 0);
		Utente u2 = new Utente("",0);
		Game.intro(u1, u2);
		Game.game(u1, u2);
	}
}