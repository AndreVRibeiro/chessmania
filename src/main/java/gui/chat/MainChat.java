package gui.chat;

import client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import networking.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;


// for javaFX to work add this to VM option in IntelliJ run dialog:
// --module-path path\to\javafx\lib --add-modules javafx.controls,javafx.fxml

/**
 * This class was used as the first chat GUI for milestone 3. It was replaced by ClientGUI and GameGUI.
 */
public class MainChat extends Application implements EventHandler<ActionEvent> {

    private static final Logger clientLogger = LogManager.getLogger(Client.class);

    Stage window;
    Button chatBtn, loginBtn, quitBtn, updateListBtn;
    public static ListView<String> listView;
    public static TextArea chatBox;
    public static Label username;

    //    public static ObservableList<String> players;
    public static ObservableList<String> players = FXCollections.observableArrayList();
    public static ObservableList<String> chatMsgs = FXCollections.observableArrayList();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;

        Label lblLogin = new Label("Login Screen");
        Label lblChat = new Label("Chat Screen");

        loginBtn = new Button("Login");
        chatBtn = new Button("Chat");
        quitBtn = new Button("Quit");

        HBox topMenu = new HBox();
        Button fileBtn = new Button("File");
        Button editBtn = new Button("Edit");
        Button viewBtn = new Button("View");
        topMenu.getChildren().addAll(fileBtn, editBtn, viewBtn);

        VBox rightMenu = new VBox();
        Button fileBtn2 = new Button("File2");
        Button editBtn2 = new Button("Edit2");
        Button viewBtn2 = new Button("View2");
        rightMenu.getChildren().addAll(fileBtn2, editBtn2, viewBtn2);
//        Button fileBtn = new Button("File");
//        Button editBtn = new Button("Edit");
//        Button viewBtn = new Button("View");
//        rightMenu.getChildren().addAll(loginBtn, quitBtn);

        // Choicebox Lists
        ChoiceBox<String> lists = new ChoiceBox<>();
        lists.getItems().addAll("Lobbylist", "Playerlist", "Open Lobbies");
        lists.setValue("Playerlist");

        lists.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> { // listens for selection changes in choice box
            System.out.println("action: " + newValue);
        } );

        // ListView
        listView = new ListView<>(players);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setPrefWidth(150);
        updateListBtn = new Button("Update List");

        // TextArea
        chatBox = new TextArea();
        chatBox.setPrefHeight(350);

        // username label
        username = new Label("Your username: ");



        VBox layout1 = new VBox(20);
        VBox layout2 = new VBox(20);
        layout1.getChildren().addAll(lblLogin, loginBtn, quitBtn);
        layout2.getChildren().addAll(lblChat, chatBtn, quitBtn);


        BorderPane main = new BorderPane();
        main.setTop(topMenu);
        main.setRight(rightMenu);

//        BorderPane chat = new BorderPane();
//        chat.setTop(topMenu);
//        chat.setLeft(layout1);
//        chat.setRight(layout2);


        // login grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10)); // padding edges of window
        grid.setVgap(8); // padding individual cells
        grid.setHgap(10);

        Label nameLbl = new Label("Username");
        GridPane.setConstraints(nameLbl, 0, 0); // which cell label

        TextField nameInput = new TextField();
        nameInput.setPromptText("Type in your username");
        GridPane.setConstraints(nameInput, 1, 0); // which cell label

        GridPane.setConstraints(loginBtn, 2, 0);
        grid.getChildren().addAll(nameLbl, nameInput, loginBtn);


        Scene gridLogin = new Scene(grid, 330, 100);

        // chat grid
        GridPane chat = new GridPane();
        chat.setPadding(new Insets(10, 10, 10, 10)); // padding edges of window
        chat.setVgap(8); // padding individual cells
        chat.setHgap(10);

        TextField msgInput = new TextField();
        msgInput.setPromptText("Type in your message");
        GridPane.setConstraints(msgInput, 1, 1, 3, 1);

        Button msgBtn = new Button("Send");
        GridPane.setConstraints(msgBtn, 4, 1);

        GridPane.setConstraints(listView, 4, 0);

        GridPane.setConstraints(chatBox, 1, 0, 3, 1);
        GridPane.setConstraints(updateListBtn, 1, 3);
        GridPane.setConstraints(username, 2, 3);

        chat.getChildren().addAll(msgInput, msgBtn, listView, updateListBtn, chatBox, username);

        Scene gridChat = new Scene(chat, 600, 350);



