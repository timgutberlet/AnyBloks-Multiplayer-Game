package game.controller;

import engine.component.Field;
import engine.component.TrigonField;
import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.ColorHandler;
import engine.handler.InputHandler;
import engine.handler.ThreadHandler;
import game.model.Debug;
import game.model.Game;
import game.model.GameSession;
import game.model.Turn;
import game.model.chat.Chat;
import game.model.player.Player;
import game.model.polygon.PolySquare;
import game.model.polygon.PolyTrigon;
import game.view.DragablePolyPane;
import game.view.DragableSuarePane;
import game.view.DragableTrigonPane;
import game.view.board.BoardPane;
import game.view.board.SquareBoardPane;
import game.view.board.TrigonBoardPane;
import game.view.poly.PolyPane;
import game.view.poly.SquarePolyPane;
import game.view.poly.TrigonPolyPane;
import game.view.stack.StackPane;
import game.view.stack.StackSquarePane;
import game.view.stack.StackTrigonPane;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * UiController controlling the Ingame Inputs and Outputs, as well as the VIew
 *
 * @author tgutberl
 */

public abstract class InGameUiController extends AbstractUiController {

  private final GameSession gameSession;
  private final Game game;
  private final Chat chat;
  private final AbstractGameController gameController;
  private final InputHandler inputHandler;
  private Stage stage;
  private Button testButton;
  private boolean aiCalcRunning;
  private Player localPlayer;
  private ArrayList<int[]> possibleFields;
  private Boolean submitRequested;

  private VBox container;
  private HBox content;
  private BoardPane boardPane;
  private final List<StackPane> stackPanes;
  private DragablePolyPane dragablePolyPane;
  private VBox stacks;

  private Button infoButton;
  private Button quitButton;
  private HBox buttonBox;

  private Label hintLabel1;
  private Label hintLabel2;
  private Label hintLabel3;
  private VBox labelBox;

  private Pane topPane;
  private GridPane scorePane;
  private List<Label> scores;
  private List<Label> names;
  private Label turn;

  private ThreadHandler threadHelp;


  public InGameUiController(AbstractGameController gameController, Game game,
      GameSession gameSession, ThreadHandler threadHelp) {
    super(gameController);
    this.threadHelp = threadHelp;
    this.inputHandler = gameController.getInputHandler();
    this.gameSession = gameSession;
    this.game = gameSession.getGame();
    this.chat = gameSession.getChat();
    this.gameController = gameController;
    this.stage = gameController.getStage();
    stackPanes = new ArrayList<>();
    possibleFields = new ArrayList<>();
    submitRequested = false;
    threadHelp.start();
    setUpUi();
  }

  public void createBoard() {
    switch (game.getGamemode().getName()) {
      case "JUNIOR":
      case "DUO":
      case "CLASSIC":
        boardPane = new SquareBoardPane(game.getGameState().getBoard(), inputHandler,
            stage.getWidth());
        break;
      case "TRIGON":
        boardPane = new TrigonBoardPane(game.getGameState().getBoard(), inputHandler,
            stage.getWidth());
        break;
    }

    content.getChildren().add(boardPane);
  }

