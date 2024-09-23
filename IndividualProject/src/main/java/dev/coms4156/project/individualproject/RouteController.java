package dev.coms4156.project.individualproject;

import java.util.ArrayList;
import java.util.Arrays;
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
 * This class contains all the API routes for the system
 * for us.
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
    return """
          Welcome, in order to make an API call direct your browser or Postman to an endpoint\s

           This can be done using the following format:\s

           http:127.0.0.1:8080/endpoint?arg=value""";
  }

  /**
   * Returns the details of the specified department.
   *
   * @param deptCode A {@code String} representing the department the user wishes
   *                 to retrieve.
   *
   * @return A {@code ResponseEntity} object containing either the details of the Department and
   *       an HTTP 200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/retrieveDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveDepartment(@RequestParam("deptCode") String deptCode) {
    try {
      Map<String, Department> departmentMapping;
      departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
      boolean doesDepartmentExists = departmentMapping.containsKey(deptCode);
      if (doesDepartmentExists) {
        return new ResponseEntity<>(departmentMapping.get(
              deptCode.toUpperCase(Locale.ROOT)).toString(),
              HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
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
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to retrieve.
   *
   * @return A {@code ResponseEntity} object containing either the details of the
   *       course and an HTTP 200 response or, an appropriate message indicating the
   *       proper response.
   */
  @GetMapping(value = "/retrieveCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveCourse(@RequestParam("deptCode") String deptCode,
                                          @RequestParam("courseCode") int courseCode) {
    try {
      Map<String, Department> departmentMapping;
      departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
      boolean doesDepartmentExists = departmentMapping.containsKey(
            deptCode.toUpperCase(Locale.ROOT));
      if (doesDepartmentExists) {
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        if (coursesMapping.containsKey(Integer.toString(courseCode))) {
          return new ResponseEntity<>(coursesMapping.get(Integer.toString(courseCode)).toString(),
                HttpStatus.OK);
        } else {
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        }
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }


  /**
   * Displays the details of the requested course to the user or displays the proper error
   * message in response to the request.
   *
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to retrieve.
   *
   * @return A {@code ResponseEntity} object containing either the details of the
   *       course and an HTTP 200 response or, an appropriate message indicating the
   *       proper response.
   */
  @GetMapping(value = "/retrieveCourses", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveCourses(@RequestParam("courseCode") int courseCode) {
    try {
      Map<String, Department> departmentMapping;
      departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
      Department[] departments = departmentMapping.values().toArray(new Department[0]);
      ArrayList<String> res = new ArrayList<>();
      for (Department dept : departments) {
        if (dept.getCourseSelection().containsKey(Integer.toString(courseCode))) {
          res.add(dept.getCourseSelection().get(Integer.toString(courseCode)).toString()
                          + "; Code: " + courseCode + "; Department: " + dept.getCode());
        }
      }

      if (!res.isEmpty()) {
        return new ResponseEntity<>(Arrays.asList(res.toArray()), HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }


  /**
   * Displays whether the course has at minimum reached its enrollmentCapacity.
   *
   * @param deptCode   A {@code String} representing the department the user wishes
   *                   to find the course in.
   * @param courseCode A {@code int} the course the user requests.
   *
   * @return A {@code ResponseEntity} object containing either the requested information
   *       and an HTTP 200 response or, a message indicating a proper response.
   */
  @GetMapping(value = "/isCourseFull", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> isCourseFull(@RequestParam("deptCode") String deptCode,
                                        @RequestParam("courseCode") int courseCode) {
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
   * @param deptCode A {@code String} representing the department the user wishes
   *                 to find number of majors for.
   *
   * @return A {@code ResponseEntity} object containing either number of majors for the
   *       specified department and an HTTP 200 response or, an appropriate message
   *       indicating the proper response.
   */
  @GetMapping(value = "/getMajorCountFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getMajorCtFromDept(@RequestParam("deptCode") String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        String body = "There are: " + departmentMapping.get(deptCode)
                                            .getNumberOfMajors() + " majors in the department";
        return new ResponseEntity<>(body, HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the department chair for the specified department.
   *
   * @param deptCode A {@code String} representing the department the user wishes
   *                 to find the department chair of.
   *
   * @return A {@code ResponseEntity} object containing either department chair of the
   *       specified department and an HTTP 200 response or, an appropriate message
   *       indicating the proper response.
   */
  @GetMapping(value = "/idDeptChair", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> identifyDeptChair(@RequestParam("deptCode") String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        String body = departmentMapping.get(deptCode)
                            .getDepartmentChair() + " is " + "the department chair.";
        return new ResponseEntity<>(body, HttpStatus.OK);
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
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to find information about.
   *
   * @return A {@code ResponseEntity} object containing either the location of the
   *       course and an HTTP 200 response or, an appropriate message indicating the
   *       proper response.
   */
  @GetMapping(value = "/findCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseLocation(@RequestParam("deptCode") String deptCode,
                                              @RequestParam("courseCode") int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(
              requestedCourse.getCourseLocation() + " is where the course " + "is located.",
              HttpStatus.OK);
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
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to find information about.
   *
   * @return A {@code ResponseEntity} object containing either the course instructor and
   *       an HTTP 200 response or, a message indicating the proper response
   */
  @GetMapping(value = "/findCourseInstructor", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseInstructor(@RequestParam("deptCode") String deptCode,
                                                @RequestParam("courseCode")
                                                int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(
              requestedCourse.getInstructorName() + " is the instructor for" + " the course.",
              HttpStatus.OK);
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
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to find information about.
   *
   * @return A {@code ResponseEntity} object containing either the details of the
   *       course timeslot and an HTTP 200 response or, an appropriate message
   *       indicating the proper response.
   */
  @GetMapping(value = "/findCourseTime", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseTime(@RequestParam("deptCode") String deptCode,
                                          @RequestParam("courseCode") int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>("The course meets at: "
                                          + requestedCourse.getCourseTimeSlot(), HttpStatus.OK);
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
   * @param deptCode A {@code String} representing the department.
   *
   * @return A {@code ResponseEntity} object containing an HTTP 200
   *       response with an appropriate message or the proper status
   *       code in tune with what has happened.
   */
  @PatchMapping(value = "/addMajorToDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> addMajorToDept(@RequestParam("deptCode") String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

        Department specifiedDept = departmentMapping.get(deptCode);
        specifiedDept.addPersonToMajor();
        return new ResponseEntity<>("Attribute was updated successfully", HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to remove a student from the specified department.
   *
   * @param deptCode A {@code String} representing the department.
   *
   * @return A {@code ResponseEntity} object containing an HTTP 200
   *       response with an appropriate message or the proper status
   *       code in tune with what has happened.
   */
  @PatchMapping(value = "/removeMajorFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> removeMajorFromDept(@RequestParam("deptCode") String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

        Department specifiedDept = departmentMapping.get(deptCode);
        specifiedDept.dropPersonFromMajor();
        return new ResponseEntity<>("Attribute was updated or is at minimum", HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to drop a student from the specified course.
   *
   * @param deptCode   A {@code String} representing the department.
   * @param courseCode A {@code int} representing the course within the department.
   *
   * @return A {@code ResponseEntity} object containing an HTTP 200
   *       response with an appropriate message or the proper status
   *       code in tune with what has happened.
   */
  @PatchMapping(value = "/dropStudentFromCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> dropStudent(@RequestParam("deptCode") String deptCode,
                                       @RequestParam("courseCode") int courseCode) {
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

        if (isStudentDropped) {
          return new ResponseEntity<>("Student has been dropped.", HttpStatus.OK);
        } else {
          return new ResponseEntity<>("Student has not been dropped.", HttpStatus.BAD_REQUEST);
        }
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to enroll a student in the requested course.
   *
   * @param deptCode   A {@code String} representing the department.
   * @param courseCode A {@code int} representing the course within the department.
   *
   * @return A {@code ResponseEntity} object containing an HTTP 200
   *       response with an appropriate message or the proper status
   *       code in tune with what has happened.
   */
  @PatchMapping(value = "/enrollStudentInCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> enrollStudent(@RequestParam("deptCode") String deptCode,
                                       @RequestParam("courseCode") int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        boolean isStudentEnrolled = requestedCourse.enrollStudent();

        if (isStudentEnrolled) {
          return new ResponseEntity<>("Student has been enrolled.", HttpStatus.OK);
        } else {
          return new ResponseEntity<>("Student has not been enrolled.", HttpStatus.BAD_REQUEST);
        }
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to update the enrollment count for the requested course.
   *
   * @param deptCode   A {@code String} representing the department.
   * @param courseCode A {@code int} representing the course within the department.
   * @param count      A {@code int} representing the updated count.
   *
   * @return A {@code ResponseEntity} object containing an HTTP 200
   *       response with an appropriate message or the proper status
   *       code in tune with what has happened.
   */
  @PatchMapping(value = "/updateEnrollmentCount", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateEnrollmentCount(@RequestParam("deptCode") String deptCode,
                                              @RequestParam("courseCode") int courseCode,
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
        return new ResponseEntity<>(requestedCourse.toString(), HttpStatus.OK);
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
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the time for
   * @param time       the new time for the course
   *
   * @return a ResponseEntity with a success message if the operation is
   *       successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/updateCourseTime", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateCourseTime(@RequestParam("deptCode") String deptCode,
                                            @RequestParam("courseCode") int courseCode,
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
        return new ResponseEntity<>(requestedCourse.toString(), HttpStatus.OK);
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
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the instructor for
   * @param teacher    the new instructor for the course
   *
   * @return a ResponseEntity with a success message if the operation is
   *       successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/updateCourseTeacher", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateCourseTeacher(@RequestParam("deptCode") String deptCode,
                                               @RequestParam("courseCode") int courseCode,
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
        return new ResponseEntity<>(requestedCourse.toString(), HttpStatus.OK);
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
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the location for
   * @param location   the new location for the course
   *                   successful, or an error message if the course is not found
   *
   * @return a ResponseEntity with a success message if the operation is
   */
  @PatchMapping(value = "/updateCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateCourseLocation(@RequestParam("deptCode") String deptCode,
                                                @RequestParam("courseCode") int courseCode,
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
        return new ResponseEntity<>(requestedCourse.toString(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.OK);
  }


}
