package engine;

import javafx.scene.Group;

/**
 * Init class showing.
 *
 * @author lbaudenb
 */
public interface Initializable {

  /**
   * Init Method calld when initializing the Controller.
   *
   * @param root Root object of the Application
   */
  void init(Group root);
}
