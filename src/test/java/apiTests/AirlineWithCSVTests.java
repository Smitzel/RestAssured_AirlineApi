package apiTests;

import config.TestConfigAirlinesApi;
import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AirlineWithCSVTests {

    // Endpoint URL
    private static final String BASE_URL = "https://api.instantwebtools.net/v1/airlines";

    @ParameterizedTest
    @CsvFileSource(resources = "/airlines_testdata.csv", numLinesToSkip = 1)
//    numLinesToSkip will skip the first line/header
    void testPostAirline(String name, String country, String logo, String slogan,
                         String headQuarters, String website, String established) {
        // Create JSON-body
        String jsonBody = String.format(
                "{" +
                        "\"name\": \"%s\"," +
                        "\"country\": \"%s\"," +
                        "\"logo\": \"%s\"," +
                        "\"slogan\": \"%s\"," +
                        "\"head_quaters\": \"%s\"," +
                        "\"website\": \"%s\"," +
                        "\"established\": \"%s\"" +
                        "}",
                name, country, logo, slogan, headQuarters, website, established);

        // Execute POST request
        Response response = given()
                .contentType("application/json")
                .body(jsonBody).log().all()
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(200) // Controleer of de statuscode 200 is
                .extract()
                .response();

        // Check if the name in the response matches
        String returnedName = response.jsonPath().getString("name");
        assertEquals(name, returnedName, "Name doesn't match!");
    }
}
