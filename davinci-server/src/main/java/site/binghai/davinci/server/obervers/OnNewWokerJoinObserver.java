package site.binghai.davinci.server.obervers;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.binghai.davinci.common.enums.DataPackageEnum;
import site.binghai.davinci.server.service.MethodMapService;

/**
 * Created by IceSea on 2018/5/23.
 * GitHub: https://github.com/IceSeaOnly
 */
@Service
@Log4j
public class OnNewWokerJoinObserver extends BaseObserver {
    @Autowired
    private MethodMapService methodMapService;

    @Override
    public DataPackageEnum getAcceteType() {
        return DataPackageEnum.DAVINCI_CLIENT;
    }

    @Override
    public void putData(Object data) {
        log.info("new client joined so we broadcast the service map.");
        methodMapService.mapChanged();
    }

    @Override
    protected void onBeanReady() {

    }
}
