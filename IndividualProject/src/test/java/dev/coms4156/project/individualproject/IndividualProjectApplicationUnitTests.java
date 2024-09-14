package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;


/**
 * This class aims to write unit tests for the {@link IndividualProjectApplication} class.
 * Contained in this file is primarily validating of the {@code resetDataFile()}.
 * Which is critical to ensuring that the data.txt file has the correct information.
 */
public class IndividualProjectApplicationUnitTests {
  private IndividualProjectApplication app;
  private MyFileDatabase mockDatabase;

  /**
   * The {@code individualProjectAppUnitTestSetup} method establishes a mock database
   * that allows us to test behavior of the functions specified below.
   */
  @BeforeEach
  public void individualProjectAppUnitTestSetup() {
    mockDatabase = mock(MyFileDatabase.class);
    app = new IndividualProjectApplication();
    IndividualProjectApplication.myFileDatabase = mockDatabase; // Set the mocked database
  }

  /**
   * This method determines whether a course in the course mapping of a department has the
   * expected fields.
   *
   * <p>The input for this method contains the following:
   * {@param courses}
   * {@param courseCode}
   * {@param expectedInstructor}
   * {@param expectedEnrollment}
   */
  private void validateCourseInfo(Map<String, Course> courses, String courseCode,
                                String expectedInstructor, int expectedEnrollment) {

    assertTrue(courses.containsKey(courseCode));

    Course course = courses.get(courseCode);
    assertEquals(expectedInstructor, course.getInstructorName());
    assertEquals(expectedEnrollment, course.getEnrolledCount());
  }

  /**
   * This function tests the {@code resetDataFile()} method in the IndividualProjectApplication
   * class. It determines if the data mapping, is accurate and contains all the information
   * hard-coded in the IndividualProjectApplication class.
   *
   * <p>Note that while this method utilizes many assertions to determine the validity of the data.
   * The overall goal is to determine whether hardcoded data is effectively mapped to the
   * file mapping. In this way, I test if the integrity of the data.txt file is maintained.
   */
  @Test
  public void testResetDataFile() {
    app.resetDataFile();

    ArgumentCaptor<Map<String, Department>> argumentCaptor = ArgumentCaptor.forClass(HashMap.class);
    verify(mockDatabase, times(1)).setMapping(argumentCaptor.capture());

    Map<String, Department> departmentMapping = argumentCaptor.getValue();

    assertNotNull(departmentMapping);
    assertEquals(7, departmentMapping.size());

    validateComsDept(departmentMapping);
    validateEconDept(departmentMapping);
    validateIeorDept(departmentMapping);
    validateChemDept(departmentMapping);
    validatePhysDept(departmentMapping);
    validateElenDept(departmentMapping);
    validatePsycDept(departmentMapping);
  }


  /**
   * This method verifies that all the relevant COMS classes are present in the
   * COMS department object.å
   */
  private void validateComsDept(Map<String, Department> departmentMapping) {
    // Validate Department Info
    Department compSciDept = departmentMapping.get("COMS");
    assertNotNull(compSciDept);
    assertEquals(2700, compSciDept.getNumberOfMajors());
    assertEquals("Luca Carloni", compSciDept.getDepartmentChair());

    validateCourseInfo(compSciDept.getCourseSelection(), "1004",
        "Adam Cannon", 249);
    validateCourseInfo(compSciDept.getCourseSelection(), "3134",
        "Brian Borowski", 242);
    validateCourseInfo(compSciDept.getCourseSelection(), "3157",
        "Jae Lee", 311);
    validateCourseInfo(compSciDept.getCourseSelection(), "3203",
        "Ansaf Salleb-Aouissi", 215);
    validateCourseInfo(compSciDept.getCourseSelection(), "3261",
        "Josh Alman", 140);
    validateCourseInfo(compSciDept.getCourseSelection(), "3251",
        "Tony Dear", 99);
    validateCourseInfo(compSciDept.getCourseSelection(), "3827",
          "Daniel Rubenstein", 283);
    validateCourseInfo(compSciDept.getCourseSelection(), "4156",
        "Gail Kaiser", 109);
  }


