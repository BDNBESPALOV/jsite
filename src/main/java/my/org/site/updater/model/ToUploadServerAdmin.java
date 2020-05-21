package my.org.site.updater.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.file.Files;

public class ToUploadServerAdmin {

    private final String login = "f_belgorod";

    private final String password = "E666T5fZ";

    private static String outFile = "D:\\TEMP\\patch.zip";  //private final String outFile = "A:\\temp\\patch.zip";

    private static int size = 0;

    private static double valueNow = 0;

    private static int part = 0;

    private InputStream inputStream = null;

    private static boolean checked = false;


    public static String getOutFile() {
        return outFile;
    }

    public static boolean getChecked() {
        return checked;
    }

    public static double getPart() {
        return part;
    }

    public static int getSize() {
        return size;
    }

    public static double getValueNow() {
        return valueNow;
    }



    public void closeUpload(){
        try {
            inputStream.close();
            Files.delete(new File(outFile).toPath());
            size = 0;
            valueNow = 0;
            part = 0;
            checked = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void httpPath(String path){

        try {
            URL url = new URL(path);
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(login, password.toCharArray());
                }
            });

            URLConnection connection = url.openConnection();

            setSize(connection.getContentLength());

            System.out.println(connection.getContentLength());
            inputStream = connection.getInputStream();
            File file = new File(outFile);

            setValueNow(file);

            Files.copy(inputStream, file.toPath());
            setChecked(file,connection.getContentLength());
            System.out.println("size       : " + checked);
            System.out.println("file.length: " + file.length());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setSize(int size) {
        double s = size / 1024;
        ToUploadServerAdmin.size = (int) (s/1024);
    }

    private static void setValueNow(File file) {
        new Thread(
                () -> {
                    do {
                        if(file.exists()){
                            try {
                                ToUploadServerAdmin.valueNow = Files.readAllBytes(file.toPath()).length/1024;
                                ToUploadServerAdmin.valueNow = ToUploadServerAdmin.valueNow/1024;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                           // part = (valueNow/size) * 100;
                            setPart(getSize(),valueNow);
                            System.out.println("file.length " + valueNow + " / " + getSize() + " size "+getPart()+" %");
                        }
                    }
                    while (size > valueNow) ;
                }).start();
    }

    private static void setPart(int size, double valueNow) {
        double m = valueNow / size;
        ToUploadServerAdmin.part = (int) (m * 100);
    }

    public void setChecked(File file, int connectionSize ) {
        if(file.length() == connectionSize){
            this.checked = true;
        } else {
            this.checked = false;
        }
    }


}
