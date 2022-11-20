Feature: Boards usage
  Background:
    Given There are no boards

  Scenario: Board Creation
    Given Below details:
      |name|Some name|
      |desc|Some desc|
    When I create a new Board
    Then Board is created
    And Value of "name" is "Some name"