  /**
   * This method verifies that all the relevant ECON courses are present in the
   * ECON department object.
   */
  private void validateEconDept(Map<String, Department> departmentMapping) {
    // Validate Department Info
    Department econDept = departmentMapping.get("ECON");
    assertNotNull(econDept);
    assertEquals(2345, econDept.getNumberOfMajors());
    assertEquals("Michael Woodford", econDept.getDepartmentChair());

    // Validate COMS courses
    validateCourseInfo(econDept.getCourseSelection(), "1105",
        "Waseem Noor", 187);
    validateCourseInfo(econDept.getCourseSelection(), "2257",
        "Tamrat Gashaw", 63);
    validateCourseInfo(econDept.getCourseSelection(), "3211",
        "Murat Yilmaz", 81);
    validateCourseInfo(econDept.getCourseSelection(), "3213",
        "Miles Leahey", 77);
    validateCourseInfo(econDept.getCourseSelection(), "3412",
        "Thomas Piskula", 81);
    validateCourseInfo(econDept.getCourseSelection(), "4415",
        "Evan D Sadler", 63);
    validateCourseInfo(econDept.getCourseSelection(), "4710",
        "Matthieu Gomez", 37);
    validateCourseInfo(econDept.getCourseSelection(), "4840",
        "Mark Dean", 67);
  }


  /**
   * This method verifies that all the relevant IEOR courses are present in the
   * IEOR department object.
   */
  private void validateIeorDept(Map<String, Department> departmentMapping) {
    // Validate Department Info
    Department ieorDept = departmentMapping.get("IEOR");
    assertNotNull(ieorDept);
    assertEquals(67, ieorDept.getNumberOfMajors());
    assertEquals("Jay Sethuraman", ieorDept.getDepartmentChair());

    // Validate IEOR courses
    validateCourseInfo(ieorDept.getCourseSelection(), "2500",
        "Uday Menon", 52);
    validateCourseInfo(ieorDept.getCourseSelection(), "3404",
        "Christopher J Dolan", 80);
    validateCourseInfo(ieorDept.getCourseSelection(), "3658",
        "Daniel Lacker", 87);
    validateCourseInfo(ieorDept.getCourseSelection(), "4102",
        "Antonius B Dieker", 92);
    validateCourseInfo(ieorDept.getCourseSelection(), "4106",
        "Kaizheng Wang", 161);
    validateCourseInfo(ieorDept.getCourseSelection(), "4405",
        "Yuri Faenza", 19);
    validateCourseInfo(ieorDept.getCourseSelection(), "4511",
        "Michael Robbins", 50);
    validateCourseInfo(ieorDept.getCourseSelection(), "4540",
        "Krzysztof M Choromanski", 33);
  }


  /**
   * This method verifies that all the relevant CHEM courses are present in the
   * CHEM department object.
   */
  private void validateChemDept(Map<String, Department> departmentMapping) {
    // Validate Department Info
    Department chemDept = departmentMapping.get("CHEM");
    assertNotNull(chemDept);
    assertEquals(250, chemDept.getNumberOfMajors());
    assertEquals("Laura J. Kaufman", chemDept.getDepartmentChair());

    // Validate CHEM courses
    validateCourseInfo(chemDept.getCourseSelection(), "1403",
        "Ruben M Savizky", 100);
    validateCourseInfo(chemDept.getCourseSelection(), "1500",
        "Joseph C Ulichny", 50);
    validateCourseInfo(chemDept.getCourseSelection(), "2045",
        "Luis M Campos", 29);
    validateCourseInfo(chemDept.getCourseSelection(), "2444",
        "Christopher Eckdahl", 150);
    validateCourseInfo(chemDept.getCourseSelection(), "2494",
        "Talha Siddiqui", 18);
    validateCourseInfo(chemDept.getCourseSelection(), "3080",
        "Milan Delor", 18);
    validateCourseInfo(chemDept.getCourseSelection(), "4071",
        "Jonathan S Owen", 29);
    validateCourseInfo(chemDept.getCourseSelection(), "4102",
        "Dalibor Sames", 27);
  }



