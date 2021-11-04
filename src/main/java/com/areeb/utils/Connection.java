package com.areeb.utils;

import java.io.*;
import java.net.Socket;

public class Connection {
    private Socket connection;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean isDisconnected = false;

    public Connection(Socket connection) throws IOException {
        this.connection = connection;
        this.reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        this.writer = new PrintWriter(connection.getOutputStream());
    }

    public boolean isAlive() {
        return !isDisconnected;
    }

    public void writeLine(String line) {
        write(line + "\n");
    }

    public void write(String line) {
        writer.write(line);
        writer.flush();
    }

    public String readLine() throws IOException {
        String line = reader.readLine();

        if (line == null) {
            isDisconnected = true;
            throw new IOException("Client disconnected");
        }

        return line;
    }

    public void close() throws IOException {
        connection.close();
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public OutputStream getOutputStream() throws IOException {
        return connection.getOutputStream();
    }
}
