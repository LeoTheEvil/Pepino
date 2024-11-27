import cucumber.api.CucumberOptions;
import cucumber.api.testing.AbstractTestNGCucumberTests;

@CucumberOptions(features="src/test/java/features/hireKappa.feature",glue="steps")
public class RunCucumberTest {
}
