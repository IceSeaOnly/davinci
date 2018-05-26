package site.binghai.davinci.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by IceSea on 2018/5/25.
 * GitHub: https://github.com/IceSeaOnly
 */
@Entity
@Data
public class DavinciService extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String methodName;
    private String host;
    private Integer port;
    private String appName;
    private Boolean onLine;

}
