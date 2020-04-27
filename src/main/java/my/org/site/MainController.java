package my.org.site;

import lombok.Data;
import lombok.SneakyThrows;
//import my.org.site.server.EMServer;
import my.org.site.server.JClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

@Controller
public class MainController {
    private static List<FindInLog> persons = new ArrayList();
    public static HashMap<String,JClient> map = new HashMap<>();


    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

//    @Autowired
//    EMServer echoMultiServer;

    private ServerSocket serverSocket;


    public void start(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        System.out.println("Started: " + serverSocket);
        try {
            while (true)
                new MainController.EchoClientHandler(serverSocket.accept()).start();
        }catch (SocketException e){

        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class EchoClientHandler extends Thread {
//        private Socket clientSocket;
//        private PrintWriter out;
//        private BufferedReader in;

        public EchoClientHandler(Socket socket) {
            MainController.clientSocket = socket;
        }

        @SneakyThrows
        public void run()  {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {


                    if (inputLine.contains("client")) {
                        System.out.println(inputLine);
                        inputLine = inputLine.substring(6, inputLine.length());
                        map.put(inputLine, new JClient(inputLine, inputLine, new Date().toString()));
                    } else if (inputLine.contains("Disconnect ")) {
                        System.out.println(inputLine);
                        inputLine = inputLine.substring(11, inputLine.length());
                        map.remove(inputLine);
                    }

                    System.out.println("inputLine: " + inputLine);
                    if (".".equals(inputLine)) {
                        out.println("bye");
                        break;
                    }

                    out.println(inputLine);
                }
            } catch (SocketException e){}




            in.close();
            out.close();
            clientSocket.close();
        }

    }


    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        JClient jClient = new JClient();
        model.addAttribute("jClient", jClient);
        model.addAttribute("map", map);
        model.addAttribute("message", message);
        return "index";
    }
    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.POST)
    public String findInLog(Model model, //
                            @ModelAttribute("jClient") JClient jClient
    )  {
//        String command = jClient.getCommand();
//            try {
//                echoMultiServer.start(8181);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println("command:  "+command);
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



    @RequestMapping(value = {"/startServer" }, method = RequestMethod.POST)
    public  String startServer(){
        new Thread(
                () -> {
                    try {
//                        echoMultiServer.
                       start(8181);
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
//            echoMultiServer.
           stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("stopServer");
        return "redirect:/";
    }


    @PostMapping("startSP")
    public  String startSP(){
        System.out.println("startSP");
        out.println("startSP");
        return "redirect:/";
    }
    @PostMapping("stopSP")
    public  String stopSP(){
        System.out.println("stopSP");
        out.println("stopSP");
        return "redirect:/";
    }



}