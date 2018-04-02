package site.binghai.davinci.common.enums;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 * socket通信中的数据类型
 */
public enum DataPackageEnum {
    HEART_BEATS(0,"心跳包"),
    ;
    private int code;
    private String name;

    DataPackageEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
