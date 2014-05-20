package gs.ui.tests.cosmo;

import gs.ui.tests.cosmo.pages.*;
import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
@ContextConfiguration("classpath:context.xml")
public class MyTest extends AbstractTestNGSpringContextTests {

    private static Logger logger = LoggerFactory.getLogger(MyTest.class);

    @Autowired
    public CosmoApp cosmoApp;

    @Autowired(required = false)
    public Configuration config = new Configuration();

    private Blueprints blueprints;
    private Deployments deployments;
    private Logs logs;

    @Test(groups = "uploadBlueprint")

    public void testBlueprintUpload() {
        logger.info("Start Blueprint Upload Test");
        cosmoApp.goTo(config.url);
        blueprints = cosmoApp.getBlueprints();
        String filePathFirstChar = "";

        logger.info("Number of blueprints [{}]", blueprints.numOfBlueprints());
        config.setNumOfBlueprints(blueprints.numOfBlueprints());
        Assert.assertEquals(blueprints.numOfBlueprints(), config.numOfBlueprints, "Wrong number of blueprints");


        Blueprints.UploadBlueprint upload = blueprints.uploadBlueprint();
        config.setBlueprintName(config.getBlueprintName() + (blueprints.numOfBlueprints() + 1));
        if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX) {
            filePathFirstChar = "/";
        }
        upload.browse(filePathFirstChar + config.blueprintFile)
                .enterName(config.blueprintName)
                .upload();

        logger.info("is upload succeeded? [{}]", upload.isUploadSucceeded());
        logger.info("Error Message [{}]", upload.error());

        Assert.assertEquals(upload.isUploadSucceeded(), true, upload.error());

