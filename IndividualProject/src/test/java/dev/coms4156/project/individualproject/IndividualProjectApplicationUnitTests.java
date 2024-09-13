package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for IndividualProjectApplication
 */
@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationUnitTests {

    @BeforeEach
    public void setup() {
        testApp = new IndividualProjectApplication();
        testDatabase = Mockito.mock(MyFileDatabase.class);
    }

    @Test
    public void runTest() {
      String[] args = {"setup"};
      testApp.run(args);
      assertNotNull(IndividualProjectApplication.myFileDatabase);
      
    }

    public static IndividualProjectApplication testApp;
    public static MyFileDatabase testDatabase;
    
}
