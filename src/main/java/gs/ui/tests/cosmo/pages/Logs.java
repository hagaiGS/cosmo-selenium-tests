package gs.ui.tests.cosmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webui.tests.components.abstracts.AbstractComponent;

import java.lang.reflect.Array;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Itsik on 29-Apr-14.
 */
public class Logs extends AbstractComponent<Logs> {

    private static Logger logger = LoggerFactory.getLogger(Logs.class);

    @FindBy(css = "#main-content-panel")
    WebElement header;

    @FindBy(css="div.content-section > table.table > tbody")
    List<WebElement> logsList = null;

    @FindBy(css = "div[name='blueprints'].multiSelectMenu")
    WebElement filterBlueprint;

    @FindBy(css = "div[name='blueprints'].multiSelectMenu ul > li")
    List<WebElement> filterBlueprintList;

    @FindBy(css = "div[name='deployments'].multiSelectMenu")
    WebElement filterDeployment;

    @FindBy(css = "div[name='deployments'].multiSelectMenu ul > li")
    List<WebElement> filterDeploymentList;

    @FindBy(css = "div[name='executions'].multiSelectMenu")
    WebElement filterExecution;

    @FindBy(css = "div[name='executions'].multiSelectMenu ul > li")
    List<WebElement> filterExecutionList;

    @FindBy(css = "div[name='timeframe'].multiSelectMenu")
    WebElement filterTimeframe;

    @FindBy(css = "div[name='timeframe'].multiSelectMenu ul > li")
    List<WebElement> filterTimeframeList;

    @FindBy(css = "div.filters > button.gs-btn")
    WebElement showBtn;


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

    private void waitForFilterList(final List<WebElement> filterBlueprintList) {
        logger.info("Wait for filter list...");
        waitFor.predicate( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    if (filterBlueprintList.size() > 0) {
                        logger.info("Filter list loaded...");
                        return true;
                    }
                    return null;

                } catch (NoSuchElementException e) {
                    logger.info("Unable to find filter list in the filter", e);
                    return true;
                } catch (Exception e) {
                    logger.error("Unable to determine if filter list was loaded...", e);
                    return null;
                }
            }
        });
    }

    public void filterBlueprints(String blueprintID) {
        filterBlueprint.click();
        waitForFilterList(filterBlueprintList);
        for(WebElement option : filterBlueprintList) {
            if(option.getText().equals(blueprintID)){
                logger.info("Set blueprint filter to: [{}]", blueprintID);
                option.click();
            }
        }
        header.click();
    }

    public void filterDeployments(String deploymentID) {
        filterDeployment.click();
        waitForFilterList(filterDeploymentList);
        for(WebElement option : filterDeploymentList) {
            if(option.getText().equals(deploymentID)){
                logger.info("Set deployment filter to: [{}]", deploymentID);
                option.click();
            }
        }
        header.click();
    }

    public void filterExecutions(String executionID) {
        filterExecution.click();
        waitForFilterList(filterExecutionList);
        for(WebElement option : filterExecutionList) {
            if(option.getText().equals(executionID)){
                logger.info("Set execution filter to: [{}]", executionID);
                option.click();
            }
        }
        header.click();
    }

    public void filterTimeframe(String timeframe) {
        filterTimeframe.click();
        waitForFilterList(filterTimeframeList);
        for(WebElement option : filterTimeframeList) {
            if(option.getText().equals(timeframe)){
                logger.info("Set execution filter to: [{}]", timeframe);
                option.click();
            }
        }
        header.click();
    }

    public void show() {
        showBtn.click();
    }


}
