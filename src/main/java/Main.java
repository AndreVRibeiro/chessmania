import client.Client;
import networking.Constants;
import server.Server;

/**
 * Main class to execute.
 */
public class Main {

    /**
     * This is the main method that is used to start the server and the client.
     * @param args Server or Client arguments.
     */
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.out.println("The arguments are not valid");
            System.out.println("client <hostadress>:<port> [<username>] | server <port>");
        } else {
            if (args[0].equals("client")) {
                String[] address = args[1].split(":");
                String host = address[0];
                int port = Integer.parseInt(address[1]);
                String username;
                if (args.length == 3) {
                    username = args[2];
                    new Client(host, port, username);
                } else {
                    new Client(host, port);
                }
            } else if (args[0].equals("server")) {
                int port = Integer.parseInt(args[1]);
                new Server(port);
            }
        }
    }
}
