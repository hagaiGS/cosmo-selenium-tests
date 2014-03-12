package gs.ui.tests.cosmo.pages;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import webui.tests.components.abstracts.AbstractComponent;
import webui.tests.components.conditions.SizeCondition;

import java.util.LinkedList;
import java.util.List;

public class BlueprintsPage extends AbstractComponent<BlueprintsPage> {

    private static Logger logger = LoggerFactory.getLogger(BlueprintsPage.class);

    @FindBy(css="button[ng-click='toggleAddDialog()']")
    WebElement uploadButton;

    @FindBy(css="input#fileInput")
    WebElement browseInput;

    @FindBy(css="button#uploadBtn")
    WebElement doUploadButton;

    @FindBy(css="table.table>tbody>tr")
    List<WebElement> listOfBlueprints;

    @FindBy(css="div#upload-error-message")
    WebElement errorElement;

    @FindBy(css="div.dialogClose")
    WebElement dialogClose;

    @FindBy(css="div.buttons-group>button[value='topology']")
    WebElement topologyTab;

    @FindBy(css="div.buttons-group>button[value='network']")
    WebElement networkTabButton;

    @Autowired
    NetworksTab networksTab;

    @FindBy(css="div.buttons-group>button[value='nodes']")
    WebElement nodesTab;

    @FindBy(css="div.buttons-group>button[value='code']")
    WebElement sourceTab;

    @FindBy(css="div.buttons-group>button[value='events']")
    WebElement eventsTab;

    @FindBy(css="div[ng-switch-when='code']>pre")
    WebElement sourceSection;

    @FindBy(css="div#plans")
    WebElement blueprintPage;

    @FindBy(css="div.bpContainer div.box")
    List<WebElement> topologyNodesList;

    @FindBy(css="div[ng-switch-when='nodes']>table.table>tbody")
    List<WebElement> nodesTableList;

    @FindBy(css="div.network")
    List<WebElement> networksList;

    @FindBy(css="input[ng-model='deploymentId']")
    WebElement deploymentNameInput;

    @FindBy(css="button#deployBtn")
    WebElement deployBtn;

    @FindBy(css="table#deploymentTable>tbody>tr")
    List<WebElement> listOfDeployments;

    @FindBy(css="button#confirmBtn")
    WebElement executeConfirm;

    @FindBy(css="div[ng-switch-when='events']>table.table>tbody")
    List<WebElement> eventsTableList;

    public BlueprintsPage goTo(String url) {
        webDriver.get(url);
        return load();
    }

    public void navigateTo(String url) {
        webDriver.navigate().to(url);
    }

    public void toggleUploadDialog( int toggle ) {
        switch (toggle) {
            case 0:
                dialogClose.click();
                break;
            default:
            case 1:
                uploadButton.click();
                break;
        }
    }

    public void browseAndLoadFile() {
        try {
            browseInput.sendKeys("C:\\Users\\itsik\\MyProjects\\BlueprintPKG\\hello_world_3.tar.gz");
            doUploadButton.click();
        }
        catch (Exception e) {
            throw new RuntimeException("Couldn't browse file", e);
        }
    }

