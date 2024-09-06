package dev.coms4156.project.individualproject;

import java.util.HashMap;
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
  public ResponseEntity<?> retrieveDepartment(@RequestParam(value = "deptCode") String deptCode) {
    try {
      HashMap<String, Department> departmentMapping;
      departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

      if (!departmentMapping.containsKey(deptCode.toUpperCase())) {
        return new ResponseEntity<>("Department Not Found", HttpStatus.OK);
      } else {
        return new ResponseEntity<>(departmentMapping.get(deptCode.toUpperCase()).toString(),
            HttpStatus.NOT_FOUND);
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
  public ResponseEntity<?> retrieveCourse(@RequestParam(value = "deptCode") String deptCode, 
      @RequestParam(value = "courseCode") int courseCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        HashMap<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        HashMap<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        if (!coursesMapping.containsKey(Integer.toString(courseCode))) {
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        } else {
          return new ResponseEntity<>(coursesMapping.get(Integer.toString(courseCode)).toString(),
              HttpStatus.FORBIDDEN);
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
  public ResponseEntity<?> isCourseFull(@RequestParam(value = "deptCode") String deptCode, 
      @RequestParam(value = "courseCode") int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        HashMap<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        HashMap<String, Course> coursesMapping;
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
  public ResponseEntity<?> getMajorCtFromDept(@RequestParam(value = "deptCode") String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        HashMap<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        return new ResponseEntity<>("There are: " + -departmentMapping.get(deptCode)
            .getNumberOfMajors() + " majors in the department", HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.FORBIDDEN);
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
  public ResponseEntity<?> identifyDeptChair(@RequestParam(value = "deptCode") String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        HashMap<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        return new ResponseEntity<>(departmentMapping.get(deptCode).getDepartmentChair() + " is "
            + "the department chair.", HttpStatus.OK);
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
  public ResponseEntity<?> findCourseLocation(@RequestParam(value = "deptCode") String deptCode, 
      @RequestParam(value = "courseCode") int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        HashMap<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        HashMap<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(requestedCourse.getCourseLocation() + " is where the course "
            + "is located.", HttpStatus.OK);
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
  public ResponseEntity<?> findCourseInstructor(@RequestParam(value = "deptCode") String deptCode, 
      @RequestParam(value = "courseCode") int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        HashMap<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        HashMap<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(requestedCourse.getInstructorName() + " is the instructor for"
            + " the course.", HttpStatus.OK);
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
  public ResponseEntity<?> findCourseTime(@RequestParam(value = "deptCode") String deptCode, 
      @RequestParam(value = "courseCode") int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        HashMap<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        HashMap<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>("The course meets at: " + "some time ",
            HttpStatus.OK);
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
  public ResponseEntity<?> addMajorToDept(@RequestParam(value = "deptCode") String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        HashMap<String, Department> departmentMapping;
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
   * @param deptCode       A {@code String} representing the department.
   *
   * @return               A {@code ResponseEntity} object containing an HTTP 200
   *                       response with an appropriate message or the proper status
   *                       code in tune with what has happened.
   */
  @PatchMapping(value = "/removeMajorFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> removeMajorFromDept(@RequestParam(value = "deptCode") String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        HashMap<String, Department> departmentMapping;
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
   * @param deptCode       A {@code String} representing the department.
   *
   * @param courseCode     A {@code int} representing the course within the department.
   *
   * @return               A {@code ResponseEntity} object containing an HTTP 200
   *                       response with an appropriate message or the proper status
   *                       code in tune with what has happened.
   */
  @PatchMapping(value = "/dropStudentFromCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> dropStudent(@RequestParam(value = "deptCode") String deptCode, 
      @RequestParam(value = "courseCode") int courseCode) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        HashMap<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        HashMap<String, Course> coursesMapping;
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
   * Attempts to set an enrollment count to the specified course.
   *
   * @param deptCode       A {@code String} representing the department.
   *
   * @param courseCode     A {@code int} representing the course within the department.
   * 
   * @param count          A {@code int} representing the enrollment count for the course.
   *
   * @return               A {@code ResponseEntity} object containing an HTTP 200
   *                       response with an appropriate message or the proper status
   *                       code in tune with what has happened.
   */
  @PatchMapping(value = "/setEnrollmentCount", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> setEnrollmentCount(@RequestParam(value = "deptCode") String deptCode, 
      @RequestParam(value = "courseCode") int courseCode, 
      @RequestParam(value = "count") int count) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        HashMap<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        HashMap<String, Course> coursesMapping;
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
   * department code and course code. If the course exists, its time is updated to the provided 
   * time.
   *
   * @param deptCode                    A {@code String} representing the department.
   * 
   * @param courseCode                  A {@code int} representing the course within the department.
   * 
   * @param time                        A {@code String} representing the new time for the course.
   *
   * @return                            a ResponseEntity with a success message if the operation is
   *                                    successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/changeCourseTime", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseTime(@RequestParam(value = "deptCode") String deptCode, 
      @RequestParam(value = "courseCode") int courseCode, 
      @RequestParam(value = "time") String time) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        HashMap<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        HashMap<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.reassignTime(time);
        return new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK);
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
   * @param deptCode                  A {@code String} representing the department.
   * 
   * @param courseCode                A {@code int} representing the course within the department.
   * 
   * @param teacher                   A {@code String} representing the new teacher for the course.
   *
   * @return                          a ResponseEntity with a success message if the operation is
   *                                  successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/changeCourseTeacher", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseTeacher(@RequestParam(value = "deptCode") String deptCode, 
      @RequestParam(value = "courseCode") int courseCode, 
      @RequestParam(value = "teacher") String teacher) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        HashMap<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        HashMap<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.reassignInstructor(teacher);
        return new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK);
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
   * @param deptCode                  A {@code String} representing the department.
   * 
   * @param courseCode                A {@code int} representing the course within the department.
   * 
   * @param location                  A {@code String} representing the new location for the course.
   *
   * @return                          a ResponseEntity with a success message if the operation is
   *                                  successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/changeCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseLocation(@RequestParam(value = "deptCode") String deptCode, 
      @RequestParam(value = "courseCode") int courseCode, 
      @RequestParam(value = "location") String location) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        HashMap<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        HashMap<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.reassignLocation(location);
        return new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK);
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