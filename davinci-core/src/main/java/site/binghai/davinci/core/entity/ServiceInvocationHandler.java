package site.binghai.davinci.core.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by IceSea on 2018/4/27.
 * GitHub: https://github.com/IceSeaOnly
 */
@Data
public class ServiceInvocationHandler implements InvocationHandler {
    private Class<?> classType;

    /**
     * classType为被代理的接口
     * */
    public ServiceInvocationHandler(Class<?> classType) {
        this.classType = classType;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Call call = new Call(classType.getName(), method.getName(), method.getParameterTypes(), args);
        System.out.println(JSONObject.toJSONString(call));
        /** 远程过程begin */
        /** 远程过程end */
        Object returnResult = call.getResult();
        return returnResult;
    }
}
