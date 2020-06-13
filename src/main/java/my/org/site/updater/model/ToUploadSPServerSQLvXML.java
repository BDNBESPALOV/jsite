package my.org.site.updater.model;

import my.org.site.server.JClient;
import my.org.site.server.ServerController;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;

public class ToUploadSPServerSQLvXML implements ProgressBar{

    private HashMap<String, JClient> clientHashMap;
    private Socket clientSocket;
    private PrintWriter out;
    private String path;

    private int size = 0;
    private double valueNow = 0;
    private int part ;
    private boolean checked = false;

    public void setSize( String path) {
        this.size = (int) new File(path).length();
    }

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
        this.path = path;
        String fileSize = new File(path).length()+"";
        setSize(fileSize);
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
                //ответ от клиента
                BufferedReader result = new BufferedReader( new InputStreamReader(clientSocketFile.getInputStream()));
        ) {
            byteArray = new byte[8192];
            int in;
            while ((in = bis.read(byteArray)) != -1){
                setValueNow(in);
                setPart();
                System.out.println("valueNow "+valueNow+" part "+part);
                bos.write(byteArray,0,in);
            }

            /* после передачи ждем ответ от клиента */
            String resultLine;
            while ((resultLine = result.readLine()) != null) {
                /* сравниваем размеры файлов, отправленного и плученного  */
                if (resultLine.contains("SizeFile:")){
                    if (fileSize.equals(resultLine.substring(9))){
                        System.out.println("Передача файла прошла успешно.");
                    }else {
                        System.out.println("Что-то пошло не так !!!"+"\n"+"Файл недокачен...");
                    }
                }
                /* проверяем резуьтат копирования файлов патча */
                if (resultLine.contains("copying:")){
                    if (resultLine.contains("100")){
                        System.out.println("Копирование прошло успешно "+resultLine );
                    } else {
                        System.out.println("Что-то пошло не так !!! "+resultLine );
                    }
                }
            }


        } catch (IOException e){
            System.out.println(e);
        }
        /* удаляем данные по соединению передачи */
        clientHashMap.remove("AdminFile");


    }

    public  void closeUpload(){
        try {
            Files.delete(new File(path).toPath());
            size = 0;
            valueNow = 0;
            part = 0;
            checked = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
