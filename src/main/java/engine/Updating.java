package engine;

import engine.controller.AbstractGameController;

/**
 * Method for handling Updates
 * @author tgutberl
 */
public interface Updating {

  /**
   * Method to update Objects
   * @param gameController GameController of game
   * @author tgutberl
   */
  void update(AbstractGameController gameController);

}
