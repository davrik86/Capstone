package Stepdefinitions;

import Pages.itemsPage;
import Pages.loginPage;
import Utilities.ConfigurationReader;
import Utilities.DBUtils;
import Utilities.Driver;
import Utilities.SeleniumUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.sl.In;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
/**
 * Given I am an external user of the “Prime Tech Invoice Application”,
 * And I have logged in to the application,
 * And I click on the ‘Items’ Menu Link,
 * And I click on “+ Add Item”,
 * When I enter the details for the following sections:
 * NAME.
 * UNIT.
 * PRICE.
 * DESCRIPTION
 * And I do not have any validation errors,
 * And I click on the ‘Save Item’ button,
 * Then:
 * -I should see a flash message “Success!
 * - The flash message should disappear within 5 seconds or less.
 * - The user should be navigated to the Items Page.
 * - The user should be able to view item added within the Items Table.
 * - The item information entered for the following sections should be saved in the application database:
 * NAME.
 * UNIT.
 * PRICE.
 * DESCRIPTION
 */

import static Utilities.SeleniumUtils.sendkeysWithActionsClass;


public class CreateItemSD {

    String BaseURL= "http://crater.primetech-apps.com/";
    WebDriver driver= Driver.getDriver();
    loginPage loginPage = new loginPage();
    itemsPage itemsPage= new itemsPage();


    String name1= SeleniumUtils.product();
    String price1= "180.00";
    String description= SeleniumUtils.randomLongtxt(120);
    String unit="pc";
    String query="select * from CraterDBS.items order by created_at desc;";
    String unitQuery="SELECT i.name, description, price, u.name AS 'Unit' " +
            "FROM items i " +
            "INNER JOIN units u " +
            "ON i.unit_id = u.id " +
            "WHERE i.name=\""+name1+"\";";
    @Given("I am an external user of the Prime Tech Invoice Application,")
    public void i_am_an_external_user_of_the_prime_tech_invoice_application() throws InterruptedException {

        driver.get(ConfigurationReader.getPropertyValue("craterURL"));
        String actualURL=driver.getCurrentUrl();
        driver.getCurrentUrl();

        Assert.assertEquals(BaseURL,actualURL);
        Assert.assertTrue(loginPage.loginButton.isDisplayed());

        loginPage.loginEmail.sendKeys(ConfigurationReader.getPropertyValue("userName"));
        loginPage.loginPassword.sendKeys(ConfigurationReader.getPropertyValue("userPassword"));
        loginPage.loginButton.click();
        Thread.sleep(2000);
    }
    @When("I have logged in to the application,")
    public void i_have_logged_in_to_the_application() {
        Assert.assertEquals( (BaseURL+"admin/dashboard"), driver.getCurrentUrl());
        Assert.assertTrue(loginPage.dashTab.isDisplayed());

    }
    @And("I click on the ‘Items’ Menu Link,")
    public void i_click_on_the_items_menu_link() throws InterruptedException {
        loginPage.itemTab.click();
        Thread.sleep(1000);
    }
    @When("I click on + Add Item,")
    public void i_click_on_add_item() throws InterruptedException {
        itemsPage.addItemBtn.click();
        Thread.sleep(1000);


    }
    @Then("I enter the details for name, pc, price, description.")
    public void i_enter_the_details_for_pc_price_description()  throws InterruptedException {
        Assert.assertEquals(BaseURL+"admin/items/create", driver.getCurrentUrl());
        Assert.assertTrue(itemsPage.itemsNewItemLbl.isDisplayed());
        itemsPage.itemsNameInput.sendKeys(name1);
        itemsPage.itemsPrice.sendKeys(price1);
        itemsPage.itemsDescription.sendKeys(description);
        Thread.sleep(1000);
        itemsPage.itemsUnit.click();
        Thread.sleep(1000);
        sendkeysWithActionsClass(itemsPage.itemsUnit,unit);
        itemsPage.itemsUnit.sendKeys(Keys.ENTER);
        long actualTime=SeleniumUtils.measureAlertTime(itemsPage.itemsSaveBtn, itemsPage.itemsMessageSuccess,5000);
        System.out.println("time alert was visible in miliseoncd:"+ actualTime);
        Assert.assertTrue(actualTime>5);
        Thread.sleep(1000);

    }

    @Then("I should be able to see Success message and validate entry in UI and DB")
    public void i_should_be_able_to_see_success_message_and_validate_entry_in_ui_and_db() {
        //UI part
        Assert.assertEquals(BaseURL+"admin/items", driver.getCurrentUrl());
        Assert.assertTrue(itemsPage.itemsMessageSuccess.isDisplayed());
        Assert.assertEquals("Success!",itemsPage.itemsMessageSuccess.getText());
        Assert.assertTrue(itemsPage.itemsMessage2.isDisplayed());
        Assert.assertEquals("Item created successfully",itemsPage.itemsMessage2.getText());
        Assert.assertTrue(driver.findElement(By.xpath("//td[.='"+name1+"']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'"+price1+"')]")).isDisplayed());

        //Back end part
        String actualName= DBUtils.selectRecord(query, "name");
        Assert.assertEquals(name1,actualName);
        String actualDescription= DBUtils.selectRecord(query, "description");
        Assert.assertEquals(description,actualDescription);
        String actualID= DBUtils.selectRecord(unitQuery,"Unit");
        Assert.assertEquals(unit,actualID);
        String actualPrice= DBUtils.selectRecord(unitQuery,"price");
        //converting String to double value
        double doubleValue = Double.parseDouble(price1);
        //converting double to String value
        int intValue = (int) doubleValue;
        //multiplying by 100 to match the DB value of price
        int dbprice= intValue*100;
        System.out.println(dbprice);
        Assert.assertEquals(dbprice, Integer.parseInt(actualPrice));


    }
}
