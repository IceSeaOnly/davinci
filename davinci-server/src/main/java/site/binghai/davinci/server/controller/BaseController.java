package site.binghai.davinci.server.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import site.binghai.davinci.common.utils.BaseBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by IceSea on 2018/5/5.
 * GitHub: https://github.com/IceSeaOnly
 */
public abstract class BaseController extends BaseBean {

    protected String commonResp(String title, String info, String btn, String url, ModelMap map) {
        map.put("title", title);
        map.put("btn", btn);
        map.put("info", info);
        map.put("url", url);
        return "commonResp";
    }

    /**
     * 从thread local获取网络上下文
     */
    public HttpServletRequest getServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes;
        if (requestAttributes instanceof ServletRequestAttributes) {
            servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    public HttpSession getSession() {
        return getServletRequest().getSession();
    }


    public JSONObject fail(String err) {
        JSONObject object = new JSONObject();
        object.put("status", "FAIL");
        object.put("msg", err);
        object.put("code", 1);
        return object;
    }

    public Object jsoupFail(String err, String callback) {
        if (StringUtils.isEmpty(callback)) {
            return fail(err);
        }
        return callback + "(" + fail(err).toJSONString() + ")";
    }

    public JSONObject success() {
        JSONObject object = new JSONObject();
        object.put("status", "SUCCESS");
        object.put("code", 0);
        return object;
    }

    public JSONObject success(String msg) {
        JSONObject object = new JSONObject();
        object.put("status", "SUCCESS");
        object.put("msg", msg);
        object.put("code", 0);
        return object;
    }

    public Object jsoupSuccess(Object data, String msg, String callBack) {
        if (StringUtils.isEmpty(callBack)) {
            return success(data, msg);
        }
        callBack = callBack == null ? "" : callBack;
        return callBack + "(" + success(data, msg).toJSONString() + ")";
    }

    public JSONObject success(Object data, String msg) {
        JSONObject object = new JSONObject();
        object.put("status", "SUCCESS");
        object.put("data", data);
        object.put("msg", msg);
        object.put("code", 0);
        return object;
    }
}
