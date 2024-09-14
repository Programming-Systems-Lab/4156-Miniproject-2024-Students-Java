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
 * We must setup the courses for the different departments
 */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    Map<String, Course> comsCourses = new HashMap<>();
    comsCourses.put("1004", new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400));
    comsCourses.put("3134", new Course("Brian Borowski", "301 URIS", "4:10-5:25", 250));
    comsCourses.put("3157", new Course("Jae Lee", "417 IAB", "4:10-5:25", 400));
    departmentMapping = new HashMap<>();
    Department comsDept = new Department("COMS", comsCourses, "Luca Carloni", 2700);
    departmentMapping.put("COMS", comsDept);

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


  /** Variables for database and route controller */
  @Mock
  private MyFileDatabase mockFileDatabase;

  @InjectMocks
  private RouteController routeController;

  private Map<String, Department> departmentMapping;

}
