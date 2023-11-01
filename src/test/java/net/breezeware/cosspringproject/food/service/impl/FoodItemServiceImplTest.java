package net.breezeware.cosspringproject.food.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.food.dao.FoodItemRepository;
import net.breezeware.cosspringproject.food.entity.FoodItem;
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
import static org.mockito.Mockito.*;

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
        List<FoodItem> mockFoodItems=List.of(new FoodItem(),new FoodItem());
        when(foodItemRepository.findAll()).thenReturn(mockFoodItems);
        List<FoodItem> foodItems=foodItemService.findAll();
        Assertions.assertThat(foodItems).hasSize(mockFoodItems.size());
        verify(foodItemRepository).findAll();
    }

    @Test
    void testFindFoodItemsById() {
        FoodItem mockFoodItem= FoodItem.builder().id(1).cost(20).quantity(10).build();
        when(foodItemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockFoodItem));
        FoodItem foodItem = foodItemService.findById(1L);
        Assertions.assertThat(foodItem).hasFieldOrProperty("cost");
        assert mockFoodItem != null;
        assertEquals(foodItem.getQuantity(), mockFoodItem.getQuantity());
        Mockito.verify(foodItemRepository).findById(anyLong());
    }
    @Test
    void testFindFoodItemByIdInvalidUserIdThrowCustomException() {
        CustomException exception =
                assertThrows(CustomException.class, () -> foodItemService.findById(-1L));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("The Food Item Id should be Greater than Zero.", exception.getMessage());
    }
    @Test
    void testFindFoodItemByIdInvalidUserThrowCustomException() {
        CustomException exception =
                assertThrows(CustomException.class, () -> foodItemService.findById(2L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("The Food Item not Found", exception.getMessage());
    }


    @Test
    void testSaveFoodItem() {
        FoodItem mockFoodItem= FoodItem.builder().id(1).cost(25).quantity(20).build();
        when(foodItemRepository.save(any())).thenReturn(mockFoodItem);
        FoodItem foodItem=foodItemService.save(mockFoodItem);
        assertEquals(mockFoodItem.getQuantity(),foodItem.getQuantity());
        assertEquals(mockFoodItem.getCost(),foodItem.getCost());
        assertEquals(mockFoodItem.getId(),foodItem.getId());
    }

    @Test
    void testUpdateFoodItem() {
        FoodItem mockFoodItem= FoodItem.builder().id(1).cost(25).quantity(20).build();
        when(foodItemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockFoodItem));
        when(foodItemRepository.save(any())).thenReturn(mockFoodItem);
        assert mockFoodItem != null;
        foodItemService.update(1L,mockFoodItem);
        Mockito.verify(foodItemRepository, Mockito.times(1)).save(any());
    }

    @Test
    void testDeleteFoodItem(){
        FoodItem mockFoodItem = FoodItem.builder().id(1).cost(25).quantity(20).build();
        doNothing().when(foodItemRepository).delete(mockFoodItem);
        foodItemService.delete(mockFoodItem);
        Mockito.verify(foodItemRepository,Mockito.times(1)).delete(mockFoodItem);
    }

    @Test
    void testDeleteFoodItemById() {
        FoodItem mockFoodItem = FoodItem.builder().id(1).cost(25).quantity(20).build();
        doNothing().when(foodItemRepository).deleteById(1L);
        when(foodItemRepository.findById(any())).thenReturn(Optional.ofNullable(mockFoodItem));
        foodItemService.deleteById(1L);
        Mockito.verify(foodItemRepository,Mockito.times(1)).deleteById(1L);
    }
}