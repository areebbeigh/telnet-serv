package com.areeb.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TelnetServer {
    private final ConnectionManager connectionManager;

    public TelnetServer(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void listen(int port) throws IOException {
        ServerSocket socket = new ServerSocket(port);
        //noinspection InfiniteLoopStatement
        while (true) {
            Socket clientSocket = socket.accept();
            handle(clientSocket);
        }
    }

    private void handle(Socket connection) throws IOException {
        connectionManager.handle(connection);
    }
}
