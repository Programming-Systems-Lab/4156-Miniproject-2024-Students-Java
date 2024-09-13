package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Writes test cases to check methods in the IndividualProjectApplication.
 */
@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationUnitTests {

  /**
   * Sets up a testApp object and a mocked MyFileDatabase before each test.
   */
  @BeforeEach
  public void setUpIndividualProjectApplicationForTesting() {
    testApp = new IndividualProjectApplication();
    mockDatabase = mock(MyFileDatabase.class);
  }

  /**
   * Sets IndividualProjectApplication.myFileDatabase to null,
   * and sets IndividualProjectApplication.saveData be true
   * after each test.
   */
  @AfterEach
  public void tearDown() {
    IndividualProjectApplication.myFileDatabase = null;
    IndividualProjectApplication.setSaveData(true);
  }

  /**
   * Tests main() method.
   */
  @Test
  public void mainTest() {
    IndividualProjectApplication.overrideDatabase(mockDatabase);

    String[] args = {};
    IndividualProjectApplication.main(args);

    verifyNoMoreInteractions(mockDatabase);
  }

  /**
   * Tests run() method when database already exists.
   */
  @Test
  public void runTestWhenDatabaseExists() {
    IndividualProjectApplication.overrideDatabase(mockDatabase);

    String[] args = {};
    testApp.run(args);

    verifyNoMoreInteractions(mockDatabase);
  }

  /**
   * Tests run() method when database doesn't exist,
   * and the args is "setup".
   */
  @Test
  public void runTestWithSetupArgument() {
    String[] args = {"setup"};

    testApp.run(args);
    assertNotNull(IndividualProjectApplication.myFileDatabase);
  }

  /**
   * Tests run() method when database doesn't exist,
   * and args is "non-setup".
   */
  @Test
  public void runTestWithoutSetupArgument() {
    String[] args = {"non-setup"};

    testApp.run(args);
    assertNotNull(IndividualProjectApplication.myFileDatabase);
  }

  /**
   * Tests overrideDatabase() method.
   */
  @Test
  public void overrideDatabaseTest() {
    IndividualProjectApplication.overrideDatabase(mockDatabase);
    assertSame(mockDatabase, IndividualProjectApplication.myFileDatabase);
    assertFalse(testApp.getSaveData());
  }

  /**
   * Tests resetDataFile() method.
   */
  @Test
  public void resetDataFileTest() {
    IndividualProjectApplication.overrideDatabase(mockDatabase);

    testApp.resetDataFile();

    verify(mockDatabase, times(1)).setMapping(anyMap());
  }

  /**
   * Tests resetDataFile() method when there is exception.
   */
  @Test
  public void resetDataFileTestExceptionExists() {
    IndividualProjectApplication.overrideDatabase(mockDatabase);

    doThrow(new IllegalArgumentException("Has error in resetting data file process"))
                                                        .when(mockDatabase).setMapping(anyMap());

    assertThrows(IllegalArgumentException.class, () -> testApp.resetDataFile());
  }

  /**
   * Tests getSaveData() method and setSaveData() method together.
   */
  @Test
  public void getAndSetSaveDataTest() {
    IndividualProjectApplication.overrideDatabase(mockDatabase);
    assertSame(mockDatabase, IndividualProjectApplication.myFileDatabase);
    assertFalse(testApp.getSaveData());

    IndividualProjectApplication.setSaveData(true);
    assertTrue(testApp.getSaveData());
  }

  /**
   * Tests onTermination() method when saveData is true.
   */
  @Test
  public void onTerminationTestSavaDataTrue() {
    IndividualProjectApplication.overrideDatabase(mockDatabase);
    IndividualProjectApplication.setSaveData(true);
    testApp.onTermination();

    verify(mockDatabase, times(1)).saveContentsToFile();
  }

  /**
   * Tests onTermination() method when savaData is false.
   */
  @Test
  public void onTerminationTestSavaDataFalse() {
    IndividualProjectApplication.overrideDatabase(mockDatabase);

    testApp.onTermination();
    verify(mockDatabase, never()).saveContentsToFile();
  }

  /**
   * Tests onTermination() method when myFileDatabase is null.
   */
  @Test
  public void onTerminationTestMyFileDatabaseNull() {
    testApp.onTermination();
    verify(mockDatabase, never()).saveContentsToFile();
  }

  /** The IndividualProjectApplication instance used for testing. */
  private IndividualProjectApplication testApp;

  /** The MyFileDatabase instance used for testing. */
  private MyFileDatabase mockDatabase;
}
