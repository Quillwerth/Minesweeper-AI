package ai;

import game.Board;
import game.Coord;
import game.Referee;

import java.util.ArrayList;
import java.util.Random;

public class Bot {
	//Creates and manages SolutionSquares, and plays moves.
	
	//KNOWN ISSUES: If NO solved squares exist, there is no way for the AI to proceed.
	//Solution? Find the 'safest bet'.
	ArrayList<Coord> listOfMoves = new ArrayList<Coord>();
	
	
	public void findMoves(char[][] b){
		listOfMoves.clear();
		for(int x = 0; x < b.length; x++){
			for(int y = 0; y < b[0].length; y++){
				SolutionSquare s = new SolutionSquare(b, new Coord(x, y, "meh"));//Everything's in the constructor. Nice and clean.
				if(s.isSolved()){
					for(Coord c : s.getMoves()){
						boolean inList = false;
						for(Coord listed : listOfMoves){
							if (c.isSame(listed)){//Checks list for duplicates, or else we get the same move repeated.
								inList = true;
								break;
							}
						}
						if(inList){
							continue;
						}else{
							listOfMoves.add(c);
						}
					}
				}
			}
		}
	}
	
	public String makeMove(Referee r){
		//Does the first move on the list, then clears the list.
		//Hideously inefficient, but: this way, you only get a move once, not over and over.
		//I should probably fix this, but meh
		
		//So, those comments above? Fixed this problem with about 3 minutes of coding.
		//Whose house? SEV'S HOUSE
		if(listOfMoves.isEmpty()){
			findMoves(r.getBoard());
		}
		String ret;
		try{
			ret = listOfMoves.get(0).toString();
			listOfMoves.remove(0);
		}catch(IndexOutOfBoundsException e){
			//If we have no solved squares, randomly select a square.
			//TODO: Make this less sucky.
			Random guess = new Random();
			char[][] curBoard = r.getBoard();
			System.out.println("Oh noes! Random Guess.");
			ret = new Coord(guess.nextInt(curBoard.length), guess.nextInt(curBoard[0].length), "m").toString();//Random ass guess.
		}
		return ret;
	}
	
}
