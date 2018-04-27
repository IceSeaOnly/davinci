package site.binghai.davinci.core.reflect;

import site.binghai.davinci.core.entity.ServiceInvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 根据接口生成代理对象
 * GitHub: https://github.com/IceSeaOnly
 */
public class ProxyFactory {

    public static Object getInstance(Class<?> clazz) {
        // 获取动态代理类
        Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz}, new ServiceInvocationHandler(clazz));
        return proxy;
    }
}
