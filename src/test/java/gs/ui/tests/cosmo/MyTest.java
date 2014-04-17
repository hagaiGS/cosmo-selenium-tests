package gs.ui.tests.cosmo;

import gs.ui.tests.cosmo.pages.Blueprints;
import gs.ui.tests.cosmo.pages.CosmoApp;
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

    @Test
    public void initTestOfBlueprintUpload() {
        logger.info("Start Blueprint Upload Test");

        cosmoApp.goTo(config.url);

        Blueprints blueprints = cosmoApp.getBlueprints();

        logger.info("Number of blueprints [{}]", blueprints.numOfBlueprints());
        config.setNumOfBlueprints(blueprints.numOfBlueprints());
        Assert.assertEquals(blueprints.numOfBlueprints(), config.numOfBlueprints, "Wrong number of blueprints");


        Blueprints.UploadBlueprint upload = blueprints.uploadBlueprint();
        config.setBlueprintName(config.getBlueprintName()+(blueprints.numOfBlueprints()+1));
        upload.browse(config.blueprintFile)
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

//    public void initTestOfBlueprintDetails() {
//        logger.info("Start Blueprint Details Test");
//
//        cosmoApp.navigateTo(config.url);
//
//        Blueprints blueprints = cosmoApp.getBlueprints();
//
//        logger.info("Number of blueprints [{}]", blueprints.numOfBlueprints());
//        config.setNumOfBlueprints(blueprints.numOfBlueprints());
//        config.setBlueprintName(config.getBlueprintName()+(blueprints.numOfBlueprints()));
//
//        Blueprints.Blueprint NewBlueprint = blueprints.getBlueprintById(config.blueprintName);
//        Assert.assertNotEquals(NewBlueprint, null, "Unable to find blueprint by ID: "+config.blueprintName);
//
//        logger.info("Name of blueprint: [{}]", NewBlueprint.getName());
//        logger.info("Number of deployment: [{}]", NewBlueprint.numOfDeployments());
//        config.setNumOfDeployments(NewBlueprint.numOfDeployments());
//        Assert.assertEquals(NewBlueprint.numOfDeployments(), config.numOfDeployments, "Wrong number of deployments");
//
//        Blueprints.Blueprint.CreateDeployment NewBlueprintDeploy = NewBlueprint.createDeployment();
//
//        logger.info("NewBlueprintDeploy [{}]", NewBlueprintDeploy);
//
//        NewBlueprintDeploy
//                .enterName(NewBlueprint.getName()+"_Deploy")
//                .deploy();
//
//        logger.info("Blueprint successfully deployed!");
//    }


    public static class Configuration {

        String url = "http://localhost:9000/";
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

