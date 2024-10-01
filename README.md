# Welcome Students of 4156

Please follow the assignment specifications on Courseworks when completing this project.

### I1: Setup, Testing and Bug Fixing

##### 1. Code Clean up

`mvn checkstyle:check` yields no warnings or violations.

##### 2. Test Suite Creation

78% branch coverage achieved.

##### 3. Bug Fixing

13 bugs discovered. Static bug finder [PMD](https://pmd.github.io/) can be run with the following command.

```bash
alias pmd="./pmd-bin-7.6.0/bin/pmd"
$ pmd check -d /IndividualProject/src -R rulesets/java/quickstart.xml -f text
```
