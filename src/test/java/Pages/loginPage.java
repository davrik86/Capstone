package Pages;

import Utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class loginPage {

    public loginPage()
        {
            PageFactory.initElements(Driver.getDriver(), this);
        }

    @FindBy(xpath = "//input[@name='email']")
    public WebElement loginEmail;

    @FindBy(xpath = "//input[@name='password']")
    public WebElement loginPassword;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement loginButton;

    @FindBy(xpath = "//p[@class='mb-3']")
    public WebElement loginCopyright;

    @FindBy(xpath = "(//a[@href='/admin/dashboard'])[2]")
    public WebElement dashTab;

    @FindBy(xpath = "(//a[@href='/admin/items'])")
    public WebElement itemTab;



//    @FindBy(xpath = "")
//    public WebElement;




}
