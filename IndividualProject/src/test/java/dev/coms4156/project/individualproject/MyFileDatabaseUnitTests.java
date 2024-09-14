package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


class MyFileDatabaseUnitTests {

  @TempDir
  Path tempDir;

  private MyFileDatabase database;
  private String filePath;

  @BeforeEach
  void setUp() {
    filePath = tempDir.resolve("testDatabase.db").toString();
    database = new MyFileDatabase(0, filePath);

    Map<String, Department> initialData = new HashMap<>();
    initialData.put("CS", new Department("CS", new HashMap<>(), "Dr. Smith", 100));
    database.setMapping(initialData);
  }

  @Test
  void serializationAndDeserializationTest() throws Exception {
    database.saveContentsToFile();

    MyFileDatabase newDatabase = new MyFileDatabase(0, filePath);
    assertEquals("Dr. Smith", newDatabase.getDepartmentMapping().get("CS").getDepartmentChair());

    assertNotNull(newDatabase.getDepartmentMapping());
    assertTrue(newDatabase.getDepartmentMapping().containsKey("CS"));
  }

  @Test
  void setMappingTest() {
    Map<String, Department> newMapping = new HashMap<>();
    newMapping.put("ABC", new Department("ABC", new HashMap<>(), "Dr. SHEN", 50));

    database.setMapping(newMapping);
    assertEquals("Dr. SHEN", database.getDepartmentMapping().get("ABC").getDepartmentChair());
    assertEquals(50, database.getDepartmentMapping().get("ABC").getNumberOfMajors());
  }

  @Test
  void saveContentsToFileTest() {
    database.saveContentsToFile();

    File file = new File(filePath);
    assertTrue(file.exists());
    assertTrue(file.length() > 0);
  }

}

