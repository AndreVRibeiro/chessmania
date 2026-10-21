package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * This class handles the highscore after a game has ended. It can update the highscore list file and encode a highscore
 * string which can be sent to the clients which requested it.
 */
public class Highscore {

    /**
     * This method updates the highscore for a specific username. It adds +1 points to the specified username.
     * If this username doesn't exist it adds it to the highscoreList.txt File
     * @param username the username receiving one extra point for a win
     */
    public static void updateHighscoreFor(String username) {
        File highscoreList = new File("highscoreList.txt");
        File tempFile = new File("temporaryList.txt");
        try {
            if(!highscoreList.exists()) {
                highscoreList.createNewFile();
                FileWriter fileWriter = new FileWriter(highscoreList);
                fileWriter.write(username + ":" + 1);
                fileWriter.flush();
                fileWriter.close();
                return;
            }


            tempFile.createNewFile();
            FileWriter fileWriter = new FileWriter(tempFile);
            Scanner scanner;
            try {
                scanner = new Scanner(highscoreList);
            } catch (FileNotFoundException e ) {
                e.printStackTrace();
                highscoreList.createNewFile();
                FileWriter fileWriter2 = new FileWriter(highscoreList);
                fileWriter2.write(username + ":" + 999);
                fileWriter2.flush();
                fileWriter2.close();
                scanner = new Scanner(highscoreList);
            }

            String entry;
            boolean userHasScore = false;
            String entryName;
            while(scanner.hasNext()) {
                entry = scanner.nextLine();

                String[] args = entry.split(":");
                entryName = args[0];
                if(entryName.equals(username)) {
                    int points = Integer.parseInt(args[1]);
                    points++;
                    fileWriter.write(username + ":" + points + "\n");
                    fileWriter.flush();
                    userHasScore = true;
                } else {
                    fileWriter.write(entry + "\n");
                    fileWriter.flush();
                }
            }

            if(!userHasScore) {
                fileWriter.write(username + ":1\n");
                fileWriter.flush();
            }

            fileWriter.close();
            scanner.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        highscoreList.delete();
        tempFile.renameTo(new File("highscoreList.txt"));

    }

    /**
     * This function returns the encoded highscore string of the complete highscore list on the server.
     * Encoded argument: %&<username1>:<userscore1> ... %&<usernameN>:<userscoreN>
     * @return encoded highscore list string
     */
    public static String encodeHighscore() {
        StringBuilder msg = new StringBuilder();

        try {
            List<String> highscoreEntry = new LinkedList<>();

            File highscoreList = new File("highscoreList.txt");
            Scanner scanner = new Scanner(highscoreList);

            while (scanner.hasNextLine()) {
                highscoreEntry.add(scanner.nextLine());
            }

            while (highscoreEntry.size() > 0) {
                String entry = "";
                int max = -1;

                for (String posEntry : highscoreEntry) {
                    String[] args = posEntry.split(":");
                    int posMax = Integer.parseInt(args[1]);
                    if (posMax > max) {
                        entry = posEntry;
                        max = posMax;
                    }
                }

                highscoreEntry.remove(entry);
                msg.append("%&").append(entry);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "" + msg;

    }

}