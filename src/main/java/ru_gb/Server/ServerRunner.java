package ru_gb.Server;


public class ServerRunner {
    public static void main(String[] args) {
        final ChatServer chatServer = new ChatServer();
        chatServer.start();
    }
}