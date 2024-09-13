# Welcome Students of 4156

Please follow the assignment specifications on Courseworks when completing this project.


To Run Code:
mvn spring-boot:run

Example URL:
http://localhost:8080/retrieveCourse?deptCode=COMS&courseCode=4156


How to run code coverage
```
mvn verify (mvn clean verify if for some reason that doesn't work)
```
When in the Individual Project folder, run: 
```
open target/site/jacoco/index.html
```
junit added to pom.xml


How to run PMD -> Taken from this website
https://sachinsf.com/how-to-use-pmd-code-analyzer-with-intellij/

1. Download PMD
2. Download PMD plugin in IntelliJ
3. Settings -> Tools -> External Tools
4. Create New
   5. Name can be anything
   6. Description can be anything
   7. Program enter the PMD.bat file that you downloaded form the pmd website that was given in the course
   8. Arguments put this in
```
-d “$FilePath$” -f ideaj -R rulesets/java/quickstart.xml -P sourcePath=”$Sourcepath$” -P classAndMethodName=$FileClass$.method -P fileName=$FileName$
```
9. Working Directory put 
```
$ProjectFileDir$
```
10. Click Ok And Apply
11. Go Into Settings > PMD (not in any category)
12. Add a Ruleset of the one I used (COMS 4156 - Advanced Software Engineering/4156-Miniproject-2024-Students-Java/IndividualProject/001-maven-pmd-plugin-default.xml)
    11. Note: This ruleset is just the one taken from https://maven.apache.org/plugins/maven-pmd-plugin/examples/usingRuleSets.html (default ruleset)
    12. The only differance is that I removed TooManyStaticImports and UnnecessaryImport because I believe that that is just a stylistic choice
12. Right Click on java.dev.coms4156.project.individualproject folder in src/main > Run PMD > Custom Rules > Default Maven PMD Plugin Ruleset


