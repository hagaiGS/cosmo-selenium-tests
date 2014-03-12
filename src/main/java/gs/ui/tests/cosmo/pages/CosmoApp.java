package gs.ui.tests.cosmo.pages;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAware;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import webui.tests.annotations.LazyLoad;
import webui.tests.components.abstracts.AbstractComponent;


/**
 * Created by Itsik on 3/10/14.
 */
public class CosmoApp extends AbstractComponent<CosmoApp>{

    @Autowired
    @LazyLoad
    @FindBy(css = "#main-content")
    public Blueprints blueprints = new Blueprints();

    private static Logger logger = LoggerFactory.getLogger(CosmoApp.class);

    public CosmoApp goTo(String url) {
        webDriver.get(url);
        return load();
    }

    public void navigateTo(String url) {
        webDriver.navigate().to(url);
    }

    public Blueprints getBlueprints() {
        blueprints.load();
        return blueprints;
    }

    public void setBlueprints(Blueprints blueprints) {
        this.blueprints = blueprints;
    }
}
