package site.binghai.davinci.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import site.binghai.davinci.server.entity.BaseEntity;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 */
public abstract class BaseService<T extends BaseEntity> {
    @Autowired
    private EntityManager entityManager;
    private SimpleJpaRepository<T, Long> daoHolder;

    protected JpaRepository<T, Long> getDao() {
        if (daoHolder != null) {
            return daoHolder;
        }
        daoHolder = new SimpleJpaRepository(getTypeArguement(), entityManager);
        return daoHolder;
    }

    protected Class<T> getTypeArguement() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }


    @Transactional
    public T save(T t) {
        return getDao().save(t);
    }

    /**
     * 更新不存在的记录会失败
     */
    @Transactional
    public T update(T t) {
        if (t.getId() > 0) {
            return save(t);
        }
        return t;
    }

    public T findById(Long id) {
        return getDao().findOne(id);
    }

    @Transactional
    public void delete(Long id) {
        getDao().delete(id);
    }

    @Transactional
    public boolean deleteAll(String confirm) {
        if (confirm.equals("confirm")) {
            getDao().deleteAll();
            return true;
        }
        return false;
    }

    /**
     * 覆盖保存
     */
    @Transactional
    public boolean coverSave(T t) {
        if (getDao().exists(t.getId())) {
            getDao().delete(t);
        }
        getDao().save(t);
        return true;
    }

    public List<T> findByIds(List<Long> ids) {
        return getDao().findAll(ids);
    }

    public List<T> findAll(int limit) {
        return getDao().findAll(new PageRequest(0, limit)).getContent();
    }

    public long count() {
        return getDao().count();
    }

    @Transactional
    public void batchSave(List<T> batch) {
        getDao().save(batch);
    }
}
