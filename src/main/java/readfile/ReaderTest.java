package readfile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class ReaderTest {
    public static void main(String ... args) throws IOException {
//        try {
//            FileReader file = new FileReader("");
//            Scanner scanner = new Scanner(file);
//            while(scanner.hasNextLine()){
//                String s =scanner.nextLine();
//                if(s.contains("samba")){
//                    System.out.println(s);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    int i = 0;
//    URL url = new URL("https://google.com/");
//    URLConnection urlConnection = url.openConnection();
//    System.out.println(new Date(urlConnection.getLastModified()));
//    while( (i = urlConnection.getInputStream().read()) != -1){
//        System.out.print((char) i);
//    }
        System.out.println(++i);
        System.out.println(i++);
        System.out.println(i);

    }
}
