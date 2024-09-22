# Welcome Students of 4156

Please follow the assignment specifications on Courseworks when completing this project.

Follow these steps to run PMD static code analysis:
1. Navigate to the src folder
2. Enter in the following command: 
pmd check -f text -R rulesets/java/quickstart.xml -r pmd-report<your_report_number>.txt -d main,test
3. The output of the analysis will be viewable in the specified output file
