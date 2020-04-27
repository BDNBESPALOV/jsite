package my.org.site.server;

import lombok.Data;

import java.util.Date;

public class JClient {
    private String date;
    private String name;
    private String command;

    public JClient(){}

    public JClient(String command, String name, String date) {
        this.date = date;
        this.name = name;
        this.command = command;
    }

    public String getStatus() {
        return date;
    }

    public void setStatus(int status) {
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
