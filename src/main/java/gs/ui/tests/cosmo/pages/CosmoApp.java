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
import webui.tests.annotations.LazyLoad;
import webui.tests.annotations.NoEnhancement;
import webui.tests.components.abstracts.AbstractComponent;
import webui.tests.selenium.GsFieldDecorator;

import java.util.NoSuchElementException;


/**
 * Created by Itsik on 3/10/14.
 */
public class CosmoApp extends AbstractComponent<CosmoApp> {

    @Autowired
    @LazyLoad
    @FindBy(css = "#main-content")
    public Deployments deployments;

    @Autowired
    @LazyLoad
    @FindBy(css = "#main-content")
    public Blueprints blueprints;

    @Autowired
    @LazyLoad
    @FindBy(css = "#main-content")
    public Logs logs;

    private static Logger logger = LoggerFactory.getLogger(CosmoApp.class);

    public CosmoApp goTo(String url) {
        try {
            webDriver.get(url);
            return load(webDriver);
        } catch (Exception e) {
            logger.error("unable to load page", e);
            return null;
        }
    }

    public void navigateTo(String url) {
        webDriver.navigate().to(url);
    }

    public void refreshPage() {
        webDriver.navigate().refresh();
    }

    public Blueprints getBlueprints() {
        blueprints.init();
        return blueprints;
    }

    public Deployments getDeployments() {
        deployments.init();
        return deployments;
    }

    public Logs getLogs() {
        logs.init();
        return logs;
    }

    public void setDeployments(Deployments deployments) {
        this.deployments = deployments;
    }

    public void setBlueprints(Blueprints blueprints) {
        this.blueprints = blueprints;
    }
}
