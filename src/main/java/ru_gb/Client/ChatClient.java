package ru_gb.Client;

import ru_gb.ClientController;
import ru_gb.Command;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import static ru_gb.Command.*;


public class ChatClient {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ClientController controller;

    public ChatClient(ClientController controller) {
        this.controller = controller;
        openConnection();
    }

    private void openConnection() {
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {

                    while (true) {
                        final String authMessage = in.readUTF();
                        if (getCommandByText(authMessage) == AUTHOK) {
                            final String nick = authMessage.split(" ")[1];
                            controller.addMessage("Успешная авторизация под ником: " + nick);
                            controller.setAuth(true);
                            break;
                        }
                    }
                    while (true) {
                        final String message = in.readUTF();
                        if (Command.isCommand(message)) {
                            final Command command = getCommandByText(message);
                            if (command == END) {
                                controller.setAuth(false);
                                break;
                            }
                            if (getCommandByText(message) == CLIENTS) {
                                final String[] clients = message.replace(CLIENTS
                                        .getCommand() + " ", " ").split(" ");
                                controller.updateClientsList(clients);
                            }
                        }
                        controller.addMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}