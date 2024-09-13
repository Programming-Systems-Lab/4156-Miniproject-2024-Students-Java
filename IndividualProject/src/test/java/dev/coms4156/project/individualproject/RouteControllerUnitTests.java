package dev.coms4156.project.individualproject;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Represents a unit test class for the RouteController class. This class tests various APIs written
 * in the RouteController class covering many edge cases.
 */
class RouteControllerUnitTests {

  @BeforeEach
  void setupRouteControllerForTesting() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(mockController).build();
    IndividualProjectApplication.overrideDatabase(mockDb);
  }

  @Test
  void retriveDeptTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Department dept = new Department("ECON", courses, "Michael Woodford", 2345);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("ECON", dept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/retrieveDept").param("deptCode", "ECON").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(dept.toString()));
  }

  @Test
  void retrieveDeptDoesntExistTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Department dept = new Department("ECON", courses, "Michael Woodford", 2345);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("ECON", dept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/retrieveDept").param("deptCode", "COMS").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  void isCourseFullTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(120);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/isCourseFull")
                .param("deptCode", "COMS")
                .param("courseCode", "123")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  void isCourseNotFullTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/isCourseFull")
                .param("deptCode", "COMS")
                .param("courseCode", "123")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
  }

  @Test
  void isCourseFullCourseNotFoundTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/isCourseFull")
                .param("deptCode", "COMSS")
                .param("courseCode", "205")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void getMajorCountFromDeptTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/getMajorCountFromDept")
                .param("deptCode", "COMS")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("There are: 2500 majors in the department"));
  }

  @Test
  void getMajorCountFromDeptNotFoundTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/getMajorCountFromDept")
                .param("deptCode", "CSEE")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  void idDeptChairTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/idDeptChair").param("deptCode", "COMS").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Daniel Bauer is the department chair."));
  }

  @Test
  void idDeptChairNotFoundTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/idDeptChair").param("deptCode", "ECON").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  void findCourseLocationTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/findCourseLocation")
                .param("deptCode", "COMS")
                .param("courseCode", "123")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Zoom is where the course is located."));
  }

  @Test
  void findCourseLocationCourseNotFoundTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/findCourseLocation")
                .param("deptCode", "COMS")
                .param("courseCode", "1234")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void findCourseInstructorTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/findCourseInstructor")
                .param("deptCode", "COMS")
                .param("courseCode", "123")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Gail Kaiser is the instructor for the course."));
  }

  @Test
  void findCourseInstructorCourseNotFoundTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/findCourseInstructor")
                .param("deptCode", "COMS")
                .param("courseCode", "1234")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void findCourseTimeTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/findCourseTime")
                .param("deptCode", "COMS")
                .param("courseCode", "123")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("The course meets at: 10:10-11:20"));
  }

  @Test
  void findCourseTimeCourseNotFoundTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            get("/findCourseTime")
                .param("deptCode", "COMS")
                .param("courseCode", "1234")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void addMajorToDeptTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/addMajorToDept")
                .param("deptCode", "COMS")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated successfully"));
  }

  @Test
  void addMajorToDeptDepartmentNotFoundTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/addMajorToDept")
                .param("deptCode", "ECON")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  void removeMajorFromDeptTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/removeMajorFromDept")
                .param("deptCode", "COMS")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated or is at minimum"));
  }

  @Test
  void removeMajorFromDeptDepartmentNotFoundTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/removeMajorFromDept")
                .param("deptCode", "ECON")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  void dropStudentFromCourseStudentDroppedTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(105);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/dropStudentFromCourse")
                .param("deptCode", "COMS")
                .param("courseCode", "123")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Student has been dropped."));
  }

  @Test
  void dropStudentFromCourseStudentNotDroppedTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(0);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/dropStudentFromCourse")
                .param("deptCode", "COMS")
                .param("courseCode", "123")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Student has not been dropped."));
  }

  @Test
  void dropStudentFromCourseButCourseNotFoundTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/dropStudentFromCourse")
                .param("deptCode", "COMS")
                .param("courseCode", "1234")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void setEnrollmentCountTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(105);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/setEnrollmentCount")
                .param("deptCode", "COMS")
                .param("courseCode", "123")
                .param("count", "110")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
            content().string("Course enrollment count is updated successfully to the value 110."));
  }

  @Test
  void setEnrollmentCountCourseNotFoundTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("105", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/setEnrollmentCount")
                .param("deptCode", "COMS")
                .param("courseCode", "1234")
                .param("count", "110")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void changeCourseTimeTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(105);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/changeCourseTime")
                .param("deptCode", "COMS")
                .param("courseCode", "123")
                .param("time", "12:00-13:00")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .string("Course time slot is updated successfully to the value '12:00-13:00'."));
  }

  @Test
  void changeCourseTimeCourseNotFoundTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("105", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/changeCourseTime")
                .param("deptCode", "COMS")
                .param("courseCode", "1234")
                .param("time", "12:00-13:00")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void changeCourseTeacherTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(105);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/changeCourseTeacher")
                .param("deptCode", "COMS")
                .param("courseCode", "123")
                .param("teacher", "Donald Ferguson")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .string(
                    "Course instructor is updated successfully to the value 'Donald Ferguson'."));
  }

  @Test
  void changeCourseTeacherCourseNotFoundTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("105", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/changeCourseTeacher")
                .param("deptCode", "COMS")
                .param("courseCode", "1234")
                .param("teacher", "Donald Ferguson")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void changeCourseLocationTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(105);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("123", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/changeCourseLocation")
                .param("deptCode", "COMS")
                .param("courseCode", "123")
                .param("location", "MUDD 252")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
            content().string("Course location is updated successfully to the value 'MUDD 252'."));
  }

  @Test
  void changeCourseLocationCourseNotFoundTest() throws Exception {
    Course testCourse = new Course("Gail Kaiser", "Zoom", "10:10-11:20", 120);
    testCourse.setEnrolledStudentCount(119);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("105", testCourse);
    Department testDept = new Department("COMS", courses, "Daniel Bauer", 2500);
    HashMap<String, Department> deptMapping = new HashMap<>();
    deptMapping.put("COMS", testDept);

    when(mockDb.getDepartmentMapping()).thenReturn(deptMapping);

    mockMvc
        .perform(
            patch("/changeCourseLocation")
                .param("deptCode", "COMS")
                .param("courseCode", "1234")
                .param("location", "MUDD 252")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  private MockMvc mockMvc;

  @Mock private MyFileDatabase mockDb;

  @InjectMocks private RouteController mockController;
}
