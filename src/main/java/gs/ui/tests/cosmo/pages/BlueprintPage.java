package gs.ui.tests.cosmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webui.tests.components.abstracts.AbstractComponent;

import java.util.List;

/**
 * Created by Itsik on 24-Apr-14.
 */
public class BlueprintPage extends AbstructBlueprint<BlueprintPage> {

    private static Logger logger = LoggerFactory.getLogger(Blueprints.class);

    @FindBy(css="#plans")
    WebElement planPage;

    @FindBy(css="div.buttons-group>button[value='nodes']")
    WebElement nodesTab;

    @FindBy(css="div[ng-switch-when='nodes']>table.table>tbody")
    List<WebElement> nodesList;

    @FindBy(css="div.buttons-group>button[value='code']")
    WebElement sourceTab;

    @FindBy(css="div[ng-switch-when='code']>pre")
    WebElement sourceSection;

    private boolean isBluePrintOpened() {
        waitFor.predicate( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    if (planPage.isDisplayed()) {
                        logger.info("Blueprint page opened...");
                        return true;
                    }
                    return null;
                } catch (Exception e) {
                    logger.info("Blueprint page not opened...", e);
                    return null;
                }
            }
        });
        return false;
    }

    public int numOfNodes() {
        openNodes();
        return nodesList.size();
    }

    public void openNodes() {
        logger.info("Open Nodes...");
        nodesTab.click();
    }

    public boolean isSourceExist() {
        openSource();
        if(sourceSection.getText() != "") {
            return true;
        }
        return false;
    }

    public void openSource() {
        logger.info("Open Source...");
        sourceTab.click();
    }

}
