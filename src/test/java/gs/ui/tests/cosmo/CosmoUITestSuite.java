//package gs.ui.tests.cosmo;
//
//import gs.ui.tests.cosmo.pages.CosmoApp;
//import junit.framework.TestCase;
//import junit.textui.TestRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//
///**
// * Created by erezcarmel on 5/19/14.
// */
//@ContextConfiguration("classpath:context.xml")
//public class CosmoUITestSuite extends TestCase {
//
//    @Autowired
//    public CosmoApp cosmoApp;
//
//    @Autowired(required = false)
//    public Configuration config = new Configuration();
//
//    public static junit.framework.Test suite()
//    {
//        junit.framework.TestSuite suite = new junit.framework.TestSuite();
//
//        suite.addTestSuite(BlueprintPageTestCase.class);
//        return suite;
//    }
//
//    public static void main(String arg[])
//    {
//        TestRunner.run(suite());
//    }
//
//    public static class Configuration {
//
//        String url = "http://localhost:9000/";
//        String deploymentUrl = "http://localhost:9000/#/deployments";
//        String logsUrl = "http://localhost:9000/#/logs";
//        String blueprintFile = Configuration.class.getClassLoader().getResource("neutronBlueprint.tar.gz").getPath().substring(1);
//        String blueprintName = "Neutron_Blueprint_Test_";
//
//        int numOfBlueprints = 1;
//        int numOfDeployments = 0;
//
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//
//        public String getBlueprintFile() {
//            return blueprintFile;
//        }
//
//        public void setBlueprintFile(String blueprintFile) {
//            this.blueprintFile = blueprintFile;
//        }
//
//        public String getBlueprintName() {
//            return blueprintName;
//        }
//
//        public void setBlueprintName(String blueprintName) {
//            this.blueprintName = blueprintName;
//        }
//
//        public int getNumOfBlueprints() {
//            return numOfBlueprints;
//        }
//
//        public void setNumOfBlueprints(int numOfBlueprints) {
//            this.numOfBlueprints = numOfBlueprints;
//        }
//
//        public int getNumOfDeployments() {
//            return numOfDeployments;
//        }
//
//        public void setNumOfDeployments(int numOfDeployments) {
//            this.numOfDeployments = numOfDeployments;
//        }
//    }
//}
