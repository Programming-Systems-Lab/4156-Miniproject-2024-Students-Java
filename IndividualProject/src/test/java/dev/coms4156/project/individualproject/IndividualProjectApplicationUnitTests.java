package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for the IndividualProjectApplication class.
 * This class contains tests to verify the behavior and functionality of the 
 * IndividualProjectApplication class.
 * It uses Spring's testing framework to set up the environment and run the tests.
 */
@SpringBootTest
public class IndividualProjectApplicationUnitTests {

  public static IndividualProjectApplication testApplication;
  public static MyFileDatabase testDatabase;
  private Logger logger;
  private CustomLoggerHandler logHandler;

  class CustomLoggerHandler extends StreamHandler {
    private LogRecord latestLogRecord;

    @Override
    public void publish(LogRecord record) {
      // Store the most recent log record
      latestLogRecord = record;
      super.publish(record);
    }

    @Override
    public void flush() {
      super.flush();
    }

    @Override
    public void close() throws SecurityException {
      super.close();
    }

    // Method to get the latest log record
    public LogRecord getLatestLogRecord() {
      return latestLogRecord;
    }
  }

  /**
  * Sets up the test environment before each test.
  * 
  * <p>This method initializes a new instance of the {@code IndividualProjectApplication} 
  * class and captures the logger output by redirecting to a 
  * {@code ByteArrayOutputStream}. This allows the test to verify log messages 
  * generated during the execution of the application.
  * 
  * <p>Each test can access the logger output through the {@code logOutput} field.
  */
  @BeforeEach
  public void setup() {
    testApplication = new IndividualProjectApplication();
    testDatabase = Mockito.mock(MyFileDatabase.class);

    logger = Logger.getLogger(IndividualProjectApplication.class.getName());
    logHandler = new CustomLoggerHandler();
    logger.addHandler(logHandler);
    logger.setLevel(java.util.logging.Level.ALL);
  }

  @Test
  public void testOverride() {
    IndividualProjectApplication.overrideDatabase(testDatabase);
    assertSame(testDatabase, IndividualProjectApplication.myFileDatabase, 
            "myFileDatabase should refer to the mock object after overrideDatabase is called.");
  }

  @Test
  public void testRunWithSetupArgument() {
    testApplication.run("setup");
    assertEquals("Jennifer Blaze", IndividualProjectApplication.myFileDatabase
                                              .getDepartmentMapping().get("PSYC")
                                              .getCourseSelection().get("4493")
                                              .getInstructorName());

    LogRecord record = logHandler.getLatestLogRecord();
    assertTrue(record.getMessage().contains("System Setup"), "Log should contain 'System Setup'");
  }

  @Test
  public void testRunWithoutSetupArgument() {
    testApplication.run();

    LogRecord record = logHandler.getLatestLogRecord();
    assertTrue(record.getMessage().contains("Start up"), "Log should contain 'Start up'");
  }

  @Test
  public void testResetDataFile() {
    IndividualProjectApplication.overrideDatabase(testDatabase);
    testApplication.resetDataFile();

    verify(testDatabase, times(1)).setMapping(anyMap());
  }

  @Test
  public void testOnTerminationSaveDataFalse() {
    IndividualProjectApplication.overrideDatabase(testDatabase);
    testApplication.onTermination();
    verify(testDatabase, times(0)).saveContentsToFile();
  }


}
