package chatapp;

import java.net.*;
import java.util.*;

public class Server {
    public static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        Database.init();

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started on port 5000...");

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);
                new Thread(handler).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void broadcastText(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendText(message);
            }
        }
    }

    public static void broadcastFile(String senderName, String fileName, byte[] fileBytes, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendFile(senderName, fileName, fileBytes);
            }
        }
    }
}