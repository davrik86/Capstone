package Utilities;

import com.github.javafaker.Faker;

import java.util.Random;

public class SeleniumUtils {
    Faker faker;

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
}