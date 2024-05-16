Feature: Movement
    How the game handles the player manipulating the game world.
    
Scenario: Player places can pick up pump
    Given the player is on a cistern
    When the player picks up a pump
    Then the player has a pump

Scenario: Player places pump on a pipe
    Given the player already has a pump
    Given the player is on a pipe
    When the player waits a turn
    When the player places the pump
    Then the pump is placed

Scenario: Player tries to place a pump but can't because he does'nt have one
    Given the player is on a pipe with no pump
    When the player places the pump
    Then the pump is not placed

Scenario: Player deteaches pipe's end from element he is standing on
    Given the player is on a pump connected to a pipe
    When the player deteaches the pipe
    Then the pipe is deteached

Scenario: Player deteaches pipe's end from element he is standing on but the the pipe's that end is already deteached so he pick up the other end
    Given the player is on a pump connected to a pipe that has one free end
    When the player deteaches the pipe
    Then the pipe is picked up

Scenario: Player attaches pipe's end to element he is standing on
    Given the player is on a pump with a pipe
    When the player attaches the pipe
    Then the pipe is attached

Scenario: Player attaches pipe's end to element he is standing on but the element has no free end
    Given the player is on a pump with a pipe that has no free end
    When the player attaches the pipe
    Then the pipe is not attached
