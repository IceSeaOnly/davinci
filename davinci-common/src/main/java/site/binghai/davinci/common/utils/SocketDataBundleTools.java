package site.binghai.davinci.common.utils;

import com.alibaba.fastjson.JSONObject;
import site.binghai.davinci.common.def.DataBundle;
import site.binghai.davinci.common.enums.DataPackageEnum;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 * socket通信中的规范编解码器
 */
public class SocketDataBundleTools {

    public static String toPostData(DataBundle dataBundle){
        return JSONObject.toJSONString(dataBundle);
    }

    public static DataBundle encodeData(Object data, DataPackageEnum type) {
        DataBundle dataBundle = new DataBundle(data, type);
        return dataBundle;
    }

    public static Object decode(String data) {
        JSONObject obj = JSONObject.parseObject(data);
        DataBundle dataBundle = obj.toJavaObject(DataBundle.class);
        return dataBundle.getData();
    }
}
