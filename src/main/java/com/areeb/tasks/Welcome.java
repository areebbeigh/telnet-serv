package com.areeb.tasks;

import com.areeb.utils.Connection;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class Welcome implements TelnetTask {
    @Override
    public void run(Connection connection) {
        String hostName = "Unknown Host";
        String os = System.getProperty("os.name");

        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        connection.writeLine("\n" + new Date());
        connection.writeLine(String.format("Welcome to %s's telnet server!", hostName));
        connection.writeLine(String.format("Running %s\n\n", os));
    }
}
