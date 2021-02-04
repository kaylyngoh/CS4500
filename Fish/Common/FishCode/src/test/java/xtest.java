import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class xtest {

  public static void main(String[] args) {
    Result result1 = JUnitCore.runClasses(BoardTest.class);
    Result result2 = JUnitCore.runClasses(FishModelTest.class);
    Result result3 = JUnitCore.runClasses(GameNodeTest.class);
    Result result4 = JUnitCore.runClasses(InternalPlayerTest.class);
    Result result5 = JUnitCore.runClasses(PenguinTest.class);
    Result result6 = JUnitCore.runClasses(PlayerTest.class);
    Result result7 = JUnitCore.runClasses(Pos2DTest.class);
    Result result8 = JUnitCore.runClasses(RefereeTest.class);
    Result result9 = JUnitCore.runClasses(StrategyTest.class);
    Result result10 = JUnitCore.runClasses(xBoardTest.class);
    Result result11 = JUnitCore.runClasses(xStateTest.class);
    Result result12 = JUnitCore.runClasses(xTreeTest.class);
    Result result13 = JUnitCore.runClasses(PieceTest.class);
    Result result14 = JUnitCore.runClasses(ManagerTest.class);

    List<Result> allTests = new ArrayList<>(Arrays.asList(result1, result2, result3, result4, result5,
            result6, result7, result8, result9,result10,result11, result12, result13, result14));

    for (Result result : allTests) {
      for (Failure failure : result.getFailures()) {
        System.out.println(failure.toString());
      }
    }

    int totalCount = 0;
    for (Result result: allTests) {
      totalCount += result.getRunCount();
    }

    int totalFailCount = 0;
    for (Result result: allTests) {
      totalFailCount += result.getFailureCount();
    }

    int totalRunTime = 0;
    for (Result result: allTests) {
      totalRunTime += result.getRunTime();
    }

    System.out.println("Number of tests run: " + totalCount);
    System.out.println("Number of failed tests: " + totalFailCount);
    System.out.println("Runtime: " + totalRunTime);
    System.exit(0);
  }
}
