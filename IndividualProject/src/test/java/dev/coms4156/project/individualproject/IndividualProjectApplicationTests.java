package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/** Tests the IndividualProjectApplication class. */
@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationTests {

  private IndividualProjectApplication app;

  /** Sets up the application and mocks the database before each test. */
  @BeforeEach
  public void setup() {
    app = new IndividualProjectApplication();
    MyFileDatabase mockDatabase = Mockito.mock(MyFileDatabase.class);
    IndividualProjectApplication.overrideDatabase(mockDatabase);
  }

  /** Tests main(). */
  @Test
  public void testMainMethod() {
    IndividualProjectApplication.main(new String[] {});
  }

  /** Tests onTermination(). */
  @Test
  public void testOnTerminationSavesData() {
    app.onTermination();
  }

  /** Tests run() with the "setup" argument. */
  @Test
  public void testRunWithSetup() {
    app.run(new String[] {"setup"});
    assertNotNull(IndividualProjectApplication.myFileDatabase);
  }

  /** Tests run() without the "setup" argument. */
  @Test
  public void testRunWithoutSetup() {
    app.run(new String[] {});
    assertNotNull(IndividualProjectApplication.myFileDatabase);
  }

  /** Tests resetDataFile(). */
  @Test
  public void testResetDataFile() {
    app.resetDataFile();
    assertNotNull(IndividualProjectApplication.myFileDatabase);
  }
}
