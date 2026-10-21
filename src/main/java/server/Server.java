package server;

import gamelogic.Game;
import networking.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * This class starts a server and opens up a socket to listen if a client is connecting.
 * It generates a server thread for each specific connection.
 */
public class Server {
    public static List<ClientID> clientList = new ArrayList<>();
    private boolean serverRunning;
    public static List<Lobby> lobbyList = new ArrayList<>();
    public static List<Game> currentGames = new ArrayList<>();
    public static List<Game> pastGames = new ArrayList<>();
    private static final Logger serverLogger = LogManager.getLogger(Server.class);

    /**
     * This is the constructor of the server class.
     * @param port The port.
     */
    public Server(int port) {

        try {
            System.out.println(
                    "Server started and awaiting Clients on Port " + port);
            ServerSocket ss = new ServerSocket(port);

            serverRunning = true;
            while (serverRunning) {
                Socket socket = ss.accept();
                System.out.println("NEW CONNECTION --> Accepted: " + socket);
                ServerThread serverThread = new ServerThread(socket);
                Thread thread = new Thread(serverThread);
                thread.start();
                clientList.add(new ClientID(serverThread));
            }
        } catch (IOException e) {
            serverRunning = false;
            System.err.println(e.toString());
            e.printStackTrace();
            serverLogger.error("Server Error: " +e.toString());
        }
    }

    /**
     * Sends protocol command from server to all clients.
     * @param cmd command
     * @param msg message
     */
    public static void sendToAll(Protocol cmd, String msg) {
        for (ClientID clientID : clientList) {
            clientID.send(cmd, msg);
        }
    }

    /**
     * This function sends protocol commands to the clients.
     * @param id The clientID.
     * @param cmd THe command.
     * @param msg The message.
     */
    public static void sendTo(int id, Protocol cmd, String msg) {
        for (ClientID clientID : clientList) {
            if (clientID.getID() == id) {
                clientID.send(cmd, msg);
                return;
            }
        }
    }

    /**
     * Small method to get the index of a game stored in the client's gamesList.
     */
    public static int getGameIndex(Game game) {
        if (game == null) {return -1;}
        for (Game i : currentGames) {
            if (i.getGameID() == game.getGameID()) {
                return currentGames.indexOf(i);
            }
        }
        return -1;
    }

    /**
     * This function tells if a player is in the game.
     * @param clientID The clientID.
     * @return True or false.
     */
    public static boolean isPlayerInGame(int clientID) {
        for (Game i : currentGames) {
            for (int j = 1; j < 5; j++) {
                if (i.getClientID(j) == clientID) {
                    return true;
                }
            }
        }
        return false;
    }

}

