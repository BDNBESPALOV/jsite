package my.org.site.updater.model;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

public class ToUploadMainServer implements ProgressBar {

    private final String login = "f_belgorod";

    private final String password = "E666T5fZ";

    //private  String outFile = "D:\\TEMP\\patch.zip";
    private  String outFile1 = "A:\\temp\\patch.zip";

    /* запоминаем длину пути */
    private int beforeLengthFile = outFile1.length();


    private BufferedInputStream bis = null;
    private BufferedOutputStream bos = null;

    private boolean callClosed = false;


    private int size = 0;
    private double valueNow = 0;
    private int part ;
    private boolean checked = false;

    private long contentLength = 0;

    /* добавляем окончание waitLoading, чтоб небыло соблазна открыть файл раньше времени  */
    private  String outFile = outFile1+".waitLoading";


    public String getOutFile() {
        return outFile;
    }

    public void setOutFile(String outFile) {
        this.outFile = outFile;
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

    public void setChecked(File file) {

        /* сравниваем размеры полученного файла */
        System.out.println("сравниваем размеры, полученный файл   "+file.length());
        System.out.println("сравниваем размеры, отправляемый файл "+contentLength);
        if (new File(outFile).length() == contentLength ){
            /* убираем окончание .waitLoading */
            if (file.renameTo(new File(outFile.substring(0, beforeLengthFile)))){
                System.out.println("убираем окончание, успех ! ");
                setOutFile(this.outFile.substring(0, beforeLengthFile));
                this.checked = true;
            } else {
                System.out.println("Что то пошло не так, переиновать полученный чайл неполучилось !!! ");
            }
        }
    }
    /* Прирываем загрузку и удаляем файл */
    public  void closeUpload(){
        try {
            this.callClosed = true;
            this.bos.close();
            this.bis.close();
            Files.delete(new File(this.outFile).toPath());
            this.size = 0;
            this.valueNow = 0;
            this.part = 0;
            this.checked = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /* Проверка корректности введенноного URL и наличия файла */
    public boolean fileExists(String path){
        if (path.contains("http")) {
            try {
                URL url = new URL(path);
                Authenticator.setDefault(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, password.toCharArray());
                    }
                });

                URLConnection connection = url.openConnection();
                connection.getContent();
                return true;

            } catch (FileNotFoundException e) {
                System.out.println("Неудалось найти указанный файл, возможно он удален !!!");
                return false;
            } catch (MalformedURLException e) {
                System.out.println("Не корректный URL!!!");
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } else {
            return new File(path).exists();
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

        System.out.println("Размер файла на сервере разработчика: "+connection.getContentLength());
        contentLength = connection.getContentLength();
        byte[] byteArray;

        try {
            bis = new BufferedInputStream(connection.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(outFile));

            byteArray = new byte[8192];
            int in;
            double inSize = 0;
            callClosed = false;
            while ((in = bis.read(byteArray)) != -1 && !callClosed){

           //     System.out.println("valueNow = "+valueNow);
                bos.write(byteArray,0,in);
                setValueNow(in);
                setPart();

            }

            /* Закрываем потоки  */
            try {
                bos.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setChecked(new File(outFile));



        } catch (FileNotFoundException e) {
            System.out.println("Нет такого файла!!!");
            return;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        } finally {
            bis.close();
            bos.close();

        }

    }


}
