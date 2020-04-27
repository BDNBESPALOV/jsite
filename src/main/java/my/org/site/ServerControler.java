package my.org.site;

import my.org.site.server.EMServer;
import my.org.site.server.JClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class ServerControler {
    private EMServer emServer = new EMServer();


    static {
        EMServer.map.put("sp1",new JClient("test command", "test name", 1));
    }

    @RequestMapping(value = {"/startServer" }, method = RequestMethod.POST)
    public  String startServer(){
       new Thread(
                () -> {
                    try {
                        emServer.start(8181);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();

        System.out.println("startServer");
        return "redirect:/";
    }

    @RequestMapping(value = {"/stopServer" }, method = RequestMethod.POST)
    public  String stopServer(){
        try {
            emServer.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("stopServer");
        return "redirect:/";
    }

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("map", EMServer.map);
        return "index";
    }



}
