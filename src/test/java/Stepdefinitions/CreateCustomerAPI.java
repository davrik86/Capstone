package Stepdefinitions;

import Utilities.ConfigurationReader;
import Utilities.DBUtils;
import Utilities.SeleniumUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;
/**
 * Cucumber version
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

public class CreateCustomerAPI {
    String baseURL=ConfigurationReader.getPropertyValue("craterURL");
    String token;
    Response response;
    String name= SeleniumUtils.name();
    String email = SeleniumUtils.email();
    Map<String,String> requestHeaders=new HashMap<>();
    Map<String,String>  requestBody=new HashMap<>();
    String query="select *from CraterDBS.customers order by created_at desc;";


    @Given("I am an authorized customer of the Create customer REST API webservice,")
    public void i_am_an_authorized_customer_of_the_create_customer_rest_api_webservice() {
        String endpoint = "/api/v1/auth/login";

        Map<String,String> requestHeaders=new HashMap<>();
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
                .post(baseURL+endpoint);
        response.then().statusCode(200);
        token= response.path("token");
        System.out.println(token);

    }
    @When("I send a request to the customer webservice with the ‘POST’ HTTP method,")
    public void i_send_a_request_to_the_customer_webservice_with_the_post_http_method() {


//      Request Headers
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Accept", "application/json");
        requestHeaders.put("Authorization", "Bearer "+token);

//      Request Body
        requestBody.put("name",name);
        requestBody.put("email",email);




    }
    @Then("the create customer  REST API should have status code {int}")
    public void the_create_customer_rest_api_should_have_status_code(Integer code) {
        String endpoint= "/api/v1/customers";
        response = RestAssured.given()
                .headers(requestHeaders)
                .body(requestBody)
                .when()
                .post(baseURL+endpoint);
        response.then().statusCode(code);

    }
    @Then("Respond Body should have id\\(randomly gen), name and email matching the POST request body")
    public void respond_body_should_have_id_randomly_gen_name_and_email_matching_the_post_request_body() {
//        response.prettyPrint();
        response.then().body("data.name", Matchers.equalTo(name));
        response.then().body("data.email", Matchers.equalTo(email));
        String actualName= DBUtils.selectRecord(query, "name");
        Assert.assertEquals(name,actualName);
        String actualEmail= DBUtils.selectRecord(query, "email");
        Assert.assertEquals(email,actualEmail);

    }
}
