package game.controller;

import engine.component.Field;
import engine.component.TrigonField;
import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.ColorHandler;
import engine.handler.InputHandler;
import game.config.Config;
import game.model.Debug;
import game.model.Game;
import game.model.GameSession;
import game.model.Turn;
import game.model.chat.ChatMessage;
import game.model.player.Player;
import game.model.player.PlayerType;
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
import java.util.concurrent.TimeUnit;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
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
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.game.HostQuitPacket;


/**
 * UiController controlling the Ingame Inputs and Outputs, as well as the VIew.
 *
 * @author tgutberl
 * @author lbaudenb
 */

public abstract class InGameUiController extends AbstractUiController {

  private final GameSession gameSession;
  private final Game game;
  private final AbstractGameController gameController;
  private final InputHandler inputHandler;
  private final List<StackPane> stackPanes;
  /**
   * Main Anchorpane used for Style.
   */
  private final AnchorPane anchorPane;
  /**
   * String to save Input Message before repaint.
   */
  private final String bufferChat = "";
  /**
   * used for comparing already in chat messages to chat Object.
   */
  private final ArrayList<String> alreadyInChat;
  private final Stage stage;
  /**
   * Label to drag the chat.
   */
  Label dragLabel;
  /**
   * Color used for collision detection.
   */
  Color color;
  /**
   * Button to Skip Turns.
   */
  Button skipTurnButton;
  /**
   * Button to open Chat Window.
   */
  private Button chatButton;
  /**
   * Pane where the Chat is put onto.
   */
  private VBox chatPane;
  /**
   * Chat area.
   */
  private TextArea chat;
  /**
   * Text field for chat input.
   */
  private TextField chatInput;
  /**
   * Checks if Chat Selected.
   */
  private Boolean chatSelected = false;
  /**
   * Block int that determines if the player did a move or not.
   */
  private int moveCheck = 0;
  /**
   * Checks if Ai is calulating.
   */
  private boolean aiCalcRunning;
  /**
   * This Label informs the User, why a certain move was possible / impossible.
   */
  private Label errorLabel;
  /**
   * Text that should be written into the Label each frame.
   */
  private String errorLabelText = "";
  /**
   * Block boolean that shows if moves are possible for player or not.
   */
  private boolean block = false;

  private VBox boardBox;

  private Player localPlayer;
  private ArrayList<int[]> possibleFields;
  private Boolean submitRequested;
  private VBox container;
  private HBox content;
  private BoardPane boardPane;
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

  /**
   * Constructor used for setting gameController, game and gameSession.
   *
   * @author tgutberl
   * @author lbaudenb
   */
  public InGameUiController(AbstractGameController gameController, Game game,
      GameSession gameSession) {
    //Initializing all needed variables
    super(gameController);
    this.inputHandler = gameController.getInputHandler();
    this.gameSession = gameSession;
    this.game = gameSession.getGame();
    this.gameController = gameController;
    this.stage = gameController.getStage();
    this.alreadyInChat = new ArrayList<>();
    this.anchorPane = new AnchorPane();
    stackPanes = new ArrayList<>();
    possibleFields = new ArrayList<>();
    submitRequested = false;
    setUpUi();
  }

  /**
   * Method that is called when chatInput Enter was done. Sends Chatmessage to Server.
   *
   * @author tgutberl
   */
  public void registerChatMessage() {
    if (chatInput.getText().length() > 0) {
      String text = chatInput.getText();
      chatInput.setText("");
      gameSession.addChatMessage(text);
      chatSelected = false;
    } else {
      chatSelected = false;
    }
  }

