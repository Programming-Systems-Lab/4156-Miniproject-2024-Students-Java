package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the IndividualProjectApplication class.
 */
@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationUnitTests {

    private static MyFileDatabase testDatabase;

    /**
     * Sets up the test environment by initializing the database instance
     * with a test file and overriding the default database in the application.
     */
    @BeforeAll
    public static void setupTestEnvironment() {
        // Initialize the database instance with a test file (not creating an actual file)
        testDatabase = new MyFileDatabase(1, "./test_data.txt");
        IndividualProjectApplication.overrideDatabase(testDatabase);
    }

    /**
     * Tests the application's run method with the "setup" argument to verify
     * that the database is initialized properly.
     */
    @Test
    public void testRunWithSetupArgument() {
        // Prepare arguments to simulate "setup" command
        String[] args = {"setup"};

        // Run the application with the setup argument
        IndividualProjectApplication application = new IndividualProjectApplication();
        application.run(args);

        // Verify that the database is initialized properly
        assertNotNull(IndividualProjectApplication.myFileDatabase, "Database should be initialized.");
        assertFalse(IndividualProjectApplication.myFileDatabase.getDepartmentMapping().isEmpty(), "Database should have departments loaded.");
    }

    /**
     * Tests the application's run method without any arguments to verify
     * that the database is initialized with default values.
     */
    @Test
    public void testRunWithoutArguments() {
        // Prepare arguments to simulate no command line argument
        String[] args = {};

        // Run the application without any arguments
        IndividualProjectApplication application = new IndividualProjectApplication();
        application.run(args);

        // Verify that the database is initialized properly
        assertNotNull(IndividualProjectApplication.myFileDatabase, "Database should be initialized.");
        assertTrue(IndividualProjectApplication.myFileDatabase.getDepartmentMapping().containsKey("COMS"), "Database should contain COMS department.");
    }

    /**
     * Tests the overrideDatabase method to ensure that the application's
     * database can be overridden with a new test instance.
     */
    @Test
    public void testOverrideDatabase() {
        // Override with a new test database
        MyFileDatabase newTestDatabase = new MyFileDatabase(1, "./new_test_data.txt");
        IndividualProjectApplication.overrideDatabase(newTestDatabase);

        // Verify that the database was overridden successfully
        assertEquals(newTestDatabase, IndividualProjectApplication.myFileDatabase, "Database should be overridden with the new test instance.");
    }

    /**
     * Tests the resetDataFile method to verify that the data is reset correctly
     * in the application.
     */
    @Test
    public void testResetDataFile() {
        // Create an instance of IndividualProjectApplication and reset the data file
        IndividualProjectApplication application = new IndividualProjectApplication();
        application.resetDataFile();

        // Verify that the data is reset correctly
        HashMap<String, Department> departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        assertTrue(departmentMapping.containsKey("COMS"), "Database should contain COMS department.");
        assertTrue(departmentMapping.containsKey("ECON"), "Database should contain ECON department.");
        assertTrue(departmentMapping.containsKey("IEOR"), "Database should contain IEOR department.");
        assertTrue(departmentMapping.containsKey("CHEM"), "Database should contain CHEM department.");
        assertTrue(departmentMapping.containsKey("PHYS"), "Database should contain PHYS department.");
        assertTrue(departmentMapping.containsKey("ELEN"), "Database should contain ELEN department.");
        assertTrue(departmentMapping.containsKey("PSYC"), "Database should contain PSYC department.");
    }

    /**
     * Tests the onTermination method to ensure that it does not throw an exception
     * during application termination.
     */
    @Test
    public void testOnTermination() {
        // Simulate application termination
        IndividualProjectApplication application = new IndividualProjectApplication();
        application.onTermination();

        // Since saveData is set to false by overrideDatabase, no actual file operations will occur
        assertDoesNotThrow(() -> application.onTermination(), "onTermination should not throw an exception.");
    }

    /**
     * Cleans up the test environment by resetting the saveData flag for future tests.
     */
    @AfterAll
    public static void cleanupTestEnvironment() {
        // Reset the saveData flag to true for future tests
        IndividualProjectApplication.myFileDatabase = null;
    }
}
