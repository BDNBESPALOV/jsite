package my.org.site.updater.model;

import my.org.site.MainApplication;
import my.org.site.md5.ApacheMd5;
import my.org.site.server.JClient;
import my.org.site.server.ServerController;
import my.org.site.updater.UpdaterController;
import org.slf4j.Logger;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class ToUploadSPServerSQLvXML implements ProgressBar{

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ToUploadSPServerSQLvXML.class);
    private HashMap<String, JClient> clientHashMap;
    private Socket clientSocket;
    private PrintWriter out;
    private String path;

    private int size = 0;
    private double valueNow = 0;
    private int part ;
    private boolean checked = false;



//    public void setSize( String path) {
//        this.size = (int) new File(path).length();
//    }

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

    public void setChecked(String md5) {

        /* сравниваем MD5 файлов, отправленного и плученного  */
        if (ApacheMd5.md5(path).equals(md5)){
            this.checked = true;
            log.info("Передача файла прошла успешно.");
        }else {
            this.checked = false;
            log.info("отсправлен с MD5: "+ApacheMd5.md5(path));
            log.info("   получен с MD5: "+md5);
            log.info("Что-то пошло не так !!!"+"\n"+"Файл недокачен...");
        }
    }



    public void uploadFile(String path) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        this.path = path;
        String fileSize = new File(path).length()+"";
        setSize((int) new File(path).length());

        log.info("Передаваемый файл: " + path);
        log.info("Размер передоваемого файла: "+new File(path).length());


        /* извлекаем ссылку на соединение с сервером обновления */
        this.clientHashMap = ServerController.jClientMap;
        this.clientSocket = clientHashMap.get("SPAdmin").getClientSocket();

        /* получаем стрим для записи  */
        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* отправляем сообщение серверу обновления, для открытия нового соединения передачи  */
        out.println("accept file");

        /* устанявливаю таймаут на две секунды (временно потом переделать) */
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /* получаем сокит для передачи файла  */
        Socket clientSocketFile = clientHashMap.get("AdminFile").getClientSocket();

        log.info(clientSocketFile.isBound()+" <-bound "+clientSocketFile.isClosed()+" <-close "
                +clientSocketFile.isConnected()+" <-connect "+clientSocketFile.isInputShutdown()+" <-isShutdown");
        try(
                /*для отправки размера файла */
                DataOutputStream dataOutputStream = new DataOutputStream(clientSocketFile.getOutputStream());

                /* стримы для считывания и передачи файла */
                // -MD5 BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
                InputStream is = Files.newInputStream(Paths.get(path));
                DigestInputStream dis = new DigestInputStream(is, md);

                BufferedOutputStream bos = new BufferedOutputStream(clientSocketFile.getOutputStream());

                /* для отправки информационных сообщений серверу обновляения  */
                BufferedReader result = new BufferedReader( new InputStreamReader(clientSocketFile.getInputStream()));
        ) {

            dataOutputStream.writeInt((int) new File(path).length());
            /* логика передачи файла */
            byte[] byteArray = new byte[8192];
            int in;
            while ((in = dis.read(byteArray)) != -1){
                bos.write(byteArray,0,in);
                setValueNow(in);
                setPart();
                log.info("valueNow "+valueNow+" part "+part+" in "+in);
                bos.flush();
            }
//            dis.close();
//            bos.close();
            log.info(clientSocketFile.isBound()+" <-bound "+clientSocketFile.isClosed()+" <-close "
                    +clientSocketFile.isConnected()+" <-connect "+clientSocketFile.isInputShutdown()+" <-isShutdown");

            log.info("Выход из цикла ");


            /* после передачи ждем ответ от клиента */
            String resultLine;
            log.info("MD5:"+ApacheMd5.md5(path));
            log.info(clientSocketFile.isBound()+" <-bound "+clientSocketFile.isClosed()+" <-close "
                    +clientSocketFile.isConnected()+" <-connect "+clientSocketFile.isInputShutdown()+" <-isShutdown");
            setChecked(ServerController.md5file);

            while ((resultLine = result.readLine()) != null) {
                log.info("resultLine: "+resultLine);
//                /* сравниваем MD5 файлов, отправленного и плученного  */
//                if (resultLine.contains("MD5:")){
//                    if (ApacheMd5.md5(path).equals(resultLine.substring(4))){
//                        setChecked(true);
//                        log.info("Передача файла прошла успешно.");
//                    }else {
//                        log.info("Что-то пошло не так !!!"+"\n"+"Файл недокачен...");
//                    }
//                }
//                /* проверяем резуьтат копирования файлов патча */
//                if (resultLine.contains("copying:")){
//                    if (resultLine.contains("100")){
//                        log.info("Копирование прошло успешно "+resultLine );
//                    } else {
//                        log.info("Что-то пошло не так !!! "+resultLine );
//                    }
//                }
            }


        } catch (IOException e){
            log.info(String.valueOf(e));
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
