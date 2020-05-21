package my.org.site.updater.model;

import my.org.site.server.JClient;
import my.org.site.server.ServerController;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ToUploadSP {

    private  HashMap<String, JClient> clientHashMap = ServerController.jClientMap;
    private  Socket clientSocket = clientHashMap.get("SPAdmin").getClientSocket();
    private  PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
    private  long size;
    private String path;


    ToUploadSP (String path) throws IOException {
        size = new File(path).length();
        this.path = path;
    }


    public void uploadFile(){
        out.println("accept file");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Socket clientSocketFile = clientHashMap.get("AdminFile").getClientSocket();

        try(
                BufferedInputStream  bis = new BufferedInputStream(new FileInputStream(path));
                BufferedOutputStream bos = new BufferedOutputStream(clientSocketFile.getOutputStream()))
        {
            byte []  byteArray = new byte[8192];
            int in;
            while ((in = bis.read(byteArray)) != -1){
                bos.write(byteArray,0,in);
            }

        }catch (IOException e){

        }



    }

}
