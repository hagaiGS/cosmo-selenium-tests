package gs.ui.tests.cosmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webui.tests.components.abstracts.AbstractComponent;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Itsik on 29-Apr-14.
 */
public class Logs extends AbstractComponent<Logs> {

    private static Logger logger = LoggerFactory.getLogger(Logs.class);

    @FindBy(css="div.content-section > table.table > tbody")
    List<WebElement> logsList = null;

    public Logs init() {
        load();
        return this;
    }

    public int numOfLogs() {
        return logsList.size();
    }

    public boolean isLogsEmpty() {
        return numOfLogs() > 0 ? false : true;
    }

    public void waitForLogs() {
        logger.info("Wait for logs...");
        waitFor.predicate( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    if (logsList.size() > 0) {
                        logger.info("Logs are now loaded...");
                        return true;
                    }
                    return null;

                } catch (NoSuchElementException e) {
                    logger.info("Unable to find logs element", e);
                    return true;
                } catch (Exception e) {
                    logger.error("Unable to determine if logs is not empty anymore", e);
                    return null;
                }
            }
        });
    }

}
