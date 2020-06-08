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

@Controller
public class UpdaterController {

    private ToUploadMainServer updateModel = new ToUploadMainServer();
    private ToUploadSPServerSQLvXML toUploadSPServerSQLvXML = new ToUploadSPServerSQLvXML();
    private ToExecutionSQL toExecutionSQL = new ToExecutionSQL();


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

    //toExecutionSQL.setSqlListResult(">");
    model.addAttribute("toExecutionSQL",toExecutionSQL.getSqlListResult());
    return "updater";
    }

    /* Обновить систему */
    @RequestMapping(value = { "/pathgz" }, method = RequestMethod.POST)
    public String findInLog(Model model,
                                @ModelAttribute("pathGZ") PathGZ pathGZ
    )  {
        System.out.println("pathGZpathGZpathGZpathGZpathGZpathGZ");
       // new Uploading().start(pathGZ,updateModel,toUploadSPServerSQLvXML,toExecutionSQL);
        return "redirect:/updater";
    }

    /* Выполнить загрузку потча на главный сервер  */
    @RequestMapping(value = { "/executeMainUpload" }, method = RequestMethod.POST)
    public String executeMainUploadPost(Model model, @ModelAttribute("pathGZ") PathGZ pathGZ )  {
        String path = pathGZ.getPath();
        System.out.println(path);

        if(pathGZ != null || pathGZ.getPath() != null ) {
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
        System.out.println("Иницирована остановка загрузки потча на главный сервер!!!");
        updateModel.closeUpload();
        return "redirect:/updater";
    }


    /* Остановить загрузку потча на сервер обновления SQL/XML */
    @RequestMapping(value = { "/clearUploadServerSQLvXML" }, method = RequestMethod.POST)
    public String clearUploadPostServerSQLvXML(Model model)  {
        toUploadSPServerSQLvXML.closeUpload();
        return "redirect:/updater";
    }



}
