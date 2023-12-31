package net.breezeware.cosspringproject.order.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.Availability;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuAvailabilityMap;
import net.breezeware.cosspringproject.food.entity.FoodMenuFoodItemMap;
import net.breezeware.cosspringproject.food.service.api.AvailabilityService;
import net.breezeware.cosspringproject.food.service.api.FoodItemService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuAvailabilityMapService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuFoodItemMapService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuService;
import net.breezeware.cosspringproject.order.dao.OrderRepository;
import net.breezeware.cosspringproject.order.dto.FoodItemDto;
import net.breezeware.cosspringproject.order.dto.OrderDto;
import net.breezeware.cosspringproject.order.dto.OrderViewDto;
import net.breezeware.cosspringproject.order.dto.PlaceOrderDto;
import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.order.entity.OrderItem;
import net.breezeware.cosspringproject.order.enumeration.Status;
import net.breezeware.cosspringproject.order.service.api.OrderItemService;
import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.entity.UserAddressMap;
import net.breezeware.cosspringproject.user.service.api.UserAddressMapService;
import net.breezeware.cosspringproject.user.service.api.UserService;

import lombok.extern.slf4j.Slf4j;

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
        List<FoodMenuAvailabilityMap> mockListOfFoodMenuAvailabilityMap =
                List.of(FoodMenuAvailabilityMap.builder().id(1).foodMenu(mockFoodMenu).build(),
                        FoodMenuAvailabilityMap.builder().id(2).foodMenu(mockFoodMenu).build());
        List<FoodMenuFoodItemMap> mockListOfFoodMenuFoodItemMap =
                List.of(FoodMenuFoodItemMap.builder().id(1).build(), FoodMenuFoodItemMap.builder().id(2).build());
        when(availabilityService.findAvailabilityByDay(any())).thenReturn(mockAvailability);
        when(foodMenuAvailabilityMapService.getFoodMenuAvailabilityMapByAvailability(any()))
                .thenReturn(mockListOfFoodMenuAvailabilityMap);
        when(foodMenuService.findFoodMenuById(anyLong())).thenReturn(mockFoodMenu);
        when(foodMenuFoodItemMapService.getFoodMenuFoodItemMapByFoodMenu(any()))
                .thenReturn(mockListOfFoodMenuFoodItemMap);
        when(foodMenuAvailabilityMapService.getFoodMenuAvailabilityMapByFoodMenu(any()))
                .thenReturn(mockListOfFoodMenuAvailabilityMap);
        List<FoodMenuDto> foodMenuDtos = orderService.retrieveAvailableFoodMenusForToday();
        Assertions.assertThat(foodMenuDtos).hasSize(2);
    }

    @Test
    void testCreateOrder() {
        Order mockOrder = new Order();
        mockOrder.setUser(User.builder().id(1).roleId(2).build());
        FoodItem mockFoodItem = FoodItem.builder().id(1).cost(30).quantity(30).build();
        OrderItem mockOrderItem = OrderItem.builder().id(1).quantity(2).cost(50).build();
        when(userService.isCustomer(any())).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(mockOrder);
        when(orderRepository.save(any())).thenReturn(mockOrder);
        Order order = orderService.createOrder(OrderDto.builder().order(mockOrder)
                .foodItemDtos(List.of(FoodItemDto.builder().foodItem(mockFoodItem).requiredQuantity(5).build()))
                .build());
        assertEquals(mockOrder.getUser().getId(), order.getUser().getId());
        assertEquals(mockOrder.getUser().getRoleId(), order.getUser().getRoleId());
    }

    @Test
    void testViewOrderById() {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        FoodItem mockFoodItem = FoodItem.builder().id(1).cost(20).quantity(20).build();
        List<OrderItem> mockOrderItems =
                List.of(OrderItem.builder().id(1).foodItem(mockFoodItem).quantity(10).cost(20).build());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        when(orderItemService.findByOrder(any())).thenReturn(mockOrderItems);
        when(foodItemService.findFoodItemById(1L)).thenReturn(mockFoodItem);
        OrderViewDto orderViewDto = orderService.viewOrder(1L);
        assertEquals(orderViewDto.getFoodItems().size(), 1);
        assertEquals(orderViewDto.getOrder().getId(), mockOrder.getId());
    }

    @Test
    void testUpdateOrder() {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        FoodItem mockFoodItem = FoodItem.builder().id(1).cost(20).quantity(20).createdOn(Instant.now())
                .modifiedOn(Instant.now()).build();
        OrderItem mockOrderItem =
                OrderItem.builder().id(1).order(mockOrder).foodItem(mockFoodItem).quantity(10).cost(20).build();
        List<OrderItem> mockOrderItems = new ArrayList<>();
        mockOrderItems.add(mockOrderItem);
        List<OrderItem> mockNotNeededOrderItem = new ArrayList<>();
        mockNotNeededOrderItem.add(mockOrderItem);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        when(orderItemService.findByOrder(mockOrder)).thenReturn(mockOrderItems);
        mockOrderItems.removeAll(mockNotNeededOrderItem);
        when(foodItemService.findFoodItemById(1L)).thenReturn(mockFoodItem);
        doReturn(mockOrderItem).when(orderItemService).createOrderItem(any(OrderItem.class));
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);
        orderService.updateOrder(1, List.of(FoodItemDto.builder().foodItem(mockFoodItem).requiredQuantity(2).build()));
        verify(orderRepository).findById(1L);
    }

    @Test
    void testCreateAddress() {
        User mockUser = User.builder().id(1).name("Ram").userName("ram_06").password("breeze123").roleId(1).build();
        UserAddressMap mockAddress = UserAddressMap.builder().id(1).user(mockUser).pincode(624001).build();
        when(userAddressMapService.save(any())).thenReturn(mockAddress);
        when(userService.isCustomer(mockUser.getId())).thenReturn(true);
        UserAddressMap address = orderService.createAddress(mockAddress);
        assertEquals(address.getId(), mockAddress.getId());
        assertEquals(address.getPincode(), mockAddress.getPincode());
    }

    @Test
    void testPlaceOrder() {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        UserAddressMap mockAddress = UserAddressMap.builder().id(1).pincode(624001).build();
        PlaceOrderDto mockPlaceOrderDto =
                PlaceOrderDto.builder().userAddressMap(mockAddress).email("chand2ram@gmail.com")
                        .phoneNumber("9677963066").deliveryTime(Instant.parse("2023-11-06T12:00:00.000Z")).build();
        FoodItem mockFoodItem = FoodItem.builder().id(1).cost(20).quantity(20).build();
        List<OrderItem> mockOrderItems =
                List.of(OrderItem.builder().id(1).foodItem(mockFoodItem).quantity(10).cost(20).build());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        when(orderItemService.findByOrder(any())).thenReturn(mockOrderItems);
        when(foodItemService.findFoodItemById(1L)).thenReturn(mockFoodItem);
        when(orderRepository.save(any())).thenReturn(mockOrder);
        orderService.placeOrder(1L, mockPlaceOrderDto);
        verify(orderRepository).findById(1L);
        verify(foodItemService).findFoodItemById(1L);
        verify(orderItemService).findByOrder(any());
    }

    @Test
    void testCancelOrder() {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        FoodItem mockFoodItem = FoodItem.builder().id(1).cost(20).quantity(20).build();
        List<OrderItem> mockOrderItems =
                List.of(OrderItem.builder().id(1).foodItem(mockFoodItem).quantity(10).cost(20).build());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        when(foodItemService.findFoodItemById(1L)).thenReturn(mockFoodItem);
        when(orderItemService.findByOrder(any())).thenReturn(mockOrderItems);
        orderService.cancelOrder(1L);
        verify(orderRepository).findById(1L);
        verify(foodItemService).findFoodItemById(1L);
        verify(orderItemService).findByOrder(any());
    }

    @Test
    void testViewActiveOrders() {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        List<Order> mockOrders = List.of(mockOrder);
        when(orderRepository.getOrderByStatus(any())).thenReturn(mockOrders);
        when(userService.isCafeteriaStaff(1L)).thenReturn(true);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        List<OrderViewDto> orderViewDtos = orderService.viewActiveOrders(1L);
        Assertions.assertThat(orderViewDtos).hasSize(1);
    }

    @Test
    void testViewReceivedOrder() {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        when(userService.isCafeteriaStaff(1L)).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(mockOrder);
        OrderViewDto orderViewDto = orderService.viewReceivedOrder(1L, 1L);
        assertEquals(mockOrder.getId(), orderViewDto.getOrder().getId());
    }

    @Test
    void testChangeStatusToWaitingForDelivery() {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(any())).thenReturn(mockOrder);
        when(userService.isCafeteriaStaff(1L)).thenReturn(true);
        orderService.changeStatusToWaitingForDelivery(1L, 1L);
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any());
    }

    @Test
    void testChangeStatusToPendingDelivery() {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(any())).thenReturn(mockOrder);
        when(userService.isCafeteriaStaff(1L)).thenReturn(true);
        orderService.changeStatusToPendingDelivery(1L, 1L);
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any());
    }

    @Test
    void testChangeStatusToOrderDelivered() {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(any())).thenReturn(mockOrder);
        when(userService.isDeliveryStaff(1L)).thenReturn(true);
        orderService.changeStatusToOrderDelivered(1L, 1L);
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any());
    }

    @Test
    void testViewCancelledOrders() {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        List<Order> mockOrders = List.of(mockOrder);
        when(orderRepository.getOrderByStatus(any())).thenReturn(mockOrders);
        when(userService.isDeliveryStaff(1L)).thenReturn(true);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        List<OrderViewDto> orderViewDtos = orderService.viewCancelledOrders(1L);
        Assertions.assertThat(orderViewDtos).hasSize(1);
    }

    @Test
    void testViewCancelledOrder() {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        mockOrder.setStatus(Status.ORDER_CANCELLED.name());
        when(userService.isDeliveryStaff(1L)).thenReturn(true);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        orderService.viewCancelledOrder(1L, 1L);
        verify(orderRepository, Mockito.times(2)).findById(1L);
    }

    @Test
    void testViewCompletedOrders() {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        List<Order> mockOrders = List.of(mockOrder);
        when(orderRepository.getOrderByStatus(any())).thenReturn(mockOrders);
        when(userService.isDeliveryStaff(1L)).thenReturn(true);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        List<OrderViewDto> orderViewDtos = orderService.viewCompletedOrders(1L);
        Assertions.assertThat(orderViewDtos).hasSize(1);
    }

    @Test
    void testViewCompletedOrder() {
        Order mockOrder = new Order();
        mockOrder.setId(1);
        mockOrder.setStatus(Status.ORDER_DELIVERED.name());
        when(userService.isDeliveryStaff(1L)).thenReturn(true);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        orderService.viewCompletedOrder(1L, 1L);
        verify(orderRepository, Mockito.times(2)).findById(1L);
    }
}