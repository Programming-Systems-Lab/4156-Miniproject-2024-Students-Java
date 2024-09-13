package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

/**
 * Unit tests for the RouteController class.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class RouteControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyFileDatabase myFileDatabase;

    private RouteController routeController;

    private HashMap<String, Department> departmentMapping;

    /**
     * Sets up the testing environment by initializing RouteController
     * and setting up the mock MyFileDatabase and Department objects.
     */
    @BeforeEach
    public void setup() {
        routeController = new RouteController();
        departmentMapping = new HashMap<>();

        Department testDepartment = mock(Department.class);
        when(testDepartment.getDepartmentChair()).thenReturn("Test Chair");
        when(testDepartment.getCourseSelection()).thenReturn(new HashMap<>());

        departmentMapping.put("TEST", testDepartment);

        IndividualProjectApplication.myFileDatabase = mock(MyFileDatabase.class);
        when(IndividualProjectApplication.myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    }

    /**
     * Tests the index() method of RouteController.
     */
    @Test
    public void testIndex() {
        String response = routeController.index();
        assertEquals("Welcome, in order to make an API call direct your browser or Postman to an endpoint \n\n This can be done using the following format: \n\n http:127.0.0.1:8080/endpoint?arg=value", response);
    }

    /**
     * Tests retrieveDepartment() method when the department is found.
     */
    @Test
    public void testRetrieveDepartmentFound() {
        ResponseEntity<?> response = routeController.retrieveDepartment("TEST");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests retrieveDepartment() method when the department is not found.
     */
    @Test
    public void testRetrieveDepartmentNotFound() {
        ResponseEntity<?> response = routeController.retrieveDepartment("INVALID");
        assertEquals("Department Not Found", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Tests the retrieveDepartment() method to ensure it handles exceptions correctly.
     * Mocks a database error scenario and verifies the error response.
     */
    @Test
    public void testRetrieveDepartmentException() {
        when(IndividualProjectApplication.myFileDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> response = routeController.retrieveDepartment("TEST");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("An Error has occurred", response.getBody());
    }

    /**
     * Tests retrieveCourse() method when the course is found.
     */
    @Test
    public void testRetrieveCourseFound() {
        HashMap<String, Course> courses = new HashMap<>();
        Course mockCourse = mock(Course.class);
        courses.put("101", mockCourse);

        Department mockDepartment = mock(Department.class);
        when(mockDepartment.getCourseSelection()).thenReturn(courses);
        departmentMapping.put("CS", mockDepartment);

        ResponseEntity<?> response = routeController.retrieveCourse("CS", 101);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    /**
     * Tests retrieveCourse() method when the course is not found.
     */
    @Test
    public void testRetrieveCourseNotFound() {
        ResponseEntity<?> response = routeController.retrieveCourse("CS", 999);
        assertEquals("Course Not Found", response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests the retrieveCourse() method to ensure it handles exceptions correctly.
     * Mocks a database error scenario and verifies the error response.
     */
    @Test
    public void testRetrieveCourseException() {
        when(IndividualProjectApplication.myFileDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> response = routeController.retrieveCourse("CS", 101);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("An Error has occurred", response.getBody());
    }

    /**
     * Tests isCourseFull() method to check if a course is full.
     */
    @Test
    public void testIsCourseFull() {
        HashMap<String, Course> courses = new HashMap<>();
        Course mockCourse = mock(Course.class);
        when(mockCourse.isCourseFull()).thenReturn(true);
        courses.put("101", mockCourse);

        Department mockDepartment = mock(Department.class);
        when(mockDepartment.getCourseSelection()).thenReturn(courses);
        departmentMapping.put("CS", mockDepartment);

        ResponseEntity<?> response = routeController.isCourseFull("CS", 101);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody());
    }

    /**
     * Tests isCourseFull() method when the course is not found.
     */
    @Test
    public void testIsCourseFullCourseNotFound() {
        ResponseEntity<?> response = routeController.isCourseFull("CS", 999);
        assertEquals("Course Not Found", response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests getMajorCtFromDept() method to retrieve the number of majors in a department.
     */
    @Test
    public void testGetMajorCtFromDept() {
        Department mockDepartment = mock(Department.class);
        when(mockDepartment.getNumberOfMajors()).thenReturn(200);
        departmentMapping.put("CS", mockDepartment);

        ResponseEntity<?> response = routeController.getMajorCtFromDept("CS");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("There are: -200 majors in the department", response.getBody());
    }

    /**
     * Tests getMajorCtFromDept() method when the department is not found.
     */
    @Test
    public void testGetMajorCtFromDeptNotFound() {
        ResponseEntity<?> response = routeController.getMajorCtFromDept("INVALID");
        assertEquals("Department Not Found", response.getBody());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    /**
     * Tests identifyDeptChair() method to retrieve the department chair's name.
     */
    @Test
    public void testIdentifyDeptChair() {
        ResponseEntity<?> response = routeController.identifyDeptChair("TEST");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Chair is the department chair.", response.getBody());
    }

    /**
     * Tests findCourseLocation() method to retrieve the location of a course.
     */
    @Test
    public void testFindCourseLocation() {
        HashMap<String, Course> courses = new HashMap<>();
        Course mockCourse = mock(Course.class);
        when(mockCourse.getCourseLocation()).thenReturn("Room 101");
        courses.put("101", mockCourse);

        Department mockDepartment = mock(Department.class);
        when(mockDepartment.getCourseSelection()).thenReturn(courses);
        departmentMapping.put("CS", mockDepartment);

        ResponseEntity<?> response = routeController.findCourseLocation("CS", 101);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Room 101 is where the course is located.", response.getBody());
    }

    /**
     * Tests findCourseLocation() method when the course is not found.
     */
    @Test
    public void testFindCourseLocationNotFound() {
        ResponseEntity<?> response = routeController.findCourseLocation("CS", 999);
        assertEquals("Course Not Found", response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests findCourseInstructor() method to retrieve the instructor of a course.
     */
    @Test
    public void testFindCourseInstructor() {
        HashMap<String, Course> courses = new HashMap<>();
        Course mockCourse = mock(Course.class);
        when(mockCourse.getInstructorName()).thenReturn("Dr. Smith");
        courses.put("101", mockCourse);

        Department mockDepartment = mock(Department.class);
        when(mockDepartment.getCourseSelection()).thenReturn(courses);
        departmentMapping.put("CS", mockDepartment);

        ResponseEntity<?> response = routeController.findCourseInstructor("CS", 101);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Dr. Smith is the instructor for the course.", response.getBody());
    }

    /**
     * Tests findCourseInstructor() method when the course is not found.
     */
    @Test
    public void testFindCourseInstructorNotFound() {
        ResponseEntity<?> response = routeController.findCourseInstructor("CS", 999);
        assertEquals("Course Not Found", response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests findCourseTime() method to retrieve the time slot of a course.
     */
    @Test
    public void testFindCourseTime() {
        HashMap<String, Course> courses = new HashMap<>();
        Course mockCourse = mock(Course.class);
        when(mockCourse.getCourseTimeSlot()).thenReturn("9:00 AM - 10:30 AM");
        courses.put("101", mockCourse);

        Department mockDepartment = mock(Department.class);
        when(mockDepartment.getCourseSelection()).thenReturn(courses);
        departmentMapping.put("CS", mockDepartment);

        ResponseEntity<?> response = routeController.findCourseTime("CS", 101);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The course meets at: 9:00 AM - 10:30 AM", response.getBody());
    }

    /**
     * Tests addMajorToDept() method to add a major to a department.
     */
    @Test
    public void testAddMajorToDept() {
        Department mockDepartment = mock(Department.class);
        departmentMapping.put("CS", mockDepartment);

        ResponseEntity<?> response = routeController.addMajorToDept("CS");
        verify(mockDepartment, times(1)).addPersonToMajor();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Tests removeMajorFromDept() method to remove a major from a department.
     */
    @Test
    public void testRemoveMajorFromDept() {
        Department mockDepartment = mock(Department.class);
        departmentMapping.put("CS", mockDepartment);

        ResponseEntity<?> response = routeController.removeMajorFromDept("CS");
        verify(mockDepartment, times(1)).dropPersonFromMajor();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Tests dropStudent() method to drop a student from a course.
     */
    @Test
    public void testDropStudentFromCourse() {
        HashMap<String, Course> courses = new HashMap<>();
        Course mockCourse = mock(Course.class);
        when(mockCourse.dropStudent()).thenReturn(true);
        courses.put("101", mockCourse);

        Department mockDepartment = mock(Department.class);
        when(mockDepartment.getCourseSelection()).thenReturn(courses);
        departmentMapping.put("CS", mockDepartment);

        ResponseEntity<?> response = routeController.dropStudent("CS", 101);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Student has been dropped.", response.getBody());
    }

    /**
     * Tests changeCourseTime() method to change the time of a course.
     */
    @Test
    public void testChangeCourseTime() {
        HashMap<String, Course> courses = new HashMap<>();
        Course mockCourse = mock(Course.class);
        courses.put("101", mockCourse);

        Department mockDepartment = mock(Department.class);
        when(mockDepartment.getCourseSelection()).thenReturn(courses);
        departmentMapping.put("CS", mockDepartment);

        ResponseEntity<?> response = routeController.changeCourseTime("CS", 101, "10:00 AM - 11:30 AM");
        verify(mockCourse, times(1)).reassignTime("10:00 AM - 11:30 AM");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Tests changeCourseTeacher() method to change the instructor of a course.
     */
    @Test
    public void testChangeCourseTeacher() {
        HashMap<String, Course> courses = new HashMap<>();
        Course mockCourse = mock(Course.class);
        courses.put("101", mockCourse);

        Department mockDepartment = mock(Department.class);
        when(mockDepartment.getCourseSelection()).thenReturn(courses);
        departmentMapping.put("CS", mockDepartment);

        ResponseEntity<?> response = routeController.changeCourseTeacher("CS", 101, "Dr. Johnson");
        verify(mockCourse, times(1)).reassignInstructor("Dr. Johnson");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Tests changeCourseLocation() method to change the location of a course.
     */
    @Test
    public void testChangeCourseLocation() {
        HashMap<String, Course> courses = new HashMap<>();
        Course mockCourse = mock(Course.class);
        courses.put("101", mockCourse);

        Department mockDepartment = mock(Department.class);
        when(mockDepartment.getCourseSelection()).thenReturn(courses);
        departmentMapping.put("CS", mockDepartment);

        ResponseEntity<?> response = routeController.changeCourseLocation("CS", 101, "Room 202");
        verify(mockCourse, times(1)).reassignLocation("Room 202");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
