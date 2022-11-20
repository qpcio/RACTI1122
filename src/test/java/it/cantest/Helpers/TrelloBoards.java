package it.cantest.Helpers;

import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.*;

public class TrelloBoards extends TrelloBase {
    private String boardsURL = "boards/";
    private String meURL = "members/me/";

    public void getAllBoards() {
        this.lastRespose = get(this.meURL + this.boardsURL);
    }

    public List<String> getAllboardsIDs() {
        return this.lastRespose.body().jsonPath().get("id");
    }

    public void deleteAllBoards(){
        this.getAllBoards();
        for(String id:getAllboardsIDs()){
            deleteBoard(id);
        }
    }

    public void createBoard(String name) {
        this.lastRespose = given()
                .queryParam("name", name)
                .post(this.boardsURL);
    }

    public void getBoard(String id) {
        this.lastRespose = get(this.boardsURL + id);
    }

    public void updateBoard(String id, String newJSON) {
        this.lastRespose = given()
                .body(newJSON)
                .put(this.boardsURL + id);
    }

    public void updateBoardName(String id, String newName) {
        this.lastRespose = given()
                .queryParam("name", newName)
                .put(this.boardsURL + id);
    }

    public void updateBoardDesc(String id, String newDesc) {
        this.lastRespose = given()
                .queryParam("desc", newDesc)
                .put(this.boardsURL + id);
    }

    public void deleteBoard(String id) {
        this.lastRespose = delete(this.boardsURL + id);
    }

    public String getLastBoardId() {
        return this.lastRespose.body().jsonPath().get("id");
    }

    public String getLastBoardName() {
        return this.getLastResponseValue("name");
    }

    public String getLastBoardDesc() {
        return this.getLastResponseValue("desc");
    }




    public TrelloBoards() {
        super();
    }
}
