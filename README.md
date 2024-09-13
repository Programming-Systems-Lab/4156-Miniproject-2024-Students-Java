# Welcome Students of 4156

Please follow the assignment specifications on Courseworks when completing this project.

# Test Suite
To generate the test report:
mvn clean test
mvn jacoco:report

The unit tests have 66% coverage.

# Bug Fixing

In individualProject, used: 
pmd.bat check -d src -R rulesets/java/quickstart.xml -f text -r pmd-report.txt
To generate a pmd report.