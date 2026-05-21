package chatapp;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            String command = input.readUTF();
            username = input.readUTF();
            String password = input.readUTF();

            if (command.equals("REGISTER")) {
                if (Database.registerUser(username, password)) {
                    output.writeUTF("REGISTER_SUCCESS");
                } else {
                    output.writeUTF("REGISTER_FAILED");
                    socket.close();
                    return;
                }
            }

            if (command.equals("LOGIN")) {
                if (Database.loginUser(username, password)) {
                    output.writeUTF("LOGIN_SUCCESS");
                } else {
                    output.writeUTF("LOGIN_FAILED");
                    socket.close();
                    return;
                }
            }

            Server.broadcastText(username + " joined the chat.", this);

            while (true) {
                String type = input.readUTF();

                if (type.equals("TEXT")) {
                    String message = input.readUTF();
                    Database.saveMessage(username, message);
                    Server.broadcastText(username + ": " + message, this);
                }

                if (type.equals("FILE")) {
                    String fileName = input.readUTF();
                    long fileSize = input.readLong();

                    byte[] fileBytes = new byte[(int) fileSize];
                    input.readFully(fileBytes);

                    Server.broadcastFile(username, fileName, fileBytes, this);
                }
            }

        } catch (Exception e) {
            System.out.println(username + " disconnected.");
            Server.clients.remove(this);
        }
    }

    public void sendText(String message) {
        try {
            output.writeUTF("TEXT");
            output.writeUTF(message);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFile(String sender, String fileName, byte[] fileBytes) {
        try {
            output.writeUTF("FILE");
            output.writeUTF(sender);
            output.writeUTF(fileName);
            output.writeLong(fileBytes.length);
            output.write(fileBytes);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}