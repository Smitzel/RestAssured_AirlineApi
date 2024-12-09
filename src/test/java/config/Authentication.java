package config;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class Authentication {

    static String getAuthToken() {
        // OAuth2 Token Endpoint
        String tokenEndpoint = "https://dev-457931.okta.com/oauth2/aushd4c95QtFHsfWt4x6/v1/token";

        // Parameters for the token request
        Map<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", "password");
        formParams.put("scope", "offline_access");
        formParams.put("username", "api-user1@iwt.net");
        formParams.put("password", "b3z0nV0cLO");
        formParams.put("client_id", "0oahdhjkutaGcIK2M4x6");

        Response response = given()
                .contentType(ContentType.URLENC)
                .formParams(formParams)
                .post(tokenEndpoint);

        // Get the token from the JSON-response
        JSONObject jsonResponse = new JSONObject(response.asString());
        return jsonResponse.getString("access_token");
    }
}

