package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Unit testing for IndividualProjectApplication.java
 */
public class IndividualProjectApplicationUnitTests {


  /**
   * This must setup the envrionment in order for the IndividualProjectApplication
   * to be working.
   * 
   * <p>Instantiates {@code IndividualProjectApplication} This happens for each object.
   * </p>
   */
  @BeforeEach 
  public void setup() {
    testApplication = new IndividualProjectApplication();
    testDatabase = Mockito.mock(MyFileDatabase.class);
    app = new IndividualProjectApplication();
  }

  @Test
  public void overrideDatabaseTest() {
    IndividualProjectApplication.overrideDatabase(testDatabase);
    assertSame(testDatabase, IndividualProjectApplication.myFileDatabase, 
        "Should refer to the object created (test/our file should be the same).");
  }
 
  @Test
  public void onTerminationSaveDataTest() {
    IndividualProjectApplication.overrideDatabase(testDatabase);
    testApplication.onTermination();
    verify(testDatabase, times(0)).saveContentsToFile();
  }

  /*
   * Our variables we instantiate.
   */
  public static MyFileDatabase testDatabase;
  public static IndividualProjectApplication testApplication;
  public static IndividualProjectApplication app;

}
