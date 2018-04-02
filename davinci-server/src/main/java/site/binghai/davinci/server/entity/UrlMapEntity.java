package site.binghai.davinci.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 */
@Data
@Entity
public class UrlMapEntity extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;
    private String mapKey;
    private String mapUrl;

    @Override
    public Long getId() {
        return id;
    }
}
