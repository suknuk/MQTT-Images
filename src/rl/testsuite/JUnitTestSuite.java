package rl.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


//JUnit Suite Test
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	CommandLineValuesAssertTests.class,
	TwitterTests.class
})

public class JUnitTestSuite {
}