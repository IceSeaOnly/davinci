package site.binghai.davinci.server.service;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import site.binghai.davinci.common.def.HostConfig;
import site.binghai.davinci.server.entity.DavinciService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by IceSea on 2018/5/25.
 * GitHub: https://github.com/IceSeaOnly
 */
@Service
public class DavinciServicesService extends BaseService<DavinciService> {
    @Transactional
    public void offLine(HostConfig config) {
        DavinciService example = new DavinciService();
        example.setHost(config.getIp());
        example.setAppName(config.getAppName());

        List<DavinciService> list = query(example);
        if (CollectionUtils.isNotEmpty(list)) {
            for (DavinciService davinciService : list) {
                davinciService.setOnLine(Boolean.FALSE);
                update(davinciService);
            }
        }
    }

    @Transactional
    public void onLine(HostConfig config, String method) {
        DavinciService example = new DavinciService();
        example.setHost(config.getIp());
        example.setAppName(config.getAppName());
        example.setMethodName(method);

        DavinciService service = queryOne(example);
        if (service == null) {
            example.setId(null);
            example.setPort(config.getPort());
            example.setOnLine(Boolean.TRUE);
            save(example);
        } else {
            service.setPort(config.getPort());
            service.setOnLine(Boolean.TRUE);
            update(service);
        }

    }

    @Transactional
    public void setAllOffline(){
        findAll(9999).forEach(v -> {
            v.setOnLine(Boolean.FALSE);
            update(v);
        });
    }

    public List<DavinciService> list(Integer page, String appName, String status, String method) {
        DavinciService exmaple = new DavinciService();
        if(status != null){
            exmaple.setOnLine(status.equals("onLine")?Boolean.TRUE:Boolean.FALSE);
        }
        if(appName != null){
            exmaple.setAppName(appName);
        }
        if(method != null){
            exmaple.setMethodName(method);
        }
        return pageQuery(exmaple,page,10);
    }
}
