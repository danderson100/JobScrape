import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//Define a test suite, with the classes that contain the tests you wish to run
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestCases.class
})
//main class to be executed
public class RunTests {

    public static void main(String[] args) {
        JUnitCore runner = new JUnitCore();
                //run the test suite
        Result r = runner.run(RunTests.class);
    }
}
