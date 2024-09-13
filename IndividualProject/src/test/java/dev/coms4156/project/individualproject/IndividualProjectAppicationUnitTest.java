package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class IndividualProjectAppicationUnitTest {

  private IndividualProjectApplication application;

  @BeforeEach
    void setUp() {
    // Initialize the application
    application = new IndividualProjectApplication();
    application.myFileDatabase = new MyFileDatabase(1, "testfile.txt"); // Dummy path for test
  }

  @Test
    void testResetDataFile() {
    application.resetDataFile();

    Map<String, Department> departmentMapping = application
                .myFileDatabase.getDepartmentMapping();

    assertNotNull(departmentMapping);

    // Verify that the correct departments were added
    assertTrue(departmentMapping.containsKey("COMS"));
    assertTrue(departmentMapping.containsKey("ECON"));
    assertTrue(departmentMapping.containsKey("IEOR"));
    assertTrue(departmentMapping.containsKey("CHEM"));
    assertTrue(departmentMapping.containsKey("PHYS"));
    assertTrue(departmentMapping.containsKey("ELEN"));
    assertTrue(departmentMapping.containsKey("PSYC"));

    // // Verify some course details for one department (e.g., COMS)
    // Department compSci = departmentMapping.get("COMS");
    // assertEquals("Luca Carloni", compSci.getChair());
    // assertEquals(2700, compSci.getNumberOfStudents());

    // // Verify that a specific course exists in the COMS department
    // Course coms1004 = compSci.getCourses().get("1004");
    // assertNotNull(coms1004);
    // assertEquals("Adam Cannon", coms1004.getInstructor());
    // assertEquals(249, coms1004.getEnrolledStudentCount());
  }

  @Test
    void testRun() {
    String[] args = new String[]{"setup"};
    application.run(args);
  }
  
  @Test
    void testMain() {
    String[] args = new String[]{"setup"};
    application.main(args);
  }
  
  @Test
    void testonTermination() {
    // application.saveData = true;
    application.onTermination();
  }
  
  @Test
    void testOverrideDatabase() {
    application.overrideDatabase(application.myFileDatabase);
  }

}
