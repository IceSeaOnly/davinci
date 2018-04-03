package site.binghai.davinci.server.service;

import com.alibaba.fastjson.JSONObject;
import site.binghai.davinci.common.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 */
public class Test {
    @org.junit.Test
    public void zzkun(){
        int workers = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(workers);
        List<Future<String>> resultList = new ArrayList<Future<String>>();

        for (int i = 0; i < workers; i++) {
            Future<String> future = executorService.submit(()->{
                String url="http://t.zzkun.com/api/url/longToShort?url=";
                while (true){
                    try {
                        String next = "http://t.zzkun.com/"+ UUID.randomUUID().toString();
                        JSONObject data = HttpUtils.sendJSONGet(url+next,null);
                        System.out.println(data.getString("data"));
                    }catch (Exception e){}
                }
            });

            resultList.add(future);
        }

        executorService.shutdown();
        for (Future<String> stringFuture : resultList) {
            try {
                stringFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @org.junit.Test
    public void testTH() throws Exception {
        boolean a = true;
        boolean b = true;
        boolean c = false;
        boolean d = false;
        System.out.println(!a ^ b);
        System.out.println(!a ^ c);
        System.out.println(!b ^ c);
        System.out.println(!d ^ c);
        System.out.println(!d ^ c);
    }
}
