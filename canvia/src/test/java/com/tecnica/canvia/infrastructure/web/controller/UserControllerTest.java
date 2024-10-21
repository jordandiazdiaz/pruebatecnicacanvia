package com.tecnica.canvia.infrastructure.web.controller;

import com.tecnica.canvia.application.dto.UserDto;
import com.tecnica.canvia.application.service.UserApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

  private MockMvc mockMvc;

  @Mock
  private UserApplicationService userApplicationService;

  @InjectMocks
  private UserController userController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  void createUser_Success() throws Exception {
    UserDto userDto = new UserDto();
    userDto.setUsername("testuser");
    userDto.setEmail("test@example.com");

    when(userApplicationService.createUser(any(UserDto.class))).thenReturn(userDto);

    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"password\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("testuser"))
        .andExpect(jsonPath("$.email").value("test@example.com"));

    verify(userApplicationService).createUser(any(UserDto.class));
  }

  @Test
  void getUser_Success() throws Exception {
    UserDto userDto = new UserDto();
    userDto.setId(1L);
    userDto.setUsername("testuser");

    when(userApplicationService.getUserById(1L)).thenReturn(userDto);

    mockMvc.perform(get("/api/users/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.username").value("testuser"));

    verify(userApplicationService).getUserById(1L);
  }

}
