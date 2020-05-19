package my.org.site.updater;


import my.org.site.server.FindInLog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UpdaterController {

    private UpdateModel updateModel = new UpdateModel();

    @RequestMapping(value = { "/center" }, method = RequestMethod.GET)
    public String te(Model model){
        model.addAttribute("valueNow", UpdateModel.getValueNow());
        return "updater";
    }


    @RequestMapping(value = { "/updater" }, method = RequestMethod.GET)
    public String updater(Model model) {
    model.addAttribute("pathGZ", new PathGZ());
    model.addAttribute("size",  UpdateModel.getSize());
    model.addAttribute("valueNow", UpdateModel.getValueNow());
    model.addAttribute("part", UpdateModel.getPart());
    model.addAttribute("checked", UpdateModel.getChecked());

    return "updater";
    }

    @RequestMapping(value = { "/pathgz" }, method = RequestMethod.POST)
    public String findInLog(Model model,
                                @ModelAttribute("pathGZ") PathGZ pathGZ
    )  {
        String path = pathGZ.getPath();
        new Thread(() ->{
            updateModel.parsePath(path);
        }).start();
        System.out.println(" "+path);

        return "redirect:/updater";
    }

    @RequestMapping(value = { "/clearUpload" }, method = RequestMethod.POST)
    public String clearUploadPost(Model model
    )  {
        updateModel.closeUpload();
        return "redirect:/updater";
    }



}
