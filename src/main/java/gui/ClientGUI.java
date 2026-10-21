package gui;

import animatefx.animation.*;
import client.Client;
import javafx.application.Platform;
import javafx.scene.text.FontWeight;
import networking.Protocol;
import gui.chat.AlertBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

/**
 * This class creates the GUI for the client.
 */
public class ClientGUI extends Application {


    public static ObservableList globalChatList = FXCollections.observableArrayList();
    public static ObservableList lobbyList = FXCollections.observableArrayList();
    public static ObservableList playerList = FXCollections.observableArrayList();
    public static ObservableList lobbyPlayerList = FXCollections.observableArrayList();
    public static ObservableList lobbyChatList = FXCollections.observableArrayList();
    public static ObservableList highScoreList = FXCollections.observableArrayList();
    public static ObservableList pastGamesList = FXCollections.observableArrayList();
    public static ObservableList openGamesList = FXCollections.observableArrayList();
    public static ObservableList runningGamesList = FXCollections.observableArrayList();
    public static StringProperty myUsername = new SimpleStringProperty("");
    public static StringProperty myLobbyName = new SimpleStringProperty("");
    public static Stage lobbyWindow;
    public static boolean inLobby = false;
    public static boolean openLobbyWindow = false;
    public static boolean firstGame = true;

    private ImageView imageviewLogo;
    private ImageView imageviewLogo2;
    private ImageView imageviewLogo3;
    private ImageView imageviewLogo4;
    private ImageView imageviewLogo5;
    Scene titleMenu;
    Scene mainMenu;
    private double stageWidth;
    private double stageHeight;
    private int size = 15;

