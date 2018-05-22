package site.binghai.davinci.common.enums;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 * socket通信中的数据类型
 */
public enum DataPackageEnum {
    HEART_BEATS(0,"心跳包"),
    DAVINCI_CLIENT(1,"davinci工作机证明"),
    DAVINCI_SERVER(2,"davinci种子机证明"),
    TEST_TO_SERVER(3,"工作机发往种子机的测试信号"),
    TEST_TO_CLIENT(4,"种子机发往工作机的测试信号"),
    SERVICE_MAP_DATA(5,"种子机下发的最新服务列表"),
    POST_LOCAL_SERVICES(6,"工作机广播本地服务列表"),

    ;
    private int code;
    private String name;

    DataPackageEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
