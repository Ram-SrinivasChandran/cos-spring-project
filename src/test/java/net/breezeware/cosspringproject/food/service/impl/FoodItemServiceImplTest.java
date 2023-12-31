package net.breezeware.cosspringproject.food.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.http.HttpStatus;

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.food.dao.FoodItemRepository;
import net.breezeware.cosspringproject.food.entity.FoodItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
class FoodItemServiceImplTest {
    @Mock
    FoodItemRepository foodItemRepository;
    @InjectMocks
    FoodItemServiceImpl foodItemService;
    @Mock
    Validator validator;

    @Test
    void testFindAllFoodItems() {
        List<FoodItem> mockFoodItems = List.of(new FoodItem(), new FoodItem());
        when(foodItemRepository.findAll()).thenReturn(mockFoodItems);
        List<FoodItem> foodItems = foodItemService.findAllFoodItems(1L);
        Assertions.assertThat(foodItems).hasSize(mockFoodItems.size());
        verify(foodItemRepository).findAll();
    }

    @Test
    void testFindFoodItemsById() {
        FoodItem mockFoodItem = FoodItem.builder().id(1).cost(20).quantity(10).build();
        when(foodItemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockFoodItem));
        FoodItem foodItem = foodItemService.findFoodItemById(1L);
        Assertions.assertThat(foodItem).hasFieldOrProperty("cost");
        assert mockFoodItem != null;
        assertEquals(foodItem.getQuantity(), mockFoodItem.getQuantity());
        Mockito.verify(foodItemRepository).findById(anyLong());
    }

    @Test
    void testFindFoodItemByIdInvalidUserIdThrowCustomException() {
        CustomException exception = assertThrows(CustomException.class, () -> foodItemService.findFoodItemById(-1L));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("The Food Item Id should be Greater than Zero.", exception.getMessage());
    }

    @Test
    void testFindFoodItemByIdInvalidUserThrowCustomException() {
        CustomException exception = assertThrows(CustomException.class, () -> foodItemService.findFoodItemById(2L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("The Food Item not Found", exception.getMessage());
    }

    @Test
    void testSaveFoodItem() {
        FoodItem mockFoodItem = FoodItem.builder().id(1).cost(25).quantity(20).build();
        when(foodItemRepository.save(any())).thenReturn(mockFoodItem);
        FoodItem foodItem = foodItemService.saveFoodItem(mockFoodItem);
        assertEquals(mockFoodItem.getQuantity(), foodItem.getQuantity());
        assertEquals(mockFoodItem.getCost(), foodItem.getCost());
        assertEquals(mockFoodItem.getId(), foodItem.getId());
    }

    @Test
    void testUpdateFoodItem() {
        FoodItem mockFoodItem = FoodItem.builder().id(1).cost(25).quantity(20).build();
        when(foodItemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockFoodItem));
        when(foodItemRepository.save(any())).thenReturn(mockFoodItem);
        assert mockFoodItem != null;
        foodItemService.updateFoodItem(1L, mockFoodItem);
        Mockito.verify(foodItemRepository, Mockito.times(1)).save(any());
    }

    @Test
    void testDeleteFoodItem() {
        FoodItem mockFoodItem = FoodItem.builder().id(1).cost(25).quantity(20).build();
        doNothing().when(foodItemRepository).delete(mockFoodItem);
        foodItemService.deleteFoodItem(mockFoodItem);
        Mockito.verify(foodItemRepository, Mockito.times(1)).delete(mockFoodItem);
    }

    @Test
    void testDeleteFoodItemById() {
        FoodItem mockFoodItem = FoodItem.builder().id(1).cost(25).quantity(20).build();
        doNothing().when(foodItemRepository).deleteById(1L);
        when(foodItemRepository.findById(any())).thenReturn(Optional.ofNullable(mockFoodItem));
        foodItemService.deleteFoodItemById(1L);
        Mockito.verify(foodItemRepository, Mockito.times(1)).deleteById(1L);
    }
}