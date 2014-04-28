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

    @FindBy(css = "div.actions div.multiSelectMenu > div.button")
    WebElement workflowSelect;

    @FindBy(css = "div.actions div.multiSelectMenu > ul > li")
    List<WebElement> workflowSelectList;

    @FindBy(css = "div.actions i.deployment-play")
    WebElement deployPlay;

    @Absolute
    @FindBy(css="body")
    Confirm confirm = null;

    public void setWorkflow(String workflow) {
        workflowSelect.click();
        for(WebElement option : workflowSelectList) {
            if(option.getText().equals(workflow)){
                logger.info("Set workflow to: [{}]", workflow);
                option.click();
            }
        }
    }

    public Confirm deployPlay() {
        deployPlay.click();
        return confirm;
    }

}
