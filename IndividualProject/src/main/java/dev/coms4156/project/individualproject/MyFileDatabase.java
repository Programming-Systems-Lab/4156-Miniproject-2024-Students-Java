package dev.coms4156.project.individualproject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a file-based database containing department mappings.
 */
public class MyFileDatabase {

  /** The path to the file containing the database entries. */
  private final String filePath;

  /** The mapping of department names to Department objects. */
  private Map<String, Department> departmentMapping;

  /**
   * Constructs a MyFileDatabase object and loads up the data structure with
   * the contents of the file.
   *
   * @param flag     used to distinguish mode of database
   * @param filePath the path to the file containing the entries of the database
   */
  public MyFileDatabase(int flag, String filePath) {
    this.filePath = filePath;
    if (flag == 0) {
      this.departmentMapping = deSerializeObjectFromFile();
    }
  }

  /**
   * Sets the department mapping of the database.
   *
   * @param mapping the mapping of department names to Department objects
   */
  public void setMapping(Map<String, Department> mapping) {
    this.departmentMapping = mapping;
  }

  /**
   * Deserializes the object from the file and returns the department mapping.
   *
   * @return the deserialized department mapping
   */
  public final Map<String, Department> deSerializeObjectFromFile() {
    try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(
        Files.newInputStream(Paths.get(filePath))))) {
      Object obj = in.readObject();
      if (obj instanceof HashMap) {
        return (Map<String, Department>) obj;
      } else {
        throw new IllegalArgumentException("Invalid object type in file.");
      }
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return new HashMap<>();
    }
  }

  /**
   * Saves the contents of the internal data structure to the file.
   * Contents of the file are overwritten with this operation.
   */
  public void saveContentsToFile() {
    try (ObjectOutputStream out = new ObjectOutputStream(
        new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath))))) {
      out.writeObject(departmentMapping);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets the department mapping of the database.
   *
   * @return the department mapping
   */
  public Map<String, Department> getDepartmentMapping() {
    return this.departmentMapping;
  }

  /**
   * Returns a string representation of the database.
   *
   * @return a string representation of the database
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Department> entry : departmentMapping.entrySet()) {
      String key = entry.getKey();
      Department value = entry.getValue();
      result.append("For the ").append(key).append(" department: \n").append(value.toString());
    }
    return result.toString();
  }

}