package model;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import model.*;
import model.characters.*;
import model.characters.Plumber;
import model.fields.*;
import io.cucumber.java.en.Then;
import static org.junit.Assert.assertEquals;
import org.junit.Before;


public class StepDefinitions {

    private Pump pump;
    private Pipe pipe;
    private Plumber player;

    @Given("player is standing on a pump next to a pipe")
    public void player_is_standing_on_a_pump_next_to_a_pipe() {
        pump = new Pump();
        pipe = new Pipe();
        pump.addNeighbourField(pipe);
        player = new Plumber(pump);
    }

    @When("steps on the pipe")
    public void steps_on_the_pipe() {
        player.move(pipe);
    }

    @Then("player should be on the pipe")
    public void player_should_be_on_the_pipe() {
        assertEquals(pipe, player.getActField());
    }

    @Given("player is standing on a pump next to a slippery pipe")
    public void player_is_standing_on_a_pump_next_to_a_slippery_pipe() {
        // Implement the logic for this step
        System.out.println("Given: player is standing on a pump next to a slippery pipe");
    }

    @Then("player should slide to the other side of the pipe")
    public void player_should_slide_to_the_other_side_of_the_pipe() {
        // Implement the logic for this step
        System.out.println("Then: player should slide to the other side of the pipe");
    }

    @Then("player should slide to his original position")
    public void player_should_slide_to_his_original_position() {
        // Implement the logic for this step
        System.out.println("Then: player should slide to his original position");
    }

    @Given("player is standing on a pump next to a sticky pipe")
    public void player_is_standing_on_a_pump_next_to_a_sticky_pipe() {
        // Implement the logic for this step
        System.out.println("Given: player is standing on a pump next to a sticky pipe");
    }

    @Then("player should stick to the pipe")
    public void player_should_stick_to_the_pipe() {
        // Implement the logic for this step
        System.out.println("Then: player should stick to the pipe");
    }

    @When("another player is on the pipe")
    public void another_player_is_on_the_pipe() {
        // Implement the logic for this step
        System.out.println("When: another player is on the pipe");
    }

    @When("tries to step on the pipe but can't")
    public void tries_to_step_on_the_pipe_but_can_t() {
        // Implement the logic for this step
        System.out.println("When: tries to step on the pipe but can't");
    }

    @Then("player should stay on the pump")
    public void player_should_stay_on_the_pump() {
        
    }

    @Given("player is standing on a pump next to an active element")
    public void player_is_standing_on_a_pump_next_to_an_active_element() {
        // Implement the logic for this step
        System.out.println("Given: player is standing on a pump next to an active element");
    }

    @When("tries to step on the element")
    public void tries_to_step_on_the_element() {
        // Implement the logic for this step
        System.out.println("When: tries to step on the element");
    }

    @Then("player should step on the element")
    public void player_should_step_on_the_element() {
        // Implement the logic for this step
        System.out.println("Then: player should step on the element");
    }

    @When("player steps on the sticky pipe")
    public void player_steps_on_the_sticky_pipe() {
        // Implement the logic for this step
        System.out.println("When: player steps on the sticky pipe");
    }

    @When("tries to step back to the pump")
    public void tries_to_step_back_to_the_pump() {
        // Implement the logic for this step
        System.out.println("When: tries to step back to the pump");
    }

    @Then("player should stay on the pipe")
    public void player_should_stay_on_the_pipe() {
        // Implement the logic for this step
        System.out.println("Then: player should stay on the pipe");
    }
}
