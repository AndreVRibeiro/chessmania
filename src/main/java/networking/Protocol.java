package networking;

/**
 * Lists all commands for the network protocol used.
 */
public enum Protocol {

    // Login and Logout -> Client to Server

    /**
     * This command signals that a client / new player has connected to the server.
     * It is only sent from client to server.
     * The data string is formatted as follows: username
     */
    LOGIN("LGIN"),

    /**
     * This command signals that a client wants to disconnect from the server.
     * All the clients who are still connected get a message.
     * This is sent from the client to the server
     * No data string is required.
     */
    LOGOUT("LOGT"),

// Game Menu -> Client to Server

    /**
     * This command signals that a client wants to change its name.
     * Needs to be followed by argument "ClientID".
     * It is sent from clients to server
     * The server checks whether the name is legal
     * The data string is formatted as follows: new username
     */
    SETNAME("NAME"),

    /**
     * This command signals that a client wants to have access to the highscore list.
     * It is sent from the clients to the server.
     * The server then sends the results to the client.
     * The data string for the client is formatted as follows:
     * <username>:<score>%&<username2>:<score2>...<usernameN>:<scoreN>
     */
    HIGHSCORES("HSCR"),

    /**
     * This command signals that the client wants to create a new lobby.
     * It is sent from clients to server
     * Server should then create a new lobby and add  the client to the lobby.
     * No data string is needed.
     */
    CREATELOBBY("CELY"),

    /**
     * This command signals that a client wants to join an existing lobby.
     * It is sent from clients to server.
     * The server checks id the lobby exists and adds the player
     * The data string needs LobbyID as argument.
     */
    JOINLOBBY("JOLY"),

    /**
     * This command signals that a client wants to leave his lobby.
     * It is sent from clients to server.
     * The server displays a leaving message and removes the client from the lobby.
     * No data string is needed
     */
    QUITLOBBY("QILY"),

    /**
     * This command is used to start a new game
     * It is sent from a player to server
     * The server creates a new game with every player who is in the same lobby.
     * No data string is needed
     */
    NEWGAME("NEWG"),

    /**
     *This command sends responses from the server to the clients the in the form of messages.
     * It is sent from the server to the client
     * The data string is formatted as follows: <message>
     */
    MESSAGE("MSGE"),


// Game / Game Logic -> Client to Server

    /**
     * This command is used to transmit the move of a figure.
     * It is sent from clients to server.
     * The command transmits the data to identify the figure and
     * the square onto which the figure is going to move
     * The data string is formatted as follows: <startSquare>/<endSquare>
     */
    MOVE("MOVE"),

    /**
     * This command signals that the player wants to select a specific figure.
     * It is sent from clients to server.
     */
    SELECT("SELT"),

// Game / Game Logic -> Server to Client

    /**
     * This command tells all clients which figure has been selected by the player.
     * It is sent from server to clients.
     */
    SELECTED("SLTD"),

    /**
     * This command tells all the players that a new turn has begun and who's turn it is.
     * It is sent from server to all clients.
     * No string data is needed.
     */
    TURN("TURN"),

    /**
     * This command signals that the game is over.
     * It is sent from server to to all clients.
     */
    GAMEOVER("GOVR"),

    /**
     * This command relays the current status of a lobby game.
     * It is sent from server to client.
     */
    STATUS("STAT"),

    /**
     * This command is used to update the chessboard after every move.
     * It is sent from the server to the clients.
     * The server then updates the board and returns it to the players.
     * It encodes from the top left scuare to the bottom right one and uses an integer for empty spaces and
     * the first char of a piece color + chesspiece initial char: Chesspiece = <piece color char><chesspiece initial char>
     * Argument encoding: <Nr of empty Squares><Chesspiece>/..../<Chesspiece><Nr of empty Squares>/
     *
     */
    CHESSBOARD("CHBD"),

    /**
     * This command is used to end the game.
     * It is sent from the server to the clients.
     * The server calculates which has more points and returns the result.
     *
     */
    ENDGAME("ENDG"),

    /**
     * This command is used when the game end in a draw.
     * It is sent from the server to the clients.
     * The server returns a message with points that were achievded by both teams
     */
    DRAW("DRAW"),

    /**
     * This command returns the winners of the game after the game has ended.
     * It is sent from the server to the clients.
     * The data string return the winners and the achieved points.
     * Argument encoding: <lobbyID><clientID1><username1>....<clientID4><username4><gameID><playerTurnID><status>
     *     <playerpoints1>...<playerpoints4> with a "%&" string splitter in between each argument
     */
    WINNER("WINN"),

