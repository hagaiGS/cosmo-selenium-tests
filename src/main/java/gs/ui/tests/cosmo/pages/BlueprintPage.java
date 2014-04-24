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
public class BlueprintPage extends AbstractComponent<BlueprintPage> {

    private static Logger logger = LoggerFactory.getLogger(Blueprints.class);

    @FindBy(css="#plans")
    WebElement planPage;

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
