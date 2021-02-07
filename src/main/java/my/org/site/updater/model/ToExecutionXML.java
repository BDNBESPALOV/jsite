package my.org.site.updater.model;

import my.org.site.server.JClientPOJO;
import my.org.site.server.ServerController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class ToExecutionXML {
    private final Properties property = new Properties();

    private final ArrayList<String> xmlListResult = new ArrayList<>();

    public void send(){
        System.out.println("send ToExecutionXML");
        try {
            property.load(new FileInputStream("Server.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* получение сокета с сервера SPAdmin */
        HashMap<String, JClientPOJO> clientHashMap = ServerController.jClientMap;
        Socket clientSocket = clientHashMap.get(property.getProperty("SPAdmin.ip")).getClientSocket();

        try (
                /*  */
                BufferedReader result = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                /* писатель для отправки сообщений клиенту */
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            /* посылаем команду на обновление  */
            System.out.println("посылаем команду на обновление");
            out.println("UpdateXML: ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*  Заполняем лист собщениями от клиента */
            System.out.println("Заполняем лист собщениями от клиента");
            String resultLine;

            while ((resultLine = result.readLine()) != null) {
                System.out.println(resultLine);
                xmlListResult.add(resultLine);
            }
            System.out.println("...");

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getSqlListResult() {
        return xmlListResult;
    }
}
