package my.org.site.server;

import lombok.Data;

import java.net.Socket;
import java.util.Date;

public class JClient {
    private Socket clientSocket;
    private String date;
    private String name;
    private String command;

    public JClient(){}

    public JClient(String command, String name, String date,Socket clientSocket) {
        this.date = date;
        this.name = name;
        this.command = command;
        this.clientSocket = clientSocket;
    }
    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand(){
        return command;
    }

    public void setCommand(String command){
        this.command = command;
    }




}
