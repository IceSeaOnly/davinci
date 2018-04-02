package site.binghai.davinci.server.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.binghai.davinci.server.entity.UrlMapEntity;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlMapServiceTest {
    @Autowired
    private UrlMapService urlMapService;
    private UrlMapEntity urlMapEntity;

    @Test
    public void tests() throws Exception {
        addTest();
        updateTest();
        queryTest();
        deleteTest();
    }

    public void addTest() throws Exception {
        urlMapEntity = new UrlMapEntity();
        urlMapEntity.setMapKey("TEST_KEY");
        urlMapEntity.setMapUrl("TEST_URL");

        urlMapEntity = urlMapService.save(urlMapEntity);
        assert urlMapEntity != null;
    }

    public void updateTest() throws Exception {
        assert urlMapEntity != null;
        urlMapEntity.setMapUrl("TEST_URL_2");

        urlMapService.update(urlMapEntity);
    }

    public void queryTest() throws Exception {
        assert urlMapEntity != null;
        UrlMapEntity t = urlMapService.findById(urlMapEntity.getId());
        assert t != null;
        System.out.println(String.format("%s : %s",t.getMapKey(),t.getMapUrl()));
    }

    public void deleteTest() throws Exception {
        urlMapService.delete(urlMapEntity.getId());
    }
}