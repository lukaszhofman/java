
import java.io.*;
import java.net.*;
import java.util.*;
 

public class Server {
    private int port;
    private Set<String> users = new HashSet<>();
    private Set<UserTh> userThs = new HashSet<>();
 
    public Server(int port) {
        this.port = port;
    }
 
    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            System.out.println("Serwer działa na porcie " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Dołączył nowy użytkownik");
 
                UserTh newUser = new UserTh(socket, this);
                userThs.add(newUser);
                newUser.start();
 
            }
 
        } catch (IOException ex) {
            System.out.println("Błąd: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Poprawna składnia: java Server <numer portu>");
            System.exit(0);
        }
 
        int port = Integer.parseInt(args[0]);
 
        Server server = new Server(port);
        server.execute();
    }
 

    void broadcast(String message, UserTh excludeUser) {
        for (UserTh aUser : userThs) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }
 

    void addUser(String user) {
        users.add(user);
    }
 

    void removeUser(String user, UserTh aUser) {
        boolean removed = users.remove(user);
        if (removed) {
            userThs.remove(aUser);
            System.out.println("Użytkownik " + user + " opuścił czat");
        }
    }
 
    Set<String> getUsers() {
        return this.users;
    }
 
    
    boolean hasUsers() {
        return !this.users.isEmpty();
    }
}