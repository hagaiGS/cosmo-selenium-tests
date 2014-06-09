package gs.ui.tests.cosmo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by erezcarmel on 5/18/14.
 */
public class DeploymentProgressPanel {

    private static Logger logger = LoggerFactory.getLogger(DeploymentProgressPanel.class);

    @FindBy(css = "div#deployment-progress-panel")
    WebElement deploymentProgressPanel;

    @FindBy(css = "div.expand-button")
    WebElement expandButton;

    @FindBy(css = "div.collapse-button")
    WebElement collapseButton;

    @FindBy(css = "div#closedPanel")
    WebElement closedPanel;

//    @FindBy(css = "div#openedPanel")
//    WebElement openedPanel;

    public boolean isPanelVisible() {
        if(!deploymentProgressPanel.isDisplayed()) {
            logger.info("Progress Panel not opened");
            return false;
        }
        return true;
    }

    public void openPanel() {
        if (isPanelOpen()) {
            expandButton.click();
        }
    }

    public void closePanel() {
        if (!isPanelOpen()) {
            collapseButton.click();
        }
    }

    private boolean isPanelOpen() {
        return closedPanel.isDisplayed();
    }
}
