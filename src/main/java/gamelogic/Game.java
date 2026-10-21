package gamelogic;

import networking.Protocol;
import server.Lobby;
import server.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static gamelogic.GameStatus.*;

/**
 * This class represents a game with a chessboard, players and lobby.
 */
public class Game {
    private ChessBoard board;
    private final Player[] players;
    private Player currentTurn;
    private GameStatus status;
    private ArrayList<Move> movesList;
    private ArrayList<Piece> pieceKilled;
    private int lobbyID;
    private Lobby lobby;
    private int gameID;
    private static int counter = 0;
    private int blueAndGreenPoints = 0;
    private int redAndYellowPoints = 0;
    public boolean validated = false;
    private String winner1 = "";
    private String winner2 = "";
    private boolean gameFinished = false;


    /**
     * This is the constructor of the Game class. It is used for testing purposes.
     * @param board The board that will be played on.
     */
    public Game(ChessBoard board) {
        this.board = board;
        this.gameID = counter++;

        this.players = new Player[4];
        this.players[0] = new Player();
        this.players[1] = new Player();
        this.players[2] = new Player();
        this.players[3] = new Player();

        this.movesList = new ArrayList<>();
        this.pieceKilled = new ArrayList<>();

        this.setColourAndTeamMate();
        this.currentTurn = players[0];
    }


    /**
     * This is the constructor of the Game class. It starts a new Game with a new board and 4 players.
     */
    public Game(Lobby lobby) {
        this.lobby = lobby;

        if (lobby.getPlayerCount() != 4) {
            this.gameID = -1;
            this.players = new Player[4];
            this.players[0] = new Player();
            this.players[1] = new Player();
            this.players[2] = new Player();
            this.players[3] = new Player();
            this.players[0].setUsername("- empty slot -");
            this.players[1].setUsername("- empty slot -");
            this.players[2].setUsername("- empty slot -");
            this.players[3].setUsername("- empty slot -");
            return;
        }
        Player player1 = new Player(lobby.lobbyClients.get(0).getID() , lobby.lobbyClients.get(0).getUsername());
        Player player2 = new Player(lobby.lobbyClients.get(1).getID() , lobby.lobbyClients.get(1).getUsername());
        Player player3 = new Player(lobby.lobbyClients.get(2).getID() , lobby.lobbyClients.get(2).getUsername());
        Player player4 = new Player(lobby.lobbyClients.get(3).getID() , lobby.lobbyClients.get(3).getUsername());

        lobby.lobbyClients.get(0).setPlayer(player1);
        lobby.lobbyClients.get(1).setPlayer(player2);
        lobby.lobbyClients.get(2).setPlayer(player3);
        lobby.lobbyClients.get(3).setPlayer(player4);

        this.lobbyID = lobby.getLobbyID();
        this.gameID = counter++;

        this.status = ACTIVE;
        this.board = new ChessBoard();
        this.players = new Player[4];
        this.players[0] = player1;
        this.players[1] = player2;
        this.players[2] = player3;
        this.players[3] = player4;
        this.movesList = new ArrayList<>();
        this.pieceKilled = new ArrayList<>();

        this.setColourAndTeamMate();

        for (int i = 0; i < 4; i++) {
            if (this.players[i].isRed()) {
                this.currentTurn = this.players[i];
            }
        }
    }

    /**
     * This method returns the gameID.
     * @return The gameID.
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * This function returns the player at a certain position in the array list.
     * @param player The number of the player.
     * @return The player that is at that position in the array list.
     */
    public String getPlayer(int player) {
        return players[player - 1].getUsername();
    }

    /**
     * This function returns the player at a certain position in the array list.
     * @param player The position in the array list.
     * @return The player.
     */
    public Player getPlayerFromList(int player) {
        return this.players[player];
    }

    /**
     * This returns the current turn.
     * @return The player who is playing now.
     */
    public Player getCurrentTurn() {
        return this.currentTurn;
    }

