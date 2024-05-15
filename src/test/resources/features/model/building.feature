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
