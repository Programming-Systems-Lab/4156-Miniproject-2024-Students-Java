package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {
    
  @BeforeEach
  public void setupDepartmentForTesting() {
    MyFileDatabase myFileDatabase = new MyFileDatabase(0, "./data.txt");
    Map<String, Department> departmentMapping = myFileDatabase.getDepartmentMapping();
    testCSDepartment = departmentMapping.get("COMS");
  }

  @Test
  public void getNumberOfMajorsSuccessTest() {
    int expectedResult = 2700;
    assertEquals(expectedResult, testCSDepartment.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairSuccessTest() {
    String expectedResult = "Luca Carloni";
    assertEquals(expectedResult, testCSDepartment.getDepartmentChair());
  }

  @Test
  public void addPersonToMajorTest(){
    testCSDepartment.addPersonToMajor();
    int expectedResult = 2701;
    assertEquals(expectedResult, testCSDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest(){
    testCSDepartment.dropPersonFromMajor();
    int expectedResult = 2699;
    assertEquals(expectedResult, testCSDepartment.getNumberOfMajors());
  }

  @Test
  public void addCourseTest(){
    Course coms1234 = new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    testCSDepartment.addCourse("coms1234", coms1234);
    assertEquals(coms1234, testCSDepartment.getCourseSelection().get("coms1234"));
  }

  public static Department testCSDepartment;

}
