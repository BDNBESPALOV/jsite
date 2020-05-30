package my.org.site.updater.model;

import my.org.site.updater.PathGZ;

import java.io.IOException;

public class Uploading {

    public void start(PathGZ pathGZ, ToUploadServerAdmin updateModel, ToUploadSP toUploadSP){

        String path = pathGZ.getPath();
        Thread thread = null;

        /* Загрузка патча на сервер контроллера */
        if(path.contains("http")){
           thread = new Thread(() ->{
               try {
                   updateModel.httpPath(path);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           });
           thread.start();

//            /* Загрузка патча на сервер обновления */
//            if (thread != null){
//
//                Thread finalThread = thread;
//                Thread thread2 =  new Thread(() ->{
//                    try {
//                        finalThread.join();
//                        toUploadSP.uploadFile(updateModel.getOutFile());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                });
//                thread2.start();
//            }
        } else {
            /* Загрузка патча на сервер обновления */
            if (thread != null) {

                Thread finalThread = thread;
                Thread thread2 = new Thread(() -> {
                    try {
                        finalThread.join();
                        toUploadSP.uploadFile(pathGZ.getPath());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                thread2.start();
            } else {

                Thread thread2 = new Thread(() -> {
                    toUploadSP.uploadFile(pathGZ.getPath());
                });
                thread2.start();
            }

        }




    }
}
