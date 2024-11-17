package Utilities;

import com.github.javafaker.Faker;

public class SeleniumUtils {
Faker faker;
    public static String funnyname(){
    Faker faker= new Faker();
    String productName=faker.commerce().productName();
    return productName;
    }
}
