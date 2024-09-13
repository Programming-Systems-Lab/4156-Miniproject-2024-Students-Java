/**
 * Test case for the dropStudent method in the RouteController class.
 *
 * @throws Exception if an error occurs during the test
 */
@Test
public void dropStudent() throws Exception {
  // Add a student to the department
  testDepartment.addStudent("John Doe", "1234567890");

  // Call the dropStudent method in the RouteController class with
  // deptCode="4995" and studentId="1234567890" and expect a ResponseEntity with HttpStatus.OK
  ResponseEntity<?> response = testRouteController.dropStudent("4995", "1234567890");
  assertEquals(HttpStatus.OK, response.getStatusCode());
  assertEquals("Student dropped successfully", response.getBody());

  // Verify that the student has been removed from the department
  assertNull(testDepartment.getStudent("1234567890"));
}

/**
 * Test case for the dropStudent method in the RouteController class when
 * the department is not found.
 *
 * @throws Exception if an error occurs during the test
 */
@Test
public void dropStudentDepartmentNotFound() throws Exception {
  // Call the dropStudent method in the RouteController class with
  // deptCode="5000" and studentId="1234567890" and expect a ResponseEntity with HttpStatus.NOT_FOUND
  ResponseEntity<?> response = testRouteController.dropStudent("5000", "1234567890");
  assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  assertEquals("Department Not Found", response.getBody());
}

/**
 * Test case for the dropStudent method in the RouteController class when
 * the student is not found in the department.
 *
 * @throws Exception if an error occurs during the test
 */
@Test
public void dropStudentStudentNotFound() throws Exception {
  // Call the dropStudent method in the RouteController class with
  // deptCode="4995" and studentId="9876543210" and expect a ResponseEntity with HttpStatus.NOT_FOUND
  ResponseEntity<?> response = testRouteController.dropStudent("4995", "9876543210");
  assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  assertEquals("Student Not Found", response.getBody());
}/**
 * Test case for the dropStudent method in the RouteController class.
 *
 * @throws Exception if an error occurs during the test
 */
@Test
public void dropStudent() throws Exception {
  // Add a student to the department
  testDepartment.addStudent("John Doe", "1234567890");

  // Call the dropStudent method in the RouteController class with
  // deptCode="4995" and studentId="1234567890" and expect a ResponseEntity with HttpStatus.OK
  ResponseEntity<?> response = testRouteController.dropStudent("4995", "1234567890");
  assertEquals(HttpStatus.OK, response.getStatusCode());
  assertEquals("Student dropped successfully", response.getBody());

  // Verify that the student has been removed from the department
  assertNull(testDepartment.getStudent("1234567890"));
}

/**
 * Test case for the dropStudent method in the RouteController class when
 * the department is not found.
 *
 * @throws Exception if an error occurs during the test
 */
@Test
public void dropStudentDepartmentNotFound() throws Exception {
  // Call the dropStudent method in the RouteController class with
  // deptCode="5000" and studentId="1234567890" and expect a ResponseEntity with HttpStatus.NOT_FOUND
  ResponseEntity<?> response = testRouteController.dropStudent("5000", "1234567890");
  assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  assertEquals("Department Not Found", response.getBody());
}

/**
 * Test case for the dropStudent method in the RouteController class when
 * the student is not found in the department.
 *
 * @throws Exception if an error occurs during the test
 */
@Test
public void dropStudentStudentNotFound() throws Exception {
  // Call the dropStudent method in the RouteController class with
  // deptCode="4995" and studentId="9876543210" and expect a ResponseEntity with HttpStatus.NOT_FOUND
  ResponseEntity<?> response = testRouteController.dropStudent("4995", "9876543210");
  assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  assertEquals("Student Not Found", response.getBody());
}/**
 * Test case for the setEnrollmentCount method in the RouteController class.
 *
 * @throws Exception if an error occurs during the test
 */
@Test
public void setEnrollmentCount() throws Exception {
  // Set the enrollment count for department "4995" to 50
  ResponseEntity<?> response = testRouteController.setEnrollmentCount("4995", 50);
  assertEquals(HttpStatus.OK, response.getStatusCode());
  assertEquals("Enrollment count set successfully", response.getBody());

  // Verify that the enrollment count has been updated
  Department department = departmentMapping.get("4995");
  assertEquals(50, department.getEnrollmentCount());
}

/**
 * Test case for the setEnrollmentCount method in the RouteController class when
 * the department is not found.
 *
 * @throws Exception if an error occurs during the test
 */
@Test
public void setEnrollmentCountDepartmentNotFound() throws Exception {
  // Set the enrollment count for department "5000" to 50
  ResponseEntity<?> response = testRouteController.setEnrollmentCount("5000", 50);
  assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  assertEquals("Department Not Found", response.getBody());
}

/**
 * Test case for the setEnrollmentCount method in the RouteController class when
 * the enrollment count is negative.
 *
 * @throws Exception if an error occurs during the test
 */
@Test
public void setEnrollmentCountNegativeCount() throws Exception {
  // Set the enrollment count for department "4995" to -10
  ResponseEntity<?> response = testRouteController.setEnrollmentCount("4995", -10);
  assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  assertEquals("Invalid enrollment count", response.getBody());
}