    /**
     * This function gets the clientID of a certain player.
     * @param player The number of the player.
     * @return The clientID
     */
    public int getClientID(int player) {
        return players[player - 1].getID();
    }

    /**
     * This method returns the chessboard.
     * @return The chessboard.
     */
    public ChessBoard getBoard() {
        return this.board;
    }

    /**
     * This function is used to to set a player in the player list.
     * @param i The position that the player will be put in.
     * @param username The username of the player.
     */
    public void setPlayer(int i, String username) {
        players[i].setUsername(username);
    }

    /**
     * This function sends protocol commands to all the players in the lobby.
     * @param cmd The command.
     * @param msg The message.
     */
    public void sendToLobby(Protocol cmd, String msg) {
        Server.sendTo(players[0].getID(), cmd, msg);
        Server.sendTo(players[1].getID(), cmd, msg);
        Server.sendTo(players[2].getID(), cmd, msg);
        Server.sendTo(players[3].getID(), cmd, msg);
    }

    /**
     * This method gives each player a color and a teammate
     */
    public void setColourAndTeamMate() {
        this.players[0].color = 'R';
        this.players[1].color = 'B';
        this.players[2].color = 'Y';
        this.players[3].color = 'G';

        this.players[0].teammate = this.players[2];
        this.players[1].teammate = this.players[3];
        this.players[2].teammate = this.players[0];
        this.players[3].teammate = this.players[1];
    }

    /**
     * This method checks if any king pieces were eliminated and sets the game status to BLUE_GREEN_WIN or RED_YELLOW_WIN.
     */
    public boolean checkGameOver() {
        if (this.pieceKilled.size() > 0) {
            for (Piece piece : this.pieceKilled) {
                if (piece.p.equals("K")) {
                    gameOver();
                    return true;

                }
            }

        }
        return false;
    }

    /**
     * This function gets the points of a player.
     * @param player The player whose points are to be returned.
     * @return The points
     */
    public int getPlayerpoints(int player) {
        return this.players[player].getPoints();
    }

    public void sendEndMsgToLobby(int winnerTeam) {
        int player1 = lobby.lobbyClients.get(0).getID();
        int player2 = lobby.lobbyClients.get(1).getID();
        int player3 = lobby.lobbyClients.get(2).getID();
        int player4 = lobby.lobbyClients.get(3).getID();

        if (winnerTeam == 0) {
            Server.sendTo(player1, Protocol.SOUND, "WINNER");
            Server.sendTo(player3, Protocol.SOUND, "WINNER");
            Server.sendTo(player2, Protocol.SOUND, "LOSER");
            Server.sendTo(player4, Protocol.SOUND, "LOSER");
        } else {
            Server.sendTo(player1, Protocol.SOUND, "LOSER");
            Server.sendTo(player3, Protocol.SOUND, "LOSER");
            Server.sendTo(player2, Protocol.SOUND, "WINNER");
            Server.sendTo(player4, Protocol.SOUND, "WINNER");
        }
    }


