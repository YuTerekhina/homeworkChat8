package ru_gb.Server;

public interface AuthService {
    String getNickByLoginAndPassword(String login, String password);
}