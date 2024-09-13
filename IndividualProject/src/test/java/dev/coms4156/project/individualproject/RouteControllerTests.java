package dev.coms4156.project.individualproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.is;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;



import java.util.HashMap;

import dev.coms4156.project.individualproject.MyFileDatabase;
import dev.coms4156.project.individualproject.Department;
import dev.coms4156.project.individualproject.RouteController;


@WebMvcTest(RouteController.class)
public class RouteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyFileDatabase myFileDatabase;

    @BeforeEach
    void setUp() {
        HashMap<String, Department> departmentData = new HashMap<>();
        HashMap<String, Course> courses = new HashMap<>();
        courses.put("101", new Course("Mr. Shen", "HAM 101", "9 AM - 10 AM", 30));
        Course fullCourse = new Course("Mr. F", "HAM 201", "2 PM - 3 PM", 25);
        fullCourse.setEnrolledStudentCount(25);
        Course notFullCourse = new Course("Mr. NF", "HAM 301", "1 PM - 2 PM", 35);
        notFullCourse.setEnrolledStudentCount(20);

        courses.put("201", fullCourse);
        courses.put("301", notFullCourse);
        Department csDept = new Department("CS", courses, "Dr. Smith", 100);
        departmentData.put("CS", csDept);
        when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentData);
    }

    @Test
    public void retrieveDepartmentWhenExists() throws Exception {
        mockMvc.perform(get("/retrieveDept?deptCode=CS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departmentChair").value("Dr. Smith"));
    }

    @Test
    public void retrieveDepartmentWhenDepartmentDoesNotExist() throws Exception {
        mockMvc.perform(get("/retrieveDept?deptCode=EEE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Department Not Found")));
    }

    @Test
    public void retrieveCourseWhenExists() throws Exception {
        mockMvc.perform(get("/retrieveCourse?deptCode=CS&courseCode=101")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.instructorName").value("Mr. Shen"));
    }

    @Test
    public void retrieveCourseWhenCourseDoesNotExist() throws Exception {
        mockMvc.perform(get("/retrieveCourse?deptCode=CS&courseCode=999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Course Not Found")));
    }

    @Test
    public void retrieveCourseWhenDepartmentDoesNotExist() throws Exception {
        mockMvc.perform(get("/retrieveCourse?deptCode=ECON&courseCode=101")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Department Not Found")));
    }

    @Test
    public void isCourseFullWhenCourseIsFull() throws Exception {
        mockMvc.perform(get("/isCourseFull?deptCode=CS&courseCode=201")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void isCourseFullWhenCourseIsNotFull() throws Exception {
        mockMvc.perform(get("/isCourseFull?deptCode=CS&courseCode=301")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void isCourseFullWhenCourseDoesNotExist() throws Exception {
        mockMvc.perform(get("/isCourseFull?deptCode=CS&courseCode=777")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Course Not Found")));
    }

    @Test
    public void isCourseFullWhenDepartmentDoesNotExist() throws Exception {
        mockMvc.perform(get("/isCourseFull?deptCode=ECON&courseCode=101")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Department Not Found")));
    }

    @Test
    public void getMajorCountWhenDepartmentExists() throws Exception {
        mockMvc.perform(get("/getMajorCountFromDept?deptCode=CS")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("There are: 100 majors in the department")));
    }

    @Test
    public void getMajorCountWhenDepartmentDoesNotExist() throws Exception {
        mockMvc.perform(get("/getMajorCountFromDept?deptCode=ECON")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("Department Not Found")));
    }

    @Test
    public void identifyDeptChairWhenDepartmentExists() throws Exception {
        mockMvc.perform(get("/idDeptChair?deptCode=CS")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Dr. Smith is the department chair.")));
    }

    @Test
    public void identifyDeptChairWhenDepartmentDoesNotExist() throws Exception {
        mockMvc.perform(get("/idDeptChair?deptCode=ECON")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Department Not Found")));
    }

    @Test
    public void findCourseLocationWhenCourseExists() throws Exception {
        mockMvc.perform(get("/findCourseLocation?deptCode=CS&courseCode=101")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("HAM 101 is where the course is located.")));
    }

    @Test
    public void findCourseLocationWhenCourseDoesNotExist() throws Exception {
        mockMvc.perform(get("/findCourseLocation?deptCode=CS&courseCode=777")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Course Not Found")));
    }

    @Test
    public void findCourseLocationWhenDepartmentDoesNotExist() throws Exception {
        mockMvc.perform(get("/findCourseLocation?deptCode=BIO&courseCode=101")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Course Not Found")));
    }

    @Test
    public void findCourseInstructorWhenCourseExists() throws Exception {
        mockMvc.perform(get("/findCourseInstructor?deptCode=CS&courseCode=101")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Mr. Shen is the instructor for the course.")));
    }

    @Test
    public void findCourseInstructorWhenCourseDoesNotExist() throws Exception {
        mockMvc.perform(get("/findCourseInstructor?deptCode=CS&courseCode=404")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Course Not Found")));
    }

    @Test
    public void findCourseInstructorWhenDepartmentDoesNotExist() throws Exception {
        mockMvc.perform(get("/findCourseInstructor?deptCode=PHYS&courseCode=202")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Course Not Found")));
    }

    @Test
    public void findCourseTimeWhenCourseExists() throws Exception {
        mockMvc.perform(get("/findCourseTime?deptCode=CS&courseCode=101")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("The course meets at: 9 AM - 10 AM")));
    }

    @Test
    public void findCourseTimeWhenCourseDoesNotExist() throws Exception {
        mockMvc.perform(get("/findCourseTime?deptCode=CS&courseCode=404")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Course Not Found")));
    }

    @Test
    public void findCourseTimeWhenDepartmentDoesNotExist() throws Exception {
        mockMvc.perform(get("/findCourseTime?deptCode=ME&courseCode=101")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Department Not Found")));
    }

    @Test
    public void addMajorToDeptWhenDepartmentExists() throws Exception {
        mockMvc.perform(patch("/addMajorToDept?deptCode=CS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Attribute was updated successfully")));

        verify(myFileDatabase, times(1)).getDepartmentMapping();
        Department csDept = myFileDatabase.getDepartmentMapping().get("CS");
        assertThat(csDept.getNumberOfMajors(), is(101));  // Assuming the `addPersonToMajor` increments the count by 1
    }

    @Test
    public void addMajorToDeptWhenDepartmentDoesNotExist() throws Exception {
        mockMvc.perform(patch("/addMajorToDept?deptCode=ME")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Department Not Found")));

        verify(myFileDatabase, times(1)).getDepartmentMapping();
    }

    @Test
    public void removeMajorFromDeptSuccess() throws Exception {
        mockMvc.perform(patch("/removeMajorFromDept?deptCode=CS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Attribute was updated successfully")));
    }

    @Test
    public void removeMajorFromDeptNotFound() throws Exception {
        mockMvc.perform(patch("/removeMajorFromDept?deptCode=EEE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Department Not Found")));
    }

    @Test
    public void dropStudentFromCourseSuccess() throws Exception {
        mockMvc.perform(patch("/dropStudentFromCourse?deptCode=CS&courseCode=101")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Student has been dropped.")));
    }

//    @Test
//    public void dropStudentFromCourseFail() throws Exception {
//        Course course = new Course("Dr. Jane", "MUD 100", "10 AM - 11 AM", 30);
//        course.setEnrolledStudentCount(0); // No students to drop
//        when(course.dropStudent()).thenReturn(false);
//
//        mockMvc.perform(patch("/dropStudentFromCourse?deptCode=CS&courseCode=101")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string(containsString("Student has not been dropped.")));
//    }

    @Test
    public void dropStudentFromCourseNotFound() throws Exception {
        mockMvc.perform(patch("/dropStudentFromCourse?deptCode=CS&courseCode=999") // Non-existent course
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Course Not Found")));
    }


    @Test
    void setEnrollmentCountSuccess() throws Exception {
        mockMvc.perform(patch("/setEnrollmentCount?deptCode=CS&courseCode=101&count=30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Attribute was updated successfully.")));
    }

    @Test
    void setEnrollmentCountCourseNotFound() throws Exception {
        when(myFileDatabase.getDepartmentMapping()).thenReturn(new HashMap<>());

        mockMvc.perform(patch("/setEnrollmentCount?deptCode=CS&courseCode=101&count=30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Course Not Found")));
    }

    @Test
    void changeCourseTimeSuccess() throws Exception {
        mockMvc.perform(patch("/changeCourseTime?deptCode=CS&courseCode=101&time=13:00 - 14:30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Attribute was updated successfully.")));

        verify(myFileDatabase).getDepartmentMapping();
    }

    @Test
    void changeCourseTimeCourseNotFound() throws Exception {
        mockMvc.perform(patch("/changeCourseTime?deptCode=CS&courseCode=999&time=09:00 - 10:30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Course Not Found")));
    }

    @Test
    void changeCourseTeacherSuccess() throws Exception {
        mockMvc.perform(patch("/changeCourseTeacher?deptCode=CS&courseCode=101&teacher=Dr. ABC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Attributed was updated successfully.")));

        verify(myFileDatabase).getDepartmentMapping();
    }

    @Test
    void changeCourseTeacherNotFound() throws Exception {
        mockMvc.perform(patch("/changeCourseTeacher?deptCode=CS&courseCode=999&teacher=Dr. ABC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Course Not Found")));
    }

    @Test
    void changeCourseLocationSuccess() throws Exception {
        mockMvc.perform(patch("/changeCourseLocation?deptCode=CS&courseCode=101&location=New Location")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Attributed was updated successfully.")));

        verify(myFileDatabase).getDepartmentMapping();
    }

    @Test
    void changeCourseLocationNotFound() throws Exception {
        // Assuming the course code does not exist
        mockMvc.perform(patch("/changeCourseLocation?deptCode=CS&courseCode=999&location=New Location")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Course Not Found")));
    }
}






