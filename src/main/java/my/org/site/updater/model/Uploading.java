package my.org.site.updater.model;

import my.org.site.updater.PathGZ;

public class Uploading {

    public void start(PathGZ pathGZ, ToUploadServerAdmin updateModel){

        String path = pathGZ.getPath();

        /* Загрузка патча на сервер контроллера */
        if(path.contains("http")){
            new Thread(() ->{
                updateModel.httpPath(path);
            }).start();
        }
        /* Загрузка патча на сервер обновления */




    }
}
