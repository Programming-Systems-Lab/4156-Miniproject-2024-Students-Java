package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Represents a unit test class for the IndividualProjectApplication class. This class tests various
 * functionalities of the IndividualProjectApplication class under wide variety of conditions and
 * makes sure they work as expected.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class IndividualProjectApplicationTests {

  @BeforeEach
  void setupApplicationForTesting() {
    testApp = spy(new IndividualProjectApplication());
  }

  @Test
  @Order(1)
  public void appRunTestWithSetup() {
    String[] runArgs = {"setup"};
    testApp.run(runArgs);
    verify(testApp).resetDataFile();
    assertNotNull(IndividualProjectApplication.myFileDatabase);
  }

  @Test
  @Order(2)
  public void appRunTestWithoutSetup() {
    String[] runArgs = {};
    testApp.run(runArgs);
    verify(testApp, never()).resetDataFile();
    assertNotNull(IndividualProjectApplication.myFileDatabase);
  }

  @Test
  @Order(3)
  public void resetDataFileTest() {
    testApp.resetDataFile();
    assertNotNull(IndividualProjectApplication.myFileDatabase.getDepartmentMapping());
    assertEquals(7, IndividualProjectApplication.myFileDatabase.getDepartmentMapping().size());
  }

  @Test
  @Order(4)
  public void onTerminationTestSave() {
    IndividualProjectApplication.myFileDatabase = mockDb;
    testApp.onTermination();
    verify(mockDb).saveContentsToFile();
  }

  @Test
  @Order(5)
  public void onTerminationTestDontSave() {
    IndividualProjectApplication.overrideDatabase(mockDb);
    testApp.onTermination();
    verify(mockDb, never()).saveContentsToFile();
  }

  @Spy private IndividualProjectApplication testApp;

  @Mock private MyFileDatabase mockDb;
}
