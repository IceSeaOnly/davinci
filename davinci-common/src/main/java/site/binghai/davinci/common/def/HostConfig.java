package site.binghai.davinci.common.def;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Created by IceSea on 2018/5/20.
 * GitHub: https://github.com/IceSeaOnly
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostConfig {
    private String ip;
    private Integer port;
    private String appName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HostConfig)) return false;
        if (!super.equals(o)) return false;
        HostConfig that = (HostConfig) o;
        return Objects.equals(getIp(), that.getIp()) &&
                Objects.equals(getPort(), that.getPort()) &&
                Objects.equals(getAppName(), that.getAppName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getIp(), getPort(), getAppName());
    }
}
