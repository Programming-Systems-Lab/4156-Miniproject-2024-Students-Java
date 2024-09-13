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
  @GetMapping({ "/", "/index", "/home" })
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
   * @return A {@code ResponseEntity} object containing either the details of the
   *         Department and
   *         an HTTP 200 response or, an appropriate messageELEN=ELEN 3082:
   *         Instructor: Kenneth Shepard; Location: 1205 MUDD; Time: 4:10-6:40
   *         ELEN 1201:
   *         Instructor: David G Vallancourt; Location: 301 PUP; Time: 4:10-5:25
   *         ELEN 3401:
   *         Instructor: Keren Bergman; Location: 829 MUDD; Time: 2:40-3:55
   *         ELEN 4510:
   *         Instructor: Mohamed Kamaludeen; Location: 903 SSW; Time: 7:00-9:30
   *         ELEN 3331:
   *         Instructor: David G Vallancourt; Location: 203 MATH; Time:
   *         11:40-12:55
   *         ELEN 4830:
   *         Instructor: Christine P Hendon; Location: 633 MUDD; Time: 10:10-12:40
   *         ELEN 3701:
   *         Instructor: Irving Kalet; Location: 333 URIS; Time: 2:40-3:55
   *         ELEN 4702:
   *         Instructor: Alexei Ashikhmin; Location: 332 URIS; Time: 7:00-9:30
   *         , CHEM=CHEM 1403:
   *         Instructor: Ruben M Savizky; Location: 309 HAV; Time: 6:10-7:25
   *         CHEM 3080:
   *         Instructor: Milan Delor; Location: 209 HAV; Time: 10:10-11:25
   *         CHEM 1500:
   *         Instructor: Joseph C Ulichny; Location: 302 HAV; Time: 6:10-9:50
   *         CHEM 2444:
   *         Instructor: Christopher Eckdahl; Location: 309 HAV; Time: 11:40-12:55
   *         CHEM 4102:
   *         Instructor: Dalibor Sames; Location: 320 HAV; Time: 10:10-11:25
   *         CHEM 2045:
   *         Instructor: Luis M Campos; Location: 209 HAV; Time: 1:10-2:25
   *         CHEM 2494:
   *         Instructor: Talha Siddiqui; Location: 202 HAV; Time: 1:10-5:00
   *         CHEM 4071:
   *         Instructor: Jonathan S Owen; Location: 320 HAV; Time: 8:40-9:55
   *         , PHYS=PHYS 4040:
   *         Instructor: James C Hill; Location: 214 PUP; Time: 4:10-5:25
   *         PHYS 1602:
   *         Instructor: Kerstin M Perez; Location: 428 PUP; Time: 10:10-11:25
   *         PHYS 3008:
   *         Instructor: William A Zajc; Location: 329 PUP; Time: 10:10-11:25
   *         PHYS 1201:
   *         Instructor: Eric Raymer; Location: 428 PUP; Time: 2:40-3:55
   *         PHYS 4003:
   *         Instructor: Frederik Denef; Location: 214 PUP; Time: 4:10-5:25
   *         PHYS 1001:
   *         Instructor: Szabolcs Marka; Location: 301 PUP; Time: 2:40-3:55
   *         PHYS 4018:
   *         Instructor: James W McIver; Location: 307 PUP; Time: 2:40-3:55
   *         PHYS 2802:
   *         Instructor: Yury Levin; Location: 329 PUP; Time: 10:10-12:00
   *         , PSYC=PSYC 4493:
   *         Instructor: Jennifer Blaze; Location: 200 SCH; Time: 2:10-4:00
   *         PSYC 1610:
   *         Instructor: Christopher Baldassano; Location: 200 SCH; Time:
   *         10:10-11:25
   *         PSYC 2235:
   *         Instructor: Katherine T Fox-Glassman; Location: 501 SCH; Time:
   *         11:40-12:55
   *         PSYC 2620:
   *         Instructor: Jeffrey M Cohen; Location: 303 URIS; Time: 1:10-3:40
   *         PSYC 3445:
   *         Instructor: Mariam Aly; Location: 405 SCH; Time: 2:10-4:00
   *         PSYC 1001:
   *         Instructor: Patricia G Lindemann; Location: 501 SCH; Time: 1:10-2:25
   *         PSYC 3212:
   *         Instructor: Mayron Piccolo; Location: 200 SCH; Time: 2:10-4:00
   *         PSYC 4236:
   *         Instructor: Trenton Jerde; Location: 405 SCH; Time: 6:10-8:00
   *         , COMS=COMS 3827:
   *         Instructor: Daniel Rubenstein; Location: 207 Math; Time: 10:10-11:25
   *         COMS 1004:
   *         Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55
   *         COMS 3203:
   *         Instructor: Ansaf Salleb-Aouissi; Location: 301 URIS; Time:
   *         10:10-11:25
   *         COMS 4156:
   *         Instructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25
   *         COMS 3157:
   *         Instructor: Jae Lee; Location: 417 IAB; Time: 4:10-5:25
   *         COMS 3134:
   *         Instructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25
   *         COMS 3251:
   *         Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40
   *         COMS 3261:
   *         Instructor: Josh Alman; Location: 417 IAB; Time: 2:40-3:55
   *         , ECON=ECON 1105:
   *         Instructor: Waseem Noor; Location: 309 HAV; Time: 2:40-3:55
   *         ECON 2257:
   *         Instructor: Tamrat Gashaw; Location: 428 PUP; Time: 10:10-11:25
   *         ECON 3412:
   *         Instructor: Thomas Piskula; Location: 702 HAM; Time: 11:40-12:55
   *         ECON 3213:
   *         Instructor: Miles Leahey; Location: 702 HAM; Time: 4:10-5:25
   *         ECON 3211:
   *         Instructor: Murat Yilmaz; Location: 310 FAY; Time: 4:10-5:25
   *         ECON 4840:
   *         Instructor: Mark Dean; Location: 142 URIS; Time: 2:40-3:55
   *         ECON 4710:
   *         Instructor: Matthieu Gomez; Location: 517 HAM; Time: 8:40-9:55
   *         ECON 4415:
   *         Instructor: Evan D Sadler; Location: 309 HAV; Time: 10:10-11:25
   *         , IEOR=IEOR 3404:
   *         Instructor: Christopher J Dolan; Location: 303 MUDD; Time:
   *         10:10-11:25
   *         IEOR 2500:
   *         Instructor: Uday Menon; Location: 627 MUDD; Time: 11:40-12:55
   *         IEOR 4540:
   *         Instructor: Krzysztof M Choromanski; Location: 633 MUDD; Time:
   *         7:10-9:40
   *         IEOR 4102:
   *         Instructor: Antonius B Dieker; Location: 209 HAM; Time: 10:10-11:25
   *         IEOR 4511:
   *         Instructor: Michael Robbins; Location: 633 MUDD; Time: 9:00-11:30
   *         IEOR 4106:
   *         Instructor: Kaizheng Wang; Location: 501 NWC; Time: 10:10-11:25
   *         IEOR 4405:
   *         Instructor: Yuri Faenza; Location: 517 HAV; Time: 11:40-12:55
   *         IEOR 3658:
   *         Instructor: Daniel Lacker; Location: 310 FAY; Time: 10:10-11:25
   *         }> but was: java.util.HashMap@1e0bae92<{ELEN=ELEN 3082:
   *         Instructor: Kenneth Shepard; Location: 1205 MUDD; Time: 4:10-6:40
   *         ELEN 1201:
   *         Instructor: David G Vallancourt; Location: 301 PUP; Time: 4:10-5:25
   *         ELEN 3401:
   *         Instructor: Keren Bergman; Location: 829 MUDD; Time: 2:40-3:55
   *         ELEN 4510:
   *         Instructor: Mohamed Kamaludeen; Location: 903 SSW; Time: 7:00-9:30
   *         ELEN 3331:
   *         Instructor: David G Vallancourt; Location: 203 MATH; Time:
   *         11:40-12:55
   *         ELEN 4830:
   *         Instructor: Christine P Hendon; Location: 633 MUDD; Time: 10:10-12:40
   *         ELEN 3701:
   *         Instructor: Irving Kalet; Location: 333 URIS; Time: 2:40-3:55
   *         ELEN 4702:
   *         Instructor: Alexei Ashikhmin; Location: 332 URIS; Time: 7:00-9:30
   *         , CHEM=CHEM 1403:
   *         Instructor: Ruben M Savizky; Location: 309 HAV; Time: 6:10-7:25
   *         CHEM 3080:
   *         Instructor: Milan Delor; Location: 209 HAV; Time: 10:10-11:25
   *         CHEM 1500:
   *         Instructor: Joseph C Ulichny; Location: 302 HAV; Time: 6:10-9:50
   *         CHEM 2444:
   *         Instructor: Christopher Eckdahl; Location: 309 HAV; Time: 11:40-12:55
   *         CHEM 4102:
   *         Instructor: Dalibor Sames; Location: 320 HAV; Time: 10:10-11:25
   *         CHEM 2045:
   *         Instructor: Luis M Campos; Location: 209 HAV; Time: 1:10-2:25
   *         CHEM 2494:
   *         Instructor: Talha Siddiqui; Location: 202 HAV; Time: 1:10-5:00
   *         CHEM 4071:
   *         Instructor: Jonathan S Owen; Location: 320 HAV; Time: 8:40-9:55
   *         , PHYS=PHYS 4040:
   *         Instructor: James C Hill; Location: 214 PUP; Time: 4:10-5:25
   *         PHYS 1602:
   *         Instructor: Kerstin M Perez; Location: 428 PUP; Time: 10:10-11:25
   *         PHYS 3008:
   *         Instructor: William A Zajc; Location: 329 PUP; Time: 10:10-11:25
   *         PHYS 1201:
   *         Instructor: Eric Raymer; Location: 428 PUP; Time: 2:40-3:55
   *         PHYS 4003:
   *         Instructor: Frederik Denef; Location: 214 PUP; Time: 4:10-5:25
   *         PHYS 1001:
   *         Instructor: Szabolcs Marka; Location: 301 PUP; Time: 2:40-3:55
   *         PHYS 4018:
   *         Instructor: James W McIver; Location: 307 PUP; Time: 2:40-3:55
   *         PHYS 2802:
   *         Instructor: Yury Levin; Location: 329 PUP; Time: 10:10-12:00
   *         , PSYC=PSYC 4493:
   *         Instructor: Jennifer Blaze; Location: 200 SCH; Time: 2:10-4:00
   *         PSYC 1610:
   *         Instructor: Christopher Baldassano; Location: 200 SCH; Time:
   *         10:10-11:25
   *         PSYC 2235:
   *         Instructor: Katherine T Fox-Glassman; Location: 501 SCH; Time:
   *         11:40-12:55
   *         PSYC 2620:
   *         Instructor: Jeffrey M Cohen; Location: 303 URIS; Time: 1:10-3:40
   *         PSYC 3445:
   *         Instructor: Mariam Aly; Location: 405 SCH; Time: 2:10-4:00
   *         PSYC 1001:
   *         Instructor: Patricia G Lindemann; Location: 501 SCH; Time: 1:10-2:25
   *         PSYC 3212:
   *         Instructor: Mayron Piccolo; Location: 200 SCH; Time: 2:10-4:00
   *         PSYC 4236:
   *         Instructor: Trenton Jerde; Location: 405 SCH; Time: 6:10-8:00
   *         , COMS=COMS 3827:
   *         Instructor: Daniel Rubenstein; Location: 207 Math; Time: 10:10-11:25
   *         COMS 1004:
   *         Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55
   *         COMS 3203:
   *         Instructor: Ansaf Salleb-Aouissi; Location: 301 URIS; Time:
   *         10:10-11:25
   *         COMS 4156:
   *         Instructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25
   *         COMS 3157:
   *         Instructor: Jae Lee; Location: 417 IAB; Time: 4:10-5:25
   *         COMS 3134:
   *         Instructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25
   *         COMS 3251:
   *         Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40
   *         COMS 3261:
   *         Instructor: Josh Alman; Location: 417 IAB; Time: 2:40-3:55
   *         , ECON=ECON 1105:
   *         Instructor: Waseem Noor; Location: 309 HAV; Time: 2:40-3:55
   *         ECON 2257:
   *         Instructor: Tamrat Gashaw; Location: 428 PUP; Time: 10:10-11:25
   *         ECON 3412:
   *         Instructor: Thomas Piskula; Location: 702 HAM; Time: 11:40-12:55
   *         ECON 3213:
   *         Instructor: Miles Leahey; Location: 702 HAM; Time: 4:10-5:25
   *         ECON 3211:
   *         Instructor: Murat Yilmaz; Location: 310 FAY; Time: 4:10-5:25
   *         ECON 4840:
   *         Instructor: Mark Dean; Location: 142 URIS; Time: 2:40-3:55
   *         ECON 4710:
   *         Instructor: Matthieu Gomez; Location: 517 HAM; Time: 8:40-9:55
   *         ECON 4415:
   *         Instructor: Evan D Sadler; Location: 309 HAV; Time: 10:10-11:25
   *         , IEOR=IEOR 3404:
   *         Instructor: Christopher J Dolan; Location: 303 MUDD; Time:
   *         10:10-11:25
   *         IEOR 2500:
   *         Instructor: Uday Menon; Location: 627 MUDD; Time: 11:40-12:55
   *         IEOR 4540:
   *         Instructor: Krzysztof M Choromanski; Location: 633 MUDD; Time:
   *         7:10-9:40
   *         IEOR 4102:
   *         Instructor: Antonius B Dieker; Location: 209 HAM; Time: 10:10-11:25
   *         IEOR 4511:
   *         Instructor: Michael Robbins; Location: 633 MUDD; Time: 9:00-11:30
   *         IEOR 4106:
   *         Instructor: Kaizheng Wang; Location: 501 NWC; Time: 10:10-11:25
   *         IEOR 4405:
   *         Instructor: Yuri Faenza; Location: 517 HAV; Time: 11:40-12:55
   *         IEOR 3658:
   *         Instructor: Daniel Lacker; Location: 310 FAY; Time: 10:10-11:25
   *         indicating the proper
   *         response.
   */
  @GetMapping(value = "/retrieveDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveDepartment(@RequestParam(value = "deptCode") String deptCode) {
    try {
      HashMap<String, Department> departmentMapping;
      departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

      if (!departmentMapping.containsKey(deptCode.toUpperCase())) {
        return new ResponseEntity<>(departmentMapping.toString(), HttpStatus.NOT_FOUND);
      } else {
        return new ResponseEntity<>(departmentMapping.get(deptCode.toUpperCase()).toString(),
            HttpStatus.OK);
      }

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the details of the requested course to the user or displays the
   * proper error message in response to the request.
   *
   * @param deptCode   A {@code String} representing the department the user
   *                   wishes
   *                   to find the course in.
   *
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to retrieve.
   *
   * @return A {@code ResponseEntity} object containing either the details of the
   *         course and an HTTP 200 response or, an appropriate message indicating
   *         the
   *         proper response.
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
   * @param deptCode   A {@code String} representing the department the user
   *                   wishes
   *                   to find the course in.
   *
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to retrieve.
   *
   * @return A {@code ResponseEntity} object containing either the requested
   *         information
   *         and an HTTP 200 response or, an appropriate message indicating the
   *         proper
   *         response.
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
   * @param deptCode A {@code String} representing the department the user wishes
   *                 to find number of majors for.
   *
   * @return A {@code ResponseEntity} object containing either number of majors
   *         for the
   *         specified department and an HTTP 200 response or, an appropriate
   *         message
   *         indicating the proper response.
   */
  @GetMapping(value = "/getMajorCountFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getMajorCtFromDept(@RequestParam(value = "deptCode") String deptCode) {
    try {
      boolean doesDepartmentExists = retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        HashMap<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        return new ResponseEntity<>("There are: " + departmentMapping.get(deptCode)
            .getNumberOfMajors() + " majors in the department", HttpStatus.OK);
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
   * @return A {@code ResponseEntity} object containing either department chair of
   *         the
   *         specified department and an HTTP 200 response or, an appropriate
   *         message
   *         indicating the proper response.
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
   * @param deptCode   A {@code String} representing the department the user
   *                   wishes
   *                   to find the course in.
   *
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to find information about.
   *
   * @return A {@code ResponseEntity} object containing either the location of the
   *         course and an HTTP 200 response or, an appropriate message indicating
   *         the
   *         proper response.
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
   * @param deptCode   A {@code String} representing the department the user
   *                   wishes
   *                   to find the course in.
   *
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to find information about.
   *
   * @return A {@code ResponseEntity} object containing either the course
   *         instructor and
   *         an HTTP 200 response or, an appropriate message indicating the proper
   *         response.
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
   * @param deptCode   A {@code String} representing the department the user
   *                   wishes
   *                   to find the course in.
   *
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to find information about.
   *
   * @return A {@code ResponseEntity} object containing either the details of the
   *         course timeslot and an HTTP 200 response or, an appropriate message
   *         indicating the proper response.
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
   * Attempts to add a student to the specified department.
   *
   * @param deptCode A {@code String} representing the department.
   *
   * @return A {@code ResponseEntity} object containing an HTTP 200
   *         response with an appropriate message or the proper status
   *         code in tune with what has happened.
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
   * @param deptCode A {@code String} representing the department.
   *
   * @return A {@code ResponseEntity} object containing an HTTP 200
   *         response with an appropriate message or the proper status
   *         code in tune with what has happened.
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
   * @param deptCode   A {@code String} representing the department.
   *
   * @param courseCode A {@code int} representing the course within the
   *                   department.
   *
   * @return A {@code ResponseEntity} object containing an HTTP 200
   *         response with an appropriate message or the proper status
   *         code in tune with what has happened.
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
   * Endpoint for setting a new enrollment count.
   * This method handles PATCH requests to set a new enrollment count
   * identified by department code and course code.
   * If the course exists, its enrollment count is updated to the provided
   * enrollment count.
   *
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the time for
   * @param count      the new enrollment count for the course
   *
   * @return a ResponseEntity with a success message if the operation is
   *         successful, or an error message if the course is not found
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
   * This method handles PATCH requests to change the time of a course identified
   * by
   * department code and course code.If the course exists, its time is updated to
   * the provided time.
   *
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the time for
   * @param time       the new time for the course
   *
   * @return a ResponseEntity with a success message if the operation is
   *         successful, or an error message if the course is not found
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
   * This method handles PATCH requests to change the instructor of a course
   * identified by department code and course code.
   * If the course exists, its instructor is updated to the provided instructor.
   *
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the instructor for
   * @param teacher    the new instructor for the course
   *
   * @return a ResponseEntity with a success message if the operation is
   *         successful, or an error message if the course is not found
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
   * * Endpoint for changing the location of a course.
   * This method handles PATCH requests to change the location of a course
   * identified by department code and course code.
   * If the course exists, its location is updated to the provided location.
   *
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the location
   * @param location   the new location for the course
   *
   * @return a ResponseEntity with a success message if the operation is
   *         successful, or an error message if the course is not found
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
    return new ResponseEntity<>("An Error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }

}