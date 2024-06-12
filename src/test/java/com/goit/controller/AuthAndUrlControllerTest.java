package com.goit.controller;

import com.goit.auth.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class AuthAndUrlControllerTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtUtils jwtUtils;

    private String jwtToken;



    @BeforeAll
    public void setUp() {
        User dummy = new User("w@w.w", "Password8");
        dummy.setRoles(List.of(new Role("ROLE_USER")));
        jwtToken = jwtUtils.generateToken(dummy);
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
    public void testRegisterSuccess() throws Exception {
        String jsonRequest = "{ \"email\": \"w@w.w\", \"password\": \"Password8\" }";

        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @Order(2)
    public void testRegisterFailure() throws Exception {
        String jsonRequest = "{ \"email\": \"ww.w\", \"password\": \"pa\" }";

        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    @Order(3)
    public void testLoginFailure() throws Exception {
        String jsonRequest = "{ \"email\": \"w@w.w\", \"password\": \"password99999\" }";

        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    @Order(4)
    public void testLoginSuccess() throws Exception {
        String jsonRequest = "{ \"email\": \"w@w.w\", \"password\": \"Password8\" }";

        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    @Order(5)
    public void testCreateUrl() throws Exception
    {
        String jsonRequest = "{ \"longUrl\": \"http://epowhost.com\", \"expiryDate\": \"2025-06-11T14:32:42\" }";
        mvc.perform(post("/api/v1/urls/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + jwtToken))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.shortId").isNotEmpty());

        jsonRequest = "{ \"longUrl\": \"http://epowhost.com\", \"expiryDate\": \"2025-06-11\" }";
        mvc.perform(post("/api/v1/urls/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + jwtToken))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    @Order(6)
    public void testUrlList() throws Exception
    {
        String jsonRequest = "{ \"longUrl\": \"http://epowhost.com\", \"expiryDate\": \"2025-06-11T14:32:42\" }";
        mvc.perform(get("/api/v1/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].longUrl").isNotEmpty());
    }
}