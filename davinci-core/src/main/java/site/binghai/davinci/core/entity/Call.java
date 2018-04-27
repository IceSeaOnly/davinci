package site.binghai.davinci.core.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * GitHub: https://github.com/IceSeaOnly
 */
@Data
public class Call implements Serializable {
    private static final long serialVersionUID = 5386052199960133937L;

    private String className; // 调用的类名或接口名
    private String methodName; // 调用的方法名
    private Class<?>[] paramTypes; // 方法参数类型
    private Object[] params; // 调用方法时传入的参数值
    /**
     * 表示方法的执行结果 如果方法正常执行,则 result 为方法返回值,
     * 如果方法抛出异常,那么 result 为该异常。
     */
    private Object result;

    public Call() {
    }

    public Call(String className, String methodName, Class<?>[] paramTypes, Object[] params) {
        this.className = className;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
    }
}
