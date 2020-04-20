package readfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ReaderTest {
    public static void main(String ... args){
        try {
            FileReader file = new FileReader("");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String s =scanner.nextLine();
                if(s.contains("samba")){
                    System.out.println(s);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
