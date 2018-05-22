package site.binghai.davinci.client.monitor;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import site.binghai.davinci.common.def.MonitorReport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IceSea on 2018/4/1.
 * GitHub: https://github.com/IceSeaOnly
 */
@Component
@Log4j
public class MonitorClient implements InitializingBean {
    private static ConcurrentLinkedQueue<MonitorReport> reports;
    private ExecutorService executorService;

    public void push(MonitorReport t) {
        reports.add(t);
    }

    public void push(Collection<MonitorReport> ts) {
        reports.addAll(ts);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        reports = new ConcurrentLinkedQueue<>();
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(consumerTask());
    }

    private Runnable consumerTask() {
        return () -> {
            List<MonitorReport> pkg = new ArrayList<>();
            while (true) {
                pkg.add(reports.poll());
                if (pkg.size() > 10) {
                    upload2Server(pkg);
                    pkg.clear();
                }
            }
        };
    }

    private void upload2Server(List<MonitorReport> pkg) {

    }
}
