package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * This class is built to test the Department class, which we have created to represent
 * a department of the courses
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {
    @BeforeEach
    public void setupDepartmentForTesting() {
        testCourses = new HashMap<String, Course>();
        testCourses.put("4156", new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250));
        testDepartment = new Department("COMS", testCourses, "Griffin Newbold", 2700);
    }

    @Test
    public void toStringTest() {
        String expectedResult = "COMS 4156: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n";
        assertEquals(expectedResult, testDepartment.toString());
    }

    @Test
    public void getDepartmentChairTest() {
        assertEquals("Griffin Newbold", testDepartment.getDepartmentChair());
    }

    @Test
    public void getCourseSelectionTest() {
        assertEquals(testCourses, testDepartment.getCourseSelection());
    }

    @Test
    public void addPersonToMajorTest() {
        testDepartment.addPersonToMajor();
        assertEquals(2701, testDepartment.getNumberOfMajors());
    }

    @Test
    public void dropPersonFromMajorTest() {
        testDepartment.dropPersonFromMajor();
        assertEquals(2699, testDepartment.getNumberOfMajors());
    }

    @Test
    public void addCourseTest() {
        Course newCourse = new Course("Griffin BBB", "417 BBB", "1:00-2:15", 250);
        testDepartment.addCourse("4157", newCourse);
        assertEquals(newCourse, testDepartment.getCourseSelection().get("4157"));
    }

    @Test
    public void createCourseTest() {
        Course newCourse = new Course("Griffin BBB", "417 BBB", "1:00-2:15", 250);
        testDepartment.createCourse("4157", "Griffin BBB", "417 BBB", "1:00-2:15", 250);
        assertEquals(newCourse.toString(), testDepartment.getCourseSelection().get("4157").toString());
    }


    @Test
    public void getNumberOfMajorsTest() {
        assertEquals(2700, testDepartment.getNumberOfMajors());
    }

    private Department testDepartment;
    private HashMap<String, Course> testCourses;
}
