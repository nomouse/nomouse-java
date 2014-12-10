package nomouse.spring.context;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

public class SpringContextLoaderListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("#################################################################");
        System.out.println("spring.profiles.active=" + System.getProperty("spring.profiles.active"));
        System.out.println("spring.profiles.default=" + System.getProperty("spring.profiles.default"));
        System.out.println("#################################################################");
        super.contextInitialized(event);

    }


}
