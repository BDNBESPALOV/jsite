package my.org.site.server;

public class ParserLine {
    private String key;
    private String value;

    public ParserLine (String line) {
        int index = line.indexOf("Value:");
        this.key = line.substring(4,index);
        this.value = line.substring(index);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
