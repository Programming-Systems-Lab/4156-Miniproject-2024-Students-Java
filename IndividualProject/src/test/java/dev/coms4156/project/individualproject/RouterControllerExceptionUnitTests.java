// package dev.coms4156.project.individualproject;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import static org.mockito.Mockito.doThrow;
// import static org.mockito.Mockito.when;

// @ExtendWith(MockitoExtension.class)
// @SpringJUnitConfig
// @WebMvcTest(RouteController.class)  // This will only load the web layer
// public class RouterControllerExceptionUnitTests {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private MyFileDatabase myFileDatabase;

//     // Mock the IndividualProjectApplication or methods if needed
//     @MockBean
//     private IndividualProjectApplication individualProjectApplication;

//     //exception
//     @Test
//     public void testException() throws Exception {
//         when(myFileDatabase.getDepartmentMapping()).
//        thenThrow(new RuntimeException("Database error"));
//         mockMvc.perform(patch("/changeCourseLocation").param("deptCode", "nonExisting")
//          .param("courseCode", "2500ddd").param("location", "633 MUDD"))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("An Error has occurred"));
//     }

// }
