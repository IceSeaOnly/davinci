package site.binghai.davinci.server.entity;

import site.binghai.davinci.common.utils.TimeTools;

import javax.persistence.MappedSuperclass;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 */
@MappedSuperclass
public abstract class BaseEntity {
    private Boolean hasDeleted;
    private Long created;
    private String createdTime;

    public BaseEntity() {
        hasDeleted = false;
        created = TimeTools.currentTS();
        createdTime = TimeTools.format(created);
    }

    public abstract Long getId();

    public Boolean getHasDeleted() {
        return hasDeleted;
    }

    public void setHasDeleted(Boolean hasDeleted) {
        this.hasDeleted = hasDeleted;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
