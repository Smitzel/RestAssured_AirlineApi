package apiTests;

import POJO.Airline;
import config.TestConfigAirlinesApi;
import io.restassured.response.Response;
import org.junit.*;
import utils.TestData;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class AirlinesTests extends TestConfigAirlinesApi {

    @Test()
    public void printStatusCode() {
        int statusCode =
                given()
                        .log().all()
                        .get("airlines")
                        .getStatusCode();
        System.out.println("The response status is " + statusCode);
    }


    @Test
    public void createAirlineWithRandomData() {
        String payload = "{\n" +
                "    \"_id\":\"" + TestData.getUUID() + "\",\n" +
                "    \"name\": \"" + TestData.getCountry() + "-Airways\",\n" +
                "    \"country\": \"" + TestData.getCountry() + "\",\n" +
                "    \"logo\": \"" + TestData.getLogo() + "\",\n" +
                "    \"slogan\": \"" + TestData.getSlogan() + "\",\n" +
                "    \"head_quaters\": \"" + TestData.getCity() + ", " + TestData.getCountry() + "\",\n" +
                "    \"website\": \"" + TestData.getWebsite() + "\",\n" +
                "    \"established\": \"" + TestData.getYear() + "\"\n" +
                "}";
        given()
                .body(payload)
                .when()
                .post("airlines")
                .then()
                .assertThat()
                .body("__v", equalTo(0))
                .statusCode(200)
                .log().all();
    }

    @Test
    public void createReadAirlineWithRandomData() {

        String name = TestData.getCountry() + "-Airways";

        String payload = "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"country\": \"" + TestData.getCountry() + "\",\n" +
                "    \"logo\": \"" + TestData.getLogo() + "\",\n" +
                "    \"slogan\": \"" + TestData.getSlogan() + "\",\n" +
                "    \"head_quaters\": \"" + TestData.getCity() + ", " + TestData.getCountry() + "\",\n" +
                "    \"website\": \"" + TestData.getWebsite() + "\",\n" +
                "    \"established\": \"" + TestData.getYear() + "\"\n" +
                "}";
        Response response = given()
                .body(payload)
                .when()
                .post("airlines");

        String airlineId = response.jsonPath().getString("_id");
        String airlineName = response.jsonPath().getString("name");
        System.out.println("Airline Name: " + airlineName);
        assertEquals(name, airlineName);

        given()
                .when()
                .get("airlines/" + airlineId)
                .then()
                .assertThat().body("name", equalTo(name));
    }


    @Test
    public void PostAirlineWithFakerInPOJO() {

        Airline airline = new Airline();

        System.out.println("Generated Airline Data: " + airline);

        given()
                .when()
                .body(airline)
                .post("airlines")
                .then()
                .statusCode(200)
                .log().all();

    }


    @Test
    public void PostAirlineWithFakerInPOJOWithUpdatedData() {

        Airline airline = new Airline();

        airline.setEstablished("-1000");

        System.out.println("Generated Airline Data: " + airline);

        given()
                .when()
                .body(airline)
                .post("airlines")
                .then()
                .statusCode(200)
                .log().all();
    }


    @Test
    public void createReadAirlinePOJOWithRandomData() {

        Airline airline = new Airline();

        System.out.println("Generated Airline Data: " + airline);

        given()
                .when()
                .body(airline)
                .post("airlines")
                .then()
                .statusCode(200)
                .log().all();

        Response response = given()
                .body(airline)
                .when()
                .post("airlines");

        String airlineId = response.jsonPath().getString("_id");
        String name = response.jsonPath().getString("name");

        given()
                .when()
                .get("airlines/" + airlineId)
                .then()
                .assertThat().body("name", equalTo(name));
    }

}
