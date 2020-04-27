package my.org.site.server;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

@Controller
public class EMServer {
        private ServerSocket serverSocket;
        public static HashMap<String,JClient> map = new HashMap<>();

        public void start(int port) throws IOException{
            serverSocket = new ServerSocket(port);
            System.out.println("Started: " + serverSocket);
            try {
                while (true)
                    new EchoClientHandler(serverSocket.accept()).start();
            }catch (SocketException e){

            }
        }

        public void stop() throws IOException {
            serverSocket.close();
        }

        private static class EchoClientHandler extends Thread {
            private Socket clientSocket;
            private PrintWriter out;
            private BufferedReader in;

            public EchoClientHandler(Socket socket) {
                this.clientSocket = socket;
            }

            @SneakyThrows
            public void run()  {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {


//            if (inputLine.contains("client"))   {
//                    System.out.println("contains(Command:) tempMessage: "+inputLine);
//                    inputLine = inputLine.substring(6,inputLine.length());
//                    System.out.println("tempMessage.substring(0,7): "+inputLine);
//                    map.put(inputLine, new JClient(inputLine,inputLine,1));
//
//                }

                    System.out.println("inputLine: " + inputLine);
                    if (".".equals(inputLine)) {
                        out.println("bye");
                        break;
                    }

                    out.println(inputLine);
                }

                in.close();
                out.close();
                clientSocket.close();
            }
        }
//        public static void main(String ... args){
//            try {
//                new EchoMultiServer().start(8181);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

}
