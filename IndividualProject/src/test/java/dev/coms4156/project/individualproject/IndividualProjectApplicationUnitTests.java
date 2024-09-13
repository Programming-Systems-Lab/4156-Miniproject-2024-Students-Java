package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Writes test case to check methods in the Course.
 */
@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationUnitTests {

  @BeforeEach
  public void setUpIndividualProjectApplicationForTesting() {
    testApp = new IndividualProjectApplication();
    mockDatabase = mock(MyFileDatabase.class);
    IndividualProjectApplication.overrideDatabase(mockDatabase);
  }


  @AfterEach
  public void tearDown() {
    IndividualProjectApplication.myFileDatabase = null;
    IndividualProjectApplication.setSaveData(true);
  }

  @Test
  public void runDatabaseExistsTest() {
    String[] args = {};
    testApp.run(args);

    verifyNoMoreInteractions(mockDatabase);
  }


  @Test
  public void runWithSetupArgumentTest() {
    String[] args = new String[] {"setup"};

    Map<String, Department> mockDepartmentMap = new HashMap<>();
    when(mockDatabase.getDepartmentMapping()).thenReturn(mockDepartmentMap);

    testApp.run(args);

    assertNotNull(IndividualProjectApplication.myFileDatabase);
    assertEquals(mockDepartmentMap, IndividualProjectApplication.myFileDatabase.getDepartmentMapping());
  }


  @Test
  public void runWithoutSetupArgumentTest() {
    String[] args = {};

    Map<String, Department> mockDepartmentMap = new HashMap<>();
    when(mockDatabase.getDepartmentMapping()).thenReturn(mockDepartmentMap);

    testApp.run(args);

    assertNotNull(IndividualProjectApplication.myFileDatabase);
    assertEquals(0, IndividualProjectApplication.myFileDatabase.getDepartmentMapping().size());
  }


  @Test
  public void overrideDatabaseTest() {
    assertEquals(mockDatabase, IndividualProjectApplication.myFileDatabase);
    assertFalse(testApp.getSaveData());
  }


  @Test
  public void resetDataFileTest() {
    Map<String, Department> mockDepartmentMap = new HashMap<>();
    when(mockDatabase.getDepartmentMapping()).thenReturn(mockDepartmentMap);

    testApp.resetDataFile();

    assertEquals(mockDepartmentMap, IndividualProjectApplication.myFileDatabase.getDepartmentMapping());
    verify(mockDatabase, times(1)).setMapping(anyMap());
  }


  @Test
  public void resetDataFileExceptionTest() {
    doThrow(new IllegalArgumentException("Has error in resetting data file process")).when(mockDatabase).setMapping(anyMap());

    Exception exception = assertThrows(IllegalArgumentException.class, () -> testApp.resetDataFile());
    assertEquals("Has error in resetting data file process", exception.getMessage());
  }

  @Test
  public void onTerminationWithSavaDataTest() {
    IndividualProjectApplication.setSaveData(true);
    testApp.onTermination();
    verify(mockDatabase, times(1)).saveContentsToFile();
  }


  @Test
  public void onTerminationWithoutSavaDataTest() {
    testApp.onTermination();
    verify(mockDatabase, never()).saveContentsToFile();
  }


  @Test
  public void getAndSetSaveDataTest() {
    assertFalse(testApp.getSaveData());

    IndividualProjectApplication.setSaveData(true);
    assertTrue(testApp.getSaveData());
  }


  private IndividualProjectApplication testApp;
  private MyFileDatabase mockDatabase;
}
