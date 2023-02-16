package Lesson3;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@Disabled
public class Tests extends AbstractTest{
    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
    }
    @Test
    void complexSearchTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .when()
                .get(getBaseUrl() + getComplexSearch())
                .prettyPeek()
                .then()
                .statusCode(200);
    }
    @Test
    void italianCuisineComplexSearchTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "italian")
                .when()
                .get(getBaseUrl() + getComplexSearch())
                .prettyPeek()
                .then()
                .statusCode(200);
    }
    @Test
    void minCarbs100Test () {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("minCarbs", "100")
                .when()
                .get(getBaseUrl() + getComplexSearch())
                .prettyPeek()
                .then()
                .statusCode(200);
    }
    @Test
    void riceComplexSearchTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "rice")
                .when()
                .get(getBaseUrl() + getComplexSearch())
                .prettyPeek()
                .then()
                .statusCode(200);
    }
    @Test
    void pastaComplexSearchTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pasta")
                .response()
                .contentType(ContentType.JSON)
                .expect()
                .body("results[0].title", containsString("Pasta"))
                .when()
                .get(getBaseUrl() + getComplexSearch())
                .prettyPeek();

    }
    @Test
    void classifyCuisineTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .when()
                .post(getBaseUrl() + getRecipesCuisine())
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
