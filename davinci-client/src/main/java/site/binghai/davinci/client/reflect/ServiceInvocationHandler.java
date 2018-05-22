package site.binghai.davinci.client.reflect;

import lombok.Data;
import site.binghai.davinci.client.socket.ServiceConnector;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by IceSea on 2018/4/27.
 * GitHub: https://github.com/IceSeaOnly
 * 远程调用的核心
 */
@Data
public class ServiceInvocationHandler implements InvocationHandler {
    private Class<?> classType;
    private ServiceConnector serviceConnector;

    /**
     * classType为被代理的接口
     */
    public ServiceInvocationHandler(Class<?> classType, ServiceConnector serviceConnector) {
        this.classType = classType;
        this.serviceConnector = serviceConnector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Call call = new Call(classType.getCanonicalName(), method.getName(), method.getParameterTypes(), args);
        /** 远程过程begin */
        Future<Call> callFuture = serviceConnector.post(call);
        call = callFuture.get(3, TimeUnit.SECONDS);
        Object returnResult = call.getResult();
        /** 远程过程end */
        if(returnResult instanceof Exception){
            throw (Throwable) returnResult;
        }
        return returnResult;
    }
}
