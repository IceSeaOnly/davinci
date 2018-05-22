package site.binghai.davinci.client.annotations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IceSea on 2018/5/20.
 * GitHub: https://github.com/IceSeaOnly
 */
@Order(value = 1)
@Component
public class ContextListener implements BeanPostProcessor, InitializingBean {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, Object> services; // class+method:component

    public Object getService(String service) {
        return services.get(service);
    }

    public Map<String, Object> getServices() {
        return services;
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        Annotation annotation = o.getClass().getAnnotation(Davinci.class);
        if (annotation != null) {
            if (o.getClass().getInterfaces().length != 1) {
                throw new ExceptionInInitializerError("davinci component should implement only one interface.");
            }
            String clazz = o.getClass().getInterfaces()[0].getName();
            Method[] methods = o.getClass().getInterfaces()[0].getMethods();
            for (Method method : methods) {
                String key = clazz + "." + method.getName();
                services.put(key, o);
            }
        }
        return o;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("\n\noooooooooo.                          o8o                         o8o  \n" +
                "`888'   `Y8b                         `\"'                         `\"'  \n" +
                " 888      888  .oooo.   oooo    ooo oooo  ooo. .oo.    .ooooo.  oooo  \n" +
                " 888      888 `P  )88b   `88.  .8'  `888  `888P\"Y88b  d88' `\"Y8 `888  \n" +
                " 888      888  .oP\"888    `88..8'    888   888   888  888        888  \n" +
                " 888     d88' d8(  888     `888'     888   888   888  888   .o8  888  \n" +
                "o888bood8P'   `Y888\"\"8o     `8'     o888o o888o o888o `Y8bod8P' o888o\n");
        services = new ConcurrentHashMap<>();
    }
}
