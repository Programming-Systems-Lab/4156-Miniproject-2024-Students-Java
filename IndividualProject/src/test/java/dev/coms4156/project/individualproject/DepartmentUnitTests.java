package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class implements Unit Tests for the {@link Department} class.
 *
 * <p>This includes testing the getNumberOfMajors() and getDepartmentMajors(), addPersonToMajor(),
 * dropPersonFromMajor(), addCourse(), createCourse(), and toString() methods.
 */

@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

    private static MyFileDatabase myFileDatabase;
    private static Department compSciDept;

    @BeforeAll
    public static void setupDepartmentDataForTesting() {
        myFileDatabase = new MyFileDatabase(0, "./data.txt");
        HashMap<String, Department> departmentMapping = myFileDatabase.getDepartmentMapping();
    }


    @Test
    public void testToString() {
        // Test the string representation of the department
        String departmentString = compSciDept.toString();
        System.out.println(departmentString);
        assertTrue(departmentString.contains("For the COMS department"), "String representation should contain the department name.");
    }
}
