package gamelogic;

import server.Lobby;

/**
 * This class creates a timer for the game.
 */
public class Timer implements Runnable {
    int min = 2;
    int sec = 10;
    public boolean threadRunning = true;
    boolean isRunning = true;
    String message = "-1";
    Lobby lobby;
    String timeString;

    /**
     * This is the constructor of the class.
     * @param min The amount of minutes.
     * @param sec The amount of seconds.
     * @param lobby The lobby.
     */
    public Timer(int min, int sec, Lobby lobby) {
        this.min = min;
        this.sec = sec;
        this.lobby = lobby;
    }

    /**
     * This is the constructor of the class.
     * @param min The amount of minutes.
     * @param sec The amount of seconds.
     */
    public Timer(int min, int sec){
        this.min = min;
        this.sec = sec;
    }

    /**
     * This starts the timer.
     */
    public void run(){
        while (min > -1 && threadRunning) {
            if (sec == 0) {
                timeString = min + ":0" + sec;
                if (lobby != null) { lobby.sendTimersToLobby(); }

                --min;
                if (min != -1) { message = "MIN"; }
                sec = 59;
                if (min == -1) {
                    timeString = "00:00";
                    if (lobby != null) { lobby.sendTimersToLobby(); }
                    message = "END";
                    isRunning = false;
                    threadRunning = false;
                    return;
                }
                try {
                    Thread.sleep(250);
                    if (lobby != null) { lobby.sendTimersToLobby(); }
                    Thread.sleep(250);
                    if (lobby != null) { lobby.sendTimersToLobby(); }
                    Thread.sleep(250);
                    if (lobby != null) { lobby.sendTimersToLobby(); }
                    Thread.sleep(250);
                    if (lobby != null) { lobby.sendTimersToLobby(); }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (sec < 10){
                timeString = min + ":0" + sec;
                if (lobby != null) { lobby.sendTimersToLobby(); }
                try {
                    Thread.sleep(250);
                    if (lobby != null) { lobby.sendTimersToLobby(); }
                    Thread.sleep(250);
                    if (lobby != null) { lobby.sendTimersToLobby(); }
                    Thread.sleep(250);
                    if (lobby != null) { lobby.sendTimersToLobby(); }
                    Thread.sleep(250);
                    if (lobby != null) { lobby.sendTimersToLobby(); }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                --sec;
            } else {
                timeString = min + ":" + sec;
                if (lobby != null) { lobby.sendTimersToLobby(); }
                try {
                    Thread.sleep(250);
                    if (lobby != null) { lobby.sendTimersToLobby(); }
                    Thread.sleep(250);
                    if (lobby != null) { lobby.sendTimersToLobby(); }
                    Thread.sleep(250);
                    if (lobby != null) { lobby.sendTimersToLobby(); }
                    Thread.sleep(250);
                    if (lobby != null) { lobby.sendTimersToLobby(); }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                --sec;
            }
        }

    }

    /**
     * This gets the time as a string.
     * @return The timer
     */
    public String getTimer() {
        return (timeString);
    }

    /**
     * This returns the seconds.
     * @return The seconds.
     */
    public String getSecTimer() {
        return "" + sec;
    }

    /**
     * Returns true if the timer is running, false if not.
     * @return True or False.
     */
    public synchronized boolean isRunning() {
        return isRunning;
    }

    /**
     * This resets the timer.
     * @param min The minutes.
     * @param sec The seconds.
     */
    public synchronized void reset(int min, int sec){
        threadRunning = true;
        isRunning = true;
        this.min = min;
        this.sec = sec;
    }

    /**
     * This stops the timer.
     */
    public synchronized void stop() {
        this.isRunning = false;
        this.threadRunning = false;
    }

    /**
     * This returns the message.
     * @return The message.
     */
    public synchronized String getMessage() {
        return message;
    }

    /**
     * This sets the message.
     * @param msg The message.
     */
    public synchronized void setMessage(String msg) {
        this.message = msg;
    }

    /**
     * This ends the timer.
     */
    public synchronized void endTimer() { this.threadRunning = false; }


}