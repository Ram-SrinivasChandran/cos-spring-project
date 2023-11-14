package net.breezeware.cosspringproject.food.controller;

import static org.hamcrest.Matchers.hasSize;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import net.breezeware.cosspringproject.exception.ExceptionHandling;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.service.api.FoodItemService;

@ExtendWith(MockitoExtension.class)
class FoodItemControllerTest {
    @Mock
    FoodItemService foodItemService;
    @InjectMocks
    FoodItemController foodItemController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodItemController).setControllerAdvice(new ExceptionHandling())
                .build();
    }

    @Test
    void testFindAllFoodItems() throws Exception {
        Mockito.when(foodItemService.findAllFoodItems(1L)).thenReturn(List.of(new FoodItem(), new FoodItem()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/food-items?user-id=1")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testFindFoodItemById() throws Exception {
        FoodItem mockfoodItem = FoodItem.builder().id(1).cost(20).quantity(10).build();
        Mockito.when(foodItemService.findFoodItemById(anyLong())).thenReturn(mockfoodItem);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/food-items/{id}", 1))
                .andExpectAll(jsonPath("$.id").value(1), jsonPath("$.cost")
                        .value(20), jsonPath("$.quantity").value(10))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testSaveFoodItem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/food-items").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Idly\",\"cost\":\"25\",\"quantity\":\"20\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testUpdateFoodItem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/food-items/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Idly\",\"cost\":\"25\",\"quantity\":\"20\"}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteFoodItemById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/food-items/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}