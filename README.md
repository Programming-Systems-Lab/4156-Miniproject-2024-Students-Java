# Welcome Students of 4156

Please follow the assignment specifications on Courseworks when completing this project.

# 0 Set up 
mvn spring-boot:run -D"spring-boot.run.arguments=setup" for Window

# Style 
mvn checkstyle:check for style errors 

# Test Suite
To generate the test report:
mvn clean test
mvn jacoco:report

The unit tests have 82% coverage.

# Bug Fixing

In individualProject, used: 
pmd.bat check -d src -R rulesets/java/quickstart.xml -f text -r pmd-report.txt
To generate a pmd report.