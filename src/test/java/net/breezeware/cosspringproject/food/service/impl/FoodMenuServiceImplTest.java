package net.breezeware.cosspringproject.food.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.food.dao.FoodMenuRepository;
import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.*;
import net.breezeware.cosspringproject.food.enumeration.Days;
import net.breezeware.cosspringproject.food.service.api.AvailabilityService;
import net.breezeware.cosspringproject.food.service.api.FoodItemService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuAvailabilityMapService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuFoodItemMapService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class FoodMenuServiceImplTest {
    @Mock
    FoodItemService foodItemService;
    @Mock
    FoodMenuRepository foodMenuRepository;
    @Mock
    AvailabilityService availabilityService;
    @Mock
    FoodMenuAvailabilityMapService foodMenuAvailabilityMapService;
    @Mock
    FoodMenuFoodItemMapService foodMenuFoodItemMapService;
    @InjectMocks
    FoodMenuServiceImpl foodMenuService;
    @Mock
    Validator validator;

    @Test
    void testViewFoodMenus() {
        List<FoodMenu> mockFoodMenus=List.of(new FoodMenu(),new FoodMenu());
        when(foodMenuRepository.findAll()).thenReturn(mockFoodMenus);
        List<FoodMenu> foodMenus=foodMenuService.findAll();
        Assertions.assertThat(foodMenus).hasSize(mockFoodMenus.size());
        verify(foodMenuRepository).findAll();
    }

    @Test
    void testViewFoodMenuById() {
        FoodMenu mockFoodMenu= FoodMenu.builder().id(1).build();
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockFoodMenu));
        FoodMenu foodMenu = foodMenuService.findById(1L);
        Assertions.assertThat(foodMenu).hasFieldOrProperty("type");
        assert mockFoodMenu != null;
        assertEquals(foodMenu.getId(), mockFoodMenu.getId());
        Mockito.verify(foodMenuRepository).findById(anyLong());
    }
    @Test
    void testViewFoodMenuByIdInvalidUserIdThrowCustomException() {
        CustomException exception =
                assertThrows(CustomException.class, () -> foodMenuService.findById(-1L));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("The Food Menu Id should be Greater than Zero.", exception.getMessage());
    }
    @Test
    void testViewFoodMenuByIdInvalidUserThrowCustomException() {
        CustomException exception =
                assertThrows(CustomException.class, () -> foodMenuService.findById(2L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("The Food Menu not Found", exception.getMessage());
    }
    @Test
    void testCreateFoodMenu(){
        List<FoodItem> mockFoodItems=List.of(FoodItem.builder().id(1).cost(20).quantity(10).build(), FoodItem.builder().id(2).cost(30).quantity(20).build());
        FoodMenu mockFoodMenu= FoodMenu.builder().id(1).type("Veg").name("Breakfast").build();
        Availability mockAvailability=Availability.builder().id(1).day(Days.MONDAY.name).build();
        FoodMenuFoodItemMap mockFoodMenuFoodItemMap= FoodMenuFoodItemMap.builder().id(1).build();
        FoodMenuAvailabilityMap mockFoodMenuAvailabilityMap= FoodMenuAvailabilityMap.builder().id(1).build();
        FoodItem mockFoodItem=FoodItem.builder().id(1).cost(20).quantity(10).build();
        List<Availability> mockAvailabilityList=List.of(Availability.builder().id(1).day(Days.MONDAY.name).build(), Availability.builder().id(2).day("Tuesday").build());
        FoodMenuDto menuDto=FoodMenuDto.builder().foodItems(mockFoodItems).foodMenu(mockFoodMenu).availabilityList(mockAvailabilityList).build();
        when(foodItemService.findById(anyLong())).thenReturn(mockFoodItem);
        when(availabilityService.findById(anyLong())).thenReturn(mockAvailability);
        when(foodMenuFoodItemMapService.save(any())).thenReturn(mockFoodMenuFoodItemMap);
        when(foodMenuAvailabilityMapService.save(any())).thenReturn(mockFoodMenuAvailabilityMap);
        when(foodMenuRepository.save(any())).thenReturn(mockFoodMenu);
        FoodMenu foodMenu=foodMenuService.save(menuDto);
        assertEquals(foodMenu.getId(), mockFoodMenu.getId());
    }
}