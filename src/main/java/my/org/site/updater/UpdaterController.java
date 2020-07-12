package my.org.site.updater;

import my.org.site.updater.model.ToExecutionSQL;
import my.org.site.updater.model.ToUploadSPServerSQLvXML;
import my.org.site.updater.model.ToUploadMainServer;
import my.org.site.updater.model.Uploading;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Controller
public class UpdaterController {

    /* главный сервер */
    private ToUploadMainServer updateModel = new ToUploadMainServer();
    /* сервер обновления */
    public ToUploadSPServerSQLvXML toUploadSPServerSQLvXML = new ToUploadSPServerSQLvXML();
    /* запуск скриптов SQL */
    private ToExecutionSQL toExecutionSQL = new ToExecutionSQL();

    /* дериктория загруженного патча */
    private final String PATCH_DIR = "A:\\temp\\patch.zip";


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
    model.addAttribute("checked", updateModel.getChecked());

    model.addAttribute("sizeToUploadSP",  toUploadSPServerSQLvXML.getSize());
    model.addAttribute("valueNowToUploadSP", toUploadSPServerSQLvXML.getValueNow());
    model.addAttribute("partToUploadSP", toUploadSPServerSQLvXML.getPart());
    model.addAttribute("checkedToUploadSP", toUploadSPServerSQLvXML.getChecked());

    model.addAttribute("toExecutionSQL",toExecutionSQL.getSqlListResult());
    return "updater";
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



}
