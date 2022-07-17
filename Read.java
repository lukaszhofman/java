

import java.io.*;
import java.net.*;
 

public class Read extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private Client client;
 
    
    public Read(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
 
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Błąd: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                System.out.println("\n" + response);
 

                if (client.getUser() != null) {
                    System.out.print("~" + client.getUser() + ": ");
                }
            } catch (IOException ex) {
                System.out.println("Błąd: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}