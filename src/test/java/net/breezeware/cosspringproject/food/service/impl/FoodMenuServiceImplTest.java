package net.breezeware.cosspringproject.food.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.food.dao.FoodMenuRepository;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class FoodMenuServiceImplTest {
    @Mock
    FoodMenuRepository foodMenuRepository;
    @InjectMocks
    FoodMenuServiceImpl foodMenuService;
    @Mock
    Validator validator;

    @Test
    void testFindAllFoodMenus() {
        List<FoodMenu> mockFoodMenus=List.of(new FoodMenu(),new FoodMenu());
        when(foodMenuRepository.findAll()).thenReturn(mockFoodMenus);
        List<FoodMenu> foodMenus=foodMenuService.findAll();
        Assertions.assertThat(foodMenus).hasSize(mockFoodMenus.size());
        verify(foodMenuRepository).findAll();
    }

    @Test
    void testFindFoodItemsById() {
        FoodMenu mockFoodMenu= FoodMenu.builder().id(1).build();
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockFoodMenu));
        FoodMenu foodMenu = foodMenuService.findById(1L);
        Assertions.assertThat(foodMenu).hasFieldOrProperty("type");
        assert mockFoodMenu != null;
        assertEquals(foodMenu.getId(), mockFoodMenu.getId());
        Mockito.verify(foodMenuRepository).findById(anyLong());
    }

}