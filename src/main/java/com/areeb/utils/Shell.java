package com.areeb.utils;

import java.io.*;
import java.util.stream.Stream;

public class Shell {
    private InputStream out;
    private PrintWriter in;
    private Process shell;
    private boolean isAlive;

    public void start() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("sh");
        pb.directory(new File(System.getProperty("user.home")));
        shell = pb.start();
        isAlive = true;
        out = shell.getInputStream();
        in = new PrintWriter(shell.getOutputStream());

        new Thread(() -> {
            try {
                shell.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isAlive = false;
        }).start();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void run(String command) throws InterruptedException {
        in.println(command);
        in.flush();
        Thread.sleep(500);
    }

    public String getOutput() throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        readOutput(outStream);
        return outStream.toString();
    }

    public void readOutput(OutputStream toStream) throws IOException {
        while (out.available() > 0) {
            byte[] buff = new byte[1024];
            out.read(buff, 0, 1024);
            toStream.write(buff);
        }
    }
}
