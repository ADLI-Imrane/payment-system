package com.wrx.paymentsystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.wrx.paymentsystem.security.JwtService;

@ExtendWith(MockitoExtension.class)
public class JwtControllerTest {

    private MockMvc mockMvc;

    @Mock
    private JwtService jwtService;
    
    @InjectMocks
    private JwtController jwtController;

    private final String testUsername = "testUser";
    private final String testToken = "test.token.value";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(jwtController).build();
    }

    private UserDetails createTestUserDetails() {
        return User.builder()
                .username(testUsername)
                .password("password")
                .roles("USER")
                .build();
    }

    @Test
    public void generateToken_ShouldReturnToken() throws Exception {
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn(testToken);

        mockMvc.perform(post("/api/generate-token")
                .param("username", testUsername))
            .andExpect(status().isOk())
            .andExpect(content().string(testToken));
    }

    @Test
    public void validateToken_ShouldReturnTrueForValidToken() throws Exception {
        UserDetails userDetails = createTestUserDetails();
        when(jwtService.isTokenValid(testToken, userDetails)).thenReturn(true);

        mockMvc.perform(get("/api/validate-token")
                .param("token", testToken)
                .param("username", testUsername))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }

    @Test
    public void validateToken_ShouldReturnFalseForInvalidToken() throws Exception {
        UserDetails userDetails = createTestUserDetails();
        when(jwtService.isTokenValid("invalid.token", userDetails)).thenReturn(false);

        mockMvc.perform(get("/api/validate-token")
                .param("token", "invalid.token")
                .param("username", testUsername))
            .andExpect(status().isOk())
            .andExpect(content().string("false"));
    }
}