package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class IndividualProjectApplicationTest {

    private IndividualProjectApplication application;
    private MyFileDatabase mockDatabase;

    @BeforeEach
    public void setup() {
        application = new IndividualProjectApplication();
        mockDatabase = mock(MyFileDatabase.class);
        IndividualProjectApplication.overrideDatabase(mockDatabase);   
    }

    @Test
    public void testRunWithoutSetupArgument() {
        String[] args = {};
        application.run(args);

        verify(mockDatabase, never()).saveContentsToFile();   
    }

    @Test  
    public void testResetDataFile() {
        application.resetDataFile();
        verify(mockDatabase, times(1)).setMapping(anyMap()); // Adjust as necessary based on actual logic
    }

}
