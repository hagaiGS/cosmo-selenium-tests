package gs.ui.tests.cosmo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import webui.tests.annotations.FirstDisplayed;
import webui.tests.components.abstracts.AbstractComponent;

public class GoogleSearchPage extends AbstractComponent<GoogleSearchPage> {


    @FindBy(css="[name=q]")
    WebElement searchInput;


    @FindBy(css="button[aria-label='Google Search'], input[type='submit'],table>tbody>tr>td>table>tbody>tr>td>div>div>span>span>input")
    @FirstDisplayed
    WebElement searchButton;

    public void search( String term ){
        searchInput.sendKeys(term);
        searchButton.click();
    }

    public GoogleSearchPage goTo() {
        webDriver.get("http://www.google.com");

        return load();
    }
}