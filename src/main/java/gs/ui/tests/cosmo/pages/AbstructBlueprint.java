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
 * Created by Itsik on 28-Apr-14.
 */
public abstract class AbstructBlueprint<T extends AbstractComponent> extends AbstractComponent<T> {

    private static Logger logger = LoggerFactory.getLogger(AbstructBlueprint.class);

    @FindBy(css="div.buttons-group>button[value='topology']")
    WebElement topologyTab;

    @FindBy(css="div.bpContainer div.box")
    List<WebElement> topologyNodesList;

    @FindBy(css="div.buttons-group>button[value='network']")
    WebElement networkTab;

    @FindBy(css="div.network")
    List<WebElement> networkNetworksList;

    @FindBy(css="div.subnet")
    List<WebElement> networkSubnetsList;

    @FindBy(css="div.device")
    List<WebElement> networkDevicesList;

    public int numOfTopologyNodes() {
        openTopology();
        return topologyNodesList.size();
    }

    public void openTopology() {
        logger.info("Open Topology...");
        topologyTab.click();
        logger.info("Wait for topology to be draw...");
        waitFor.predicate( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    if (topologyNodesList.size() > 0) {
                        logger.info("Topology drawn...");
                        return true;
                    }
                    return null;
                } catch (Exception e) {
                    logger.info("There are no topology nodes...", e);
                    return null;
                }
            }
        });
    }

    public int numOfNetworks() {
        openNetwork();
        return networkNetworksList.size();
    }

    public int numOfSubnets() {
        openNetwork();
        return networkSubnetsList.size();
    }

    public int numOfDevices() {
        openNetwork();
        return networkDevicesList.size();
    }

    public void openNetwork() {
        logger.info("Open Network...");
        networkTab.click();
        logger.info("Wait for network to be draw...");
        waitFor.predicate( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    if (networkNetworksList.size() > 1) {
                        logger.info("Topology drawn...");
                        return true;
                    }
                    return null;
                } catch (Exception e) {
                    logger.info("There are no networks...", e);
                    return null;
                }
            }
        });
    }

}
