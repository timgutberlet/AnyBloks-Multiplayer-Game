package Game;

/**
 * @author tgeilen
 * @Date 12.03.22
 */
public class Player {

	static int playerCount = 0;

	private String name;
	private final String color;
	private int score;
	private Session session;

	public Player(String name){
		this.name = name;
		switch (playerCount % 4){
			case 0: this.color = "BLUE"; break;
			case 1: this.color = "RED"; break;
			case 2: this.color = "GREEN"; break;
			case 3: this.color = "YELLOW"; break;
			default: this.color = "WHITE"; break;
		}
		playerCount++;

	}

	public void joinSession(Session session){
		this.session = session;
	}

	public void putPieceOnBoard(Polygon p, int x,int y){
		if(this.session.getGame().getBoard().putPiece(this, p, x, y)){
			this.score += p.getWeight();
		};
	}

	public void addChatMessage(String msg){
		this.session.getChat().addMessage(this, msg);

	}

	public void createPieces(){
		//TODO add logic
	}

	public static int getPlayerCount() {
		return playerCount;
	}

	public static void setPlayerCount(int playerCount) {
		Player.playerCount = playerCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
