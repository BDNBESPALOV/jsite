package my.org.site.server;

import lombok.SneakyThrows;
import my.org.site.updater.model.ToExecutionSQL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

@Controller
public class ServerController {

    public static HashMap<String,JClient> jClientMap = new HashMap<>();
    public static HashMap<Socket,String> socketVsName = new HashMap<>();
    private boolean start = false;

    public static int pSize = 0;
    public static String clientName;


    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    private static PrintWriter out;

    private static BufferedReader in;

    private ServerSocket serverSocket;


    public void start(int port) throws IOException{
        serverSocket = new ServerSocket(port);

        System.out.println("serverSocketStarted: " + serverSocket);

        try {
            while (true)
                new ServerController.EchoClientHandler(serverSocket.accept()).start();
        }catch (SocketException e){

        }
    }

    private static class EchoClientHandler extends Thread {

        private volatile Socket clientSocket;

        public EchoClientHandler(Socket socket) {
            try {
                clientSocket = socket;
                clientSocket.setKeepAlive(true);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            System.out.println("ClientSocketStarted: " + socket);

        }


        @SneakyThrows
        public void run()  {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            try  {
                String inputLine;



                while ((inputLine = in.readLine()) != null) {

                    if (inputLine.contains("client")) {

                        System.out.println(inputLine);
                        inputLine = inputLine.substring(6, inputLine.length());
                        jClientMap.put(inputLine, new JClient(inputLine, inputLine, new Date().toString(),clientSocket));
                        socketVsName.put(clientSocket,inputLine);
                        System.out.println("clientSocket.isConnected(): "+clientSocket.isConnected());

                    } else if (inputLine.contains("Disconnect ")) {

                        System.out.println("Disconnect: "+inputLine);
                        inputLine = inputLine.substring(11, inputLine.length());
                        jClientMap.remove(inputLine);
                        socketVsName.remove(clientSocket);

                    } else  if(inputLine.contains("Key:") && inputLine.contains("Value:")){

                        String name;
                        name = socketVsName.get(clientSocket);
                        System.out.println("socketVsName: "+name);
                        jClientMap.get(name).addProcess(new ParserLine(inputLine).getKey(),
                                new ParserLine(inputLine).getValue());

                    }

                    System.out.println("inputLine: " + inputLine);

 //                   out.println(inputLine);
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
        model.addAttribute("jClientMap", jClientMap);
        model.addAttribute("message", message);
        model.addAttribute("pSize", pSize);
        model.addAttribute("start", start);
        return "index";
    }
//    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.POST)
//    public String findInLog(Model model, //
//                            @ModelAttribute("jClient") JClient jClient
//    )  {
//
//        return "index";
//    }

//    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
//    public String personList(Model model) {
//        model.addAttribute("persons", persons);
//        return "personList";
//    }

//    @RequestMapping(value = { "/formFindLog" }, method = RequestMethod.GET)
//    public String addfindInLog(Model model) {
//        FindInLog findInLog = new FindInLog();
//        model.addAttribute("findInLog", findInLog);
//        return "formFindLog";
//    }

//    @RequestMapping(value = { "/formFindLog" }, method = RequestMethod.POST)
//    public String findInLog(Model model, //
//                                @ModelAttribute("findInLog") FindInLog findInLog
//    )  {
//        String path = findInLog.getPath();
//        String name = findInLog.getLogFilter();
//        try {
//            FileReader  file = new FileReader(path);
//            Scanner scanner = new Scanner(file);
//            while(scanner.hasNextLine()){
//                String s =scanner.nextLine();
//                if(s.contains(name)){
//                    persons.add(new FindInLog(name,s));
//                    System.out.println(s);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return "redirect:/personList";
//    }



    @RequestMapping(value = {"/startServer" }, method = RequestMethod.POST)
    public  String startServer(){
        if(!start){
            new Thread(
                    () -> {
                        try {
                            start(8181);
//
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            ).start();
            start = true;
            System.out.println("startServer");
        } else {

            System.out.println("ERROR: server уже запущен!!!");
        }


        return "redirect:/";
    }

    @RequestMapping(value = {"/stopServer" }, method = RequestMethod.POST)
    public  String stopServer(){
        try {
            serverSocket.close();
            start = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("stopServer");
        return "redirect:/";
    }

    @RequestMapping(value = {"/refresh" }, method = RequestMethod.POST)
    public  String refresh(){
        System.out.println("refresh "+jClientMap.size());
        if(jClientMap.size() > 0){
            for (Map.Entry<String,JClient> e: jClientMap.entrySet()){
                String key = e.getKey();
                Socket socketTemp = e.getValue().getClientSocket();
                System.out.println("refresh "+socketTemp.isConnected());

                    System.out.println("isBound() "+socketTemp.isBound());
                    System.out.println("isClosed() "+socketTemp.isClosed());

                if(socketTemp.isClosed()){
                    System.out.println("socketTemp.isConnected "+socketTemp.isConnected());
                    try {
                        socketTemp.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    jClientMap.remove(key);
                }
            }

        }
        return "redirect:/";
    }



}