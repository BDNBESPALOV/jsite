package my.org.site.updater.model;

import my.org.site.updater.PathGZ;

import java.io.IOException;

public class Uploading {

    public void start(PathGZ pathGZ, ToUploadServerAdmin updateModel){

        String path = pathGZ.getPath();
        Thread thread = null;

        /* Загрузка патча на сервер контроллера */
        if(path.contains("http")){
           thread = new Thread(() ->{
                updateModel.httpPath(path);
            });
           thread.start();
        }
        /* Загрузка патча на сервер обновления */
        if (thread != null){

            Thread finalThread = thread;
            Thread thread2 =  new Thread(() ->{
                try {
                    finalThread.join();
                    new ToUploadSP(ToUploadServerAdmin.getOutFile()).uploadFile();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread2.start();

        }





    }
}
