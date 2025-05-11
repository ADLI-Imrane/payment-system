package com.wrx.paymentsystem.controller;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;   // << THIS ONE
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wrx.paymentsystem.security.JwtService;

@WebMvcTest(JwtController.class)
@Import(JwtControllerTest.TestConfig.class)  // import custom config
public class JwtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;  // no mockbean, simple Autowired

    @TestConfiguration
    static class TestConfig {
        @Bean
        public JwtService jwtService() {
            return org.mockito.Mockito.mock(JwtService.class);  // manually return a mock
        }
    }

    @Test
    public void testGenerateToken() throws Exception {
        String username = "testUser";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";

        when(jwtService.generateToken(username)).thenReturn(token);

        mockMvc.perform(post("/api/generate-token")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(content().string(token));
    }

    @Test
    public void testValidateToken() throws Exception {
        String username = "testUser";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";

        when(jwtService.validateToken(token, username)).thenReturn(true);

        mockMvc.perform(get("/api/validate-token")
                        .param("token", token)
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
