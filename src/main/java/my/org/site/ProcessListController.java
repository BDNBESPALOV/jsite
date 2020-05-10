package my.org.site;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class ProcessListController {

    @RequestMapping(value = { "/processList" }, method = RequestMethod.GET)
    public String processList(Model model) {
        model.addAttribute("spProc",
                ServerController.jClientMap.get(ServerController.clientName).getProcess());
        model.addAttribute("clientName", ServerController.clientName );
        return "processList";
    }

    @PostMapping("clientName")
    public  String clientName(String cName)  {
        ServerController.clientName = cName;
        return "redirect:/processList";
    }

    @PostMapping("killProcess")
    public  String killProcess(String killProcess) throws IOException {
        ServerController.jClientMap.get(ServerController.clientName).removeProcess(killProcess);
        PrintWriter printWriter = new PrintWriter(ServerController.jClientMap
                .get(ServerController.clientName).getClientSocket().getOutputStream(), true);
        printWriter.println("Killed: "+killProcess);
        System.out.println("Killed: "+ ServerController.clientName+" pid: "+killProcess);
        return "redirect:/processList";
    }


}
