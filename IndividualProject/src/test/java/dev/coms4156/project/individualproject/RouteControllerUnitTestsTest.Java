@Test
public void retrieveCourses() throws Exception {
  // Call the retrieveCourses method in the RouteController class with deptCode="COMS"
  ResponseEntity<?> response = testRouteController.retrieveCourses("COMS");
  
  // Assert that the response status is HttpStatus.OK
  assertEquals(HttpStatus.OK, response.getStatusCode());
  
  // Assert that the response body contains the expected course information
  assertEquals("COMS 3827: \n" +
      "Instructor: Daniel Rubenstein; Location: 207 Math; Time: 10:10-11:25\n" +
      "COMS 1004: \n" +
      "Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55\n" +
      "COMS 3203: \n" +
      "Instructor: Ansaf Salleb-Aouissi; Location: 301 URIS; Time: 10:10-11:25\n" +
      "COMS 4156: \n" +
      "Instructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25\n" +
      "COMS 3157: \n" +
      "Instructor: Jae Lee; Location: 417 IAB; Time: 4:10-5:25\n" +
      "COMS 3134: \n" +
      "Instructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25\n" +
      "COMS 3251: \n" +
      "Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40\n" +
      "COMS 3261: \n" +
      "Instructor: Josh Alman; Location: 417 IAB; Time: 2:40-3:55\n", response.getBody());
}