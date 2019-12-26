//LAST MODIFIED: 2019.12.02

package JumpInTestCases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CommandPackageTest.class, ControllerPackageTest.class, ElementPackageTest.class, HillTest.class,
		HoleTest.class, JumpinPackageTest.class, PositionTest.class, SolverPackageTest.class, SpotTest.class,
		ViewPackageTest.class })
public class AllTests {

}
