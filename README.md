# Welcome Students of 4156

Please follow the assignment specifications on Courseworks when completing this project.

## I1: Setup, Testing and Bug Fixing

### 1. Code Clean up

`mvn checkstyle:check` yields no warnings or violations.

### 2. Test Suite Creation

78% branch coverage achieved.

### 3. Bug Fixing

13 bugs discovered. Static bug finder [PMD](https://pmd.github.io/) can be run with the following command.

```bash
alias pmd="./pmd-bin-7.6.0/bin/pmd"
$ pmd check -d /IndividualProject/src -R rulesets/java/quickstart.xml -f text
```

## I2: Feature Implementation and Maintenance

### 1. Feature A Implementation

`/retrieveCourses` endpoint realized with robustness guarantee. 

### 2. Feature B Implementation

`/enrollStudentInCourse` endpoint realized with robustness guarantee. 

### 3. Maintaining the Codebase

All new branches tested with a 78% total branch coverage rate. Checkstyle requirements satisfied. 
