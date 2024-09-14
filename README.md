# Welcome Students of 4156

Please follow the assignment specifications on Courseworks when completing this project.

Commands:
Stylecheck: mvn checkstyle:check
Test: mvn test
Generate report: mvn jacoco:report


Instructions for PMD:

To run: 

I ran the following commands:

Course.java:
pmd check -d /Users/lucifeinberg/Desktop/2024-2025/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individualproject/course.java -R rulesets/java/quickstart.xml -f text

Department.java:
pmd check -d /Users/lucifeinberg/Desktop/2024-2025/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individualproject/department.java -R rulesets/java/quickstart.xml -f text

IndividualProjectApplication.java:
pmd check -d /Users/lucifeinberg/Desktop/2024-2025/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individualproject/IndividualProjectApplication.java -R rulesets/java/quickstart.xml -f text

MyFileDatabase.java:
pmd check -d /Users/lucifeinberg/Desktop/2024-2025/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individual project/MyFileDatabase.java -R rulesets/java/quickstart.xml -f text

RouteController.java:
pmd check -d /Users/lucifeinberg/Desktop/2024-2025/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individual project/RouteController.java -R rulesets/java/quickstart.xml -f text


And then, after I read the output in the terminal (the warnings) to derive bugs I needed to fix.







Note: 

I initially planned to drop the class due to extenuating circumstances/credits (as this class is not required for my track); however, I decided to keep it! Thus, I did not have time on Friday to finish testing for RouteController.java and MyFileDatabase.java

Here are the steps I did take:
Clean the code (without any tests)
Run PMD to find static bugs (all files)
Ran tests for Course.java, Department.java, IndividualProjectApplication.java
Fixed bugs for Course.java, Department.java, IndividualProjectApplication.java
Documented bugs in bugs.txt
Ran style check for the test files.
I have not: As of Friday September 13th, 10:35pm, I have not finished debugging RouteController.java and MyFileDatabase.java

NOTE: I am aware this does not excuse not completing the assignment, and I know this will get points deducted for sure! I just wanted to provide context for why some testing files may be empty.