  /**
   * Method to create Board.
   *
   * @author lbaudenb
   */
  public void createBoard() {
    //Create Board by gamemode
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
      default:
        break;
    }
    //init error label
    errorLabel = new Label("This is the Error Label");
    errorLabel.setPrefHeight(20);
    errorLabel.setPrefWidth(350);
    errorLabel.setFont(Font.font("System", 15));
    boardPane.getChildren().add(errorLabel);
    boardBox = new VBox();
    boardBox.setPadding(new Insets(0, 0, 0, 10));
    boardBox.getChildren().add(boardPane);
    content.getChildren().add(boardBox);
  }

  /**
   * Method to setup UI.
   *
   * @author lbaudenb
   */
  private void setUpUi() {

    switch (Config.getStringValue("THEME")) {
      case "BRIGHT":
        ColorHandler.whiteMode = true;
        break;
      case "DARK":
        ColorHandler.darkMode = true;
        break;
      default:
        ColorHandler.darkMode = false;
        ColorHandler.whiteMode = false;
    }

    double width;
    width = stage.getWidth();
    double height;
    height = stage.getHeight();
    //setup main anchorpane and container box
    container = new VBox();
    container.setTranslateY(110);
    container.setPrefWidth(width);
    container.setSpacing(10);
    container.setAlignment(Pos.CENTER);
    anchorPane.getChildren().add(container);
    anchorPane.setPrefWidth(stage.getWidth() + 1000);
    anchorPane.setPrefHeight(stage.getHeight() + 1000);

    content = new HBox();
    content.setPrefWidth(width);
    content.setSpacing(10);
    content.setAlignment(Pos.CENTER_LEFT);
    container.getChildren().add(content);

    createBoard();
    //Setup TopPane with scores
    topPane = new Pane();
    topPane.setStyle("-fx-background-color: #eeeeee");
    topPane.setEffect(new DropShadow());
    topPane.setPrefWidth(width);
    topPane.setPrefHeight(100);
    topPane.setMaxHeight(100);
    anchorPane.getChildren().add(topPane);

    scores = new ArrayList<>();
    names = new ArrayList<>();
    scorePane = new GridPane();
    scorePane.setPrefWidth(width);
    scorePane.setPrefHeight(100);
    scorePane.setMaxHeight(100);
    anchorPane.getChildren().add(scorePane);
    //Init the scores and the turn label
    int i = 0;
    int playerSize = game.getPlayers().size();
    for (Player p : this.gameSession.getPlayerList()) {
      VBox vbox;
      vbox = new VBox();
      Label name = new Label(p.getUsername());
      name.setTextFill(ColorHandler.getJavaColor(game.getGameState().getColorFromPlayer(p)));
      name.setFont(Font.font("System", 20));
      name.setPadding(new Insets(0, 10, 0, 10));
      Label score = new Label("0");
      score.setPadding(new Insets(0, 10, 0, 10));
      score.setFont(Font.font("System", 30));
      //Set colors of Scores by player color
      switch (game.getGameState().getColorFromPlayer(p).toString()) {
        case "RED":
          if (ColorHandler.whiteMode) {
            name.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #8b0000;");
            score.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #8b0000;");
          } else {
            name.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #ff0000;");
            score.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #ff0000;");
          }
          break;
        case "BLUE":
          if (ColorHandler.whiteMode) {
            name.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #00008b;");
            score.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #00008b;");
          } else {
            name.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #0000ff;");
            score.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #0000ff;");
          }
          break;
        case "GREEN":
          if (ColorHandler.whiteMode) {
            name.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #006400;");
            score.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #006400;");
          }
          if (ColorHandler.darkMode) {
            name.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #00ff00;");
            score.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #00ff00;");
          } else {
            name.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #00cc00;");
            score.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #00cc00;");
          }
          break;
        case "YELLOW":
          if (ColorHandler.whiteMode) {
            name.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #b2b200;");
            score.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #b2b200;");
          } else {
            name.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #fff44f;");
            score.setStyle(
                "-fx-background-color:#FFFFFF; -fx-background-radius: 5; -fx-text-fill: #fff44f;");
          }
          break;
        default:
          break;
      }

      names.add(name);
      scores.add(score);
      vbox.getChildren().add(name);
      vbox.getChildren().add(score);
      vbox.setAlignment(Pos.CENTER);
      vbox.setSpacing(5);
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
    //Init the turn label
    VBox vbox;
    vbox = new VBox();
    turn = new Label("");
    turn.setFont(Font.font("System", 30));
    turn.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10;");
    turn.setPadding(new Insets(0, 10, 0, 10));
    vbox.getChildren().add(turn);
    vbox.setAlignment(Pos.CENTER);
    scorePane.addColumn(playerSize / 2, vbox);

    stacks = new VBox();
    stacks.setSpacing(10);
    //Init the right GameMode so that the stacks are painted right
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
      default:
        break;
    }
    content.getChildren().add(stacks);
    //Init the chat
    chat = new TextArea();
    chat.setPrefHeight(height / 7);
    chat.setPrefWidth(300);
    chatInput = new TextField();
    chatInput.setText("Write a Message and press Enter...");
    chatInput.setOnMousePressed(event -> {
      this.chatInput.setText("");
      chatSelected = true;
    });
    //Make chat draggable
    dragLabel = new Label("Drag and Move the Chat here!");
    dragLabel.setPrefHeight(30);
    dragLabel.setPrefWidth(300);
    dragLabel.setAlignment(Pos.CENTER);
    chatInput.setPrefWidth(300);
    chatInput.setPrefHeight(30);
    chatPane = new VBox();
    chatPane.setSpacing(5);
    chatPane.setVisible(true);
    chatPane.setPrefHeight(height / 6);
    chatPane.setMinWidth(300);
    chatPane.setMaxWidth(300);
    chatPane.getChildren().add(dragLabel);
    chatPane.getChildren().add(chat);
    chatPane.getChildren().add(chatInput);
    chatPane.setAlignment(Pos.CENTER);
    inputHandler.makeDraggable(chatPane);
    double chatHeight = gameController.getStage().getScene().getHeight();
    double chatWidth = gameController.getStage().getScene().getWidth();
    chatPane.setTranslateX(chatWidth - 300 - 50);
    chatPane.setTranslateY(chatHeight - height / 6 - 15);
    anchorPane.getChildren().add(chatPane);

    buttonBox = new HBox();
    buttonBox.setAlignment(Pos.BOTTOM_CENTER);
    buttonBox.setMinHeight(50);
    buttonBox.setSpacing(40);
    //Init all the Buttons
    skipTurnButton = new Button("Skip Turn");
    infoButton = new Button("Help");
    quitButton = new Button("Quit");
    chatButton = new Button("Hide Chat");

    //Event to start Chat Window
    chatButton.setOnMouseClicked(event -> {
      this.chatPane.setVisible(!this.chatPane.isVisible());
    });

    //Set Event for Skip Turn Button
    skipTurnButton.setOnMouseClicked(event -> {
      this.localPlayer.nullTurn();
      skipTurnButton.setVisible(false);
    });

    skipTurnButton.setVisible(false);

    chatInput.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.ENTER)) {
        registerChatMessage();
        chatInput.setText("");
      }
    });

    infoButton.setOnMouseClicked(mouseEvent -> {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Information");
      alert.setContentText(
          "Set Poly: Push 'P'-Button, ENTER or SPACE \n Mirror: Push 'M'-Button \n "
              + "Left/Right Rotate: Push 'L' or 'M' Button");
      alert.setHeaderText(null);
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(stage);
      alert.show();
    });

    quitButton.setOnMouseClicked(mouseEvent -> this.handleQuitButtonClicked());
    //Add all Buttons to buttonBox
    buttonBox.getChildren().add(infoButton);
    buttonBox.getChildren().add(quitButton);
    buttonBox.getChildren().add(chatButton);
    buttonBox.getChildren().add(skipTurnButton);
    double errerHeight = gameController.getStage().getScene().getHeight();
    double errerWidth = gameController.getStage().getScene().getWidth();
    errorLabel.setTranslateX(10);
    errorLabel.setTranslateY(errerHeight - 35);
    anchorPane.getChildren().add(errorLabel);
    container.getChildren().add(buttonBox);
    this.root.getChildren().add(anchorPane);
    //Init the right css by theme
    switch (Config.getStringValue("THEME")) {
      case "BRIGHT":
        topPane.setStyle("-fx-background-color:#FF4B4B;");
        errorLabel.setStyle((
            "-fx-background-color: #FF4B4B; -fx-background-radius: 5; -fx-text-fill: #FFFFFF;"));
        dragLabel.setStyle(
            "-fx-background-color:#FF4B4B; -fx-text-fill: #FFFFFF; -fx-background-radius: 10;");
        anchorPane.getStylesheets()
            .add(getClass().getResource("/styles/styleBrightTheme.css").toExternalForm());
        break;
      case "DARK":
        topPane.setStyle("-fx-background-color:#F0B27A;");
        boardPane.setStyle("-fx-background-color:#383837;");
        turn.setStyle(
            "-fx-background-color: #ffffff; -fx-background-radius: 10; -fx-text-fill: #000000; ");
        errorLabel.setStyle((
            "-fx-background-color: #ffffff; -fx-background-radius: 5; -fx-text-fill: #000000;"));
        chatPane.setStyle("-fx-background-color:#383837;");
        dragLabel.setStyle(
            "-fx-background-color:#F0B27A; -fx-text-fill: #000000;-fx-background-radius: 10;");
        buttonBox.setStyle("-fx-background-color:#383837;");
        anchorPane.setStyle("-fx-background-color:#383837;");
        content.setStyle("-fx-background-color:#383837;");
        container.setStyle("-fx-background-color:#383837;");
        this.root.setStyle("-fx-background-color:#383837;");
        anchorPane.getStylesheets()
            .add(getClass().getResource("/styles/styleDarkTheme.css").toExternalForm());
        break;
      case "INTEGRA":
        topPane.setStyle("-fx-background-color:#FF8000;");
        errorLabel.setStyle((
            "-fx-background-color: #FF8000; -fx-background-radius: 5; -fx-text-fill: #FFFFFF;"));
        dragLabel.setStyle(
            "-fx-background-color:#FF8000; -fx-text-fill: #FFFFFF; -fx-background-radius: 10;");
        anchorPane.getStylesheets()
            .add(getClass().getResource("/styles/styleIntegra.css").toExternalForm());
        break;
      case "THINC!":
        topPane.setStyle("-fx-background-color:#0A123D;");
        chatPane.setStyle("-fx-background-color:#D8EFFF;");
        errorLabel.setStyle((
            "-fx-background-color: #0A123D; -fx-background-radius: 5; -fx-text-fill: #FFFFFF;"));
        dragLabel.setStyle(
            "-fx-background-color:#0A123D; -fx-text-fill: #FFFFFF; -fx-background-radius: 10;");
        buttonBox.setStyle("-fx-background-color:#D8EFFF;");
        anchorPane.setStyle("-fx-background-color:#D8EFFF;");
        content.setStyle("-fx-background-color:#D8EFFF;");
        container.setStyle("-fx-background-color:#D8EFFF;");
        boardPane.setStyle("-fx-background-color:#D8EFFF;");
        anchorPane.getStylesheets()
            .add(getClass().getResource("/styles/styleThinc.css").toExternalForm());
        break;
      default:
        break;
    }
  }

  /**
   * Handles the Quit Button getting clicked.
   */
  private void handleQuitButtonClicked() {

    Debug.printMessage(
        "SOME CLICKED QUIT the some has type " + gameSession.getLocalPlayer().getType());
    if (this.gameSession.getLocalPlayer().getType().equals(PlayerType.HOST_PLAYER)) {
      //Player is the host
      this.gameSession.getClientHandler().getClient()
          .sendToServer(new WrappedPacket(PacketType.HOST_QUIT_PACKET, new HostQuitPacket()));
      gameController.setActiveUiController(
          new LocalQuitUiController(gameController, gameSession, true));
    } else {
      //Player is a remote player
      this.gameSession.getClientHandler().disconnectClient();
      gameController.setActiveUiController(
          new LocalQuitUiController(gameController, gameSession, false));
    }
  }

  private void refreshUi() {
    int playerSize = gameSession.getGame().getGamemode().getNeededPlayers();
    stage.widthProperty().addListener((obs, oldVal, newVal) -> {
      //makes topPane resizeable
      topPane.setPrefWidth(stage.getWidth());
      //makes container, content and buttonBox fit to stage width
      container.setPrefWidth(stage.getWidth());
      content.setPrefWidth(stage.getWidth());
      buttonBox.setPrefWidth(stage.getWidth());

      //makes ScorePane resizable
      ColumnConstraints coll = new ColumnConstraints();
      coll.setMinWidth(stage.getWidth() / (playerSize + 1));
      for (int i = 0; i <= playerSize; i++) {
        scorePane.getColumnConstraints().set(i, coll);
      }

      root.getChildren().remove(dragablePolyPane);
      dragablePolyPane = null;
    });

    //update player score labels
    for (int i = 0; i < playerSize; i++) {
      if (i < scores.size()) {
        scores.get(i).setText(game.getGameState().getScores()[i] + "");
      }
    }

    //updates turn label
    if (game.getGameState().getPlayerCurrent().equals(localPlayer)) {
      turn.setText("Your Turn");
    } else {
      turn.setText(
          this.gameSession.getGame().getGameState().getPlayerCurrent().getUsername() + " 's Turn");
    }

    // repaint stackPanes
    stackPanes.clear();
    stacks.getChildren().clear();
    Debug.printMessage(game.getGamemode().getName());
    switch (game.getGamemode().getName()) {
      case "JUNIOR":
      case "DUO":
      case "CLASSIC":
        for (Player p : game.getGameState().getPlayerList()) {
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
      default:
        break;
    }
    errorLabel.setText(errorLabelText);
    errorLabel.setVisible(!Config.getStringValue("TOOLTIPS").equals("OFF"));
  }

  /**
   * Method repaints the BoardPane.
   */
  public void repaintBoardPane() {
    //Repaint board and color the right collision fields
    boardPane.resetAllCheckFields();
    if (possibleFields != null) {
      for (int[] coords : possibleFields) {
        switch (dragablePolyPane.getPoly().getColor().toString()) {
          case "RED":
            color = Color.RED;
            break;
          case "BLUE":
            color = Color.BLUE;
            break;
          case "GREEN":
            color = Color.GREEN;
            break;
          case "YELLOW":
            color = Color.YELLOW;
            break;
          default:
            color = Color.BLACK;
        }
        if (game.getGamemode().getName().equals("TRIGON")) {
          boardPane.setCheckFieldColor(color, coords[0], coords[1], coords[2]);
        } else {
          boardPane.setCheckFieldColor(color, coords[0], coords[1]);
        }
      }
    }
    this.boardPane.repaint(game.getGameState().getBoard());
  }

  /**
   * Paints all possible Fields for a move by the player.
   *
   * @param dragablePolyPane dragablePolyPane
   */
  public void paintPossibleFields(DragablePolyPane dragablePolyPane) {
    possibleFields = game.getGameState().getBoard()
        .getPossibleFieldsForPoly(dragablePolyPane.getPoly(),
            game.getGameState().isFirstRound());
  }

  /**
   * Handles Button Submit Request of DraggablePolyPane.
   *
   * @author tgutberl
   */
  public void setSubmitRequested() {
    this.submitRequested = true;
  }

  /**
   * function that updates the screen and calls the next move to be made.
   *
   * @param gameController gameController
   * @param deltaTime      deltaTime
   * @author tgeilen
   */
  @Override
  public void update(AbstractGameController gameController, double deltaTime) {
    //Check if player has moves left. If not : auto-skip
    if (moveCheck == 1) {
      if (this.game.getGameState().getBoard().getPossibleMoves(
          this.gameSession.getGame().getGameState().getRemainingPolys(localPlayer),
          this.gameSession.getGame().getGameState().isFirstRound()).size() == 0) {
        this.localPlayer.nullTurn();
        skipTurnButton.setVisible(false);
        errorLabelText = "You are out of moves and auto-skip turns now...";
        block = true;
      }
    }

    if (!chatSelected) {
      root.requestFocus();
    }

    String help = "";
    //Write new Chatmessages into chat
    for (ChatMessage chatMessage : gameSession.getChat().getChatMessages()) {
      if (!alreadyInChat.contains(chatMessage.getTime() + " "
          + chatMessage.getUsername() + " : " + chatMessage.getMessage() + "\n")) {
        alreadyInChat.add(chatMessage.getTime() + " "
            + chatMessage.getUsername() + " : " + chatMessage.getMessage() + "\n");
        help += chatMessage.getTime().getHours() + ":" + chatMessage.getTime().getMinutes() + " "
            + chatMessage.getUsername() + " : " + chatMessage.getMessage() + "\n";
      }
    }

    this.chat.appendText(help);

    localPlayer = gameSession.getLocalPlayer();

    aiCalcRunning = game.getCurrentPlayer().getAiCalcRunning();
    //Check if AI is calculating - only refresh Board then
    if (!this.gameSession.isLocalPlayerTurn()) {
      moveCheck = 0;
      boardPane.repaint(game.getGameState().getBoard());
      if (!block) {
        errorLabelText = "  Please wait while the other Players are playing!";
      }
      skipTurnButton.setVisible(false);
      if (!game.getGameState().playsTurn()) {
        //Debug.printMessage(this, "" + game.getGameState().playsTurn());
        refreshUi();
      }
    } else {
      moveCheck++;
      if (dragablePolyPane == null) {
        boardPane.repaint(game.getGameState().getBoard());
      }
      if (this.game.getGameState().getBoard()
          .getPossibleFields(this.game.getGameState().getColorFromPlayer(localPlayer),
              this.gameSession.getGame().getGameState().isFirstRound()).size() == 0) {
        this.localPlayer.nullTurn();
        skipTurnButton.setVisible(false);
        if (!block) {
          errorLabelText = "You are out of moves and auto-skip turns now...";
        }
      }
      if (this.game == null) {
        Debug.printMessage(this, "Game is null");
      }
      if (!this.game.getGameState().isFirstRound()) {
        skipTurnButton.setVisible(true);
      }
      if (!game.getGameState().playsTurn()) {
        //Debug.printMessage(this, "" + game.getGameState().playsTurn());
        refreshUi();
      }
      //Check if Player has Turn
      //Debug.printMessage(this, this.localPlayer.getUsername() + " " + this.localPlayer);
      if (this.gameSession.isLocalPlayerTurn()) {
        //Checks if a Poly has no moves anymore in this turn. Then color it grey
        help = "";
        for (ChatMessage chatMessage : gameSession.getChat().getChatMessages()) {
          if (!alreadyInChat.contains(chatMessage.getTime() + " "
              + chatMessage.getUsername() + " : " + chatMessage.getMessage() + "\n")) {
            alreadyInChat.add(chatMessage.getTime() + " "
                + chatMessage.getUsername() + " : " + chatMessage.getMessage() + "\n");
            help +=
                chatMessage.getTime().getHours() + ":" + chatMessage.getTime().getMinutes() + " "
                    + chatMessage.getUsername() + " : " + chatMessage.getMessage() + "\n";
          }
        }
        //hintLabel1.setText("Erkannt");
        //Debug.printMessage(this, "GUI ready for input");
        boolean action = false;
        //Update DragablePolyPane
        if (!this.gameSession.isUpdatingGameState()) {
          for (PolyPane polyPane : stackPanes.get(gameSession.getPlayerList().indexOf(localPlayer))
              .getPolyPanes()) {
            if (inputHandler.isPolyClicked(polyPane)) {
              if (!block) {
                errorLabelText = "  Drag the Poly to a possible Position (it lights up)!";
              }
              chatSelected = false;
              if (dragablePolyPane != null) {
                root.getChildren().remove(dragablePolyPane);
                dragablePolyPane = null;
              }
              //Init the right dragable polypane by Gamemode
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
                default:
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
              //Paint possible fields by color
              boardPane.resetAllCheckFields();
              if (possibleFields != null) {
                for (int[] coords : possibleFields) {
                  switch (dragablePolyPane.getPoly().getColor().toString()) {
                    case "RED":
                      color = Color.RED;
                      break;
                    case "BLUE":
                      color = Color.BLUE;
                      break;
                    case "GREEN":
                      color = Color.GREEN;
                      break;
                    case "YELLOW":
                      color = Color.YELLOW;
                      break;
                    default:
                      color = Color.BLACK;
                  }
                  if (game.getGamemode().getName().equals("TRIGON")) {
                    boardPane.setCheckFieldColor(color, coords[0], coords[1], coords[2]);
                  } else {
                    boardPane.setCheckFieldColor(color, coords[0], coords[1]);
                  }
                }
              }

              boardPane.repaint(game.getGameState().getBoard());

            } else {
              if (!block) {
                errorLabelText = "  Please click on a Poly (Your Color: " + this.game.getGameState()
                    .getColorFromPlayer(localPlayer).toString() + ")";
              }
            }
          }
          //Register inputhandler on dragablepolypane
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
              //Check collision fields to paint them afterwards
              for (Field field : boardPane.getCheckFields()) {
                if (game.getGamemode().getName().equals("TRIGON")) {
                  boardPane.resetCheckFieldColor(field.getXcord(), field.getYcord(),
                      ((TrigonField) field).getIsRight());
                } else {
                  boardPane.resetCheckFieldColor(field.getXcord(), field.getYcord());
                }
                Bounds boardBounds = field.localToScene(field.getBoundsInParent());
                if (polyBounds.intersects(boardBounds)) {
                  //Add is Poly Possible
                  int addX;
                  int addY;
                  int addIsRight = 0;
                  int[] pos;
                  if (game.getGamemode().getName().equals("TRIGON") && dragablePolyPane != null) {
                    //Calculate the right position to check if a poly is possible
                    addX = ((PolyTrigon) dragablePolyPane.getPoly()).getShape().get(0).getPos()[0];
                    addY = ((PolyTrigon) dragablePolyPane.getPoly()).getShape().get(0).getPos()[1];
                    addIsRight = ((PolyTrigon) dragablePolyPane.getPoly()).getShape().get(0)
                        .getPos()[2];
                    pos = new int[3];
                    pos[0] = field.getXcord() + addX;
                    pos[1] = field.getYcord() + addY;
                    pos[2] = addIsRight;
                  } else {
                    addX = ((PolySquare) dragablePolyPane.getPoly()).getShape().get(0).getPos()[0];
                    addY = ((PolySquare) dragablePolyPane.getPoly()).getShape().get(0).getPos()[1];
                    pos = new int[2];
                    pos[0] = field.getXcord() + addX;
                    pos[1] = field.getYcord() + addY;
                  }
                  //Show that a poly is possible and paint it yellow
                  if (game.getGameState().getBoard().isPolyPossible(pos, dragablePolyPane.getPoly(),
                      game.getGameState().isFirstRound())) {
                    errorLabelText = "  Press Enter/Space to place the Poly here!";
                    dragablePolyPane.inncerCircleSetColor();
                    currentIntersection = true;
                    dragablePolyPane.rerender();
                    //When enter was pressed on possible Poly, put it into a
                    // right turn and send to server
                    if (inputHandler.isKeyPressed(KeyCode.ENTER) || inputHandler.isKeyPressed(
                        KeyCode.SPACE) || submitRequested) {
                      errorLabelText = "";
                      possibleFields = null;
                      boardPane.resetAllCheckFields();
                      Turn turn = new Turn(dragablePolyPane.getPoly(), pos);
                      Debug.printMessage("" + turn.getPoly());
                      root.getChildren().remove(dragablePolyPane);
                      dragablePolyPane = null;
                      this.gameSession.getGame().getGameState().playTurn(turn);
                      refreshUi();
                      boardPane.repaint(game.getGameState().getBoard());
                      localPlayer.setSelectedTurn(turn);
                    }
                  }
                }
              }
              //Reset poly light up if there is no intersection
              if (!currentIntersection) {
                errorLabelText = "  Drag the Poly to a possible Position where it lights up!";
                dragablePolyPane.inncerCircleResetColor();
                dragablePolyPane.rerender();
              }
              //Paint the right possible fields
              if (possibleFields != null) {
                for (int[] coords : possibleFields) {
                  switch (dragablePolyPane.getPoly().getColor().toString()) {
                    case "RED":
                      color = Color.RED;
                      break;
                    case "BLUE":
                      color = Color.BLUE;
                      break;
                    case "GREEN":
                      color = Color.GREEN;
                      break;
                    case "YELLOW":
                      color = Color.YELLOW;
                      break;
                    default:
                      color = Color.BLACK;
                  }
                  if (game.getGamemode().getName().equals("TRIGON")) {
                    boardPane.setCheckFieldColor(color, coords[0], coords[1], coords[2]);
                  } else {
                    boardPane.setCheckFieldColor(color, coords[0], coords[1]);
                  }
                }
              }
            }
          } catch (Exception e) {
            Debug.printMessage("");
          }
        }
      }
    }
    submitRequested = false;
    //check if game is over
    if (this.gameSession.isGameOver()) {
      Debug.printMessage("GAME IS OVER");
      //Sending user to ScoreBoard

      ScoreBoardUiController.sortScoreBoard(gameSession);
      gameController.setActiveUiController(
          new ScoreBoardUiController(gameController, gameSession));
    }
    //check if user got kicked
    if (gameSession.getGotKicked()) {
      Debug.printMessage("GETTING KICKED");
      gameController.setActiveUiController(new KickInfoUiController(gameController));
    }

    //Check if the host left, is so, return to the lobby
    if (gameSession.getHostQuit()) {
      //The host has left, so the user is sent to proper screen
      gameController.setActiveUiController(new HostQuitUiController(gameController, gameSession));

    }

  }

  /**
   * Method used to update the current frame.
   *
   * @param gameController GameController of game
   * @author tgutberl
   */
  @Override
  public void update(AbstractGameController gameController) {

  }

  /**
   * Exit Method given by Abstract Class.
   *
   * @author tgutberl
   */
  @Override
  public void onExit() {
  }

  /**
   * Init Method given by Abstract Class.
   *
   * @author tgutberl
   */
  @Override
  public void init(Group root) {

  }

}
