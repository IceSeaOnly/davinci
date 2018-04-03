package site.binghai.davinci.common.enums;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 * socket通信中的数据类型
 */
public enum DataPackageEnum {
    HEART_BEATS(0,"心跳包"),
    DAVINCI_CLIENT(1,"davinci客户端证明"),
    DAVINCI_SERVER(2,"davinci服务端证明"),
    TEST_TO_SERVER(3,"客户端发往服务端的测试信号"),
    TEST_TO_CLIENT(4,"服务端发往客户端的测试信号"),
    ;
    private int code;
    private String name;

    DataPackageEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
