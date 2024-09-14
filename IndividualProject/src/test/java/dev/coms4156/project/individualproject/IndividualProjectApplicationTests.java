package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains tests for {@link IndividualProjectApplication} class.
 * These tests verify that the application initializes, resets data correctly,
 * and handles termination logic as expected.
 */
@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationTests {

  /**
   * Mocked database instance used for testing purposes.
   */
  @Mock private static MyFileDatabase myFileDatabase;

  /**
   * Initializes the mocked database before running tests.
   */
  @BeforeAll
  public static void setupMockDatabase() {
    myFileDatabase = Mockito.mock(MyFileDatabase.class);
    // The following command ensures the tests to use the mock database
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
  }

  /**
   * Tests the
   * {@link IndividualProjectApplication#overrideDatabase(MyFileDatabase)}
   * method.
   */
  @Test
  public void overrideDatabaseTest() {
    setupMockDatabase();
    //overrideDatabase is embedded in this function

    assertEquals(myFileDatabase, IndividualProjectApplication.myFileDatabase);
  }

  /**
   * Tests the {@link IndividualProjectApplication#run(String[])} method
   * with setup argument.
   */
  @Test
  public void runWithSetupArgumentTest() {
    setupMockDatabase();
    IndividualProjectApplication app = spy(new IndividualProjectApplication());
    String[] args = {"setup"};

    app.run(args);

    assertNotNull(IndividualProjectApplication.myFileDatabase);
    verify(app, times(1)).resetDataFile();
  }

  /**
   * Tests the {@link IndividualProjectApplication#run(String[])}
   * method without setup argument.
   */
  @Test
  public void runWithoutSetupArgumentTest() {
    setupMockDatabase();
    IndividualProjectApplication app = spy(new IndividualProjectApplication());
    String[] args = {};

    app.run(args);

    assertNotNull(IndividualProjectApplication.myFileDatabase);
    verify(app, never()).resetDataFile();
  }

  /**
   * Tests the {@link IndividualProjectApplication#onTermination()} method.
   * Ensures data not saved during termination when saveData is false.
   */
  @Test
  public void onTerminationWithoutSaveTest() {
    // overrideDatabase is embedded in this function, which sets
    // savedata to false
    setupMockDatabase();
    IndividualProjectApplication app = spy(new IndividualProjectApplication());

    app.onTermination();

    verify(myFileDatabase, never()).saveContentsToFile();
  }

  /**
   * Tests the {@link IndividualProjectApplication#resetDataFile()} method.
   */
  @Test
  public void resetDataFileTest() {
    setupMockDatabase();
    IndividualProjectApplication app = spy(new IndividualProjectApplication());

    app.resetDataFile();

    verify(myFileDatabase, times(1)).setMapping(any());

    HashMap<String, Department> expectedMapping = new HashMap<>();
    expectedMapping.put("COMS", mock(Department.class));
    expectedMapping.put("ECON", mock(Department.class));
    expectedMapping.put("IEOR", mock(Department.class));
    expectedMapping.put("CHEM", mock(Department.class));
    expectedMapping.put("PHYS", mock(Department.class));
    expectedMapping.put("ELEN", mock(Department.class));
    expectedMapping.put("PSYC", mock(Department.class));

    verify(myFileDatabase).setMapping(
            argThat(map -> map.keySet().containsAll(expectedMapping.keySet())));
  }
}