    /**
     * This function is called once a game has ended properly and the game's outcome needs to be evaluated.
     */
    public void gameOver() {

        sendToLobby(Protocol.CHESSBOARD, getBoard().encode());

        this.redAndYellowPoints = this.players[0].points + this.players[2].points;
        this.blueAndGreenPoints = this.players[1].points + this.players[3].points;

        if (redAndYellowPoints > blueAndGreenPoints) {
            sendToLobby(Protocol.LOBBYCHAT, players[0].getUsername() + " and " + players[2].getUsername() +
                    " have " + this.redAndYellowPoints + " Points");
            sendToLobby(Protocol.LOBBYCHAT, players[1].getUsername() + " and " + players[3].getUsername() +
                    " have " + this.blueAndGreenPoints + " Points");
            this.status = GameStatus.RED_YELLOW_WIN;
            winner1 = players[0].getUsername();
            winner2 = players[2].getUsername();


            if (!gameFinished) {
                sendToLobby(Protocol.STATUS, this.encodeGameStatus());
                sendToLobby(Protocol.WINNER, "1");
                gameFinished = true;
                sendEndMsgToLobby(0);
            }


        } else if (redAndYellowPoints < blueAndGreenPoints) {
            sendToLobby(Protocol.LOBBYCHAT, players[1].getUsername() + " and " + players[3].getUsername() +
                    " have " + this.blueAndGreenPoints + " Points");
            sendToLobby(Protocol.LOBBYCHAT, players[0].getUsername() + " and " + players[2].getUsername() +
                    " have " + redAndYellowPoints + " Points");
            this.status = GameStatus.BLUE_GREEN_WIN;
            winner1 = players[1].getUsername();
            winner2 = players[3].getUsername();

            if (!gameFinished) {
                sendToLobby(Protocol.STATUS, this.encodeGameStatus());
                sendToLobby(Protocol.WINNER, "2");
                gameFinished = true;
                sendEndMsgToLobby(1);
            }

        } else { // redAndYellowPoints == blueAndGreenPoints
            sendToLobby(Protocol.LOBBYCHAT, "Both teams have " + this.redAndYellowPoints + " Points");
            this.status = GameStatus.DRAW;

            if (!gameFinished) {
                sendToLobby(Protocol.STATUS, this.encodeGameStatus());
                sendToLobby(Protocol.DRAW,"");
                gameFinished = true;
                sendToLobby(Protocol.SOUND, "DRAW");
            }
        }
        lobby.endGame();
    }

    /**
     * This function encodes the game status of a game. Its encoded message contains lobbID, player IDs and names,
     * gameID, clientID of current turn, status of the game, player points and timer.
     * @return encoded game status
     */
    public String encodeGameStatus() {
        String msg = "";
        msg += encodeStatus();
        msg += this.players[0].points + "%&" + this.players[1].points + "%&"
                + this.players[2].points + "%&" + this.players[3].points + "%&";
        return msg;
    }

    /**
     * This function encodes the game status of a game. Its encoded message contains lobbID, player IDs and names,
     * gameID, clientID of current turn and the status of the game.
     * @return encoded status
     */
    public String encodeStatus() {
        String msg = "";
        msg += lobbyID + "%&";
        msg += getClientID(1) + "%&" + players[0].getUsername() + "%&";
        msg += getClientID(2) + "%&" + players[1].getUsername() + "%&";
        msg += getClientID(3) + "%&" + players[2].getUsername() + "%&";
        msg += getClientID(4) + "%&" + players[3].getUsername() + "%&";
        msg += gameID + "%&";
        msg += currentTurn.getID() + "%&";
        msg += status + "%&";
        return msg;
    }

    /**
     * This function decodes the status of a game.
     * @param msg encoded status
     */
    public void decodeStatus(String msg) {
//        System.out.println("decodestatus msg: " + msg);
        List<String> argsList = new ArrayList<>(Arrays.asList(msg.split("%&", -1)));

        lobbyID = Integer.parseInt(argsList.remove(0));

        players[0].setID(argsList.remove(0));
        players[0].setUsername(argsList.remove(0));

        players[1].setID(argsList.remove(0));
        players[1].setUsername(argsList.remove(0));

        players[2].setID(argsList.remove(0));
        players[2].setUsername(argsList.remove(0));

        players[3].setID(argsList.remove(0));
        players[3].setUsername(argsList.remove(0));

        gameID = Integer.parseInt(argsList.remove(0));

        int currentTurn = Integer.parseInt(argsList.remove(0));
        for (int i = 0; i < 4; i++) {
            if (players[i].getID() == currentTurn) {
                this.currentTurn = players[i];
            }
        }
        String statustest = argsList.remove(0);
        status = returnStatus(statustest);

//        try {
            players[0].setPoints(Integer.parseInt(argsList.remove(0)));
            players[1].setPoints(Integer.parseInt(argsList.remove(0)));
            players[2].setPoints(Integer.parseInt(argsList.remove(0)));
            players[3].setPoints(Integer.parseInt(argsList.remove(0)));
//        } catch (NumberFormatException e) {
//            System.out.println("no points yet");
//        }

    }