    /**
     * This command indicates that the player wants to see a list of all the
     * games that are currently being played.
     * It is sent from clients to server.
     * The server returns a data string containing all the ongoing games.
     * Argument encoding: <lobbyID><gameID><status><username1>...<username4>
     *     with a "%&" String splitter in between each argument. Argument arrangement can be repeated
     *     for each game.
     */
    SHOWGAMES("SHGM"),

    /**
     * This command indicates that the player wants to see a list of all finished games.
     * It is sent from clients to server.
     * The server returns a list with all the finished games.
     * Argument encoding: <lobbyID><gameID><status><username1>...<username4>
     *     with a "%&" String splitter in between each argument. Argument arrangement can be repeated
     *     for each game.
     *
     */
    PASTGAMES("PSMS"),

    /**
     * This command indicates that the player wants to see a list of all open games ready to be joined.
     * It is sent from clients to server.
     * The server then return a list with all the lobbies that are still waiting for players.
     * Argument encoding: <lobbyID><playercount><username1>...<username4>
     *     with a "%&" String splitter in between each argument. Argument arrangement can be repeated
     *     for each lobby.
     */
    OPENGAMES("OPGM"),

    /**
     * This command is used to update both timers, the game and turn timer, to clients during a game.
     *  * Argument encoding: <gameTimer><turnTimer><currentTurnUsername>
     *      *     with a "%&" String splitter in between each argument.
     */
    TIMER("TMER"),

// Chat
    /**
     * This command signals that a client wants to send a message to
     * all the connected clients. The server then sends the message to all the clients.
     * It is sent from clients to server.
     * The data string is formatted as follows: message
     */
    GLOBALCHAT("CHAT"),

    /**
     * This command signals that a client wants to send a private message to a client.
     * It is sent from clients to server.
     * The message contains the encoded ClientID of the sender as argument.
     * Argument encoding: <clientID> <message>
     */
    WHISPERCHAT("WCHT"),

    /**
     * This command is used to send messages in the lobby. The server sends the message to
     * all the players in the same lobby.
     * It is sent from clients to server.
     * The data string is formatted as follows: message.
     */
    LOBBYCHAT("LCHT"),

    /**
     * This command shows all available lobbies to the player. The server sends
     * a list with all lobbies to the client.
     * It is sent from clients to server.
     * The server now send a list with all lobbies and players.
     * Argument encoding: <lobbyID><playercount><username1>...<username4>
     */
    SHOWLOBBIES("SLOB"),

    /**
     * This command is used to update the list that contains all lobbies.
     * It is sent from the server to the clients.
     * Argument encoding: <lobbyID><playercount><username1>...<username4>
     */
    UPDATELOBBIES("LBBS"),

    /**
     * This command indicates that the player wants to know their username.
     * It is sent from clients to server.
     * The server now sends a data string with the name.
     * The data string is formatted as follows: username.
     */
    SHOWNAME("SNAM"),

    /**
     * This command indicates that a client wants to know in which lobby he is currently in.
     * The server now sends a data string containing the LobbyID as well as current players
     * in the lobby to the client.
     * Argument encoding: <lobbyID><username1>...<username4>
     *     with a "%&" String splitter in between each argument
     */
    SHOWLOBBY("SLNA"),

    /**
     * This command shows the client how many and which players are already in the lobby.
     * It is sent from the clients to server.
     * The server returns a data string containing all the information.
     * <clientID 1><username 1><clientID 2><username 2>...<clientID n><username n>
     *     with a "%&" String splitter in between each argument
     */
    SHOWPLAYERS("SPLY"),

    /**
     * This command is used to update the list that contains all players.
     * It is sent from the server to the clients.
     * <clientID 1><username 1><clientID 2><username 2>...<clientID n><username n>
     *     with a "%&" String splitter in between each argument
     */
    UPDATEPLAYERS("UPPL"),

// Music
    /**
     * This command is used to tell the client when and which sound files to play.
     * It is sent from clients to server and from the server to the client.
     * The data string is formatted as follows: name of sound file.
     */
    SOUND("SONO"),

// Ping-Pong
    /**
     * This command is used to ping the server and check whether the connection is still standing.
     */
    PING("PING"),

    /**
     * This command is used to pong the clients and check whether the connection is still standing.
     */
    PONG("PONG");


    private final String cmdStr;

    /**
     * This is the constructor of the Protocol class.
     * @param cmdStr The abbreviated command.
     */
    Protocol(String cmdStr) {
        this.cmdStr = cmdStr;
    }

    /**
     * This function returns the command String.
     * @return
     */
    public String getString(){
        return cmdStr;
    }

    /**
     * checks whether given string command is a valid protocol command.
     */
    public static Protocol getCommand(String GivenCommandString) {
        for(Protocol c : Protocol.values()) {
            if (GivenCommandString.equals(c.cmdStr)){
                return c;
            }
        }
        return null;
    }

}