//        Scene sceneLogin = new Scene(main, 300, 250);
//        Scene sceneChat = new Scene(chat, 300, 250);

        // main scene
        window.setTitle("ChessMania Chat");

        if (Client.getUsername() != null) {
            try {
//                TimeUnit.SECONDS.sleep(3);
                Client.clientThread.sendCmdToServer(Protocol.LOGIN, Client.getUsername());
                Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");
                window.setScene(gridChat);
            } catch (Exception e2) {
                AlertBox.display("Server offline", "Couldnt connect to the server" );
            }
        } else { window.setScene(gridLogin); }
        window.show();


        window.setOnCloseRequest(e -> {
            e.consume();
            if (ConfirmBox.display("Quit Chat", "Are you sure you want to quit the chat?")) {
                try {
                    Client.clientThread.sendCmdToServer(Protocol.LOGOUT, "");
                    Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");
                    window.close();
                } catch (Exception e2) {
                    window.close();
                    e2.printStackTrace();
                }
            }
        });



        // BUTTON ACTIONS
        loginBtn.setOnAction(e -> {
            Client.setUsername(System.getProperty("user.name"));

            if (!validUsername(nameInput) && !nameInput.getText().equals("")) {
                AlertBox.display("Username error", "This is not a valid username.");
            } else {

                String username;
                if (!nameInput.getText().equals("")) {
                    username = nameInput.getText();
                    Client.setUsername(username);
                } else {
                    username = Client.getUsername();
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                    Client.clientThread.sendCmdToServer(Protocol.LOGIN, username);
                    Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");
                    window.setScene(gridChat);
                } catch (Exception e2) {
                    AlertBox.display("Server offline", "Couldnt connect to the server" );
                }
//                Client.clientThread.sendCmdToServer(Protocol.LOGIN, username);



            }


//            AlertBox.display("Alert Box", "This is an alert box. You need to " +
//                    "press OK first before going back.");
        });

        chatBtn.setOnAction(e -> {

        });

        quitBtn.setOnAction(e -> {
            e.consume();
            if (ConfirmBox.display("Quit Chat", "Are you sure you want to quit the chat?")) {
                Client.clientThread.sendCmdToServer(Protocol.LOGOUT, "");
                Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");
                quitPopup();
            }
        });

        updateListBtn.setOnAction(e -> Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE"));


        msgBtn.setOnAction(e -> {
            String msg = msgInput.getText();
            if (msg != null) {
                if (isNotCommand(msg)) {
                    Client.clientThread.sendCmdToServer(Protocol.GLOBALCHAT, msg);
                }
            }
            window.setScene(gridChat);
        });



//        loginButton.setOnAction(this); // this handles the method in this class

/*        // compact version of event handling
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == loginBtn) {
                    System.out.println("You pressed the login button");
                }
            }
        });*/





    }



    private void quitPopup() {
        boolean ans = ConfirmBox.display("Quit Game", "Are you sure you want to quit the game?");
        if (ans) window.close();
    }

    // handles what happens when a user presses a button for example
    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == loginBtn) {
            System.out.println("You pressed the login button");
        }
        if(event.getSource() == updateListBtn) {
            System.out.println("You pressed the login button");
        }

    }


    private boolean validUsername(TextField username) {
        return username.getText().matches("^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
    }



    public void getCurrentList(ChoiceBox<String> lists) {
        String currentList = lists.getValue();
    }

    public static void delay(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public static void updatePlayers() /*throws InterruptedException*/ {
        Platform.runLater(() -> {
            players.clear();
            for (int i = 0; i < Client.playerList.size(); i++) {
                players.add(Client.playerList.get(i).getUsername());
            }
        });
    }

    public static void setUsername(String username) {
        Platform.runLater(() -> MainChat.username.setText("Your username: " + username));
    }


//    private void startUpdateThread() {
//        Thread updateThread = new Thread(() -> {
//            boolean updateThreadRunning = true;
//            try {
//                while (updateThreadRunning) {
////                    Client.clientThread.sendCmdToServer(Protocol.UPDATELOBBIES, "");
//                    Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");
//                    delay(2000);
//                    updatePlayers();
//
//                }
//            } catch (Exception e) {
//                updateThreadRunning = false;
//                e.printStackTrace();
//            }
//        });
//        updateThread.start();
//    }


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

//    public void startUpdateThread() {
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Runnable updater = new Runnable() {
//                        @Override
//                        public void run() {
//                            updatePlayers();
//                        }
//                    };
//                    while (true) {
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException ex) {
//                            clientLogger.error("startUpdateThread() coult not "); //LOGGER
//                        }
//                        Platform.runLater(updater);
//                    }
//                }
//
//            });
//            // don't let thread prevent JVM shutdown
//            thread.setDaemon(true);
//            thread.start();
//    }
}