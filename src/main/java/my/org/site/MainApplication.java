package my.org.site;

import my.org.site.server.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {
    public static void main(String... args) {
        ConfigurableApplicationContext a = SpringApplication.run(MainApplication.class, args);
//        try {
//            new Server().startServer();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }




}
