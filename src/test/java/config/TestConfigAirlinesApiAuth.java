package config;

import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.LogConfig;
import org.junit.BeforeClass;

import static config.Authentication.getAuthToken;

public class TestConfigAirlinesApiAuth {
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://api.instantwebtools.net";
        RestAssured.basePath = "/v2/";

        RestAssured.requestSpecification = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + getAuthToken()); //


        RestAssured.config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 5000)
                        .setParam("http.socket.timeout", 5000))
                .logConfig(LogConfig.logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails())
                .decoderConfig(DecoderConfig.decoderConfig()
                        .defaultContentCharset("UTF-8"))
//                .sslConfig(SSLConfig.sslConfig()
//                        .relaxedHTTPSValidation())
        ;

/*
Explanation:
Timeouts: We set connection and socket timeouts to 5 seconds,
which helps prevent tests from hanging if a response takes too long.

Logging: The configuration enables logging of requests and responses only when validation fails.
This helps in debugging failing tests by providing detailed logs.

Decoder Config: The default content charset is set to UTF-8,
ensuring that responses are interpreted correctly.

SSL Config: relaxedHTTPSValidation() is used to bypass SSL certificate validation,
which is useful for testing against environments with self-signed certificates.
 */

    }
}
