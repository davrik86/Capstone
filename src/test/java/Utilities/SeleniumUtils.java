package Utilities;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;



public class SeleniumUtils {
    Faker faker;
  WebDriver driver = Driver.getDriver();
    public static String product() {
        Faker faker = new Faker();
        String productName = faker.commerce().productName();
        return productName;
    }

    public static String name() {
        Faker faker = new Faker();
        String name = faker.name().firstName();
        return name;
    }

    public static String email() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        return email;
    }
    public static void sendkeysWithActionsClass(WebElement element, String input) {
        Actions actions = new Actions(Driver.getDriver());
        actions.sendKeys(element, input).build().perform();
    }
    public static String randomLongtxt(int lenght) {
        // Create a Random object
        Random random = new Random();
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Create a StringBuilder to store the random string
        StringBuilder sb = new StringBuilder();

        // Generate the random string of specified length

        for (int i = 0; i < lenght; i++) {
            // Get a random index to select a character from the pool
            int randomIndex = random.nextInt(CHARACTERS.length());

            // Append the randomly selected character to the string
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        // Convert StringBuilder to String and return
        return sb.toString();
    }
    public static long measureAlertTime(WebElement triggElim, WebElement alert, int timeoutSeconds) {
        try {

            // Find the trigger element and record the start time
            WebElement triggerElement = triggElim;
            long startTime = System.currentTimeMillis();

            // Click the trigger element
            triggerElement.click();

            // Wait for the alert message to become visible
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated((By) alert));

            // Record the end time
            long endTime = System.currentTimeMillis();
            long time=endTime - startTime;
            // Return the time taken
            return time;
        } catch (TimeoutException e) {
            System.out.println("Alert message did not appear within the expected time.");
            return -1; // Indicate failure to appear within timeout
        }
    }
}
