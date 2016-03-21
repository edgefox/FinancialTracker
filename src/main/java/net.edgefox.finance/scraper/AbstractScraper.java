package net.edgefox.finance.scraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.List;

/**
 * Created by edgefox on 3/19/16.
 */
public abstract class AbstractScraper implements Scraper {
    protected WebClient webClient;

    public AbstractScraper(WebClient webClient) {
        this.webClient = webClient;
    }

    public HtmlPage waitPageToLoad(HtmlPage page) throws Exception {
        Thread.sleep(5000);

        return page;
    }

    protected List<DomElement> getElementsByXpath(HtmlPage page, String xpath) throws Exception {
        List<DomElement> byXPath = (List<DomElement>) page.getByXPath(xpath);
        return byXPath;
    }

    protected DomElement getFirstElementByXpath(HtmlPage bankingPage, String xpath) throws Exception {
        return getElementsByXpath(bankingPage, xpath).get(0);
    }
}
