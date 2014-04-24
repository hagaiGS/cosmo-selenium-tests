package gs.ui.tests.cosmo.pages;

import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webui.tests.annotations.LazyLoad;
import webui.tests.components.abstracts.AbstractComponent;


/**
 * Created by Itsik on 3/10/14.
 */
public class CosmoApp extends AbstractComponent<CosmoApp>{

    @Autowired
    @LazyLoad
    @FindBy(css = "#main-content")
    public Deployments deployments;

    @Autowired
    @LazyLoad
    @FindBy(css = "#main-content")
    public Blueprints blueprints;

    private static Logger logger = LoggerFactory.getLogger(CosmoApp.class);

    public CosmoApp goTo(String url) {
        try {
            webDriver.get(url);
            return load();
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

    public void setDeployments(Deployments deployments) {
        this.deployments = deployments;
    }

    public void setBlueprints(Blueprints blueprints) {
        this.blueprints = blueprints;
    }
}
