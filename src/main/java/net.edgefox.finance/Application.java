package net.edgefox.finance;

import com.google.inject.Inject;
import net.edgefox.finance.scraper.Account;
import net.edgefox.finance.scraper.Transaction;
import net.edgefox.finance.scraper.bnz.BNZScraper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by edgefox on 3/19/16.
 */
public class Application {
    private static final String INSERT_TEMPLATE = "insert into transactions(date, amount, payee, particulars, code, reference, transaction_type) values %s";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
    @Inject
    private BNZScraper bnzScraper;
    @Inject
    private Connection dbConnection;

    @Inject
    public void run() throws Exception {
        List<Account> accounts = bnzScraper.getAccounts();
        for (Account account : accounts) {
            saveTransactions(bnzScraper.exportStatements(account, null, null));
        }
    }

    public void saveTransactions(List<Transaction> transactions) throws SQLException {
        String[] inserts = new String[transactions.size()];
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            String date = dateFormat.format(transaction.getDate());
            inserts[i] = String.format("('%s', %s, '%s', '%s', '%s', '%s', '%s')",
                    date,
                    transaction.getAmount(),
                    StringEscapeUtils.escapeEcmaScript(transaction.getPayee()),
                    StringEscapeUtils.escapeEcmaScript(transaction.getParticulars()),
                    StringEscapeUtils.escapeEcmaScript(transaction.getCode()),
                    StringEscapeUtils.escapeEcmaScript(transaction.getReference()),
                    StringEscapeUtils.escapeEcmaScript(transaction.getTransactionType()));
        }
        Statement statement = dbConnection.createStatement();
        String insert = String.format(INSERT_TEMPLATE, StringUtils.join(inserts, ","));
        statement.execute(insert);
    }
}
