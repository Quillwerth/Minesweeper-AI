package game;

public class Coord {
	/* Coord:
	 * Contains a coordinate pair x,y and a text tag for additional identification.
	 * 
	 */
	int x; int y; String tag;
	
	public Coord(int X, int Y, String T){
		x = X; y = Y; tag = T.toUpperCase();
	}
	
	public int[] getCoord(){
		int[] r = {x,y};
		return r;
	}
	
	public int getX(){
		return getCoord()[0];
	}
	
	public int getY(){
		return getCoord()[1];
	}
	
	public String getTag(){
		return tag;
	}
	
	public boolean isSameLocation(Coord c){
		//Compares the coordinates for equality in terms of locale.
		if(this.getX()==c.getX() && this.getY()==c.getY()){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isSame(Coord c){
		//Compares both tag and location.
		if(isSameLocation(c)&&this.getTag().equals(c.getTag())){
			return true;
		}else{
			return false;
		}
	}
	
	
}
