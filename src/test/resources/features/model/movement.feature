Feature: Movement
    How the game handles player movement in the various states of the environment

Scenario: Player stepping on a normal pipe
    Given player is standing on a pump next to a pipe
    When player steps on the pipe
    Then player should be on the pipe

Scenario: Player stepping on a slippery pipe sliding to the other side
    Given player is standing on a pump connected to a pipe which is connected to another pump, GameRandom is 1
    Given the pipe is slippery
    When player steps on the pipe
    Then player should slide to the other side of the pipe    

Scenario: Player stepping on a slippery pipe sliding to his original position
    Given player is standing on a pump connected to a pipe which is connected to another pump, GameRandom is 0
    Given the pipe is slippery
    When player steps on the pipe
    Then player should slide to his original position

Scenario: Player steps on a sticky pipe and gets stuck
    Given player is standing on a pump next to a pipe
    Given the pipe is sticky
    When player steps on the pipe
    When player tries to move
    Then player should be stuck on the pipe

Scenario: Player tries to step on a pipe but the pipe is blocked by another player
    Given player is standing on a pump next to a pipe
    Given the pipe is blocked by another player
    When player steps on the pipe
    Then player should not be able to step on the pipe

Scenario: Player tries to step on an active element
    Given player is standing on a pipe next to a pump
    When player steps on the pump
    Then player should be on the pump

Scenario: Player tries to step on an inactive element but can't because he's on a sticky pipe
    Given player is standing on a sticky pipe next to a pump
    When player steps on the pump
    Then player should be stuck on the pipe

    