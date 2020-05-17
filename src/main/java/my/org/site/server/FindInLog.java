package my.org.site.server;

public class FindInLog {
    private String path;
    private String logFilter;

    public FindInLog(){}

    public FindInLog(String name, String s) {
        this.path = name;
        this.logFilter = s;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public String getPath(){
        return path;
    }

    public void setLogFilter(String logFilter){
        this.logFilter=logFilter;
    }
    public String getLogFilter(){
        return logFilter;
    }

}