    /**
     * This function transmits the moves that the player wants to make to the respective Piece classes.
     * The function checks if it is the players turn and if the figures to be moved belong to the player.
     * @param player The player that wants to make the move
     * @param move The move that the player wants to make
     */
    public void move(Player player, Move move) {
        move.coordinates = move.coordinates.toUpperCase(Locale.ROOT);
        if (player != this.currentTurn) {
            Server.sendTo(player.getID(), Protocol.LOBBYCHAT, "Sorry, but it is not your turn!");
        } else {
            if (move.coordinates.equals("SKIP")) {
                this.nextTurn();
            } else  {
                if (!move.coordinates.contains("/")) {
                    Server.sendTo(player.getID(), Protocol.LOBBYCHAT,"Invalid Input! Try again!");
                    Server.sendTo(player.getID(), Protocol.SOUND,"INVALIDMOVE");
                } else {
                    int[] coordinates = this.board.getXYCoordinates(move);
                    if (coordinates[0] == 0 && coordinates[1] == 0) {
                        Server.sendTo(player.getID(), Protocol.LOBBYCHAT,"Invalid Input! Try again!");
                        Server.sendTo(player.getID(), Protocol.SOUND,"INVALIDMOVE");
                    } else if (coordinates[2] == 0 && coordinates[3] == 0) {
                        Server.sendTo(player.getID(), Protocol.LOBBYCHAT,"Invalid Input! Try again!");
                        Server.sendTo(player.getID(), Protocol.SOUND,"INVALIDMOVE");
                    } else if (!this.board.getBox(coordinates[0],coordinates[1]).isSquareOccupied()) {
                        Server.sendTo(player.getID(), Protocol.LOBBYCHAT,"There is no Chess piece on that field. Try again!");
                        Server.sendTo(player.getID(), Protocol.SOUND,"INVALIDMOVE");
                    } else if (this.board.getBox(coordinates[0],coordinates[1]).getPiece().color != player.color) {
                        Server.sendTo(player.getID(), Protocol.LOBBYCHAT,"That figure does not belong to you. Try again!");
                        Server.sendTo(player.getID(), Protocol.SOUND,"INVALIDMOVE");
                    } else {
                        this.board.getBox(coordinates[0],coordinates[1]).getPiece().move(move,this.board);
                        if (move.valid){
                            boolean isGameOver = false;

                            this.movesList.add(move);
                            getCurrentTurn().validMove = true;
                            validated = true;

                            if (move.killedPiece != null) {
                                this.pieceKilled.add(move.killedPiece);
                                this.setPoints(player,move);
                                isGameOver = this.checkGameOver();
                            }
                            if (!isGameOver) {
                                this.nextTurn();
                            }
                        } else {
                            Server.sendTo(player.getID(), Protocol.LOBBYCHAT,"Try again");
                            Server.sendTo(player.getID(), Protocol.SOUND,"INVALIDMOVE");
                        }
                    }
                }
            }
        }
    }



    /**
     * This function gives the player point for taking the opponents pieces
     * @param move The move that the player did
     */
    public void setPoints(Player player, Move move) {
        if (move.killedPiece.p.equals("P")) {
            player.points = player.points + 1;
        } else if (move.killedPiece.p.equals("R")) {
            player.points = player.points + 5;
        } else if (move.killedPiece.p.equals("N")) {
            player.points = player.points + 3;
        } else if (move.killedPiece.p.equals("B")) {
            player.points = player.points + 5;
        } else if (move.killedPiece.p.equals("K")) {
            player.points = player.points + 20;
        } else if (move.killedPiece.p.equals("Q") && move.killedPiece.promotedPawn) {
            player.points = player.points + 1;
        } else if (!move.killedPiece.promotedPawn && move.killedPiece.p.equals("Q")) {
            player.points = player.points + 9;
        }
    }

