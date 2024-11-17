package API_tests;

import Utilities.ConfigurationReader;
import Utilities.SeleniumUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CreateCusomerAPItestNG {
    String baseURI="http://crater.primetech-apps.com/";
    String token;
    Response response;
    Map<String, String> requestHeaders= new HashMap<>();

    String name= SeleniumUtils.name();
    String email = SeleniumUtils.email();
    @Test
    public void login(){


        String endpoint = "api/v1/auth/login";


        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Accept", "application/json");
        requestHeaders.put("company", "1");

        Map<String, String> requestBody= new HashMap<>();
        requestBody.put("username", ConfigurationReader.getPropertyValue("userName"));
        requestBody.put("password", ConfigurationReader.getPropertyValue("userPassword"));
        requestBody.put("device_name", "mobile_app");

        response= RestAssured.given()
                .headers(requestHeaders)
                .body(requestBody)
                .when()
                .post(baseURI+endpoint);
        response.then().statusCode(200);
        token= response.path("token");
        System.out.println(token);

    }

    @Test(dependsOnMethods = "login")
    public void createCustomer(){

        String endpoint="api/v1/customers";

        requestHeaders.put("Authorization", "Bearer "+token);

        Map<String, String> requestBody= new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("email", email);

        response = RestAssured.given()
                .headers(requestHeaders)
                .body(requestBody)
                .when()
                .post(baseURI+endpoint);
        response.then().statusCode(200);
        response.prettyPrint();
        response.path("data.name", String.valueOf(Matchers.equalTo(name)));
        response.path("data.email", String.valueOf(Matchers.equalTo(email)));



    }

}
