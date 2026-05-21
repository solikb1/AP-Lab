package chatapp;

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client {
    private Socket socket; // conn to server
    private DataInputStream input; // recieve
    private DataOutputStream output;// send

    private JFrame frame; // main window
    private JTextArea chatArea; //message apper
    private JTextField messageField; //type

    public Client() { // runs when the program starts and it is automatically 
        try {
            socket = new Socket("localhost", 5000); // connect to server
            input = new DataInputStream(socket.getInputStream()); // 
            output = new DataOutputStream(socket.getOutputStream());

            loginScreen();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not connect to server.");
        }
    }

    private void loginScreen() {
        String[] options = {"LOGIN", "REGISTER"};

        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose an option",
                "Chat App",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        String command = choice == 0 ? "LOGIN" : "REGISTER";

        String username = JOptionPane.showInputDialog("Username:");
        String password = JOptionPane.showInputDialog("Password:");

        try {
            output.writeUTF(command); // send command to server
            output.writeUTF(username); 
            output.writeUTF(password); 
            output.flush(); // make sure data is sent it is loke forcefully

            String response = input.readUTF(); //  wait for response from server

            if (response.contains("SUCCESS")) { 
                createChatWindow(username); 
                listenForMessages();
            } else {
                JOptionPane.showMessageDialog(null, "Login/Register failed.");
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    private void createChatWindow(String username) {
        frame = new JFrame("Chat - " + username); 
        chatArea = new JTextArea(); 
        chatArea.setEditable(false); 

        messageField = new JTextField();

        JButton sendButton = new JButton("Send");
        JButton fileButton = new JButton("Send File");

        sendButton.addActionListener(e -> sendMessage());
        fileButton.addActionListener(e -> sendFile());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(fileButton, BorderLayout.WEST);
        panel.add(messageField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER); 
        frame.add(panel, BorderLayout.SOUTH); 

        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void sendMessage() {
        try {
            String message = messageField.getText();

            if (!message.isEmpty()) {
                output.writeUTF("TEXT");
                output.writeUTF(message);
                output.flush();

                chatArea.append("Me: " + message + "\n");
                messageField.setText("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendFile() {
        try {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();

                FileInputStream fileInput = new FileInputStream(file);
                byte[] fileBytes = fileInput.readAllBytes();
                fileInput.close();

                output.writeUTF("FILE");
                output.writeUTF(file.getName());
                output.writeLong(fileBytes.length);
                output.write(fileBytes);
                output.flush();

                chatArea.append("Me sent file: " + file.getName() + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listenForMessages() {
        new Thread(() -> {// run in background so it does not freeze the UI  
            try {
                while (true) {
                    String type = input.readUTF();

                    if (type.equals("TEXT")) {
                        String message = input.readUTF();
                        chatArea.append(message + "\n");
                    }

                    if (type.equals("FILE")) {
                        String sender = input.readUTF();
                        String fileName = input.readUTF();
                        long fileSize = input.readLong();

                        byte[] fileBytes = new byte[(int) fileSize];
                        input.readFully(fileBytes);

                        File folder = new File("downloads");
                        if (!folder.exists()) {
                            folder.mkdir();
                        }

                        File receivedFile = new File(folder, fileName);
                        FileOutputStream fileOutput = new FileOutputStream(receivedFile);
                        fileOutput.write(fileBytes);
                        fileOutput.close();

                        chatArea.append(sender + " sent a file: " + fileName + "\n");
                        chatArea.append("Saved in downloads/" + fileName + "\n");
                    }
                }

            } catch (Exception e) {
                chatArea.append("Disconnected from server.\n");
            }
        }).start();
    }

    public static void main(String[] args) {
        new Client();
    }
}