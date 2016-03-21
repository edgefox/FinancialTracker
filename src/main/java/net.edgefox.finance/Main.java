package net.edgefox.finance;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

import javax.inject.Inject;

/**
 * Created by edgefox on 3/19/16.
 */
public class Main extends AbstractModule {
    @Inject
    private Application application;

    public static void main(String[] args) {
        Main main = new Main();
        Injector injector = Guice.createInjector(main);
        injector.injectMembers(main);
    }

    @Override
    protected void configure() {
        install(new JpaPersistModule("financialTrackerUnit"));
    }
}
