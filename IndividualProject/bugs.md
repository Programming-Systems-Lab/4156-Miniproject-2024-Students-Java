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
- **Method Name**: `dropPersonFromMajor()`
    - **Type**: Bug
    - **Description**: There is no input validation if the number of majors is <= 0 in a department.
    - **Fix**: Throw an `IllegalArgumentException` is the number of majors is <= 0.
- **Method Name**: `addCourse()`
    - **Type**: Bug
    - **Description**: There is no input validation to check if the `courseId` is non-null and not
      an
      empty string. Additionally, no check to ensure that `course` is not null
    - **Fix**: Throw an `IllegalArgumentException` if the `course` is null or `courseId` is
      null/empty string.
- **Method Name**: `Department.toString()`.
    - **Type**: Bug
    - **Description**: The method returns the string `"result.toString()"` and not the actual
      value `result.toString()`. Additionally, there is no check to ensure that courses is non-null.
    - **Fix**: Remove double quotes of `"result.toString()"` and return the value stored in
      `result.toString()`. Add a check to ensure courses is non-null.

### Bugs/Updates in `MyFileDatabase`

- **Variable Name**: `filePath`.
    - **Type**: Bug
    - **Description**: `filePath` variable should be final
    - **Fix**: `private final String filePath;`
- **Method Name**: `MyFileDatabase constructor`
    - **Type**: Bug
    - **Description**: We only perform an action when flag is set to `0`
    - **Fix**: When `flag==1`, initialize `this.departmentMapping` to an empty `HashMap` by default
      in the constructor; else throw `IllegalArgumentException`.
- **Method Name**: `deSerializeObjectFromFile()`.
    - **Type**: Bug
    - **Description**: if we cannot get a valid object input stream, we return `null` which could
      lead to `NullPointerException`.
    - **Fix**: return an empty `HashMap` instead.
- **Method Name**: `myFileDatabase.toString()`.
    - **Type**: Bug
    - **Description**: there is no check to see if `departmentMapping` is null or not.
    - **Fix**: add an if-statement to see if `departmentMapping` is null. If it is null, print
      `"No department data available."`; else print departments.