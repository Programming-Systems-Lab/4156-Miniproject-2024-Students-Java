# 4156 Mini Project README

### Check Style
Command: mvn checkstyle:check 

There are no more checkstyle warnings.

### Bug Finder
Bug finder: PMD
Command: pmd check -d <path to IndividualProject>/src/main/java/dev/coms4156/project/individualproject -R rulesets/java/quickstart.xml -f text

There are no more PMD bugs.

### Test Coverage
Command: mvn clean test jacoco:report 

The JaCoCo report an instruction coverage of 93% and a branch coverage of 83%.
