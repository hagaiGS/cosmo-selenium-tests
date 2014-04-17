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
    public void initTestOfBlueprint() {
        logger.info("Start Blueprint Test");

        cosmoApp.goTo(config.url);

        Blueprints blueprints = cosmoApp.getBlueprints();

        logger.info("Number of blueprints [{}], dom size: [{}]", blueprints.numOfBlueprints());
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


    public static class Configuration {

        String url = "http://localhost:9000/";
        String blueprintFile = Configuration.class.getClassLoader().getResource("neutronBlueprint.tar.gz").getPath().substring(1);
        String blueprintName = "Neutron_Blueprint_Test_";
        String getBlueprintById = "Neutron_Blueprint_Test";
        String deploymeanName = "Neutron_Blueprint_Test_Deploy";

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

        public String getGetBlueprintById() {
            return getBlueprintById;
        }

        public void setGetBlueprintById(String getBlueprintById) {
            this.getBlueprintById = getBlueprintById;
        }

        public String getDeploymeanName() {
            return deploymeanName;
        }

        public void setDeploymeanName(String deploymeanName) {
            this.deploymeanName = deploymeanName;
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

