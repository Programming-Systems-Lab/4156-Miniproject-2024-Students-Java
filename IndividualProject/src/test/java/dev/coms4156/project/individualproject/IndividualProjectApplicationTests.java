package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class contains unit tests for the IndividualProjectApplication class.
 */
@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationTests {

  /** Sets up a testing Application  */
  @BeforeEach
  public void setForTesting() {
    testApplication = new IndividualProjectApplication();
    testDatabase = new MyFileDatabase(0, "./data.txt");
    IndividualProjectApplication.overrideDatabase(testDatabase);
  }

  @Test
  public void runTest() {
    String[] setUp = {"setup"};
    testApplication.run(setUp);
    assertNotNull(testDatabase);

    String[] notSetup = {" "};
    testApplication.run(notSetup);
    assertNotNull(testDatabase);
  }




    public static MyFileDatabase testDatabase;
    public static IndividualProjectApplication testApplication;
}
