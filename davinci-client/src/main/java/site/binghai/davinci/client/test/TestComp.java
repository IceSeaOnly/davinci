package site.binghai.davinci.client.test;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import site.binghai.davinci.client.annotations.Davinci;

/**
 * Created by IceSea on 2018/5/20.
 * GitHub: https://github.com/IceSeaOnly
 */
@Component
@Davinci
public class TestComp implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
