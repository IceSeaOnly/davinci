package site.binghai.davinci.server.obervers;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.binghai.davinci.common.enums.DataPackageEnum;
import site.binghai.davinci.server.service.MethodMapService;

/**
 * Created by IceSea on 2018/5/25.
 * GitHub: https://github.com/IceSeaOnly
 */
@Service
public class OfflineObserver extends BaseObserver {
    @Autowired
    private MethodMapService methodMapService;

    @Override
    public DataPackageEnum getAcceteType() {
        return DataPackageEnum.WORKER_OFFLINE;
    }

    @Override
    public void putData(Object data) {
        JSONObject obj = JSONObject.parseObject(data.toString());
        methodMapService.hostOffline(obj.getString("host"), obj.getString("appName"));
    }

    @Override
    protected void onBeanReady() {

    }
}
