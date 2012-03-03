package test;
import ai.Bot;
import game.*;
public class GameGrounds {

	public static void main(String[] args) {
		Board B = new Board(20, 20, 100);
		Bot bobby = new Bot();
		Referee Stripes = new Referee(B);
		Stripes.playBot(bobby);

	}

}
