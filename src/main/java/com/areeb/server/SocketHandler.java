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

    SocketHandler(Socket connection) throws IOException {
        this.connection = new Connection(connection);
    }

    public void handle() throws IOException {
        List<TelnetTask> handlers = List.of(new Welcome(), new Authenticator(), new ShellRunner());
        handlers.forEach((TelnetTask task) -> task.run(connection));
    }

    @Override
    public void run() {
        try {
            this.handle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
