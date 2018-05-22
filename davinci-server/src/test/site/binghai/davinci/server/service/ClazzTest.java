package site.binghai.davinci.server.service;

import org.junit.*;
import org.junit.Test;

/**
 * Created by IceSea on 2018/5/18.
 * GitHub: https://github.com/IceSeaOnly
 */
public class ClazzTest {
    @Test
    public void simpleName() throws Exception {
        System.out.println(this.getClass().getSimpleName());
        System.out.println(this.getClass().getCanonicalName());
    }
}
