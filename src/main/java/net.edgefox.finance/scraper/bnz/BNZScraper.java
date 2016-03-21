package net.edgefox.finance.scraper.bnz;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.edgefox.finance.scraper.AbstractScraper;
import net.edgefox.finance.entity.Account;
import net.edgefox.finance.entity.Transaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by edgefox on 3/18/16.
 */
@Singleton
public class BNZScraper extends AbstractScraper {
    private final String accessId;
    private final String password;
    private static final String BNZ_URL = "https://www.bnz.co.nz/ib/app/login";

    @Inject
    public BNZScraper(WebClient webClient) throws IOException {
        super(webClient);
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("finance.properties"));
        accessId = properties.getProperty("access.id");
        password = properties.getProperty("access.password");
    }

    public List<Transaction> exportStatements(Account account, Date startDate, Date endDate) throws Exception {
        try {
            HtmlPage bankingPage = login();
            HtmlPage accountHome = bankingPage.getElementById(String.format("account-%s", account.getNaturalId())).click();
            DomElement transactionPanel = accountHome.getElementById(String.format("transactions-panel-%s", account.getNaturalId()));
            HtmlElement exportButton = transactionPanel
                    .getFirstElementChild()
                    .getFirstElementChild()
                    .getLastElementChild()
                    .getElementsByTagName("button").get(1);

            HtmlPage exportModalOpened = waitPageToLoad(exportButton.click());
            DomElement saveButton = getFirstElementByXpath(exportModalOpened, "/html/body/div[1]/div[2]/div[3]/div[4]/button[2]");

            Page csv = saveButton.click();
            String accountTransactionsRaw = csv.getWebResponse().getContentAsString();

            return parseTransactions(accountTransactionsRaw, account);
        } finally {
            webClient.getCookieManager().clearCookies();
        }
    }

    private List<Transaction> parseTransactions(String accountTransactionsRaw, Account account) throws ParseException {
        List<Transaction> transactions = new ArrayList<>();
        String[] rows = accountTransactionsRaw.split("\\r\\n");
        for (String row : Arrays.copyOfRange(rows, 1, rows.length)) {
            String[] fields = row.split(",");
            if (fields.length != 7) {
                throw new IllegalArgumentException();
            }

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
            Transaction transaction = new Transaction();
            transaction.setDate(df.parse(fields[0]));
            transaction.setAmount(new BigDecimal(fields[1]));
            transaction.setPayee(fields[2]);
            transaction.setParticulars(fields[3]);
            transaction.setCode(fields[4]);
            transaction.setReference(fields[5]);
            transaction.setTransactionType(fields[6]);
            transaction.setAccount(account);
            transactions.add(transaction);
        }

        return transactions;
    }

    @Override
    public List<Account> getAccounts() throws Exception {
        HtmlPage bankingPage = login();
        DomElement accountContainer = bankingPage.getElementById("accounts");
        List<Account> accounts = StreamSupport.stream(accountContainer.getChildElements().spliterator(), false).map((element) -> {
            DomElement accountInfoElement = element.getLastElementChild();
            String accountName = accountInfoElement.getFirstElementChild().getTextContent();
            if (accountName.equals("Add an account")) {
                return null;
            }

            String balanceText = accountInfoElement.getLastElementChild().getFirstElementChild().getTextContent();
            BigDecimal balance = new BigDecimal(balanceText.replaceAll(",", ""));

            Account account = new Account();
            account.setNaturalId(element.getId().replaceAll("^account-", ""));
            account.setName(accountName);
            account.setBalance(balance);

            return account;
        }).filter(account -> account != null).collect(Collectors.toList());

        return accounts;
    }

    private HtmlPage login() throws Exception {
        HtmlPage loginPage = waitPageToLoad(webClient.getPage(BNZ_URL));
        loginPage.getElementById("accessId").setAttribute("value", accessId);
        loginPage.getElementById("password").setAttribute("value", password);

        return waitPageToLoad(loginPage.getElementById("login").click());
    }
}
