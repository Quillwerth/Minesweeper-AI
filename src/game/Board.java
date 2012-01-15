package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
	/* Board:
	 * Holds a 2D integer array, containing the position of bombs (defined as negative numbers), 
	 * or, if a particular square is not a bomb, how many squares around it are.
	 * 
	 * When a move is made, the Board class constructs a new view for the player, showing the player only what they have already revealed.
	 * In this way, we know that no one can cheat: The raw data is not able to be seen from outside the game (it is private).
	 * 
	 * Also contains a list of moves, to allow display() to determine what to display.
	 * 
	 * NOTE: Some functionality for flagging has been added, but display doesn't know what to do with it.
	 * Add this later, if you find it necessary.
	 */
	
	private int[][] rawBoard;
	int b;
	private ArrayList<Coord> moves = new ArrayList<Coord>();
	
	public Board(int row, int col, int bombs){
		//Generate rawBoard, with dimensions row X col, and place (bombs) bombs.
		rawBoard = new int[row][col];
		b = bombs;
		for(int[] i : rawBoard){//Initial fill of zeros.
			Arrays.fill(i, 0);
		}
		Random r = new Random();
		while(bombs>0){
			int rPos = r.nextInt(row);
			int cPos = r.nextInt(col);
			if(rawBoard[rPos][cPos]>=0){
				rawBoard[rPos][cPos]=-9;// -9 will never be incremented to a non-negative number by surrounding squares.
				for(int x = -1; x<2; x++){
					for(int y = -1; y<2; y++){
						//Increment surrounding squares
						try{
							rawBoard[rPos+x][cPos+y]++;
						}catch(ArrayIndexOutOfBoundsException e){
							continue;
						}
					}
				}
				bombs--;
				continue;
			}else{
				continue;
			}
		}
	}
	
	private boolean isBomb(int r, int c){
		if(rawBoard[r][c]<0){
			return true;
		}
		return false;
	}
	
	int move(int r, int c, String t){
		//Returns an integer: The number of elapsed moves. If negative, a bomb was found.
		//This method is only visible to classes in the same package.
		Coord newMove = new Coord(r, c, t);
		for(int i = 0; i<moves.size(); i++){
			if(moves.get(i).isSameLocation(newMove) && moves.get(i).getTag().equalsIgnoreCase("m")){
				return moves.size();//Do not add the move. It has already happened, no need to do anything else.
			}
		}
		moves.add(newMove);
		if(isBomb(r,c) && newMove.getTag().equalsIgnoreCase("f")==false){//Makes sure player wasn't just flagging the bomb.
			return moves.size()*-1;
		}else{
			return moves.size();
		}
	}
	
	public char[][] display(){
		//The meat of this class. Uses the rMoves and cMoves lists to generate a view for the player.
		char[][] visibleBoard = new char[rawBoard.length][rawBoard[0].length];
		for(char[] c : visibleBoard){
			Arrays.fill(c, '#');
		}
		for(Coord c : moves){
			int sq = rawBoard[c.getX()][c.getY()];
			String num = sq + "";
			//Check if the square is flagged. All bombs must be flagged to win.
			if(c.getTag().equalsIgnoreCase("f")){
				if(visibleBoard[c.getX()][c.getY()]=='#'){
					visibleBoard[c.getX()][c.getY()]='^';
					continue;
				}
				if(visibleBoard[c.getX()][c.getY()]=='^'){
					visibleBoard[c.getX()][c.getY()]='#';//Unflag the square, if it's flagged twice.
					continue;
				}
			}else{
			visibleBoard[c.getX()][c.getY()] =
					(sq==0 ? '-' : sq<0 ? '*' : num.charAt(0));
			}
		}
		boolean changes;
		//Seek through the board for '-', and reveal all the squares around it as well.
		do{
			changes = false;
			for(int r = 0; r<visibleBoard.length; r++){
				for(int c = 0; c<visibleBoard[0].length; c++){//visibleBoard[0] is the first row, and thus gives the width of the board.
					
					if(visibleBoard[r][c] == '-'){//This will get changed to an underscore so we don't get into infinite loops.
						visibleBoard[r][c] = '_';
						for(int x = -1; x<2; x++){
							for(int y = -1; y<2; y++){
								try{
									if(visibleBoard[r+x][c+y]=='#'){//If it is unknown...
										int sq = rawBoard[r+x][c+y];
										String num = sq+"";
										visibleBoard[r+x][c+y] = (sq==0 ? '-' : num.charAt(0));
									}else{
										continue;
									}
								}catch(ArrayIndexOutOfBoundsException e){
									continue;
								}
							}
						}
						changes = true;
					}
				}
			}
		}while(changes);
		return visibleBoard;
	}
	
	boolean isWinner(){
		//Determines player win by checking the board for '#'. If there are none, all squares have been selected or flagged, correctly.
		char[][] checkBoard = display();
		int bCount = 0;
		for(char[] row : checkBoard){
			for(char c : row){
				if(c=='#'){
					return false;
				}
				if(c=='^'){
					bCount++;
				}
			}
		}
		if(bCount!=b){
			return false;//Too many bombs, someone's trying to abuse the flagging mechanism!
		}
		return true;
	}
	
	void initialMove(){
		//Finds a zero that's reasonably close to the center of the board, and selects that as a move.
		//Since the first move of Minesweeper is totally arbitrary, this version does it for you!
		int xCent = (rawBoard.length/2); int yCent = (rawBoard[0].length/2);
		int radius = 1;
		boolean firstMove = false;
		while(!firstMove){
			for(int x = radius*-1; x <= radius; x++){
				for(int y = radius*-1; y <= radius; y++){
					try{
						if(rawBoard[xCent+x][yCent+y]==0){
							firstMove = true;
							move(xCent+x, yCent+y, "m");
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){
						continue;
					}
				}
			}
		}
	}
}
