package my.org.site.updater.model;

import my.org.site.server.JClient;
import my.org.site.server.ServerController;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ToUploadSP {

    private  HashMap<String, JClient> clientHashMap = ServerController.jClientMap;
    private  Socket clientSocket = clientHashMap.get("SPAdmin").getClientSocket();
    private  PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
    private  long size;


    ToUploadSP (String path) throws IOException {
        size = new File(path).length();
        out.print("accept file");

    }

}
