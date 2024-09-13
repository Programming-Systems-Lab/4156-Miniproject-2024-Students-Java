package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * This class contains tests for all the API routes for the system.
 */
@WebMvcTest(RouteController.class)
@AutoConfigureMockMvc
public class RouteControllerUnitTests {
  /**
   * Initialize some IndividualProjectApplication objects used for unit tests.
   */
  @Autowired
  private MockMvc mockMvc;
    
  @BeforeAll
  public static void setupCourseForTesting() {
    
  }
}
