# COMS 4156: Individual Project

This project is done using Java.

To set up the project, I navigated to the IndividualProject folder and ran the following command:

```mvn spring-boot:run -Dspring-boot.run.arguments="setup"```

## Part 1
To run stylecheck, I use the following command in the IndividualProject directory:
```mvn checkstyle:check```

## Part 2
To run JaCoCo, I ran the following command in the IndividualProject directory:
- `mvn test`
- `mvn jacoco:report`

I then opened the file `IndividualProject/target/site/jacoco/index.html` to see the coverage.

## Part 3
I used PMD in this project as a static bug finder, executing the following command to get a report:
```mvn pmd:check```
