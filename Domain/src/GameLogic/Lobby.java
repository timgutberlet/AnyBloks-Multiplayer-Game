package GameLogic;

/**
 * @author tgeilen
 * @Date 13.03.22
 */
public class Lobby {

	private String username;

	public Lobby(){

	}

	public Session hostSession(){
		Player player = new Player(this.username);
		Session session = new Session(player);
		return session;
	}

	public void joinSession(Session session){
		session.addPlayer(this.username);
	}

	public void setUsername(String username){
		this.username = username;
	}

}
