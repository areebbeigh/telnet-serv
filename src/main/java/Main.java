import com.areeb.server.ConnectionManager;
import com.areeb.server.TelnetServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        TelnetServer server = new TelnetServer(new ConnectionManager(5));
        server.listen(9000);
    }
}
