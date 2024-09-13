package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Assertions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


class MyFileDatabaseUnitTests {

    @TempDir
    Path tempDir;

    private MyFileDatabase database;
    private String filePath;

    @BeforeEach
    void setUp() {
        filePath = tempDir.resolve("testDatabase.db").toString();
        database = new MyFileDatabase(0, filePath);

        HashMap<String, Department> initialData = new HashMap<>();
        initialData.put("CS", new Department("CS", new HashMap<>(), "Dr. Smith", 100));
        database.setMapping(initialData);
    }

    @Test
    void SerializationAndDeserializationTest() throws Exception {
        database.saveContentsToFile();

        MyFileDatabase newDatabase = new MyFileDatabase(0, filePath);
        assertEquals("Dr. Smith", newDatabase.getDepartmentMapping().get("CS").getDepartmentChair());

        assertNotNull(newDatabase.getDepartmentMapping());
        assertTrue(newDatabase.getDepartmentMapping().containsKey("CS"));
    }

    @Test
    void SetMappingTest() {
        HashMap<String, Department> newMapping = new HashMap<>();
        newMapping.put("ABC", new Department("ABC", new HashMap<>(), "Dr. SHEN", 50));

        database.setMapping(newMapping);
        assertEquals("Dr. SHEN", database.getDepartmentMapping().get("ABC").getDepartmentChair());
        assertEquals(50, database.getDepartmentMapping().get("ABC").getNumberOfMajors());
    }

    @Test
    void SaveContentsToFileTest() {
        database.saveContentsToFile();

        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void ToStringtest() {
        String result = database.toString();
        assertTrue(result.contains("For the CS department"));
        assertTrue(result.contains("Dr. Smith"));
    }
}

