package com.areeb.server;

import com.areeb.tasks.Authenticator;
import com.areeb.tasks.ShellRunner;
import com.areeb.tasks.TelnetTask;
import com.areeb.tasks.Welcome;
import com.areeb.utils.Connection;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class SocketHandler extends Thread {
    Connection connection;
    Socket socket;

    SocketHandler(Socket connection) throws IOException {
        this.connection = new Connection(connection);
        this.socket = connection;
        System.out.println("New connection: " + connection.getInetAddress());
    }

    public void handle() throws IOException {
        List<TelnetTask> handlers = List.of(new Welcome(), new Authenticator(), new ShellRunner());
        handlers.forEach((TelnetTask task) -> task.run(connection));
    }

    @Override
    public void run() {
        try {
            this.handle();
            System.out.println("Connection closing: " + socket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
