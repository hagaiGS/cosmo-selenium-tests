package gs.ui.tests.cosmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webui.tests.components.abstracts.AbstractComponent;

import java.util.NoSuchElementException;

/**
 * Created by Itsik on 28-Apr-14.
 */
public class Confirm extends AbstractComponent<Confirm> {

    @FindBy(css="#confirmationDialogContainer")
    WebElement confirmDialog;

    @FindBy(css="button#confirmBtn")
    WebElement confirmBtn;

    @FindBy(css="button#cancelBtn")
    WebElement cancelBtn;

    private static Logger logger = LoggerFactory.getLogger(Confirm.class);

    private void waitForConfirm() {
        waitFor.predicate( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    return confirmDialog.isDisplayed() ? true : null;
                }
                catch (NoSuchElementException e) {
                    logger.info("Confirm Dialog is not Displayed");
                    return null;
                }
                catch (Exception e) {
                    logger.error("unable to determine if confirm dialog is displayed", e);
                    return null;
                }
            }
        });
    }

    private boolean isConfirmOpen() {
        waitForConfirm();
        if(!confirmDialog.isDisplayed()) {
            logger.info("Confirm Model not opened");
            return false;
        }
        return true;
    }

    public void confirm() {
        if(isConfirmOpen()) {
            confirmBtn.click();
        }
    }

    public void cancel() {
        if(isConfirmOpen()) {
            cancelBtn.click();
        }
    }

}
