package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Writes test case to check methods in the Course.
 */
@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationUnitTests {

  @BeforeEach
  public void setUp() {
    testApp.overrideDatabase(mockDatabase);
  }


  @Test
  public void runWithSetupArgumentTest() {
    testApp.run(new String[] {"setup"});

    assertNotNull(IndividualProjectApplication.myFileDatabase);
    verify(mockDatabase, times(1)).setMapping(any(HashMap.class));
    verify(mockDatabase, never()).deSerializeObjectFromFile();
  }


  @Test
  public void runWithoutSetupArgumentTest() {
    testApp.run(new String[] {});

    assertNotNull(IndividualProjectApplication.myFileDatabase);
    verify(mockDatabase, times(1)).deSerializeObjectFromFile();
    verify(mockDatabase, never()).setMapping(any(HashMap.class));
  }


  @Test
  public void overrideDatabaseTest() {
    MyFileDatabase testDatabase = new MyFileDatabase(0, "./testData.txt");

    testApp.overrideDatabase(testDatabase);

    assertSame(testDatabase, IndividualProjectApplication.myFileDatabase);
    assertFalse(testApp.getSaveData());
  }


  @Test
  public void onTerminationWithSavaDataTest() {
    testApp.setSaveData(true);

    testApp.onTermination();

    verify(mockDatabase, times(1)).saveContentsToFile();
  }


  @Test
  public void onTerminationWithoutSavaDataTest() {
    testApp.setSaveData(false);

    testApp.onTermination();

    verify(mockDatabase, never()).saveContentsToFile();
  }


  @Test
  public void getAndSetSaveDataTest() {
    assertTrue(testApp.getSaveData());
    testApp.setSaveData(false);
    assertFalse(testApp.getSaveData());
  }

  @Mock
  public MyFileDatabase mockDatabase;

  @InjectMocks
  public IndividualProjectApplication testApp;
}
