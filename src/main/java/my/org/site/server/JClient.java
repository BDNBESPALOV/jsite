package my.org.site.server;

import java.net.Socket;
import java.util.HashMap;

public class JClient {
    private Socket clientSocket;
    private String date;
    private String name;
    private String command;
    private HashMap<String,String> process ;

    private int size;

    public JClient(){}

    public JClient(String command, String name, String date,Socket clientSocket) {
        this.date = date;
        this.name = name;
        this.command = command;
        this.clientSocket = clientSocket;
        this.process = new HashMap<>();
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

    public HashMap<String, String> getProcess() {
        return process;
    }

    public void addProcess(String key, String value) {
        this.process.put(key,value);
        this.size = this.process.size();
    }
    public void removeProcess(String key) {
        this.process.remove(key);
        this.size = this.process.size();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }




}
