package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the IndividualProjectApplication class.
 */
public class IndividualProjectApplicationUnitTests {
    
  @Test
  public void resetDataFileTest() {
    String[] args = {"setup"};
    IndividualProjectApplication.main(args);

    assertEquals(
        7,
        IndividualProjectApplication.myFileDatabase.getDepartmentMapping().size()
    );
  }
}
