package my.org.site;

import org.omg.CORBA.WStringSeqHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;


@SpringBootApplication
public class MainApplication {
    public static void main(String... args) throws IOException {
        SpringApplication.run(MainApplication.class, args);
    }
}
