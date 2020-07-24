//package readfile;
//
//import my.org.site.server.ServerController;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.net.SocketException;
//
//public class ReaderTest {
//    ServerSocket  serverSocket = new ServerSocket(8181);
//    public ReaderTest() throws IOException {
//    }
//
//    public static void main(String ... args) throws IOException {
//
//
//    }
//
//    public void uploadFile() throws IOException {
//
//        serverSocket.accept();
//
//        out.println("accept file");
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//     //   Socket clientSocketFile = clientHashMap.get("AdminFile").getClientSocket();
//
//        try(
//                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
//                BufferedOutputStream bos = new BufferedOutputStream(clientSocketFile.getOutputStream()))
//        {
//            byte []  byteArray = new byte[8192];
//            int in;
//            while ((in = bis.read(byteArray)) != -1){
//                bos.write(byteArray,0,in);
//            }
//
//        }catch (IOException e){
//
//        }
//
//
//
//    }
//}
