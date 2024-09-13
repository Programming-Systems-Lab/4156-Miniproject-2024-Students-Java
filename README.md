# Tommy Lam tsl2139 Individual Project

## Static Analysis

This project uses [PMD](https://pmd.github.io/) for static code analysis. To run PMD, use the following command:

```
pmd check -d IndividualProject/src -R rulesets/java/quickstart.xml -f text
```

This will analyze the code in the `src` directory using the Java QuickStart ruleset and output the results in text format.