package net.edgefox.finance.provider;

import com.gargoylesoftware.htmlunit.WebClient;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Created by edgefox on 3/19/16.
 */
@Singleton
public class WebClientProvider implements Provider<WebClient> {

    @Override
    public WebClient get() {
        return new WebClient();
    }
}
