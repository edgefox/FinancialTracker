package net.edgefox.finance.service;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import net.edgefox.finance.entity.Account;
import net.edgefox.finance.entity.Transaction;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by ivan.lyutov on 21/03/16.
 */
@Singleton
public class TransactionService {
    @Inject
    private Provider<EntityManager> em;

    @Transactional
    public void save(Transaction transaction) {
        em.get().persist(transaction);
    }

    public Transaction getLastTransactionForAccount(Account account) {
        final Query query = em.get().createQuery("select t from Transaction as t where t.account=:account order by t.id desc");
        query.setParameter("account", account);
        query.setMaxResults(1);
        final List<Transaction> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            return null;
        }

        return resultList.get(0);
    }

    @Transactional
    public void saveAll(List<Transaction> transactions) {
        final EntityManager entityManager = em.get();
        for (Transaction transaction : transactions) {
            entityManager.merge(transaction);
        }
    }
}
