package server;

import gamelogic.Game;
import gamelogic.Move;
import gamelogic.Player;
import networking.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This class sends server protocol commands to clients and handles incoming client
 * protocol commands. Every client gets his own instance.
 */
public class ServerTasks {

    private final ClientID clientID;
    protected long pongBackTime;

    /**
     * This is the constructor of the ServerTasks class.
     * @param clientID The clientID.
     */
    ServerTasks(ClientID clientID) { this.clientID = clientID; }
    private static final Logger serverTasksLogger = LogManager.getLogger(ServerTasks.class);

    /**
     * This method handles all the server tasks based on the protocol command passed
     * on as argument.
     * @param cmd The Protocol command which specifies which task should be executed.
     * @param msg The String message needed to execute. Depending on the task it includes
     *            various arguments which need to be decoded.
     */
    public void serverTasks(Protocol cmd, String msg) {

        switch(cmd) {
            case LOGIN:
                login(msg);
                break;
            case LOGOUT:
                logout();
                break;
            case SETNAME:
                setName(msg);
                break;
            case HIGHSCORES:
                highscores();
                break;
            case CREATELOBBY:
                createLobby();
                break;
            case JOINLOBBY:
                joinLobby(msg);
                break;
            case QUITLOBBY:
                quitLobby();
                break;
            case NEWGAME:
                newGame();
                break;
            case MOVE:
                move(msg);
                break;
            case TURN:
                turn();
                break;
            case GAMEOVER:
                break;
            case GLOBALCHAT:
                globalChat(msg);
                break;
            case WHISPERCHAT:
                whisper(msg);
                break;
            case LOBBYCHAT:
                lobbyChat(msg);
                break;
            case SHOWLOBBIES:
                showLobbies();
                break;
            case UPDATELOBBIES:
                updateLobbies();
                break;
            case SHOWNAME:
                showUsername();
                break;
            case SHOWLOBBY:
                showLobby();
                break;
            case SHOWPLAYERS:
                players("");
                break;
            case SOUND:
                test(msg);
                break;
            case PING:
                break;
            case PONG:
                pong();
                break;
            case CHESSBOARD:
                updateChessboard();
                break;
            case STATUS:
                sendStatus();
                break;
            case ENDGAME:
                endGame();
                break;
            case SHOWGAMES:
                showGames();
                break;
            case PASTGAMES:
                pastGames();
                break;
            case OPENGAMES:
                openGames();
                break;
            case UPDATEPLAYERS:
                players("UPDATE");
                break;
            case TIMER:
                timer();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + cmd);
        }
    }

    /**
     * This method is called by the server when a client needs the current highscores.
     */
    private void highscores() {

        clientID.send(Protocol.HIGHSCORES, Highscore.encodeHighscore());
    }

    /**
     * This method is called by the server when a client needs the current timers of its game. The following arguments
     * get passed on, divided by the string splitter "%&": GameTimer + TurnTimer + UsernameCurrentPlayer
     */
    private void timer() {
        String msg = "%&" + clientID.currentLobby.getGameTimer() + "%&" +
                clientID.currentLobby.getTurnTimer() + "%&" + clientID.currentLobby.getGame().getCurrentTurn().getUsername();
        clientID.send(Protocol.TIMER, msg);

    }

    /**
     * This method is called by the server when a client wants to list open games. It sends open
     * lobbies as string encoded to this client
     */
    private void openGames() {
        String msg = encodedOpenLobbies();
        clientID.send(Protocol.OPENGAMES, msg);
    }

    /**
     * This function is called by the server if a client wants to list all currently running games. It sends the encoded
     * list to this client.
     */
    private void showGames() {
        StringBuilder msg = new StringBuilder();
        for (Game i : Server.currentGames) {
            msg.append("%&").append(i.getLobbyID());
            msg.append("%&").append(i.getGameID());
            msg.append("%&").append(i.returnStatus());
            for (int j = 1; j < 5; j++) {
                msg.append("%&").append(i.getPlayer(j));
            }
        }
        updateLobbies();
        clientID.send(Protocol.SHOWGAMES, msg.toString());
    }

    /**
     * This function is called by the server if a client wants to list past games. It sends the encoded list to the
     * client.
     */
    public void pastGames() {
        StringBuilder msg = new StringBuilder();
        // past games
        for (Game i : Server.pastGames) {
            msg.append("%&").append(i.getLobbyID());
            msg.append("%&").append(i.getGameID());
            msg.append("%&").append("FINISHED");
            for (int j = 1; j < 5; j++) {
                msg.append("%&").append(i.getPlayer(j));
            }
        }
        updateLobbies();
        clientID.send(Protocol.PASTGAMES, msg.toString());

    }

    /**
     * This function gets called when a game ends.
     */
    private void endGame() {
        if (clientID.currentLobby == null || clientID.currentLobby.getGame() == null) {
            return;
        }

        updateChessboard("LOBBY");

        if (Server.getGameIndex(clientID.currentLobby.getGame()) != -1) {
            int index = Server.getGameIndex(clientID.currentLobby.getGame());
            Server.pastGames.add(Server.currentGames.remove(index));
            Server.pastGames.get(index).setStatus("FINISHED");

        }

        // ends the game if a player quits
        // todo check this
        clientID.currentLobby.sendToLobby(Protocol.ENDGAME, "");
        clientID.currentLobby.endGame();
        updateLobbies();
    }


    /**
     * This function sets the next turn and signals this to the client.
     */
    private void turn() {
        if (clientID.currentLobby.getGame() == null) { return; }
        String nextPlayer = clientID.currentLobby.getGame().getNextPlayer();
        String nextPlayerColor = clientID.currentLobby.getGame().getNextColor();
        clientID.send(Protocol.TURN, nextPlayer + "%&" + nextPlayerColor);
    }

    /**
     * This function sends the encoded status of a game to the client.
     */
    private void sendStatus() {
        String msg = clientID.currentLobby.getGame().encodeGameStatus();
        Server.sendTo(clientID.getID(), Protocol.STATUS, msg);

        // ends game in case there is a player disconnect
        if (clientID.currentLobby.getPlayerCount() != 4) {
            clientID.send(Protocol.ENDGAME, "");
            endGame();
        }
    }

    /**
     * This function processes a players move and evaluates whether it is valid.
     * @param msg move argument of the client
     */
    private void move(String msg) {
        // makes sure you are in a game for the command to work
        if (clientID.currentLobby == null) {
            clientID.send(Protocol.MESSAGE, "(MOVE command works only in game)");
            return;
        } else if (clientID.currentLobby.getGame() == null) {
            clientID.send(Protocol.MESSAGE, "(MOVE command works only in game)");
            return;
        }


        if (msg.startsWith(" ")) {
            msg = msg.substring(1);
        }
        if (msg.equals("SKIP")) {
            Player player = clientID.getPlayer();
            Move move = new Move(player, msg);
            clientID.currentLobby.move(player, move);
            clientID.currentLobby.sendToLobby(Protocol.CHESSBOARD, clientID.currentLobby.getBoard().encode());
            clientID.currentLobby.sendToLobby(Protocol.MOVE, "");
            return;
        }
        if (!msg.matches("^[A-Na-n]\\d{1,14}/[A-Na-n]\\d{1,14}")) {
            clientID.send(Protocol.MESSAGE, "Your move does not match the criteria for a valid move.");
            clientID.send(Protocol.MESSAGE, "You can make your move with the command: MOVE <startSquare/endSquare>");
            return;
        }
        String coordinates = msg;
        String[] args = msg.split("/", 5);
        String startSquare = args[0];
        String endSquare = args[1];

        Player player = clientID.getPlayer();
        Move move = new Move(player, coordinates);
        clientID.currentLobby.move(player, move);

        if (clientID.currentLobby.getBoard() != null) {
            clientID.send(Protocol.CHESSBOARD, clientID.currentLobby.getBoard().encode());
            if (move.valid) {
                clientID.currentLobby.sendToLobby(Protocol.CHESSBOARD, clientID.currentLobby.getBoard().encode());
                clientID.currentLobby.sendToLobby(Protocol.MOVE, "");
            }
        }
    }

    /**
     * This function sends the updated chessboard of a game to the client which requested it.
     */
    private void updateChessboard() {
        String msg = clientID.currentLobby.getBoard().encode();
        clientID.send(Protocol.CHESSBOARD, msg);
    }

    /**
     * This function sends the updated chessboard of a game to the lobby.
     * @param lobby The lobby.
     */
    private void updateChessboard(String lobby) {
        String msg = clientID.currentLobby.getBoard().encode();

        if (lobby.equals("LOBBY")) {
            clientID.currentLobby.sendToLobby(Protocol.CHESSBOARD,msg);
            return;
        }
        clientID.send(Protocol.CHESSBOARD, msg);
    }

    /**
     * This function starts a new game if a client requested it.
     */
    private void newGame() {
        updateLobbies();
        if (clientID.currentLobby == null) {
            clientID.send(Protocol.LOBBYCHAT, "You're not in a lobby yet");
            return;
        }

        if (clientID.currentLobby.getPlayerCount() != 4) {
            clientID.send(Protocol.LOBBYCHAT, "4 players needed to start a game.");
            return;
        }

        if (clientID.currentLobby.getGame() != null) {
            clientID.send(Protocol.LOBBYCHAT, "You're already in a game currently.");
            return;
        }
        clientID.currentLobby.newGame(clientID);


        String msg = clientID.currentLobby.getGame().encodeGameStatus();
        clientID.currentLobby.sendToLobby(Protocol.STATUS, msg);
    }

    /**
     * This method is called by the server to know the pong back time.
     */
    private void pong() {
        pongBackTime = System.currentTimeMillis();
    }

    /**
     * This method is called by the server if a client wants to know the lobby hes currently in.
     * It gets all necessary data needed to display the current lobby and its players and
     * sends it to the client as one String via protocol. The separation of arguments is
     * done with "%&" so it can be easily split up again from the client.
     */
    private void showLobby() {
        if (clientID.currentLobbyID == -1) {
            return;
        }
        StringBuilder msg = new StringBuilder(String.valueOf(clientID.currentLobbyID));
        for (ClientID lobbyPlayer : clientID.currentLobby.lobbyClients) {
            msg.append("%&").append(lobbyPlayer.getUsername());
        }
        clientID.currentLobby.sendToLobby(Protocol.SHOWLOBBY, msg.toString());
    }

    /**
     * This method is called if the client wants to quit the current lobby.
     */
    private void quitLobby() {
        if (clientID.currentLobby == null) {
            return;
        }
        if (clientID.currentLobby.getGame() != null) {
            endGame();
        }

        int lobbyID = clientID.currentLobbyID;
        if (lobbyID == -1) {
            clientID.send(Protocol.MESSAGE, "You are in no lobby hence you can't quit.");
            return;
        }

        String msg = "";
        for (Lobby lobbyID2 : Server.lobbyList) {
            if (lobbyID2.getLobbyID() == lobbyID) {
                msg += lobbyID + "%&" + clientID.getID() + "%&";
                lobbyID2.deleteClient(clientID);

                //player lobbylist update in GUI
                StringBuilder msg2 = new StringBuilder(String.valueOf(lobbyID2));
                for (ClientID lobbyPlayer : lobbyID2.lobbyClients) {
                    msg2.append("%&").append(lobbyPlayer.getUsername());
                }
                lobbyID2.sendToLobby(Protocol.SHOWLOBBY, msg2.toString());
                break;
            }

        }

        System.out.println(clientID.getUsername() + " --> has left lobbyID: " + lobbyID);
        clientID.send(Protocol.QUITLOBBY, msg);

    }

    /**
     * This method creates a new lobby to start a game in for a specific client.
     */
    private void createLobby() {
        if (clientID.currentLobbyID != -1) {
            clientID.send(Protocol.MESSAGE, "You're already in lobby " + clientID.currentLobbyID + ".");
            return;
        }
        Lobby lobbyID = new Lobby(clientID);
        clientID.currentLobby = lobbyID;
        System.out.println("NEWLOBBY --> LobbyID: " + lobbyID.getLobbyID());
        clientID.send(Protocol.GLOBALCHAT, clientID.getUsername() + " has created a new lobby --> LobbyID: "
                + lobbyID.getLobbyID());
        String message = "LobbyID: " + lobbyID.getLobbyID() + " - " + lobbyID.getLobbyName() + " | " + lobbyID.getPlayerCount() + "/4";
        Server.sendToAll(Protocol.CREATELOBBY, message);
        showLobby();
    }

    /**
     * This method is called by the server when a client wants to see all the lobbies.
     */
    private void showLobbies() {
        clientID.send(Protocol.SHOWLOBBIES, encodedLobbies());
    }

    /**
     * This method is called by the server and gets all necessary data needed for the client to
     * display all current lobbies and its players. It sends it then to the client via protocol.
     */
    private void updateLobbies() {
        Server.sendToAll(Protocol.UPDATELOBBIES, encodedLobbies());
        showLobby();
    }

    /**
     * This function encodes all currently non-empty lobbies.
     * @return encoded lobbies
     */
    public String encodedLobbies() {
        StringBuilder msg = new StringBuilder();
//        System.out.println(msg);
        for (Lobby lobbyID : Server.lobbyList) {
            msg.append("%&").append(lobbyID.getLobbyID()).append("%&").append(lobbyID.getPlayerCount()).append("%&").append(lobbyID.getLobbyName());
            if (lobbyID.getPlayerCount() != 0) {
                for (ClientID clientID : lobbyID.lobbyClients) {
                    String username = clientID.getUsername();
                    if (username != null) {
                        msg.append("%&").append(username);
                    }
                }
            }
        }
        return "" + msg;
    }

    /**
     * This function encodes all lobbies which are not full yet.
     * @return encoded open lobbies
     */
    private String encodedOpenLobbies() {
        StringBuilder msg = new StringBuilder();
        for (Lobby lobby : Server.lobbyList) {
            if (lobby.getPlayerCount() >= 4) {
                continue;
            }
            msg.append("%&").append(lobby.getLobbyID()).append("%&").append(lobby.getPlayerCount());
            if (lobby.getPlayerCount() != 0) {
                for (ClientID clientID : lobby.lobbyClients) {
                    String username = clientID.getUsername();
                    if (username != null) {
                        msg.append("%&").append(username);
                    }
                }
            }
        }
        return "" + msg;
    }

    /**
     * This method gets called by the server allows a client to join a specific lobby.
     * @param msg The argument given by the client, specifying which lobby
     *                to join.
     */
    private void joinLobby(String msg) {
        if (msg.startsWith(" ")) {
            msg = msg.substring(1);
        }
        if (clientID.currentLobbyID != -1) {
            clientID.send(Protocol.MESSAGE, "You're already in lobby " + clientID.currentLobbyID
                    + ", quit to join another.");
            return;
        }
        try {
            int lobbyID = Integer.parseInt(msg);
            int counter = 0;
            // adds client to the lobbyclients list in the lobby class instance
            for (Lobby lobbyID2 : Server.lobbyList) {
                if (lobbyID2.getLobbyID() == lobbyID) {
                    if (lobbyID2.getPlayerCount() == 4) {   // checks if there is place in the lobby
                        clientID.send(Protocol.MESSAGE, "Sorry, this lobby is already full.");
                        break;
                    }
                    lobbyID2.addClient(clientID);
                    clientID.send(Protocol.MESSAGE, "You have joined lobby " + lobbyID + ".");
                    break;
                }
                ++counter;
            }
            if (counter == Server.lobbyList.size()) {
                clientID.send(Protocol.MESSAGE, "This LobbyID doesn't exist.");
            }
        } catch (Exception e) {
            serverTasksLogger.error("ERROR: " + clientID.getUsername() + "-->  failed to join lobby");
            e.printStackTrace();
            clientID.send(Protocol.MESSAGE, "Failed to join lobby, try again.");
        }

        clientID.send(Protocol.JOINLOBBY, "");
        showLobby();
    }

    /**
     * This function sends a lobby message from a client to all lobby members.
     * @param msg message
     */
    private void lobbyChat(String msg) {
        if (clientID.currentLobbyID == -1) {
            clientID.send(Protocol.MESSAGE, "You are currently not in a lobby.");

        }
        if (msg.startsWith(" ")) {
            msg = msg.substring(1);
        }

        for (Lobby lobby : Server.lobbyList) {
            if (clientID.currentLobbyID == lobby.getLobbyID()) {
                for (ClientID clientID : lobby.lobbyClients) {
                    clientID.send(Protocol.LOBBYCHAT,this.clientID.getUsername() + "@lobby: " + msg);
                }
            }
        }

    }

    /**
     * Logs the user into chat and displays some information in the server console.
     */
    private void login(String msg) {
        players("UPDATE");
        if (msg.startsWith(" ")) {
            msg = msg.substring(1);
        }

        if (clientID.getUsername() == null) {
            clientID.checkUsername(msg);
            clientID.send(Protocol.SETNAME, clientID.getUsername());
        } else {
            String oldname = clientID.getUsername();
            clientID.checkUsername(msg);
            String newname = clientID.getUsername();
            clientID.send(Protocol.SETNAME, newname);
            players("UPDATE");
        }

        Server.sendToAll(Protocol.MESSAGE, "[SYSTEM] " + clientID.getUsername() + " joined the chat.");
        System.out.print("LOGIN --> ClientID: " + clientID.getID());
        System.out.println("   Username: " + clientID.getUsername());

    }

    /**
     * This method logs the user out of the chat.
     */
    public void logout() {
        clientID.send(Protocol.LOGOUT, "");
        Server.clientList.remove(clientID);

        endGame();
        showLobby();

        // remove from lobby if necessary
        if (clientID.currentLobbyID != -1) {
            Server.lobbyList.get(getIndex(clientID.currentLobby)).deleteClient(clientID);
            updateLobbies();
        }

        if (!clientID.getUsername().equals("/nullUsername")) {
            Server.sendToAll(Protocol.GLOBALCHAT, "[SYSTEM] " + clientID.getUsername() + " has logged out.");
            System.out.println("LOGOUT --> ClientID: " + clientID.getID() + "  Username: " + clientID.getUsername());
        } else {
            System.out.println("DISCONNECT --> ClientID: " + clientID.getID() + "  Username: " + clientID.getUsername());
        }

        players("UPDATE"); // sends all current clients the updated playerlist
        clientID.serverThread.closeThread();

    }

    /**
     * Small method to get the index of a lobby stored in the server's lobbylist.
     */
    private int getIndex(Lobby lobby) {
        for (Lobby i : Server.lobbyList) {
            if (i.getLobbyID() == lobby.getLobbyID()) {
                return Server.lobbyList.indexOf(i);
            }
        }
        return -1;
    }

    /**
     * This method allows the client to see his name.
     */
    private void showUsername() {
        String username = clientID.getUsername();
        clientID.send(Protocol.SHOWNAME, username);
    }

    /**
     * This function changes the client's username with a new one.
     * @param msg The new username.
     */
    private void setName(String msg) {
        if (msg.startsWith(" ")) {
            msg = msg.substring(1);
        }
        // checks if player is in game, if so is not allowed to change name
        if (Server.isPlayerInGame(clientID.getID())) {
            clientID.send(Protocol.MESSAGE, "You can't change your name during a game.");
            return;
        }
        if (clientID.getUsername() == null) {
            clientID.checkUsername(msg);
            clientID.send(Protocol.SETNAME, clientID.getUsername());

        } else {
            String oldname = clientID.getUsername();
            clientID.checkNewUsername(msg);
            String newname = clientID.getUsername();
            clientID.send(Protocol.SETNAME, newname);
            msg = "[SYSTEM] " + oldname + "'s new username is " + newname;
            Server.sendToAll(Protocol.GLOBALCHAT, msg);
        }
    }

    /**
     * This function encodes and sends the complete playerlist to the client. When the keyword "UPDATE" is used it
     * sends it to all clients currently online.
     */
    private void players(String mode) {
        StringBuilder str = new StringBuilder();
        for (ClientID clientID : Server.clientList) {
            str.append("%&").append(clientID.getID()).append("%&").append(clientID.getUsername());
        }
        if (mode.equals("")) {
            this.clientID.send(Protocol.SHOWPLAYERS, str.toString());
        } else if (mode.equals("UPDATE")) {
            Server.sendToAll(Protocol.UPDATEPLAYERS, str.toString());
        }

    }


    /**
     * This function sends messages to the global chat.
     * @param msg The actual message.
     */
    private void globalChat(String msg) {
        if (msg != null) {
            Server.sendToAll(Protocol.GLOBALCHAT, clientID.getUsername() + "@all: " + msg);
        }
    }

    /**
     * This function is used for testing purposes.
     * @param msg message
     */
    private void test(String msg) {
//        System.out.println(msg);
    }

    /**
     * This function allows the clients to send private chat messages to a specific client.
     * @param msg This is the message that needs to be sent.
     */
    private void whisper(String msg) {
        if (msg == null || msg.equals("")) {
            clientID.send(Protocol.MESSAGE, "@<exactUsername> <yourMessage> sends out a whisper message.");
            return;
        }
        if (msg.startsWith(" ")) {
            msg = msg.substring(1);
        }
        String trimmedMessage = "";
        int maxLength = 0;
        ClientID friend = null;
        for (ClientID clientID : Server.clientList) {
            String name = clientID.getUsername();
            if (msg.length() < name.length()) {continue;}
            String currentName = msg.substring(0, name.length());
            if (name.equals(currentName)) {
                if (currentName.length() > maxLength) {
                    maxLength = currentName.length();
                    trimmedMessage = msg.substring(maxLength).trim();
                    friend = clientID;
                }
            }
        }
        if (this.clientID == friend) {
            this.clientID.send(Protocol.MESSAGE, "You are talking to yourself :P");
            return;
        }
        if (msg.length() <= maxLength) {
            this.clientID.send(Protocol.MESSAGE, "Say something to your friend ^^");
            return;
        }
        if (maxLength > 0) {
            this.clientID.send(Protocol.WHISPERCHAT, "you@" + friend.getUsername() + ": " + trimmedMessage);
            msg = this.clientID.getUsername() + "@you: " + trimmedMessage;
            friend.send(Protocol.WHISPERCHAT, msg);
        } else {
            msg = "Your friend doesn't exist, sorry :(";
            this.clientID.send(Protocol.MESSAGE, msg);
        }
    }

}
