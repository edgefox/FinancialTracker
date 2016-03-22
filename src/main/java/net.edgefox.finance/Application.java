package net.edgefox.finance;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;
import net.edgefox.finance.entity.Account;
import net.edgefox.finance.entity.Category;
import net.edgefox.finance.entity.Criteria;
import net.edgefox.finance.entity.Transaction;
import net.edgefox.finance.scraper.bnz.BNZScraper;
import net.edgefox.finance.service.AccountService;
import net.edgefox.finance.service.CategoryService;
import net.edgefox.finance.service.CriteriaService;
import net.edgefox.finance.service.TransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by edgefox on 3/19/16.
 */
public class Application {
    private static final Log log = LogFactory.getLog(Application.class);
    private final SimpleDateFormat dateFormat;
    @Inject
    private BNZScraper bnzScraper;
    @Inject
    private TransactionService transactionService;
    @Inject
    private AccountService accountService;
    @Inject
    private CategoryService categoryService;
    @Inject
    private CriteriaService criteriaService;
    private final ScheduledExecutorService threadPool;

    @Inject
    public Application(PersistService persistService) {
        persistService.start();
        threadPool = Executors.newSingleThreadScheduledExecutor();
        dateFormat = new SimpleDateFormat("yy-MM-dd");
    }

    @Inject
    public void run() throws Exception {
        threadPool.scheduleAtFixedRate((Runnable) () -> {
            try {
                List<Account> accounts = bnzScraper.getAccounts();
                accountService.saveAll(accounts);

                for (Account account : accounts) {
                    final Transaction lastTransactionForAccount = transactionService.getLastTransactionForAccount(account);
                    List<Transaction> transactions = bnzScraper.exportStatements(account, null, null);
                    if (lastTransactionForAccount != null && !transactions.isEmpty()) {
                        int currentTransactionIndex = transactions.size() - 1;
                        List<Transaction> transactionsToSave = new ArrayList<>();
                        while (!lastTransactionForAccount.equals(transactions.get(currentTransactionIndex)) && currentTransactionIndex >= 0) {
                            transactionsToSave.add(transactions.get(currentTransactionIndex));
                            currentTransactionIndex--;
                        }

                        Collections.reverse(transactionsToSave);
                        transactions = transactionsToSave;
                    }

                    for (Transaction transaction : transactions) {
                        selectCategory(transaction);
                    }
                    transactionService.saveAll(transactions);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    public void selectCategory(Transaction transaction) {
        final List<Category> categories = categoryService.getCategories();
        CategoryLoop:
        for (Category category : categories) {
            final List<Criteria> criterias = criteriaService.getLastCriteriasForCategory(category);
            for (Criteria criteria : criterias) {
                if ((transaction.getPayee().contains(criteria.getPayeePattern()) && criteria.getReferencePattern() == null) ||
                    (transaction.getPayee().contains(criteria.getPayeePattern()) && transaction.getReference().contains(criteria.getReferencePattern()))) {
                    transaction.setCategory(category);
                    break CategoryLoop;
                }
            }
        }
    }
}
