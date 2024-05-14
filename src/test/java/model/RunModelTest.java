package model;


import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/model/",
        plugin = {"pretty"},
        glue = {"model"}
)
public class RunModelTest {
}
