package net.breezeware.cosspringproject.order.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.Availability;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuAvailabilityMap;
import net.breezeware.cosspringproject.food.entity.FoodMenuFoodItemMap;
import net.breezeware.cosspringproject.food.service.api.AvailabilityService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuAvailabilityMapService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuFoodItemMapService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuService;
import net.breezeware.cosspringproject.order.dao.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
}