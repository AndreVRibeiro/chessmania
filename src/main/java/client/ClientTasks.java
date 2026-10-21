package client;


import gamelogic.ChessBoard;
import gamelogic.Game;
import gui.Audio;
import gui.ClientGUI;
import gui.GameGUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ClientID;
import networking.Protocol;
import server.Lobby;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * This class sends client protocol commands and handles server protocol commands.
 */
public class ClientTasks {

    private static final Logger clientTasksLogger = LogManager.getLogger(ClientTasks.class);
    protected long pingBackTime;
    protected ChessBoard board;
    protected ClientThread clientThread;
    private boolean gameThreadRunning = true;

    /**
     * This is the constructor of the class ClientTasks.
     */
    ClientTasks() {}

    /**
     * This method handles all the client tasks based on the protocol command passed
     * on as argument.
     * @param cmd The Protocol command which specifies which task should be executed.
     * @param msg The String message needed to execute. Depending on the task it includes
     *            various arguments which need to be decoded.
     */
    public void clientTasks(Protocol cmd, String msg) {

        switch(cmd) {

            case LOGOUT:
                logout();
                break;
            case HIGHSCORES:
                highscores(msg);
                break;
            case CREATELOBBY:
                createLobby(msg);
                break;
            case MOVE:
                move(msg);
                break;
            case TURN:
                turn(msg);
                break;
            case GAMEOVER:
                break;
            case GLOBALCHAT:
                printMessage(msg);
                break;
            case WHISPERCHAT:
                getWhisper(msg);
                break;
            case LOBBYCHAT:
                lobbyChat(msg);
                break;
            case SHOWLOBBIES:
                showLobbies(msg);
                break;
            case UPDATELOBBIES:
                updateLobbies(msg);
                break;
            case SHOWNAME:
                showUsername(msg);
                break;
            case SHOWLOBBY:
                showLobby(msg);
                break;
            case SHOWPLAYERS:
                showPlayers(msg);
                break;
            case UPDATEPLAYERS:
                updatePlayers(msg);
                break;
            case SOUND:
                sound(msg);
                break;
            case QUITLOBBY:
                quitLobby(msg);
                break;
            case MESSAGE:
                printMessage(msg);
                break;
            case PING:
                ping();
                break;
            case NEWGAME:
                newGame(msg);
                break;
            case CHESSBOARD:
                updateBoard(msg);
                break;
            case STATUS:
                updateStatus(msg);
                break;
            case ENDGAME:
                endGame();
                break;
            case DRAW:
                draw();
            case WINNER:
                winner(msg);
            case SHOWGAMES:
                showGames(msg);
                break;
            case PASTGAMES:
                showPastGames(msg);
                break;
            case OPENGAMES:
                openGames(msg);
                break;
            case SETNAME:
                setName(msg);
                break;
            case TIMER:
                timer(msg);
                break;
            case JOINLOBBY:
                joinLobby();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + cmd + " msg: " + msg);
        }
    }

    /**
     * This function is plays an audio file on the clients GameGUI, when playing a game.
     * It is sent from the server to the client.
     * @param msg The name of the audio that should be played
     */
    private void sound(String msg) {
        switch(msg) {
            case ("TIKTOK"):
                Random number = new Random();
                int rndNr = number.nextInt(3) + 1;

                switch(rndNr)
                {
                    case 1:
                        Audio.kingAdd("/UI-Sounds/tiktok.mp3");
                        break;
                    case 2:
                        Audio.kingAdd("/UI-Sounds/makeyourmove.mp3");
                        break;
                    case 3:
                        Audio.kingAdd("/UI-Sounds/hurryup.mp3");
                        break;
                }
            case ("ONEMINUTE"):
                Audio.kingAdd("/UI-Sounds/gameend1min.mp3");
                break;
            case ("TENSECONDS"):
                Audio.kingAdd("/UI-Sounds/tenseconds.mp3");
                break;
            case ("WINNER"):
                Audio.stop();
                Audio.king("/UI-Sounds/winner.mp3");
                break;
            case ("LOSER"):
                Audio.stop();
                Audio.king("/UI-Sounds/loser.mp3");
                break;
            case ("DRAW"):
                Audio.stop();
                Audio.king("/UI-Sounds/draw.mp3");
                break;
            case ("INVALIDMOVE"):
                Random number2 = new Random();
                int rndNr2 = number2.nextInt(3) + 1;

                switch(rndNr2)
                {
                    case 1:
                        Audio.kingAdd("/UI-Sounds/invalid_forbidden.mp3");
                        break;
                    case 2:
                        Audio.kingAdd("/UI-Sounds/invalid_no.mp3");
                        break;
                    case 3:
                        Audio.kingAdd("/UI-Sounds/invalid_youcantdothis.mp3");
                        break;
                }
                break;
        }
    }


    /**
     * This function decodes the encoded highscore string given by the server and updates the client sided
     * highscore list. The string is structured in descending order of the score as:
     * "%&" + "username1:userscore1" + ... "%&" + usernameN:userscoreN
     * @param msg encoded highscore list
     */
    private void highscores(String msg) {
        ClientGUI.resetHighscoreList();
        String[] args = msg.split("%&");

        for (int i = 1; i < args.length; i++) {
            ClientGUI.addToHighscoreList(args[i]);
        }
    }

    /**
     * This method sends a lobbylist update request to the server when the client joins a lobby.
     */
    private void joinLobby() {
        Client.clientThread.sendCmdToServer(Protocol.UPDATELOBBIES, "");
    }

    /**
     * This method decodes the encoded timer string from the server and sets the game and turn timers in the GUI.
     * @param msg encoded timer string: "%&" + gameTimer + "%&" + turnTimer
     */
    private void timer(String msg) {
        String[] args = msg.split("%&");
        String gameTimer = args[1];
        String turnTimer = args[2];

        GameGUI.setTimers(gameTimer, turnTimer);
    }

    /**
     * This method gets called when a lobby was created and adds the new lobby information to the lobbylist.
     * @param msg lobby information: LobbyID: <lobbyID> - <lobbyname> | <playercount>/4
     */
    private void createLobby(String msg) {
        ClientGUI.addToLobbyList(msg);
    }

    /**
     * This method adds lobby chat messages to the specific lobby chat of the clients.
     * @param msg lobby message
     */
    private void lobbyChat(String msg) {
        ClientGUI.addToLobbyChat(msg);
    }

    /**
     * This function lists open lobbies for the client.
     * @param msg encoded open lobbies
     */
    private void openGames(String msg) {
        ClientGUI.resetOpenGamesList();
        if (msg.equals("")) {
            ClientGUI.addToOpenGamesList("Currently no free lobbies available");
            return;
        }
        List<String> argsList = new ArrayList<>(Arrays.asList(msg.split("%&", -1)));
        argsList.remove(0); // removes first empty space
        Client.openLobbies = new ArrayList<>();

        // update client-sided lobbylist
        int index = 0;
        while (argsList.size() > 0) {
            int lobbyID = Integer.parseInt(argsList.remove(0));
            int playerCount = Integer.parseInt(argsList.remove(0));
            Lobby currentLobby = new Lobby(lobbyID, 0);

            StringBuilder listEntry = new StringBuilder("LobbyID:" + lobbyID + " " +
                    playerCount + "/4 (");

            for (int i = 0; i < playerCount; i++) {
                String player = argsList.remove(0);
                currentLobby.addClient(new ClientID(player));
                listEntry.append(" ").append(player);
            }
            listEntry.append(" )");

            Client.openLobbies.add(currentLobby);
            ClientGUI.addToOpenGamesList(listEntry.toString());
            ++index;
        }
    }

    /**
     * This function lists past games for the client.
     * @param msg encoded past games
     */
    private void showPastGames(String msg) {
        ClientGUI.resetPastGamesList();
        if (msg.equals("")) {
            ClientGUI.addToPastGamesList("No games finished yet.");
            return;
        }

        Client.clientThread.sendCmdToServer(Protocol.UPDATELOBBIES, "");
        List<String> argsList = new ArrayList<>(Arrays.asList(msg.split("%&", -1)));
        String test = argsList.remove(0); // removes first empty space
        Client.pastGames = new ArrayList<>();

        while (!argsList.isEmpty()) {
            int lobbyID = Integer.parseInt(argsList.remove(0));
            int gameID = Integer.parseInt(argsList.remove(0));
            String status = argsList.remove(0);
//            int lobbyID2 = Client.clientLobbyList.get(Client.getIndex(lobbyID)).getLobbyID();

            try {
                Game game = new Game(new Lobby());
                game.setGameID(gameID);
                game.setStatus(status);

                String player1 = argsList.remove(0);
                game.setPlayer(0, player1);
                String player2 = argsList.remove(0);
                game.setPlayer(1, player2);
                String player3 = argsList.remove(0);
                game.setPlayer(2, player3);
                String player4 = argsList.remove(0);
                game.setPlayer(3, player4);

                Client.pastGames.add(game);
                ClientGUI.addToPastGamesList("GameID:" + game.getGameID() +
                        " was played by LobbyID:" + game.getGameID());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }


        }

    }

    /**
     * This function lists games which are currently running.
     * @param msg encoded games
     */
    private void showGames(String msg) {
        ClientGUI.resetRunningGamesList();
        if (msg.equals("")) {
            ClientGUI.addToRunningGamesList("Currently no games running.");
            return;
        }

        Client.clientThread.sendCmdToServer(Protocol.UPDATELOBBIES, "");
        List<String> argsList = new ArrayList<>(Arrays.asList(msg.split("%&", -1)));
        argsList.remove(0); // removes first empty space
        Client.currentGames = new ArrayList<>();

        while (!argsList.isEmpty()) {
            int lobbyID = Integer.parseInt(argsList.remove(0));
            int gameID = Integer.parseInt(argsList.remove(0));
            String status = argsList.remove(0);

            Game game = new Game(Client.clientLobbyList.get(Client.getIndex(lobbyID)));

            String player1 = argsList.remove(0);
            game.setPlayer(0, player1);
            String player2 = argsList.remove(0);
            game.setPlayer(1, player2);
            String player3 = argsList.remove(0);
            game.setPlayer(2, player3);
            String player4 = argsList.remove(0);
            game.setPlayer(3, player4);

            game.setGameID(gameID);
            game.setStatus(status);

            Client.currentGames.add(game);
            ClientGUI.addToRunningGamesList("GameID:" + game.getGameID() + " (" +
                    game.getPlayer(1) + " " + game.getPlayer(2) + " " +
                    game.getPlayer(3) + " " + game.getPlayer(4) + ")");
        }

    }

    /**
     * This function gets called once a game ends and there is a clear winner.
     * @param msg encoded winner information
     */
    private void winner(String msg) {
        gameThreadRunning = false;
        String winner1;
        String winner2;

        if (msg.equals("1")) {
            winner1 = GameGUI.game.getPlayer(1);
            winner2 = GameGUI.game.getPlayer(3);
        } else if (msg.equals("2")) {
            winner1 = GameGUI.game.getPlayer(2);
            winner2 = GameGUI.game.getPlayer(4);
        } else {
            GameGUI.setEndGame();
            return;
        }

        int points1 = GameGUI.game.getPlayerpoints(0);
        int points2 = GameGUI.game.getPlayerpoints(1);
        int points3 = GameGUI.game.getPlayerpoints(2);
        int points4 = GameGUI.game.getPlayerpoints(3);

        int teamRY = points1 + points3;
        int teamBG = points2 + points4;


        GameGUI.setEndGame();
        ClientGUI.addToLobbyChat(winner1 + " and " + winner2 + " have won, nice!");
//        GameGUI.setWinners(winner1, winner2);
        GameGUI.displayBoard();



    }

    /**
     * This function gets called when the client's game ends and there is no clear winner.
     */
    private void draw() {
        gameThreadRunning = false;
        GameGUI.setEndGame();
        ClientGUI.addToLobbyChat("The game has ended in a draw, well played.");
        updateGameLists();
    }

    /**
     * this method sends an update request for all game lists.
     */
    private void updateGameLists() {
        Client.clientThread.sendCmdToServer(Protocol.SHOWGAMES, "");
        Client.clientThread.sendCmdToServer(Protocol.PASTGAMES, "");
        Client.clientThread.sendCmdToServer(Protocol.OPENGAMES, "");
    }

    /**
     * This function gets called when a player leaves a currently running game.
     */
    private void endGame() {
        GameGUI.displayBoard();
        gameThreadRunning = false;
        System.out.println("Game ended because a player has left");
        ClientGUI.addToLobbyChat("Game ended because a player has left");
        GameGUI.setEndGame();
        updateGameLists();

        Audio.stop();
        Audio.king("/UI-Sounds/gameend.mp3");
    }

    /**
     * This function is called when it's the next turn of a game.
     * @param msg encoded next player
     */
    private void turn(String msg) {
        String[] args = msg.split("%&", 5);
        String nextPlayer = args[0];
        String nextPlayerColor = args[1];

        GameGUI.setNextTurn(nextPlayer, nextPlayerColor);

        Client.clientThread.sendCmdToServer(Protocol.TIMER, "");
        Client.clientThread.sendCmdToServer(Protocol.STATUS, "");

        GameGUI.setPoints();
        GameGUI.setPlayernames();

    }

    /**
     * This function signals the next turn of a game and prints out the board for the client.
     * @param msg encoded game board
     */
    private void move(String msg) {
        GameGUI.displayBoard();
        Client.clientThread.sendCmdToServer(Protocol.TURN, "");
    }


    /**
     * This function updates the client sided status of the game list.
     * @param msg encoded game list
     */
    private void updateStatus(String msg) {
        GameGUI.game.decodeStatus(msg);
        updateGameLists();

        GameGUI.setPoints();
        GameGUI.setPlayernames();
    }

    /**
     * This function updates the board of a currently running game.
     * @param msg encoded game board
     */
    public void updateBoard(String msg) {
        GameGUI.game.getBoard().decode(msg);
        GameGUI.displayBoard();
    }

    /**
     * This function starts a new game for the client.
     * @param msg encoded board
     */
    private void newGame(String msg) {
        GameGUI.startGameGUI();

        GameGUI.game = new Game(new ChessBoard(0));
        GameGUI.game.getBoard().decode(msg);

        Client.clientThread.sendCmdToServer(Protocol.STATUS, "");
        Client.clientThread.sendCmdToServer(Protocol.SHOWLOBBY, "");
        Client.clientThread.sendCmdToServer(Protocol.UPDATELOBBIES, "");

        updateGameLists();

        GameGUI.displayBoard();
        GameGUI.setPlayernames();

        Client.clientThread.sendCmdToServer(Protocol.TURN, "");
        Client.clientThread.sendCmdToServer(Protocol.STATUS, "");

//         thread updates game board every quarter second, not quit necessary atm, but will be used to keep track
//         of timers and player connections etc
//        Thread gameThread = new Thread(() -> {
//            try {
//                while (gameThreadRunning) {
//                    Client.clientThread.sendCmdToServer(Protocol.CHESSBOARD, "");
////                    TimeUnit.MILLISECONDS.sleep(500);
//                    Client.clientThread.sendCmdToServer(Protocol.STATUS, "");
//                    Client.clientThread.sendCmdToServer(Protocol.UPDATELOBBIES, "");
//                    TimeUnit.MILLISECONDS.sleep(250);
//                }
//            } catch (Exception e) {
//                gameThreadRunning = false;
//                e.printStackTrace();
//                clientTasksLogger.error("The game thread is not running!"); //LOGGER
//            }
//        });
//        gameThread.start();
    }


    /**
     * This function stores the current time of a ping so the round trip time can be established.
     */
    private void ping() {
        pingBackTime = System.currentTimeMillis();
    }


    /**
     * This method prints out the client's current Lobby and its players. It gets
     * a String from the server which contains the relevant information and splits
     * it up for the client to process.
     * @param msg contains the lobbyID and all players in it as a String. The
     *            arguments are split by the symbols "%&" for the
     *            client to easily process.
     */
    private void showLobby(String msg) {
        ClientGUI.resetLobbyPlayerList();

        String[] args = msg.split("%&", 5);
        String lobbyID = args[0];

        for (int i = 1; i < args.length; i++) {
            ClientGUI.addToLobbyPlayerList(args[i]);
            if (GameGUI.game != null) {
                GameGUI.game.setPlayer(i-1, args[i]);
            }
        }

    }

    /**
     * This function prints a message for the client. This message is visible in the console as well
     * as in chat
     * @param msg message
     */
    private void printMessage(String msg) {
        try {
            System.out.println(msg);

            //ChatGUI
            msg += "\n";
            Client.messages.add(msg);
            ClientGUI.addToGlobalChat(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is used for the private chat and allows private communication.
     * @param msg message
     */
    public void getWhisper(String msg) {
        //ChatGUI
        Client.messages.add(msg);
        ClientGUI.addToGlobalChat(msg);
        ClientGUI.addToLobbyChat(msg);
    }

    /**
     * This function updates the client sided lobby lists in case a player leaves a lobby and
     * signals the client he has left the lobby successfully.
     * @param msg encoded lobbyID and clientID
     */
    private void quitLobby(String msg) {
        Client.clientThread.sendCmdToServer(Protocol.UPDATELOBBIES, "");
        String[] args = msg.split("%&", 5);
        int lobbyID = Integer.parseInt(args[0]);
        int clientID = Integer.parseInt(args[1]);

        String msg2 = "[SYSTEM] You (ClientID:" + clientID + ") have left lobby " + lobbyID + ". \n";
        Client.messages.add(msg2);
        Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");

//        ClientGUI.addToGlobalChat(msg);
    }

    /**
     * This method gets lobby and player information as an encoded string
     * from the server and stores the data in the client-sided lobbylist.
     * It then prints out all the information on the client's console.
     * @param msg encoded player and lobby information
     */
    private void showLobbies(String msg) {
        List<String> argsList = new ArrayList<>(Arrays.asList(msg.split("%&", -1)));
        argsList.remove(0); // removes first empty space
        Client.clientLobbyList = new ArrayList<>();

        // update client-sided lobbylist
        int index = 0;
        while (argsList.size() > 0) {
            int lobbyID = Integer.parseInt(argsList.remove(0));
            int playerCount = Integer.parseInt(argsList.remove(0));
            String lobbyName = argsList.remove(0);
            Lobby currentLobby = new Lobby(lobbyID, 0);

            for (int i = 0; i < playerCount; i++) {
                currentLobby.addClient(new ClientID(argsList.remove(0)));
            }

            Client.clientLobbyList.add(currentLobby);
            ++index;
        }
    }


    /**
     * This function updates the client sided lobby list.
     * @param msg encoded lobbies
     */
    private void updateLobbies(String msg) {
        List<String> argsList = new ArrayList<>(Arrays.asList(msg.split("%&", -1)));
        argsList.remove(0); // removes first empty space
        Client.clientLobbyList = new ArrayList<>();
        ClientGUI.resetLobbyList();

        // update client-sided lobbylist
        int index = 0;
        while (argsList.size() > 0) {
            int lobbyID = Integer.parseInt(argsList.remove(0));
            int playerCount = Integer.parseInt(argsList.remove(0));
            String lobbyName = argsList.remove(0);
            Lobby currentLobby = new Lobby(lobbyID, 0);

            for (int i = 0; i < playerCount; i++) {
                currentLobby.addClient(new ClientID(argsList.remove(0)));
            }
            Client.clientLobbyList.add(currentLobby);
            ClientGUI.addToLobbyList("LobbyID:" + + lobbyID + " - " + lobbyName + " - " + playerCount + "/4");
            ++index;
        }
    }

    /**
     * This function sets the new name in the chat GUI when the user changes its name.
     * @param msg new username
     */
    private void setName(String msg) {
       // MainChat.setUsername(msg);
        ClientGUI.myUsername.setValue(msg);
        ClientGUI.myUsername.set(msg);
        Client.clientThread.sendCmdToServer(Protocol.UPDATELOBBIES, "");
    }


    /**
     * This function logs the user out of the chat and displays a leaving message on the clients console.
     */
    private void logout() {
        Client.clientThread.sendCmdToServer(Protocol.UPDATELOBBIES, "");
        Client.clientThread.sendCmdToServer(Protocol.UPDATEPLAYERS, "UPDATE");

        try {
            gameThreadRunning = false;
            System.out.println("You have logged out. Terminating client.");
            String msg = "[SYSTEM] You have logged out. Terminating client. \n";
            Client.messages.add(msg);

            TimeUnit.SECONDS.sleep(1);
            System.exit(0);
            updateGameLists();
            clientThread.closeThread();
        } catch (Exception e2) {
            e2.printStackTrace();
            clientTasksLogger.error("The client wasn't able to log out!"); //LOGGER
        }
    }

    /**
     * This function displays the client's username on the client's console.
     * @param username The username that is returned by the server.
     */
    private void showUsername(String username) {
        System.out.println("Your current user name is " + username);
        String msg = "[SYSTEM] Your current user name is " + username + "\n";
        Client.messages.add(msg);
        ClientGUI.myUsername.setValue(username);
        ClientGUI.addToGlobalChat(msg);
    }

    /**
     * This function updates the playerlist and prints it in the console.
     * @param msg message
     */
    public void showPlayers(String msg) {
        updatePlayers(msg);
    }

    /**
     * This function updates the playerlist
     * @param msg message
     */
    public void updatePlayers(String msg) {
        Client.playerList.clear();
        ClientGUI.resetPlayerList();
        List<String> argsList = new ArrayList<>(Arrays.asList(msg.split("%&", -1)));
        argsList.remove(0); // removes first empty space
        while (!argsList.isEmpty()) {
            int id = Integer.parseInt(argsList.remove(0));
            String username = argsList.remove(0);
            if (!username.equals("/nullUsername")) {
                Client.playerList.add(new ClientID(id, username));
                ClientGUI.addToPlayerList(username);
            }
        }
    }

}

