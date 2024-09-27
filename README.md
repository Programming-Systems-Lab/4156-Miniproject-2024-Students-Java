# COMS 4156: Individual Project

Developed on MacOS and ARM 64. Fall 2024.

## Building and Running a Local Instance

To build and run this project, you need to:
1. Clone this repo.
2. Install Maven 3.9.5 (Follow the download and installation instructions here: [https://maven.apache.org/docs/3.9.5/release-notes.html](url)).
3. Install JDK. This project was developed using JDK 17, so this version is recommended. Download the JDK for your device here: [https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html](url).
4. Install any IDE that can run a Java project. This project was developed using VS Code (Download here: [https://code.visualstudio.com/download](url)).
5. Set up the project by running the following command in the [IndividualProject](https://github.com/elifia-muthia/4156-Miniproject-2024-Students-Java/tree/main/IndividualProject) directory by running the command below. Terminate execution (^C) as soon as you see `System Setup`.
```
mvn spring-boot:run -Dspring-boot.run.arguments="setup"
```

## Running a Cloud based Instance

To deploy your app on the cloud, you can use Google Cloud Platform's App Engine (overview here: [https://cloud.google.com/appengine/docs/an-overview-of-app-engine](url)). To begin, you need to:
1. Download and install the gcloud CLI (link: [https://cloud.google.com/sdk/docs/install](url))
2. Run the following on your terminal for authentication and setting up your SDK:
```
gcloud init
```
3. Create a project on Google Cloud Console
4. Navigate to the [IndividualProject directory](https://github.com/elifia-muthia/4156-Miniproject-2024-Students-Java/tree/main/IndividualProject) in your local copy of the repo and create an `app.yaml` file containing the following:
```
entrypoint: java -jar target/IndividualProject-0.0.1-SNAPSHOT.jar
runtime: java17
instance_class: F1
```
5. Make sure that you have set up the project as described in step 5 of [this section](https://github.com/elifia-muthia/4156-Miniproject-2024-Students-Java/edit/docs-and-maintenance/README.md#building-and-running-a-local-instance).
6. Run the following command, replacing `PROJECT_ID` with your GCP Project ID:
```
gcloud config set project PROJECT_ID
```
7. Run the following command in your terminal to deploy:
```
gcloud app deploy
```
8. Running the following command will tell you what URL your app has been deployed in:
```
gcloud app browse
```
9. Test your app using [Postman](https://www.postman.com/). You can find a video demo of this app being tested on Postman in the [Video-Demo-Link.txt](https://github.com/elifia-muthia/4156-Miniproject-2024-Students-Java/blob/main/Video-Demo-Link.txt) of this repo's root directory.

Voila - Your app is now fully deployed on the cloud!

You can see what the app instance on Google Cloud looks like in the [Google-Cloud-Instance.png](https://github.com/elifia-muthia/4156-Miniproject-2024-Students-Java/blob/main/Google-Cloud-Instance.png) image found in the root directory of this repo.

## Running Unit Tests

The unit tests for this project can be found in the [IndividualProject/src/test/java/dev/coms4156/project/individualproject](https://github.com/elifia-muthia/4156-Miniproject-2024-Students-Java/tree/main/IndividualProject/src/test/java/dev/coms4156/project/individualproject) directory. In that directory, you will find unit tests for each of the classes developed for this project.

To run the unit tests, you must first build the project ([see here](https://github.com/elifia-muthia/4156-Miniproject-2024-Students-Java/edit/docs-and-maintenance/README.md#building-and-running-a-local-instance)) and run the following command in the [IndividualProject](https://github.com/elifia-muthia/4156-Miniproject-2024-Students-Java/tree/main/IndividualProject) directory
```
mvn clean test
```

Running the command above will show you if all the unit tests are successful or any errors that occurred.

## Style Checking Report

Style checking was done using the checkstyle tool on Maven. You can run checkstyle using the following command:
```
mvn checkstyle:check
```

Below is the checkstyle report as of 09/27/24, showing that there are 0 checkstyle violations:
<img width="407" alt="Screenshot 2024-09-27 at 00 09 52" src="https://github.com/user-attachments/assets/b9d2a086-dadf-48df-8c5e-5b74d758dad7">

## Static Code Analysis Report

PMD is used for the static code analysis report (install here: [https://pmd.github.io/](url)). Run PMD with the following command,
```
mvn pmd:check
```

Below is the PMD report as of 09/26/24:

<img width="503" alt="Screenshot 2024-09-26 at 23 58 51" src="https://github.com/user-attachments/assets/2dabf991-7840-48aa-bea3-c9c3d632b838">

## Branch Coverage Reporting

This project uses JaCoco to analyze branch coverage for the unit tests. Run the unit tests first ([see here](https://github.com/elifia-muthia/4156-Miniproject-2024-Students-Java/edit/docs-and-maintenance/README.md#running-unit-tests)), and then run the following command:
```
mvn jacoco:report
```

A target directory will be created (if it doesn't exist already) in IndividualProject and you can find the coverage report in IndividualProject/target/site/jacoco/index.html. Below is the coverage report as of 09/26/24:
<img width="1103" alt="Screenshot 2024-09-26 at 23 54 12" src="https://github.com/user-attachments/assets/f7d10817-b43f-49a1-b06d-376ef327167d">

