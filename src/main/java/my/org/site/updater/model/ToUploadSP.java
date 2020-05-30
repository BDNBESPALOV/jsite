package my.org.site.updater.model;

import my.org.site.server.JClient;
import my.org.site.server.ServerController;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;

public class ToUploadSP implements ProgressBar{

    private HashMap<String, JClient> clientHashMap;
    private Socket clientSocket;
    private PrintWriter out;
    private String path;

    public void setSize( String path) {
        this.size = (int) new File(path).length();
    }

    private int size = 0;
    private double valueNow = 0;
    private int part ;
    private boolean checked = false;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = (int) ProgressBar.roundToMegabytes(size);
    }

    public double getValueNow() {
        return valueNow;
    }

    public void setValueNow(int valueNow) {
        this.valueNow += ProgressBar.roundToMegabytes(valueNow);
    }

    public int getPart() {
        return part;
    }

    public void setPart() {
        this.part = ProgressBar.percentageOfProgress(getSize(),getValueNow());
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public void uploadFile(String path)  {
        setSize((int) new File(path).length());
        System.out.println("path.length()== "+new File(path).length());

        this.clientHashMap = ServerController.jClientMap;
        this.clientSocket = clientHashMap.get("SPAdmin").getClientSocket();


        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }


        out.println("accept file");

        //из-за  лени !!!!!!!!
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //из-за  лени !!!!!!!!

        byte[] byteArray;
        Socket clientSocketFile = clientHashMap.get("AdminFile").getClientSocket();
        try(
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
                BufferedOutputStream bos = new BufferedOutputStream(clientSocketFile.getOutputStream());
        ) {
            byteArray = new byte[8192];
            int in;
            while ((in = bis.read(byteArray)) != -1){
                setValueNow(in);
                setPart();
                System.out.println("valueNow "+valueNow+" part "+part);
                bos.write(byteArray,0,in);
            }

        } catch (IOException e){
            System.out.println(e);
        }

        clientHashMap.remove("AdminFile");


    }




}
