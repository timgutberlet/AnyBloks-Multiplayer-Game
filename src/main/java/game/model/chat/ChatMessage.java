package game.model.chat;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author tgeilen
 * @Date 21.03.22
 */
public class ChatMessage {

  private String username;
  private Timestamp time;
  private String message;


  public ChatMessage(String username, String message) {

    this.username = username;

    Date date = new Date();
    this.time = new Timestamp(date.getTime());

    this.message = message;

  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}

