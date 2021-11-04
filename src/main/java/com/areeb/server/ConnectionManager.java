package com.areeb.server;

import java.io.IOException;
import java.net.Socket;

public class ConnectionManager {
    private int threadCount = 0;
    private final int maxThreadCount;

    public ConnectionManager(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    public void handle(Socket connection) throws IOException {
        if (threadCount > maxThreadCount) connection.close();

        SocketHandler socketHandler = new SocketHandler(connection);
        socketHandler.start();
        threadCount++;

        new Thread(() -> {
            try {
                socketHandler.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            decrementThreadCount();
        }).start();
    }

    public synchronized void decrementThreadCount() {
        threadCount--;
    }
}
