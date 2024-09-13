# Welcome Students of 4156

## Running Checkstyle
To run the Checkstyle checker, use the following command:

`mvn checkstyle:checkstyle`

## Running Static Bug Checker
To run the static bug checker using PMD, you can use either of the following commands:

`pmd check -d src/main/java -R rulesets/java/quickstart.xml -f text -r static_bugs.txt`

Alternatively, you can run it using Maven:

`mvn pmd:check`

## Checking Unit Test Branch Coverage
To check the branch coverage of your unit tests using JaCoCo, run the following command:

`mvn clean test jacoco:report`
