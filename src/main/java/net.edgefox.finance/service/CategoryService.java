package net.edgefox.finance.service;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import net.edgefox.finance.entity.Category;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by ivan.lyutov on 21/03/16.
 */
@Singleton
public class CategoryService {
    @Inject
    private Provider<EntityManager> em;

    @Transactional
    public List<Category> getCategories() {
        final Query query = em.get().createQuery("select c from Category c");
        return (List<Category>)query.getResultList();
    }
}
