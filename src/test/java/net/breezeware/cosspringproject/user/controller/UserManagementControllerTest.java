package net.breezeware.cosspringproject.user.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.exception.ExceptionHandling;
import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.service.api.UserService;

@ExtendWith(MockitoExtension.class)
class UserManagementControllerTest {
    @Mock
    UserService userService;
    @InjectMocks
    UserManagementController userManagementController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userManagementController).setControllerAdvice(new ExceptionHandling())
                .build();
    }

    @Test
    void testGetUsers() throws Exception {
        Mockito.when(userService.findAll()).thenReturn(List.of(new User(), new User()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetUserById() throws Exception {
        Mockito.when(userService.findById(any()))
                .thenReturn(User.builder().id(1).userName("sathish_01").name("Sathish").roleId(1).build());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", 1)).andExpectAll(jsonPath("$.id").value(1),
                jsonPath("$.name").value("Sathish"), jsonPath("$.userName").value("sathish_01"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testRetrieveUserByIdWithUnknownId() throws Exception {
        Mockito.when(userService.findById(anyLong()))
                .thenThrow(new CustomException("Unknown Id", HttpStatus.BAD_REQUEST));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", -2))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testSaveUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users").contentType(MediaType.APPLICATION_JSON).content(
                "{\"name\":\"Sathiesh\",\"userName\":\"sathish_01\",\"password\":\"breeze123\",\"roleId\":\"1\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testUpdateUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/users/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(
                        "{\"name\":\"Sathiesh\",\"userName\":\"sathish_01\",\"password\":\"breeze123\",\"roleId\":\"1\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}