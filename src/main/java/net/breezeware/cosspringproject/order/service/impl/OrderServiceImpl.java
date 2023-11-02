package net.breezeware.cosspringproject.order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.exception.ValidationException;
import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.*;
import net.breezeware.cosspringproject.food.service.api.*;
import net.breezeware.cosspringproject.order.dao.OrderRepository;
import net.breezeware.cosspringproject.order.dto.FoodItemDto;
import net.breezeware.cosspringproject.order.dto.OrderDto;
import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.order.entity.OrderItem;
import net.breezeware.cosspringproject.order.enumeration.Status;
import net.breezeware.cosspringproject.order.service.api.OrderItemService;
import net.breezeware.cosspringproject.order.service.api.OrderService;
import net.breezeware.cosspringproject.user.service.api.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final AvailabilityService availability;
    private final FoodMenuAvailabilityMapService foodMenuAvailabilityMapService;
    private final FoodMenuService foodMenuService;
    private final FoodMenuFoodItemMapService foodMenuFoodItemMapService;
    private final OrderRepository orderRepository;
    private final FoodItemService foodItemService;
    private final UserService userService;
    private final OrderItemService orderItemService;
    private final Validator fieldValidator;
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

    @Transactional
    @Override
    public Order createOrder(OrderDto orderDto) {
        log.info("Entering createOrder()");
        Order order=orderDto.getOrder();
        if(!userService.isACustomer(order.getUser())){
            throw new CustomException("Access Denied.", HttpStatus.UNAUTHORIZED);
        }
        Set<ConstraintViolation<Order>> constraintViolationSet = fieldValidator.validate(order);
        ValidationException.handlingException(constraintViolationSet);
        List<FoodItemDto>foodItemDtos=orderDto.getFoodItemDtos();
        for(var foodItemDto:foodItemDtos){
            FoodItem foodItem=foodItemDto.getFoodItem();
            Set<ConstraintViolation<FoodItem>> constraintViolationSet1 = fieldValidator.validate(foodItem);
            ValidationException.handlingException(constraintViolationSet1);
        }
        order.setStatus(Status.INCART.name());
        order.setCreatedOn(Instant.now());
        order.setModifiedOn(Instant.now());
        Order savedOrder = orderRepository.save(order);
        double totalCostOfTheOrder=0;
        for(var foodItemDto:foodItemDtos){
            FoodItem foodItem=foodItemDto.getFoodItem();
            FoodItem retainedFoodItem = foodItemService.findById(foodItem.getId());
            if(retainedFoodItem.getQuantity()<foodItemDto.getRequiredQuantity()){
                throw new CustomException("The FoodItem Quantity is Unavailable",HttpStatus.BAD_REQUEST);
            }else {
                OrderItem orderItem=new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setFoodItem(foodItem);
                orderItem.setQuantity(foodItemDto.getRequiredQuantity());
                orderItem.setCost(foodItem.getCost()*foodItemDto.getRequiredQuantity());
                orderItem.setCreatedOn(Instant.now());
                orderItem.setModifiedOn(Instant.now());
                OrderItem createdOrderItem = orderItemService.createOrderItem(orderItem);
                totalCostOfTheOrder+=createdOrderItem.getCost();
            }
        }
        savedOrder.setTotalCost(totalCostOfTheOrder);
        savedOrder.setModifiedOn(Instant.now());
        log.info("Leaving createOrder()");
        return orderRepository.save(savedOrder);
    }
}
