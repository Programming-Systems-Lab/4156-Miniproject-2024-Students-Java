package dev.coms4156.project.individualproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class represents a file-based database containing department mappings.
 */
public class MyFileDatabase {
  Logger log = Logger.getLogger(MyFileDatabase.class.getName());
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
  public void setMapping(HashMap<String, Department> mapping) {
    this.departmentMapping = mapping;
  }

  /**
   * Deserializes the object from the file and returns the department mapping.
   *
   * @return the deserialized department mapping
   */
  public final HashMap<String, Department> deSerializeObjectFromFile() {
    try (InputStream is = Files.newInputStream(Paths.get(filePath));
         ObjectInputStream in = new ObjectInputStream(is)) {
      Object obj = in.readObject();
      if (obj instanceof HashMap) {
        return (HashMap<String, Department>) obj;
      } else {
        throw new IllegalArgumentException("Invalid object type in file.");
      }
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return new HashMap<>();
    }
  }

  /**
   * Saves the contents of the internal data structure to the file. Contents of the file are
   * overwritten with this operation.
   */
  public void saveContentsToFile() {
    try (OutputStream os = Files.newOutputStream(Paths.get(filePath));
         ObjectOutputStream out = new ObjectOutputStream(os)) {
      out.writeObject(departmentMapping);
      log.fine("Object serialized successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets the department mapping of the database.
   *
   * @return the department mapping
   */
  public HashMap<String, Department> getDepartmentMapping() {
    return this.departmentMapping;
  }

  /**
   * Returns a string representation of the database.
   *
   * @return a string representation of the database
   */
  @Override
  public String toString() {
    int estimatedCapacity = departmentMapping.size() * 50;
    StringBuilder result = new StringBuilder(estimatedCapacity);
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
  private HashMap<String, Department> departmentMapping;
}
