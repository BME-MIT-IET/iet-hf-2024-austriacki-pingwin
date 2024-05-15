package model;

import io.cucumber.java.de.Gegebensei;
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
    private Pump pump2;
    private Pipe pipe;
    private Plumber player;
    private Saboteur player2;

    @Given("player is standing on a pump next to a pipe")
    public void player_is_standing_on_a_pump_next_to_a_pipe() {
        pump = new Pump();
        pipe = new Pipe();
        pipe.connectTo(pump);
        pump.addNeighbourField(pipe);
        player = new Plumber(pump);
        Game.getInstance().addCharacter(player);
        Game.getInstance().addSteppable(pump);
        Game.getInstance().addPipe(pipe);
    }

    @When("player steps on the pipe")
    public void steps_on_the_pipe() {
        player.move(pipe);
    }

    @Then("player should be on the pipe")
    public void player_should_be_on_the_pipe() {
        assertEquals(pipe, player.getActField());
    }

    @Given("player is standing on a pump connected to a pipe which is connected to another pump, GameRandom is {int}")
    public void player_is_standing_on_a_pump_connected_to_a_pipe_which_is_connected_to_another_pump(int isRandom) {
        pump = new Pump();
        pipe = new Pipe();
        pump2 = new Pump();
        pipe.connectTo(pump);
        pipe.connectTo(pump2);
        pump.addNeighbourField(pipe);
        pump2.addNeighbourField(pipe);
        player = new Plumber(pump);
        Game.getInstance().testHelper(isRandom == 1 ? true : false); //Random kikapcsolása, hogy megfellő oldalra csússzon
        Game.getInstance().addCharacter(player);
        Game.getInstance().addSteppable(pump);
        Game.getInstance().addSteppable(pump2);
        Game.getInstance().addPipe(pipe);
    }

    @Given("the pipe is slippery")
    public void the_pipe_is_slippery() {
        pipe.makeSlippery();
    }

    @When("player steps on the slippery pipe")
    public void steps_on_the_slippery_pipe() {
        player.move(pipe);
    }

    @Then("player should slide to the other side of the pipe")
    public void player_should_slide_to_the_other_side_of_the_pipe() {
        assertEquals(pump2, player.getActField());
    }

    @Then("player should slide to his original position")
    public void player_should_slide_to_his_original_position() {
        assertEquals(pump, player.getActField());
    }

    @Given("the pipe is sticky")
    public void the_pipe_is_sticky() {
        pipe.makeSticky();
    }
    
    @When("player tries to move")
    public void player_tries_to_move() {
        player.move(pump);
    }
    @Then("player should be stuck on the pipe")
    public void player_should_be_stuck_on_the_pipe() {
        assertEquals(pipe, player.getActField());
    }
   
    @Given("the pipe is blocked by another player")
    public void the_pipe_is_blocked_by_another_player() {
        player2 = new Saboteur(pipe);
        Game.getInstance().addCharacter(player2);
    }
   
    @Then("player should not be able to step on the pipe")
    public void player_should_not_be_able_to_step_on_the_pipe() {
        assertEquals(pump, player.getActField());
    }

    @Given("player is standing on a pipe next to a pump")
    public void player_is_standing_on_a_pipe_next_to_a_pump() {
        pump = new Pump();
        pipe = new Pipe();
        pipe.connectTo(pump);
        pump.addNeighbourField(pipe);
        player = new Plumber(pipe);
        Game.getInstance().addCharacter(player);
        Game.getInstance().addSteppable(pump);
        Game.getInstance().addPipe(pipe);
    }
    @When("player steps on the pump")
    public void player_steps_on_the_pump() {
        player.move(pump);
    }
    @Then("player should be on the pump")
    public void player_should_be_on_the_pump() {
        assertEquals(pump, player.getActField());
    }

    @Given("player is standing on a sticky pipe next to a pump")
    public void player_is_standing_on_a_sticky_pipe_next_to_a_pump() {
        pump = new Pump();
        pipe = new Pipe();
        pipe.connectTo(pump);
        pump.addNeighbourField(pipe);
        player = new Plumber(pump);
        pipe.makeSticky();
        player.move(pipe);
        Game.getInstance().addCharacter(player);
        Game.getInstance().addSteppable(pump);
        Game.getInstance().addPipe(pipe);
    }

}
