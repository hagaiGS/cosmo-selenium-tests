//package gs.ui.tests.cosmo;
//
//import gs.ui.tests.cosmo.pages.BlueprintPage;
//import gs.ui.tests.cosmo.pages.Blueprints;
//import gs.ui.tests.cosmo.pages.CosmoApp;
//import junit.framework.TestCase;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.testng.Assert;
//
///**
// * Created by erezcarmel on 5/19/14.
// */
//public class BlueprintPageTestCase extends TestCase {
//
//    private static Logger logger = LoggerFactory.getLogger(BlueprintPageTestCase.class);
//
//    @Autowired
//    public CosmoApp cosmoApp;
//
//    @Autowired(required = false)
//    public CosmoUITestSuite.Configuration config = new CosmoUITestSuite.Configuration();
//
//    logger.info("Start Blueprint Page Test");
//    cosmoApp.goTo(config.getUrl());
//    Blueprints blueprints = cosmoApp.getBlueprints();
//
//    config.setBlueprintName(config.getBlueprintName()+(12));
//
//    Blueprints.Blueprint blueprint = blueprints.getBlueprintById(config.blueprintName);
//    Assert.assertNotEquals(blueprint, null, "Unable to find blueprint by ID: " + config.blueprintName);
//
//    logger.info("Start testing of blueprint: [{}]", blueprint.getName());
//
//    BlueprintPage blueprintPage = blueprint.open();
//
//    Assert.assertEquals(blueprintPage.numOfTopologyNodes(), 2, "Wrong number of topology nodes");
//    Assert.assertEquals(blueprintPage.numOfNetworks(), 2, "Wrong number of networks");
//    Assert.assertEquals(blueprintPage.numOfSubnets(), 1, "Wrong number of subnets");
//    Assert.assertEquals(blueprintPage.numOfDevices(), 0, "Wrong number of devices");
//    Assert.assertEquals(blueprintPage.numOfNodes(), 4, "Wrong number of nodes");
//    Assert.assertEquals(blueprintPage.isSourceExist(), true, "Source code are empty");
//}
