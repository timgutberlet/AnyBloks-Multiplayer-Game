package Game;

/**
 * @author tgeilen
 * @Date 12.03.22
 */
public class Square {

	private int x,y;
	private String color;

	public Square(int x, int y) {
		this.x = x;
		this.y = y;

	}

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return this.color;
	}

}