  private void setUpUi() {
    double width = stage.getWidth();
    double height = stage.getHeight();

    container = new VBox();
    container.setTranslateY(110);
    container.setPrefWidth(width);
    container.setSpacing(10);
    container.setAlignment(Pos.CENTER);
    super.root.getChildren().add(container);

    content = new HBox();
    content.setPrefWidth(width);
    content.setSpacing(10);
    content.setAlignment(Pos.CENTER);
    container.getChildren().add(content);

    createBoard();

    topPane = new Pane();
    topPane.setStyle("-fx-background-color: #eeeeee");
    topPane.setEffect(new DropShadow());
    topPane.setPrefWidth(width);
    topPane.setPrefHeight(100);
    topPane.setMaxHeight(100);
    super.root.getChildren().add(topPane);

    scores = new ArrayList<>();
    names = new ArrayList<>();
    scorePane = new GridPane();
    scorePane.setPrefWidth(width);
    scorePane.setPrefHeight(100);
    scorePane.setMaxHeight(100);
    super.root.getChildren().add(scorePane);

    int i = 0;
    int playerSize = game.getPlayers().size();
    for (Player p : this.gameSession.getPlayerList()) {
      VBox vbox = new VBox();
      Label name = new Label(p.getUsername());
      name.setTextFill(ColorHandler.getJavaColor(game.getGameState().getColorFromPlayer(p)));
      name.setFont(Font.font("System", 20));
      Label score = new Label("0");
      score.setFont(Font.font("System", 30));
      names.add(name);
      scores.add(score);
      vbox.getChildren().add(name);
      vbox.getChildren().add(score);
      vbox.setAlignment(Pos.CENTER);
      if (i < playerSize / 2) {
        scorePane.add(vbox, i, 0);
      } else {
        scorePane.add(vbox, i + 1, 0);
      }
      i++;
    }

    ColumnConstraints coll = new ColumnConstraints();
    coll.setMinWidth(stage.getWidth() / (playerSize + 1));

    for (int j = 0; j <= playerSize; j++) {
      scorePane.getColumnConstraints().add(coll);
    }

    RowConstraints row = new RowConstraints();
    row.setPrefHeight(100);
    scorePane.getRowConstraints().add(row);

    VBox vbox = new VBox();
    turn = new Label("");
    turn.setFont(Font.font("System", 30));
    turn.setStyle("-fx-background-color: ##ffffff; -fx-background-radius: 10;");
    turn.setPadding(new Insets(0, 10, 0, 10));
    vbox.getChildren().add(turn);
    vbox.setAlignment(Pos.CENTER);
    scorePane.addColumn(playerSize / 2, vbox);

    stacks = new VBox();
    stacks.setSpacing(10);

    switch (game.getGamemode().getName()) {
      case "JUNIOR":
      case "DUO":
      case "CLASSIC":
        for (Player p : this.gameSession.getPlayerList()) {
          StackPane stackPane = new StackSquarePane(p, inputHandler,
              game.getGameState().getRemainingPolys(p), stage.getWidth());
          stackPanes.add(stackPane);
          stacks.getChildren().add(stackPane);
        }
        break;
      case "TRIGON":
        for (Player p : this.gameSession.getPlayerList()) {
          StackPane stackPane = new StackTrigonPane(p, inputHandler,
              game.getGameState().getRemainingPolys(p), stage.getWidth());
          stackPanes.add(stackPane);
          stacks.getChildren().add(stackPane);
        }
        break;
    }
    content.getChildren().add(stacks);

    buttonBox = new HBox();
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setSpacing(20);

    infoButton = new Button("?");
    quitButton = new Button("Quit");
    testButton = new Button("ScoreBoard");

    infoButton.setOnMouseClicked(mouseEvent -> {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Information");
      alert.setContentText(
          "Set Poly: Push 'P'-Button, ENTER or SPACE \n Mirror: Push 'M'-Button \n Left/Right Rotate: Push 'L' or 'M' Button");
      alert.setHeaderText(null);
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(stage);
      alert.show();
    });

    quitButton.setOnMouseClicked(mouseEvent -> this.handleQuitButtonClicked());
    testButton.setOnMouseClicked(mouseEvent -> {
      this.handleTestButtonClicked();
    });

    buttonBox.getChildren().add(infoButton);
    buttonBox.getChildren().add(quitButton);
    buttonBox.getChildren().add(testButton);
    container.getChildren().add(buttonBox);
  }

  private void handleTestButtonClicked() {
    ScoreBoardUiController.sortScoreBoard(gameSession);
    gameController.setActiveUiController(new ScoreBoardUiController(gameController, gameSession));
  }

