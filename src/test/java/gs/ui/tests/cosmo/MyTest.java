package gs.ui.tests.cosmo;

import gs.ui.tests.cosmo.pages.BlueprintsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test
@ContextConfiguration("classpath:context.xml")
public class MyTest extends AbstractTestNGSpringContextTests {

    private static Logger logger = LoggerFactory.getLogger(MyTest.class);

    @Autowired
    public BlueprintsPage blueprintPage;

    // Configs
    String blueprintURL = "http://localhost:9000/#/blueprints";
    String deploymenTest = "TestDeployment";
    String executeAction = "install";
    int blueprintItem = 5;

    // Checking Data
    int topology = 2; // Topology nodes
    int[] network = {1, 1, 0}; // Networks, Subnets, Devices
    int nodes = 2; // Table nodes

    @Test
    public void exampleTest(){
        logger.info("## Starting test blueprints...");
        blueprintPage.goTo(blueprintURL);

        logger.info("## Start sequence of uploading blueprint");
        blueprintPage.toggleUploadDialog(1);
        blueprintPage.browseAndLoadFile();
        blueprintPage.waitingForUploadComplete();
        blueprintPage.toggleUploadDialog(0);
        logger.info("## Sequence of uploading blueprint ended");

        logger.info("## Start sequence of checking blueprint");
        blueprintPage.openBlueprint(blueprintItem);
        blueprintPage.checkIfBlueprintOpened();
        blueprintPage.checkTopology(topology);
        blueprintPage.checkNetwork(network);
        blueprintPage.checkNodes(nodes);
        blueprintPage.checkSource();
        logger.info("## Sequence of checking blueprint ended");

        logger.info("## Start sequence of checking deployment");
        blueprintPage.navigateTo(blueprintURL);
        blueprintPage.createDeployment(blueprintItem, deploymenTest);
        blueprintPage.executeDeployment(blueprintItem, deploymenTest, executeAction);
        blueprintPage.checkTopology(topology);
        blueprintPage.checkNetwork(network);
        blueprintPage.checkEvents();
        logger.info("## Sequence of checking deployment ended");

        logger.info("## end test blueprints");
    }
}