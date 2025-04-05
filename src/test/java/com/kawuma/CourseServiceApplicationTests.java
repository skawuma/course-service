package com.kawuma;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kawuma.controller.CourseController;


import com.kawuma.dto.CourseRequestDTO;
import com.kawuma.dto.CourseResponseDTO;
import com.kawuma.entity.CourseEntity;
import com.kawuma.util.AppUtils;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class CourseServiceApplicationTests {


    @Autowired
    private MockMvc mockMvc;


    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0.30");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        mySQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mySQLContainer.stop();
    }

    @Before()
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(CourseController.class)
                .build();
    }


    @Test
    public void addNewCourseTest() throws Exception {
        //build request body
        CourseRequestDTO course = CourseRequestDTO.builder()
                .name("test-course")
                .emailId("skawumadev@gmail.com")
                .contact("9783982410")
                .trainerName("test-trainer")
                .duration("9 month")
                .startDate(new java.util.Date())
                .courseType("LIVE")
                .fees(2000.00)
                .isCertifiedAvailable(true)
                .description("Computer Science")
                .build();
        //call controller endpoints
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/course/add")
                        .contentType("application/json")
                        .content(Objects.requireNonNull(AppUtils.convertObjectToJson(course)))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.courseId").exists());
//
    }


    @Test
    public void getAllTheCoursesTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/course")
                        .accept("application/json")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].courseId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].courseId").exists());

    }

//	private String asJsonString(Object object) {
//		try {
//			return new ObjectMapper().writeValueAsString(object);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
