package gamelogic;

import server.ClientID;

/**
 * This class represents the players.
 */
public class Player {

    public char color;
    public Player teammate;
    private String username;
    private Timer timer;
    public GameStatus status = GameStatus.ACTIVE;
    private int id;
    public int points = 0;
    public boolean validMove = false;

    /**
     * This is a constructor of the Player class.
     */
    public Player(){
        this.color = 'u';
    }

    /**
     * This is a constructor of the Player class.
     * @param id The playerID
     * @param username The username.
     */
    public Player(int id, String username) {
        this.username = username;
        this.id = id;
        this.color = 'u';
    }

    /**
     * This method returns the ID
     */
    public int getID() {
        return id;
    }

    /**
     * This method returns the color of the player-
     * @return The color.
     */
    public char getColor() {return color;}

    /**
     * This method returns the points of the player.
     * @return The points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * This sets the points.
     * @param points The points.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * This method sets the players username.
     * @param username The username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method sets the players ID.
     * @param id The ID
     */
    public void setID(String id) {
        this.id = Integer.parseInt(id);
    }

    /**
     * This method sets the players color.
     * @param color The color.
     */
    public void setColor(char color) {
        this.color = color;
    }

    /**
     * This returns the players username.
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns true or false if player color is blue.
     * @return True or false
     */
    public boolean isBlue() {
        return this.color == 'B';
    }

    /**
     * Returns true or false if player color is red.
     * @return True or false
     */
    public boolean isRed() {
        return this.color == 'R';
    }

    /**
     * Returns true or false if player color is green.
     * @return True or false
     */
    public boolean isGreen() {
        return this.color == 'G';
    }

    /**
     * Returns true or false if player color is yellow.
     * @return True or false
     */
    public boolean isYellow() {
        return this.color == 'Y';
    }
}
