package my.org.site.updater;

import my.org.site.updater.model.ToExecutionSQL;
import my.org.site.updater.model.ToExecutionXML;
import my.org.site.updater.model.ToUploadSPServerSQLvXML;
import my.org.site.updater.model.ToUploadMainServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

@Controller
public class UpdaterController {

    Thread threadExecuteSQL;

    /* подключение файла Server.properties  */
    private Properties property = new Properties();

    {
        try {
            property.load(new FileInputStream("Server.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* главный сервер */
    private ToUploadMainServer updateModel = new ToUploadMainServer();
    /* сервер обновления */
    public ToUploadSPServerSQLvXML toUploadSPServerSQLvXML = new ToUploadSPServerSQLvXML();
    /* запуск выполнеия SQL скриптов */
    private ToExecutionSQL toExecutionSQL = new ToExecutionSQL();
    /* запуск выполнеия XML скриптов */
    private ToExecutionXML toExecutionXML = new ToExecutionXML();

    /* дериктория загруженного патча */
    private final String PATCH_DIR = property.getProperty("directory.downloaded.patch");


//    @RequestMapping(value = { "/center" }, method = RequestMethod.GET)
//    public String te(Model model){
//        model.addAttribute("valueNow", updateModel.getValueNow());
//        return "updater";
//    }

    @RequestMapping(value = {"/updater"}, method = RequestMethod.GET)
    public String updater(Model model) {
        model.addAttribute("pathGZ", new PathGZ());

        model.addAttribute("size", updateModel.getSize());
        model.addAttribute("valueNow", updateModel.getValueNow());
        model.addAttribute("part", updateModel.getPart());
        model.addAttribute("checked", updateModel.ProgressBarChecked());
        /*	Загрузка патча на сервер обновления */
        model.addAttribute("sizeToUploadSP", toUploadSPServerSQLvXML.getSize());
        model.addAttribute("valueNowToUploadSP", toUploadSPServerSQLvXML.getValueNow());
        model.addAttribute("partToUploadSP", toUploadSPServerSQLvXML.getPart());
        model.addAttribute("checkedToUploadSP", toUploadSPServerSQLvXML.ProgressBarChecked());
        /*	Установка SQL */
        model.addAttribute("toExecutionSQL", toExecutionSQL.getSqlListResult());
        /*	Установка XML */
        model.addAttribute("toExecutionXML", toExecutionXML.getSqlListResult());
 ///       model.addAttribute("checkFoundScripts", toExecutionSQL.getCheckFoundScripts());
//        model.addAttribute("checkScripts", toExecutionSQL.ProgressBarChecked());
        model.addAttribute("checkedSQLType", toExecutionSQL.getCheckedSQLType());
        return "updater";
    }

    @RequestMapping(value = {"/in_detail_sql"}, method = RequestMethod.GET)
    public String in_detail_sql(Model model) {
        /*	Установка SQL */
        model.addAttribute("toExecutionSQL", toExecutionSQL.getSqlListResult());

        return "in_detail_sql";
    }

    /* Обновить систему, автоматический режим*/
    @RequestMapping(value = {"/pathgz"}, method = RequestMethod.POST)
    public String findInLog(Model model,
                            @ModelAttribute("pathGZ") PathGZ pathGZ
    ) {
        System.out.println("pathGZ=============");
        // new Uploading().start(pathGZ,updateModel,toUploadSPServerSQLvXML,toExecutionSQL);
        return "redirect:/updater";
    }

    /**
     * Ручной режим обновления
     */

    /* Главный сервер */
    /* Выполнить загрузку потча на главный сервер  */
    @RequestMapping(value = {"/executeMainUpload"}, method = RequestMethod.POST)
    public String executeMainUploadPost(Model model, @ModelAttribute("pathGZ") PathGZ pathGZ) {
        String path = pathGZ.getPath();
        System.out.println(path);

        if (updateModel.fileExists(path)) {
            /* Загрузка патча на сервер контроллера */
            if (path.contains("http")) {
                new Thread(() -> {
                    try {
                        updateModel.httpPath(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } else {
            System.out.println("Нечего выполнять !!!");
        }
        return "redirect:/updater";
    }

    /* Остановить загрузку потча на главный сервер  */
    @RequestMapping(value = {"/clearMainUpload"}, method = RequestMethod.POST)
    public String clearMainUploadPost(Model model) {
        System.out.println("Иницирована остановка загрузки потча на главный сервер!");
        updateModel.closeUpload();
        return "redirect:/updater";
    }


    /**
     * Сервер обновления
     */
    /* Передача файла */

    /* Выполнить загрузку потча на сервер обновления SQL/XML  */
    @RequestMapping(value = {"/executeSPServerSQLvXML"}, method = RequestMethod.POST)
    public String executeSPServerSQLvXML(Model model) {
        System.out.println("Загрузка потча на сервер обновления SQL/XMLexecuteSPServerSQLvXML");
        new Thread(() -> {
            try {
                toUploadSPServerSQLvXML.uploadFile(PATCH_DIR);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }).start();
        return "redirect:/updater";
    }

    /* Остановить загрузку патча, на "сервер обновления SQL/XML" */
    @RequestMapping(value = {"/clearUploadSPServerSQLvXML"}, method = RequestMethod.POST)
    public String clearUploadPostServerSQLvXML(Model model) {
        toUploadSPServerSQLvXML.closeUpload();
        return "redirect:/updater";
    }




    /**
     * Выполнение SQL скриптов
     */
    @RequestMapping(value = {"/executeSQL"}, method = RequestMethod.POST)
    public String executeSQL(HttpServletResponse response) throws IOException {
        System.out.println("Выполнения SQL скриптов");
        threadExecuteSQL = new Thread(() -> {
            toExecutionSQL.send();
        }, "ThreadExecuteSQL");
        threadExecuteSQL.setName("Execute_SQL_____");
        threadExecuteSQL.start();


        for (int i = 0; i < toExecutionSQL.getSqlListResult().size(); i++) {
            System.out.println("SqlListResult: " + i + toExecutionSQL.getSqlListResult().get(i));
            response.getWriter().println(toExecutionSQL.getSqlListResult().get(i));
           // response.flushBuffer();
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        return "redirect:/updater";

    }

    @RequestMapping(value = {"/executeSQLYes"}, method = RequestMethod.POST)
    public String executeSQLYes() {
        System.out.println("Нажата кнопка Yes");
        ToExecutionSQL.onClickYes = true;
        System.out.println("Нажата кнопка Yes"+toExecutionSQL.onClickYes);
        return "redirect:/updater";
    }

    @RequestMapping(value = {"/executeSQLNo"}, method = RequestMethod.POST)
    public String executeSQLNo() {
        System.out.println("Нажата кнопка No" + " Текущий поток " + Thread.currentThread() + " NAME: " + Thread.currentThread().getName());
        toExecutionSQL.onClickNo = true;
        return "redirect:/updater";
    }

    @RequestMapping(value = {"/executeSQLInfo"}, method = RequestMethod.POST)
    public String executeSQLInfo() {
//        try {
//            threadExecuteSQL.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("Нажата кнопка Info");
        toExecutionSQL.onClickInfo = true;

        return "redirect:/in_detail_sql";
    }

    /* Остановка SQL скриптов */
    @RequestMapping(value = {"/stopExecuteSQL"}, method = RequestMethod.POST)
    public String stopExecuteSQL() {
        System.out.println("Остановка  SQL скриптов");
        /* посылаем команду на отмену  */
        executeSQLNo();
        /* очищаем список SQL */
        toExecutionSQL.getSqlListResult().clear();
        /* скрываем понель деалога  */
//        toExecutionSQL.setCheckFoundScripts("clear");

        return "redirect:/updater";
    }


//    @RequestMapping(value = {"/in_detail_sql/text"}, method = RequestMethod.POST)
//    public void testPage(HttpServletResponse response) throws  java.io.IOException {
//        //   response.setHeader("Access-Control-Allow-Origin", "*");
//        // String ourForm_inp = request.getParameter("ourForm_inp");
//
//
//        for (int i = 10000; i > 0; i--) {
//            System.out.println("BufferSize " + response.getBufferSize());
//            response.getWriter().print(i + " ");
//            response.flushBuffer();
//        }
//        try {
//            response.getWriter().print("alert-success");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        response.setContentType("text/html;charset=utf-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//
////        // отправка значения напряжения клиенту до тех пор пока ему не надоест эти значения читать
////        for (int i = 0; i < 10; i++) {
////            try {
////                Thread.sleep(1000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////            os.print("voltmeter.getVoltage()" + "\n" + i);
////
////            System.out.println("voltmeter.getVoltage()" + "\n" + i);
//
////            response.flushBuffer();
////            os.flush();
////        }
//
////        if(ourForm_inp.contains("success")){
////
////            try {
////                response.getWriter().print("alert-success");
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////
////        }
////        if(ourForm_inp.contains("danger")){
////
////            try {
////                response.getWriter().print("alert-danger");
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////
////        }
//
//
//        //   System.out.println("ourForm_inp = " + ourForm_inp);
//
//
//    }


    @RequestMapping(value = {"/in_detail_sql/executeSQL"}, method = RequestMethod.POST)
    public void testPageX(HttpServletResponse response) throws java.io.IOException {
        for (int i = 100; i > 0; i--) {
            ///System.out.println("BufferSize " + response.getBufferSize());
            response.getWriter().print(i + "<>");
           /// response.flushBuffer();
        }
    }

    /**
     * Выполнение XML скриптов
     */
    @RequestMapping(value = {"/executeXML"}, method = RequestMethod.POST)
    public String executeXML() {
        System.out.println("Выполнение XML скриптов");
        toExecutionXML.send();
//        new Thread(() -> {
//            toExecutionXML.send();
//        }, "ThreadExecuteXML");

        return "redirect:/updater";
    }

}
