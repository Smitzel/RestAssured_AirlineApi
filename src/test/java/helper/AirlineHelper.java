package helper;

import POJO.Airline;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static utils.TestVariableManager.*;
import static utils.TestVariables.*;

public class AirlineHelper {

    public static void postAirline() {
        Airline airline = new Airline();
        System.out.println("Generated Airline Data: " + airline);
        Response response = given()
                .body(airline)
                .when()
                .post("airlines");
        response.then().statusCode(200);
        SetVariable(AIRLINE_ID, response.jsonPath().getString("_id"));
    }
}
