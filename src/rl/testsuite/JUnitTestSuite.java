package rl.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


//JUnit Suite Test
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	CommandLineValuesAssertTests.class,
	SocialMediaTests.class
})

public class JUnitTestSuite {
}