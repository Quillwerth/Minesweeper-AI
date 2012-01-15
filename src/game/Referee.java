package game;

import java.util.Scanner;

public class Referee {
	/* Referee:
	 * Manages the game. Takes in moves from the player, manages display of the game, parses moves.
	 * 
	 */
	Board b;
	
	public Referee(Board B){
		b = B;
	}
	
	public void startGame(){
		b.initialMove();
		
	}
	
	public void displayBoard(){
		char[][] knownBoard = b.display();
		System.out.print("  ");
		for(int i = 0; i<knownBoard.length; i++){
			System.out.print(i%10);
			System.out.print(" ");
		}
		System.out.println();
		int i = 0;
		for(char[] row : knownBoard){
			System.out.print(i%10); System.out.print(" ");
			for(char c : row){
				System.out.print(c);
				System.out.print(" ");
			}
			i++;
			System.out.println("\n");
		}
	}
	
	public char[][] getBoard(){
		return b.display();//For AI's.
	}
	
	public int turn(String move){//Parses move, a string with two numbers separated by a comma, and uses that in the Board's move function.
		String[] parsedMove = move.split(",");
		if(parsedMove.length == 3){
			return b.move(new Integer(parsedMove[0]), new Integer(parsedMove[1]), parsedMove[2]);
		}
		return b.move(new Integer(parsedMove[0]), new Integer(parsedMove[1]), "m");
	}
	
	public void playHuman(){
		Scanner sc = new Scanner(System.in);
		startGame();
		while(!b.isWinner()){
			displayBoard();
			System.out.println("Make move:");
			if(turn(sc.nextLine())<0){
				System.out.println("Bang! HA HA!");
				break;
			}
			if(b.isWinner()){
				System.out.println("VICTORY!!");
			}
		}
		System.out.println("Game Over!");
	}
}
