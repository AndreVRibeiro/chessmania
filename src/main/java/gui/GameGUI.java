package gui;

import animatefx.animation.GlowBackground;
import client.Client;
import gamelogic.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import javafx.util.Duration;
import networking.Protocol;
import server.Lobby;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class creates the GUI for the chess game.
 */
public class GameGUI {

    public static Game game;
    private static ImageView imageviewLogo;

    private static Stage gameStage;
    private static Pane root;
    public static GridPane boardPane;
    public static GridPane coordinatesX;
    public static GridPane coordinatesY;
    public static Queue<Integer> moveCoordinates = new LinkedList<>();
    public static ImageView pieceImage = new ImageView();

    private static Label currentTurn;
    private static Label currentColor;
    private static Label turnTimer;
    private static Label gameTimer;
    private static Label timer;

    private static Label playername1;
    private static Label playername2;
    private static Label playername3;
    private static Label playername4;

    private static Label points1;
    private static Label points2;
    private static Label points3;
    private static Label points4;
    private static String nextColor;

    /**
     * This method starts the game GUI
     */
    public static void startGameGUI() {
        Platform.runLater(() -> {

            if (ClientGUI.firstGame == true) {
                ClientGUI.closeLobbyWindow();
                ClientGUI.firstGame = false;
            } else {
                gameStage.close();
            }

            gameStage = new Stage();
            gameStage.setScene(gameScene());
            gameStage.show();
            gameStage.setResizable(false);

            Audio.gamestart();

            Image logo = new Image("/images/Small_Logo.png");
            gameStage.getIcons().add(logo);
            gameStage.setTitle("CHESS MANIA!");

            gameStage.setOnCloseRequest(e -> {
                try {
                    if (game != null) {
                        Client.clientThread.sendCmdToServer(Protocol.ENDGAME, "");
                        Client.clientThread.sendCmdToServer(Protocol.QUITLOBBY, "");
                        ClientGUI.firstGame = true;
                    } else {
                        Client.clientThread.sendCmdToServer(Protocol.QUITLOBBY, "");
                        ClientGUI.firstGame = true;
                    }
                    gameStage.close();
                    ClientGUI.closeLobbyWindow();
                    ClientGUI.inLobby = false;
                    ClientGUI.resetLobbyChat();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            });
        });
    }

    /**
     * This method displays the actual game scene that contains the chessboard, the lobby chat, buttons and text.
     * @return The scene containing everything.
     */
    public static Scene gameScene() {
        // This is used for the layout of the game scene.
        root = new Pane();

        // These are used to display the game info on the scene.
        Label username = new Label();
        username.setText(ClientGUI.myUsername.getValue());
        username.setFont(new Font("Arial",30));
        username.relocate(30,20);

        currentTurn = new Label();
        currentColor = new Label();
        turnTimer = new Label();
        gameTimer = new Label();
        timer = new Label();
        playername1 = new Label();
        playername2 = new Label();
        playername3 = new Label();
        playername4 = new Label();

        points1 = new Label();
        points2 = new Label();
        points3 = new Label();
        points4 = new Label();

        playername1.setFont(new Font("Arial",20));
        playername2.setFont(new Font("Arial",20));
        playername3.setFont(new Font("Arial",20));
        playername4.setFont(new Font("Arial",20));
        points1.setFont(new Font("Arial",12));
        points2.setFont(new Font("Arial",12));
        points3.setFont(new Font("Arial",12));
        points4.setFont(new Font("Arial",12));

        playername2.relocate(90,100);
        playername3.relocate(570,100);
        playername1.relocate(80, 550);
        playername4.relocate(570,550);

        points2.relocate(90,130);
        points3.relocate(570,130);
        points1.relocate(80,580);
        points4.relocate(570,580);

        currentColor.setText("YELLOW");
        currentColor.setFont(new Font("Arial",20));
        currentColor.relocate(820, 100);

        currentTurn.relocate(700,100);
        currentTurn.setFont(new Font("Arial",20));
        currentTurn.setText("Current turn:");

        turnTimer.relocate(930,100);
        turnTimer.setFont(new Font("Arial",20));

        gameTimer.relocate(880,150);
        gameTimer.setFont(new Font("Arial",20));

        timer.setText("Game ends in:");
        timer.setFont(new Font("Arial",20));
        timer.relocate(740,150);

        // The items listed above are now added into the layout.
        root.getChildren().add(username);
        root.getChildren().add(currentTurn);
        root.getChildren().add(currentColor);
        root.getChildren().add(turnTimer);
        root.getChildren().add(gameTimer);
        root.getChildren().add(timer);

        // This creates a new GridPane that is used as the chessboard in the game.
        boardPane = new GridPane();
        root.getChildren().add(boardPane);
        boardPane.setCursor(Cursor.HAND);
        boardPane.relocate(75,59);
        setUpSquares();

        game = new Game(new Lobby());
        game.getBoard().newGameBoard();
        displayBoard();

        // When a board square is pressed, the coordinates are saved and a move is sent to the server.
        boardPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clickGrid(event);

                if (moveCoordinates.size() == 4) {
                    int x1 = moveCoordinates.remove();
                    int y1 = moveCoordinates.remove();
                    int x2 = moveCoordinates.remove();
                    int y2 = moveCoordinates.remove();
                    String move = getMoveCommand(x1, y1, x2, y2);
                    game.move(game.getCurrentTurn(), new Move(game.getCurrentTurn(), move));
                    moveCoordinates.clear();
                    Client.clientThread.sendCmdToServer(Protocol.MOVE, move);
                }

            }
        });

        // This adds the Logo of the game to the game scene.
        Image image = new Image("/images/Logo_name2.png");
        imageviewLogo = new ImageView();
        imageviewLogo.setImage(image);
        imageviewLogo.setFitWidth(300); //750
        imageviewLogo.setPreserveRatio(true);
        imageviewLogo.setVisible(true);
        imageviewLogo.relocate(690,-25);

        root.getChildren().add(imageviewLogo);


        // This creates a new chat window, a playerList, a text field and a send button and all the corresponding labels.
        ListView lobbyChatView = new ListView(ClientGUI.lobbyChatList);
        ListView lobbyPLayerView = new ListView(ClientGUI.lobbyPlayerList);
        TextField input = new TextField();
        CustomButtons send = new CustomButtons("SEND",'W',0,10);
        Label players = new Label();
        Label chat = new Label();

        // The lobbyPlayerList is put on the scene and adjusted.
        lobbyPLayerView.relocate(700,230);
        lobbyPLayerView.setPrefSize(270,95); //120
        lobbyPLayerView.setOnMouseClicked(e -> {
            String selectedPlayer = (String) lobbyPLayerView.getSelectionModel().getSelectedItem();
            if (e.getClickCount() == 2 && selectedPlayer != null) {
                if (!selectedPlayer.equals(ClientGUI.myUsername.get())) {
                    input.setText("@"+selectedPlayer+" ");
                }
            }
        });

        // The labels are created.
        players.setText("Players:");
        players.relocate(700,200);
        players.setFont(new Font("Arial",20));

        lobbyChatView.relocate(700,385); //410
        lobbyChatView.setPrefSize(270,170);
        chat.setText("Lobby chat:");
        chat.relocate(700,355); // 380
        chat.setFont(new Font("Arial",20));

        // This sets up he textField.
        input.setPromptText("Enter messages here");
        input.relocate(700,565); //590
        input.setPrefSize(215,30);
        input.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                if (!input.getText().trim().equals("")) {
                    if (input.getText().startsWith("@")) {
                        String message = input.getText();
                        Client.clientThread.sendCmdToServer(Protocol.WHISPERCHAT, message.substring(1));
                        input.clear();
                    } else {
                        String message = input.getText();
                        Client.clientThread.sendCmdToServer(Protocol.LOBBYCHAT, message);
                        input.clear();
                    }
                }
            }
        });

        // This sets up the send button.
        send.relocate(920,565); //590
        send.setPrefSize(50,30);
        send.setOnAction(e -> {
            if(!input.getText().trim().equals("")) {
                if (input.getText().startsWith("@")) {
                    String message = input.getText();
                    Client.clientThread.sendCmdToServer(Protocol.WHISPERCHAT, message.substring(1));
                    input.clear();
                } else {
                    String message = input.getText();
                    Client.clientThread.sendCmdToServer(Protocol.LOBBYCHAT, message);
                    input.clear();
                }
            }
        });

        // This initializes a new quit button and gives it a task.
        CustomButtons quit = new CustomButtons("QUIT GAME",'G',1,15);
        quit.relocate(700,605);
        quit.setPrefSize(130,50);
        quit.setOnAction(e -> {
            e.consume();
                try {
                    if (game != null) {
                        Client.clientThread.sendCmdToServer(Protocol.ENDGAME, "");
                        Client.clientThread.sendCmdToServer(Protocol.QUITLOBBY, "");
                        ClientGUI.firstGame = true;
                    } else {
                        Client.clientThread.sendCmdToServer(Protocol.QUITLOBBY, "");
                        ClientGUI.firstGame = true;
                    }
                    gameStage.close();
                    ClientGUI.closeLobbyWindow();
                    ClientGUI.inLobby = false;
                    ClientGUI.resetLobbyChat();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
        });

        // This initializes a newGame button and gives it a task.
        CustomButtons newGame = new CustomButtons("NEW GAME",'Y',1,15);
        newGame.relocate(840,605); // 630
        newGame.setPrefSize(130,50);
        newGame.setOnAction(e -> {
            Client.clientThread.sendCmdToServer(Protocol.NEWGAME,"");
        });

        // All the objects are added to the root layout and made visible.
        root.getChildren().add(players);
        root.getChildren().add(chat);
        root.getChildren().add(lobbyPLayerView);
        root.getChildren().add(lobbyChatView);
        root.getChildren().add(input);
        root.getChildren().add(send);
        root.getChildren().add(quit);
        root.getChildren().add(newGame);
        root.getChildren().add(playername1);
        root.getChildren().add(playername2);
        root.getChildren().add(playername3);
        root.getChildren().add(playername4);
        root.getChildren().add(points1);
        root.getChildren().add(points2);
        root.getChildren().add(points3);
        root.getChildren().add(points4);

        //root.setStyle("-fx-background-color: blue, lightblue;");
        root.setStyle("-fx-background-color: rgb(230, 173, 173)");
        Scene scene = new Scene(root,1000,700);

        return scene;
    }

    /**
     * This function is used to determine the coordinates of the clicked squares on the chessboard.
     * @param event The mouse event
     */
    public static void clickGrid(javafx.scene.input.MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        Integer x;
        Integer y;
        if (clickedNode != boardPane) {
            x = GridPane.getRowIndex(clickedNode);
            y = GridPane.getColumnIndex(clickedNode);
            moveCoordinates.add(x);
            moveCoordinates.add(y);
        }
    }

    /**
     * This function translates the coordinates into actual move commands.
     * @param x1 The x coordinate of the first square
     * @param y1 The y coordinate of the first square
     * @param x2 The x coordinate of the second square
     * @param y2 The y coordinate of the second square
     * @return The move
     */
    public static String getMoveCommand(int x1, int y1, int x2, int y2) {
        String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
        String[] nums = {"14", "13", "12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1"};

        String move = "";
        move += chars[y1];
        move += nums[x1];
        move += "/";
        move += chars[y2];
        move += nums[x2];

        return move;
    }

    /**
     * This method creates a boardPane containing rectangles with different colors. This is the chessboard.
     */
    public static void setUpSquares() {
        Platform.runLater(() -> {
            for (int i = 0; i < 14; i++) {
                for (int j = 0; j < 14; j++) {

//                    StackPane stack = new StackPane();
                    Rectangle square = new Rectangle(43,43);
                    square.setFill(getSquareColor(i,j));

                    boardPane.add(square,i,j);
//                    boardPane.add(stack, i, j);
                }
            }
        });
    }

    /**
     * This function will be used to display a game over message in a new stage.
     * @param winnerTeam The winning team.
     */
    public static void gameOverMessage(String winnerTeam) {

    }

    /**
     * This function will be used for the animations of the pieces.
     */
    public static void colorAnimation() {
        Platform.runLater(() -> {
            for (int i = 0; i < 14; i++) {
                for (int j = 0; j < 14; j++) {

//                    StackPane stack = new StackPane();
                    Rectangle square = new Rectangle(43,43);
                    colorSquares(i,j, square);

                }
            }
        });
    }

    /**
     * This function gives each rectangle in the chessboard its respective color.
     * @param i x-coordinate
     * @param j y-coordinate
     * @return The color
     */
    public static Color getSquareColor(int i, int j) {
        int[][] coordinates = {{0,0,0,1,2,1,2,1,2,1,2,0,0,0},{0,0,0,2,1,2,1,2,1,2,1,0,0,0},
                {0,0,0,1,2,1,2,1,2,1,2,0,0,0}, {1,2,1,2,1,2,1,2,1,2,1,2,1,2},
                {2,1,2,1,2,1,2,1,2,1,2,1,2,1}, {1,2,1,2,1,2,1,2,1,2,1,2,1,2},
                {2,1,2,1,2,1,2,1,2,1,2,1,2,1}, {1,2,1,2,1,2,1,2,1,2,1,2,1,2},
                {2,1,2,1,2,1,2,1,2,1,2,1,2,1}, {1,2,1,2,1,2,1,2,1,2,1,2,1,2},
                {2,1,2,1,2,1,2,1,2,1,2,1,2,1}, {0,0,0,2,1,2,1,2,1,2,1,0,0,0},
                {0,0,0,1,2,1,2,1,2,1,2,0,0,0}, {0,0,0,2,1,2,1,2,1,2,1,0,0,0}};

        if (coordinates[i][j] == 0) {
//            return Color.LIGHTGREY;
            if (nextColor == null) {
                return Color.PINK;
            }
            switch (nextColor) {
                case "R":
                    return Color.rgb(230, 173, 173);
                case "B":
                    return Color.rgb(173, 216, 230);
                case "Y":
                    return Color.rgb(230, 229, 173);
                case "G":
                    return Color.rgb(173, 230, 183);
            }

        } else if (coordinates[i][j] == 1) {
            return  Color.rgb(216, 230, 173);
        } else {
            return Color.rgb(197, 218, 134);
        }
        return Color.DARKGREY;
    }

    /**
     * This function has the purpose to color the illegal squares that belong to the chessboard.
     * @param i position i
     * @param j position j
//     * @param stack The stack
     * @param square The Rectangle
     */
    public static void colorSquares(int i, int j, Rectangle square) {
        int[][] coordinates = {{0,0,0,1,2,1,2,1,2,1,2,0,0,0},{0,0,0,2,1,2,1,2,1,2,1,0,0,0},
                {0,0,0,1,2,1,2,1,2,1,2,0,0,0}, {1,2,1,2,1,2,1,2,1,2,1,2,1,2},
                {2,1,2,1,2,1,2,1,2,1,2,1,2,1}, {1,2,1,2,1,2,1,2,1,2,1,2,1,2},
                {2,1,2,1,2,1,2,1,2,1,2,1,2,1}, {1,2,1,2,1,2,1,2,1,2,1,2,1,2},
                {2,1,2,1,2,1,2,1,2,1,2,1,2,1}, {1,2,1,2,1,2,1,2,1,2,1,2,1,2},
                {2,1,2,1,2,1,2,1,2,1,2,1,2,1}, {0,0,0,2,1,2,1,2,1,2,1,0,0,0},
                {0,0,0,1,2,1,2,1,2,1,2,0,0,0}, {0,0,0,2,1,2,1,2,1,2,1,0,0,0}};

        if (coordinates[i][j] == 0) {
            if (nextColor.equals("R")) {
                root.setStyle("-fx-background-color: rgb(230, 173, 173)");
                square.setFill(Color.rgb(230, 173, 173));
                boardPane.add(square,i,j);
//                boardPane.add(stack, i, j);
            } else if (nextColor.equals("B")) {
                root.setStyle("-fx-background-color: rgb(173, 216, 230)");
                square.setFill(Color.rgb(173, 216, 230));
                boardPane.add(square,i,j);
//                boardPane.add(stack, i, j);
            } else if (nextColor.equals("Y")) {
                root.setStyle("-fx-background-color: rgb(230, 229, 173)");
                square.setFill(Color.rgb(230, 229, 173));
                boardPane.add(square,i,j);
//                boardPane.add(stack, i, j);
            } else if (nextColor.equals("G")) {
                root.setStyle("-fx-background-color: rgb(173, 230, 183)");
                square.setFill(Color.rgb(173, 230, 183));
                boardPane.add(square,i,j);
//                boardPane.add(stack, i, j);
            }
        }

    }

    /**
     * This method puts every single piece image on the chessboard and makes it visible to the player.
     */
    public static void displayBoard() {
        setUpSquares();
        Platform.runLater(() -> {

            if (game.getBoard() == null) {
                System.out.println("gameboard gleich null");
                return;
            }

            for (int x = 0; x < 14; x++) {
                for (int y = 0; y < 14; y++) {
                    // The red pieces are placed on the board
                    if (game.getBoard().getPieceStr(x, y).equals("RP")) {
                        Image bPimage = new Image("/images/pieces/rP.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("RR")) {
                        Image bPimage = new Image("/images/pieces/rR.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("RN")) {
                        Image bPimage = new Image("/images/pieces/rN.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("RB")) {
                        Image bPimage = new Image("/images/pieces/rB.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("RQ")) {
                        Image bPimage = new Image("/images/pieces/rQ.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("RK")) {
                        Image bPimage = new Image("/images/pieces/rK.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }

                    // The blue pieces are placed on the board
                    if (game.getBoard().getPieceStr(x, y).equals("BP")) {
                        Image bPimage = new Image("/images/pieces/bP.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("BR")) {
                        Image bPimage = new Image("/images/pieces/bR.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("BN")) {
                        Image bPimage = new Image("/images/pieces/bN.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("BB")) {
                        Image bPimage = new Image("/images/pieces/bB.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("BQ")) {
                        Image bPimage = new Image("/images/pieces/bQ.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("BK")) {
                        Image bPimage = new Image("/images/pieces/bK.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }

                    // The yellow pieces are placed on the board
                    if (game.getBoard().getPieceStr(x, y).equals("YP")) {
                        Image bPimage = new Image("/images/pieces/yP.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("YR")) {
                        Image bPimage = new Image("/images/pieces/yR.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("YN")) {
                        Image bPimage = new Image("/images/pieces/yN.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("YB")) {
                        Image bPimage = new Image("/images/pieces/yB.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("YQ")) {
                        Image bPimage = new Image("/images/pieces/yQ.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("YK")) {
                        Image bPimage = new Image("/images/pieces/yK.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }

                    // The green pieces are placed on the board
                    if (game.getBoard().getPieceStr(x, y).equals("GP")) {
                        Image bPimage = new Image("/images/pieces/gP.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("GR")) {
                        Image bPimage = new Image("/images/pieces/gR.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("GN")) {
                        Image bPimage = new Image("/images/pieces/gN.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("GB")) {
                        Image bPimage = new Image("/images/pieces/gB.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("GQ")) {
                        Image bPimage = new Image("/images/pieces/gQ.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }
                    if (game.getBoard().getPieceStr(x, y).equals("GK")) {
                        Image bPimage = new Image("/images/pieces/gK.png");
                        ImageView bP = new ImageView();
                        bP.setImage(bPimage);
                        bP.setFitHeight(43);
                        bP.setFitWidth(43);
                        boardPane.add(bP, y, x);
                    }

                }
            }
        });

    }

    /**
     * This function sets the next player on the gameGUI and changes the color of the player when it is their turn.
     * @param player The player whose turn it is.
     * @param color The color of the player
     */
    public static void setNextTurn(String player, String color) {
        Platform.runLater(() -> {
            moveCoordinates.clear();
            currentColor.setText(player);
            nextColor = color;

            switch (color) {
                case "R":
                    currentColor.setTextFill(Color.rgb(255, 34, 13));
                    colorAnimation();
//                    root.setStyle("-fx-background-color: rgb(230, 173, 173)");
                    break;
                case "B":
                    currentColor.setTextFill(Color.rgb(0, 162, 255));
                    colorAnimation();
//                    root.setStyle("-fx-background-color: rgb(173, 216, 230)");
                    break;
                case "Y":
                    currentColor.setTextFill(Color.rgb(250, 225, 50));
                    colorAnimation();
//                    root.setStyle("-fx-background-color: rgb(230, 229, 173)");
                    break;
                case "G":
                    currentColor.setTextFill(Color.rgb(97, 216, 53));
                    colorAnimation();
//                    root.setStyle("-fx-background-color: rgb(173, 230, 183)");
                    break;
            }
        });
    }

    /**
     * This method sets up the names of each player with their respective colors on the gamGUI
     */
    public static void setPlayernames() {
        Platform.runLater(() -> {

            playername1.setText(game.getPlayer(1));
            playername1.setTextFill(Color.rgb(255, 34, 13));

            playername2.setText(game.getPlayer(2));
            playername2.setTextFill(Color.rgb(0, 162, 255));

            playername3.setText(game.getPlayer(3));
            playername3.setTextFill(Color.rgb(250, 225, 50));

            playername4.setText(game.getPlayer(4));
            playername4.setTextFill(Color.rgb(97, 216, 53));

        });

    }


    /**
     * This function sets up the timers in the gameGUI.
     * @param gameT The game timer.
     * @param turnT The turn timer.
     */
    public static void setTimers(String gameT, String turnT) {
        Platform.runLater(() -> {
            try {
                gameTimer.setText(gameT);
                turnTimer.setText(turnT + "s");
            } catch (NullPointerException e) {
                return;
            }
        });

    }

    /**
     * This method sets up the achieved points in the gameGUI.
     */
    public static void setPoints() {
        Platform.runLater(() -> {

            String p1 = "" + game.getPlayerpoints(0);
            String p2 = "" + game.getPlayerpoints(1);
            String p3 = "" + game.getPlayerpoints(2);
            String p4 = "" + game.getPlayerpoints(3);

            points1.setText("Points: " + p1);
            points2.setText("Points: " + p2);
            points3.setText("Points: " + p3);
            points4.setText("Points: " + p4);

        });

    }

    /**
     * This method sets up the game over message on the gamGUI.
     */
    public static void setEndGame() {
        Platform.runLater(() -> {
            currentTurn.setText("GAME OVER");
            timer.setText("Game ended.");
            currentColor.setText("");
            turnTimer.setText(" ");
            gameTimer.setText(" ");
        });
    }

    /**
     * This function sets up the winners on the gameGUI.
     * @param winner1 The first winner.
     * @param winner2 The second winner.
     */
    public static void setWinners(String winner1, String winner2) {
        Platform.runLater(() -> {
            timer.setText(winner1 + " / " + winner2);
            gameTimer.setText("  ");
            turnTimer.setText("  ");
        });
    }

}