    /**
     * This is used to set the Status od the game.
     * @param status The status.
     */
    public void setStatus(GameStatus status) {
        this.status = status;
    }

    /**
     * This function sets the next player each turn.
     */
    public void nextTurn() {
        if (this.currentTurn.isRed()) {
            for (int i = 0; i < 4; i++) {
                if (this.players[i].isBlue()) {
                    this.currentTurn = this.players[i];
                }
            }
        } else if (this.currentTurn.isBlue()) {
            for (int i = 0; i < 4; i++) {
                if (this.players[i].isYellow()) {
                    this.currentTurn = this.players[i];
                }
            }
        } else if (this.currentTurn.isYellow()) {
            for (int i = 0; i < 4; i++) {
                if (this.players[i].isGreen()) {
                    this.currentTurn = this.players[i];
                }
            }
        } else if (this.currentTurn.isGreen()) {
            for (int i = 0; i < 4; i++) {
                if (this.players[i].isRed()) {
                    this.currentTurn = this.players[i];
                }
            }
        }
    }

    /**
     * This is a small function to get the Index of a game via specific gameID
     * @param gameID game indicator
     * @return index of the game
     */
    public int getIndex(int gameID) {
        for (Game i : Server.currentGames) {
            if (i.getGameID() == gameID) {
                return Server.currentGames.indexOf(i);
            }
        }
        return -1;
    }

    /**
     * This function returns the status od the game.
     * @param status The status that is to be returned.
     * @return The game status.
     */
    public GameStatus returnStatus(String status) {
        switch (status) {
            case "ACTIVE":
                return ACTIVE;
            case "FINISHED":
                return FINISHED;
            case "BLUE_GREEN_WIN":
                return BLUE_GREEN_WIN;
            case "RED_YELLOW_WIN":
                return RED_YELLOW_WIN;
            case "STALEMATE":
                return STALEMATE;
            case "CHECKMATE":
                return CHECKMATE;
            case "DRAW":
                return DRAW;
            default:
                System.out.println("not a status in Game.java returnStatus()");
                return null;
        }
    }

    /**
     * This method actually returns the game status.
     */
    public String returnStatus() {
        return this.status.toString();
    }

    /**
     * This sets the game status.
     * @param status The status.
     */
    public void setStatus(String status) {
        switch (status) {
            case "ACTIVE":
                this.status = ACTIVE;
                break;
            case "FINISHED":
                this.status =  FINISHED;
                break;
            case "BLUE_GREEN_WIN":
                this.status =  BLUE_GREEN_WIN;
                break;
            case "RED_YELLOW_WIN":
                this.status =  RED_YELLOW_WIN;
                break;
            case "STALEMATE":
                this.status =  STALEMATE;
                break;
            case "CHECKMATE":
                this.status =  CHECKMATE;
                break;
            case "DRAW":
                this.status =  DRAW;
                break;
            default:
                System.out.println("not a status in Game.java returnStatus(): " + status);
                this.status =  null;
                break;
        }
    }

    /**
     * This function sets the gameID.
     * @param gameID The gameID.
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * This method returns the current player.
     * @return The player.
     */
    public String getNextPlayer() {
        return currentTurn.getUsername();
    }

    /**
     * This method returns the current players color.
     * @return The color.
     */
    public String getNextColor() {
        return "" + currentTurn.getColor();
    }

    /**
     * This returns the lobbyID.
     */
    public String getLobbyID() {
        return "" + lobbyID;
    }

    /**
     * This returns the winners of the game.
     * @param i The first or second winner.
     * @return The winning player.
     */
    public String getWinner(int i) {
        if (i == 1) {
            if (!winner1.equals("")) {
                return winner1;
            }
        } else if (i == 2) {
            if (!winner2.equals("")) {
                return winner2;
            }
        }
        return "" + -1;
    }




}
