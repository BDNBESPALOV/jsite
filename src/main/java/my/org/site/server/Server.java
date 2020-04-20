package my.org.site.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Controller
public class Server {
    @Value("${server.port1}")
    private int PORT ;
    @Bean
    public  void startServer() throws IOException {
        ServerSocket s = new ServerSocket(PORT);
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

//                while (true) {
//                    String str = in.readLine();
//                    //   if (str == "END") { break; }
//                    if(str != null){
//                        System.out.println("Echoing: " + str);
//                        out.println("Command:C:\\Users\\userBDN\\AppData\\Local\\Postman\\Postman.exe");
//                    }
////java.net.SocketException: Connection reset
//                }
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
