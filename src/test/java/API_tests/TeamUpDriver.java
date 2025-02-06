package API_tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.*;

public class TeamUpDriver {

    String baseURI = "https://api.teamup.com";
    String TeamupToken = "4db5f96aec9bcdfff7c8091b731b106c51b94330ad175cdd310afcf12aea9b70";
    String token;
    Response response;
    Map<String, String> requestHeader = new HashMap<>();
    String email = "diyorjon.rafikov@sultantrans.com";
    String password = "Friendofdiyor@19";

    @Test
    public void login() {

        String endpoint = "/auth/tokens";

        requestHeader.put("Content-Type", "application/json");
        requestHeader.put("Accept", "application/json");
        requestHeader.put("Teamup-Token", TeamupToken);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("app_name", "My awesome new app");
        requestBody.put("device_id", "My device");
        requestBody.put("email", email);
        requestBody.put("password", password);

        response = RestAssured.given()
                .headers(requestHeader)
                .body(requestBody)
                .when()
                .post(baseURI + endpoint);
        response.then().statusCode(200);
//        response.prettyPrint();
        token = response.path("auth_token");
//        System.out.println(token);
    }

    @Test(dependsOnMethods = "login")
    public void GetdrviersInfo() {
        String endpoint = "/1vpxm6/events";

        requestHeader.put("Content-Type", "application/json");
        requestHeader.put("Accept", "application/json");
        requestHeader.put("Teamup-Token", TeamupToken);
        requestHeader.put("Authorization", token);

        response = RestAssured.given()
                .headers(requestHeader)
                .get(baseURI + endpoint);
//        response.prettyPrint();
//        String title = response.path("events[1].title");
//        String who = response.path("events[1].who");
//        String note = response.path("events[1].notes");
//        System.out.println(title);
//        System.out.println(who);
//        System.out.println(note);
        List<Map<String, String>> eventsList = new ArrayList<>();

        // Get the list of events from the response
        List<Map<String, Object>> events = response.path("events");
        // Count the number of events retrieved
        int eventCount = events.size();
        System.out.println("Number of events retrieved: " + eventCount);


        // Iterate through each event and extract the required fields
        for (Map<String, Object> event : events) {
            String title = (String) event.get("title");
            String who = (String) event.get("who");
            String note = (String) event.get("notes");

            // Create a map to store the fields of the current event
            Map<String, String> eventDetails = new HashMap<>();
            eventDetails.put("title", title);
            eventDetails.put("who", who);
            eventDetails.put("note", note);

            // Add the event details to the list
            eventsList.add(eventDetails);
        }


        // Print the list of events (for verification)
        for (Map<String, String> eventDetails : eventsList) {
            System.out.println("Title: " + eventDetails.get("title"));
            System.out.println("Who: " + eventDetails.get("who"));
            System.out.println("Note: " + eventDetails.get("note"));
            System.out.println("-----------------------------");
        }


   }

}
