

import java.io.*;
import java.net.*;
 

public class Write extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private Client client;
 
    public Write(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
 
        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Błąd: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
 
        Console console = System.console();
 
        String user = console.readLine("\nWitaj na czacie. W celu wyjscia wpisz 'koniec'. Podaj nick: ");
        client.setUser(user);
        writer.println(user);
 
        String text;
 
        do {
            text = console.readLine("~" + user + ": ");
            writer.println(text);
 
        } while (!text.equals("koniec"));
 
        try {
            socket.close();
        } catch (IOException ex) {
 
            System.out.println("Błąd: " + ex.getMessage());
        }
    }
}