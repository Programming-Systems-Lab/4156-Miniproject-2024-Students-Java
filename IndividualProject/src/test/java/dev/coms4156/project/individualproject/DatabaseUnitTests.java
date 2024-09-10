package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Course class.
 * This class contains tests to verify the behavior and functionality of the Course class.
 * It uses Spring's testing framework to set up the environment and run the tests.
 */
@SpringBootTest
@ContextConfiguration
@TestMethodOrder(OrderAnnotation.class)
public class DatabaseUnitTests {

  @Nested
  @TestMethodOrder(OrderAnnotation.class)
  class FlagZeroTests {
    
    @BeforeAll
    public static void setupDatabaseFlagZeroTest() {
      testDatabase = new MyFileDatabase(0, "./data.txt");
      System.out.println(testDatabase.toString());
    }

  }

  @Nested
  @TestMethodOrder(OrderAnnotation.class)
  class FlagOneTests {

  }

  /** The test course instance used for testing. */
  public static MyFileDatabase testDatabase;
}

