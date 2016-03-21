package net.edgefox.finance.scraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by edgefox on 3/19/16.
 */
public abstract class AbstractScraper implements Scraper {
    protected WebClient webClient;
    private static final int BACKOFF_STEP_SECONDS = 5;
    private static final int MAX_BACKOFF_SECONDS = 60;

    public AbstractScraper(WebClient webClient) {
        this.webClient = webClient;
    }

    public HtmlPage waitPageToLoad(HtmlPage page, String expectedElementIdToAppear) throws Exception {
        DomElement elementById = page.getElementById(expectedElementIdToAppear);
        int timeout = 0;
        while (elementById == null && timeout < MAX_BACKOFF_SECONDS) {
            timeout += BACKOFF_STEP_SECONDS;
            TimeUnit.SECONDS.sleep(timeout);
            elementById = page.getElementById(expectedElementIdToAppear);
        }

        if (elementById == null) {
            throw new IllegalStateException(String.format("Unable to load page within %d seconds timeout", MAX_BACKOFF_SECONDS));
        }

        return page;
    }

    protected List<DomElement> getElementsByXpath(HtmlPage page, String xpath) throws Exception {
        return (List<DomElement>) page.getByXPath(xpath);
    }

    protected DomElement getFirstElementByXpath(HtmlPage bankingPage, String xpath) throws Exception {
        return getElementsByXpath(bankingPage, xpath).get(0);
    }
}
