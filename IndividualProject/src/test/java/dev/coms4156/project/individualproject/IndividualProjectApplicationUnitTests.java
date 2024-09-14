package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class is a test for individualProjectApplication class.
 */
@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationUnitTests {
  /** The test course instance used for testing. */
  public static IndividualProjectApplication application;

  @BeforeEach
  public void setupIndividualProjectApplicationForTesting() {
    application = new IndividualProjectApplication();
  }

  @Test
  public void mainTest() {
    String[] args = {};
    application.main(args);
  }

  @Test
  public void runTest1() {
    String[] args = {" ", " ", " ", " ", " ", "setup"};
    application.run(args);

    assertNotNull(application.myFileDatabase);
  }

  @Test
  public void runTest2() {
    String[] args = {};
    application.run(args);

    assertNotNull(application.myFileDatabase);
  }

  @Test
  public void onTerminationTestWithSaveData() {
    application.setSaveData(true);
    application.onTermination();
    assertTrue(application.isSaveData());
  }

  @Test
  public void onTerminationTestWithoutSaveData() {
    application.setSaveData(false);
    application.onTermination();
    assertFalse(application.isSaveData());
  }

  @Test
  public void overrideDatabaseTest() {
    MyFileDatabase mockDatabase = mock(MyFileDatabase.class);

    application.overrideDatabase(mockDatabase);
    assertEquals(mockDatabase, application.myFileDatabase);
    assertFalse(application.isSaveData());
  }

  @Test
  public void resetDataFileTest() {
    application.resetDataFile();

    assertNotNull(IndividualProjectApplication.myFileDatabase);
    assertFalse(IndividualProjectApplication.myFileDatabase.getDepartmentMapping().isEmpty());
  }
}
