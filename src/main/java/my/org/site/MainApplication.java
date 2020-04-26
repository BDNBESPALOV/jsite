package my.org.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;


@SpringBootApplication
public class MainApplication {
    public static void main(String... args) throws IOException {

       ConfigurableApplicationContext s = SpringApplication.run(MainApplication.class, args);
      // s.getBean("server", JServer.class).startServer();

    }
}
