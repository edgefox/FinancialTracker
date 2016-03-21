package net.edgefox.finance.scraper;

import net.edgefox.finance.entity.Account;
import net.edgefox.finance.entity.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Created by edgefox on 3/19/16.
 */
public interface Scraper {
    List<Transaction> exportStatements(Account account, Date startDate, Date endDate) throws Exception;
    List<Account> getAccounts() throws Exception;
}
