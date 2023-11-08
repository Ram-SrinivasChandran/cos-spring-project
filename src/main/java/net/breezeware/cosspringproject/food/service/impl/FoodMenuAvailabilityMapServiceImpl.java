package net.breezeware.cosspringproject.food.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.breezeware.cosspringproject.food.dao.FoodMenuAvailabilityMapRepository;
import net.breezeware.cosspringproject.food.entity.Availability;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuAvailabilityMap;
import net.breezeware.cosspringproject.food.service.api.FoodMenuAvailabilityMapService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodMenuAvailabilityMapServiceImpl implements FoodMenuAvailabilityMapService {

    private final FoodMenuAvailabilityMapRepository foodMenuAvailabilityMapRepository;

    @Override
    public List<FoodMenuAvailabilityMap> findAll() {
        return null;
    }

    @Override
    public FoodMenuAvailabilityMap findById(long id) {
        return null;
    }

    @Override
    public FoodMenuAvailabilityMap save(FoodMenuAvailabilityMap foodMenuAvailabilityMap) {
        return foodMenuAvailabilityMapRepository.save(foodMenuAvailabilityMap);
    }

    @Override
    public void delete(FoodMenuAvailabilityMap foodMenuAvailabilityMap) {

    }

    @Override
    public List<FoodMenuAvailabilityMap> getFoodMenuAvailabilityMapByFoodMenu(FoodMenu foodMenu) {
        return foodMenuAvailabilityMapRepository.getFoodMenuAvailabilityMapByFoodMenu(foodMenu);
    }

    @Override
    public void deleteById(long id) {
        foodMenuAvailabilityMapRepository.deleteById(id);
    }

    @Override
    public void update(Long id, FoodMenuAvailabilityMap foodMenuAvailabilityMap) {

    }

    @Override
    public List<FoodMenuAvailabilityMap> getFoodMenuAvailabilityMapByAvailability(Availability availability) {
        return foodMenuAvailabilityMapRepository.getFoodMenuAvailabilityMapByAvailability(availability);
    }
}
