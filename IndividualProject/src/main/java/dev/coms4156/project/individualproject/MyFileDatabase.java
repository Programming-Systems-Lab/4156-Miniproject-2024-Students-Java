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


/**
 * This class represents a file-based database containing department mappings.
 */
public class MyFileDatabase {

  private static final Logger LOGGER = Logger.getLogger(MyFileDatabase.class.getName());

  /** The path to the file containing the database entries. */
  private String filePath;

  /** The mapping of department names to Department objects. */
  private HashMap<String, Department> departmentMapping;

  /**
   * Private constructor for MyFileDatabase.
   *
   * @param filePath the path to the file containing the entries of the database
   */
  private MyFileDatabase(String filePath) {
    this.filePath = filePath;
  }

  /**
   * Factory method for creating and initializing the MyFileDatabase object.
   *
   * @param flag     used to distinguish mode of database
   * @param filePath the path to the file containing the entries of the database
   * @return a new MyFileDatabase object
   */
  public static MyFileDatabase create(int flag, String filePath) {
    MyFileDatabase db = new MyFileDatabase(filePath);
    if (flag == 0) {
      db.departmentMapping = db.deSerializeObjectFromFile();
    }
    return db;
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
  protected HashMap<String, Department> deSerializeObjectFromFile() {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
      Object obj = in.readObject();
      if (obj instanceof HashMap) {
        return (HashMap<String, Department>) obj;
      } else {
        throw new IllegalArgumentException("Invalid object type in file.");
      }
    } catch (IOException | ClassNotFoundException e) {
      LOGGER.log(Level.SEVERE, "Failed to deserialize object from file: " + e.getMessage(), e);
      return new HashMap<>(); // Return an empty HashMap instead of null
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
      LOGGER.log(Level.SEVERE, "Failed to serialize object: " + e.getMessage(), e);
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
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Department> entry : departmentMapping.entrySet()) {
      String key = entry.getKey();
      Department value = entry.getValue();
      result.append("For the ").append(key).append(" department: \n").append(value.toString());
    }
    return result.toString();
  }
}
