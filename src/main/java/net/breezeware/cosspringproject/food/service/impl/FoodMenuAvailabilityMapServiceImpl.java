package net.breezeware.cosspringproject.food.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.food.dao.FoodMenuAvailabilityMapRepository;
import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.FoodMenuAvailabilityMap;
import net.breezeware.cosspringproject.food.service.api.FoodMenuAvailabilityMapService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void deleteById(long id) {

    }

    @Override
    public void update(Long id, FoodMenuAvailabilityMap foodMenuAvailabilityMap) {

    }
}
