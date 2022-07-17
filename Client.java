

import java.net.*;
import java.io.*;
 
public class Client {
    private String host;
    private int port;
    private String user;
 
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }
 
    public void execute() {
        try {
            Socket socket = new Socket(host, port);
 
            System.out.println("Podłączono do serwera");
 
            new Read(socket, this).start();
            new Write(socket, this).start();
 
        } catch (UnknownHostException ex) {
            System.out.println("Błąd: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Błąd: " + ex.getMessage());
        }
 
    }
 
    void setUser(String user) {
        this.user = user;
    }
 
    String getUser() {
        return this.user;
    }
 
 
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Poprawna składnia: java Client <adres serwera> <numer portu>");
            System.exit(0);
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
 
        Client client = new Client(host, port);
        client.execute();
    }
}