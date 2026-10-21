package server;

import gamelogic.*;
import networking.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static server.Highscore.updateHighscoreFor;

/**
 * This class is used to create lobby instances and manage games played by this specific instance.
 */
public class Lobby {
    private static int lobbyCounter = 0;
    public List<ClientID> lobbyClients = new ArrayList<>();
    public int playerCount = 0;
    public String lobbyName = "";
    private final int lobbyID;
    private Game game;
    private Timer gameTimer;
    private Timer turnTimer = new Timer(0, 10);
    private int gameStatus;
    private static final Logger lobbyLogger = LogManager.getLogger(Lobby.class);

    /**
     * This is the constructor of the Lobby class.
     * @param clientID The clientID.
     */
    public Lobby(ClientID clientID) {
        ++this.playerCount;
        this.lobbyID = ++lobbyCounter;
        // this.game = new ChessBoardStatus(); // needs to be programmed yet
        this.gameStatus = 0; // TO DO i.e. 0 = Lobby initialized, 1 = Lobby not full yet, ...
        lobbyClients.add(clientID);

        clientID.currentLobbyID = this.lobbyID;
        clientID.currentLobby = this;
        lobbyName = clientID.getUsername() + "'s Lobby";

        Server.lobbyList.add(this);

    }

    /**
     * This returns the chessboard that is being played on.
     * @return The chessboard.
     */
    public ChessBoard getBoard() {
        if (game != null) {
            return game.getBoard();
        }
        return null;
    }

    /**
     * This is a constructor for te Lobby class.
     * @param lobbyID The lobbyID.
     * @param playerCount The amount of players.
     */
    public Lobby(int lobbyID, int playerCount) {
        this.lobbyID = lobbyID;
        this.playerCount = playerCount;
    }

    /**
     * Only for testing purposes in console
     */
    public Lobby() {
        this.lobbyID = 789;
        for (int i = 0; i < 4; i++) {
            lobbyClients.add(new ClientID("player" + (i + 1)));
            lobbyClients.get(i).player = new Player();
        }
        this.playerCount = 4;
    }

    /**
     * This returns the lobby name.
     * @return the lobby name.
     */
    public String getLobbyName() { return this.lobbyName; }

    /**
     * This returns the lobby itself.
     * @return The lobby.
     */
    public Lobby getLobby() {
        return this;
    }

    /**
     * This returns the lobbyID.
     * @return The lobbyID.
     */
    public int getLobbyID() {
        return lobbyID;
    }

    /**
     * This returns the username of a specific client.
     * @param i The position of the client in the list.
     * @return The username.
     */
    public String getUsername(int i) {
        return lobbyClients.get(i).getUsername();
    }

    /**
     * This returns the amount of players in the lobby.
     * @return The amount of players.
     */
    public int getPlayerCount() {
        return this.playerCount;
    }

    /**
     * This returns the game.
     * @return The game.
     */
    public Game getGame() {
        return game;
    }


    /**
     * This method adds a client to the lobbyClients list of this lobby instance.
     *
     * @param clientID This is the unique identifier of a client.
     */
    public void addClient(ClientID clientID) {
        if (playerCount < 4) {
            lobbyClients.add(clientID);

            clientID.currentLobbyID = this.lobbyID;
            clientID.currentLobby = this;
            ++this.playerCount;
        } else {
            System.out.println("Lobby full already, sry!");
        }
    }

    /**
     * This method deletes a client from the lobbyClients list of this lobby instance.
     *
     * @param clientID This is the unique identifier of a client.
     */
    public void deleteClient(ClientID clientID) {
        lobbyClients.remove(clientID);
        clientID.currentLobbyID = -1;
        clientID.currentLobby = null;
//        --clientID.currentLobby.playerCount;
        --playerCount;
        if (playerCount == 0) {
            Server.lobbyList.remove(this);
        }
    }

