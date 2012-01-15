package test;
import game.*;
public class GameGrounds {

	public static void main(String[] args) {
		Board B = new Board(10, 10, 3);
		Referee Stripes = new Referee(B);
		Stripes.playHuman();

	}

}
