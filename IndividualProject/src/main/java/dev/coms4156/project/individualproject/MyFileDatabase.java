package dev.coms4156.project.individualproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 * This class represents a file-based database containing department mappings.
 */
// @Service  // Mark it as a Spring-managed service
public class MyFileDatabase {

  private static final Logger LOGGER = Logger.getLogger(MyFileDatabase.class.getName());

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
  public Map<String, Department> deSerializeObjectFromFile() {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
      Object obj = in.readObject();
      if (obj instanceof Map) {
        return (Map<String, Department>) obj;
      } else {
        throw new IllegalArgumentException("Invalid object type in file.");
      }
    } catch (IOException | ClassNotFoundException e) {
      LOGGER.log(Level.SEVERE, "An error occurred", e);
      return null;
    }
  }

  /**
   * Saves the contents of the internal data structure to the file. Contents of the file are
   * overwritten with this operation.
   */
  public void saveContentsToFile() {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
      out.writeObject(departmentMapping);
      LOGGER.info("Object serialized successfully.");
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "An error occurred", e);
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

  /** The path to the file containing the database entries. */
  private String filePath;

  /** The mapping of department names to Department objects. */
  private Map<String, Department> departmentMapping;
}