    /**
     * Main method to start the ClientGUI
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This will start the clientGUI
     * @param primaryStage The main stage
     */
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        Image logo = new Image("/images/Small_Logo.png");
        primaryStage.getIcons().add(logo);
        primaryStage.setTitle("CHESS MANIA!");
        primaryStage.setOnCloseRequest(e -> {
            Client.clientThread.sendCmdToServer(Protocol.LOGOUT,"");
            Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS,"UPDATE");
            System.exit(0);
        });

        if (Client.getUsername() != null) {
            try {
                Client.clientThread.sendCmdToServer(Protocol.LOGIN, Client.getUsername());
                Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");
                myUsername.setValue(Client.getUsername());
                introScene(primaryStage);
            } catch (Exception e2) {
                AlertBox.display("Server offline", "Couldn't connect to the server" );
            }
        } else {
            showLogin();
            introScene(primaryStage);
        }
    }

    /**
     * This displays the intro with the logo and some animations that still need to be done
     * @param primaryStage The Stage
     */
    public void introScene(Stage primaryStage) {

        // Adds the logo to the main page of the game
        Image image = new Image("/images/intro_1.png");
        imageviewLogo = new ImageView();
        imageviewLogo.setImage(image);
        imageviewLogo.setFitWidth(850);
        imageviewLogo.setPreserveRatio(true);
        imageviewLogo.setVisible(true);
        imageviewLogo.setLayoutX(65);
        imageviewLogo.setLayoutY(5);

        new ZoomIn(imageviewLogo).setSpeed(0.5).play();

        Image image2 = new Image("/images/intro_2.png");
        imageviewLogo2 = new ImageView();
        imageviewLogo2.setImage(image2);
        imageviewLogo2.setFitWidth(850);
        imageviewLogo2.setPreserveRatio(true);
        imageviewLogo2.setVisible(true);
        imageviewLogo2.relocate(65,5);

        Image image3 = new Image("/images/intro_3.png");
        imageviewLogo3 = new ImageView();
        imageviewLogo3.setImage(image3);
        imageviewLogo3.setFitWidth(850);
        imageviewLogo3.setPreserveRatio(true);
        imageviewLogo3.setVisible(true);
        imageviewLogo3.relocate(65,5);

        Image image4 = new Image("/images/intro_corner_1.png");
        imageviewLogo4 = new ImageView();
        imageviewLogo4.setImage(image4);
        imageviewLogo4.setFitWidth(850);
        imageviewLogo4.setPreserveRatio(true);
        imageviewLogo4.setVisible(true);
        imageviewLogo4.relocate(122,122);

        Image image5 = new Image("/images/intro_corner_2.png");
        imageviewLogo5 = new ImageView();
        imageviewLogo5.setImage(image5);
        imageviewLogo5.setFitWidth(850);
        imageviewLogo5.setPreserveRatio(true);
        imageviewLogo5.setVisible(true);
        imageviewLogo5.relocate(0,0);


        new SlideInUp(imageviewLogo2).setSpeed(0.4).play();
        new SlideInDown(imageviewLogo3).setSpeed(0.4).play();
        new SlideInRight(imageviewLogo4).setSpeed(0.5).play();
        new SlideInLeft(imageviewLogo5).setSpeed(0.5).play();

        // Creates a Start-button
        CustomButtons buttonStart = new CustomButtons("START",'Y',0,size);
        buttonStart.relocate(390,500);
        buttonStart.setPrefSize(190,50);
        buttonStart.setOnAction(e -> mainMenu(primaryStage));


        // Creates a new Layout
        Pane root = new Pane();
        root.getChildren().add(buttonStart);
        root.getChildren().add(imageviewLogo);
        root.getChildren().add(imageviewLogo2);
        root.getChildren().add(imageviewLogo3);
        root.getChildren().add(imageviewLogo4);
        root.getChildren().add(imageviewLogo5);

        root.setStyle("-fx-background-color: blue, lightblue;");

        // Scene1
        titleMenu = new Scene(root,970,600);
        primaryStage.setScene(titleMenu);
        primaryStage.show();
        primaryStage.setResizable(false);
        Audio.intromusic();
        Audio.king("/UI-Sounds/intro.mp3");
        //new GlowBackground(root, Color.FUCHSIA,Color.OLIVE,5).setSpeed(0.5).play();
        new FadeIn(buttonStart).setSpeed(0.4).play();
    }

    /**
     * This displays the main menu of the game, containing the chat, lobbylist, playerlist and various buttons.
     * @param primaryStage The stage
     */
    public void mainMenu(Stage primaryStage) {

        ListView globalChatView = new ListView<>(globalChatList);
        ListView lobbyListView = new ListView<String>(lobbyList);
        ListView playerListView = new ListView<>(playerList);
        Label nameLabel = new Label();
        Label playerListLabel = new Label();
        Label lobbyListLabel = new Label();
        Label chatListLabel = new Label();
        TextField input = new TextField();
        CustomButtons tutorial, gameList, highscores, credits,  changeName, newLobby, joinLobby, sendMessage, myLobby, ping;

        primaryStage.setTitle("CHESS MANIA!");
        primaryStage.setOnCloseRequest(e -> {
            Client.clientThread.sendCmdToServer(Protocol.LOGOUT, "");
            Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS,"UPDATE");
            System.exit(0);
        });

        // Scene2
        Pane root = new Pane();
        mainMenu = new Scene(root,970,600);
        primaryStage.setScene(mainMenu);
        primaryStage.show();

        // The Logo is created
        Image image = new Image("/images/Logo_name2.png");
        imageviewLogo = new ImageView();
        imageviewLogo.setImage(image);
        imageviewLogo.setFitWidth(900); //750
        imageviewLogo.setPreserveRatio(true);
        imageviewLogo.setVisible(true);
        imageviewLogo.setLayoutX(40);
        imageviewLogo.setLayoutY(-130);

        new Pulse(imageviewLogo).setCycleCount(Integer.MAX_VALUE).setSpeed(0.5).play();

        // Ping-button
        ping = new CustomButtons("PING",'W',1,12);
        ping.relocate(10,10);
        ping.setPrefSize(75,30);
        ping.setOnAction(e -> {
            ping.setText("" + Client.getRTT() + " ms");
        });

        // Change Name-button
        changeName = new CustomButtons("CHANGE NAME",'B',0, size);
        changeName.relocate(75,260);
        changeName.setPrefSize(190,50);
        changeName.setOnAction(e -> {
            changeUsername();
            Client.clientThread.sendCmdToServer(Protocol.SHOWLOBBY, "");
            mainMenu(primaryStage);
        });

        // Tutorial-button
        tutorial = new CustomButtons("TUTORIAL",'G',0,size);
        tutorial.relocate(75,320);
        tutorial.setPrefSize(190,50);
        tutorial.setOnAction(e -> {
            showTutorialFirstScene(primaryStage);
            Audio.stop();
            Audio.kingAdd("/UI-Sounds/tutorial.mp3");
        });

        // Highscores-button
        highscores = new CustomButtons("HIGHSCORES",'R',0,size);
        highscores.relocate(75,380);
        highscores.setPrefSize(190,50);
        highscores.setOnAction(e -> {
            Client.clientThread.sendCmdToServer(Protocol.HIGHSCORES, "");
            showHighscore(primaryStage);
        });

        // GameList-button
        gameList = new CustomButtons("GAME LISTS",'B',0,size);
        gameList.relocate(75,440);
        gameList.setPrefSize(190,50);
        gameList.setOnAction(e -> setGameList(primaryStage));

        // Credits-button
        credits = new CustomButtons("CREDITS",'Y',0,size);
        credits.relocate(75,500);
        credits.setPrefSize(190,50);
        credits.setOnAction(e -> showCredits(primaryStage));

        // New Lobby-button
        newLobby = new CustomButtons("NEW LOBBY",'B',1,12);
        newLobby.relocate(695,500);
        newLobby.setPrefSize(95,50);
        newLobby.setOnAction(e -> {
            if (!inLobby) {
                Client.clientThread.sendCmdToServer(Protocol.CREATELOBBY,"");
                Client.clientThread.sendCmdToServer(Protocol.UPDATELOBBIES, "");
                showLobby();
                myLobbyName.setValue(myUsername.getValue()+"'s Lobby");
                inLobby = true;
                openLobbyWindow = true;
            }
        });

        // Join Lobby-button
        joinLobby = new CustomButtons("JOIN LOBBY",'R',1,12);
        joinLobby.relocate(800,500);
        joinLobby.setPrefSize(95,50);
        joinLobby.setOnAction(e -> {
            Client.clientThread.sendCmdToServer(Protocol.UPDATELOBBIES, "");

            if (!inLobby) {
                String lobbyEntry = (String) lobbyListView.getSelectionModel().getSelectedItem();
                if (lobbyEntry != null) {
                    String[] data = lobbyEntry.split(" ");
                    int l = data.length;
                    int lobbyID = Integer.parseInt(data[0].substring(8));
                    String lobbyName = data[2] + " Lobby";
                    System.out.println(lobbyName);
                    int playercount = Integer.parseInt(data[l-1].substring(0, 1));

                    if (playercount != 4) {
                        Client.clientThread.sendCmdToServer(Protocol.JOINLOBBY, "" + lobbyID);
                        showLobby();
                        inLobby = true;
                        myLobbyName.setValue(lobbyName);
                    }
                }
            }
        });

        // Send Message-button
        sendMessage = new CustomButtons("SEND",'W',0,10);
        //sendMessage.setText();
        sendMessage.relocate(620,520);
        sendMessage.setPrefSize(50,30);
        sendMessage.setOnAction(e -> {
            String msg = input.getText();
            if (!input.getText().trim().equals("")) {
                if (isNotCommand(msg)) {
                    Client.clientThread.sendCmdToServer(Protocol.GLOBALCHAT, msg);
                    input.clear();
                }
            }
            input.clear();
        });

        // My-Lobby button
        myLobby = new CustomButtons("MY LOBBY",'W',1,10);
        myLobby.relocate(820,363);
        myLobby.setPrefSize(75,30);
        myLobby.setOnAction(e -> {
            if (inLobby && !openLobbyWindow) {
                showLobby();
                openLobbyWindow = true;
            } else if (inLobby && openLobbyWindow){
                lobbyWindow.toFront();
                } else {
                Audio.error();
                AlertBox.display("Lobby error", "You are currently not in a lobby. \nCreate a lobby or join an already existing lobby!");
            }
        });

        // globalChatView
        globalChatView.relocate(290,260);
        globalChatView.setPrefSize(380,248);

        // playerListView
        playerListView.relocate(695,260);
        playerListView.setPrefSize(200,95);
        playerListView.setOnMouseClicked(e -> {
            String selectedPlayer = (String) playerListView.getSelectionModel().getSelectedItem();
            if (e.getClickCount() == 2 && selectedPlayer != null) {
                if (!selectedPlayer.equals(myUsername.get())) {
                    input.setText("@"+selectedPlayer+" ");
                }
            }
        });

        // lobbyListView
        lobbyListView.relocate(695,397);
        lobbyListView.setPrefSize(200,95);
        lobbyListView.setOnMouseClicked(e -> {
            String lobbyEntry = (String) lobbyListView.getSelectionModel().getSelectedItem();
            if (e.getClickCount() == 2 && !inLobby) {
                if (lobbyEntry != null) {
                    String[] data = lobbyEntry.split(" ");
                    int l = data.length;
                    int lobbyID = Integer.parseInt(data[0].substring(8));
                    int playercount = Integer.parseInt(data[l-1].substring(0, 1));

                    if (playercount != 4) {
                        Client.clientThread.sendCmdToServer(Protocol.JOINLOBBY, "" + lobbyID);
                        showLobby();
                        inLobby = true;
                    }
                }
            }
        });

        //Text-Input
        input.setPromptText("Enter messages here");
        input.relocate(290,520);
        input.setPrefSize(320,30);
        input.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {

                String msg = input.getText();
                if (!input.getText().trim().equals("")) {
                    if (isNotCommand(msg)) {
                        Client.clientThread.sendCmdToServer(Protocol.GLOBALCHAT, msg);
                        input.clear();
                    }
                }
                input.clear();
            }
        });

        // The labels get their values.
        nameLabel.setText(myUsername.getValue());
        nameLabel.setId("username");
        nameLabel.relocate(75,235);
        nameLabel.setFont(new Font("Arial",20));
        playerListLabel.setText("Players:");
        playerListLabel.relocate(695,235);
        playerListLabel.setFont(new Font("Arial",20));
        lobbyListLabel.setText("Lobbies:");
        lobbyListLabel.relocate(695,372);
        lobbyListLabel.setFont(new Font("Arial",20));
        chatListLabel.setText("Global chat:");
        chatListLabel.relocate(290,235);
        chatListLabel.setFont(new Font("Arial",20));

        // All the objects created above are added to the layout
        root.getChildren().add(joinLobby);
        root.getChildren().add(ping);
        root.getChildren().add(myLobby);
        root.getChildren().add(newLobby);
        root.getChildren().add(credits);
        root.getChildren().add(tutorial);
        root.getChildren().add(gameList);
        root.getChildren().add(changeName);
        root.getChildren().add(highscores);
        root.getChildren().add(sendMessage);
        root.getChildren().add(imageviewLogo);
        root.getChildren().add(globalChatView);
        root.getChildren().add(playerListView);
        root.getChildren().add(lobbyListView);
        root.getChildren().add(input);
        root.getChildren().add(nameLabel);
        root.getChildren().add(lobbyListLabel);
        root.getChildren().add(chatListLabel);
        root.getChildren().add(playerListLabel);
        root.setStyle("-fx-background-color: blue, lightblue;");
    }

    /**
     * This method displays a stage where the client can write a username and login to the server.
     */
    public void showLogin() {
        Stage loginWindow = new Stage();
        Image logo = new Image("/images/Small_Logo.png");
        loginWindow.getIcons().add(logo);
        loginWindow.initModality(Modality.APPLICATION_MODAL);
        loginWindow.setTitle("Login");

        loginWindow.setOnCloseRequest(e -> {
            Client.clientThread.sendCmdToServer(Protocol.LOGOUT, "");
            System.exit(0);
        });

        Label text = new Label();
        CustomButtons submit = new CustomButtons("SUBMIT",'W',1,12);
        CustomButtons quit = new CustomButtons("QUIT",'W',1,12);
        TextField usernameInput = new TextField();
        GridPane buttons = new GridPane();
        GridPane layout = new GridPane();

        text.setText("Please enter a username:");
        text.setId("usernameInput");

        usernameInput.setPromptText("Enter a Username");
        usernameInput.setText(getSystemUsername());
        usernameInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {

                if (!validUsername(usernameInput) && !usernameInput.getText().equals("")) {
                    AlertBox.display("Username error", "This is not a valid username.");
                } else {
                    String username1 = usernameInput.getText();
                    try {
                        Client.clientThread.sendCmdToServer(Protocol.LOGIN, username1);
                        Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");
                    } catch (Exception e1) {
                        AlertBox.display("Server offline", "Couldn't connect to the server" );
                    }
                    loginWindow.close();
                }
            }
        });

        // Sumbit button
        submit.setPrefSize(75,30);
        submit.setOnAction(e -> {
            String username = usernameInput.getText();
            if (!username.equals("") && !validUsername(usernameInput)) {
                AlertBox.display("Username error", "This is not a valid username.");
            } else {
                String username2 = usernameInput.getText();
                try {
                    Client.clientThread.sendCmdToServer(Protocol.LOGIN, username2);
                    Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");
                } catch (Exception e1) {
                    AlertBox.display("Server offline", "Couldnt connect to the server" );
                }
                loginWindow.close();
            }
        });

        // Quit button
        quit.setPrefSize(75,30);
        quit.setOnAction(e -> {
                Client.clientThread.sendCmdToServer(Protocol.LOGOUT, "");
                Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");
            System.exit(0);
        });

        buttons.add(quit, 0, 0);
        buttons.add(submit, 1, 0);
        buttons.setAlignment(Pos.CENTER);
        buttons.setHgap(10);

        layout.setPadding(new Insets(15, 15, 15, 5));
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setAlignment(Pos.CENTER);
        layout.add(text, 1, 0);
        layout.add(usernameInput, 1, 1);
        layout.add(buttons, 1, 2);
        layout.setStyle("-fx-background-color: blue, lightblue;");

        loginWindow.setHeight(200);
        loginWindow.setWidth(300);
        loginWindow.setMaxHeight(200);
        loginWindow.setMaxWidth(300);
        loginWindow.setMinHeight(200);
        loginWindow.setMinWidth(300);

        Scene scene = new Scene(layout);
        loginWindow.setScene(scene);
        loginWindow.showAndWait();
    }

    /**
     * This method displays a new stage. This stage represents the lobby and contains a lobby chat, a player list,
     * and buttons to leave the lobby and to start a new game.
     */
    private void showLobby() {
        lobbyWindow = new Stage();
        Image logo = new Image("/images/Small_Logo.png");
        lobbyWindow.getIcons().add(logo);
        lobbyWindow.setTitle("Lobby");
        lobbyWindow.setResizable(false);

        // labels, buttons, textField, listViews are created
        ListView lobbyChat = new ListView(lobbyChatList);
        ListView lobbyPlayers = new ListView(lobbyPlayerList);
        Label lobbyLabel = new Label();
        TextField input = new TextField();
        CustomButtons leave = new CustomButtons("QUIT",'R',1,14);

        CustomButtons send = new CustomButtons("SEND",'W',0,10);
        CustomButtons startGame = new CustomButtons("START GAME",'G',1,14);
        Pane root = new Pane();

        lobbyLabel.textProperty().bind(myLobbyName);
        lobbyLabel.relocate(100,10);
        lobbyLabel.setId("lobbyLabel");

        lobbyChat.relocate(100,145);
        lobbyChat.setPrefSize(235,120);

        // Text input
        input.setPromptText("Enter messages here");
        input.relocate(100,275);
        input.setPrefSize(175,30);
        input.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
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
            }
        });

        // Leave button
        leave.setPrefSize(95,50);
        leave.relocate(100,315);
        leave.setOnAction(e -> {
            Client.clientThread.sendCmdToServer(Protocol.QUITLOBBY,"");
            resetLobbyChat();
            lobbyWindow.close();
            inLobby = false;

        });

        // send button
        send.setPrefSize(50,30);
        send.relocate(285,275);
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

        // start game button
        startGame.setPrefSize(130,50);
        startGame.relocate(205,315);
        startGame.setOnAction(e -> {
            Client.clientThread.sendCmdToServer(Protocol.NEWGAME,"");
        });

        // lobbyPlayer list
        lobbyPlayers.setPrefSize(235,95);
        lobbyPlayers.relocate(100,40);
        lobbyPlayers.setOnMouseClicked(e -> {
            String selectedPlayer = (String) lobbyPlayers.getSelectionModel().getSelectedItem();
            if (e.getClickCount() == 2 && selectedPlayer != null) {
                if (!selectedPlayer.equals(myUsername.get())) {
                    input.setText("@"+selectedPlayer+" ");
                }
            }
        });

        // This adds the objects above to the layout and makes them visible
        root.getChildren().add(lobbyLabel);
        root.getChildren().add(lobbyPlayers);
        root.getChildren().add(lobbyChat);
        root.getChildren().add(input);
        root.getChildren().add(send);
        root.getChildren().add(leave);
        root.getChildren().add(startGame);

        root.setStyle("-fx-background-color: blue, lightblue;");

        lobbyWindow.setOnCloseRequest(e -> {
            inLobby = true;
            openLobbyWindow = false;
        });

        Scene scene = new Scene(root,435,390);

        lobbyWindow.setScene(scene);
        lobbyWindow.show();
    }

    /**
     * This function displays a new scene containing three lists. A list with all the past games, a list with all the open games
     * and a list with all the running games.
     * @param stage The stage
     */
    private void setGameList(Stage stage) {
        stage.setTitle("Games");
        
        GridPane layout = new GridPane();
        ListView<String> listView1 = new ListView<>(pastGamesList);
        ListView<String> listView2 = new ListView<>(openGamesList);
        ListView<String> listView3 = new ListView<>(runningGamesList);
        Label title1 = new Label();
        Label title2 = new Label();
        Label title3 = new Label();
        CustomButtons exit = new CustomButtons("BACK",'W',0,12);
        CustomButtons refreshPage = new CustomButtons("REFRESH",'W',1,12);

        title1.setText("Past Games");
        title2.setText("Open Games");
        title3.setText("Running Games");

        // Exit button
        exit.setPrefSize(50,30);
        exit.setOnAction(e -> mainMenu(stage));

        // refresh button
        refreshPage.setPrefSize(75,30);
        refreshPage.setOnAction(e -> setGameList(stage));

        Client.clientThread.sendCmdToServer(Protocol.OPENGAMES,"");
        Client.clientThread.sendCmdToServer(Protocol.SHOWGAMES,"");
        Client.clientThread.sendCmdToServer(Protocol.PASTGAMES,"");

        layout.add(title1, 0, 0);
        layout.add(title2,1,0);
        layout.add(title3,2,0);
        layout.add(listView1, 0, 1);
        layout.add(listView2,1,1);
        layout.add(listView3,2,1);
        layout.add(exit, 0, 2);
        layout.add(refreshPage,1,2);
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: blue, lightblue;");

        Scene scene = new Scene(layout,970,600);
        stage.setScene(scene);
    }

    /**
     * This method displays a pop-up stage where the clients are able to change their username.
     */
    private void changeUsername() {
        Stage changeUsernameWindow = new Stage();
        Image logo = new Image("/images/Small_Logo.png");
        changeUsernameWindow.getIcons().add(logo);
        changeUsernameWindow.initModality(Modality.APPLICATION_MODAL);
        changeUsernameWindow.setTitle("Change Username");

        changeUsernameWindow.setOnCloseRequest(e -> changeUsernameWindow.close());

        Label text = new Label();
        CustomButtons submit = new CustomButtons("SUBMIT",'W',1,12);
        CustomButtons cancel = new CustomButtons("CANCEL",'W',1,12);
        TextField usernameInput = new TextField();
        GridPane buttons = new GridPane();
        GridPane layout = new GridPane();

        text.setText("Please enter a username:");

        // TextField
        usernameInput.setPromptText("Enter a Username");
        usernameInput.setText(getSystemUsername());
        usernameInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {

                if (!validUsername(usernameInput) && !usernameInput.getText().equals("")) {
                    AlertBox.display("Username error", "This is not a valid username.");
                } else {
                    String username1 = usernameInput.getText();
                    try {
                        Client.clientThread.sendCmdToServer(Protocol.SETNAME, username1);
                        Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");

                    } catch (Exception e1) {
                        AlertBox.display("Server offline", "Couldn't connect to the server" );
                    }
                    changeUsernameWindow.close();

                }
            }
        });

        // Submit button
        submit.setPrefSize(75,30);
        submit.setOnAction(e -> {
            String username = usernameInput.getText();
            if (!validUsername(usernameInput) && !usernameInput.getText().equals("")) {
                AlertBox.display("Username error", "This is not a valid username.");
            } else {
                String username1 = usernameInput.getText();
                try {
                    Client.clientThread.sendCmdToServer(Protocol.SETNAME, username1);
                    Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");
                } catch (Exception e1) {
                    AlertBox.display("Server offline", "Couldn't connect to the server" );
                }
                changeUsernameWindow.close();
            }
        });

        // Cancel button
        cancel.setPrefSize(75,30);
        cancel.setOnAction(e -> changeUsernameWindow.close());

        buttons.add(cancel, 0, 0);
        buttons.add(submit, 1, 0);
        buttons.setAlignment(Pos.CENTER);
        buttons.setHgap(10);

        layout.setPadding(new Insets(15, 15, 15, 5));
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setAlignment(Pos.CENTER);
        layout.add(text, 1, 0);
        layout.add(usernameInput, 1, 1);
        layout.add(buttons, 1, 2);
        layout.setStyle("-fx-background-color: blue, lightblue;");

        changeUsernameWindow.setHeight(200);
        changeUsernameWindow.setWidth(300);
        changeUsernameWindow.setResizable(false);
        changeUsernameWindow.setOnCloseRequest(e -> changeUsernameWindow.close());

        Scene scene = new Scene(layout);
        changeUsernameWindow.setScene(scene);
        changeUsernameWindow.showAndWait();
    }

    /**
     * This function displays a new scene containing the highscores.
     * @param stage The stage
     */
    private void showHighscore(Stage stage) {
        stage.setTitle("Highscores");

        GridPane layout = new GridPane();
        ListView listView = new ListView<>(highScoreList);
        Label title = new Label();
        CustomButtons exit = new CustomButtons("Back",'W',0,12);

        title.setText("Highscores");

        // Exit button
        exit.setPrefSize(50,30);
        exit.setOnAction(e -> {
            mainMenu(stage);
        });

        layout.add(title, 0, 0);
        layout.add(listView, 0, 1);
        layout.add(exit, 0, 2);
        layout.setVgap(10);

        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: blue, lightblue;");

        Scene scene = new Scene(layout,970,600);
        stage.setScene(scene);
    }

    /**
     * This shows the first scene of the tutorial.
     * @param stage The primaryStage
     */
    private void showTutorialFirstScene(Stage stage) {
        stage.setTitle("Tutorial");
        Pane layout = new Pane();
        Label title = new Label();

        Image king = new Image("/images/tutorial/tutorial_king_speech.png");
        ImageView imageview = new ImageView();
        imageview.setImage(king);
        imageview.setFitWidth(800); //750
        imageview.setPreserveRatio(true);
        imageview.setVisible(true);
        imageview.relocate(70,70);
        new FadeIn(imageview).play();

        title.setText("TUTORIAL");
        title.setFont(Font.font("Verdana",FontWeight.BOLD,40));
        title.relocate(368,30);

        CustomButtons back = new CustomButtons("MAIN MENU",'B',0,size);
        back.relocate(290,500);
        back.setPrefSize(190,50);
        back.setOnAction(e -> {
            mainMenu(stage);
            Audio.stopKing();
        });

        CustomButtons next = new CustomButtons("NEXT",'G',0,size);
        next.relocate(490,500);
        next.setPrefSize(190,50);
        next.setOnAction(e -> showTutorialSecondScene(stage));

        layout.getChildren().add(title);
        layout.getChildren().add(imageview);
        layout.getChildren().add(back);
        layout.getChildren().add(next);

        layout.setStyle("-fx-background-color: blue, lightblue;");
        Scene scene = new Scene(layout,970,600);
        stage.setScene(scene);
    }

    /**
     * This shows the second scene of the tutorial.
     * @param stage The primaryStage
     */
    private void showTutorialSecondScene(Stage stage) {
        stage.setTitle("Tutorial");
        Pane layout = new Pane();

        CustomButtons back = new CustomButtons("BACK",'B',0,size);
        back.relocate(290,500);
        back.setPrefSize(190,50);
        back.setOnAction(e -> showTutorialFirstScene(stage));

        CustomButtons next = new CustomButtons("NEXT",'G',0,size);
        next.relocate(490,500);
        next.setPrefSize(190,50);
        next.setOnAction(e -> showTutorialThirdScene(stage));

        Image king = new Image("/images/tutorial/tutorial_scene2.png");
        ImageView imageview = new ImageView();
        imageview.setImage(king);
        imageview.setFitWidth(1100); //750
        imageview.setPreserveRatio(true);
        imageview.setVisible(true);
        imageview.relocate(-70,-10);

        layout.getChildren().add(imageview);
        layout.getChildren().add(back);
        layout.getChildren().add(next);

        layout.setStyle("-fx-background-color: blue, lightblue;");
        Scene scene = new Scene(layout,970,600);
        stage.setScene(scene);
    }

    /**
     * This shows the third scene of the tutorial.
     * @param stage The primaryStage
     */
    private void showTutorialThirdScene(Stage stage) {
        stage.setTitle("Tutorial");
        Pane layout = new Pane();

        CustomButtons back = new CustomButtons("BACK",'B',0,size);
        back.relocate(290,500);
        back.setPrefSize(190,50);
        back.setOnAction(e -> showTutorialSecondScene(stage));

        CustomButtons next = new CustomButtons("NEXT",'G',0,size);
        next.relocate(490,500);
        next.setPrefSize(190,50);
        next.setOnAction(e -> showTutorialForthScene(stage));

        Image king = new Image("/images/tutorial/tutorial_scene3.png");
        ImageView imageview = new ImageView();
        imageview.setImage(king);
        imageview.setFitWidth(1000); //750
        imageview.setPreserveRatio(true);
        imageview.setVisible(true);
        imageview.relocate(-12,-10);

        layout.getChildren().add(imageview);
        layout.getChildren().add(back);
        layout.getChildren().add(next);

        layout.setStyle("-fx-background-color: blue, lightblue;");
        Scene scene = new Scene(layout,970,600);
        stage.setScene(scene);
    }

    /**
     * This shows the forth scene of the tutorial.
     * @param stage The primaryStage
     */
    private void showTutorialForthScene(Stage stage) {
        stage.setTitle("Tutorial");
        Pane layout = new Pane();
        Label title = new Label();
        Label text = new Label();

        title.setText("PAWN");
        title.setFont(Font.font("Verdana",FontWeight.BOLD,40));
        title.relocate(310,30);

        text.setText("Pawns are very simple in their movement. \n\nEach player begins a game with 8 pawns, \nstanding in front of their other chess pieces. \n\n" +
                "- Pawns can only move forward one square.\n" +
                "- Pawns can move forward two squares on their first move only. \n" +
                "- Pawns can move diagonally forward when capturing an \n  opponent's chess piece. \n" +
                "- A pawn automatically promotes to a queen, once it \n  moves 6 squares forward (passes over half the board). \n");
        text.setFont(Font.font("Verdana",20));
        text.relocate(310,100);

        CustomButtons back = new CustomButtons("BACK",'B',0,size);
        back.relocate(290,500);
        back.setPrefSize(190,50);
        back.setOnAction(e -> showTutorialThirdScene(stage));

        CustomButtons next = new CustomButtons("NEXT",'G',0,size);
        next.relocate(490,500);
        next.setPrefSize(190,50);
        next.setOnAction(e -> showTutorialFifthScene(stage));

        Image pawn1 = new Image("/images/tutorial/pawn_tutorial1.png");
        ImageView imageview1 = new ImageView();
        imageview1.setImage(pawn1);
        imageview1.setFitWidth(450); //750
        imageview1.setPreserveRatio(true);
        imageview1.setVisible(true);
        imageview1.relocate(-54,20);

        Image pawn2 = new Image("/images/tutorial/pawn_tutorial2.png");
        ImageView imageview2 = new ImageView();
        imageview2.setImage(pawn2);
        imageview2.setFitWidth(450); //750
        imageview2.setPreserveRatio(true);
        imageview2.setVisible(true);
        imageview2.relocate(-54,240);

        layout.getChildren().add(title);
        layout.getChildren().add(text);
        layout.getChildren().add(back);
        layout.getChildren().add(next);
        layout.getChildren().add(imageview1);
        layout.getChildren().add(imageview2);

        layout.setStyle("-fx-background-color: blue, lightblue;");
        Scene scene = new Scene(layout,970,600);
        stage.setScene(scene);
    }

    /**
     * This shows the fifth scene of the tutorial.
     * @param stage The primaryStage
     */
    private void showTutorialFifthScene(Stage stage) {
        stage.setTitle("Tutorial");
        Pane layout = new Pane();
        Label title = new Label();
        Label text = new Label();

        title.setText("KNIGHT");
        title.setFont(Font.font("Verdana",FontWeight.BOLD,40));
        title.relocate(310,30);

        text.setText("Knights move very differently from the other pieces. \n\nEach player begins a game with 2 knights. They are also \nthe only piece capable of jumping over other pieces. \n\n" +
                "- Knights can move two squares in any direction vertically \n  followed by one square horizontally.\n" +
                "- Knights can move two squares in any direction horizontally \n  followed by one square vertically. \n" +
                "- Knights can only move to 1 of up to 8 positions on the board. \n" +
                "- Knights can move to any position that is not already \n  occupied by another piece of its player or teammate. \n" +
                "- Knights can skip over any piece to reach their desired position.\n");
        text.setFont(Font.font("Verdana",20));
        text.relocate(310,100);

        CustomButtons back = new CustomButtons("BACK",'B',0,size);
        back.relocate(290,500);
        back.setPrefSize(190,50);
        back.setOnAction(e -> showTutorialForthScene(stage));

        CustomButtons next = new CustomButtons("NEXT",'G',0,size);
        next.relocate(490,500);
        next.setPrefSize(190,50);
        next.setOnAction(e -> showTutorialSixthScene(stage));

        Image pawn1 = new Image("/images/tutorial/knight_tutorial1.png");
        ImageView imageview1 = new ImageView();
        imageview1.setImage(pawn1);
        imageview1.setFitWidth(450); //750
        imageview1.setPreserveRatio(true);
        imageview1.setVisible(true);
        imageview1.relocate(-42,20);

        layout.getChildren().add(title);
        layout.getChildren().add(text);
        layout.getChildren().add(back);
        layout.getChildren().add(next);
        layout.getChildren().add(imageview1);

        layout.setStyle("-fx-background-color: blue, lightblue;");
        Scene scene = new Scene(layout,970,600);
        stage.setScene(scene);
    }

    /**
     * This shows the sixth scene of the tutorial.
     * @param stage The primaryStage
     */
    private void showTutorialSixthScene(Stage stage) {
        stage.setTitle("Tutorial");
        Pane layout = new Pane();
        Label title = new Label();
        Label text = new Label();

        title.setText("ROOK");
        title.setFont(Font.font("Verdana",FontWeight.BOLD,40));
        title.relocate(310,30);

        text.setText("Rooks are the most simple chess pieces to move. \n\nEach player begins a game with 2 rooks.\n\n" +
                "- Rooks can move forward, backward, left, \n  or right at any given time. \n" +
                "- Rooks can move in any direction, as long as it is not being \n  hindered by any other pieces. \n");
        text.setFont(Font.font("Verdana",20));
        text.relocate(310,100);

        CustomButtons back = new CustomButtons("BACK",'B',0,size);
        back.relocate(290,500);
        back.setPrefSize(190,50);
        back.setOnAction(e -> showTutorialFifthScene(stage));

        CustomButtons next = new CustomButtons("NEXT",'G',0,size);
        next.relocate(490,500);
        next.setPrefSize(190,50);
        next.setOnAction(e -> showTutorialSeventhScene(stage));

        Image pawn1 = new Image("/images/tutorial/rook_tutorial1.png");
        ImageView imageview1 = new ImageView();
        imageview1.setImage(pawn1);
        imageview1.setFitWidth(450); //750
        imageview1.setPreserveRatio(true);
        imageview1.setVisible(true);
        imageview1.relocate(-42,20);

        layout.getChildren().add(title);
        layout.getChildren().add(text);
        layout.getChildren().add(back);
        layout.getChildren().add(next);
        layout.getChildren().add(imageview1);

        layout.setStyle("-fx-background-color: blue, lightblue;");
        Scene scene = new Scene(layout,970,600);
        stage.setScene(scene);
    }

    /**
     * This shows the seventh scene of the tutorial.
     * @param stage The primaryStage
     */
    private void showTutorialSeventhScene(Stage stage) {
        stage.setTitle("Tutorial");
        Pane layout = new Pane();
        Label title = new Label();
        Label text = new Label();

        title.setText("BISHOP");
        title.setFont(Font.font("Verdana",FontWeight.BOLD,40));
        title.relocate(310,30);

        text.setText("Bishops can move diagonally. \n\nEach player starts out with two bishops and \ncan cover the whole board with them.\n\n" +
                "- Bishops can move in any direction diagonally, as long \n  as there is no other piece in their way.  \n" +
                "- Bishops cannot move past any piece that is blocking their way.\n" +
                "- Bishops can capture any opponent piece on the board \n  that is within their range of movement.  \n");
        text.setFont(Font.font("Verdana",20));
        text.relocate(310,100);

        CustomButtons back = new CustomButtons("BACK",'B',0,size);
        back.relocate(290,500);
        back.setPrefSize(190,50);
        back.setOnAction(e -> showTutorialSixthScene(stage));

        CustomButtons next = new CustomButtons("NEXT",'G',0,size);
        next.relocate(490,500);
        next.setPrefSize(190,50);
        next.setOnAction(e -> showTutorialEightScene(stage));

        Image pawn1 = new Image("/images/tutorial/bishop_tutorial1.png");
        ImageView imageview1 = new ImageView();
        imageview1.setImage(pawn1);
        imageview1.setFitWidth(450); //750
        imageview1.setPreserveRatio(true);
        imageview1.setVisible(true);
        imageview1.relocate(-42,20);

        layout.getChildren().add(title);
        layout.getChildren().add(text);
        layout.getChildren().add(back);
        layout.getChildren().add(next);
        layout.getChildren().add(imageview1);

        layout.setStyle("-fx-background-color: blue, lightblue;");
        Scene scene = new Scene(layout,970,600);
        stage.setScene(scene);
    }

    /**
     * This shows the eight scene of the tutorial.
     * @param stage The primaryStage
     */
    private void showTutorialEightScene(Stage stage) {
        stage.setTitle("Tutorial");
        Pane layout = new Pane();
        Label title = new Label();
        Label text = new Label();

        title.setText("QUEEN");
        title.setFont(Font.font("Verdana",FontWeight.BOLD,40));
        title.relocate(310,30);

        text.setText("The Queen is not only considered the most versatile \nchess piece on the board, but also the most dangerous. \n\n" +
                "The queen is like a combination of the Rook and Bishop. \n\nEach player starts out with one queen piece but \nany pawn can promote and turn into a queen too.\n\n" +
                "- The queen can move in any direction on a straight \n  or diagonal path.\n" +
                "- The queen can't move past any piece that is blocking her way.\n" +
                "- The queen's movements are restricted to any direction \n  of unoccupied squares.\n");
        text.setFont(Font.font("Verdana",20));
        text.relocate(310,100);

        CustomButtons back = new CustomButtons("BACK",'B',0,size);
        back.relocate(290,500);
        back.setPrefSize(190,50);
        back.setOnAction(e -> showTutorialSeventhScene(stage));

        CustomButtons next = new CustomButtons("NEXT",'G',0,size);
        next.relocate(490,500);
        next.setPrefSize(190,50);
        next.setOnAction(e -> showTutorialNinthScene(stage));

        Image pawn1 = new Image("/images/tutorial/queen_tutorial1.png");
        ImageView imageview1 = new ImageView();
        imageview1.setImage(pawn1);
        imageview1.setFitWidth(450); //750
        imageview1.setPreserveRatio(true);
        imageview1.setVisible(true);
        imageview1.relocate(-42,20);

        layout.getChildren().add(title);
        layout.getChildren().add(text);
        layout.getChildren().add(back);
        layout.getChildren().add(next);
        layout.getChildren().add(imageview1);

        layout.setStyle("-fx-background-color: blue, lightblue;");
        Scene scene = new Scene(layout,970,600);
        stage.setScene(scene);
    }

    /**
     * This shows the ninth scene of the tutorial.
     * @param stage The primaryStage
     */
    private void showTutorialNinthScene(Stage stage) {
        stage.setTitle("Tutorial");
        Pane layout = new Pane();
        Label title = new Label();
        Label text = new Label();

        title.setText("KING");
        title.setFont(Font.font("Verdana",FontWeight.BOLD,40));
        title.relocate(310,30);

        text.setText("The king is in comparison with the other pieces somewhat \nlimited in his movement.\n\n" +
                "Each player starts out with one king piece.\n\n" +
                "- The king can move one single square in any direction.\n" +
                "- The king cannot move on to a square that is occupied \n  by a piece of the same color/team.\n" +
                "- The king cannot move to any square that puts him into a \n  “check” position.\n" +
                "- Once the king is captured the game is over.\n");
        text.setFont(Font.font("Verdana",20));
        text.relocate(310,100);

        CustomButtons back = new CustomButtons("BACK",'B',0,size);
        back.relocate(290,500);
        back.setPrefSize(190,50);
        back.setOnAction(e -> showTutorialEightScene(stage));

        CustomButtons next = new CustomButtons("MAIN MENU",'G',0,size);
        next.relocate(490,500);
        next.setPrefSize(190,50);
        next.setOnAction(e -> {
            mainMenu(stage);
            Audio.stopKing();
        });

        Image pawn1 = new Image("/images/tutorial/king_tutorial1.png");
        ImageView imageview1 = new ImageView();
        imageview1.setImage(pawn1);
        imageview1.setFitWidth(450); //750
        imageview1.setPreserveRatio(true);
        imageview1.setVisible(true);
        imageview1.relocate(-42,20);

        layout.getChildren().add(title);
        layout.getChildren().add(text);
        layout.getChildren().add(back);
        layout.getChildren().add(next);
        layout.getChildren().add(imageview1);

        layout.setStyle("-fx-background-color: blue, lightblue;");
        Scene scene = new Scene(layout,970,600);
        stage.setScene(scene);
    }

    /**
     * This creates a new scene with the credits.
     * @param stage The stage.
     */
    private void showCredits(Stage stage) {
        stage.setTitle("Credits");
        Pane layout = new Pane();
        Label title = new Label();
        Label developers = new Label();
        Label sound = new Label();
        Label thanks = new Label();
        Label graphics = new Label();
        Label dv1 = new Label();
        Label dv2 = new Label();
        Label gr1 = new Label();
        Label gr2 = new Label();
        Label gr3 = new Label();
        Label sn1 = new Label();
        Label sn2 = new Label();
        Label sn3 = new Label();
        Label sn4 = new Label();
        Label ts1 = new Label();
        Label ts2 = new Label();
        CustomButtons back;

        title.setText("CREDITS");
        title.setFont(Font.font("Verdana",FontWeight.BOLD,40));
        title.relocate(355,30);

        developers.setText("DEVELOPERS");
        developers.setFont(Font.font("Verdana", 24));
        developers.relocate(360,100);

        dv1.setText("Mark Starzynski");
        dv1.setFont(Font.font("Verdana", 15));
        dv1.relocate(360,140);
        dv2.setText("André Vieira Ribeiro");
        dv2.setFont(Font.font("Verdana", 15));
        dv2.relocate(360,170);

        graphics.setText("GRAPHICS");
        graphics.setFont(Font.font("Verdana", 24));
        graphics.relocate(360,200);

        gr1.setText("Logo and UI graphics: André Vieira Ribeiro");
        gr1.setFont(Font.font("Verdana", 15));
        gr1.relocate(360,240);
        gr2.setText("Chess Pieces: Derivative work originally created by Cburnett (CC BY-SA 3.0.)");
        gr2.setFont(Font.font("Verdana", 15));
        gr2.relocate(360,270);
        gr3.setText("Buttons: Kenney Vleugels (Creative Commons Zero, CC0)");
        gr3.setFont(Font.font("Verdana", 15));
        gr3.relocate(360,300);
        
        sound.setText("SOUND");
        sound.setFont(Font.font("Verdana", 24));
        sound.relocate(360,330);

        sn1.setText("UI Sounds: Kenney Vleugels (Creative Commons Zero, CC0)");
        sn1.setFont(Font.font("Verdana", 15));
        sn1.relocate(360,370);
        sn2.setText("Voice Actor: Calgaryvotalent (Fiverr.com)");
        sn2.setFont(Font.font("Verdana", 15));
        sn2.relocate(360,400);
        sn3.setText("Intro Music: Silly Intro by Alexander Nakarada (CC BY 4.0)");
        sn3.setFont(Font.font("Verdana", 15));
        sn3.relocate(360,430);
        sn4.setText("Game Music: Traveling your mind by Loyalty Freak Musik (CC BY 4.0)");
        sn4.setFont(Font.font("Verdana", 15));
        sn4.relocate(360,460);

        thanks.setText("SPECIAL THANKS");
        thanks.setFont(Font.font("Verdana", 24));
        thanks.relocate(360,490);

        ts1.setText("Nikodem Kernbach");
        ts1.setFont(Font.font("Verdana", 15));
        ts1.relocate(360,530);
        ts2.setText("University of Basel");
        ts2.setFont(Font.font("Verdana", 15));
        ts2.relocate(360,560);

        Image image1 = new Image("/images/intro_2.png");
        ImageView imageview1 = new ImageView();
        imageview1.setImage(image1);
        imageview1.setFitWidth(900); //750
        imageview1.setPreserveRatio(true);
        imageview1.setVisible(true);
        imageview1.relocate(-80,-30);

        Image image2 = new Image("/images/intro_3.png");
        ImageView imageview2 = new ImageView();
        imageview2.setImage(image2);
        imageview2.setFitWidth(900); //750
        imageview2.setPreserveRatio(true);
        imageview2.setVisible(true);
        imageview2.relocate(-425,15);
        
        back = new CustomButtons("MAIN MENU",'G',0,size);
        back.relocate(75,500);
        back.setPrefSize(190,50);
        back.setOnAction(e -> mainMenu(stage));

        layout.getChildren().add(title);
        layout.getChildren().add(developers);
        layout.getChildren().add(dv1);
        layout.getChildren().add(dv2);
        layout.getChildren().add(graphics);
        layout.getChildren().add(gr1);
        layout.getChildren().add(gr2);
        layout.getChildren().add(gr3);
        layout.getChildren().add(sound);
        layout.getChildren().add(sn1);
        layout.getChildren().add(sn2);
        layout.getChildren().add(sn3);
        layout.getChildren().add(sn4);
        layout.getChildren().add(thanks);
        layout.getChildren().add(ts1);
        layout.getChildren().add(ts2);
        layout.getChildren().add(back);
        layout.getChildren().add(imageview1);
        layout.getChildren().add(imageview2);
        
        layout.setStyle("-fx-background-color: blue, lightblue;");

        Scene scene = new Scene(layout,970,600);
        stage.setScene(scene);
    }

    /**
     * This function adds a String (message) to the global chat.
     * @param data The message.
     */
    public static void addToGlobalChat(String data) {
        Platform.runLater(() -> globalChatList.add(data));
    }

    /**
     * This function adds a String (message) to the lobby chat.
     * @param data The message.
     */
    public static void addToLobbyChat(String data) {
        Platform.runLater(() -> lobbyChatList.add(data));
    }

    /**
     * This function resets/clears the lobby list.
     */
    public static void resetLobbyList() {
        Platform.runLater(() -> lobbyList.clear());
    }

    /**
     * This function removes a specific player from the lobby list.
     * @param data The name of the player.
     */
    public static void removeFromLobbyList(String data) {
        for (int i = 0; i < lobbyList.size(); i++) {
            if (lobbyList.get(i).equals(data)) {
                lobbyList.remove(i);
                break;
            }
        }
    }

    /**
     * This function adds a player/client to the lobby list.
     * @param data The name of the player.
     */
    public static void addToLobbyList(String data) {
        Platform.runLater(() -> lobbyList.add(data));
    }

    /**
     * This function resets/clears the lobby chat.
     */
    public static void resetLobbyChat() {
        Platform.runLater(() -> lobbyChatList.clear());
    }

    /**
     * This function resets/clears the player list.
     */
    public static void resetPlayerList() {
        Platform.runLater(() -> playerList.clear());
    }

    /**
     * This function adds players to the player list.
     * @param item The player.
     */
    public static void addToPlayerList(String item) {
        Platform.runLater(() -> playerList.add(item));
    }

    /**
     * This function resets/clears the highscore list.
     */
    public static void resetHighscoreList() {
        Platform.runLater(() -> highScoreList.clear());
    }

    /**
     * This function adds new scores to the highscore list.
     * @param item The achieved score.
     */
    public static void addToHighscoreList(String item) {
        Platform.runLater(() -> highScoreList.add(item));
    }

    /**
     * This function adds open games to the open games list
     * @param data The name of the lobby that is open.
     */
    public static void addToOpenGamesList(String data) {
        Platform.runLater(() -> openGamesList.add(data));
    }

    /**
     * This function resets/clears the open games list.
     */
    public static void resetOpenGamesList() {
        Platform.runLater(() -> openGamesList.clear());
    }

    /**
     * This function adds past games/already finished games to he past games list.
     * @param data The name of the lobby.
     */
    public static void addToPastGamesList(String data) {
        Platform.runLater(() -> pastGamesList.add(data));
    }

    /**
     * This function resets/clears the past games list.
     */
    public static void resetPastGamesList() { Platform.runLater(() -> pastGamesList.clear()); }

    /**
     * This function adds running games to he running games list.
     * @param data The data of the game.
     */
    public static void addToRunningGamesList(String data) {
        Platform.runLater(() -> runningGamesList.add(data));
    }

    /**
     * This function resets/clears the running games list.
     */
    public static void resetRunningGamesList() {
        Platform.runLater(() -> runningGamesList.clear());
    }

    /**
     * This function adds players to the lobby player list.
     * @param data The name of the player.
     */
    public static void addToLobbyPlayerList(String data) {
        Platform.runLater(() -> lobbyPlayerList.add(data));
    }

    /**
     * This function resets/clears the lobby player list.
     */
    public static void resetLobbyPlayerList() {
        Platform.runLater(() -> lobbyPlayerList.clear());
    }

    /**
     * this function closes the lobby window.
     */
    public static void closeLobbyWindow() {
        lobbyWindow.close();
    }

    /**
     * This method returns the system username.
     * @return Returns the username.
     */
    private String getSystemUsername() {
        return System.getProperty("user.name");
    }

    /**
     * This function is used to check if the players username is valid.
     * @param username The username in the text field.
     * @return True or false.
     */
    private boolean validUsername(TextField username) {
        return username.getText().matches("^(?=.{3,7}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
    }

    /**
     * This function is used to create an alert box.
     * @param error The error name.
     * @param message The actual message.
     */
    public static void showErrorMessages(String error, String message) {
        AlertBox.display(error,message);
    }

    /**
     * This function checks if a String is a command message
     * @param msg The message that needs to be checked
     * @return True or false.
     */
    public static boolean isNotCommand(String msg) {
        if (msg.startsWith("SETNAME") || msg.startsWith("setname")) {
            Client.clientThread.sendCmdToServer(Protocol.SETNAME, msg.substring(7));
            Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");
        } else if (msg.startsWith("LOGOUT") || msg.startsWith("logout")) {
            Client.clientThread.sendCmdToServer(Protocol.LOGOUT, msg.substring(6));
            Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");
        } else if (msg.toUpperCase().startsWith("SHOWNAME")) {
            Client.clientThread.sendCmdToServer(Protocol.SHOWNAME, msg.substring(8));
        } else if (msg.startsWith("SHOWPLAYERS") || msg.startsWith("showplayers")) {
            Client.clientThread.sendCmdToServer(Protocol.SHOWPLAYERS, msg.substring(11));
        } else if (msg.startsWith("@LOBBY") || msg.startsWith("@lobby")) {
            Client.clientThread.sendCmdToServer(Protocol.LOBBYCHAT, msg.substring(6));
        } else if (msg.toUpperCase().startsWith("WHISPER")) {
            Client.clientThread.sendCmdToServer(Protocol.WHISPERCHAT, msg.substring(7));
        } else if (msg.startsWith("@")) {
            Client.clientThread.sendCmdToServer(Protocol.WHISPERCHAT, msg.substring(1));
        } else if (msg.startsWith("COMMANDS") || msg.startsWith("commands")) {
            System.out.println("Commands for chat:");
            System.out.println("======================================================");
            System.out.println("SETNAME allows you to change your name");
            System.out.println("SHOWNAME displays your current name");
            System.out.println("WHISPER <playername> <message> sends a private message");
            System.out.println("@<playername> <message> works as well for whisper chat");
            System.out.println("@lobby <message> to write in lobby chat");
            System.out.println();
            System.out.println("SHOWNAME displays your current name");
            System.out.println("SHOWPLAYERS for all players online currently");
            System.out.println("SHOWLOBBY to display your current lobby");
            System.out.println("SHOWLOBBIES to display all lobbies and its players");
            System.out.println("SHOWPLAYERS displays all currently connected clients");
            System.out.println("RTT to check your round-trip time");
            System.out.println();
            System.out.println("SHOWGAMES to display currently running games");
            System.out.println("PASTGAMES to display finished games");
            System.out.println("OPENGAMES to display open lobbies, ready to join");
            System.out.println();
            System.out.println("MOVE <startSquare/endSquare> to make your move in game");
            System.out.println();
            System.out.println("NEWLOBBY to create a new lobby");
            System.out.println("JOINLOBBY <lobbyID> to join a specific lobby");
            System.out.println("QUITLOBBY to leave current lobby");
            System.out.println();
            System.out.println("LOGOUT or QUIT to end the chat");
            System.out.println("======================================================");
        } else if (msg.startsWith("RTT") || msg.startsWith("rtt")) {
            System.out.println("Your last round-trip time was " + Client.clientThread.getRTT() + "ms");
        } else if (msg.startsWith("NEWLOBBY") || msg.startsWith("newlobby")) {
            Client.clientThread.sendCmdToServer(Protocol.CREATELOBBY, msg.substring(8));
        } else if (msg.startsWith("SHOWLOBBIES") || msg.startsWith("showlobbies")) {
            Client.clientThread.sendCmdToServer(Protocol.SHOWLOBBIES, msg.substring(11));
        } else if (msg.startsWith("SHOWLOBBY") || msg.startsWith("showlobby")) {
            Client.clientThread.sendCmdToServer(Protocol.SHOWLOBBY, msg.substring(9));
        } else if (msg.startsWith("JOINLOBBY") || msg.startsWith("joinlobby")) {
            Client.clientThread.sendCmdToServer(Protocol.JOINLOBBY, msg.substring(9));
        } else if (msg.startsWith("QUITLOBBY") || msg.startsWith("quitlobby")) {
            Client.clientThread.sendCmdToServer(Protocol.QUITLOBBY, msg.substring(9));
        } else if (msg.startsWith("LOBBY") || msg.startsWith("lobby")) {
            Client.clientThread.sendCmdToServer(Protocol.LOBBYCHAT, msg.substring(5));
        } else if (msg.startsWith("NEWGAME") || msg.startsWith("newgame")) {
            Client.clientThread.sendCmdToServer(Protocol.NEWGAME, msg.substring(7));
        } else if (msg.startsWith("QUIT") || msg.startsWith("quit")
                || msg.startsWith("EXIT") || msg.startsWith("exit")) {
            Client.clientThread.sendCmdToServer(Protocol.LOGOUT, msg.substring(4));
            Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");
        } else if (msg.startsWith("MOVE") || msg.startsWith("move")) {
            Client.clientThread.sendCmdToServer(Protocol.MOVE, msg.substring(4));
        } else if (msg.startsWith("SHOWGAMES") || msg.startsWith("showgames")) {
            Client.clientThread.sendCmdToServer(Protocol.SHOWGAMES, msg.substring(9));
        } else if (msg.startsWith("PASTGAMES") || msg.startsWith("pastgames")) {
            Client.clientThread.sendCmdToServer(Protocol.PASTGAMES, msg.substring(9));
        } else if (msg.startsWith("OPENGAMES") || msg.startsWith("opengames")) {
            Client.clientThread.sendCmdToServer(Protocol.OPENGAMES, msg.substring(9));
        } else {
            return true;
        }
        return false;
    }

}



