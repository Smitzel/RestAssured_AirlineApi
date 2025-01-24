package helper;

import POJO.Passenger;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static utils.TestData.faker;
import static utils.TestVariableManager.*;
import static utils.TestVariables.*;

public class PassengerHelper {

    public static void postPassenger() {
        Passenger passenger = new Passenger();
        String airlineId = GetVariable(AIRLINE_ID);
        passenger.setAirline(airlineId);
        System.out.println("Generated Passenger Data: " + passenger);
        Response createPassenger = given()
                .when()
                .body(passenger)
                .post("passenger");
        createPassenger.then().statusCode(200);
        SetVariable(PASSENGER_ID, createPassenger.jsonPath().getString("_id"));
        SetVariable(PASSENGER_NAME, createPassenger.jsonPath().getString("name"));
        SetVariable(PASSENGER_TRIPS, createPassenger.jsonPath().getString("trips"));
    }

    public static void readPassenger(int expectedStatusCode) {
        Response getPassenger = given()
                .when()
                .get("passenger/" + GetVariable(PASSENGER_ID));
        getPassenger.then().statusCode(expectedStatusCode);

        switch (expectedStatusCode) {
            case 200:
                SetVariable(PASSENGER_ID_GET, getPassenger.jsonPath().getString("_id"));
                SetVariable(PASSENGER_NAME_GET, getPassenger.jsonPath().getString("name"));
                SetVariable(PASSENGER_TRIPS_GET, getPassenger.jsonPath().getString("trips"));
                SetVariable(AIRLINE_ID_GET, (String) getPassenger.jsonPath().getList("airline._id").get(0));
                break;
            case 204:
                System.out.println("Status 204: Passenger Deleted, no content.");
                break;
            default:
                System.out.println("Unexpected statuscode: " + expectedStatusCode);
                throw new IllegalArgumentException("Unexpected statuscode " + expectedStatusCode);
        }
    }

    public static void patchPassenger() {
        SetVariable(PASSENGER_NAME_PATCH, faker.name().fullName());
        String patchBody = "{\"name\":\"" + GetVariable(PASSENGER_NAME_PATCH) + "\"}";
        System.out.println("Patchedbody: " + patchBody);
        given()
                .when()
                .body(patchBody)
                .patch("passenger/" + GetVariable(PASSENGER_ID))
                .then()
                .statusCode(200)
                .assertThat().body("message", equalTo("Passenger data updated successfully."));
    }

    public static void deletePassenger() {
        Response deletePassenger = given()
                .when()
                .delete("passenger/" + GetVariable(PASSENGER_ID));
        String responseMessage = deletePassenger.jsonPath().getString("message");
        assertEquals("Passenger data deleted successfully.", responseMessage);
    }
}
