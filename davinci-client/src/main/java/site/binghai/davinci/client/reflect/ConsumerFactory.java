package site.binghai.davinci.client.reflect;

import site.binghai.davinci.client.socket.ServiceConnector;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IceSea on 2018/5/16.
 * GitHub: https://github.com/IceSeaOnly
 */
public class ConsumerFactory {
    private static Map<Class, Object> proxy = new HashMap();

    public static Object getProxyBean(Class interfaceClass) {
        if (proxy.get(interfaceClass) == null) {
            Object obj = Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                    new Class[]{interfaceClass}, new ServiceInvocationHandler(interfaceClass,ServiceConnector.getInstance()));
            proxy.put(interfaceClass, obj);
            return obj;
        } else {
            return proxy.get(interfaceClass);
        }
    }
}
