package site.binghai.davinci.core.reflect;

import org.junit.Test;


/**
 * Created by IceSea on 2018/4/27.
 * GitHub: https://github.com/IceSeaOnly
 */
public class ProxyFTest {

    @Test
    public void name() throws Exception {
        IService instance = (IService) new ProxyFactory().getInstance(IService.class);
        System.out.println(instance.name());
        System.out.println(instance.count());
    }
}

interface IService{
    int count();
    String name();
}