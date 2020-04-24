package testtt.spring.starter;


import testtt.spring.tcp.Server;
import testtt.spring.tcp.TcpServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import testtt.spring.tcp.TcpControllerBeanPostProcessor;
import testtt.spring.tcp.TcpServerAutoStarterApplicationListener;

@Configuration
@EnableConfigurationProperties(TcpServerProperties.class)
@ConditionalOnProperty(prefix = "javagrinko.tcp-server", name = {"port", "autoStart"})
public class TcpServerAutoConfiguration {

    @Bean
    TcpServerAutoStarterApplicationListener tcpServerAutoStarterApplicationListener() {
        return new TcpServerAutoStarterApplicationListener();
    }

    @Bean
    TcpControllerBeanPostProcessor tcpControllerBeanPostProcessor() {
        return new TcpControllerBeanPostProcessor();
    }

    @Bean
    Server server(){
        return new TcpServer();
    }
}
