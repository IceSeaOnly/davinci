package site.binghai.davinci.client.socket;

import com.alibaba.fastjson.JSONObject;
import site.binghai.davinci.client.annotations.ContextListener;
import site.binghai.davinci.client.reflect.Call;
import site.binghai.davinci.common.sockets.Server2ClientHandler;
import java.lang.reflect.Method;

/**
 * Created by IceSea on 2018/5/22.
 * GitHub: https://github.com/IceSeaOnly
 * 相应远程方法并返回值
 */
public class ResponseCallTask implements Runnable {
    private Call call;
    private Object provider;
    private Server2ClientHandler handler;

    public ResponseCallTask(Call call, ContextListener contextListener, Server2ClientHandler responseHandler) {
        this.call = call;
        this.provider = contextListener.getService(call.getFullMethodName());
        this.handler = responseHandler;
    }

    @Override
    public void run() {
        try {
            Method method = provider.getClass().getDeclaredMethod(call.getMethodName(), call.getParamTypes());
            if (method == null) {
                returnException(new Exception("no such method:" + call.getMethodName()));
                return;
            }

            Object resp = method.invoke(provider, call.getParams());

            call.setResult(resp);
            handler.post(JSONObject.toJSONString(call));
        } catch (Exception e) {
            e.printStackTrace();
            returnException(e);
        }
    }

    private void returnException(Exception e) {
        call.setResult(null);
        call.setExceptionMessage(e.getMessage());
        handler.post(JSONObject.toJSONString(call));
    }
}
