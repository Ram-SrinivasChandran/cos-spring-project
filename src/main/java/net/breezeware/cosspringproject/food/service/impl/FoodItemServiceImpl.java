package net.breezeware.cosspringproject.food.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.exception.ValidationException;
import net.breezeware.cosspringproject.food.dao.FoodItemRepository;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.service.api.FoodItemService;
import net.breezeware.cosspringproject.user.service.api.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodItemServiceImpl implements FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final UserService userService;
    private final Validator fieldValidator;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FoodItem> findAllFoodItems(long userId) {
        log.info("Entering findAllFoodItems()");
        validateAdminAccess(userId);
        List<FoodItem> foodItem = foodItemRepository.findAll();
        log.info("Leaving findAllFoodItems()");
        return foodItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FoodItem findFoodItemById(long foodItemId) {
        log.info("Entering findFoodItemById()");
        if (foodItemId <= 0) {
            throw new CustomException("The Food Item Id should be Greater than Zero.", HttpStatus.BAD_REQUEST);
        }

        log.info("Leaving findFoodItemById()");
        return foodItemRepository.findById(foodItemId)
                .orElseThrow(() -> new CustomException("The Food Item not Found", HttpStatus.NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public FoodItem saveFoodItem(FoodItem foodItem) {
        log.info("Entering saveFoodItem()");
        Set<ConstraintViolation<FoodItem>> constraintViolationSet = fieldValidator.validate(foodItem);
        ValidationException.handlingException(constraintViolationSet);
        foodItem.setCreatedOn(Instant.now());
        foodItem.setModifiedOn(Instant.now());
        log.info("Leaving saveFoodItem()");
        return foodItemRepository.save(foodItem);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updateFoodItem(long foodItemId, FoodItem updatedFoodItem) {
        log.info("Entering updateFoodItem()");
        Set<ConstraintViolation<FoodItem>> constraintViolationSet = fieldValidator.validate(updatedFoodItem);
        ValidationException.handlingException(constraintViolationSet);
        FoodItem foodItem = findFoodItemById(foodItemId);
        foodItem.setName(updatedFoodItem.getName());
        foodItem.setCost(updatedFoodItem.getCost());
        foodItem.setQuantity(updatedFoodItem.getQuantity());
        foodItem.setModifiedOn(Instant.now());
        foodItemRepository.save(foodItem);
        log.info("Leaving updateFoodItem()");
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteFoodItem(FoodItem foodItem) {
        log.info("Entering deleteFoodItem()");
        foodItemRepository.delete(foodItem);
        log.info("Leaving deleteFoodItem()");
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteFoodItemById(long foodItemId) {
        log.info("Entering deleteFoodItemById()");
        findFoodItemById(foodItemId);
        foodItemRepository.deleteById(foodItemId);
        log.info("Leaving deleteFoodItemById()");
    }

    /**
     * Validates if a user has admin privileges.
     * @param  userId          The ID of the user to check.
     * @throws CustomException Thrown if the user does not have admin privileges.
     */
    private void validateAdminAccess(long userId) {
        if (!userService.isAdmin(userId)) {
            throw new CustomException("Access Denied", HttpStatus.UNAUTHORIZED);
        }

    }
}
