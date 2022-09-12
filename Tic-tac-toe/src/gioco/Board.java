package gioco;

public class Board {

	static char[][] aB = new char [3][3];
	
	public static void newBoard() {

		for (int i = 0; i < 3; i++) {
			for( int j = 0; j < 3; j++) {
				aB[i][j] = ' ';
			}
		}
	}
	
	public static void printBoard() {
		
		System.out.println();
		System.out.println("           colonna   ");
		System.out.println("          1   2   3  ");
		System.out.println("   riga                  ");
		System.out.println("    1    [" + aB[0][0] + "] [" + aB[0][1] + "] [" + aB[0][2] + "] ");
		System.out.println("    2    [" + aB[1][0] + "] [" + aB[1][1] + "] [" + aB[1][2] + "] ");
		System.out.println("    3    [" + aB[2][0] + "] [" + aB[2][1] + "] [" + aB[2][2] + "] ");
		System.out.println("                     ");
		System.out.println();
	}
}
