package site.binghai.davinci.client.reflect;

import com.alibaba.fastjson.JSONObject;
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

    /**
     * classType为被代理的接口
     */
    public ServiceInvocationHandler(Class<?> classType) {
        this.classType = classType;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Call call = new Call(classType.getCanonicalName(), method.getName(), method.getParameterTypes(), args);
        /** 远程过程begin */
        Future<Call> callFuture = ServiceConnector.getInstance().post(call);
        call = callFuture.get(3, TimeUnit.SECONDS);
        /** 远程过程end */
        if (call.getExceptionMessage() != null) {
            throw new Exception(call.getExceptionMessage());
        }
        Object returnResult = call.getResult();
        String data = returnResult.toString();
        return JSONObject.parseObject(data, method.getReturnType());
    }
}
