package API_tests;

import Utilities.ConfigurationReader;
import Utilities.SeleniumUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.instanceOf;

/**
 * Given I am an authorized customer of the "Create customer" REST API webservice,
 * When I send a request to the "Creater customer" with the ‘POST’ HTTP method,
 * Then the "create customer"  REST API should:
 * 1. If the request is successful then HTTP Status Code 200 code should be returned.
 * 2. The Response Body should have the following elements:
 *  - “id”: a randomly generated integer value from the server representing the newly created customer.
 *  - “name”: the name of the customer that was provided in the request body.
 *  - “email”: the email of the customer that was provided in the request body.
 * 3. The customer should be created in the application database.
 */



public class CreateCusomerAPItestNG {

    String baseURI=ConfigurationReader.getPropertyValue("craterURL");
    String token;
    Response response;
    Map<String, String> requestHeaders= new HashMap<>();

    String name= SeleniumUtils.name();
    String email = SeleniumUtils.email();
    @Test
    public void login(){


        String endpoint = "/api/v1/auth/login";


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

        String endpoint="/api/v1/customers";

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
        // the format to follow for future assertions
        response.then().body("data.id", instanceOf(Integer.class));



    }

}
