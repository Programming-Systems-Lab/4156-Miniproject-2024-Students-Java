# Welcome Students of 4156

## COMS4156 Advanced Software Engineering Mini Project 1

## Next will be the step-by-step guideline to run the code (only for Mac java users)

## Step 0: Forking the Repository and Running on Your Machine

To begin, please use the following command to fork the repository:

`git clone git@github.com:peterppap1/4156-Miniproject-2024-Students-Java.git`

After successfully cloning this repository, please navigate to IndividualProject directory using the following command:

`cd IndividualProject`

Make sure you are currently at "4156-Miniproject-2024-Students-Java/IndividualProject" directory.

Then, run the project with this command:

`mvn spring-boot:run -Dspring-boot.run.arguments="setup"`

Leave it running until you see “System Setup” in the terminal, then press "Ctrl" + "C" to terminate the program.
This initializes the database and if you run into any trouble with the database this is the process you’ll need to perform.

## Step 1: Code Clean Up

Continue staying at the "4156-Miniproject-2024-Students-Java/IndividualProject" directory.

Run the following command to check code style:

`mvn checkstyle:checkstyle`

After running this, it should generate "checkstyle-result.xml" file in the directory 
"4156-Miniproject-2024-Students-Java/IndividualProject/target/checkstyle-result.xml".

At this point, there is no warning generated.

## Step 2: Find Potential Bugs

Continue staying at the "4156-Miniproject-2024-Students-Java/IndividualProject" directory.

Firstly, run the following command to generate static bug report:

`mvn pmd:check`

At this point, there is no static bugs warning generated.

Based on the unit test cases, find any potential bugs and fix it.

I wrote down all the bugs I found in the "4156-Miniproject-2024-Students-Java/bugs.txt", which
recorded the location and the name of bug, and what the issue I fixed was.

## Step 3: Check Branch Coverage

Continue staying at the "4156-Miniproject-2024-Students-Java/IndividualProject" directory.

There are totally 5 test files, "CourseUnitTests.java", "DepartmentUnitTests.java", 
"IndividualProjectApplicationUnitTests.java", "MyFileDatabaseUnitTests" and "RouteControllerUnitTests".

Run the following command to check these 5 test files together:

`mvn test`

At this point, there are 103 tests running, passes: 103, Failures: 0, Errors: 0, Skipped: 0.

Finally, run the following command to generate a branch coverage report:

`mvn clean test jacoco:report`

"4156-Miniproject-2024-Students-Java/IndividualProject/target/site/jacoco/index.html" should 
be generated and open it to see the tests branch coverage report. 

At this point, it reaches 100% branch coverage.
