package my.org.site.updater;

import my.org.site.updater.model.ToUploadServerAdmin;
import my.org.site.updater.model.Uploading;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UpdaterController {

    private ToUploadServerAdmin updateModel = new ToUploadServerAdmin();


    @RequestMapping(value = { "/center" }, method = RequestMethod.GET)
    public String te(Model model){
        model.addAttribute("valueNow", ToUploadServerAdmin.getValueNow());
        return "updater";
    }


    @RequestMapping(value = { "/updater" }, method = RequestMethod.GET)
    public String updater(Model model) {
    model.addAttribute("pathGZ", new PathGZ());
    model.addAttribute("size",  ToUploadServerAdmin.getSize());
    model.addAttribute("valueNow", ToUploadServerAdmin.getValueNow());
    model.addAttribute("part", ToUploadServerAdmin.getPart());
    model.addAttribute("checked", ToUploadServerAdmin.getChecked());

    return "updater";
    }

    @RequestMapping(value = { "/pathgz" }, method = RequestMethod.POST)
    public String findInLog(Model model,
                                @ModelAttribute("pathGZ") PathGZ pathGZ
    )  {
        new Uploading().start(pathGZ,updateModel);
        return "redirect:/updater";
    }

    @RequestMapping(value = { "/clearUpload" }, method = RequestMethod.POST)
    public String clearUploadPost(Model model
    )  {
        updateModel.closeUpload();
        return "redirect:/updater";
    }



}
