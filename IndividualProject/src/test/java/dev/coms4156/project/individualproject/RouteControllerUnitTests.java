package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


/**
 * This is used to test toString method of Course class.
 */

@ContextConfiguration
@WebMvcTest(RouteController.class)
public class RouteControllerUnitTests {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private IndividualProjectApplication individualProjectApplication;

  @MockBean
  private MyFileDatabase myFileDatabase;

  @MockBean
  private Department departmentcs;

  @MockBean
  private Course course2500;




  private HashMap<String, Department> departmentMapping;
  private HashMap<String, Course> coursesOfferedByDept;

  /** This is the pre-set up for each test cases.*/
  @BeforeEach
  public void setupRouteControllerTesting() {
    departmentMapping = new HashMap<>();
    coursesOfferedByDept = new HashMap<>();
    coursesOfferedByDept.put("2500", new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 600));
    coursesOfferedByDept.put("3000", new Course("David", "Room 102", "11:00-12:00", 40));
    course2500 = coursesOfferedByDept.get("2500");

    Department departmentcs = mock(Department.class);

    departmentMapping.put("CS", new Department("CS", coursesOfferedByDept, "Nim Tottenham", 437));
    departmentcs = departmentMapping.get("CS");

    // Mock the behavior of myFileDatabase to return the departmentMapping
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
  }

  @Test
  public void testIndex() throws Exception {
    this.mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "Welcome, in order to make an API call direct your "
                            + "browser or Postman to an endpoint "
                    + "\n\n This can be done using the following format: \n\n http:127.0.0"
                    + ".1:8080/endpoint?arg=value"));
  }

  @Test
  public void testRetrieveDepartment_ValidDeptCode() throws Exception {
    String deptCode = "CS";  // Assuming "CS" is a valid department code
    when(IndividualProjectApplication.myFileDatabase.getDepartmentMapping())
            .thenReturn(departmentMapping);


    this.mockMvc.perform(get("/retrieveDept")
                    .param("deptCode", deptCode))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "CS 2500: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n"
                    + "CS 3000: \nInstructor: David; Location: Room 102; Time: 11:00-12:00\n"));
  }

  @Test
  public void testRetrieveDepartment_InvalidDeptCode() throws Exception {
    String deptCode = "Math";
    when(IndividualProjectApplication.myFileDatabase.getDepartmentMapping())
            .thenReturn(departmentMapping);


    this.mockMvc.perform(get("/retrieveDept")
                    .param("deptCode", deptCode))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testRetrieveCourse_validDeptCodeWithInvalidCourseCode() throws Exception {
    String deptCode = "CS";
    int  courseCode = 2222;


    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    when(departmentcs.getCourseSelection()).thenReturn(coursesOfferedByDept);

    this.mockMvc.perform(get("/retrieveCourse")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testRetrieveCourse_validDeptCodeWithValidCourseCode() throws Exception {
    String deptCode = "CS";
    int  courseCode = 2500;


    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    when(departmentcs.getCourseSelection()).thenReturn(coursesOfferedByDept);

    this.mockMvc.perform(get("/retrieveCourse")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55"));
  }

  @Test
  public void testRetrieveCourse_InvalidDeptCode() throws Exception {
    String deptCode = "Math";
    int  courseCode = 2500;
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(departmentcs.getCourseSelection()).thenReturn(coursesOfferedByDept);
    this.mockMvc.perform(get("/retrieveCourse")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testIsCourseFull_InvalidCourseCode() throws Exception {
    String deptCode = "CS";
    int  courseCode = 22222;
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(departmentcs.getCourseSelection()).thenReturn(coursesOfferedByDept);

    this.mockMvc.perform(get("/isCourseFull")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testIsCourseFull_CourseExistsIsNotFull() throws Exception {
    String deptCode = "CS";
    int  courseCode = 2500;
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(departmentcs.getCourseSelection()).thenReturn(coursesOfferedByDept);

    this.mockMvc.perform(get("/isCourseFull")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isOk())
            .andExpect(content().string("false"));
  }

  @Test
  public void testIsCourseFull_CourseExistsIsFull() throws Exception {
    String deptCode = "CS";
    int  courseCode = 3000;
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    when(departmentcs.getCourseSelection()).thenReturn(coursesOfferedByDept);

    this.mockMvc.perform(get("/isCourseFull")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
  }


  @Test
  public void testGetMajorCountFromDept_DeptNotExists() throws Exception {
    String deptCode = "Math";
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    this.mockMvc.perform(get("/getMajorCountFromDept")
                    .param("deptCode", deptCode))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }


  @Test
  public void testGetMajorCountFromDept_DeptExists() throws Exception {
    String deptCode = "CS";
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    this.mockMvc.perform(get("/getMajorCountFromDept")
                    .param("deptCode", deptCode))
            .andExpect(status().isOk())
            .andExpect(content().string("There are: 437 majors in the department"));
  }

  @Test
  public void testGetIdDeptChair_DeptNotExists() throws Exception {
    String deptCode = "Math";
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    this.mockMvc.perform(get("/idDeptChair")
                    .param("deptCode", deptCode))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testGetIdDeptChair_DeptExists() throws Exception {
    String deptCode = "CS";
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    this.mockMvc.perform(get("/idDeptChair")
                    .param("deptCode", deptCode))
            .andExpect(status().isOk())
            .andExpect(content().string("Nim Tottenham is the department chair."));
  }

  @Test
  public void testFindCourseLocation_CourseExists() throws Exception {
    String deptCode = "CS";
    int  courseCode = 2500;
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    this.mockMvc.perform(get("/findCourseLocation")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isOk())
            .andExpect(content().string("417 IAB is where the course is located."));
  }

  @Test
  public void testFindCourseLocation_CourseNotExists() throws Exception {
    String deptCode = "math";
    int  courseCode = 2500;
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    this.mockMvc.perform(get("/findCourseLocation")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testFindCourseInstructor_CourseNotExists() throws Exception {
    String deptCode = "Math";
    int  courseCode = 2222;
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    this.mockMvc.perform(get("/findCourseInstructor")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testFindCourseInstructor_CourseExists() throws Exception {
    String deptCode = "CS";
    int  courseCode = 2500;
    this.mockMvc.perform(get("/findCourseInstructor")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isOk())
            .andExpect(content().string("Griffin Newbold is the instructor for the course."));
  }


  @Test
  public void testFindCourseTime_CourseNotExists() throws Exception {
    String deptCode = "Math";
    int  courseCode = 2222;
    this.mockMvc.perform(get("/findCourseTime")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testFindCourseTime_CourseExists() throws Exception {
    String deptCode = "CS";
    int  courseCode = 2500;
    this.mockMvc.perform(get("/findCourseTime")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isOk())
            .andExpect(content().string("The course meets at: 11:40-12:55."));
  }


  @Test
  public void testAddMajorToDept_ValidDepartment() throws Exception {
    String deptCode = "CS";
    Department departmentcs = mock(Department.class);
    departmentMapping.put("CS", departmentcs);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    this.mockMvc.perform(patch("/addMajorToDept")
                    .param("deptCode", deptCode))
            .andExpect(status().isOk())
            .andExpect(content().string("Attribute was updated successfully"));

    // Verify that addPersonToMajor() was called on the mock department
    verify(departmentcs, times(1)).addPersonToMajor();
  }

  @Test
  public void testAddMajorToDept_InvalidDepartment() throws Exception {
    String deptCode = "Math";
    Department departmentcs = mock(Department.class);
    departmentMapping.put("CS", departmentcs);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    this.mockMvc.perform(patch("/addMajorToDept")
                    .param("deptCode", deptCode))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testRemoveMajorFromDept_MajorDroppedSuccessfully() throws Exception {
    String deptCode = "CS";

    Department departmentcs = new Department("CS", coursesOfferedByDept, "David", 100);
    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("CS", departmentcs);

    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    this.mockMvc.perform(patch("/removeMajorFromDept")
                    .param("deptCode", deptCode))
            .andExpect(status().isOk())
            .andExpect(content().string("Attribute was updated or is at minimum"));
    assertEquals(99, departmentcs.getNumberOfMajors());
  }

  @Test
  public void testRemoveMajorFromDept_NoMajorsToDrop() throws Exception {
    String deptCode = "CS";
    Department departmentcs = new Department("CS", coursesOfferedByDept, "David", 0);
    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("CS", departmentcs);

    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    this.mockMvc.perform(patch("/removeMajorFromDept")
                    .param("deptCode", deptCode))
            .andExpect(status().isOk())
            .andExpect(content().string("Attribute was updated or is at minimum"));
    assertEquals(0, departmentcs.getNumberOfMajors());
  }

  @Test
  public void testRemoveMajorFromDept_InvalidDepartment() throws Exception {
    String deptCode = "MATH";
    when(myFileDatabase.getDepartmentMapping()).thenReturn(new HashMap<>());
    this.mockMvc.perform(patch("/removeMajorFromDept")
                    .param("deptCode", deptCode))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Department Not Found"));
  }


  @Test
  public void testDropStudent_CourseNotFound() throws Exception {
    String deptCode = "MATH";
    int courseCode = 2222;

    this.mockMvc.perform(patch("/dropStudentFromCourse")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testDropStudent_dropSuccessfully() throws Exception {
    String deptCode = "CS";
    int courseCode = 2500;

    this.mockMvc.perform(patch("/dropStudentFromCourse")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isOk())
            .andExpect(content().string("Student has been dropped."));
    assertEquals(499, course2500.getEnrolledStudentCount());
  }

  @Test
  public void testDropStudent_NotDropped() throws Exception {
    int courseCode = 2500;

    HashMap<String, Course> coursesMapping = new HashMap<>();
    Course mockCourse = mock(Course.class);
    when(mockCourse.dropStudent()).thenReturn(false);
    coursesMapping.put(Integer.toString(courseCode), mockCourse);
    Department mockDepartment = mock(Department.class);
    when(mockDepartment.getCourseSelection()).thenReturn(coursesMapping);
    String deptCode = "CS";
    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put(deptCode, mockDepartment);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    this.mockMvc.perform(patch("/dropStudentFromCourse")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Student has not been dropped."));

  }

  @Test
  public void testSetEnrollmentCount_CourseNotFound() throws Exception {
    String deptCode = "MATH";
    int courseCode = 2222;
    int count = 40;

    this.mockMvc.perform(patch("/setEnrollmentCount")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode))
                    .param("count", String.valueOf(count)))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testSetEnrollmentCount_SuccessfulSet() throws Exception {
    String deptCode = "CS";
    int courseCode = 2500;
    HashMap<String, Course> coursesMapping = new HashMap<>();
    Course mockCourse = mock(Course.class);
    coursesMapping.put(Integer.toString(courseCode), mockCourse);
    Department mockDepartment = mock(Department.class);
    when(mockDepartment.getCourseSelection()).thenReturn(coursesMapping);
    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put(deptCode, mockDepartment);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    int count = 40;
    this.mockMvc.perform(patch("/setEnrollmentCount")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode))
                    .param("count", String.valueOf(count)))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));
    verify(mockCourse, times(1)).setEnrolledStudentCount(count);

  }

  @Test
  public void testChangeCourseTime_CourseNotFound() throws Exception {
    String deptCode = "MATH";
    int courseCode = 2222;
    String time = "11:30-12:30";

    this.mockMvc.perform(patch("/changeCourseTime")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode))
                    .param("time", time))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }


  @Test
  public void testChangeCourseTime_SuccessfullyChanged() throws Exception {
    String deptCode = "CS";
    int courseCode = 2500;

    HashMap<String, Course> coursesMapping = new HashMap<>();
    Course mockCourse = mock(Course.class);
    coursesMapping.put(Integer.toString(courseCode), mockCourse);
    Department mockDepartment = mock(Department.class);
    when(mockDepartment.getCourseSelection()).thenReturn(coursesMapping);
    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put(deptCode, mockDepartment);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    String time = "11:30-12:30";

    this.mockMvc.perform(patch("/changeCourseTime")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode))
                    .param("time", time))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));
    verify(mockCourse, times(1)).reassignTime(time);
  }

  @Test
  public void testChangeCourseTeacher_CourseNotFound() throws Exception {
    String deptCode = "MATH";
    int courseCode = 2222;
    String teacher = "David";

    this.mockMvc.perform(patch("/changeCourseTeacher")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode))
                    .param("teacher", teacher))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testChangeCourseTeacher_SuccessfullyChanged() throws Exception {
    String deptCode = "CS";
    int courseCode = 2500;

    HashMap<String, Course> coursesMapping = new HashMap<>();
    Course mockCourse = mock(Course.class);
    coursesMapping.put(Integer.toString(courseCode), mockCourse);
    Department mockDepartment = mock(Department.class);
    when(mockDepartment.getCourseSelection()).thenReturn(coursesMapping);
    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put(deptCode, mockDepartment);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    String teacher = "David";

    this.mockMvc.perform(patch("/changeCourseTeacher")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode))
                    .param("teacher", teacher))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));
    verify(mockCourse, times(1)).reassignInstructor(teacher);
  }

  @Test
  public void testChangeCourseLocation_CourseNotFound() throws Exception {
    String deptCode = "MATH";
    int courseCode = 2222;
    String location = "locationA";

    this.mockMvc.perform(patch("/changeCourseLocation")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode))
                    .param("location", location))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testChangeCourseLocation_SuccessfullyChanged() throws Exception {
    String deptCode = "CS";
    int courseCode = 2500;

    HashMap<String, Course> coursesMapping = new HashMap<>();
    Course mockCourse = mock(Course.class);
    coursesMapping.put(Integer.toString(courseCode), mockCourse);
    Department mockDepartment = mock(Department.class);
    when(mockDepartment.getCourseSelection()).thenReturn(coursesMapping);
    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put(deptCode, mockDepartment);
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    String location = "locationA";

    this.mockMvc.perform(patch("/changeCourseLocation")
                    .param("deptCode", deptCode)
                    .param("courseCode", String.valueOf(courseCode))
                    .param("location", location))
            .andExpect(status().isOk())
            .andExpect(content().string("Attributed was updated successfully."));
    verify(mockCourse, times(1)).reassignLocation(location);
  }
}

