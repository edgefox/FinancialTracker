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
public class AccountService {
    @Inject
    private Provider<EntityManager> em;

    @Transactional
    public void save(Account account) {
        em.get().persist(account);
    }

    public Account getAccount(Account account) {
        final Query query = em.get().createQuery("select a from Account where a=:account");
        query.setParameter(1, account);
        query.setMaxResults(1);
        return (Account) query.getSingleResult();
    }

    @Transactional
    public void saveAll(List<Account> accounts) {
        for (Account transaction : accounts) {
            em.get().merge(transaction);
        }
    }
}
