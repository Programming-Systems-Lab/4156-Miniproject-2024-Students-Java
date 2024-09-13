package dev.coms4156.project.individualproject;

// import org.junit.jupiter.api.*;
// import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * DepartmentUnitTests.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  @Test
    public void testCreateCourse() {
        
    // Create test data
    String[] times = { "11:40-12:55" };
    String[] locations = { "417 IAB" };

    Course course1 = new Course("Andrew", locations[0], times[0], 400);

    HashMap<String, Course> courses1 = new HashMap<>();
    courses1.put("1001", course1);
        

    Department department = new Department("CS", courses1, "Andrew", 500);


    // Create test data2
    String[] times2 = { "11:40-12:52" };
    String[] locations2 = { "417 IAC" };

    Course course2 = new Course("Andrew", locations[0], times[0], 400);

    HashMap<String, Course> courses2 = new HashMap<>();
    courses2.put("1002", course1);
        
    department.createCourse("1002", "Griffin Newbold", "417 IAC", "11:40-12:52", 500);
  }   

}