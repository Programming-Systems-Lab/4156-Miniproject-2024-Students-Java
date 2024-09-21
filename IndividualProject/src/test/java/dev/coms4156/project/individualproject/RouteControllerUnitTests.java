package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit testing for RouteController.java.
 */
public class RouteControllerUnitTests {

  /**
   * We must setup the courses for the different departments.
   */
  @BeforeEach
  public void setup() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    MockitoAnnotations.openMocks(this);

    departmentMapping = new HashMap<>();

    Map<String, Course> comsCourses = new HashMap<>();
    comsCourses.put("1004", new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400));
    comsCourses.put("3134", new Course("Brian Borowski", "301 URIS", "4:10-5:25", 700));
    comsCourses.put("3157", new Course("Jae Lee", "417 IAB", "4:10-5:25", 400));
    departmentMapping = new HashMap<>();
    Department comsDept = new Department("COMS", comsCourses, "Luca Carloni", 2700);
    departmentMapping.put("COMS", comsDept);

    Map<String, Course> wgsCourses = new HashMap<>();
    wgsCourses.put("1000", new Course("Peggy Elsberg", "301 MIL", "4:10-5:25", 200));
    wgsCourses.put("1004", new Course("John Snow", "102 DIA", "10:10-11:25", 125));
    wgsCourses.put("3000", new Course("Alex Honnold", "301 URIS", "10:10-11:25", 100));
    Department wgsDept = new Department("WGS", wgsCourses, "Peggy Elsberg", 800);
    departmentMapping.put("Peggy Elsberg", wgsDept);

    Map<String, Course> chemCourses = new HashMap<>();
    chemCourses.put("1403", new Course("Ruben M Savizky", locations[1], "6:10-7:25", 120));
    chemCourses.put("1500", new Course("Joseph C Ulichny", "302 HAV", "6:10-9:50", 46));
    chemCourses.put("2045", new Course("Luis M Campos", "209 HAV", "1:10-2:25", 50));
    Department chemDept = new Department("CHEM", chemCourses, "Laura J. Kaufman", 250);
    departmentMapping.put("CHEM", chemDept);

    Map<String, Course> physCourses = new HashMap<>();
    physCourses.put("1001", new Course("Szabolcs Marka", "301 PUP", times[3], 150));
    physCourses.put("1201", new Course("Eric Raymer", "428 PUP", times[3], 145));
    physCourses.put("1602", new Course("Kerstin M Perez", "428 PUP", times[2], 140));
    Department physDept = new Department("PHYS", physCourses, "Laura J. Kaufman", 250);
    departmentMapping.put("PHYS", physDept);
    
    when(mockFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    IndividualProjectApplication.myFileDatabase = mockFileDatabase;
  }


  @Test
  public void retrieveDepartmentTestExists() {
    ResponseEntity<?> response = routeController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(departmentMapping.get("COMS").toString(), response.getBody());
  }

  @Test
  public void retrieveDepartmentTestDoesNotExist() {
    ResponseEntity<?> response = routeController.retrieveDepartment("ANTH");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void enrollStudentInCourseTestNotFound() {
    ResponseEntity<?> response = routeController.enrollStudentInCourse("ANTH", 1002);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void enrollStudentInCourseTestBadReqest() {
    ResponseEntity<?> response = routeController.enrollStudentInCourse("COMS", 1004);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); 
  }

  @Test
  public void enrollStudentInCourseTestGood() {
    ResponseEntity<?> response = routeController.enrollStudentInCourse("COMS", 3134);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void RetrieveCourseTestDNE() {
    ResponseEntity<?> response = routeController.retrieveCourse("PHYS", 1000);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void RetrieveCourseTestDNE2() {
    ResponseEntity<?> response = routeController.retrieveCourse("IEOR", 9);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test 
  public void RetrieveCoursesTestExistsInMultipleDepartments() {
    ResponseEntity<?> response = routeController.retrieveCourses(1004);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void RetrieveCoursesTestNoCourses() {
    ResponseEntity<?> response = routeController.retrieveCourses(9);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }


  /** Variables for database and route controller. */
  @Mock
  private MyFileDatabase mockFileDatabase;

  @InjectMocks
  private RouteController routeController;

  private Map<String, Department> departmentMapping;

}