package GameLogic;

/**
 * @author tgeilen
 * @Date 12.03.22
 */
public class Board {

	public Square[][] board;
	private final int HEIGHT, WIDTH;


	public Board(int height, int width){
		this.HEIGHT = height;
		this.WIDTH = width;

		board = new Square[HEIGHT][WIDTH];
		for (int x=0; x<HEIGHT; x++){
			for(int y=0; y<WIDTH; y++){
				board[x][y] = new Square(x,y);
			}
		}
	}

	/**
	 * /TODO ADD THE BUSINESS LOGIC
	 * @param player
	 * @param piece
	 * @param x
	 * @param y
	 * @return was Piece placed or not
	 */
	public Boolean putPiece(Player player, Polygon piece, int x, int y){
		if (true){
			return true;
		} else {
			return false;
		}
	}


	@Override
	public String toString() {
		String result = "";
		for(Square[] row:board){
			for(Square cell: row){
				switch (cell.getColor()){

				}
			}
		}

		return result;
	}
}


