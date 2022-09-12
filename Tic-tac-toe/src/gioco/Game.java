package gioco;
import java.util.Scanner;

public class Game {	
	
		//variabili di gestione gioco
		private static int gameCnt = 0;
		private static int manche = 0;
		private static int turns = 0;
		private static String currentPlayer = "";
		
		//pixel art
		public static final String trisLine= "\n\n /$$$$$$$$ /$$$$$$$  /$$$$$$  /$$$$$$" +
			"\n|__  $$__/| $$__  $$|_  $$_/ /$$__  $$"+
			"\n   | $$   | $$  \\ $$  | $$  | $$  \\__/"+
			"\n   | $$   | $$$$$$$/  | $$  |  $$$$$$" +
			"\n   | $$   | $$__  $$  | $$   \\____  $$"+
			"\n   | $$   | $$  \\ $$  | $$   /$$  \\ $$"+
			"\n   | $$   | $$  | $$ /$$$$$$|  $$$$$$/"+
			"\n   |__/   |__/  |__/|______/ \\______/" ;

		public static final String winLine= "\n\n /$$    /$$ /$$$$$$ /$$$$$$$$ /$$$$$$$$ /$$$$$$  /$$$$$$$  /$$$$$$  /$$$$$$ " +
			"\n| $$   | $$|_  $$_/|__  $$__/|__  $$__//$$__  $$| $$__  $$|_  $$_/ /$$__  $$" +
			"\n| $$   | $$  | $$     | $$      | $$  | $$  \\ $$| $$  \\ $$  | $$  | $$  \\ $$" +
			"\n|  $$ / $$/  | $$     | $$      | $$  | $$  | $$| $$$$$$$/  | $$  | $$$$$$$$" +
			"\n \\  $$ $$/   | $$     | $$      | $$  | $$  | $$| $$__  $$  | $$  | $$__  $$" +
			"\n  \\  $$$/    | $$     | $$      | $$  | $$  | $$| $$  \\ $$  | $$  | $$  | $$" +
			"\n   \\  $/    /$$$$$$   | $$      | $$  |  $$$$$$/| $$  | $$ /$$$$$$| $$  | $$" +
			"\n    \\_/    |______/   |__/      |__/   \\______/ |__/  |__/|______/|__/  |__/";

		public static final String winLineEaster= "\n\n  /$$$$$$   /$$$$$$  /$$$$$$$$ /$$$$$$  /$$   /$$ /$$$$$$  /$$$$$$   /$$$$$$"+ 
			"\n /$$__  $$ /$$__  $$|__  $$__//$$__  $$| $$$ | $$|_  $$_/ /$$__  $$ /$$__  $$"+
			"\n| $$  \\__/| $$  \\ $$   | $$  | $$  \\ $$| $$$$| $$  | $$  | $$  \\__/| $$  \\ $$"+
			"\n|  $$$$$$ | $$$$$$$$   | $$  | $$$$$$$$| $$ $$ $$  | $$  | $$      | $$$$$$$$"+
			"\n \\____  $$| $$__  $$   | $$  | $$__  $$| $$  $$$$  | $$  | $$      | $$__  $$"+
			"\n /$$  \\ $$| $$  | $$   | $$  | $$  | $$| $$\\  $$$  | $$  | $$    $$| $$  | $$"+
			"\n|  $$$$$$/| $$  | $$   | $$  | $$  | $$| $$ \\  $$ /$$$$$$|  $$$$$$/| $$  | $$"+
			"\n \\______/ |__/  |__/   |__/  |__/  |__/|__/  \\__/|______/ \\______/ |__/  |__";
		
		//metodo che gestisce il game flow
		public static void game(Utente u1, Utente u2) {	
			
			//operazioni preliminari e di reset
			Game.assP1(u1,u2);
			Board.newBoard();
			
			//ciclo di gioco 9 turni max
			for(; turns < 9;turns++) {
				Game.turn(u1,u2);
				Board.printBoard();
				if (Game.winCheck())
					break;
			}
			//operazioni di endgame
			Game.epilogue(u1,u2);
			Game.retry(u1,u2);
		}
		
