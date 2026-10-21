package server;

import gamelogic.Player;
import networking.Protocol;

/**
 * This class handles the information of each separate client and assigns instances
 * of server classes needed to connect to the server and let it execute the tasks
 * necessary.
 */
public class ClientID {
    private static int clientCounter;
    protected int id;
    private String username;
    public ServerThread serverThread;
    public ServerTasks serverTasks;
    public Player player;

    public int currentLobbyID = -1;
    public Lobby currentLobby;

    /**
     * This is a constructor of the ClientID class.
     * @param serverThread The server Thread.
     */
    public ClientID(ServerThread serverThread) {
        id = clientCounter++;
        this.serverThread = serverThread;
        serverTasks = new ServerTasks(this);
        serverThread.setServerTasks(serverTasks);
        serverThread.setClientID(this);
        this.username = "/nullUsername";
    }

    /**
     * This is a constructor of the ClientID class.
     * @param username The username of the client.
     */
    public ClientID(String username) {
        this.username = username;
    }

    /**
     * This is a constructor of the ClientID class.
     * @param clientID The clientID
     * @param username The username of the client.
     */
    public ClientID(int clientID, String username) {
        this.id = clientID;
        this.username = username;
    }

    /**
     * This function returns the clients username.
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * This returns the ClientID.
     * @return The ClientID.
     */
    public int getID() {
        return id;
    }

    /**
     * This returns the Player.
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * This method is used to set a player.
     * @param player The player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * This method is used to set an username.
     * @param name The username.
     */
    public void setUsername(String name) {
        this.checkUsername(name);
        //this.username = name;
    }


    /**
     *  This method gives the Client a username based on his system username.
     *  After getting the system username the method checkUsername is called.
     *  It checks if the username is already being used and changes is accordingly.
     */
    public void suggestUsername() {
        String suggestedUsername = System.getProperty("user.name");
        this.checkUsername(suggestedUsername);
    }


    /**
     * This method takes the username and checks if it is already being used by somebody else.
     * If so it adds a number at the end of the name, that corresponds with the amount of
     * equal names.
     * @param username username
     *
     */
    public void checkUsername(String username) {
        String placeHolderName = username;
        int counter = 0;

        for (int i = 0; i < Server.clientList.size()-1; i++) {
            for (int j = 0; j < Server.clientList.size()-1; j++) {
                if (placeHolderName.equals(Server.clientList.get(j).username)) {
                    counter++;
                }
            }
            if (counter > 0) {
                placeHolderName = username + "_" + counter;
            }
        }
        this.username = placeHolderName;
    }

    /**
     * This method takes the username and checks if it is already being used by somebody else.
     * If so it adds a number at the end of the name, that corresponds with the amount of
     * equal names. It is used in the checkUsername method to further inspect the names.
     * @param username username
     *
     */
    public void checkNewUsername(String username) {
        int id = this.getID();
        String placeHolderName = username;
        int counter = 0;

        for (int i = 0; i < Server.clientList.size(); i++) {
            for (int j = 0; j < Server.clientList.size(); j++) {
                if (Server.clientList.get(j).getID() != id) {
                    if (placeHolderName.equals(Server.clientList.get(j).username)) {
                        counter++;
                    }
                }
            }
            if (counter > 0) {
                placeHolderName = username + "_" + counter;
            }
        }

        this.username = placeHolderName;
    }

    /**
     * Sends a protocol command from the server to this specific client.
     * @param cmd command
     * @param msg message
     */
    public void send(Protocol cmd, String msg) {
        if(serverThread == null || msg == null){
            return;
        }
        serverThread.sendCmdToClient(cmd, msg);

    }

}
