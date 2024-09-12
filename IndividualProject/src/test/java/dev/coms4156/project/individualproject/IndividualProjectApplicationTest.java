package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationTest {

  @BeforeEach
  public void createNewApplication() {
    testApplication = new IndividualProjectApplication();
  }

  @Test
  public void mainMethodTest() {
    IndividualProjectApplication.main(new String[]{});
  }

  @Test
  public void runWithSetupTest() {
    String[] setup = {"setup"};
    testApplication.run(setup);
    assertNotNull(IndividualProjectApplication.myFileDatabase);
  }

  @Test
  public void runWithoutSetupTest() {
    String[] setup = {};
    testApplication.run(setup);
    assertNotNull(IndividualProjectApplication.myFileDatabase);
  }

  @Test
  public void overrideDatabaseTest() {
    MyFileDatabase empty = new MyFileDatabase(1, "nothingOnPurpose.txt");
    IndividualProjectApplication.overrideDatabase(empty);
    Map<String, Department> myFileDatabaseMap =
        IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    Map<String, Department> emptyMap =
        IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    assertTrue((myFileDatabaseMap == null && emptyMap == null) ||
        (Objects.requireNonNull(myFileDatabaseMap).isEmpty() && emptyMap.isEmpty()));
  }

  @Test
  public void resetDataFileTest() {
    IndividualProjectApplication.myFileDatabase = getMyFileDatabaseHelper();
    MyFileDatabase beforeReset = IndividualProjectApplication.myFileDatabase;
    testApplication2 = new IndividualProjectApplication();
    String[] setup = {};
    testApplication2.run(setup);
    MyFileDatabase afterReset = IndividualProjectApplication.myFileDatabase;
    assertNotEquals(beforeReset.getDepartmentMapping(), afterReset.getDepartmentMapping());
  }

  private static MyFileDatabase getMyFileDatabaseHelper() {
    MyFileDatabase myFileDatabase = new MyFileDatabase(1, "nothingOnPurpose.txt");
    Course sam1234 = new Course(
        "Sam Edwards", "600 SAM", "1:00-11:45", 1);
    sam1234.setEnrolledStudentCount(420);
    Map<String, Course> courses = new HashMap<>();
    courses.put("1234", sam1234);
    Department compSci = new Department(
        "SAM", courses, "Jon Snow", 1);
    Map<String, Department> mapping = new HashMap<>();
    mapping.put("SAM", compSci);
    myFileDatabase.setMapping(mapping);
    return myFileDatabase;
  }

  public IndividualProjectApplication testApplication;
  public IndividualProjectApplication testApplication2;

}