  private void handleQuitButtonClicked() {
    gameEnd();
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  private void refreshUi() {
    int playerSize = game.getPlayers().size();
    stage.widthProperty().addListener((obs, oldVal, newVal) -> {

      topPane.setPrefWidth(stage.getWidth());

      container.setPrefWidth(stage.getWidth());
      content.setPrefWidth(stage.getWidth());
      buttonBox.setPrefWidth(stage.getWidth());

      ColumnConstraints coll = new ColumnConstraints();
      coll.setMinWidth(stage.getWidth() / (playerSize + 1));
      for (int i = 0; i <= playerSize; i++) {
        scorePane.getColumnConstraints().set(i, coll);
      }

      root.getChildren().remove(dragablePolyPane);
      dragablePolyPane = null;
    });

    for (int i = 0; i < playerSize; i++) {
      scores.get(i).setText(game.getGameState().getScores()[i] + "");
    }

    if (game.getGameState().getPlayerCurrent().equals(localPlayer)) {
      turn.setText("Your Turn");
    } else {
      turn.setText(game.getGameState().getPlayerCurrent().getUsername() + " 's Turn");
    }

    stackPanes.clear();
    stacks.getChildren().clear();
    switch (game.getGamemode().getName()) {
      case "JUNIOR":
      case "DUO":
      case "CLASSIC":
        for (Player p : game.getPlayers()) {
          StackPane stackPane = new StackSquarePane(p, inputHandler,
              game.getGameState().getRemainingPolys(p), stage.getWidth());
          stackPanes.add(stackPane);
          stacks.getChildren().add(stackPane);
        }
        break;
      case "TRIGON":
        for (Player p : game.getPlayers()) {
          StackTrigonPane stackPane = new StackTrigonPane(p, inputHandler,
              game.getGameState().getRemainingPolys(p), stage.getWidth());
          stackPanes.add(stackPane);
          stacks.getChildren().add(stackPane);
        }
        break;
    }

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
    root.requestFocus();
    boardPane.repaint(game.getGameState().getBoard());

    localPlayer = gameSession.getLocalPlayer();

    aiCalcRunning = game.getCurrentPlayer().getAiCalcRunning();
    //Check if AI is calculating - only refresh Board then
    if (aiCalcRunning) {
    } else {
      if (this.game == null) {
        Debug.printMessage(this, "Game is null");
      }

      if (!game.getGameState().playsTurn()) {
        Debug.printMessage(this, "" + game.getGameState().playsTurn());
        refreshUi();
      }
      //Check if Player has Turn
      Debug.printMessage(this, this.localPlayer.getUsername() + " " + this.localPlayer);
      if (this.gameSession.isLocalPlayerTurn()) {
        //hintLabel1.setText("Erkannt");
        Debug.printMessage(this, "GUI ready for input");
        boolean action = false;

        if (!this.gameSession.isUpdatingGameState()) {
          for (PolyPane polyPane : stackPanes.get(gameSession.getPlayerList().indexOf(localPlayer)).getPolyPanes()) {
            if (inputHandler.isPolyClicked(polyPane)) {
              if (dragablePolyPane != null) {
                root.getChildren().remove(dragablePolyPane);
                dragablePolyPane = null;
              }

              switch (game.getGamemode().getName()) {
                case "JUNIOR":
                case "DUO":
                case "CLASSIC":
                  dragablePolyPane = new DragableSuarePane(
                      new SquarePolyPane(polyPane.getPoly().clone(), inputHandler,
                          stage.getWidth()), boardPane.getSize(),
                      inputHandler, this);
                  break;
                case "TRIGON":
                  dragablePolyPane = new DragableTrigonPane(
                      new TrigonPolyPane(polyPane.getPoly().clone(), inputHandler,
                          stage.getWidth()), boardPane.getSize(),
                      inputHandler, this);
                  break;
              }

              stacks.layout();

              double centerX = 0;
              double centerY = 0;

              for (Field field : polyPane.getFields()) {
                Bounds bounds = field.localToScene(field.getBoundsInLocal());
                centerX += bounds.getCenterX();
                centerY += bounds.getCenterY();
              }
              centerX /= polyPane.getPoly().getSize();
              centerY /= polyPane.getPoly().getSize();

              double ofSetX = centerX - dragablePolyPane.getCircleX();
              double ofSetY = centerY - dragablePolyPane.getCircleY();

              dragablePolyPane.relocate(ofSetX, ofSetY);
              root.getChildren().add(dragablePolyPane);

              localPlayer.setSelectedPoly(polyPane.getPoly());
              possibleFields = game.getGameState().getBoard()
                  .getPossibleFieldsForPoly(dragablePolyPane.getPoly(),
                      game.getGameState().isFirstRound());
            }
          }

          if (inputHandler.isKeyPressed(KeyCode.ESCAPE)) {
            root.getChildren().remove(dragablePolyPane);
            boardPane.resetAllCheckFields();
            dragablePolyPane = null;
            possibleFields = null;
          }
          try {
            if (dragablePolyPane != null) {
              boolean currentIntersection = false;
              Bounds polyBounds = dragablePolyPane.getCheckPolyField()
                  .localToScene(dragablePolyPane.getCheckPolyField().getBoundsInLocal());
              for (Field field : boardPane.getCheckFields()) {
                if (game.getGamemode().getName().equals("TRIGON")) {
                  boardPane.resetCheckFieldColor(field.getX(), field.getY(),
                      ((TrigonField) field).getIsRight());
                } else {
                  boardPane.resetCheckFieldColor(field.getX(), field.getY());
                }
                Bounds boardBounds = field.localToScene(field.getBoundsInParent());
                if (polyBounds.intersects(boardBounds)) {
                  //Add is Poly Possible
                  int addX;
                  int addY;
                  int addIsRight = 0;
                  int pos[];
                  if (game.getGamemode().getName().equals("TRIGON") && dragablePolyPane != null) {

                    addX = ((PolyTrigon) dragablePolyPane.getPoly()).getShape().get(0).getPos()[0];
                    addY = ((PolyTrigon) dragablePolyPane.getPoly()).getShape().get(0).getPos()[1];
                    addIsRight = ((PolyTrigon) dragablePolyPane.getPoly()).getShape().get(0)
                        .getPos()[2];
                    pos = new int[3];
                    pos[0] = field.getX() + addX;
                    pos[1] = field.getY() + addY;
                    pos[2] = addIsRight;
                  } else {
                    addX = ((PolySquare) dragablePolyPane.getPoly()).getShape().get(0).getPos()[0];
                    addY = ((PolySquare) dragablePolyPane.getPoly()).getShape().get(0).getPos()[1];
                    pos = new int[2];
                    pos[0] = field.getX() + addX;
                    pos[1] = field.getY() + addY;
                  }
                  //change
                  if (game.getGameState().getBoard().isPolyPossible(pos, dragablePolyPane.getPoly(),
                      game.getGameState().isFirstRound())) {
                    dragablePolyPane.inncerCircleSetColor();
                    currentIntersection = true;
                    dragablePolyPane.rerender();
                    if (inputHandler.isKeyPressed(KeyCode.ENTER) || inputHandler.isKeyPressed(
                        KeyCode.SPACE) || submitRequested) {
                      possibleFields = null;
                      boardPane.resetAllCheckFields();
                      Turn turn = new Turn(dragablePolyPane.getPoly(), pos);
                      System.out.println(turn.getPoly());
                      root.getChildren().remove(dragablePolyPane);
                      dragablePolyPane = null;
                      localPlayer.setSelectedTurn(turn);
                    }
                  }
                }
                if (!currentIntersection) {
                  dragablePolyPane.inncerCircleResetColor();
                  dragablePolyPane.rerender();
                }
                if (possibleFields != null) {
                  for (int[] coords : possibleFields) {
                    if (game.getGamemode().getName().equals("TRIGON")) {
                      boardPane.setCheckFieldColor(Color.BLACK, coords[0], coords[1], coords[2]);

                    } else {
                      boardPane.setCheckFieldColor(Color.BLACK, coords[0], coords[1]);
                    }
                  }
                }
              }
            }
          } catch (Exception e) {
          }

          //If localPlayer has selected a Poly, check if he also already click on the Board
        /*
        /*if (localPlayer.getSelectedPoly() != null) {
          localPlayer.setSelectedPoly(localPlayer.getSelectedPoly());
          System.out.println("Localplayer Selected Poly");
          //create helpArraylist containing the selectedPoly to check the possible Moves
          ArrayList<Poly> helpList = new ArrayList<>();
          helpList.add(localPlayer.getSelectedPoly());

          ArrayList<Turn> possibleTurns = new ArrayList<Turn>();
          possibleTurns = game.getGameState().getBoard().getPossibleMoves(helpList, false);
          localPlayer.setSelectedTurn(possibleTurns.get(0));
          paintPossibleTurns(possibleTurns);
          //TODO implement check of any FieldTile if it is clicked
        }*/
        }
      } else {
        //Debug.printMessage(this, "The two players are NOT the same");
        //hintLabel1.setText("Nicht erkannt");
      }
    }
    submitRequested = false;

    //check if game is over
    if(this.gameSession.isGameOver()){
      gameController.setActiveUiController(
          new ScoreBoardUiController(gameController, gameSession));
      System.out.println("GAME IS OVER");
    }

  }

  public void paintPossibleFields(DragablePolyPane dragablePolyPane) {
    possibleFields = game.getGameState().getBoard()
        .getPossibleFieldsForPoly(dragablePolyPane.getPoly(),
            game.getGameState().isFirstRound());
  }

  /**
   * Handles Button Submit Request of DraggablePolyPane
   *
   * @author tgutberl
   */
  public void setSubmitRequested() {
    this.submitRequested = true;
  }

  protected void gameEnd() {
    threadHelp.interrupt();
    this.gameSession.endGame(null);
  }

  /**
   * Exit Method given by Abstract Class
   *
   * @author tgutberl
   */
  @Override
  public void onExit() {
    threadHelp.interrupt();
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
