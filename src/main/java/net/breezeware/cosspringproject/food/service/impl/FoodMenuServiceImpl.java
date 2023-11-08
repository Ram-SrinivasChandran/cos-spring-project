package net.breezeware.cosspringproject.food.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.exception.ValidationException;
import net.breezeware.cosspringproject.food.dao.FoodMenuRepository;
import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.*;
import net.breezeware.cosspringproject.food.service.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodMenuServiceImpl implements FoodMenuService {

    private final FoodMenuRepository foodMenuRepository;
    private final FoodMenuFoodItemMapService foodMenuFoodItemMapService;
    private final FoodMenuAvailabilityMapService foodMenuAvailabilityMapService;
    private final FoodItemService foodItemService;
    private final AvailabilityService availabilityService;
    private final Validator fieldValidator;

    @Override
    public List<FoodMenu> findAll() {
        log.info("Entering findAll()");
        List<FoodMenu> foodMenus = foodMenuRepository.findAll();
        log.info("Leaving findAll()");
        return foodMenus;
    }

    @Override
    public FoodMenu findById(long id) {
        log.info("Entering findById()");
        if (id <= 0) {
            log.info("Leaving findById()");
            throw new CustomException("The Food Menu Id should be Greater than Zero.", HttpStatus.BAD_REQUEST);
        }
        log.info("Leaving findById()");
        return foodMenuRepository.findById(id).orElseThrow(() -> new CustomException("The Food Menu not Found", HttpStatus.NOT_FOUND));
    }

    @Override
    public FoodMenu save(FoodMenuDto foodMenuDto) {
        log.info("Entering save()");
        Set<ConstraintViolation<FoodMenu>> constraintViolationSet = fieldValidator.validate(foodMenuDto.getFoodMenu());
        ValidationException.handlingException(constraintViolationSet);
        for (var foodItem : foodMenuDto.getFoodItems()) {
            Set<ConstraintViolation<FoodItem>> constraintViolationSet1 = fieldValidator.validate(foodItem);
            ValidationException.handlingException(constraintViolationSet1);
            foodItemService.findById(foodItem.getId());
        }
        for (var availability : foodMenuDto.getAvailabilityList()) {
            Set<ConstraintViolation<Availability>> constraintViolationSet2 = fieldValidator.validate(availability);
            ValidationException.handlingException(constraintViolationSet2);
            availabilityService.findById(availability.getId());
        }
        if (!foodMenuRepository.existsByNameAndType(foodMenuDto.getFoodMenu().getName(), foodMenuDto.getFoodMenu().getType())) {
            foodMenuDto.getFoodMenu().setCreatedOn(Instant.now());
            foodMenuDto.getFoodMenu().setModifiedOn(Instant.now());
            FoodMenu addedFoodMenu = foodMenuRepository.save(foodMenuDto.getFoodMenu());
            for (var foodItem : foodMenuDto.getFoodItems()) {
                FoodMenuFoodItemMap foodMenuFoodItemMap = new FoodMenuFoodItemMap();
                foodMenuFoodItemMap.setFoodMenu(addedFoodMenu);
                foodMenuFoodItemMap.setFoodItem(foodItem);
                foodMenuFoodItemMap.setCreatedOn(Instant.now());
                foodMenuFoodItemMap.setModifiedOn(Instant.now());
                foodMenuFoodItemMapService.save(foodMenuFoodItemMap);
            }
            for (var availability : foodMenuDto.getAvailabilityList()) {
                FoodMenuAvailabilityMap foodMenuAvailabilityMap = new FoodMenuAvailabilityMap();
                foodMenuAvailabilityMap.setFoodMenu(addedFoodMenu);
                foodMenuAvailabilityMap.setAvailability(availability);
                foodMenuAvailabilityMap.setCreatedOn(Instant.now());
                foodMenuAvailabilityMap.setModifiedOn(Instant.now());
                foodMenuAvailabilityMapService.save(foodMenuAvailabilityMap);
            }
            log.info("Leaving save()");
            return addedFoodMenu;
        } else {
            throw new CustomException("The Food Menu is Already Exists", HttpStatus.ALREADY_REPORTED);
        }
    }

    @Transactional
    @Override
    public void update(Long id, FoodMenuDto foodMenuDto) {
        log.info("Entering update()");
        FoodMenu foodMenu = findById(id);
        foodMenuDto.setFoodMenu(foodMenu);
        for (var foodItem : foodMenuDto.getFoodItems()) {
            Set<ConstraintViolation<FoodItem>> constraintViolationSet1 = fieldValidator.validate(foodItem);
            ValidationException.handlingException(constraintViolationSet1);
            foodItemService.findById(foodItem.getId());
        }
        for (var availability : foodMenuDto.getAvailabilityList()) {
            Set<ConstraintViolation<Availability>> constraintViolationSet2 = fieldValidator.validate(availability);
            ValidationException.handlingException(constraintViolationSet2);
            availabilityService.findById(availability.getId());
        }
        List<FoodMenuFoodItemMap> listOfFoodMenuFoodItemMap = foodMenuFoodItemMapService.getFoodMenuFoodItemMapByFoodMenu(foodMenuDto.getFoodMenu());
        List<FoodMenuAvailabilityMap> listOfFoodMenuAvailabilityMap = foodMenuAvailabilityMapService.getFoodMenuAvailabilityMapByFoodMenu(foodMenuDto.getFoodMenu());
        Set<String> retrievedFoodItemName = listOfFoodMenuFoodItemMap.stream()
                .map(foodMenuFoodItemMap -> foodMenuFoodItemMap.getFoodItem().getName()).collect(Collectors.toSet());
        Set<String> foodItemNames = foodMenuDto.getFoodItems()
                .stream()
                .map(FoodItem::getName)
                .collect(Collectors.toSet());
        boolean containsAll = retrievedFoodItemName.containsAll(foodItemNames);
        if (!containsAll) {
            listOfFoodMenuFoodItemMap.forEach(foodMenuFoodItemMap -> {
                if (!foodItemNames.contains(foodMenuFoodItemMap.getFoodItem().getName())) {
                    foodMenuFoodItemMapService.deleteById(foodMenuFoodItemMap.getId());
                }
            });
            foodMenuDto.getFoodItems().forEach(foodItem -> {
                if (!retrievedFoodItemName.contains(foodItem.getName())) {
                    FoodMenuFoodItemMap newFoodMenuFoodItemMap = new FoodMenuFoodItemMap();
                    newFoodMenuFoodItemMap.setFoodMenu(foodMenu);
                    newFoodMenuFoodItemMap.setFoodItem(foodItem);
                    newFoodMenuFoodItemMap.setCreatedOn(Instant.now());
                    newFoodMenuFoodItemMap.setModifiedOn(Instant.now());
                    foodMenuFoodItemMapService.save(newFoodMenuFoodItemMap);
                }
            });
        }
        Set<Long> availabilities = foodMenuDto.getAvailabilityList()
                .stream()
                .map(Availability::getId)
                .collect(Collectors.toSet());
        Set<Long> retrievedAvailability = listOfFoodMenuAvailabilityMap.stream()
                .map(foodMenuAvailabilityMap -> foodMenuAvailabilityMap.getAvailability().getId())
                .collect(Collectors.toSet());
        boolean areAvailabilityEquals = listOfFoodMenuAvailabilityMap.stream()
                .map(foodMenuAvailabilityMap -> foodMenuAvailabilityMap.getAvailability().getId())
                .allMatch(availabilities::contains);
        if (!areAvailabilityEquals) {
            listOfFoodMenuAvailabilityMap.forEach(availabilityMap -> {
                if (!availabilities.contains(availabilityMap.getAvailability().getId())) {
                    foodMenuAvailabilityMapService.deleteById(availabilityMap.getId());
                }
            });
            foodMenuDto.getAvailabilityList().forEach(availability -> {
                if (!retrievedAvailability.contains(availability.getId())) {
                    FoodMenuAvailabilityMap foodMenuAvailabilityMap = new FoodMenuAvailabilityMap();
                    foodMenuAvailabilityMap.setFoodMenu(foodMenu);
                    foodMenuAvailabilityMap.setAvailability(availability);
                    foodMenuAvailabilityMap.setCreatedOn(Instant.now());
                    foodMenuAvailabilityMap.setModifiedOn(Instant.now());
                    foodMenuAvailabilityMapService.save(foodMenuAvailabilityMap);
                }
            });
        }
        foodMenuDto.getFoodMenu().setModifiedOn(Instant.now());
        foodMenuRepository.save(foodMenuDto.getFoodMenu());
        log.info("Leaving update()");
    }


    @Override
    public void delete(FoodMenu foodMenu) {
        foodMenuRepository.delete(foodMenu);
    }

    @Override
    public void deleteById(long id) {
        log.info("Entering deleteById()");
        FoodMenu foodMenu = findById(id);
        List<FoodMenuFoodItemMap> listOfFoodMenuFoodItemMap = foodMenuFoodItemMapService.getFoodMenuFoodItemMapByFoodMenu(foodMenu);
        List<FoodMenuAvailabilityMap> listOfFoodMenuAvailabilityMap = foodMenuAvailabilityMapService.getFoodMenuAvailabilityMapByFoodMenu(foodMenu);
        for (var foodMenuFoodItemMap : listOfFoodMenuFoodItemMap) {
            foodMenuFoodItemMapService.deleteById(foodMenuFoodItemMap.getId());
        }
        for (var foodMenuAvailabilityMap : listOfFoodMenuAvailabilityMap) {
            foodMenuAvailabilityMapService.deleteById(foodMenuAvailabilityMap.getId());
        }
        foodMenuRepository.deleteById(id);
        log.info("Leaving deleteById()");
    }
}