  /**
   * This method verifies that all the relevant PHYS courses are present in the PHYS
   * department object.
   */
  private void validatePhysDept(Map<String, Department> departmentMapping) {
    // Validate Department Info
    Department physDept = departmentMapping.get("PHYS");
    assertNotNull(physDept);
    assertEquals(43, physDept.getNumberOfMajors());
    assertEquals("Dmitri N. Basov", physDept.getDepartmentChair());

    // Validate PHYS courses
    validateCourseInfo(physDept.getCourseSelection(), "1001",
          "Szabolcs Marka", 131);
    validateCourseInfo(physDept.getCourseSelection(), "1201",
        "Eric Raymer", 130);
    validateCourseInfo(physDept.getCourseSelection(), "1602",
        "Kerstin M Perez", 77);
    validateCourseInfo(physDept.getCourseSelection(), "2802",
        "Yury Levin", 23);
    validateCourseInfo(physDept.getCourseSelection(), "3008",
        "William A Zajc", 60);
    validateCourseInfo(physDept.getCourseSelection(), "4003",
        "Frederik Denef", 19);
    validateCourseInfo(physDept.getCourseSelection(), "4018",
        "James W McIver", 18);
    validateCourseInfo(physDept.getCourseSelection(), "4040",
        "James C Hill", 31);
  }


  /**
   * This method verifies that all the relevant COMS classes are present
   * in the COMS department object.
   */
  private void validateElenDept(Map<String, Department> departmentMapping) {
    // Validate Department Info
    Department elenDept = departmentMapping.get("ELEN");
    assertNotNull(elenDept);
    assertEquals(250, elenDept.getNumberOfMajors());
    assertEquals("Ioannis Kymissis", elenDept.getDepartmentChair());

    // Validate ELEN courses
    validateCourseInfo(elenDept.getCourseSelection(), "1201",
        "David G Vallancourt", 108);
    validateCourseInfo(elenDept.getCourseSelection(), "3082",
        "Kenneth Shepard", 30);
    validateCourseInfo(elenDept.getCourseSelection(), "3331",
        "David G Vallancourt", 54);
    validateCourseInfo(elenDept.getCourseSelection(), "3401",
        "Keren Bergman", 25);
    validateCourseInfo(elenDept.getCourseSelection(), "3701",
        "Irving Kalet", 24);
    validateCourseInfo(elenDept.getCourseSelection(), "4510",
        "Mohamed Kamaludeen", 22);
    validateCourseInfo(elenDept.getCourseSelection(), "4702",
        "Alexei Ashikhmin", 5);
    validateCourseInfo(elenDept.getCourseSelection(), "4830",
        "Christine P Hendon", 22);
  }


  /**
   * This method verifies that all the relevant PYSC courses are present in the PYSC
   * department object.
   */
  private void validatePsycDept(Map<String, Department> departmentMapping) {
    // Validate Department Info
    Department psycDept = departmentMapping.get("PSYC");
    assertNotNull(psycDept);
    assertEquals(437, psycDept.getNumberOfMajors());
    assertEquals("Nim Tottenham", psycDept.getDepartmentChair());

    // Validate PSYC Courses
    validateCourseInfo(psycDept.getCourseSelection(), "1001",
            "Patricia G Lindemann", 191);
    validateCourseInfo(psycDept.getCourseSelection(), "1610",
            "Christopher Baldassano", 42);
    validateCourseInfo(psycDept.getCourseSelection(), "2235",
            "Katherine T Fox-Glassman", 128);
    validateCourseInfo(psycDept.getCourseSelection(), "2620",
            "Jeffrey M Cohen", 55);
    validateCourseInfo(psycDept.getCourseSelection(), "3212",
            "Mayron Piccolo", 15);
    validateCourseInfo(psycDept.getCourseSelection(), "3445",
            "Mariam Aly", 12);
    validateCourseInfo(psycDept.getCourseSelection(), "4236",
            "Trenton Jerde", 17);
    validateCourseInfo(psycDept.getCourseSelection(), "4493",
            "Jennifer Blaze", 9);
  }


}
