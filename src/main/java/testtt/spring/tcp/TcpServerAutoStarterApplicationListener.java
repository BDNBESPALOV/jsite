package testtt.spring.tcp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import testtt.spring.starter.TcpServerProperties;

@Component
public class TcpServerAutoStarterApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TcpServerProperties properties;

    @Autowired
    private Server server;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        boolean autoStart = properties.getAutoStart();
        if (autoStart){
            server.setPort(properties.getPort());
            server.start();
        }
    }
}
