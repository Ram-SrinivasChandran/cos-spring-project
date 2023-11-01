package net.breezeware.cosspringproject.order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.*;
import net.breezeware.cosspringproject.food.service.api.AvailabilityService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuAvailabilityMapService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuFoodItemMapService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuService;
import net.breezeware.cosspringproject.food.service.impl.FoodMenuFoodItemMapServiceImpl;
import net.breezeware.cosspringproject.order.service.api.OrderService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final AvailabilityService availability;
    private final FoodMenuAvailabilityMapService foodMenuAvailabilityMapService;
    private final FoodMenuService foodMenuService;
    private final FoodMenuFoodItemMapService foodMenuFoodItemMapService;
    @Override
    public List<FoodMenuDto> viewFoodMenus() {
        log.info("Entering viewFoodMenus()");
        Instant currentInstant = Instant.now();
        ZoneId zoneId = ZoneId.of("UTC");
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(currentInstant, zoneId);
        DayOfWeek dayOfWeek = zonedDateTime.getDayOfWeek();
        String todayDay= String.valueOf(dayOfWeek);
        Availability availabilityByDay = availability.findByDay(todayDay.toLowerCase());
        List<FoodMenuAvailabilityMap> foodMenusAvailability = foodMenuAvailabilityMapService.getFoodMenuAvailabilityMapByAvailability(availabilityByDay);
        List<FoodMenuDto> foodMenuDtos=new ArrayList<>();
        for(var foodMenuAvailability :foodMenusAvailability){
            FoodMenuDto foodMenuDto = new FoodMenuDto();
            FoodMenu foodMenu = foodMenuService.findById(foodMenuAvailability.getFoodMenu().getId());
            foodMenuDto.setFoodMenu(foodMenu);
            List<FoodMenuFoodItemMap> listOfFoodMenuFoodItemMap = foodMenuFoodItemMapService.getFoodMenuFoodItemMapByFoodMenu(foodMenu);
            List<FoodItem> foodItems=new ArrayList<>();
            for(var foodMenuFoodItemMap:listOfFoodMenuFoodItemMap){
                foodItems.add(foodMenuFoodItemMap.getFoodItem());
            }
            List<FoodMenuAvailabilityMap> listOfFoodMenuAvailabilityMap = foodMenuAvailabilityMapService.getFoodMenuAvailabilityMapByFoodMenu(foodMenu);
            List<Availability> availabilities=new ArrayList<>();
            for(var foodMenuAvailableMap:listOfFoodMenuAvailabilityMap){
                availabilities.add(foodMenuAvailableMap.getAvailability());
            }
            foodMenuDto.setAvailabilityList(availabilities);
            foodMenuDto.setFoodItems(foodItems);
            foodMenuDtos.add(foodMenuDto);
        }
        log.info("Leaving viewFoodMenus()");
        return foodMenuDtos;
    }
}
