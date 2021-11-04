package com.areeb.tasks;

import com.areeb.utils.Connection;
import com.areeb.utils.Shell;

import java.io.IOException;

public class ShellRunner implements TelnetTask {
    private Shell shell;
    private Connection connection;

    @Override
    public void run(Connection connection) {
        shell = new Shell();
        this.connection = connection;

        try {
            shell.start();
        } catch (IOException e) {
            connection.writeLine("Failed to start shell. Closing connection.");
            e.printStackTrace(connection.getWriter());
            connection.getWriter().flush();
            try {
                connection.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

        try {
            readCommand();
        } catch (IOException e) {
            e.printStackTrace(connection.getWriter());
            connection.getWriter().flush();
        }
    }

    private String getPwd() throws IOException {
        try {
            shell.run("pwd");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return shell.readOutput().trim();
    }

    private void printPrompt() throws IOException {
        connection.write(String.format("%s$ ", getPwd()));
    }

    private void readCommand() throws IOException {
        while (connection.isAlive() && shell.isAlive()) {
            printPrompt();
            String command = connection.readLine();
            try {
                shell.run(command);
            } catch (InterruptedException e) {
                e.printStackTrace(connection.getWriter());
                connection.getWriter().flush();
            }
            connection.writeLine(shell.readOutput());
        }

        if (!shell.isAlive()) {
            connection.writeLine("Shell exited.");
            connection.close();
        }
    }
}
