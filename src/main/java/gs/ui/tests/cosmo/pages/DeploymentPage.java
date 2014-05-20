package gs.ui.tests.cosmo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webui.tests.annotations.Absolute;

import java.util.List;

/**
 * Created by Itsik on 28-Apr-14.
 */
public class DeploymentPage extends AbstructBlueprint<DeploymentPage> {

    private static Logger logger = LoggerFactory.getLogger(DeploymentPage.class);

    @FindBy(css="div.buttons-group>button[value='events']")
    WebElement eventsTab;

    @FindBy(css="div[ng-switch-when] > table > tbody")
    List<WebElement> eventsList;

    @FindBy(css = "div.actions div.multiSelectMenu > div.button")
    WebElement workflowSelect;

    @FindBy(css = "div.actions div.multiSelectMenu > ul > li")
    List<WebElement> workflowSelectList;

    @FindBy(css = "div.actions i.deployment-play")
    WebElement deployPlay;

    @FindBy(css = "div#events-widget-list-container")
    WebElement eventsWidgetContainer;

    @FindBy(css = "div#events-widget-closed-tab")
    WebElement eventsWidgetOpen;

    @FindBy(css = "div#events-widget-header-close-button")
    WebElement eventsWidgetClose;

    @FindBy(css = "div#events-widget-list-container tr")
    List<WebElement> eventsWidgetEventsList;

    @FindBy(css = "div#deployment-progress-panel")
    WebElement deploymentProgressPanel;

    @Absolute
    @FindBy(css="body")
    Confirm confirm = null;

    public void openEvents() {
        eventsTab.click();
    }

    public int numOfEvents() {
        openEvents();
        return eventsList.size();
    }

    public boolean isEventsEmpty() {
        return numOfEvents() > 0 ? false : true;
    }

    public void setWorkflow(String workflow) {
        try {
            workflowSelect.click();
        } catch (Exception e) {
            logger.error("Workflow SelectBox are not clickable", e);
        }
        for (WebElement option : workflowSelectList) {
            if (option.getText().equals(workflow)) {
                logger.info("Set workflow to: [{}]", workflow);
                option.click();
            }
        }
    }

    public Confirm deployPlay() {
        deployPlay.click();
        return confirm;
    }

    public void openEventsWidget() {
        eventsWidgetOpen.click();
    }

    public void closeEventsWidget() {
        eventsWidgetClose.click();
    }

    public boolean isEventsWidgetOpen() {
        return eventsWidgetContainer.isDisplayed();
    }

    public int numOfEventsWidget() {
        return eventsWidgetEventsList.size();
    }

    public boolean isEventsWidgetEmpty() {
        return numOfEventsWidget() > 0 ? false : true;
    }

}
