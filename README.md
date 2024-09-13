# COMS 4156: Individual Project

To run this project, navigate into the IndividualProject folder and follow the instructions below:

1. To set up, run the following command and terminate execution (^C) as soon as you see `System Setup`
```
mvn spring-boot:run -Dspring-boot.run.arguments="setup"
```
2. To run stylecheck, run the following command
```
mvn checkstyle:check
```
3. I used PMD as my bug finder. Run PMD wil the following command,
```
mvn pmd:check
```
4. To run Jacoco and create the coverage report, I used the following commands,
```
mvn clean test
```
```
mvn jacoco:report
```
