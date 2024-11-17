package Pages;

import Utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class itemsPage {

    public itemsPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//button[text()=' Add Item']")
    public WebElement addItemBtn;

    @FindBy(xpath = "//h3[text()='New Item']")
    public WebElement itemsNewItemLbl;

    @FindBy(xpath = "(//input[@type='text'])[2]")
    public WebElement itemsNameInput;

    @FindBy(xpath = "(//input[@type='text'])[3]")
    public WebElement itemsUnit;

    @FindBy(xpath = "//input[@type='tel']")
    public WebElement itemsPrice;

    @FindBy(xpath = "//textarea[@name='description']")
    public WebElement itemsDescription;

    @FindBy(xpath = "//button[.=' Save Item']")
    public WebElement itemsSaveBtn;

    @FindBy(xpath = "//p[text()='Success!']")
    public WebElement itemsMessageSuccess;

    @FindBy(xpath = "//p[text()='Item created successfully']")
    public WebElement itemsMessage2;

//    @FindBy(xpath = "")
//    public WebElement;

}
