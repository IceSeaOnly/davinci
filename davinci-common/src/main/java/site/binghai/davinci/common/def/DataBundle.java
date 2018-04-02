package site.binghai.davinci.common.def;

import lombok.Data;
import site.binghai.davinci.common.enums.DataPackageEnum;
import site.binghai.davinci.common.utils.TimeTools;
import java.util.UUID;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 * socket通信中的传输对象
 */
@Data
public class DataBundle {
    private String uuid;
    private Long timestamp;
    private Object data;
    private DataPackageEnum type;

    public DataBundle(Object data, DataPackageEnum type) {
        this.uuid = UUID.randomUUID().toString();
        this.timestamp = TimeTools.currentTS();
        this.data = data;
        this.type = type;
    }

    public DataBundle() {
    }
}
