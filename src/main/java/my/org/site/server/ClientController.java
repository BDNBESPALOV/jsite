package my.org.site.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class ClientController {

    @PostMapping("startSP")
    public  String startSP(String startSP) throws IOException {
        System.out.println("startSP");
        new PrintWriter(ServerController.jClientMap.get(startSP).getClientSocket()
                .getOutputStream(), true).println("Command:/u01/azk/SP/start.sh");
        return "redirect:/";
    }
    @PostMapping("stopSP")
    public  String stopSP(String stopSP) throws IOException {
        System.out.println("stopSP");
        new PrintWriter(ServerController.jClientMap.get(stopSP).getClientSocket()
                .getOutputStream(), true).println("Command:/u01/azk/SP/stop.sh");
        return "redirect:/";
    }
    @PostMapping("countJavaProcess")
    public  String countJavaProcess(String countJavaProcess) throws IOException, InterruptedException {

        System.out.println("countJavaProcess");
        PrintWriter printWriter = new PrintWriter(ServerController.jClientMap
                .get(countJavaProcess).getClientSocket().getOutputStream(), true);
        printWriter.println("monitoringProcess:/u01/azk/SP/countJP.sh");
        Thread.sleep(1000);
        System.out.println("jClientMap.get(countJavaProcess).getSize():  "
                +ServerController.jClientMap.get(countJavaProcess).getSize());
        //  ServerController.pSize = psMapSize.get(countJavaProcess);

        return "redirect:/";
    }


}
