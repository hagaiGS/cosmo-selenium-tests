package gs.ui.tests.cosmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import webui.tests.components.abstracts.AbstractComponent;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Itsik on 3/10/14.
 */
public class Blueprints extends AbstractComponent<Blueprints> {

    public List<Blueprint> blueprints = new LinkedList<Blueprint>();
    private static Logger logger = LoggerFactory.getLogger(Blueprints.class);
    private UploadBlueprint uploadBlueprint = new UploadBlueprint();

    @FindBy(css="table.table>tbody")
    List<WebElement> blueprintsDomList = null;

    @FindBy(css="div#confirmationDialogContainer")
    WebElement deployConfirmBox = null;

    @FindBy(css="button[ng-click='toggleAddDialog()']")
    WebElement uploadBlueprintBtn = null;

    @FindBy(css="div#uploadDialogContainer")
    WebElement uploadDialogContainer = null;


    private void waitForBlueprintsLoaded(){
        logger.info("will wait for loading hidden");
        waitFor.predicate( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try{
                    logger.debug("checking if loading is hidden");
                    WebElement element = getRootElement().findElement(By.cssSelector(".loading"));
                    logger.debug("loading is still visible :: " + element.isDisplayed());
                    return element.isDisplayed() ? null : true;

                }catch( NoSuchElementException e){
                    logger.info("loading is finally hidden!");
                    return true;
                }catch(Exception e){
                    logger.error("unable to determine if loading is visible or not",e);
                    return null;
                }
            }
        });
    }

    @Override
    public Blueprints load() {
        super.load();
        waitForBlueprintsLoaded();
        super.load();
        init();
        return this;
    }

    public void init() {
        try {
            for (WebElement blueprintItem : blueprintsDomList) {
                Blueprint blueprint = new Blueprint();
                blueprint.init(blueprintItem);
                blueprints.add(blueprint);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Can't wait for blueprintsDomList", e);
        }
    }

    public int numOfBlueprints() {
        return blueprints.size();
    }

    public Blueprint getBlueprintById(String blueprintID) {
        for(Blueprint blueprint : blueprints) {
            if(blueprint.equalTo(blueprintID)) {
                return blueprint;
            }
        }
        return null;
    }

    public UploadBlueprint uploadBlueprint() {
        return uploadBlueprint.openDialogBox();
    }


    public class UploadBlueprint {
        protected UploadBlueprint openDialogBox() {
            uploadBlueprintBtn.click();
            waitFor.elements(uploadDialogContainer);
            return this;
        }

        public UploadBlueprint browse(String filePath) {
            uploadDialogContainer.findElement(By.cssSelector("input[name='fileInput']")).sendKeys(filePath);
            return this;
        }

        public UploadBlueprint enterName(String blueprintName) {
            uploadDialogContainer.findElement(By.cssSelector("input[ng-model='blueprintName']")).sendKeys(blueprintName);
            return this;
        }

        public void close() {
            if(uploadDialogContainer.isDisplayed()) {
                uploadDialogContainer.findElement(By.cssSelector("div.dialogClose")).click();
                if(isUploadSucceeded()) {
                    waitForBlueprintsLoaded();
                }
            }
        }

        public UploadBlueprint upload() {
            WebElement uploadBtn = uploadDialogContainer.findElement(By.cssSelector("button#uploadBtn"));
            uploadBtn.click();
            waitForUpload();
            return this;
        }

        private void waitForUpload() {
            waitFor.predicate( new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver webDriver) {
                    try {
                        WebElement element = getRootElement().findElement(By.cssSelector("button#uploadBtn"));
                        return element.isEnabled() ? true : null;
                    }
                    catch (NoSuchElementException e) {
                        logger.info("Can't fine the button");
                        return null;
                    }
                    catch (Exception e) {
                        logger.error("unable to determine if button is enabled", e);
                        return null;
                    }
                }
            });
        }

        public boolean isUploadSucceeded() {
            return uploadDialogContainer.findElement(By.cssSelector("div#upload-error-message")).getText().isEmpty();
        }

        public String error() {
            return uploadDialogContainer.findElement(By.cssSelector("div#upload-error-message")).getText();
        }
    }

    public class Blueprint {
        private WebElement blueprintElement;
        private CreateDeployment createDeployment = new CreateDeployment();

        public void init(WebElement webElement) {
            blueprintElement = webElement;
        }

        public boolean equalTo(String id) {
            return blueprintElement.findElement(By.cssSelector("td.name")).getText().equals(id);
        }

        public String getId() {
            return blueprintElement.findElement(By.cssSelector("td.name")).getText();
        }

        public String getName() {
            return blueprintElement.findElement(By.cssSelector("td.name")).getText();
        }

        public int numOfDeployments() {
            return Integer.parseInt(blueprintElement.findElement(By.cssSelector("div.deployments-number")).getText());
        }

        public CreateDeployment createDeployment() {
            return createDeployment.openDialogBox(blueprintElement);
        }

        public class CreateDeployment {
            protected CreateDeployment openDialogBox(WebElement blueprint) {
                WebElement deployButton = blueprint.findElement(By.cssSelector("button.deploy-button"));
                deployButton.click();
                waitFor.elements(deployConfirmBox);
                return this;
            }

            public CreateDeployment enterName(String deploymentName) {
                deployConfirmBox.findElement(By.cssSelector("input[ng-model='deploymentId']")).sendKeys(deploymentName);
                return this;
            }

            public void deploy() {
                deployConfirmBox.findElement(By.cssSelector("button#deployBtn")).click();
            }

            public void cancel() {
                deployConfirmBox.findElement(By.cssSelector("button#cancelBtn")).click();
            }
        }
    }

}
