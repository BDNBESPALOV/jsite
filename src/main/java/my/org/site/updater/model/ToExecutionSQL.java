package my.org.site.updater.model;

import my.org.site.server.JClient;
import my.org.site.server.ServerController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ToExecutionSQL {

    private HashMap<String, JClient> clientHashMap;
    private Socket clientSocket;
    private ArrayList<String> sqlListResult = new ArrayList<>();


    public void send(){

        this.clientHashMap = ServerController.jClientMap;
        this.clientSocket = clientHashMap.get("SPAdmin").getClientSocket();

            try (
                    /*  */
                    BufferedReader result = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    /* писатель для отправки сообщений клиенту */
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            )

            {

                out.println("UpdateSP: ");

                /*  Заполняем лист собщениями от клиента */
                String resultLine;
                while ((resultLine = result.readLine()) != null) {
                    sqlListResult.add(resultLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public ArrayList<String> getSqlListResult() {
        return sqlListResult;
    }

    public void setSqlListResult(String str) {
        this.sqlListResult.add(str);
    }
}
