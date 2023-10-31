package net.breezeware.cosspringproject.food.controller;

import net.breezeware.cosspringproject.exception.ExceptionHandling;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.service.api.FoodItemService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@ExtendWith(MockitoExtension.class)
class FoodMenuControllerTest {
    @Mock
    FoodMenuService foodMenuService;
    @InjectMocks
    FoodMenuController foodMenuController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodMenuController)
                .setControllerAdvice(new ExceptionHandling())
                .build();
    }

    @Test
    void testFindAllFoodMenus() throws Exception {
        Mockito.when(foodMenuService.findAll()).thenReturn(List.of(new FoodMenu(), new FoodMenu()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/foodMenus"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testFindFoodMenuById() throws Exception {
        FoodMenu mockFoodMenu= FoodMenu.builder().id(1).name("Breakfast").type("Veg").build();
        Mockito.when(foodMenuService.findById(anyLong())).thenReturn(mockFoodMenu);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/foodMenus/{id}",1))
                .andExpectAll(
                        jsonPath("$.id").value(1),
                        jsonPath("$.name").value("Breakfast"),
                        jsonPath("$.type").value("Veg"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}