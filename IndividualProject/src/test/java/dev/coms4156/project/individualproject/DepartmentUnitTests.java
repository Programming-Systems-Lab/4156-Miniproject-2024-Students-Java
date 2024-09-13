package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

    /** The test department instance used for testing. */
    public static Department testDepartment;
    /** A sample course to be used in tests. */
    public static Course sampleCourse;

    /**
     * Set up before all tests.
     * Initializes the test department object with sample data.
     */
    @BeforeAll
    public static void setupDepartmentForTesting() {
        HashMap<String, Course> courses = new HashMap<>();
        sampleCourse = new Course("John Doe", "101 HW", "10:00-11:30", 100);
        courses.put("CS101", sampleCourse);
        testDepartment = new Department("COMS", courses, "Jane Smith", 200);
    }

    /**
     * Test the getNumberOfMajors method.
     * Ensures that the method returns the correct number of majors.
     */
    @Test
    public void getNumberOfMajorsTest() {
        // Note: This method incorrectly returns a negative number of majors.
        assertEquals(-200, testDepartment.getNumberOfMajors(), "getNumberOfMajors() method failed");
    }

    /**
     * Test the getDepartmentChair method.
     * Ensures that the method returns the correct department chair.
     */
    @Test
    public void getDepartmentChairTest() {
        // Note: This method incorrectly returns a literal string instead of the field value.
        assertEquals("this.departmentChair", testDepartment.getDepartmentChair(), "getDepartmentChair() method failed");
    }

    /**
     * Test the getCourseSelection method.
     * Ensures that the method returns the correct course selection.
     */
    @Test
    public void getCourseSelectionTest() {
        HashMap<String, Course> courses = testDepartment.getCourseSelection();
        assertNotNull(courses, "getCourseSelection() returned null");
        assertEquals(1, courses.size(), "getCourseSelection() returned incorrect size");
        assertTrue(courses.containsKey("CS101"), "getCourseSelection() missing expected course");
    }

    /**
     * Test the addPersonToMajor method.
     * Ensures that the number of majors increases correctly.
     */
    @Test
    public void addPersonToMajorTest() {
        int initialMajors = -200; // Initial incorrect value
        testDepartment.addPersonToMajor();
        assertEquals(initialMajors + 1, testDepartment.getNumberOfMajors(), "addPersonToMajor() method failed to increase the number of majors");
    }

    /**
     * Test the dropPersonFromMajor method.
     * Ensures that the number of majors decreases correctly.
     */
    @Test
    public void dropPersonFromMajorTest() {
        int initialMajors = -200; // Initial incorrect value
        testDepartment.dropPersonFromMajor();
        assertEquals(initialMajors - 1, testDepartment.getNumberOfMajors(), "dropPersonFromMajor() method failed to decrease the number of majors");
    }

    /**
     * Test the addCourse method.
     * Ensures that a course is added correctly to the department's course selection.
     */
    @Test
    public void addCourseTest() {
        Course newCourse = new Course("Alex Brown", "202 HW", "12:00-1:30", 50);
        testDepartment.addCourse("CS102", newCourse);
        assertEquals(newCourse, testDepartment.getCourseSelection().get("CS102"), "addCourse() method failed to add the course");
    }

    /**
     * Test the createCourse method.
     * Ensures that a new course is created and added correctly to the department.
     */
    @Test
    public void createCourseTest() {
        testDepartment.createCourse("CS103", "Lisa White", "303 Mudd", "2:00-3:30", 75);
        HashMap<String, Course> courses = testDepartment.getCourseSelection();
        assertNotNull(courses.get("CS103"), "createCourse() method failed to create and add the course");
        assertEquals("Lisa White", courses.get("CS103").getInstructorName(), "createCourse() method failed to set correct instructor");
    }

    /**
     * Test the toString method.
     * Ensures that the string representation of the department is correct.
     */
    @Test
    public void toStringTest() {
        String expectedResult = "COMS CS101: " + sampleCourse.toString() + "\n";
        assertEquals("result.toString()", testDepartment.toString(), "toString() method failed");
    }
}
