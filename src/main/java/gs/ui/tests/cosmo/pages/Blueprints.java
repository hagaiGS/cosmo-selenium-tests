package gs.ui.tests.cosmo.pages;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import webui.tests.annotations.Absolute;
import webui.tests.annotations.LazyLoad;
import webui.tests.annotations.NoEnhancement;
import webui.tests.components.abstracts.AbstractComponent;
import webui.tests.selenium.GsFieldDecorator;

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

    @LazyLoad
    @FindBy(css="div#deployDialogContainer")
    DeployConfirmBox deployConfirmBox = null;

    @FindBy(css="button[ng-click='toggleAddDialog()']")
    WebElement uploadBlueprintBtn = null;

    @FindBy(css="div#uploadDialogContainer")
    WebElement uploadDialogContainer = null;

    @Absolute
    @FindBy(css="body")
    BlueprintPage blueprintPage = null;

    // GUY _ this is a hack until we get this into the test beans framework
    @NoEnhancement
    public <Y > Y load( Y component, SearchContext searchContext) {
        PageFactory.initElements(new GsFieldDecorator(searchContext, webDriver).setSwitchManager(switchManager).setWaitFor(waitFor), component);
        return (Y) this;
    }

    public Blueprints init() {
        load();
        waitForBlueprintsLoaded();
        load();
        grabBlueprints();
        return this;
    }

    private void waitForBlueprintsLoaded(){
        logger.info("will wait for loading hidden");
        waitFor.predicate( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    logger.debug("checking if loading is hidden");
                    WebElement element = getRootElement().findElement(By.cssSelector(".loading"));
                    logger.debug("loading is still visible :: " + element.isDisplayed());
                    if (element.isDisplayed() == false) {
                        logger.info("loading is now hidden..");
                        return true;
                    }
                    return null;

                } catch (NoSuchElementException e) {
                    logger.info("loading is finally hidden!");
                    return true;
                } catch (Exception e) {
                    logger.error("unable to determine if loading is visible or not", e);
                    return null;
                }
            }
        });
    }

    public void grabBlueprints() {
        try {
            for (WebElement blueprintItem : blueprintsDomList) {
                Blueprint blueprint = new Blueprint();
                load(blueprint, blueprintItem);
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

    public static class DeployConfirmBox extends AbstractComponent<DeployConfirmBox>{

        @FindBy(css = "input[ng-model='deploymentId']")
        WebElement deploymentId;

        @FindBy(css = "button#deployBtn" )
        WebElement submitButton;

        @FindBy(css = "div.dialogClose")
        WebElement cancelButton;

        public DeployConfirmBox() {
        }

        public void setDeploymentId(String deploymentName) {
            deploymentId.sendKeys(deploymentName);
        }

        public void submit() {
            submitButton.click();
        }

        public void cancel() {
            cancelButton.click();
        }
    }

    public class Blueprint extends AbstractComponent<Blueprint>  {
        private CreateDeploymentDialog createDeployment = new CreateDeploymentDialog();

        @FindBy(css = "td.name")
        WebElement name;

        @FindBy(css = "div.deployments-number")
        WebElement deploymentsNumber;

        @FindBy( css = "button#deployBtn ")
        WebElement deployButton;

        @Absolute
        @FindBy(css="#deployDialogContainer")
        CreateDeploymentDialog dialog;

        public void init(WebElement webElement) {
            this.webElement = webElement;
            load();
        }

        public boolean equalTo(String id) {
            return getId().equals(id);
        }

        public String getId() {
            return getName(); //the name is the id
        }

        public String getName() {
            return name.getText();
        }

        public int numOfDeployments() {
            return Integer.parseInt(deploymentsNumber.getText());
        }

        public BlueprintPage open() {
            logger.info("Open Blueprint");
            name.click();
            return blueprintPage.load();
        }



        public CreateDeploymentDialog createDeployment() {
            deployButton.click();
            return dialog.load();

        }

        public class CreateDeploymentDialog extends AbstractComponent<CreateDeploymentDialog>{
            protected CreateDeploymentDialog openDialogBox(WebElement blueprint) {
                logger.info("Open Deploy Dialog");
                WebElement deployButton = blueprint.findElement(By.cssSelector("button.deploy-button"));
                deployButton.click();
                deployConfirmBox.load();

                return this;
            }

            public CreateDeploymentDialog enterName(String deploymentName) {
                logger.info("CreateDeploymentDialog enterName: [{}]", deploymentName);
                deployConfirmBox.setDeploymentId(deploymentName);
                return this;
            }

            public Deployments  deploy() {
                logger.info("Deploy!");
                deployConfirmBox.submit();
                dialog.enterName("some name");
                dialog.deploy();
                Deployments deployments = new Deployments();
                return load( deployments, webDriver );

            }


            // GUY _ this is a hack until we get this into the test beans framework
            @NoEnhancement
            public <T > T load( T component, SearchContext searchContext) {
                PageFactory.initElements(new GsFieldDecorator(searchContext, webDriver).setSwitchManager(switchManager).setWaitFor(waitFor), component);
                return (T) this;
            }

            public void cancel() {
                deployConfirmBox.cancel();

            }
        }
    }

}
