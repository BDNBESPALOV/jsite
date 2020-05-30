package my.org.site.updater;

import my.org.site.updater.model.ToUploadSP;
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
    private ToUploadSP toUploadSP = new ToUploadSP();


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

    model.addAttribute("sizeToUploadSP",  toUploadSP.getSize());
    model.addAttribute("valueNowToUploadSP", toUploadSP.getValueNow());
    model.addAttribute("partToUploadSP", toUploadSP.getPart());
    model.addAttribute("checkedToUploadSP", toUploadSP.getChecked());
    return "updater";
    }

    @RequestMapping(value = { "/pathgz" }, method = RequestMethod.POST)
    public String findInLog(Model model,
                                @ModelAttribute("pathGZ") PathGZ pathGZ
    )  {
        new Uploading().start(pathGZ,updateModel,toUploadSP);
        return "redirect:/updater";
    }

    @RequestMapping(value = { "/clearUpload" }, method = RequestMethod.POST)
    public String clearUploadPost(Model model
    )  {
        updateModel.closeUpload();
        return "redirect:/updater";
    }



}