        if(upload.isUploadSucceeded()) {
            upload.close();
            logger.info("Blueprint successfully uploaded and the list of blueprints was updated!");
        }

    }

    @Test( dependsOnGroups =  "uploadBlueprint")

    public void testOfBlueprintPage() {
        logger.info("Start Blueprint Page Test");
        cosmoApp.goTo(config.url);
        blueprints = cosmoApp.getBlueprints();

        config.setBlueprintName(blueprints.blueprints.get(0).getName());
//        config.setBlueprintName(config.getBlueprintName()+(12));

        Blueprints.Blueprint blueprint = blueprints.blueprints.get(0);
//        Blueprints.Blueprint blueprint = blueprints.getBlueprintById(config.blueprintName);
        Assert.assertNotEquals(blueprint, null, "Unable to find blueprint by ID: "+config.blueprintName);

        logger.info("Start testing of blueprint: [{}]", blueprint.getName());

        BlueprintPage blueprintPage = blueprint.open();

        Assert.assertEquals(blueprintPage.numOfTopologyNodes(), 2, "Wrong number of topology nodes");
        Assert.assertEquals(blueprintPage.numOfNetworks(), 2, "Wrong number of networks");
        Assert.assertEquals(blueprintPage.numOfSubnets(), 1, "Wrong number of subnets");
        Assert.assertEquals(blueprintPage.numOfDevices(), 0, "Wrong number of devices");
        Assert.assertEquals(blueprintPage.numOfNodes(), 4, "Wrong number of nodes");
        Assert.assertEquals(blueprintPage.isSourceExist(), true, "Source code are empty");
    }

    @Test(dependsOnGroups = "uploadBlueprint")
    public void testBlueprintDetails() {
        logger.info("Start Blueprint Details Test");
        cosmoApp.goTo(config.url);
        blueprints = cosmoApp.getBlueprints();

        logger.info("Number of blueprints [{}]", blueprints.numOfBlueprints());
        config.setNumOfBlueprints(blueprints.numOfBlueprints());
        config.setBlueprintName(blueprints.blueprints.get(0).getName());
//        config.setBlueprintName(config.getBlueprintName()+(blueprints.numOfBlueprints()));

        Blueprints.Blueprint NewBlueprint = blueprints.getBlueprintById(config.blueprintName);
        Assert.assertNotEquals(NewBlueprint, null, "Unable to find blueprint by ID: "+config.blueprintName);

        logger.info("Name of blueprint: [{}]", NewBlueprint.getName());
        logger.info("Number of deployment: [{}]", NewBlueprint.numOfDeployments());
        config.setNumOfDeployments(NewBlueprint.numOfDeployments());
        Assert.assertEquals(NewBlueprint.numOfDeployments(), config.numOfDeployments, "Wrong number of deployments");

        Blueprints.Blueprint.CreateDeployment NewBlueprintDeploy = NewBlueprint.createDeployment();

        logger.info("NewBlueprintDeploy [{}]", NewBlueprintDeploy);

        NewBlueprintDeploy
                .enterName(NewBlueprint.getName()+"_Deploy")
                .deploy();

        logger.info("Blueprint successfully deployed!");

    }

    @Test( dependsOnGroups = "uploadBlueprint", groups = "deployment")
    public void testDeployment() {
        logger.info("Start Deployments Test");
        cosmoApp.goTo(config.deploymentUrl);
        deployments = cosmoApp.getDeployments();

        Deployments.Deployment deployment = deployments.getDeploymentById("Neutron_Blueprint_Test_45_Deploy");
        DeploymentProgressPanel deploymentProgressPanel = new DeploymentProgressPanel();

        logger.info("Start testing of deployment: [{}]", deployment.getName());

        deployment.setWorkflow("install");
        deployment.deployPlay().cancel();

        DeploymentPage deploymentPage = deployment.open();

        if(deploymentPage.isEventsWidgetOpen()) {
            deploymentPage.closeEventsWidget();
        }

        deploymentPage.openTopology();
        deploymentPage.setWorkflow("install");
        deploymentPage.deployPlay();
        deploymentPage.deployPlay().cancel();

        Assert.assertEquals(deploymentPage.numOfTopologyNodes(), 2, "Wrong number of topology nodes");
        Assert.assertEquals(deploymentPage.numOfNetworks(), 2, "Wrong number of networks");
        Assert.assertEquals(deploymentPage.numOfSubnets(), 1, "Wrong number of subnets");
        Assert.assertEquals(deploymentPage.numOfDevices(), 0, "Wrong number of devices");
        Assert.assertEquals(deploymentProgressPanel.isPanelVisible(), true, "Deployment progress panel is not visible");

    }

    @Test (dependsOnGroups = "deployment")
    public void testLogs() {
        logger.info("Start Logs Test");
        cosmoApp.goTo(config.logsUrl);
        logs = cosmoApp.getLogs();

        logs.filterBlueprints("guy11");
        logs.filterDeployments("dep111");
        logs.filterExecutions("install (2014-04-10 00:39:03)");
        logs.filterTimeframe("5 Days");
        logs.show();

        if(logs.isLogsEmpty()) {
            logs.waitForLogs();
            logger.info("Number Of Logs: [{}]", logs.numOfLogs());
        }
    }

    public static class Configuration {

        String url = "http://cosmo.gsdev.info/";
        String deploymentUrl = "http://cosmo.gsdev.info/#/deployments";
        String logsUrl = "http://cosmo.gsdev.info/#/logs";
        String blueprintFile = Configuration.class.getClassLoader().getResource("neutronBlueprint.tar.gz").getPath().substring(1);
        String blueprintName = "Neutron_Blueprint_Test_";

        int numOfBlueprints = 1;
        int numOfDeployments = 0;


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBlueprintFile() {
            return blueprintFile;
        }

        public void setBlueprintFile(String blueprintFile) {
            this.blueprintFile = blueprintFile;
        }

        public String getBlueprintName() {
            return blueprintName;
        }

        public void setBlueprintName(String blueprintName) {
            this.blueprintName = blueprintName;
        }

        public int getNumOfBlueprints() {
            return numOfBlueprints;
        }

        public void setNumOfBlueprints(int numOfBlueprints) {
            this.numOfBlueprints = numOfBlueprints;
        }

        public int getNumOfDeployments() {
            return numOfDeployments;
        }

        public void setNumOfDeployments(int numOfDeployments) {
            this.numOfDeployments = numOfDeployments;
        }
    }
}

