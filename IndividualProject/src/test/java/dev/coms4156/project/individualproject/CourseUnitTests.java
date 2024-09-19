package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.util.HashMap;
import org.apiguardian.api.API;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
/**
 * The CourseUnitTests class is used to test various methods of the Course class.
 *
 */

@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {
  /**
   * The SetupCourseForTesting is used to set up instances for testing.
   *
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    HashMap<String, Course> testcourses = new HashMap<>();
    testcourses.put("4156", testCourse);
    testDept = new Department("COMS", testcourses, "Luca Carloni", 100);
    testApp = new IndividualProjectApplication();
    testFD = new MyFileDatabase(0, "D:\\Study_USA\\Course\\Advanced_Software_Engineering"
    + "\\Miniproject1\\4156-Miniproject-2024-Students-Java\\IndividualProject\\data.txt");
    testRC = new RouteController();
  }


  @Test
  public void enrollStudentTest() {
    boolean expectedResult = true;
    assertEquals(expectedResult, testCourse.enrollStudent());
  }

  @Test
  public void dropStudentTest() {
    boolean expectedResult = true;
    assertEquals(expectedResult, testCourse.dropStudent());
  }

  // @Test
  // public void getCourseLocationTest() {
  //   String expectedResult = "417 IAB";
  //   assertEquals(expectedResult, testCourse.getCourseLocation());
  // }

  // @Test
  // public void getInstructorNameTest() {
  //   String expectedResult = "Griffin Newbold";
  //   assertEquals(expectedResult, testCourse.getInstructorName());
  // }

  // @Test
  // public void getCourseTimeSlotTest() {
  //   String expectedResult = "11:40-12:55";
  //   assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  // }

  @Test
  public void reassignInstructorTest() {
    String expectedResult = "Mary";
    testCourse.reassignInstructor("Mary");
    assertEquals(expectedResult, testCourse.getInstructorName());
  }

  @Test
  public void reassignLocationTest() {
    String expectedResult = "310 IAB";
    testCourse.reassignLocation("310 IAB");
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }

  @Test
  public void reassignTimeTest() {
    String expectedResult = "1:40 - 2:55";
    testCourse.reassignTime("1:40 - 2:55");
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }



  @Test
  public void isCourseFullTest() {
    boolean expectedResult = false;
    assertEquals(expectedResult, testCourse.isCourseFull());
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Mary; Location: 310 IAB; Time: 1:40 - 2:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  // @Test
  // public void GetNumberOfMajorsTest() {
  //   int expectedResult= 100;
  //   assertEquals(expectedResult, testDept.getNumberOfMajors());
  // }

  // @Test
  // public void getDepartmentChairTest() {
  //   String expectedResult= "Luca Carloni";
  //   assertEquals(expectedResult, testDept.getDepartmentChair());
  // }

  @Test
  public void addPersonToMajorTest() {
    int expectedResult = 101;
    testDept.addPersonToMajor();
    assertEquals(expectedResult, testDept.getNumberOfMajors());
  }

  @Test
  public void addCourseTest() {
    Course coms0001 = new Course("Adam Cannon", "417 IAB", "4:10 - 5:25", 400);
    testDept.addCourse("0001", coms0001);
    // System.out.println(testDept.getCourseSelection());
    assertTrue(testDept.getCourseSelection().containsKey("0001"));
  }

  @Test
  public void toStringTestDept() {
    String expectedResult = "COMS 4156: \nInstructor: Mary; Location: 310 IAB; Time: 1:40 - 2:55"
        +  "\nCOMS 0001: \nInstructor: Adam Cannon; Location: 417 IAB; Time: 4:10 - 5:25\n";
    assertEquals(expectedResult, testDept.toString());
  }

  @Test
  public void resetDataFileTest() {
    testApp.resetDataFile();
      
  }

  @Test
  public void deSerializeObjectFromFileTest() {
    // String expectedResult="[ELEN, CHEM, PHYS, PSYC, COMS, ECON, IEOR]";
    //System.out.println(testFD.deSerializeObjectFromFile().keySet());
    assertTrue(testFD.deSerializeObjectFromFile().containsKey("ELEN"));
      
  }

  @Test
  public void saveContentsToFileTest() {
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("ELEN", new Department("ELEN", new HashMap<>(), "Mary", 250));
    mapping.put("COMS", new Department("COMS", new HashMap<>(), "Tony", 2700));
    testFD.setMapping(mapping);

    testFD.saveContentsToFile();

    HashMap<String, Department> loadedMapping;
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\Study_USA\\Course\\"
        + "Advanced_Software_Engineering\\Miniproject1\\4156-Miniproject-2024-Students-Java"
        + "\\IndividualProject\\data.txt"))) {
      loadedMapping = (HashMap<String, Department>) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      return; // to satisfy the compiler
    }
    assertEquals(mapping.toString(), loadedMapping.toString());
  }

  @Test
  public void testToString() {
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("ELEN", new Department("ELEN", new HashMap<>(), "Kaiser", 250));
    mapping.put("COMS", new Department("COMS", new HashMap<>(), "Seok", 2700));
    testFD.setMapping(mapping);

    String result = testFD.toString();

    String expectedResult = "For the ELEN department: \n" 
                          + "For the COMS department: \n";
    assertEquals(expectedResult, result);
  }

  @Test
  public void indexTest() {
    String expectedResult = "Welcome, in order to make an API call direct "
        + "your browser or Postman to an endpoint "
        + "\n\n This can be done using the following format: \n\n http:127.0.0"
        + ".1:8080/endpoint?arg=value";
    assertEquals(expectedResult, testRC.index());
  }

  @Test
  public void retrieveDepartmentTest() throws Exception {
    String deptCode = "IEOR";
    String expectedResult = "200 OK OK,IEOR";
    String temp = testRC.retrieveDepartment(deptCode).toString();
    assertTrue(temp.contains(expectedResult));
  }

  @Test
  public void retrieveCourseTest() throws Exception {
    String expectedResult = "200 OK OK";
    String temp = testRC.retrieveCourse("IEOR", 3404).toString();
    assertTrue(temp.contains(expectedResult));
  }

  @Test
  public void isCourseFullTestRc() throws Exception {
    String expectedResult = "200 OK OK";
    String temp = testRC.isCourseFull("IEOR", 3404).toString();
    assertTrue(temp.contains(expectedResult));
  }

  @Test
  public void changeCourseLocationTest() throws Exception {
    String expectedResult = "Attributed was updated successfully.";
    String temp = testRC.changeCourseLocation("IEOR", 3404, "115 URI").toString();
    assertTrue(temp.contains(expectedResult));
  }

  @Test
  public void changeCourseTeacherTest() throws Exception {
    String expectedResult = "Attributed was updated successfully.";
    String temp = testRC.changeCourseTeacher("IEOR", 3404, "Mary").toString();
    assertTrue(temp.contains(expectedResult));
  }

  @Test
  public void changeCourseTimeTest() throws Exception {
    String expectedResult = "Attributed was updated successfully.";
    String temp = testRC.changeCourseTime("IEOR", 3404, "6:10 - 8:00").toString();
    assertTrue(temp.contains(expectedResult));
  }

  @Test
  public void removeMajorFromDeptTest() throws Exception {
    String expectedResult = "Attribute was updated or is at minimum";
    String temp = testRC.removeMajorFromDept("IEOR").toString();
    assertTrue(temp.contains(expectedResult));
  }

  @Test
  public void retrieveCoursesTest() throws Exception {
    String expectedResult = "Department: CHEM";
    String temp = testRC.retrieveCourses(4102).toString();
    //System.out.println(temp);
    assertTrue(temp.contains(expectedResult));
  }





  /** The test course instance used for testing. */
  public static Course testCourse;
  public static Department testDept;
  public static IndividualProjectApplication testApp;
  public static MyFileDatabase testFD;
  public static RouteController testRC;

  public static Course getTestCourse() {
    return testCourse;
  }


  public static void setTestCourse(Course testCourse) {
    CourseUnitTests.testCourse = testCourse;
  }

  public static Department getTestDept() {
    return testDept;
  }

  public static void setTestDept(Department testDept) {
    CourseUnitTests.testDept = testDept;
  }


}

