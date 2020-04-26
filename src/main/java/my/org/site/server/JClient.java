package my.org.site.server;

public class JClient {
    private int status;
    private String name;
    private String command;

    public JClient(){}

    public JClient(String command, String name, int status) {
        this.status = status;
        this.name = name;
        this.command = command;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
