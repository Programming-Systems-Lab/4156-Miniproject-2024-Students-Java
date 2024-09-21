package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests included for the MyFileDatabase class.
 * Uses Springboot for setting up database.
 */
@SpringBootTest
public class MyFileDatabaseUnitTests {

  @BeforeAll
  public static void setupDatabaseFlagZeroTest() {
    testDatabase = new MyFileDatabase(0, "./data.txt");
  }
  
  //Tests will go here

  /** 
   * Tester file database variable 
   * */
  public static MyFileDatabase testDatabase;
}