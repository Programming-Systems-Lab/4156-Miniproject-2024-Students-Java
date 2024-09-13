package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class is built to test the IndividualProjectApplication class, which we have created to represent
 * the application that manages the courses and departments.
 */
@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationUnitTest {

    @BeforeAll
    public static void setupCourseForTesting() {
        // Firstly we set up the dataset for the test
        application = new IndividualProjectApplication();
        String[] args = {"setup"};
        application.run(args);
    }

    @Test
    public void runTest() {
        myFileDatabase = IndividualProjectApplication.myFileDatabase;
        assertEquals(7, myFileDatabase.getDepartmentMapping().size());
    }

    @Test
    public void overrideDatabaseTest() {
        testMyFileDatabase = new MyFileDatabase(0, "data.txt");

        Course testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
        HashMap<String, Course> testCourses = new HashMap<String, Course>();
        testCourses.put("4156", testCourse);

        Department testDepartment = new Department("COMS", testCourses, "Griffin Newbold", 2700);
        HashMap<String, Department> testDepartmentMapping = new HashMap<String, Department>();
        testDepartmentMapping.put("COMS", testDepartment);

        overideMyFileDatabase = new MyFileDatabase(1, "override_data.txt");
        overideMyFileDatabase.setMapping(testDepartmentMapping);
        IndividualProjectApplication.overrideDatabase(overideMyFileDatabase);

        myFileDatabase = IndividualProjectApplication.myFileDatabase;
        assertEquals(1, myFileDatabase.getDepartmentMapping().size());

        // set the database back to the original value
        IndividualProjectApplication.overrideDatabase(testMyFileDatabase);
    }

    @Test
    public void onTerminationTest() {
        application.onTermination();
        testMyFileDatabase = new MyFileDatabase(0, "data.txt");
        assertEquals(7, IndividualProjectApplication.myFileDatabase.getDepartmentMapping().size());
    }
    
    private MyFileDatabase testMyFileDatabase;
    private MyFileDatabase overideMyFileDatabase;
    private MyFileDatabase myFileDatabase;
    private static IndividualProjectApplication application;
}
