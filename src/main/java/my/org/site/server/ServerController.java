package my.org.site.server;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ServerController {

    @RequestMapping(value = { "/index" }, method = RequestMethod.GET)
    public String getJClient(Model model) {
        JClient jClient = new JClient();
        model.addAttribute("jClient", jClient);
        return "index";
    }


    @RequestMapping(value = { "/index" }, method = RequestMethod.POST)
    public String findInLog(Model model, //
                            @ModelAttribute("findInLog") JClient jClient
    )  {
        String command = jClient.getCommand();
        return "index";
    }
}
