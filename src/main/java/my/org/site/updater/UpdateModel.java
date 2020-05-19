package my.org.site.updater;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.file.Files;

public class UpdateModel {
    private String login = "f_belgorod";
    private String password = "E666T5fZ";
    private static int size = 0;
    private static double valueNow = 0;
    private static int part = 0;

    public static double getPart() {
        return part;
    }

    public static int getSize() {
        return size;
    }

    public static double getValueNow() {
        return valueNow;
    }

    public void parsePath(String path){
        InputStream inputStream = null;
        if(path.contains("http")){
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
                File file = new File("A:\\temp\\patch.zip");
                setValueNow(file);
                Files.copy(inputStream, file.toPath());



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
        

  }

    private static void setSize(int size) {
        double s = size/1024;
        UpdateModel.size = (int) (s/1024);
    }

    private static void setValueNow(File file) {
        new Thread(
                () -> {
                    do {
                        if(file.exists()){
                            try {
                                UpdateModel.valueNow = Files.readAllBytes(file.toPath()).length/1024;
                                UpdateModel.valueNow = UpdateModel.valueNow/1024;
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
        UpdateModel.part = (int) (m * 100);
    }


}
