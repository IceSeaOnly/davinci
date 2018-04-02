package site.binghai.davinci.common.def;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import static site.binghai.davinci.common.utils.TimeTools.currentTS;

/**
 * Created by IceSea on 2018/4/1.
 * GitHub: https://github.com/IceSeaOnly
 * 监控数据包结构
 */
@Data
public class MonitorReport {
    private long start; // 生成时间
    private long end; // 结束时间
    private long rt;
    private String tag; // 监控标签

    private boolean volume; // true数量监控 false RT监控

    private MonitorReport() {
    }

    /**
     * 数量监控埋点
     */
    public static MonitorReport volumeBP(String tag) {
        if (StringUtils.isBlank(tag)) return null;

        MonitorReport m = new MonitorReport();
        m.volume = true;
        m.tag = tag;
        m.start = currentTS();

        return m;
    }

    /**
     * RT监控开始点
     * */
    public static MonitorReport rtStart(String tag) {
        if (StringUtils.isBlank(tag)) return null;

        MonitorReport m = new MonitorReport();
        m.volume = false;
        m.tag = tag;
        m.start = currentTS();

        return m;
    }

    /**
     * RT监控结束点
     * */
    public static MonitorReport rtEnd(MonitorReport m) {
        if (m == null) return null;
        m.end = currentTS();
        m.rt = m.end - m.start;
        return m;
    }
}
