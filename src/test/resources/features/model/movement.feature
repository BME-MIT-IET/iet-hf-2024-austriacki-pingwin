Feature: Movement
    How the game handles player movement

Scenario: Player stepping on a normal pipe
    Given player is standing on a pump next to a pipe
    When steps on the pipe
    Then player should be on the pipe

Scenario: Player stepping on sticky pipe
    Given player is standing on a pump next to a sticky pipe
    When steps on the pipe
    Then player should stick to the pipe

Scenario: Player stepping on a slippery pipe
    Given player is standing on a pump next to a slippery pipe
    When steps on the pipe
    Then player should slide to the other side of the pipe

Scenario: Player stepping on a slippery pipe
    Given player is standing on a pump next to a slippery pipe
    When steps on the pipe
    Then player should slide to his original position

Scenario: Player tries stepping on a pipe but can't because of another player's presence
    Given player is standing on a pump next to a pipe
    When another player is on the pipe
    When tries to step on the pipe but can't
    Then player should stay on the pump

Scenario: Player tries stepping on actvie element
    Given player is standing on a pump next to an active element
    When tries to step on the element
    Then player should step on the element

Scenario: Player tries stepping on actvie element but can't because he is on a sticky pipe
    Given player is standing on a pump next to a sticky pipe
    When player steps on the sticky pipe
    When tries to step back to the pump
    Then player should stay on the pipe





    