package readfile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ReaderTest {
    public static void main(String ... args) throws IOException {
//        BigDecimal t = BigDecimal.valueOf(1024);
//        BigDecimal f = new BigDecimal(131072);
//        f = f.divide(t).divide(t);

//        BigDecimal s = new BigDecimal(130449280);


        double e = (131072/1024);
        e = e/1024;

        double s = (130449280/1024);
        int si = (int) (s/1024);

        double m = e/si;
        double mi = (m*100);
        
        System.out.println(e);
        System.out.println(si);
        System.out.println(m);
        System.out.println(mi);
    }
}