		//introduzione e getting dei nomi
		public static void intro(Utente u1, Utente u2) {
			
			System.out.println(trisLine);
			System.out.println("\nCiao Giocatore1, come ti chiami?");
			String risp1 = new Scanner(System.in).nextLine();

			//easter egg1
			if (risp1.toLowerCase().equals("francesco") || risp1.toLowerCase().equals("giovanni")) {
				System.out.println("\nAhhhh sei tu! Assaggerai la nostra vendetta!");
				u1.setPunteggio(-100); 
			}
			u1.setNome(risp1);
			System.out.println("\nBenvenuto " + u1.getNome());
			System.out.println("\nCiao Giocatore2, come ti chiami?");
			String risp2 = new Scanner(System.in).nextLine();

			//easter egg1
			if (risp2.toLowerCase().equals("francesco") || risp2.toLowerCase().equals("giovanni")) {
				System.out.println("\nAhhhh sei tu! Assaggerai la nostra vendetta!");
				u2.setPunteggio(-100); 
			}

			u2.setNome(risp2);
			System.out.println("\nBenvenuto " + u2.getNome());
			Board.newBoard();
			Board.printBoard();
			System.out.println("\nQuesto e' il gioco del Tris, inserite i vostri simboli nella griglia"); 
			System.out.println("fin quando non otterrete 3 simboli uguali prima del vostro avversario!");
		}

		//assegnazine di simbolo e primo giocatore
		public static void assP1(Utente u1, Utente u2) {

			System.out.println("\nPremi Invio per continuare...");
			String ln = new Scanner(System.in).nextLine();

			if(gameCnt%2 == 0) {
				
				u1.setSimbolo('X');
				u2.setSimbolo('O');
				System.out.println("\nIl giocatore " + u1.getNome() + " avra' la 'X' e iniziera' per primo");
			}
			else {
				
				u1.setSimbolo('O');
				u2.setSimbolo('X');
				System.out.println("\nIl giocatore " + u2.getNome() + " avra' la 'X' e iniziera' per primo");
			}
		}
		
		//turno
		public static void turn(Utente u1, Utente u2) {

			int r;
			int c;

			//indicatore a 2 stati per gestire i turni di player1 e player2
			manche = (manche == 2) ? 0 : manche;
			currentPlayer = (manche == 0) ? p1Nome(u1,u2) : p2Nome(u1,u2);
			System.out.println("\nForza " + currentPlayer + "! E' il tuo turno!");
			
			//loop di gioco con check dei valori
			while(true) {
				while (true) {
					System.out.println("\nQuale riga scegli?");
					r = new Scanner(System.in).nextInt();
					
					//easter egg 2
					if (r == 666) {
						System.out.println(winLine);
						System.out.println(winLineEaster);
						System.exit(0);
					}
					else if (r >= 1 && r <= 3)
						break;
					else 
						System.out.println("\nCoordinate non valide");
				} 

				while(true) {
					System.out.println("\nQuale colonna scegli?");
					c = new Scanner(System.in).nextInt();

					if (c >= 1 && c <= 3)
						break;
					else 
						System.out.println("\nCoordinate non valide");
				}
				if (Board.aB[r-1][c-1] == ' ')
					break;
				else 
					System.out.println("\nCoordinate non valide");
			}

			Board.aB[r-1][c-1] = (manche == 0) ? 'X' : 'O';
			manche++;			
		}
		
