package my.org.site.updater.model;

import my.org.site.updater.PathGZ;

import java.io.IOException;

public class Uploading {

    public void start(PathGZ pathGZ,
                      ToUploadMainServer updateModel, ToUploadSPServerSQLvXML toUploadSP,
    ToExecutionSQL toExecutionSQL
    ){

        String path = pathGZ.getPath();
        Thread threadUploadHTTP = null;
        Thread threadToServer = null;
        Thread threadSQL = null;

        if(pathGZ.getPath() != null) {
            /* Загрузка патча на сервер контроллера */
            if (path.contains("http")) {
                threadUploadHTTP = new Thread(() -> {
                    try {
                        updateModel.httpPath(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                threadUploadHTTP.start();

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
            }
            /* Загрузка патча на сервер обновления */
            if (threadUploadHTTP != null) {

                Thread finalThread = threadUploadHTTP;
                threadToServer = new Thread(() -> {
                    try {
                        finalThread.join();
                        toUploadSP.uploadFile(pathGZ.getPath());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                threadToServer.start();
            } else {

                Thread thread2 = new Thread(() -> {
                    toUploadSP.uploadFile(pathGZ.getPath());
                });
                thread2.start();
            }

        }

        /*ЗДЕСЬ НУЖНО СДЕЛАТЬ ПРОВЕРКУ ПО УСПЕШНОСТИ ВЫПОЛНЕНИЯ ПРЕДЫДУЩИХ ПУНКТОВ */
        /*Установка SQL*/
        //if (threadUploadHTTP != null && threadToServer !=null) {


//            Thread finalThreadToServer = threadToServer;
//            Thread finalThreadUploadHTTP = threadUploadHTTP;
//            threadSQL = new Thread(() -> {
//                try {
//                    finalThreadToServer.join();
//                    finalThreadUploadHTTP.join();
 //------                   toExecutionSQL.send();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
//            threadSQL.start();
//        } else {
//
//            Thread thread2 = new Thread(() -> {
//             //   toExecutionSQL.send();
//            });
//            thread2.start();
//        }




    }
}
