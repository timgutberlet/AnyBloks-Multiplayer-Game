package game.model;

/**
 * @author timgutberlet
 * @Date 19.03.22
 */
public class Debug {

  static boolean debug = true;

  public static void printMessage(String message) {
    if (debug) {
      System.out.println(message);
    }
  }

  public static void printMessage(Object o,String message) {
    if (debug) {
      System.out.println("["+o.getClass().getName()+"]"+ message);
    }
  }
}
