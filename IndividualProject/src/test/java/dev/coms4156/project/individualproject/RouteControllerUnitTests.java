package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class RouteControllerTest {

    private RouteController routeController;
    private TestMyFileDatabase testDatabase;
    private Map<String, Department> testDepartmentMapping;

    private static final String EXISTING_DEPT_CODE = "COMS";
    private static final String NON_EXISTING_DEPT_CODE = "BADDEPTCODE";
    private static final int EXISTING_COURSE_CODE = 1001;
    private static final int NON_EXISTING_COURSE_CODE = 9191;
    private static final String DEPT_CHAIR = "Mr. Chair";
    private static final int NUMBER_OF_MAJORS = 7;
    private static final String COURSE_INSTRUCTOR = "Mrs. Teacher";
    private static final String COURSE_LOCATION = "The Library";
    private static final String COURSE_TIME = "11:00-11:01";
    private static final int COURSE_CAPACITY = 75;

    @BeforeEach
    void setUp() {
        routeController = new RouteController();
        testDepartmentMapping = new HashMap<>();
        
        Map<String, Course> testCourses = new HashMap<>();
        testCourses.put(String.valueOf(EXISTING_COURSE_CODE), 
            new TestCourse(COURSE_INSTRUCTOR, COURSE_LOCATION, COURSE_TIME, COURSE_CAPACITY, true));

        Department testDepartment = new TestDepartment(EXISTING_DEPT_CODE, testCourses, DEPT_CHAIR, NUMBER_OF_MAJORS);
        testDepartmentMapping.put(EXISTING_DEPT_CODE, testDepartment);

        testDatabase = new TestMyFileDatabase(testDepartmentMapping);
        IndividualProjectApplication.myFileDatabase = testDatabase;
    }

    @Test
    void testRetrieveDepartmentKnownDepartment() {
        ResponseEntity<?> response = routeController.retrieveDepartment(EXISTING_DEPT_CODE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testRetrieveDepartmentUnknownDepartment() {
        ResponseEntity<?> response = routeController.retrieveDepartment(NON_EXISTING_DEPT_CODE);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Department Not Found", response.getBody());
    }

    @Test
    void testRetrieveCourseKnownCourse() {
        ResponseEntity<?> response = routeController.retrieveCourse(EXISTING_DEPT_CODE, EXISTING_COURSE_CODE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testRetrieveCourseUnknownCourse() {
        ResponseEntity<?> response = routeController.retrieveCourse(EXISTING_DEPT_CODE, NON_EXISTING_COURSE_CODE);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Course Not Found", response.getBody());
    }

    @Test
    void testIsCourseFull_FullCourse() {
        ResponseEntity<?> response = routeController.isCourseFull(EXISTING_DEPT_CODE, EXISTING_COURSE_CODE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    void testGetMajorCtFromDept_ExistingDepartment() {
        ResponseEntity<?> response = routeController.getMajorCtFromDept(EXISTING_DEPT_CODE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((String) response.getBody()).contains(String.valueOf(NUMBER_OF_MAJORS)));
    }

    @Test
    void testIdentifyDeptChair_ExistingDepartment() {
        ResponseEntity<?> response = routeController.identifyDeptChair(EXISTING_DEPT_CODE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((String) response.getBody()).contains(DEPT_CHAIR));
    }

    @Test
    void testFindCourseLocation_ExistingCourse() {
        ResponseEntity<?> response = routeController.findCourseLocation(EXISTING_DEPT_CODE, EXISTING_COURSE_CODE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((String) response.getBody()).contains(COURSE_LOCATION));
    }

    @Test
    void testAddMajorToDept() {
        ResponseEntity<?> response = routeController.addMajorToDept(EXISTING_DEPT_CODE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((String) response.getBody()).contains(String.valueOf(NUMBER_OF_MAJORS + 1)));
    }

    @Test
    void testDropStudentFromCourse() {
        ResponseEntity<?> response = routeController.dropStudentFromCourse(EXISTING_DEPT_CODE, EXISTING_COURSE_CODE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((String) response.getBody()).contains("Student Dropped"));
    }

    private static class TestMyFileDatabase extends MyFileDatabase {
        private Map<String, Department> mapping;

        public TestMyFileDatabase(Map<String, Department> mapping) {
            super(0, "test.txt");
            this.mapping = mapping;
        }

        @Override
        public Map<String, Department> getDepartmentMapping() {
            return mapping;
        }
    }

    private static class TestDepartment extends Department {
        public TestDepartment(String deptCode, Map<String, Course> courses, String departmentChair, int numberOfMajors) {
            super(deptCode, courses, departmentChair, numberOfMajors);
        }
    }

    private static class TestCourse extends Course {
        private boolean isFull;

        public TestCourse(String instructorName, String courseLocation, String courseTimeSlot, int capacity, boolean isFull) {
            super(instructorName, courseLocation, courseTimeSlot, capacity);
            this.isFull = isFull;
        }

        @Override
        public boolean isCourseFull() {
            return isFull;
        }
    }
}