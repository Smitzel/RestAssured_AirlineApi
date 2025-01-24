package apiTests;

import POJO.Airline;
import POJO.Passenger;
import config.TestConfigAirlinesApi;
import helper.AirlineHelper;
import helper.PassengerHelper;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
import static utils.TestData.faker;
import static utils.TestVariableManager.GetVariable;
import static utils.TestVariables.*;

public class PassengerTests extends TestConfigAirlinesApi {

    @Test
    public void CRUDPassenger() {
//    create airline and store id
        Airline airline = new Airline();
        System.out.println("Generated Airline Data: " + airline);
        Response response = given()
                .body(airline)
                .when()
                .post("airlines");
        String airlineId = response.jsonPath().getString("_id");

//    CREATE passenger with the stored airlineId
        Passenger passenger = new Passenger();
        passenger.setAirline(airlineId);
        System.out.println("Generated Passenger Data: " + passenger);
        Response createPassenger = given()
                .when()
                .body(passenger)
                .post("passenger");
        String passengerId = createPassenger.jsonPath().getString("_id");
        String passengerName = createPassenger.jsonPath().getString("name");

        System.out.println(passengerId);

//    READ the passenger with the PassengerId and validate the passenger
        Response getPassenger = given()
                .when()
                .get("passenger/" + passengerId);
        String passengerIDGet = getPassenger.jsonPath().getString("_id");
        String passengerNameGet = getPassenger.jsonPath().getString("name");
        String airlineIdGet = (String) getPassenger.jsonPath().getList("airline._id").get(0);

        assertEquals(passengerId, passengerIDGet);
        assertEquals(passengerName, passengerNameGet);
        assertEquals(airlineId, airlineIdGet);

//    UPDATE the passenger's name
        String newName = faker.name().fullName();
        String patchedName = "{\"name\":\"" + newName + "\"}";

        given()
                .when()
                .body(patchedName)
                .patch("passenger/" + passengerId);

//      READ the passenger and assert the patched name
        Response getPatchedPassenger = given()
                .when()
                .get("passenger/" + passengerId);

        String patchedPassengerName = getPatchedPassenger.jsonPath().getString("name");
        assertEquals(newName, patchedPassengerName);

//      DELETE the passenger
        Response deletePassenger = given()
                .when()
                .delete("passenger/" + passengerId);
        String responseMessage = deletePassenger.jsonPath().getString("message");
        assertEquals("Passenger data deleted successfully.", responseMessage);

//       READ the passenger, it should return 204 No Content
        given()
                .when()
                .get("passenger/" + passengerId).then()
                .statusCode(204)
                .log().all();
    }

    @Test
    public void CRUDPassengerWithHelperFile() {
        AirlineHelper.postAirline();
        PassengerHelper.postPassenger();

        PassengerHelper.readPassenger(200);
        assertEquals(GetVariable(PASSENGER_ID), (GetVariable(PASSENGER_ID_GET)));
        assertEquals(GetVariable(PASSENGER_NAME), (GetVariable(PASSENGER_NAME_GET)));
        assertEquals(GetVariable(AIRLINE_ID), (GetVariable(AIRLINE_ID_GET)));
        assertEquals(GetVariable(PASSENGER_TRIPS), (GetVariable(PASSENGER_TRIPS_GET)));

        PassengerHelper.patchPassenger();
        PassengerHelper.readPassenger(200);
        assertEquals(GetVariable(PASSENGER_NAME_PATCH), (GetVariable(PASSENGER_NAME_GET)));

        PassengerHelper.deletePassenger();
        PassengerHelper.readPassenger(204);
    }
}