    public void waitingForUploadComplete() {
        try {
            int listSize = listOfBlueprints.size();
            int timeoutSec = 30;
            int numOfLoops = 0;
            while(true) {
                if(numOfLoops >= timeoutSec) {
                    logger.info("Waiting for Upload was terminated due to the expiration of the maximum time.");
                    break;
                }
                if(errorElement.isDisplayed()) {
                    logger.info("Uploading blueprint got error: [{}]", errorElement.getText());
                    break;
                }
                if(listOfBlueprints.size() > listSize) {
                    dialogClose.click();
                    toggleUploadDialog(0);
                    break;
                }
                Thread.sleep(1000);
                numOfLoops++;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Couldn't wait for upload", e);
        }
    }

    public void openBlueprint(int itemIndex) {
        try {
            int timeoutSec = 60;
            int numOfLoops = 0;
            while (true) {
                logger.info("Waiting for blueprints... [{}sec]", numOfLoops);
                if(numOfLoops >= timeoutSec) {
                    throw new RuntimeException("It looks like there are no blueprints at all, or it takes too match time to load them.");
                }
                if(listOfBlueprints.size() > 0) {
                    WebElement selectedTR = listOfBlueprints.get(itemIndex);
                    logger.info("Number of Blueprints: [{}]", listOfBlueprints.size());
                    logger.info("Select blueprint [{}]", selectedTR.findElement(By.cssSelector("td.name")).getText());
                    selectedTR.findElement(By.cssSelector("td.name")).click();
                    break;
                }
                Thread.sleep(1000);
                numOfLoops++;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Couldn't open blueprint", e);
        }
    }

    public void checkIfBlueprintOpened() {
        try {
            int timeoutSec = 60;
            int numOfLoops = 0;
            while(true) {
                if(numOfLoops >= timeoutSec) {
                    throw new RuntimeException("Failed to open the blueprint page.");
                }
                if(blueprintPage.isDisplayed()) {
                    logger.info("The blueprint successfully opened.");
                    break;
                }
                Thread.sleep(1000);
                numOfLoops++;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to check if blueprint page is open.", e);
        }
    }

    public void checkTopology(int checkingData) {
        try {
            if(topologyTab.isDisplayed()) {
                logger.info("Opening topology section...");
                topologyTab.click();

                int timeoutSec = 20;
                int numOfLoops = 0;
                logger.info("Waiting for any drawing of blueprint topology for {} seconds...", timeoutSec);
                while (true) {
                    if(numOfLoops >= timeoutSec) {
                        logger.info("There are no topology nodes...");
                        break;
                    }
                    if(topologyNodesList.size() > 0) {
                        logger.info("Numbers of topology nodes is [{}] and need to be [{}]", topologyNodesList.size(), checkingData);
                        // TODO: Check if number of nodes is correct
                        break;
                    }
                    Thread.sleep(1000);
                    numOfLoops++;
                }
            }
            else {
                logger.info("There are no network for this blueprint...");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Failed checking networks", e);
        }
    }

    public List<BlueprintNetwork> getNetworks(){

        waitFor.size(networksList, SizeCondition.gt(0));

        List<BlueprintNetwork> networks = new LinkedList<BlueprintNetwork>();

        for (WebElement element : networksList) {
            BlueprintNetwork network = new BlueprintNetwork();
            network.init(element);
            networks.add(network);
        }

        return networks;

    }

    public int getSubnetsCount(){
        List<BlueprintNetwork> networks = getNetworks();
        int i = 0;

        for (BlueprintNetwork network : networks) {
            i += network.subnets.size();
        }

        return i;
    }

    public void checkNetwork(int[] checkingData) {
        try {
            if(networkTabButton.isDisplayed()) {
                networkTabButton.click();
                logger.info("Checking for network...");
                int timeoutSec = 30;
                int numOfLoops = 0;
                logger.info("Waiting for any drawing of networks for {} seconds...", timeoutSec);
                while (true) {
                    if(numOfLoops >= timeoutSec) {
                        logger.info("There are no network...");
                        break;
                    }
                    if(networksList.size() > 0) {
                        int realNetworkCount = 0;
                        int realSubnetCount = 0;
                        int realDeviceCount = 0;
                        for(WebElement network : networksList) {
                            int subnetsCount = network.findElements(By.cssSelector("div.subnet")).size();
                            int deviceCount = network.findElements(By.cssSelector("div.device")).size();
                            if(subnetsCount > 0) {
                                realSubnetCount += subnetsCount;
                                realDeviceCount += deviceCount;
                                realNetworkCount++;
                            }
                        }
                        logger.info("Numbers of networks is [{}] and need to be [{}]", realNetworkCount, checkingData[0]);
                        logger.info("Numbers of Subnets is [{}] and need to be [{}]", realSubnetCount, checkingData[1]);
                        logger.info("Numbers of Devices is [{}] and need to be [{}]", realDeviceCount, checkingData[2]);
                        break;
                    }
                    Thread.sleep(1000);
                    numOfLoops++;
                }
            }
            else {
                logger.info("There are no network for this blueprint...");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Failed checking networks", e);
        }
    }

    public void checkNodes(int checkingData) {
        try {
            if(nodesTab.isDisplayed()) {
                logger.info("Opening nodes section...");
                nodesTab.click();

                int timeoutSec = 60;
                int numOfLoops = 0;
                while (true) {
                    if(numOfLoops >= timeoutSec) {
                        logger.info("Failed to locate the nodes table.");
                        break;
                    }
                    if(nodesTableList.size() > 0 && nodesTableList.get(0).isDisplayed()) {
                        logger.info("Numbers of nodes in the table is [{}] and need to be [{}]", nodesTableList.size(), checkingData);
                        // TODO: Check if all nodes exist
                        break;
                    }
                    Thread.sleep(1000);
                    numOfLoops++;
                }
            }
            else {
                logger.info("There are no nodes for this blueprint...");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Failed checking nodes", e);
        }
    }

    public void checkSource() {
        try {
            if(sourceTab.isDisplayed()) {
                logger.info("Opening source section...");
                sourceTab.click();

                int timeoutSec = 10;
                int numOfLoops = 0;
                while (true) {
                    if(numOfLoops >= timeoutSec) {
                        logger.info("Source code are empty...");
                        break;
                    }
                    if(sourceSection.getText() != "") {
                        logger.info("The source code is shown...");
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Failed checking source", e);
        }
    }

    public void createDeployment(int itemIndex, String name) {
        try {
            int timeoutSec = 60;
            int numOfLoops = 0;
            while (true) {
                logger.info("Waiting for deployments... [{}sec]", numOfLoops);
                if(numOfLoops >= timeoutSec) {
                    throw new RuntimeException("It looks like there are no blueprints at all, or it takes too match time to load them.");
                }
                if(listOfBlueprints.size() > 0) {
                    WebElement selectedTR = listOfBlueprints.get(itemIndex);
                    logger.info("Number of deployments: [{}]", listOfBlueprints.size());
                    logger.info("Select deployment: [{}]", selectedTR.findElement(By.cssSelector("td.name")).getText());
                    selectedTR.findElement(By.cssSelector("button.deploy-button")).click();
                    deploymentNameInput.sendKeys(name+"_"+itemIndex);
                    deployBtn.click();
                    break;
                }
                Thread.sleep(1000);
                numOfLoops++;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Couldn't create deployment", e);
        }
    }

    public void executeDeployment(int itemIndex, String name, String action) {
        try {
            int timeoutSec = 60;
            int numOfLoops = 0;
            while (true) {
                logger.info("Waiting for deployments... [{}sec]", numOfLoops);
                if(numOfLoops >= timeoutSec) {
                    throw new RuntimeException("It looks like there are no deployments at all, or it takes too match time to load them.");
                }
                if(listOfDeployments.size() > 0) {
                    WebElement selectedTR = listOfDeployments.get(itemIndex);
                    List<WebElement> deploys = selectedTR.findElements(By.cssSelector("table.deploys>tbody>tr"));
                    WebElement deployItem = null;

                    logger.info("Number of Deployments: [{}]", listOfDeployments.size());
                    logger.info("Trying to locate deployment by name: [{}]", name);

                    try {
                        for(WebElement deploy : deploys) {
                            if(deploy.findElement(By.cssSelector("td.id")).getText().equals(name+"_"+itemIndex)) {
                                deployItem = deploy;
                                logger.info("Found it!");
                                break;
                            }
                        }
                    }
                    catch (Exception e) {
                        throw new RuntimeException("Couldn't locate the deployment...", e);
                    }

                    if(deployItem.equals(null)) {
                        logger.info("Deployment not found...");
                    }
                    else {
                        List<WebElement> selectActions = deployItem.findElements(By.cssSelector("td.actions>select>option"));
                        WebElement applyAction = deployItem.findElement(By.cssSelector("td.actions>i.fa-play-circle-o"));
                        for(WebElement option : selectActions) {
                            if(option.getText().equals(action)) {
                                logger.info("Select action for deployment [{}]", action);
                                option.click();
                                logger.info("Execute it!");
                                applyAction.click();
                                logger.info("Confirm Execute...");
                                executeConfirm.click();
                                break;
                            }
                        }
                    }
                    break;
                }
                Thread.sleep(1000);
                numOfLoops++;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Couldn't create deployment", e);
        }
    }

    public void checkEvents() {
        try {
            if(eventsTab.isDisplayed()) {
                logger.info("Opening events section...");
                eventsTab.click();

                logger.info("Waiting to see if events are coming...");
                int timeoutSec = 120;
                int numOfLoops = 0;
                while (true) {
                    if(numOfLoops >= timeoutSec) {
                        logger.info("Failed to locate any events...");
                        break;
                    }
                    if(eventsTableList.size() > 0 && eventsTableList.get(0).isDisplayed()) {
                        logger.info("Events begin to come...", eventsTableList.size());
                        break;
                    }
                    Thread.sleep(1000);
                    numOfLoops++;
                }
            }
            else {
                logger.info("There are no events...");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Failed checking events", e);
        }
    }

    public NetworksTab switchToNetworks() {
        waitFor.elements(networkTabButton);
        networkTabButton.click();
        networksTab.load();
        return networksTab;
    }
}
