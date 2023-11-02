package net.breezeware.cosspringproject.order.controller;

import net.breezeware.cosspringproject.exception.ExceptionHandling;
import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.order.dto.OrderDto;
import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.order.service.api.OrderService;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    OrderService orderService;
    @InjectMocks
    OrderController orderController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(new ExceptionHandling())
                .build();
    }

    @Test
    void testViewFoodMenus() throws Exception {
        Mockito.when(orderService.viewFoodMenus()).thenReturn(List.of(new FoodMenuDto(), new FoodMenuDto()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/foodMenus"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testCreateOrder()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "order":{
                                        "user":{
                                            "id":6,
                                            "name":"Seenu",
                                            "userName":"seenu_06",
                                            "password":"breeze123"
                                        }
                                    },
                                    "foodItemDtos":[
                                        {
                                            "foodItem":{
                                            "id":1,
                                            "name":"Dosa",
                                            "cost":20,
                                            "quantity":30
                                            },
                                        "requiredQuantity":3
                                        },
                                        {
                                            "foodItem":{
                                            "id":2,
                                            "name":"Idly",
                                            "cost":10,
                                            "quantity":50
                                            },
                                        "requiredQuantity":5
                                        }
                                    ]
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}