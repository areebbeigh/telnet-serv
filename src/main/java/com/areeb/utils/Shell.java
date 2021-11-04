package com.areeb.utils;

import java.io.*;

public class Shell {
    private BufferedReader out;
    private PrintWriter in;
    private Process shell;
    private boolean isAlive;

    public void start() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("sh");
        pb.directory(new File(System.getProperty("user.home")));
        shell = pb.start();
        isAlive = true;
        out = new BufferedReader(new InputStreamReader(shell.getInputStream()));
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

    public String readOutput() throws IOException {
        StringBuilder output = new StringBuilder();
        while (out.ready()) {
            output.append(out.readLine());
            output.append("\n");
        }
        return output.toString().trim();
    }
}
