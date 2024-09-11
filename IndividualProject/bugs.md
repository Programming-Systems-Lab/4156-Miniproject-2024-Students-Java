# Bugs

### Bugs/Updates in `Course`

- **Method Name**: `enrollStudent()`
    - **Type**: Bug
    - **Description**: `enrolledStudent()` only adds a student to a course, it does not check if a
      course is full or not. Additionally, it always returns false; it should return true if a
      student is successfully enrolled
    - **Fix**: Add an "if" statement to `enrollStudent()` to check if a course is full. If it is not
      full, increment the `enrolledStudentCount` by 1; otherwise return `false` indicating the class
      is full
- **Method Name**: `dropStudent()`
    - **Type**: Bug
    - **Description**: `dropStudent()` only decrements the `enrolledStudentCount`, it does not check
      to see if there are any students in the class. If `enrolledStudentCount == 0`, then the
      `enrolledStudentCount` would be updated to `-1`. Additionally, this method always returns
      `false`. It should return `true` if we successfully update the `enrolledStudentCount`.
    - **Fix**: Add an "if" statement to `dropStudent()` to check if a course has any students. If it
      has at least 1 student, decrement the `enrolledStudentCount` by 1; otherwise return `false`
      indicating the class has no students
- **Method Name**: `getCourseTimeSlot()`, `getEnrollmentCapacity()`, and
  `getEnrolledStudentCount()`
    - **Type**: Added helper methods to retrieve private params
    - **Description**: Added the `getCourseTimeSlot()`, `getEnrollmentCapacity()`, and
      `getEnrolledStudentCount()` methods for ease of testing
- **Method Name**: `reassignInstructor()`
    - **Type**: Bug
    - **Description**: `reassignInstructor()` does not check if the input is an empty string or
      null.
    - **Fix**: Throw an exception if the `newInstructorName` param is null or an empty string.
- **Method Name**: `reassignLocation()`
    - **Type**: Bug
    - **Description**: `reassignLocation()` Does not check if the input is an empty string or
      null.
    - **Fix**: Throw an exception if the `newLocation` param is null or an empty string.
- **Method Name**: `reassignTime()`
    - **Type**: Bug
    - **Description**: `reassignTime()` does not perform any input validation for the course time.
    - **Fix**: Validating the input with regex to ensure the course time matches `HH:MM-HH:MM`,
      `H:MM-HH:MM`, or `HH:MM-H:MM`
- **Method Name**: `setEnrolledStudentCount()`
    - **Type**: Bug
    - **Description**: The method does not have a validation check to ensure the enrollment `count`
      is > 0.
    - **Fix**: Ensuring the input validation and that `count > 0`.
- **Method Name**: `isCourseFull()`
    - **Type**: Bug
    - **Description**: The method checks to see if the `enrollmentCapacity > enrolledStudentCount`,
      which would indicate that the course is not yet full.
    - **Fix**: Ensuring the proper conditional check to satisfy whether a course is full or not.

### Bugs/Updates in `Department`

- **Method Name**: `getNumberOfMajors()`
    - **Type**: Bug
    - **Description**: The method returns `-this.numberOfMajors` (negative value), and not the
      number of majors stored in `this.numberOfMajors`
    - **Fix**: Change `-this.numberOfMajors` to `this.numberOfMajors`
- **Method Name**: `getDepartmentChair()`
    - **Type**: Bug
    - **Description**: The method returns the string `"this.departmentChair"` and not the actual
      value `this.departmentChair`
    - **Fix**: Remove double quotes of `"this.departmentChair"` and return the value stored in
      `this.departmentChair`