package com.goit.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class IndexControllerTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(0)
    public void testActiveProfiles() {
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        System.out.println("Active profiles: " + String.join(", ", activeProfiles));
        assertEquals("test", activeProfiles[0]);
    }

    @Test
    @Order(1)
    public void testRootEndpointToSwagger() throws Exception {

        mvc.perform(get(""))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("swagger-ui/index.html"));
    }

    @Test
    @Order(2)
    public void testRootEndpointToLongUrlNotFound() throws Exception {

        mvc.perform(get("/XXX"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}