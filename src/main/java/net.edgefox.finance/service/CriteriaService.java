package net.edgefox.finance.service;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import net.edgefox.finance.entity.Category;
import net.edgefox.finance.entity.Criteria;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by ivan.lyutov on 21/03/16.
 */
@Singleton
public class CriteriaService {
    @Inject
    private Provider<EntityManager> em;

    @Transactional
    public List<Criteria> getLastCriteriasForCategory(Category account) {
        final Query query = em.get().createQuery("select c from Criteria as c where c.category=:category");
        query.setParameter("category", account);
        return (List<Criteria>) query.getResultList();
    }
}
