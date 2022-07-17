
import java.io.*;
import java.net.*;
import java.util.*;

public class UserTh extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter writer;
 
    public UserTh(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }
 
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
 
            printUsers();
 
            String user = reader.readLine();
            server.addUser(user);
 
            String serverMessage = "Podłączono nowego użytkownika: " + user;
            server.broadcast(serverMessage, this);
 
            String clientMessage;
 
            do {
                clientMessage = reader.readLine();
                serverMessage = "~" + user + ": " + clientMessage;
                server.broadcast(serverMessage, this);
 
            } while (!clientMessage.equals("koniec"));
 
            server.removeUser(user, this);
            socket.close();
 
            serverMessage = user + " opuścił czat.";
            server.broadcast(serverMessage, this);
 
        } catch (IOException ex) {
            System.out.println("Błąd: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 

    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Podłączeni użytkownicy: " + server.getUsers());
        } else {
            writer.println("Brak innych użytkowników");
        }
    }
 

    void sendMessage(String message) {
        writer.println(message);
    }
}