package site.binghai.davinci.client.socket;

import com.alibaba.fastjson.JSONObject;
import site.binghai.davinci.client.reflect.Call;
import site.binghai.davinci.common.sockets.Client;
import site.binghai.davinci.common.sockets.Client2ServerHandler;

import java.util.Map;
import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

/**
 * Created by IceSea on 2018/5/22.
 * GitHub: https://github.com/IceSeaOnly
 */
class RemoteCallTask implements Callable<Call> {
    private Client client;
    private Map<String, Call> futurePool;
    private Call call;

    public RemoteCallTask(Client client, Map<String, Call> futurePool, Call call) {
        this.client = client;
        this.call = call;
        this.futurePool = futurePool;
    }

    @Override
    public Call call() throws Exception {
        Client2ServerHandler handler = (Client2ServerHandler) client.clientHandler();
        handler.post(JSONObject.toJSONString(call));
        return wait4Result();
    }

    private Call wait4Result() throws InterruptedException {
        while (futurePool.get(call.getToken()) == null) {
            sleep(10);
        }
        return futurePool.remove(call.getToken());
    }
}
