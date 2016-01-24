package nomouse.java.web.common.context;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * 监听web容器启动事件
 */
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
