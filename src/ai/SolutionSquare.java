package ai;

import java.util.ArrayList;

import game.Coord;

public class SolutionSquare {
	//Takes a char[][] representation of a minesweeper board and coordinates. It figures out if the particular coordinates can be solved:
	//If the number in the center is equal to the number of hashes around it, all the hashes must be bombs.
	int number;
	Coord location;
	boolean solved;
	ArrayList<Coord> moves = new ArrayList<Coord>();
	
	public SolutionSquare(char[][] board, Coord loc){
		location = loc;
		try{
			number = new Integer(new Character(board[loc.getX()][loc.getY()]).toString());
		}catch(NumberFormatException e){//Whoops, that square isn't a number!
			number = 0;
			solved = false;
			return;
		}
		int hashed = 0;
		int flagged = 0;
		//If flagged equals the number, then we found all the bombs: the rest are moves that need to be made.
		//If flagged doesn't equal the number, but flagged + hashes does, then they're all bombs.
		//Else, we don't have enough information.
		for(int x = -1; x < 2; x++){
			for(int y = -1; y < 2; y++){
				try{
					char c = board[loc.getX()+x][loc.getY()+y];
					if(c=='#'){
						hashed++;
					}
					else if(c=='^'){
						flagged++;
					}
				}catch(ArrayIndexOutOfBoundsException e){
					continue;
				}
			}
		}
		String tag = "";
		
		if(flagged == number){
			solved = true;
			tag = "m";
		}
		if((flagged + hashed) == number){
			solved = true;
			tag = "f";
		}
		if(solved){
			for(int x = -1; x < 2; x++){
				for(int y = -1; y < 2; y++){
					try{
						char c = board[loc.getX()+x][loc.getY()+y];
						if(c == '#'){
							moves.add(new Coord(loc.getX()+x, loc.getY()+y, tag));
						}
					}catch(ArrayIndexOutOfBoundsException e){
						continue;
					}
				}
			}
		}else{
			solved = false;
		}
		return;
	}
	
	public boolean isSolved(){
		return solved;
	}
	
	public ArrayList<Coord> getMoves(){
		return moves;
	}
	
	
	
}
