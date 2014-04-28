package gs.ui.tests.cosmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webui.tests.annotations.Absolute;
import webui.tests.annotations.LazyLoad;
import webui.tests.annotations.NoEnhancement;
import webui.tests.components.abstracts.AbstractComponent;
import webui.tests.selenium.GsFieldDecorator;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Itsik on 24-Apr-14.
 */
public class Deployments extends AbstractComponent<Deployments> {

    public List<Deployment> deployments = new LinkedList<Deployment>();
    private static Logger logger = LoggerFactory.getLogger(Deployments.class);

    @FindBy(css="table#deploymentTable>tbody")
    List<WebElement> deploymentsDomList = null;

    @Absolute
    @FindBy(css="body")
    DeploymentPage deploymentPage = null;

    @Absolute
    @FindBy(css="body")
    Confirm confirm = null;

    // GUY _ this is a hack until we get this into the test beans framework
    @NoEnhancement
    public <T > T load( T component, SearchContext searchContext) {
        PageFactory.initElements(new GsFieldDecorator(searchContext, webDriver).setSwitchManager(switchManager).setWaitFor(waitFor), component);
        return (T) this;
    }

    public Deployments init() {
        load();
        waitForDeploymentsLoaded();
        grabDeployments();
        return this;
    }

    private void waitForDeploymentsLoaded() {
        logger.info("will wait for loading hidden");
        waitFor.predicate( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    logger.debug("checking if loading is hidden");
                    WebElement element = getRootElement().findElement(By.cssSelector(".loading"));
                    logger.debug("loading is still visible :: " + element.isDisplayed());
                    if (element.isDisplayed() == false) {
                        logger.info("loading is now hidden..");
                        return true;
                    }
                    return null;

                } catch (NoSuchElementException e) {
                    logger.info("loading is finally hidden!");
                    return true;
                } catch (Exception e) {
                    logger.error("unable to determine if loading is visible or not", e);
                    return null;
                }
            }
        });
    }

    private void grabDeployments() {
        try {
            for (WebElement deployItem : deploymentsDomList) {
                Deployment deployment = new Deployment();
                load(deployment, deployItem);
                deployment.init(deployItem);
                deployments.add(deployment);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Can't wait for deploymentsDomList", e);
        }
    }

    public Deployment getDeploymentById(String deployemntID) {
        for(Deployment deployment : deployments) {
            if(deployment.equalTo(deployemntID)) {
                return deployment;
            }
        }
        return null;
    }

    public class Deployment extends AbstractComponent<Deployment> {

        @FindBy(css = "td.id")
        WebElement name;

        @FindBy(css = "div.multiSelectMenu")
        WebElement workflowSelect;

        @FindBy(css = "div.multiSelectMenu ul > li")
        List<WebElement> workflowSelectList;

        @FindBy(css = "i.deployment-play")
        WebElement deployPlay;

        @FindBy(css = "i.deployment-pause")
        WebElement deployPause;

        public void init(WebElement webElement) {
            this.webElement = webElement;
            load();
        }

        public boolean equalTo(String id) {
            return getId().equals(id);
        }

        public String getId() {
            return getName(); //the name is the id
        }

        public String getName() {
            return name.getText();
        }

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

        public DeploymentPage open() {
            logger.info("Open Deployment");
            name.click();
            return deploymentPage.load();
        }

    }

}
