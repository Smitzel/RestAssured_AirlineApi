package apiTests;

import POJO.Airline;
import config.TestConfigAirlinesApiAuth;
import org.junit.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class AirlinesWithAuthTests extends TestConfigAirlinesApiAuth {

    @Test
    public void noAuth() {

        Airline airline = new Airline();

        given()
                .when()
                .body(airline)
                .post("airlines")
                .then()
                .statusCode(400)
                .body("message", equalTo("You must send an Authorization header"))
                .log().all();

    }

    @Test
    public void getWithAuth() {

        Airline airline = new Airline();

        given()
                .when()
                .body(airline)
                .post("airlines")
                .then()
                .statusCode(200)
                .log().all();
    }
}
