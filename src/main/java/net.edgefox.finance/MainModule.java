package net.edgefox.finance;

import com.gargoylesoftware.htmlunit.WebClient;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import net.edgefox.finance.provider.WebClientProvider;
import net.edgefox.finance.scraper.bnz.BNZScraper;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by edgefox on 3/19/16.
 */
public class MainModule extends AbstractModule {
    @Inject
    private Application application;

    public static void main(String[] args) {
        MainModule mainModule = new MainModule();
        Injector injector = Guice.createInjector(mainModule);
        injector.injectMembers(mainModule);
    }

    @Override
    protected void configure() {
        bind(WebClient.class).toProvider(WebClientProvider.class);
        try {
            bind(BNZScraper.class).toConstructor(BNZScraper.class.getConstructor(WebClient.class));
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/financial_tracker", "root", "");
            connection.setAutoCommit(true);
            bind(Connection.class).toInstance(connection);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
