package model;

import io.cucumber.java.de.Gegebensei;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.lu.a;
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

    private Cistern cistern;

    private Pipe pipe;

    private Plumber player;
    private Saboteur player2;

    boolean success = false;

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


    //Build Features

    @Given("the player is on a cistern")
    public void the_player_is_on_a_cistern() {
        Game.getInstance().getNullGame();
        cistern = new Cistern();
        player = new Plumber(cistern);
        Game.getInstance().addCharacter(player);
        Game.getInstance().addSteppable(cistern);
    }
    @When("the player picks up a pump")
    public void the_player_picks_up_a_pump() {
        success = player.pickPump();
    }

    @Then("the player has a pump")
    public void the_player_has_a_pump() {
        assertEquals(true, success);
        success = false;
    }

    @Given("the player already has a pump")
    public void the_player_already_has_a_pump() {
        Game.getInstance().getNullGame();
        cistern = new Cistern();
        player = new Plumber(cistern);
        Game.getInstance().addCharacter(player);
        Game.getInstance().addSteppable(cistern);
        player.pickPump();
    }

    @Given("the player is on a pipe")
    public void the_player_is_on_a_pipe() {
        pipe = new Pipe();
        pipe.connectTo(cistern);
        cistern.addNeighbourField(pipe);
        Game.getInstance().addPipe(pipe);
        Game.getInstance().addCharacter(player);
        Game.getInstance().setActCharacter(player);
        player.move(pipe);
    }

    @Given("the player waits a turn")
    public void the_player_waits_a_turn() {
        Game.getInstance().nextCharacter();
    }

    @When("the player places the pump")
    public void the_player_places_the_pump() {
        success = player.placePump();
    }

    @Then("the pump is placed")
    public void the_pump_is_placed() {
        assertEquals(true, success);
        success = false;
    }


    @Given("the player is on a pipe with no pump")
    public void the_player_is_on_a_pipe_with_no_pump() {
        Game.getInstance().getNullGame();
        pipe = new Pipe();
        player = new Plumber(pipe);
        Game.getInstance().addPipe(pipe);
        Game.getInstance().addCharacter(player);
        Game.getInstance().setActCharacter(player);
    }

    @Then("the pump is not placed")
    public void the_pump_is_not_placed() {
        assertEquals(false, success);
        success = false;
    }

    @Given("the player is on a pump connected to a pipe")
    public void the_player_is_on_a_pump_connected_to_a_pipe() {
        pump = new Pump();
        pipe = new Pipe();
        pump2 = new Pump();
        pump2.addNeighbourField(pipe);
        pipe.connectTo(pump);
        pump.addNeighbourField(pipe);
        player = new Plumber(pump);
        Game.getInstance().getNullGame();
        Game.getInstance().addCharacter(player);
        Game.getInstance().setActCharacter(player);
        Game.getInstance().addSteppable(pump);
        Game.getInstance().addSteppable(pump2);
        Game.getInstance().addPipe(pipe);
    }

    @When("the player deteaches the pipe")
    public void the_player_deteaches_the_pipe() {
        success = player.pickPipe(pipe);
    }

    @Then("the pipe is deteached")
    public void the_pipe_is_deteached() {
        assertEquals(true, success);
        success = false;
    }

    @Given("the player is on a pump connected to a pipe that has one free end")
    public void the_player_is_on_a_pump_connected_to_a_pipe_that_has_one_free_end() {
        pump = new Pump();
        pipe = new Pipe();
        pipe.connectTo(pump);
        pump.addNeighbourField(pipe);
        player = new Plumber(pump);
        Game.getInstance().getNullGame();
        Game.getInstance().addCharacter(player);
        Game.getInstance().setActCharacter(player);
        Game.getInstance().addSteppable(pump);
        Game.getInstance().addPipe(pipe);
    }

    @Then("the pipe is picked up")
    public void the_pipe_is_picked_up() {
        assertEquals(true, success);
        success = false;
    }




}
