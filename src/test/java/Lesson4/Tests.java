package Lesson4;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.containsString;

public class Tests extends AbstractTest {
    ResponseSpecification responseSpecification;
    RequestSpecification requestSpecification;
    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
    }
    @BeforeEach
    void beforeTest() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, OPTIONS, DELETE, PUT")
                .build();
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", getApiKey())
                .build();

    }
    @Test
    void complexSearchTest() {
        given()
                .spec(requestSpecification)
                .when()
                .get(getBaseUrl() + getComplexSearch())
                .then()
                .spec(responseSpecification);
    }
    @Test
    void italianCuisineComplexSearchTest() {
        given()
                .spec(requestSpecification)
                .queryParam("cuisine", "italian")
                .when()
                .get(getBaseUrl() + getComplexSearch())
                .then()
                .spec(responseSpecification);
    }
    @Test
    void minCarbs100Test () {
        given()
                .spec(requestSpecification)
                .queryParam("minCarbs", "100")
                .when()
                .get(getBaseUrl() + getComplexSearch())
                .then()
                .spec(responseSpecification);
    }
    @Test
    void riceComplexSearchTest() {
        given()
                .spec(requestSpecification)
                .queryParam("query", "rice")
                .when()
                .get(getBaseUrl() + getComplexSearch())
                .then()
                .spec(responseSpecification);
    }
    @Test
    void pastaComplexSearchTest() {
        given()
                .spec(requestSpecification)
                .queryParam("query", "pasta")
                .expect()
                .body("results[0].title", containsString("Pasta"))
                .when()
                .get(getBaseUrl() + getComplexSearch())
                .then()
                .spec(responseSpecification);
    }
    @Test
    void classifyCuisineTest() {
        given()
                .spec(requestSpecification)
                .when()
                .post(getBaseUrl() + getRecipesCuisine())
                .then()
                .spec(responseSpecification);
    }
    @Test
    void postGetDeleteShoppingList() {
        given()
                .spec(requestSpecification)
                .queryParam("username", "timurlik0")
                .queryParam("hash", getHash())
                .body(getPostShopListBody())
                .when()
                .post(getBaseUrl() + "/mealplanner/timurlik0/shopping-list/items");
        int id = given()
                .spec(requestSpecification)
                .queryParam("username", "timurlik0")
                .queryParam("hash", getHash())
                .when()
                .get(getBaseUrl() + "/mealplanner/timurlik0/shopping-list")
                .then()
                .log()
                .all()
                .extract()
                .jsonPath()
                .get("aisles[0].items[0].id");
        System.out.println(id);
        given()
                .spec(requestSpecification)
                .queryParam("username", "timurlik0")
                .queryParam("id", id)
                .queryParam("hash", getHash())
                .when()
                .delete(getBaseUrl() + "/mealplanner/timurlik0/shopping-list/items/" + id)
                .then()
                .spec(responseSpecification);
    }
}
