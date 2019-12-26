package JumpInTestCases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ElementPackageTest.class, HillTest.class, HoleTest.class, JumpinPackageTest.class, PositionTest.class,
		SpotTest.class })
public class AllTests {

}