    /**
     * This function gets called when a game is starting. It sets the timer and monitors it.
     * In case the timer runs out before the game ends it handles the end of the game by calling
     * the endGame() function of this class.
     * @param clientID the class containing all information about a client.
     */
    public void newGame(ClientID clientID) {
        if (playerCount != 4) {
            clientID.send(Protocol.LOBBYCHAT, "4 players needed to start a new game.");
            return;
        }
        game = new Game(this);



        String playerStr1 = lobbyClients.get(0).getUsername();
        String playerStr2 = lobbyClients.get(1).getUsername();
        String playerStr3 = lobbyClients.get(2).getUsername();
        String playerStr4 = lobbyClients.get(3).getUsername();


        // todo addition, check
        game.setPlayer(0, playerStr1);
        game.setPlayer(1, playerStr2);
        game.setPlayer(2, playerStr3);
        game.setPlayer(3, playerStr4);

        Server.currentGames.add(game);


//        sendToLobby(Protocol.MESSAGE, "New game created with");
//        sendToLobby(Protocol.MESSAGE, playerStr1);
//        sendToLobby(Protocol.MESSAGE, playerStr2);
//        sendToLobby(Protocol.MESSAGE, playerStr3);
//        sendToLobby(Protocol.MESSAGE, playerStr4);
//        sendToLobby(Protocol.MESSAGE, "Have fun!");


        gameTimer = new Timer(2, 0, this);
//        System.out.println("[SERVER] timer started");
        new Thread(gameTimer).start();

        // This thread is used to send messages to the players at specific times
        Thread timerThread = new Thread(() -> {
            try {
                boolean tenSecsMsg = false;
                boolean oneMinMsg = false;

                while (this.gameTimer.threadRunning) {
                    TimeUnit.MILLISECONDS.sleep(200);

                    // messages the lobby that only 10s are remaining
                    if (this.gameTimer.getTimer() != null && this.gameTimer.getTimer().equals("0:10") && !tenSecsMsg) {
                        this.sendToLobby(Protocol.SOUND, "TENSECONDS");
                        tenSecsMsg = true;
                    }

                    // messages the lobby that only 1min is remaining
                    if (this.gameTimer.getTimer() != null && this.gameTimer.getTimer().equals("1:00") && !oneMinMsg) {
                        this.sendToLobby(Protocol.SOUND, "ONEMINUTE");
                        oneMinMsg = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                lobbyLogger.error("Exception timerThread: " + e.toString());
            }
            if (game != null) {
                game.gameOver();
            }
        });
        timerThread.start();



        turnTimer = new Timer(0, 10);

        // This thread is used for the 10s turn timer of the current turn
        Thread turnTimerThread = new Thread(() -> {
            try {
                while (gameTimer.threadRunning) {
                    Player player;

//                    turnTimer.reset(0, 10);
                    turnTimer = new Timer(0, 10);
                    new Thread(turnTimer).start();
//                    System.out.println(turnTimer.getSecTimer());

                    player = game.getCurrentTurn();
                    boolean tiktok = false;
                    while (turnTimer.isRunning()) {

                        if (turnTimer.getSecTimer().equals("3") && !tiktok) {
                            Server.sendTo(game.getCurrentTurn().getID(), Protocol.SOUND, "TIKTOK");
                            tiktok = true;
                        }

                        if (/*game.validated*/player.validMove /*|| !gameTimer.threadRunning*/) {
                            // if this line is not in the code the 4th player gets
                            // skipped during a game :0
//                            System.out.println("move made by player: " + player.getUsername());
                            // Timing issue, delaying
                            //
                            TimeUnit.MILLISECONDS.sleep(200);
                            turnTimer.stop();
                            turnTimer.endTimer();
                            player.validMove = false;
                        }
                    }

                    if (player == game.getCurrentTurn()
                            && gameTimer.threadRunning) {
                        Move move = new Move(game.getCurrentTurn(),"SKIP");
                        move(game.getCurrentTurn(), move);
                        sendToLobby(Protocol.MOVE,"");
                    }
                }

            } catch (Exception e) {
//                e.printStackTrace();
                lobbyLogger.error("Exception turnTimerThread: " + e.toString());
            }
        });
        turnTimerThread.start();

//        sendToLobby(Protocol.STATUS, game.encodeStatus());
        sendToLobby(Protocol.NEWGAME, game.getBoard().encode());
    }

    /**
     * This function sends commands to the lobby.
     * @param cmd The command.
     * @param msg The message.
     */
    public void sendToLobby(Protocol cmd, String msg) {
        for (ClientID client : lobbyClients) {
            client.send(cmd, msg);
        }
    }


    public void sendTo(int i, Protocol cmd, String msg) {
        lobbyClients.get(0).send(cmd, msg);
    }

    /**
     * This function is used to make a move in the game.
     * @param player The player who makes the move.
     * @param move The actual move.
     */
    public void move(Player player, Move move) {
        game.move(player, move);
    }

    /**
     * This function ends the game of a lobby. It moves the game to the past games list
     * and updates the lobbylist on the server.
     */
    public void endGame() {

        try {
            gameTimer.endTimer();
            turnTimer.endTimer();

            String winner1 = game.getWinner(1);
            String winner2 = game.getWinner(2);

            if (!winner1.equals("-1") && !winner2.equals("-1")) {
                updateHighscoreFor(winner1);
                updateHighscoreFor(winner2);
            }
        } catch (NullPointerException e) {
            //            e.printStackTrace();
        }
        int index = Server.getGameIndex(this.getGame());
        try {
            if (Server.currentGames.size() != 0) {
                Server.pastGames.add(Server.currentGames.remove(index));
                Server.pastGames.get(index).setStatus("FINISHED");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR: INDEXXXXXXX");
        }
        this.game = null;

        StringBuilder msg = new StringBuilder();
        System.out.println(msg);
        for (Lobby lobbyID : Server.lobbyList) {
            msg.append("%&").append(lobbyID.getLobbyID()).append("%&").append(lobbyID.getPlayerCount());
            if (lobbyID.getPlayerCount() != 0) {
                for (ClientID clientID : lobbyID.lobbyClients) {
                    String username = clientID.getUsername();
                    if (username != null) {
                        msg.append("%&").append(username);
                    }
                }
            }
        }
    }

    /**
     * This function returns the turn timer.
     * @return The turn timer.
     */
    public String getTurnTimer() {
        return turnTimer.getSecTimer();
    }

    /**
     * This function returns the game timer.
     * @return The game timer.
     */
    public String getGameTimer() {
        return gameTimer.getTimer();
    }

    /**
     * This method sends both timers information to the specific lobby during a game.
     * Encoded argument: %&<gameTimer>%&<turnTimer>
     */
    public void sendTimersToLobby() {

        try {
            String msg = "%&" + this.getGameTimer() + "%&" + this.getTurnTimer();
            this.sendToLobby(Protocol.TIMER, msg);
        } catch (NullPointerException e) {
            e.printStackTrace();
            String msg = "%&" + this.getGameTimer() + "%&" + "0s";
            this.sendToLobby(Protocol.TIMER, msg);
        }
    }
}
