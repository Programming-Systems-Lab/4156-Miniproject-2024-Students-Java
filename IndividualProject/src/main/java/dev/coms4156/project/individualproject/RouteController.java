package dev.coms4156.project.individualproject;

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
    return """
           Welcome, in order to make an API call direct your browser or Postman to an endpoint 
           
            This can be done using the following format: 
           
            http:127.0.0.1:8080/endpoint?arg=value""";
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
  @GetMapping(path = "/retrieveDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveDepartment(@RequestParam String deptCode) {
    try {
      Map<String, Department> departmentMapping;
      departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

      if (!departmentMapping.containsKey(deptCode.toUpperCase(Locale.ROOT))) {
        return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
      } else {
        return new ResponseEntity<>(
            departmentMapping.get(deptCode.toUpperCase(Locale.ROOT)).toString(), 
                HttpStatus.OK
        );
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
      @RequestParam String deptCode, 
      @RequestParam int courseCode
  ) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        Department department = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT));
        if (department == null){
          return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
        }
        coursesMapping = department.getCourseSelection();
        if (!coursesMapping.containsKey(Integer.toString(courseCode))) {
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        } else {
          return new ResponseEntity<>(coursesMapping.get(Integer.toString(courseCode)).toString(),
              HttpStatus.OK);
        }

      }
      return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
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
      @RequestParam String deptCode, 
      @RequestParam int courseCode
  ) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT))
            .getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        if (requestedCourse == null){
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        }
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
  public ResponseEntity<?> getMajorCtFromDept(@RequestParam String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode.toUpperCase(Locale.ROOT))
          .getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Department department = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT));
        if (department == null){
          return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("There are: " + department.getNumberOfMajors() + " majors in the department", HttpStatus.OK
        );
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
  public ResponseEntity<?> identifyDeptChair(@RequestParam String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode.toUpperCase(Locale.ROOT))
          .getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Department department = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT));
        if (department == null){
          return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department.getDepartmentChair() + " is "
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
   * @param courseCode A {@code int} representing the course the uLuca Carloniser wishes
   *                   to find information about.
   *
   * @return           A {@code ResponseEntity} object containing either the location of the
   *                   course and an HTTP 200 response or, an appropriate message indicating the
   *                   proper response.
   */
  @GetMapping(value = "/findCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseLocation(
      @RequestParam String deptCode, 
      @RequestParam int courseCode
  ) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        
        Map<String, Course> coursesMapping;
        Department department = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT));
        if (department == null){
          return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
        }
        coursesMapping = department.getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        if (requestedCourse == null){
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        }
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
  public ResponseEntity<?> findCourseInstructor(
      @RequestParam String deptCode, 
      @RequestParam int courseCode
  ) {
    try {
      ResponseEntity<?> courseResponse = retrieveCourse(deptCode, courseCode);
      if (courseResponse.getStatusCode() == HttpStatus.OK) {
        Map<String, Department> departmentMapping = IndividualProjectApplication.myFileDatabase
            .getDepartmentMapping();

        Department department = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT));
        if (department == null){
          return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
        }
        Map<String, Course> coursesMapping = department.getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        if (requestedCourse == null){
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(requestedCourse.getInstructorName() 
            + " is the instructor for the course.", HttpStatus.OK);
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
      @RequestParam String deptCode, 
      @RequestParam int courseCode
  ) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

        Department department = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT));
        if (department == null){
          return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
        }

        Map<String, Course> coursesMapping = department.getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        if (requestedCourse == null){
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("The course meets at: " + requestedCourse.getCourseTimeSlot(),
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
   *
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to find information about.
   *
   * @return           A {@code ResponseEntity} object containing either the details of the
   *                   course timeslot and an HTTP 200 response or, an appropriate message
   *                   indicating the proper response.
   */
  @GetMapping(value = "/retrieveCourses", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveCourses(
      @RequestParam int courseCode
  ) {
      Map<String, Department> departmentMapping;
      departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
      StringBuilder result = new StringBuilder();
      result.append("Course Code: ").append(courseCode).append("\n");
      for (Map.Entry<String, Department> entry : departmentMapping.entrySet()) {
          Boolean foundCourse = false;
          String deptCode = entry.getKey();
          Department value = entry.getValue();
          Map<String, Course> courseMapping = value.getCourseSelection();
          for (Map.Entry<String, Course> course : courseMapping.entrySet()) {
              String courseName = course.getKey();
              if (courseName.equals(Integer.toString(courseCode))){
                if (foundCourse == false){
                  foundCourse = true;
                  result.append("Department: ").append(deptCode);
                }
                result.append(course.getValue().toString());
              }
          }    
        }
        if (result.isEmpty()){
          return new ResponseEntity<>("No courses found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

  @PatchMapping(value = "/enrollStudentInCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> addMajorToDept(
    @RequestParam String deptCode,       
    @RequestParam int courseCode
  ) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode.toUpperCase(Locale.ROOT))
          .getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> courseMapping = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT))
            .getCourseSelection();

        Course requestedCourse = courseMapping.get(Integer.toString(courseCode));
        if (requestedCourse == null){
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        }
        StringBuilder result = new StringBuilder();
        if (requestedCourse.enrollStudent()){
          result.append("Student was successfully added to the following course: ").append("\n");
          result.append(requestedCourse.toString());
        } else {
          result.append("The following course is full: ").append("\n");
          result.append(requestedCourse.toString());
          result.append("The following courses are available as alternatives in this department: ");
          for (Map.Entry<String, Course> entry : courseMapping.entrySet()){
            if (!entry.getValue().isCourseFull()){
              result.append(entry.getValue().toString());
            } 
          }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }
    
  
  /**
   * Attempts to add a major to the specified department.
   *
   * @param deptCode       A {@code String} representing the department.
   *
   * @return               A {@code ResponseEntity} object containing an HTTP 200
   *                       response with an appropriate message or the proper status
   *                       code in tune with what has happened.
   */
  @PatchMapping(value = "/addMajorToDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> addMajorToDept(@RequestParam String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode.toUpperCase(Locale.ROOT))
          .getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Department department = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT));
        if (department == null){
          return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
        }
        department.addMajor();
        return new ResponseEntity<>("Attribute was updated successfully", HttpStatus.OK);
      }
      return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to remove a major from the specified department.
   *
   * @param deptCode       A {@code String} representing the department.
   *
   * @return               A {@code ResponseEntity} object containing an HTTP 200
   *                       response with an appropriate message or the proper status
   *                       code in tune with what has happened.
   */
  @PatchMapping(value = "/removeMajorFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> removeMajorFromDept(@RequestParam String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

        Department department = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT));
        if (department == null){
          return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
        }
        department.dropMajor();
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
  public ResponseEntity<?> dropStudent(
      @RequestParam String deptCode, 
      @RequestParam int courseCode
  ) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping =IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        
        Department department = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT));
        if (department == null){
          return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
        }
        
        Map<String, Course> coursesMapping = department.getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        if (requestedCourse == null){
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        }
        boolean isStudentDropped = requestedCourse.dropStudent();

        if (isStudentDropped) {
          return new ResponseEntity<>("Student has been dropped.", HttpStatus.OK);
        } else {
          return new ResponseEntity<>("There was an internal error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Endpoint for changing the enrollment count of a course.
   * This method handles PATCH requests to change the enrollment count of a course identified by
   * department code and course code.If the course exists, its time is updated to the provided time.
   *
   * @param deptCode                    the code of the department containing the course
   * @param courseCode                  the code of the course to change the time for
   * @param count                       the new count for the course
   *
   * @return                            a ResponseEntity with a success message if the operation is
   *                                    successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/setEnrollmentCount", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> setEnrollmentCount(
      @RequestParam String deptCode, 
      @RequestParam int courseCode, 
      @RequestParam int count
  ) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

        Department department = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT));
        if (department == null){
          return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
        }

        Map<String, Course> coursesMapping = department.getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        if (requestedCourse == null){
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        }
        int oldEnrollment = requestedCourse.getEnrollmentCount();
        requestedCourse.setEnrolledStudentCount(count);
        if (oldEnrollment == (requestedCourse.getEnrollmentCount()) & oldEnrollment != count){
          return new ResponseEntity<>("Could not update enrollment count, try again later.", HttpStatus.OK);
        }
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
      @RequestParam String deptCode, 
      @RequestParam int courseCode, 
      @RequestParam String time
  ) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Department department = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT));
        if (department == null){
          return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
        }
        Map<String, Course> coursesMapping = department.getCourseSelection();
        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        if (requestedCourse == null){
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        }
        String old_time = requestedCourse.getCourseTimeSlot();
        requestedCourse.reassignTime(time);
        String new_time = requestedCourse.getCourseTimeSlot();
        if (new_time.equals(old_time)){
          return new ResponseEntity<>("Could not update time, try again later.", HttpStatus.OK);
        }
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
   * @param deptCode                  the code of the department containing the course
   * @param courseCode                the code of the course to change the instructor for
   * @param teacher                   the new instructor for the course
   *
   * @return                          a ResponseEntity with a success message if the operation is
   *                                  successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/changeCourseTeacher", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseTeacher(
      @RequestParam String deptCode, 
      @RequestParam int courseCode, 
      @RequestParam String teacher
  ) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Department department = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT));
        if (department == null){
          return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
        }
        Map<String, Course> coursesMapping = department.getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        if (requestedCourse == null){
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        }
        
        String prevInstructor = requestedCourse.getInstructorName();
        requestedCourse.reassignInstructor(teacher);
        String newInstructor = requestedCourse.getInstructorName();

        if (prevInstructor.equals(newInstructor) & !prevInstructor.equals(teacher)){
          return new ResponseEntity<>("Could not update attribute, please try again later.", HttpStatus.OK);
        }
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
   * department code and course code.If the course exists, its time location 
   * updated to the provided time.
   *
   * @param deptCode                    the code of the department containing the course
   * @param courseCode                  the code of the course to change the time for
   * @param location                    the new location for the course
   *
   * @return                            a ResponseEntity with a success message if the operation is
   *                                    successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/changeCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseLocation(
      @RequestParam String deptCode, 
      @RequestParam int courseCode, 
      @RequestParam String location
  ) {
    try {
      boolean doesCourseExists;
      doesCourseExists = retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;

      if (doesCourseExists) {
        Map<String, Department> departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Department department = departmentMapping.get(deptCode.toUpperCase(Locale.ROOT));
        if (department == null){
          return new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND);
        }
        Map<String, Course> coursesMapping = department.getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        if (requestedCourse == null){
          return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
        }
        String prevLocation = requestedCourse.getCourseLocation();
        requestedCourse.reassignLocation(location);
        String newLocation = requestedCourse.getCourseLocation();
        if (prevLocation.equals(newLocation) & !newLocation.equals(location)){
          return new ResponseEntity<>("Could not update attribute, please try again later.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Attribute was updated successfully.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>(e.toString(), HttpStatus.OK);
  }


}