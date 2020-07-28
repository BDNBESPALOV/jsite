package my.org.site.updater;

import my.org.site.updater.model.ToExecutionSQL;
import my.org.site.updater.model.ToUploadSPServerSQLvXML;
import my.org.site.updater.model.ToUploadMainServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    /* дериктория загруженного патча */
    private final String PATCH_DIR = property.getProperty("directory.downloaded.patch");




//    @RequestMapping(value = { "/center" }, method = RequestMethod.GET)
//    public String te(Model model){
//        model.addAttribute("valueNow", updateModel.getValueNow());
//        return "updater";
//    }

    @RequestMapping(value = { "/updater" }, method = RequestMethod.GET)
    public String updater(Model model) {
    model.addAttribute("pathGZ", new PathGZ());

    model.addAttribute("size",  updateModel.getSize());
    model.addAttribute("valueNow", updateModel.getValueNow());
    model.addAttribute("part", updateModel.getPart());
    model.addAttribute("checked", updateModel.ProgressBarChecked());
    /*	Загрузка патча на сервер обновления */
    model.addAttribute("sizeToUploadSP",  toUploadSPServerSQLvXML.getSize());
    model.addAttribute("valueNowToUploadSP", toUploadSPServerSQLvXML.getValueNow());
    model.addAttribute("partToUploadSP", toUploadSPServerSQLvXML.getPart());
    model.addAttribute("checkedToUploadSP", toUploadSPServerSQLvXML.ProgressBarChecked());
    /*	Установка SQL */
    model.addAttribute("toExecutionSQL",toExecutionSQL.getSqlListResult());
    model.addAttribute("checkFoundScripts",toExecutionSQL.getCheckFoundScripts());
    model.addAttribute("checkScripts",toExecutionSQL.ProgressBarChecked());
    return "updater";
    }

    @RequestMapping(value = { "/textInfo" }, method = RequestMethod.GET)
    public String textInfo(Model model) {
        /*	Установка SQL */
        model.addAttribute("toExecutionSQL",toExecutionSQL.getSqlListResult());
        return "textInfo";
    }

    /* Обновить систему, автоматический режим*/
    @RequestMapping(value = { "/pathgz" }, method = RequestMethod.POST)
    public String findInLog(Model model,
                                @ModelAttribute("pathGZ") PathGZ pathGZ
    )  {
        System.out.println("pathGZpathGZpathGZpathGZpathGZpathGZ");
       // new Uploading().start(pathGZ,updateModel,toUploadSPServerSQLvXML,toExecutionSQL);
        return "redirect:/updater";
    }

    /** Ручной режим обновления */

    /* Главный сервер */
    /* Выполнить загрузку потча на главный сервер  */
    @RequestMapping(value = { "/executeMainUpload" }, method = RequestMethod.POST)
    public String executeMainUploadPost(Model model, @ModelAttribute("pathGZ") PathGZ pathGZ )  {
        String path = pathGZ.getPath();
        System.out.println(path);

        if(updateModel.fileExists(path)) {
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
    @RequestMapping(value = { "/clearMainUpload" }, method = RequestMethod.POST)
    public String clearMainUploadPost(Model model)  {
        System.out.println("Иницирована остановка загрузки потча на главный сервер!");
        updateModel.closeUpload();
        return "redirect:/updater";
    }


    /** Сервер обновления */
    /* Передача файла */

    /* Выполнить загрузку потча на сервер обновления SQL/XML  */
    @RequestMapping(value = { "/executeSPServerSQLvXML" }, method = RequestMethod.POST)
    public String executeSPServerSQLvXML(Model model )  {
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

    /* Остановить загрузку потча на сервер обновления SQL/XML */
    @RequestMapping(value = { "/clearUploadSPServerSQLvXML" }, method = RequestMethod.POST)
    public String clearUploadPostServerSQLvXML(Model model)  {
        toUploadSPServerSQLvXML.closeUpload();
        return "redirect:/updater";
    }

    /** Выполнения SQL скриптов */
    @RequestMapping(value = { "/executeSQL" }, method = RequestMethod.POST)
    public String executeSQL(Model model )  {
        System.out.println("Выполнения SQL скриптов");
        threadExecuteSQL = new Thread(() -> {
            toExecutionSQL.send();
        },"ThreadExecuteSQL");
        threadExecuteSQL.start();
        return "redirect:/updater";
    }

    @RequestMapping(value = { "/executeSQLYes" }, method = RequestMethod.POST)
    public String executeSQLYes()  {
        System.out.println("Нажата кнопка Yes");
        toExecutionSQL.onClickYes = true;
        return "redirect:/updater";
    }

    @RequestMapping(value = { "/executeSQLNo" }, method = RequestMethod.POST)
    public String executeSQLNo()  {
        System.out.println("Нажата кнопка No"+" Текущий поток "+Thread.currentThread()+" NAME: "+Thread.currentThread().getName());
        toExecutionSQL.onClickNo = true;
        return "redirect:/updater";
    }

    @RequestMapping(value = { "/executeSQLInfo" }, method = RequestMethod.POST)
    public String executeSQLInfo()  {
        System.out.println("Нажата кнопка Info");
        toExecutionSQL.onClickInfo = true;
        return "redirect:/textInfo";
    }

    /* Остановка SQL скриптов */
    @RequestMapping(value = { "/stopExecuteSQL" }, method = RequestMethod.POST)
    public String stopExecuteSQL()  {
        System.out.println("Остановка  SQL скриптов");
        /* посылаем команду на отмену  */
        executeSQLNo();
        /* очищаем список SQL */
        toExecutionSQL.getSqlListResult().clear();
        /* скрываем понель деалога  */
        toExecutionSQL.setCheckFoundScripts(false);
        return "redirect:/updater";
    }


}
