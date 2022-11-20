package it.cantest.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.platform.engine.Cucumber;
import it.cantest.Helpers.TrelloBoards;
import it.cantest.POJOs.Board;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class Steps {
    private Board board;
    private TrelloBoards trelloBoards = new TrelloBoards();

    @Given("Below details:")
    public void below_details(Map<String, String> details) {
        board = new Board();
        board.setName(details.get("name"));
        board.setDesc(details.get("desc"));
    }

    @Given("There are no boards")
    public void there_are_no_boards() {
        trelloBoards.deleteAllBoards();
    }


    @When("I create a new Board")
    public void i_create_a_new_board() {
        this.trelloBoards.createBoard(this.board.getName());
    }

    @Then("Board is created")
    public void board_is_created() {
        Assertions.assertEquals(HttpStatus.SC_OK, this.trelloBoards.getLastStatusCode());
    }

    @Then("Value of {string} is {string}")
    public void value_of_is(String key, String expected) {
        String actual = this.trelloBoards.getLastBoardName();
        Assertions.assertEquals(expected, actual);
    }



}
