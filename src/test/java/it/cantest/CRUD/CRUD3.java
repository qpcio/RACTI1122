package it.cantest.CRUD;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;

/**
 * W OSTATECZNOSCI!!!!!!!!!!!!!!!!!!!
 */


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CRUD3 {
    private static String baseURL = "https://api.trello.com/1/";
    private static String key = "a78b13c8f404e45e1ff845eb60f96002";
    private static String token = "82a9641e694084a53d268ed530e87329058018dc530b65250432cd9e717f1896";
    private String boardsURL = "boards/";
    private String idTablicy;


    @BeforeAll
    public static void setUP() {
        baseURI = baseURL;
        RestAssured.requestSpecification = given()
                .queryParam("key", key)
                .queryParam("token", token)
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON);
    }

    //napiszcie 4 testy:
    // 1. tworzy tablice - moze wrzucic ID do idTablicy
    // 2. odczytuje tablice (i tyle - moze sprawdzic nazwe)
    // 3. zmienia i sprawdza po zmianie
    // 4. kasuje i sprawdza 404 po skasowaniu

    @Test
    @Order(1)
    public void shouldCreateABoard() {
        String name = "Tralabum";
        Response response = given()
                .queryParam("name", name)
                .when()
                .post(this.boardsURL);
        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        Assertions.assertEquals(name, response.body().jsonPath().get("name"));
        this.idTablicy = response.body().jsonPath().get("id");
    }

    @Test
    @Order(2)
    public void shouldReadABoard() {
        if (this.idTablicy == null) throw new RuntimeException("Board ID is null");
        Response response = get(this.boardsURL + this.idTablicy);
        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        Assertions.assertEquals("Tralabum", response.body().jsonPath().get("name"));
    }

    @Test
    @Order(3)
    public void shouldChangeABoard() {
        if (this.idTablicy == null) throw new RuntimeException("Board ID is null");
        String newDesc = "UmpaUmpa";
        String newBody = "{\"desc\":\"" + newDesc + "\"}";
        Response response = given()
                .body(newBody)
                .when()
                .put(this.boardsURL + this.idTablicy);
        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        response = get(this.boardsURL + this.idTablicy);
        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        Assertions.assertEquals(newDesc, response.body().jsonPath().get("desc"));
    }

    @Test
    @Order(4)
    public void shouldDeleteABoard() {
        if (this.idTablicy == null) throw new RuntimeException("Board ID is null");
        Response response = delete(this.boardsURL + this.idTablicy);
        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        response = get(this.boardsURL + this.idTablicy);
        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }


}
