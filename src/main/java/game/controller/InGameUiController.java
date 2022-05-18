package game.controller;

import engine.component.Field;
import engine.component.TrigonField;
import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.ColorHandler;
import engine.handler.InputHandler;
import engine.handler.ThreadHandler;
import engine.handler.UserInputThread;
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
import game.view.stack.StackPane;
import game.view.stack.StackSquarePane;
import game.view.stack.StackTrigonPane;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public abstract class InGameUiController extends AbstractUiController {

  private final GameSession gameSession;
  private final Game game;
  private final Chat chat;
  private final AbstractGameController gameController;
  private final InputHandler inputHandler;
  private BorderPane pane;
  private final List<StackPane> stackPanes;
  private final List<Label> playerPoints;
  private Stage stage;
  private Button testButton;
  private StackPane stackLocal;
  private VBox stacks;
  private VBox right;
  private DragablePolyPane dragablePolyPane;
  private BoardPane boardPane;
  private Button quitButton;
  private HBox buttonBox;
  private boolean aiCalcRunning;
  private Player localPlayer;
  private ArrayList<int[]> possibleFields;
  private Boolean submitRequested;


  private Label hintLabel1;
  private Label hintLabel2;
  private Label hintLabel3;
  private VBox labelBox;

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
    playerPoints = new ArrayList<>();
    stackPanes = new ArrayList<>();
    possibleFields = new ArrayList<>();
    submitRequested = false;
    threadHelp.start();
    setUpUi();
    createBoard();
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
    pane.setCenter(boardPane);
  }

  private void setUpUi() {
    pane = new BorderPane();
    pane.setTranslateY(100);
    super.root.getChildren().add(pane);

    scores = new ArrayList<>();
    names = new ArrayList<>();
    int playerSize = game.getPlayers().size();
    scorePane = new GridPane();
    scorePane.setPrefWidth(stage.getWidth());
    scorePane.setPrefHeight(100);
    scorePane.setMaxHeight(100);
    super.root.getChildren().add(scorePane);
    int i = 0;
    for (Player p : this.gameSession.getPlayerList()) {
      VBox vbox = new VBox();
      Label score = new Label("0");
      Label name = new Label(p.getUsername());
      name.setTextFill(ColorHandler.getJavaColor(game.getGameState().getColorFromPlayer(p)));
      scores.add(score);
      names.add(name);
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
    vbox.getChildren().add(turn);
    vbox.setAlignment(Pos.CENTER);
    scorePane.addColumn(playerSize / 2, vbox);

    labelBox = new VBox();
    labelBox.setSpacing(20);
    hintLabel1 = new Label("Set Poly: Push 'P'-Button, ENTER or SPACE");
    hintLabel2 = new Label("Mirror: Push 'M'-Button");
    hintLabel3 = new Label("Left/Right Rotate: Push 'L' or 'M' Button");
    labelBox.getChildren().addAll(hintLabel1, hintLabel2, hintLabel3);
    pane.setTop(labelBox);
    right = new VBox();
    stacks = new VBox();
    switch (game.getGamemode().getName()) {
      case "JUNIOR":
      case "DUO":
      case "CLASSIC":
        for (Player p : this.gameSession.getPlayerList()) {
          StackPane stackPane = new StackSquarePane(p, inputHandler,
              game.getGameState().getRemainingPolysClone(p), stage.getWidth());
          stackPanes.add(stackPane);
          stacks.getChildren().add(stackPane);
        }
        break;
      case "TRIGON":
        for (Player p : this.gameSession.getPlayerList()) {
          StackPane stackPane = new StackTrigonPane(p, inputHandler,
              game.getGameState().getRemainingPolysClone(p), stage.getWidth());
          stackPanes.add(stackPane);
          stacks.getChildren().add(stackPane);
        }
        break;
    }
    right.getChildren().add(stacks);
    pane.setRight(right);

   /* for (Player p : this.gameSession.getPlayerList()) {
      Label label = new Label("0");
      playerPoints.add(label);
    }
    Gui.getChildren().addAll(playerPoints);*/

    buttonBox = new HBox();
    quitButton = new Button("Quit");
    testButton = new Button("Test");
    quitButton.setOnMouseClicked(mouseEvent -> this.handleQuitButtonClicked());
    buttonBox.getChildren().add(quitButton);
    buttonBox.getChildren().add(testButton);
    pane.setBottom(buttonBox);
    inputHandler.makeDraggable(testButton);
  }

  private void handleQuitButtonClicked() {
    gameEnd();
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  private void refreshUi() {
    int playerSize = game.getPlayers().size();
    stage.widthProperty().addListener((obs, oldVal, newVal) -> {
      //makes board resizable
      boardPane.resize(stage.getWidth());
      //makes scores resizable
      ColumnConstraints coll = new ColumnConstraints();
      coll.setMinWidth(stage.getWidth() / (playerSize + 1));
      for (int i = 0; i <= playerSize; i++) {
        scorePane.getColumnConstraints().set(i, coll);
      }
      pane.getChildren().remove(dragablePolyPane);
      dragablePolyPane = null;
    });

    for (int i = 0; i < playerSize; i++) {
      scores.get(i).setText(game.getGameState().getScores()[i] + "");
    }

    if (game.getGameState().getPlayerCurrent().equals(localPlayer)) {
      turn.setText("Your Turn");
    } else {
      turn.setText(game.getGameState().getPlayerCurrent().getUsername());
    }

    stackPanes.clear();
    stacks.getChildren().clear();
    switch (game.getGamemode().getName()) {
      case "JUNIOR":
      case "DUO":
      case "CLASSIC":
        for (Player p : game.getPlayers()) {
          StackPane stackPane = new StackSquarePane(p, inputHandler,
              game.getGameState().getRemainingPolysClone(p), stage.getWidth());
          stackPanes.add(stackPane);
          stacks.getChildren().add(stackPane);
        }
        break;
      case "TRIGON":
        for (Player p : game.getPlayers()) {
          StackTrigonPane stackPane = new StackTrigonPane(p, inputHandler,
              game.getGameState().getRemainingPolysClone(p), stage.getWidth());
          stackPanes.add(stackPane);
          stacks.getChildren().add(stackPane);
        }
        break;
    }

  }

    /*Gui.getChildren().removeAll(playerPoints);
    for(Player p : game.getPlayers()){
      Label label = new Label(game.getGameState().getBoard().getScoreOfColor(game.getGameState().getColorFromPlayer(p))+"");
      playerPoints.add(label);
    }
    Gui.getChildren().addAll(playerPoints);*/

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
    boolean paintAllFields = true;
    //Die Folgenden zwei Befehle sollten für einen Reibungslosen Spielablauf optimiert werden
    //noinspection LanguageDetectionInspection
    //Test
    boardPane.repaint(game.getGameState().getBoard());

    localPlayer = gameSession.getLocalPlayer();



    aiCalcRunning = localPlayer.getAiCalcRunning();
    //Check if AI is calculating - only refresh Board then
    if (aiCalcRunning) {
    } else {
      if (!game.getGameState().playsTurn()) {
        refreshUi();
      }
      //Check if Player has Turn
      if (game.getGameState().getPlayerCurrent().equals(localPlayer)) {
        boolean action = false;

        for (PolyPane polyPane : stackPanes.get(0).getPolyPanes()) {
          if (inputHandler.isPolyClicked(polyPane)) {
            if (dragablePolyPane == null) {
              switch (game.getGamemode().getName()) {
                case "JUNIOR":
                case "DUO":
                case "CLASSIC":
                  dragablePolyPane = new DragableSuarePane(polyPane, boardPane.getSize(),
                      inputHandler, this);
                  break;
                case "TRIGON":
                  dragablePolyPane = new DragableTrigonPane(polyPane, boardPane.getSize(),
                      inputHandler, this);
                  break;
              }
              pane.setLeft(dragablePolyPane);
            } else {
              dragablePolyPane.setPoly(polyPane);
            }
            localPlayer.setSelectedPoly(polyPane.getPoly());
            possibleFields = game.getGameState().getBoard()
                .getPossibleFieldsForPoly(dragablePolyPane.getPoly(),
                    game.getGameState().isFirstRound());
          }
        }

        if (inputHandler.isKeyPressed(KeyCode.ESCAPE)) {
          pane.getChildren().remove(dragablePolyPane);
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
              if(game.getGamemode().getName().equals("TRIGON")){
                boardPane.resetCheckFieldColor(field.getX(), field.getY(), ((TrigonField)field).getIsRight());
              }else{
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
                  if (inputHandler.isKeyPressed(KeyCode.ENTER) || inputHandler.isKeyPressed(KeyCode.SPACE) || submitRequested) {
                    possibleFields = null;
                    boardPane.resetAllCheckFields();
                    Turn turn = new Turn(dragablePolyPane.getPoly(), pos);
                    System.out.println(turn.getPoly());
                    pane.getChildren().remove(dragablePolyPane);
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
                  if(game.getGamemode().getName().equals("TRIGON")){
                    boardPane.setCheckFieldColor(Color.BLACK, coords[0], coords[1], coords[2]);

                  }else{
                    boardPane.setCheckFieldColor(Color.BLACK, coords[0], coords[1]);
                  }
                }
              }
            }
          }
        }catch (Exception e){
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
    }
    submitRequested = false;
  }

  public void paintPossibleFields(DragablePolyPane dragablePolyPane){
    possibleFields = game.getGameState().getBoard()
        .getPossibleFieldsForPoly(dragablePolyPane.getPoly(),
            game.getGameState().isFirstRound());
  }
  /**
   * Handles Button Submit Request of DraggablePolyPane
   * @author tgutberl
   */
  public void setSubmitRequested(){
    this.submitRequested = true;
  }

  protected void gameEnd() {
    threadHelp.interrupt();
    try {
      gameController.getApplication().stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Exit Method given by Abstract Class
   *
   * @author tgutberl
   */
  @Override
  public void onExit() {
    try {
      gameController.getApplication().stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
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










