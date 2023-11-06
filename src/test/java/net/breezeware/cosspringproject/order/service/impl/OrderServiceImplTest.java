package net.breezeware.cosspringproject.order.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.*;
import net.breezeware.cosspringproject.food.service.api.*;
import net.breezeware.cosspringproject.order.dao.OrderRepository;
import net.breezeware.cosspringproject.order.dto.FoodItemDto;
import net.breezeware.cosspringproject.order.dto.OrderDto;
import net.breezeware.cosspringproject.order.dto.OrderViewDto;
import net.breezeware.cosspringproject.order.dto.PlaceOrderDto;
import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.order.entity.OrderItem;
import net.breezeware.cosspringproject.order.service.api.OrderItemService;
import net.breezeware.cosspringproject.user.dao.UserAddressMapRepository;
import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.entity.UserAddressMap;
import net.breezeware.cosspringproject.user.service.api.UserAddressMapService;
import net.breezeware.cosspringproject.user.service.api.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    OrderRepository orderRepository;
    @Mock
    AvailabilityService availabilityService;
    @Mock
    FoodMenuAvailabilityMapService foodMenuAvailabilityMapService;
    @Mock
    FoodMenuService foodMenuService;
    @Mock
    FoodMenuFoodItemMapService foodMenuFoodItemMapService;
    @Mock
    UserService userService;
    @Mock
    UserAddressMapService userAddressMapService;
    @Mock
    FoodItemService foodItemService;
    @Mock
    OrderItemService orderItemService;
    @InjectMocks
    OrderServiceImpl orderService;
    @Mock
    Validator validator;

    @Test
    void testViewFoodMenus() {
        FoodMenu mockFoodMenu = FoodMenu.builder().id(1).name("Breakfast").type("Veg").build();
        Availability mockAvailability = Availability.builder().id(1).day("Monday").build();
        List<FoodMenuAvailabilityMap> mockListOfFoodMenuAvailabilityMap = List.of(FoodMenuAvailabilityMap.builder().id(1).foodMenu(mockFoodMenu).build(), FoodMenuAvailabilityMap.builder().id(2).foodMenu(mockFoodMenu).build());
        List<FoodMenuFoodItemMap> mockListOfFoodMenuFoodItemMap = List.of(FoodMenuFoodItemMap.builder().id(1).build(), FoodMenuFoodItemMap.builder().id(2).build());
        when(availabilityService.findByDay(any())).thenReturn(mockAvailability);
        when(foodMenuAvailabilityMapService.getFoodMenuAvailabilityMapByAvailability(any())).thenReturn(mockListOfFoodMenuAvailabilityMap);
        when(foodMenuService.findById(anyLong())).thenReturn(mockFoodMenu);
        when(foodMenuFoodItemMapService.getFoodMenuFoodItemMapByFoodMenu(any())).thenReturn(mockListOfFoodMenuFoodItemMap);
        when(foodMenuAvailabilityMapService.getFoodMenuAvailabilityMapByFoodMenu(any())).thenReturn(mockListOfFoodMenuAvailabilityMap);
        List<FoodMenuDto> foodMenuDtos = orderService.viewFoodMenus();
        Assertions.assertThat(foodMenuDtos).hasSize(2);
    }
    @Test
    void testCreateOrder(){
        Order mockOrder= new Order();
        mockOrder.setUser(User.builder().id(1).roleId(2).build());
        FoodItem mockFoodItem= FoodItem.builder().id(1).cost(30).quantity(30).build();
        OrderItem mockOrderItem= OrderItem.builder().id(1).quantity(2).cost(50).build();
        when(userService.isACustomer(any())).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(mockOrder);
        when(foodItemService.findById(anyLong())).thenReturn(mockFoodItem);
        when(orderItemService.createOrderItem(any())).thenReturn(mockOrderItem);
        when(orderRepository.save(any())).thenReturn(mockOrder);
        Order order=orderService.createOrder(OrderDto.builder().order(mockOrder).foodItemDtos(List.of(FoodItemDto.builder().foodItem(mockFoodItem).requiredQuantity(5).build())).build());
        assertEquals(mockOrder.getUser().getId(),order.getUser().getId());
        assertEquals(mockOrder.getUser().getRoleId(),order.getUser().getRoleId());
    }

    @Test
    void testViewOrderById(){
        Order mockOrder=new Order();
        mockOrder.setId(1);
        FoodItem mockFoodItem= FoodItem.builder().id(1).cost(20).quantity(20).build();
        List<OrderItem> mockOrderItems=List.of(OrderItem.builder().id(1).foodItem(mockFoodItem).quantity(10).cost(20).build());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        when(orderItemService.findByOrder(any())).thenReturn(mockOrderItems);
        when(foodItemService.findById(1L)).thenReturn(mockFoodItem);
        OrderViewDto orderViewDto = orderService.viewOrder(1L);
        assertEquals(orderViewDto.getFoodItems().size(),1);
        assertEquals(orderViewDto.getOrder().getId(),mockOrder.getId());
    }

    @Test
    void testUpdateOrder(){
        Order mockOrder=new Order();
        mockOrder.setId(1);
        FoodItem mockFoodItem= FoodItem.builder().id(1).cost(20).quantity(20).build();
        OrderItem mockOrderItem=OrderItem.builder().id(1).foodItem(mockFoodItem).quantity(10).cost(20).build();
        List<OrderItem> mockOrderItems=List.of(mockOrderItem);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        when(orderItemService.findByOrder(mockOrder)).thenReturn(mockOrderItems);
        when(orderItemService.createOrderItem(mockOrderItem)).thenReturn(mockOrderItem);
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);
        orderService.updateOrder(1,List.of(FoodItemDto.builder().foodItem(mockFoodItem).requiredQuantity(2).build()));
        verify(orderRepository).findById(1L);
    }
    @Test
    void testCreateAddress(){
        UserAddressMap mockAddress= UserAddressMap.builder().id(1).pincode(624001).build();
        when(userAddressMapService.save(any())).thenReturn(mockAddress);
        UserAddressMap address = orderService.createAddress(mockAddress);
        assertEquals(address.getId(),mockAddress.getId());
        assertEquals(address.getPincode(),mockAddress.getPincode());
    }
    @Test
    void testPlaceOrder(){
        Order mockOrder=new Order();
        mockOrder.setId(1);
        UserAddressMap mockAddress= UserAddressMap.builder().id(1).pincode(624001).build();
        PlaceOrderDto mockPlaceOrderDto=PlaceOrderDto.builder().userAddressMap(mockAddress).email("chand2ram@gmail.com").phoneNumber("9677963066").deliveryTime(Instant.parse("2023-11-06T12:00:00.000Z")).build();
        FoodItem mockFoodItem= FoodItem.builder().id(1).cost(20).quantity(20).build();
        List<OrderItem> mockOrderItems=List.of(OrderItem.builder().id(1).foodItem(mockFoodItem).quantity(10).cost(20).build());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        when(orderItemService.findByOrder(any())).thenReturn(mockOrderItems);
        when(foodItemService.findById(1L)).thenReturn(mockFoodItem);
        when(orderRepository.save(any())).thenReturn(mockOrder);
        OrderViewDto orderViewDto = orderService.placeOrder(1L,mockPlaceOrderDto);
        assertEquals(orderViewDto.getFoodItems().size(),1);
        assertEquals(orderViewDto.getOrder().getId(),mockOrder.getId());
    }
}