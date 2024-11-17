package Stepdefinitions;

import Utilities.ConfigurationReader;
import Utilities.SeleniumUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CreateCusomerAPItestNG {

    @Test
    public void createCustomer(){
       String baseURI="http://crater.primetech-apps.com/";
       String token;

        String endpoint = "api/v1/auth/login";

        Map<String, String> requestHeaders= new HashMap<>();
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Accept", "application/json");
        requestHeaders.put("company", "1");

        Map<String, String> requestBody= new HashMap<>();
        requestBody.put("username", ConfigurationReader.getPropertyValue("userName"));
        requestBody.put("password", ConfigurationReader.getPropertyValue("userPassword"));
        requestBody.put("device_name", "mobile_app");

        Response response= RestAssured.given()
                .headers(requestHeaders)
                .body(requestBody)
                .when()
                .get(baseURI+endpoint);
        response.then().statusCode(200);
        token= response.path("token");
        System.out.println(token);





    }


}
