package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.model.Game;
import game.model.player.Player;
import game.model.GameSession;
import game.model.chat.Chat;
import game.view.board.BoardPane;
import game.view.board.SquareBoardPane;
import game.view.board.TrigonBoardPane;
import game.view.stack.StackPane;
import game.view.stack.StackSquarePane;
import game.view.stack.StackTrigonPane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InGameUiController extends AbstractUiController {

	private GameSession gameSession;

	private Game game;

	private Chat chat;

	private AbstractGameController gameController;

	private BoardPane boardPane;

	private List<StackPane> stackPanes;

	private VBox stacks;

	private List<Label> playerPoints;

	private HBox Gui;

	private Button quitButton;


	public InGameUiController(AbstractGameController gameController, Game game,
			GameSession gameSession) {
		super(gameController);
		this.gameSession = gameSession;
		this.game = gameSession.getGame();
		this.chat = gameSession.getChat();
		this.gameController = gameController;
		this.Gui = new HBox();
		playerPoints = new ArrayList<>();
		stackPanes = new ArrayList<>();

		super.root.getChildren().add(Gui);
		createBoard();
		setUpUi();


	}

	public void createBoard() {
		switch (game.getGamemode().getName()) {
			case "JUNIOR":
				;
			case "DUO":
				;
			case "CLASSIC":
				boardPane = new SquareBoardPane(game.getGameState().getBoard(),
						gameController.getInputHandler());
				break;
			case "TRIGON":
				boardPane = new TrigonBoardPane(game.getGameState().getBoard(),
						gameController.getInputHandler());
				break;
		}
		Gui.getChildren().add((Node) boardPane);
	}


	private void setUpUi() {
		stacks = new VBox();

		switch (game.getGamemode().getName()) {
			case "JUNIOR":
				;
			case "DUO":
				;
			case "CLASSIC":
				for (Player p : this.gameSession.getPlayerList()) {
					StackPane squareStack = new StackSquarePane(p, gameController.getInputHandler(),
							game.getGameState().getRemainingPolys(p));
					stackPanes.add(squareStack);
					stacks.getChildren().add(squareStack);
				}
				;
				break;
			case "TRIGON":
				for (Player p : this.gameSession.getPlayerList()) {
					StackPane trigonStack = new StackTrigonPane(p, gameController.getInputHandler(),
							game.getGameState().getRemainingPolys(p));
					stackPanes.add(trigonStack);
					stacks.getChildren().add(trigonStack);
				}
				break;
		}

		Gui.getChildren().add(stacks);

		for (Player p : this.gameSession.getPlayerList()) {
			Label label = new Label("0");
			playerPoints.add(label);
		}
		//Gui.getChildren().addAll(playerPoints);

		quitButton = new Button("Quit");
		quitButton.setOnMouseClicked(mouseEvent -> this.handleQuitButtonClicked());
		Gui.getChildren().add(quitButton);
	}

	private void handleQuitButtonClicked() {
		gameController.setActiveUiController(new MainMenuUiController(gameController));
	}

	private void refreshUi() {
		stacks.getChildren().clear();
		for (Player p : game.getPlayers()) {
			switch (game.getGamemode().getName()) {
				case "JUNIOR":
					;
				case "DUO":
					;
				case "CLASSIC":
					StackPane classicStack = new StackSquarePane(p, gameController.getInputHandler(),
							game.getGameState().getRemainingPolys(p));
					stacks.getChildren().add(classicStack);
					;
					break;
				case "TRIGON":
					StackPane trigonStack = new StackTrigonPane(p, gameController.getInputHandler(),
							game.getGameState().getRemainingPolys(p));
					stacks.getChildren().add(trigonStack);
					break;
			}

		}

    /*Gui.getChildren().removeAll(playerPoints);
    for(Player p : game.getPlayers()){
      Label label = new Label(game.getGameState().getBoard().getScoreOfColor(game.getGameState().getColorFromPlayer(p))+"");
      playerPoints.add(label);
    }
    Gui.getChildren().addAll(playerPoints);*/
	}

	/**
	 * function that updates the screen and calls the next move to be made
	 *
	 * @param gameController
	 * @param deltaTime
	 * @author //TODO hier die klasse hat jemand anders geschrieben. ich habe nur paar changes
	 * gemacht. echter autor am besten noch dazu schreiben
	 * @author tgeilen
	 */
	@Override
	public void update(AbstractGameController gameController, double deltaTime) {
		this.game.makeMove();
		boardPane.repaint(game.getGameState().getBoard());
		refreshUi();
	}

	/**
	 * Exit Method given by Abstract Class
	 *
	 * @author tgutberl
	 */
	@Override
	public void onExit() {

	}

	/**
	 * Init Method given by Abstract Class
	 *
	 * @author tgutberl
	 */
	@Override
	public void init(Group root) {

	}

	/**
	 * Method used to update the current frame
	 *
	 * @param gameController GameController of game
	 * @author tgutberl
	 */
	@Override
	public void update(AbstractGameController gameController) {

	}
}

