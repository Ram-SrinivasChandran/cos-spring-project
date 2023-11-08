package net.breezeware.cosspringproject.order.controller;

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
import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.order.dto.OrderViewDto;
import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.order.enumeration.Status;
import net.breezeware.cosspringproject.order.service.api.OrderService;
import net.breezeware.cosspringproject.user.entity.User;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    OrderService orderService;
    @InjectMocks
    OrderController orderController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).setControllerAdvice(new ExceptionHandling()).build();
    }

    @Test
    void testViewFoodMenus() throws Exception {
        Mockito.when(orderService.viewFoodMenus()).thenReturn(List.of(new FoodMenuDto(), new FoodMenuDto()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/foodMenus"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testCreateOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders").contentType(MediaType.APPLICATION_JSON).content("""
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
                }""")).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testViewOrderById() throws Exception {
        Order mockOrder = new Order();
        mockOrder.setUser(User.builder().id(1).roleId(2).build());
        mockOrder.setStatus(Status.IN_CART.name());
        OrderViewDto orderViewDto = new OrderViewDto();
        orderViewDto.setOrder(mockOrder);
        Mockito.when(orderService.viewOrder(anyLong())).thenReturn(orderViewDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/{id}", 1))
                .andExpectAll(jsonPath("$.order").value(mockOrder)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateOrder() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/orders/{id}", 1).contentType(MediaType.APPLICATION_JSON).content("""
                        [
                            {
                                "foodItem": {
                                    "id": 1,
                                    "name": "Dosa",
                                    "cost": 20,
                                    "quantity": 30
                                },
                                "requiredQuantity": 4
                            },
                            {
                                "foodItem": {
                                    "id": 3,
                                    "name": "Poori",
                                    "cost": 30,
                                    "quantity": 20
                                },
                                "requiredQuantity": 5
                            },
                            {
                                "foodItem": {
                                    "id": 4,
                                    "name": "Pongal",
                                    "cost": 25,
                                    "quantity": 10
                                },
                                "requiredQuantity": 5
                            }
                        ]
                        """)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCreateAddress() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/orders/address").contentType(MediaType.APPLICATION_JSON).content("""
                        {
                            "user": {
                                "id": 6,
                                "name": "Seenu",
                                "userName": "seenu_06",
                                "password": "breeze123"
                            },
                            "doorNumber": 123,
                            "streetName": "Thirumal Nagar",
                            "city": "Dindigul",
                            "district": "Dindigul",
                            "state": "TamilNadu",
                            "pincode": 624001,
                            "landmark": "Ayyapan Kovil"
                        }""")).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testPlaceOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/placeOrder/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON).content("""
                        {
                        "userAddressMap": {
                                "id": 7,
                                "user": {
                                    "id": 6,
                                    "name": "Seenu",
                                    "userName": "seenu_06",
                                    "password": "breeze123",
                                    "createdOn": null,
                                    "modifiedOn": null,
                                    "roleId": 0
                                },
                                "doorNumber": "123",
                                "streetName": "Thirumal Nagar",
                                "city": "Dindigul",
                                "district": "Dindigul",
                                "state": "TamilNadu",
                                "pincode": 624001,
                                "landmark": "Ayyapan Kovil",
                                "createdOn": "2023-11-06T09:10:39.739795690Z",
                                "modifiedOn": "2023-11-06T09:10:39.739798606Z"
                            },
                            "email": "chand2ram@gmail.com",
                            "phoneNumber": "9677963066",
                            "deliveryTime": "2023-11-06T12:00:00.000Z"
                        }
                        """)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCancelOrder() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/orders/cancelOrder/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testViewActiveOrders() throws Exception {
        Mockito.when(orderService.viewActiveOrders(1L)).thenReturn(List.of(new OrderViewDto(), new OrderViewDto()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/activeOrders?user-id=1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testViewReceivedOrder() throws Exception {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        OrderViewDto mockOrderViewDto = OrderViewDto.builder().order(mockOrder).build();
        Mockito.when(orderService.viewReceivedOrder(1L, 1L)).thenReturn(mockOrderViewDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/receivedOrder/{id}?user-id=1", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testChangeOrderStatusToOrderPrepared() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/orderPrepared/{id}?user-id=1", 1)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testChangeOrderStatusToPendingDelivery() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/pendingDelivery/{id}?user-id=1", 1)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testChangeOrderStatusToOrderDelivered() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/orderDelivered/{id}?user-id=1", 1)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testViewCancelledOrders() throws Exception {
        Mockito.when(orderService.viewCancelledOrders(1L)).thenReturn(List.of(new OrderViewDto(), new OrderViewDto()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/cancelledOrders?user-id=1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testViewCancelledOrder() throws Exception {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        OrderViewDto mockOrderViewDto = OrderViewDto.builder().order(mockOrder).build();
        Mockito.when(orderService.viewCancelledOrder(1L, 1L)).thenReturn(mockOrderViewDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/cancelledOrder/{id}?user-id=1", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testViewCompletedOrders() throws Exception {
        Mockito.when(orderService.viewCompletedOrders(1L)).thenReturn(List.of(new OrderViewDto(), new OrderViewDto()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/completedOrders?user-id=1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testViewCompletedOrder() throws Exception {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        OrderViewDto mockOrderViewDto = OrderViewDto.builder().order(mockOrder).build();
        Mockito.when(orderService.viewCompletedOrder(1L, 1L)).thenReturn(mockOrderViewDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/completedOrder/{id}?user-id=1", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}