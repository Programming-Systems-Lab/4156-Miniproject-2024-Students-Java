package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.HashMap;

/**
 * Unit tests for the MyFileDatabase class.
 */
public class MyFileDatabaseUnitTest {

    private static final String TEST_FILE_PATH = "testData.txt";
    private static MyFileDatabase myFileDatabase;
    private static HashMap<String, Department> testMapping;

    /**
     * Setup the test environment by creating a test file and initializing the test data.
     */
    @BeforeAll
    public static void setup() {
        testMapping = new HashMap<>();
        Course testCourse = new Course("Test Instructor", "101 Test Hall", "09:00-10:15", 30);
        HashMap<String, Course> courses = new HashMap<>();
        courses.put("101", testCourse);
        Department testDept = new Department("TEST", courses, "Dr. Test", 100);
        testMapping.put("TEST", testDept);

        // Serialize the test data to a file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(TEST_FILE_PATH))) {
            out.writeObject(testMapping);
        } catch (IOException e) {
            fail("Failed to set up test file: " + e.getMessage());
        }

        myFileDatabase = new MyFileDatabase(0, TEST_FILE_PATH);
    }

    /**
     * Test the deserialization of objects from a file.
     */
    @Test
    public void testDeserializeObjectFromFile() {
        HashMap<String, Department> deserializedMapping = myFileDatabase.deSerializeObjectFromFile();
        assertNotNull(deserializedMapping, "Deserialized mapping should not be null");
        assertEquals(1, deserializedMapping.size(), "Deserialized mapping should contain 1 department");
        assertTrue(deserializedMapping.containsKey("TEST"), "Mapping should contain the TEST department");
        assertEquals("Dr. Test", deserializedMapping.get("TEST").getDepartmentChair(), "Incorrect department chair");
    }

    /**
     * Test the serialization of objects to a file.
     */
    @Test
    public void testSaveContentsToFile() {
        myFileDatabase.setMapping(testMapping);
        myFileDatabase.saveContentsToFile();

        // Deserialize to verify the data is correctly saved
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(TEST_FILE_PATH))) {
            HashMap<String, Department> deserializedMapping = (HashMap<String, Department>) in.readObject();
            assertNotNull(deserializedMapping, "Deserialized mapping should not be null after saving");
            assertEquals(1, deserializedMapping.size(), "Deserialized mapping should contain 1 department after saving");
            assertTrue(deserializedMapping.containsKey("TEST"), "Mapping should contain the TEST department after saving");
        } catch (IOException | ClassNotFoundException e) {
            fail("Failed to read test file after saving: " + e.getMessage());
        }
    }

    /**
     * Test the toString method of MyFileDatabase.
     */
    @Test
    public void testToString() {
        myFileDatabase.setMapping(testMapping);
        String expectedOutput = "For the TEST department: \n" + testMapping.get("TEST").toString();
        assertEquals(expectedOutput, myFileDatabase.toString(), "toString method returned an incorrect string");
    }

    /**
     * Cleanup after all tests are run.
     */
    @AfterAll
    public static void cleanup() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            assertTrue(testFile.delete(), "Failed to delete the test data file.");
        }
    }
}
