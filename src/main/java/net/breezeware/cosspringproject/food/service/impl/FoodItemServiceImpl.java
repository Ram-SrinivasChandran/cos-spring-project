package net.breezeware.cosspringproject.food.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.exception.ValidationException;
import net.breezeware.cosspringproject.food.dao.FoodItemRepository;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.service.api.FoodItemService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodItemServiceImpl implements FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final Validator fieldValidator;

    @Override
    public List<FoodItem> findAll() {
        log.info("Entering findAll()");
        log.info("Leaving findAll()");
        return foodItemRepository.findAll();
    }

    @Override
    public FoodItem findById(Long id) {
        log.info("Entering findById()");
        if(id<=0){
            log.info("Leaving findById()");
            throw new CustomException("The Food Item Id should be Greater than Zero.",HttpStatus.BAD_REQUEST);
        }
        log.info("Leaving findById()");
        return foodItemRepository.findById(id).orElseThrow(()->new CustomException("The Food Item not Found", HttpStatus.NOT_FOUND));
    }

    @Override
    public FoodItem save(FoodItem foodItem) {
        log.info("Entering save()");
        Set<ConstraintViolation<FoodItem>> constraintViolationSet = fieldValidator.validate(foodItem);
        ValidationException.handlingException(constraintViolationSet);
        foodItem.setCreatedOn(Instant.now());
        foodItem.setModifiedOn(Instant.now());
        log.info("Leaving save()");
        return foodItemRepository.save(foodItem);
    }

    @Override
    public void update(Long id, FoodItem updatedFoodItem) {
        log.info("Entering update()");
        Set<ConstraintViolation<FoodItem>> constraintViolationSet = fieldValidator.validate(updatedFoodItem);
        ValidationException.handlingException(constraintViolationSet);
        FoodItem foodItem=findById(id);
        foodItem.setName(updatedFoodItem.getName());
        foodItem.setCost(updatedFoodItem.getCost());
        foodItem.setQuantity(updatedFoodItem.getQuantity());
        foodItem.setModifiedOn(Instant.now());
        foodItemRepository.save(foodItem);
        log.info("Leaving update()");
    }

    @Override
    public void delete(FoodItem foodItem) {
        log.info("Entering delete()");
        foodItemRepository.delete(foodItem);
        log.info("Leaving delete()");
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        foodItemRepository.deleteById(id);
    }
}
