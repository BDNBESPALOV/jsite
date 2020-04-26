package my.org.site;

import my.org.site.server.EchoMultiServer;
import my.org.site.server.JClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class MainController {
    private static List<FindInLog> persons = new ArrayList();
    private static List<JClient> jClients = new ArrayList<>();

    static {
        jClients.add(new JClient("test command", "test name", 1));
        jClients.add(new JClient("test command2", "test name2", 1));
    }

    @Autowired
    EchoMultiServer echoMultiServer;

    // Инъетировать (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

//    @Value("${path.message}")
//    private String path;


    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        JClient jClient = new JClient();
        model.addAttribute("jClient", jClient);
        model.addAttribute("message", message);
        model.addAttribute("jClients", jClients);
        return "index";
    }
    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.POST)
    public String findInLog(Model model, //
                            @ModelAttribute("jClient") JClient jClient
    )  {
        String command = jClient.getCommand();
//            try {
//                jServer.startServer(command);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            try {
                echoMultiServer.start(8181);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("command:  "+command);
        return "index";
    }

    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
    public String personList(Model model) {
        model.addAttribute("persons", persons);
        return "personList";
    }

    @RequestMapping(value = { "/formFindLog" }, method = RequestMethod.GET)
    public String addfindInLog(Model model) {
        FindInLog findInLog = new FindInLog();
        model.addAttribute("findInLog", findInLog);
        return "formFindLog";
    }

    @RequestMapping(value = { "/formFindLog" }, method = RequestMethod.POST)
    public String findInLog(Model model, //
                                @ModelAttribute("findInLog") FindInLog findInLog
    )  {
        String path = findInLog.getPath();
        String name = findInLog.getLogFilter();

        try {
            FileReader  file = new FileReader(path);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String s =scanner.nextLine();
                if(s.contains(name)){
                    persons.add(new FindInLog(name,s));
                    System.out.println(s);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/personList";
    }




}