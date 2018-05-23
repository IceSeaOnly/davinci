package site.binghai.davinci.client.processor;

import org.springframework.stereotype.Service;
import site.binghai.davinci.common.enums.DataPackageEnum;

/**
 * Created by IceSea on 2018/5/23.
 * GitHub: https://github.com/IceSeaOnly
 */
@Service
public class HeartBeants extends BaseProcessor {
    @Override
    public DataPackageEnum getAcceteType() {
        return DataPackageEnum.HEART_BEATS;
    }

    @Override
    public void putData(Object data) {

    }

    @Override
    protected void onBeanReady() {

    }
}
