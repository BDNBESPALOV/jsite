package my.org.site.updater.model;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

public class ToUploadMainServer implements ProgressBar {

    private final String login = "f_belgorod";

    private final String password = "E666T5fZ";

    //private  String outFile = "D:\\TEMP\\patch.zip";
    private  String outFile = "A:\\temp\\patch.zip";

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

    public  void closeUpload(){
        try {
            Files.delete(new File(outFile).toPath());
            size = 0;
            valueNow = 0;
            part = 0;
            checked = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void httpPath(String path) throws IOException {
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
        byte[] byteArray;

        try (

                BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile));
        ) {

//            inputStream = connection.getInputStream();
//            File file = new File(outFile);
//
//            setValueNow(file);
//
//            Files.copy(inputStream, file.toPath());
            byteArray = new byte[8192];
            int in;
            double inSize = 0;
            while ((in = bis.read(byteArray)) != -1){
                setValueNow(in);
                setPart();
           //     System.out.println("valueNow = "+valueNow);
                bos.write(byteArray,0,in);

            }
            System.out.println("nSize = "+inSize);


        //    setChecked(new File(outFile),connection.getContentLength());
//            System.out.println("size       : " + checked);
//            System.out.println("file.length: " + file.length());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        finally {
//            try {
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }




}
