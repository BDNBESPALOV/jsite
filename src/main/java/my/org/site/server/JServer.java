package my.org.site.server;

import my.org.site.MainController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

<<<<<<<< HEAD:src/main/java/my/org/site/server/JServer.java
@Service
public class JServer {
    @Value("${server.port1}")
    private int PORT ;

    public  void startServer(String command) throws IOException {

        ServerSocket s = new ServerSocket(PORT);
========
@Component
public class Server {
    @Value("${server.port1}")
    private  static int port ;




   // private int port = 8181 /*MainController.PORT*/;
@Bean(initMethod = "${server.port1}")
    public  void startServer() throws IOException {

        System.out.println("port " + port);
        ServerSocket s = new ServerSocket(port);
>>>>>>>> origin/master:src/main/java/my/org/site/server/Server.java
        System.out.println("Started: " + s);
        try {
            // Блокирует до тех пор, пока не возникнет соединение:
            Socket socket = s.accept();
            try {
                System.out.println("Connection accepted: " + socket);
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                // Вывод автоматически выталкивается из буфера PrintWriter'ом
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);

                while (true) {
                    String str = in.readLine();
                    //   if (str == "END") { break; }
                    if(str != null){
                        System.out.println("Echoing: " + str);
                        out.println(command);
                      //  out.println("Command:C:\\Users\\userBDN\\AppData\\Local\\Postman\\Postman.exe");
                    }
//java.net.SocketException: Connection reset
                }
                // Всегда закрываем два сокета...
            }
            finally {
                System.out.println("closing...");
                socket.close();
            }
        }
        finally {
            s.close();
        }
    }
}
