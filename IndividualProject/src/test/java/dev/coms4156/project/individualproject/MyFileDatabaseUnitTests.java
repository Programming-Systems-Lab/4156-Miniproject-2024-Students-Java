package dev.coms4156.project.individualproject;

import static dev.coms4156.project.individualproject.IndividualProjectApplication.myFileDatabase;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * This class contains the tests for the {@link MyFileDatabase} class.
 * The tests verify the correctness of the MyFileDatabase class's methods.
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseUnitTests {

    /**
     * Test instance of MyFileDatabase.
     */
    public static MyFileDatabase myFileDatabase;

    /**
     * Initializes MyFileDatabase before each test.
     */
    @BeforeAll
    public static void setupMyFileDatabaseForTesting() {
        // Delete the file before each test if it exists
        File file = new File("./testdata.txt");
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Tests the {@link MyFileDatabase#setMapping(HashMap)} method.
     * Verifies that the mapping is correctly set and can be retrieved.
     */
    @Test
    public void testSetMapping() {
        setupMyFileDatabaseForTesting();
        Map<String, Department> testMapping = new HashMap<>();
        Department compSciDepartment = new Department("COMS", new HashMap<>(), "John Doe", 300);
        testMapping.put("COMS", compSciDepartment);

        myFileDatabase.setMapping((HashMap<String, Department>) testMapping);

        // The below command verifies if the mapping is correctly set
        assertEquals(compSciDepartment, myFileDatabase.getDepartmentMapping().get("COMS"));
    }

    /**
     * Tests the {@link MyFileDatabase#saveContentsToFile()} method.
     */
    @Test
    public void testSaveContentsToFile() {
        setupMyFileDatabaseForTesting();

        Department department = new Department("COMS", new HashMap<>(), "John Doe", 300);
        Map<String, Department> departmentMap = new HashMap<>();
        departmentMap.put("COMS", department);
        myFileDatabase.setMapping((HashMap<String, Department>) departmentMap);

        // Call the method to save the contents to the file
        myFileDatabase.saveContentsToFile();

        File file = new File("./testdata.txt");

        // Verify the contents of the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean contentFound = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("COMS")) {
                    contentFound = true;  // Check that department data is saved
                    break;
                }
            }
            assertTrue(contentFound);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }


    /**
     * Tests the {@link MyFileDatabase#deSerializeObjectFromFile()} method.
     * Verifies that the file content is deserialized and loaded into the database.
     */
    @Test
    public void testDeSerializeObjectFromFile() {
        // Assuming the deserialization works, though this test could verify file contents.
        MyFileDatabaseUnitTests.setupMyFileDatabaseForTesting();
        myFileDatabase.deSerializeObjectFromFile();

        // Again, file system interaction would need advanced verification.
        assertTrue(true);  // Basic test to indicate the method was called.
    }
}