		//check delle condizioni di vittoria
		public static boolean winCheck() {
		    //check sulle righe
		    for (int r = 0; r < 3; r++) {

		        if (Board.aB[r][0] == Board.aB[r][1] && Board.aB[r][1] == Board.aB[r][2] && Board.aB[r][2] != ' ')
		            return true;
		    }
		    
		    //check sulle colonne
		    for (int c = 0; c < 3; c++) {

		        if (Board.aB[0][c] == Board.aB[1][c] && Board.aB[1][c] == Board.aB[2][c] && Board.aB[2][c] != ' ' )
		            return true;
		    }
		    
		    //checks sulle diagonali
		    if (Board.aB[0][0] == Board.aB[1][1] && Board.aB[1][1] == Board.aB[2][2] && Board.aB[1][1] != ' ')
		        return true;

		    if (Board.aB[0][2] == Board.aB[1][1] && Board.aB[1][1] == Board.aB[2][0] && Board.aB[0][2] != ' ')
		        return true;
		    return false;
		}
		
		//fine del gioco e assegnazione punti
		public static void epilogue(Utente u1, Utente u2) {
			
			//check delle condizioni di fine partita
			if (turns == 9)
				System.out.println("\nAvete pareggiato!");
			else {
				System.out.println("\nHa vinto il giocatore " + currentPlayer +"!");
				System.out.println(winLine);

				if (currentPlayer.equals(u1.getNome()))
				u1.setPunteggio(u1.getPunteggio() + 1);
				
				else
						u2.setPunteggio(u2.getPunteggio() + 1);		
			}			
			System.out.println("\nPremi Invio per continuare...");
			String ln = new Scanner(System.in).nextLine();

			//pubblicazione del punteggio e commenti sulla sfida
			System.out.println("\nVittorie di " + u1.getNome() + ": " + u1.getPunteggio());
			System.out.println("\nVittorie di " + u2.getNome() + ": " + u2.getPunteggio());

			if (u1.getPunteggio() == u2.getPunteggio())
				System.out.println("\nSiete testa a testa!");

			else if (u1.getPunteggio() + 4 < u2.getPunteggio())
				System.out.println("\nChe disastro " + u1.getNome() + "! " + u2.getNome() + " ti sta stracciando!");

			else if (u1.getPunteggio() > 4 + u2.getPunteggio())
				System.out.println("\nChe disastro " + u2.getNome() + "! " + u1.getNome() + " ti sta stracciando!");

			else if (u1.getPunteggio() < u2.getPunteggio())
				System.out.println("\n" + u2.getNome() + " sta vincendo, ma " + u1.getNome() + " puo' ancora recuperare");

			else if (u1.getPunteggio() > u2.getPunteggio())
				System.out.println("\n" + u1.getNome() + " sta vincendo, ma " + u2.getNome() + " puo' ancora recuperare");
		}
		
		//metodo per rigiocare la partita
		public static void retry(Utente u1, Utente u2) {

		    String risp1;
		    String risp2;

		    do {

		        System.out.println("\nVuoi riprovare? (Y/N)");
		        risp1 = new Scanner(System.in).nextLine().toLowerCase();
		    
		    }while (risp1.charAt(0) != 'y' && risp1.charAt(0) != 'n');

		    if (risp1.charAt(0) == 'n') {

		        System.out.println("\nCi si vede in giro!");
		        System.exit(0);
		    }
		    do {

		        System.out.println("\nVuoi azzerare i punteggi? (Y/N)");
		   		risp2 = new Scanner(System.in).nextLine().toLowerCase();
		   
		    }while (risp2.charAt(0) != 'y' && risp2.charAt(0) != 'n');

		    if (risp2.charAt(0) == 'y') {

		        u1.setPunteggio(0);
		        u2.setPunteggio(0);
		    }	
		    
		    //reset statistiche e inizio nuovo gioco
		    System.out.println("\nBenissimo! Facciamone un'altra!");
		    gameCnt++;
		    manche = 0;
		    turns = 0;
		    Game.game(u1,u2);
		}
		
		//metodi di assegnazione del nome primo giocatore
		public static String p1Nome(Utente u1, Utente u2) {

			String p1Nome = (u1.getSimbolo() == 'X') ? u1.getNome() : u2.getNome();
				return p1Nome;
		}
		
		public static String p2Nome(Utente u1, Utente u2) {

			String p1Nome = (u1.getSimbolo() == 'O') ? u1.getNome() : u2.getNome();
			return p1Nome;
		}	
}
