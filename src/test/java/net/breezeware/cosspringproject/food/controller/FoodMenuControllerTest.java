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
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.service.api.FoodMenuService;

@ExtendWith(MockitoExtension.class)
class FoodMenuControllerTest {
    @Mock
    FoodMenuService foodMenuService;
    @InjectMocks
    FoodMenuController foodMenuController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodMenuController).setControllerAdvice(new ExceptionHandling())
                .build();
    }

    @Test
    void testFindAllFoodMenus() throws Exception {
        Mockito.when(foodMenuService.findAllFoodMenu()).thenReturn(List.of(new FoodMenu(), new FoodMenu()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/food-menus")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testFindFoodMenuById() throws Exception {
        FoodMenu mockFoodMenu = FoodMenu.builder().id(1).name("Breakfast").type("Veg").build();
        Mockito.when(foodMenuService.findFoodMenuById(anyLong())).thenReturn(mockFoodMenu);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/food-menus/{id}", 1)).andExpectAll(jsonPath("$.id").value(1),
                jsonPath("$.name").value("Breakfast"), jsonPath("$.type").value("Veg"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testSaveFoodMenu() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/food-menus").contentType(MediaType.APPLICATION_JSON).content("""
                        {"foodMenu": {"name":"Breakfast","type":"Veg"},
                        "foodItems": [
                                {
                                    "id": 1,
                                    "name": "Dosa",
                                    "cost": 20,
                                    "quantity": 30
                                }
                            ],
                        "availabilityList": [
                                {
                                    "id": 1,
                                    "day": "Monday"
                                },
                                {
                                    "id": 2,
                                    "day": "Tuesday"
                                }
                            ]}""")).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testUpdateFoodMenu() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/food-menus/{id}", 1).contentType(MediaType.APPLICATION_JSON).content("""
                        {"foodMenu": {},
                        "foodItems": [
                                {
                                    "id": 2,
                                    "name": "Idly",
                                    "cost": 10,
                                    "quantity": 50
                                }
                            ],
                        "availabilityList": [
                                {
                                    "id": 3,
                                    "day": "Wednesday"
                                },
                                {
                                    "id": 4,
                                    "day": "Thrusday"
                                }
                            ]}""")).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteFoodMenuById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/food-menus/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}