package my.org.site.md5;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ApacheMd5 {
    public static String md5(String file){
        String m = "-1";
        try {
            m = DigestUtils.md5Hex(Files.newInputStream(Paths.get(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return m;
    }
}
