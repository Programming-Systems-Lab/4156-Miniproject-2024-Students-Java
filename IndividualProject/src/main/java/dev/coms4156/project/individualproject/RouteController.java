package dev.coms4156.project.individualproject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains all the API routes for the system.
 */
@RestController
public class RouteController {

  /**
   * Redirects to the homepage.
   *
   * @return A String containing the name of the html file to be loaded.
   */
  @GetMapping({"/", "/index", "/home"})
  public String index() {
    return "Welcome, in order to make an API call direct your browser or Postman to an endpoint "
        + "\n\n This can be done using the following format: \n\n http:127.0.0"
        + ".1:8080/endpoint?arg=value";
  }

  /**
   * Returns the details of the specified department.
   *
   * @param deptCode A {@code String} representing the department the user wishes
   *                 to retrieve.
   *
   * @return A {@code ResponseEntity} object containing either the details of the Department and
   *         an HTTP 200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/retrieveDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveDepartment(@RequestParam(DEPT_CODE) String deptCode) {
    try {
      Map<String, Department> departmentMapping;
      departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

      if (!departmentMapping.containsKey(deptCode.toUpperCase(Locale.ROOT))) {
        return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
      } else {
        return new ResponseEntity<>(departmentMapping.get(deptCode.toUpperCase(Locale.ROOT)),
            HttpStatus.OK);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }


  /**
   * Displays the details of the requested course to the user or displays the proper error
   * message in response to the request.
   *
   * @param deptCode   A {@code String} representing the department the user wishes
   *                   to find the course in.
   *
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to retrieve.
   *
   * @return           A {@code ResponseEntity} object containing either the details of the
   *                   course and an HTTP 200 response or, an appropriate message indicating the
   *                   proper response.
   */
  @GetMapping(value = "/retrieveCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveCourse(
      @RequestParam(DEPT_CODE) String deptCode,
      @RequestParam(COURSE_CODE) int courseCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        if (!coursesMapping.containsKey(Integer.toString(courseCode))) {
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        } else {
          return new ResponseEntity<>(coursesMapping.get(Integer.toString(courseCode)),
              HttpStatus.OK);
        }
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays whether the course has at minimum reached its enrollmentCapacity.
   *
   * @param deptCode   A {@code String} representing the department the user wishes
   *                   to find the course in.
   *
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to retrieve.
   *
   * @return           A {@code ResponseEntity} object containing either the requested information
   *                   and an HTTP 200 response or, an appropriate message indicating the proper
   *                   response.
   */
  @GetMapping(value = "/isCourseFull", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> isCourseFull(
      @RequestParam(DEPT_CODE) String deptCode,
      @RequestParam(COURSE_CODE) int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(requestedCourse.isCourseFull(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the number of majors in the specified department.
   *
   * @param deptCode     A {@code String} representing the department the user wishes
   *                     to find number of majors for.
   *
   * @return             A {@code ResponseEntity} object containing either number of majors for the
   *                     specified department and an HTTP 200 response or, an appropriate message
   *                     indicating the proper response.
   */
  @GetMapping(value = "/getMajorCountFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getMajorCtFromDept(@RequestParam(DEPT_CODE) String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

        int numOfMajors = departmentMapping.get(deptCode).getNumberOfMajors();
        Map<String, Integer> response = new HashMap<>();
        response.put("numberOfMajors", numOfMajors);
        return new ResponseEntity<>(response, HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the department chair for the specified department.
   *
   * @param deptCode  A {@code String} representing the department the user wishes
   *                  to find the department chair of.
   *
   * @return          A {@code ResponseEntity} object containing either department chair of the
   *                  specified department and an HTTP 200 response or, an appropriate message
   *                  indicating the proper response.
   */
  @GetMapping(value = "/idDeptChair", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> identifyDeptChair(@RequestParam(DEPT_CODE) String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

        String deptChair = departmentMapping.get(deptCode).getDepartmentChair();
        Map<String, String> response = new HashMap<>();
        response.put("departmentChair", deptChair);
        return new ResponseEntity<>(response, HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the location for the specified course.
   *
   * @param deptCode   A {@code String} representing the department the user wishes
   *                   to find the course in.
   *
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to find information about.
   *
   * @return           A {@code ResponseEntity} object containing either the location of the
   *                   course and an HTTP 200 response or, an appropriate message indicating the
   *                   proper response.
   */
  @GetMapping(value = "/findCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseLocation(
      @RequestParam(DEPT_CODE) String deptCode,
      @RequestParam(COURSE_CODE) int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        Map<String, String> response = new HashMap<>();
        response.put("location", requestedCourse.getCourseLocation());
        return new ResponseEntity<>(response, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the instructor for the specified course.
   *
   * @param deptCode   A {@code String} representing the department the user wishes
   *                   to find the course in.
   *
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to find information about.
   *
   * @return           A {@code ResponseEntity} object containing either the course instructor and
   *                   an HTTP 200 response or, an appropriate message indicating the proper
   *                   response.
   */
  @GetMapping(value = "/findCourseInstructor", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseInstructor(
      @RequestParam(DEPT_CODE) String deptCode,
      @RequestParam(COURSE_CODE) int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        Map<String, String> response = new HashMap<>();
        response.put("instructor", requestedCourse.getInstructorName());
        return new ResponseEntity<>(response, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the time the course meets at for the specified course.
   *
   * @param deptCode   A {@code String} representing the department the user wishes
   *                   to find the course in.
   *
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to find information about.
   *
   * @return           A {@code ResponseEntity} object containing either the details of the
   *                   course timeslot and an HTTP 200 response or, an appropriate message
   *                   indicating the proper response.
   */
  @GetMapping(value = "/findCourseTime", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseTime(
      @RequestParam(DEPT_CODE) String deptCode,
      @RequestParam(COURSE_CODE) int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        Map<String, String> response = new HashMap<>();
        response.put("courseTime", requestedCourse.getCourseTimeSlot());
        return new ResponseEntity<>(response, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to add a student to the specified department.
   *
   * @param deptCode       A {@code String} representing the department.
   *
   * @return               A {@code ResponseEntity} object containing an HTTP 200
   *                       response with an appropriate message or the proper status
   *                       code in tune with what has happened.
   */
  @PatchMapping(value = "/addMajorToDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> addMajorToDept(@RequestParam(DEPT_CODE) String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

        Department specifiedDept = departmentMapping.get(deptCode);
        specifiedDept.addMajor();
        return new ResponseEntity<>(specifiedDept, HttpStatus.OK);

      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to remove a student from the specified department.
   *
   * @param deptCode       A {@code String} representing the department.
   *
   * @return               A {@code ResponseEntity} object containing an HTTP 200
   *                       response with an appropriate message or the proper status
   *                       code in tune with what has happened.
   */
  @PatchMapping(value = "/removeMajorFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> removeMajorFromDept(@RequestParam(DEPT_CODE) String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

        Department specifiedDept = departmentMapping.get(deptCode);
        specifiedDept.decreaseMajor();
        return new ResponseEntity<>(specifiedDept, HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to drop a student from the specified course.
   *
   * @param deptCode       A {@code String} representing the department.
   *
   * @param courseCode     A {@code int} representing the course within the department.
   *
   * @return               A {@code ResponseEntity} object containing an HTTP 200
   *                       response with an appropriate message or the proper status
   *                       code in tune with what has happened.
   */
  @PatchMapping(value = "/dropStudentFromCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> dropStudent(
      @RequestParam(DEPT_CODE) String deptCode,
      @RequestParam(COURSE_CODE) int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        boolean isStudentDropped = requestedCourse.dropStudent();
        Map<String, String> response = new HashMap<>();
        if (isStudentDropped) {
          response.put("message", "Student has been dropped.");
          return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
          response.put("message", "Student has not been dropped.");
          return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Endpoint for updating the enrollment count for a course.
   * This method handles PATCH requests to update the enrollment 
   * count for a course identified by department code and course code. 
   * If the course exists, its enrollment count is updated to the provided count.
   *
   * @param deptCode       A {@code String} representing the department.
   *
   * @param courseCode     A {@code int} representing the course within the department.
   *
   * @param count          A {@code int} representing the enrollment count for the course
   *                       within the department.
   *
   * @return               A {@code ResponseEntity} object containing an HTTP 200
   *                       response with an appropriate message or the proper status
   *                       code in tune with what has happened.
   */
  @PatchMapping(value = "/setEnrollmentCount", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> setEnrollmentCount(
      @RequestParam(DEPT_CODE) String deptCode,
      @RequestParam(COURSE_CODE) int courseCode,
      @RequestParam("count") int count) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.setEnrolledStudentCount(count);
        return new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Endpoint for changing the time of a course.
   * This method handles PATCH requests to change the time of a course identified by
   * department code and course code.If the course exists, its time is updated to the provided time.
   *
   * @param deptCode                    the code of the department containing the course
   * @param courseCode                  the code of the course to change the time for
   * @param time                        the new time for the course
   *
   * @return                            a ResponseEntity with a success message if the operation is
   *                                    successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/changeCourseTime", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseTime(
      @RequestParam(DEPT_CODE) String deptCode,
      @RequestParam(COURSE_CODE) int courseCode,
      @RequestParam("time") String time) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.reassignTime(time);
        Map<String, Course> response = new HashMap<>();
        response.put("course", requestedCourse);
        return new ResponseEntity<>(response, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Endpoint for changing the instructor of a course.
   * This method handles PATCH requests to change the instructor of a course identified by
   * department code and course code. If the course exists, its instructor is updated to the
   * provided instructor.
   *
   * @param deptCode                  the code of the department containing the course
   * @param courseCode                the code of the course to change the instructor for
   * @param teacher                   the new instructor for the course
   *
   * @return                          a ResponseEntity with a success message if the operation is
   *                                  successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/changeCourseTeacher", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseTeacher(
      @RequestParam(DEPT_CODE) String deptCode,
      @RequestParam(COURSE_CODE) int courseCode,
      @RequestParam("teacher") String teacher) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.reassignInstructor(teacher);
        Map<String, Course> response = new HashMap<>();
        response.put("course", requestedCourse);
        return new ResponseEntity<>(response, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Endpoint for changing the location of a course.
   * This method handles PATCH requests to change the location of a course identified by
   * department code and course code. If the course exists, its location is updated to the
   * provided location.
   *
   * @param deptCode                  the code of the department containing the course
   * @param courseCode                the code of the course to change the location for
   * @param location                  the new location for the course
   *
   * @return                          a ResponseEntity with a success message if the operation is
   *                                  successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/changeCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseLocation(
      @RequestParam(DEPT_CODE) String deptCode,
      @RequestParam(COURSE_CODE) int courseCode,
      @RequestParam("location") String location) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.reassignLocation(location);
        Map<String, Course> response = new HashMap<>();
        response.put("course", requestedCourse);
        return new ResponseEntity<>(response, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private static final String DEPT_CODE = "deptCode";
  private static final String COURSE_CODE = "courseCode";


}