package net.breezeware.cosspringproject.order.service.impl;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.exception.ValidationException;
import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.Availability;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuAvailabilityMap;
import net.breezeware.cosspringproject.food.entity.FoodMenuFoodItemMap;
import net.breezeware.cosspringproject.food.service.api.AvailabilityService;
import net.breezeware.cosspringproject.food.service.api.FoodItemService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuAvailabilityMapService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuFoodItemMapService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuService;
import net.breezeware.cosspringproject.order.dao.OrderRepository;
import net.breezeware.cosspringproject.order.dto.FoodItemDto;
import net.breezeware.cosspringproject.order.dto.OrderDto;
import net.breezeware.cosspringproject.order.dto.OrderViewDto;
import net.breezeware.cosspringproject.order.dto.PlaceOrderDto;
import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.order.entity.OrderItem;
import net.breezeware.cosspringproject.order.enumeration.Status;
import net.breezeware.cosspringproject.order.service.api.OrderItemService;
import net.breezeware.cosspringproject.order.service.api.OrderService;
import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.entity.UserAddressMap;
import net.breezeware.cosspringproject.user.service.api.UserAddressMapService;
import net.breezeware.cosspringproject.user.service.api.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    private final UserAddressMapService userAddressMapService;
    private final Validator fieldValidator;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FoodMenuDto> viewFoodMenus() {
        log.info("Entering viewFoodMenus()");
        Instant currentInstant = Instant.now();
        ZoneId zoneId = ZoneId.of("UTC");
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(currentInstant, zoneId);
        DayOfWeek dayOfWeek = zonedDateTime.getDayOfWeek();
        String todayDay = String.valueOf(dayOfWeek);
        Availability availabilityByDay = availability.findAvailabilityByDay(todayDay.toLowerCase());
        List<FoodMenuAvailabilityMap> foodMenusAvailability =
                foodMenuAvailabilityMapService.getFoodMenuAvailabilityMapByAvailability(availabilityByDay);
        List<FoodMenuDto> foodMenuDtos = new ArrayList<>();
        for (var foodMenuAvailability : foodMenusAvailability) {
            FoodMenuDto foodMenuDto = new FoodMenuDto();
            FoodMenu foodMenu = foodMenuService.findFoodMenuById(foodMenuAvailability.getFoodMenu().getId());
            foodMenuDto.setFoodMenu(foodMenu);
            List<FoodMenuFoodItemMap> listOfFoodMenuFoodItemMap =
                    foodMenuFoodItemMapService.getFoodMenuFoodItemMapByFoodMenu(foodMenu);
            List<FoodItem> foodItems = new ArrayList<>();
            for (var foodMenuFoodItemMap : listOfFoodMenuFoodItemMap) {
                foodItems.add(foodMenuFoodItemMap.getFoodItem());
            }

            List<FoodMenuAvailabilityMap> listOfFoodMenuAvailabilityMap =
                    foodMenuAvailabilityMapService.getFoodMenuAvailabilityMapByFoodMenu(foodMenu);
            List<Availability> availabilities = new ArrayList<>();
            for (var foodMenuAvailableMap : listOfFoodMenuAvailabilityMap) {
                availabilities.add(foodMenuAvailableMap.getAvailability());
            }

            foodMenuDto.setAvailabilityList(availabilities);
            foodMenuDto.setFoodItems(foodItems);
            foodMenuDtos.add(foodMenuDto);
        }

        log.info("Leaving viewFoodMenus()");
        return foodMenuDtos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order findById(long orderId) {
        log.info("Entering findById()");
        if (orderId <= 0) {
            throw new CustomException("The Order Id should be Greater than Zero.", HttpStatus.BAD_REQUEST);
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("The Order Id is not available", HttpStatus.NOT_FOUND));
        log.info("Leaving findById()");
        return order;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Order createOrder(OrderDto orderDto) {
        log.info("Entering createOrder()");
        Order order = orderDto.getOrder();
        if (!userService.isACustomer(order.getUser())) {
            throw new CustomException("Access Denied.", HttpStatus.UNAUTHORIZED);
        }

        Set<ConstraintViolation<Order>> constraintViolationSet = fieldValidator.validate(order);
        ValidationException.handlingException(constraintViolationSet);
        List<FoodItemDto> foodItemDtos = orderDto.getFoodItemDtos();
        for (var foodItemDto : foodItemDtos) {
            FoodItem foodItem = foodItemDto.getFoodItem();
            Set<ConstraintViolation<FoodItem>> constraintViolationSet1 = fieldValidator.validate(foodItem);
            ValidationException.handlingException(constraintViolationSet1);
        }

        order.setStatus(Status.IN_CART.name());
        order.setCreatedOn(Instant.now());
        order.setModifiedOn(Instant.now());
        Order savedOrder = orderRepository.save(order);
        double totalCostOfTheOrder = orderItemService.addOrderItems(order, foodItemDtos);
        savedOrder.setTotalCost(totalCostOfTheOrder);
        savedOrder.setModifiedOn(Instant.now());
        log.info("Leaving createOrder()");
        return orderRepository.save(savedOrder);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public OrderViewDto viewOrder(long orderId) {
        log.info("Entering viewOrder()");
        OrderViewDto orderViewDto = new OrderViewDto();
        Order order = findById(orderId);
        List<OrderItem> orderItems = orderItemService.findByOrder(order);
        List<FoodItem> foodItems = new ArrayList<>();
        for (var orderItem : orderItems) {
            FoodItem foodItem = foodItemService.findFoodItemById(orderItem.getFoodItem().getId());
            foodItem.setQuantity(orderItem.getQuantity());
            foodItem.setCost(orderItem.getCost());
            foodItems.add(foodItem);
        }

        orderViewDto.setOrder(order);
        orderViewDto.setFoodItems(foodItems);
        log.info("Leaving viewOrder()");
        return orderViewDto;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updateOrder(long orderId, List<FoodItemDto> foodItemDtos) {
        log.info("Entering updateOrder()");
        Order order = findById(orderId);
        foodItemDtos.forEach(foodItemDto -> {
            FoodItem foodItem = foodItemDto.getFoodItem();
            Set<ConstraintViolation<FoodItem>> constraintViolationSet = fieldValidator.validate(foodItem);
            ValidationException.handlingException(constraintViolationSet);
        });
        double totalCostOfTheOrder = 0;
        List<OrderItem> orderItems = orderItemService.findByOrder(order);
        List<Long> updatedFoodItemIds =
                foodItemDtos.stream().map(FoodItemDto::getFoodItem).map(FoodItem::getId).toList();
        List<OrderItem> notNeededOrderItems = orderItems.stream()
                .filter(orderItem -> !updatedFoodItemIds.contains(orderItem.getFoodItem().getId())).toList();
        for (var notNeededOrderItem : notNeededOrderItems) {
            orderItemService.deleteOrderItemById(notNeededOrderItem.getId());
        }

        orderItems.removeAll(notNeededOrderItems);
        for (var foodItemDto : foodItemDtos) {
            long updatedFoodItemId = foodItemDto.getFoodItem().getId();
            boolean isFoodItemAlreadyInOrder = orderItems.stream().map(OrderItem::getFoodItem).map(FoodItem::getId)
                    .toList().contains(updatedFoodItemId);
            if (isFoodItemAlreadyInOrder) {
                for (var orderItem : orderItems) {
                    if (updatedFoodItemId == orderItem.getFoodItem().getId()) {
                        orderItem.setFoodItem(foodItemDto.getFoodItem());
                        orderItem.setCost(foodItemDto.getRequiredQuantity() * orderItem.getFoodItem().getCost());
                        orderItem.setQuantity(foodItemDto.getRequiredQuantity());
                        orderItem.setModifiedOn(Instant.now());
                        orderItemService.createOrderItem(orderItem);
                        totalCostOfTheOrder += foodItemDto.getRequiredQuantity() * orderItem.getFoodItem().getCost();
                    }

                }

            } else {
                OrderItem saveOrderItem = new OrderItem();
                FoodItem foodItem = foodItemService.findFoodItemById(foodItemDto.getFoodItem().getId());
                saveOrderItem.setOrder(order);
                saveOrderItem.setFoodItem(foodItemDto.getFoodItem());
                saveOrderItem.setQuantity(foodItemDto.getRequiredQuantity());
                saveOrderItem.setCost(foodItemDto.getRequiredQuantity() * foodItem.getCost());
                saveOrderItem.setCreatedOn(Instant.now());
                saveOrderItem.setModifiedOn(Instant.now());
                orderItemService.createOrderItem(saveOrderItem);
                totalCostOfTheOrder += foodItemDto.getRequiredQuantity() * foodItem.getCost();
            }

        }

        order.setTotalCost(totalCostOfTheOrder);
        order.setModifiedOn(Instant.now());
        orderRepository.save(order);
        log.info("Leaving updateOrder()");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAddressMap createAddress(UserAddressMap userAddressMap) {
        log.info("Entering createAddress()");
        Set<ConstraintViolation<User>> constraintViolationSet = fieldValidator.validate(userAddressMap.getUser());
        ValidationException.handlingException(constraintViolationSet);
        Set<ConstraintViolation<UserAddressMap>> constraintViolationSet1 = fieldValidator.validate(userAddressMap);
        ValidationException.handlingException(constraintViolationSet1);
        if (!userService.isACustomer(userAddressMap.getUser())) {
            throw new CustomException("Access Denied.", HttpStatus.UNAUTHORIZED);
        }

        userAddressMap.setCreatedOn(Instant.now());
        userAddressMap.setModifiedOn(Instant.now());
        UserAddressMap savedAddress = userAddressMapService.save(userAddressMap);
        log.info("Leaving createAddress()");
        return savedAddress;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderViewDto placeOrder(long orderId, PlaceOrderDto placeOrderDto) {
        log.info("Entering placeOrder()");
        Order order = findById(orderId);
        if (!validateEmail(placeOrderDto.getEmail())) {
            throw new CustomException("Enter a valid Email Address", HttpStatus.BAD_REQUEST);
        }

        if (!validatePhoneNumber(placeOrderDto.getPhoneNumber())) {
            throw new CustomException("Enter a Valid Phone Number", HttpStatus.BAD_REQUEST);
        }

        order.setPhoneNumber(placeOrderDto.getPhoneNumber());
        order.setStatus(Status.ORDER_PLACED.name());
        order.setEmail(placeOrderDto.getEmail());
        order.setDeliveryOn(placeOrderDto.getDeliveryTime());
        order.setModifiedOn(Instant.now());
        order.setOrderOn(Instant.now());
        order.setUserAddress(placeOrderDto.getUserAddressMap());
        List<OrderItem> orderItems = orderItemService.findByOrder(order);
        for (var orderItem : orderItems) {
            int orderItemQuantity = orderItem.getQuantity();
            FoodItem foodItem = foodItemService.findFoodItemById(orderItem.getFoodItem().getId());
            int foodItemQuantity = foodItem.getQuantity();
            foodItem.setQuantity(foodItemQuantity - orderItemQuantity);
            foodItemService.saveFoodItem(foodItem);
        }

        Order savedOrder = orderRepository.save(order);
        log.info("Leaving placeOrder()");
        return viewOrder(savedOrder.getId());
    }

    /**
     * Validates a phone number for a specific format.
     *
     * @param phoneNumber The phone number to validate.
     * @return `true` if the phone number is valid, `false` otherwise.
     */
    private boolean validatePhoneNumber(String phoneNumber) {
        log.info("Entering validatePhoneNumber()");
        if (phoneNumber.length() != 10) {
            return false;
        }

        String phoneRegex = "^[0-9()-]+$";
        Pattern pattern = Pattern.compile(phoneRegex);
        boolean matches = pattern.matcher(phoneNumber).matches();
        log.info("Leaving validatePhoneNumber()");
        return matches;
    }

    /**
     * Validates an email address for a specific format.
     *
     * @param email The email address to validate.
     * @return `true` if the email address is valid, `false` otherwise.
     */
    private boolean validateEmail(String email) {
        log.info("Entering validateEmail()");
        String emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailRegex);
        boolean matches = pattern.matcher(email).matches();
        log.info("Leaving validateEmail()");
        return matches;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelOrder(long orderId) {
        log.info("Entering cancelOrder()");
        Order order = findById(orderId);
        order.setStatus(Status.ORDER_CANCELLED.name());
        List<OrderItem> orderItems = orderItemService.findByOrder(order);
        for (var orderItem : orderItems) {
            int orderItemQuantity = orderItem.getQuantity();
            FoodItem foodItem = foodItemService.findFoodItemById(orderItem.getFoodItem().getId());
            int foodItemQuantity = foodItem.getQuantity();
            foodItem.setQuantity(foodItemQuantity + orderItemQuantity);
            foodItemService.saveFoodItem(foodItem);
        }

        orderRepository.save(order);
        log.info("Leaving cancelOrder()");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderViewDto> viewActiveOrders(long orderId) {
        log.info("Entering viewActiveOrders()");
        if (!userService.isACafeteriaStaff(orderId)) {
            throw new CustomException("Access Denied.", HttpStatus.UNAUTHORIZED);
        }

        List<OrderViewDto> activeOrders = viewOrderListByStatus(Status.ORDER_PLACED.name());
        List<OrderViewDto> sortedActiveOrders =
                activeOrders.stream().sorted(Comparator.comparing(c -> c.getOrder().getDeliveryOn())).toList();
        log.info("Leaving viewActiveOrders()");
        return sortedActiveOrders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderViewDto viewReceivedOrder(long userId, long orderId) {
        log.info("Entering viewReceivedOrder()");
        if (!userService.isACafeteriaStaff(userId)) {
            throw new CustomException("Access Denied.", HttpStatus.UNAUTHORIZED);
        }

        Order order = findById(orderId);
        order.setStatus(Status.RECEIVED_ORDER.name());
        order.setModifiedOn(Instant.now());
        Order savedOrder = orderRepository.save(order);
        log.info("Leaving viewReceivedOrder()");
        return viewOrder(savedOrder.getId());
    }


    /**
     * Retrieves a list of orders with a specific status and returns them as a list of OrderViewDto objects.
     *
     * @param status The status of the orders to retrieve.
     * @return A list of OrderViewDto objects representing the orders with the specified status.
     */
    private List<OrderViewDto> viewOrderListByStatus(String status) {
        List<OrderViewDto> orderList = new ArrayList<>();
        List<Order> orders = orderRepository.getOrderByStatus(status);
        for (var order : orders) {
            OrderViewDto activeOrder = viewOrder(order.getId());
            orderList.add(activeOrder);
        }

        return orderList;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void changeStatusToWaitingForDelivery(long userId, long orderId) {
        log.info("Entering changeStatusToWaitingForDelivery()");
        if (!userService.isACafeteriaStaff(userId)) {
            throw new CustomException("Access Denied.", HttpStatus.UNAUTHORIZED);
        }

        changeStatus(orderId, Status.ORDER_PREPARED_WAITING_FOR_DELIVERY.name());
        log.info("Leaving changeStatusToWaitingForDelivery()");
    }


    /**
     * Changes the status of an order identified by the given order ID.
     *
     * @param orderId The unique identifier of the order to update.
     * @param status The new status to set for the order.
     */
    private void changeStatus(long orderId, String status) {
        Order order = findById(orderId);
        order.setStatus(status);
        order.setModifiedOn(Instant.now());
        orderRepository.save(order);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void changeStatusToPendingDelivery(long userId, long orderId) {
        log.info("Entering changeStatusToPendingDelivery()");
        if (!userService.isACafeteriaStaff(userId)) {
            throw new CustomException("Access Denied.", HttpStatus.UNAUTHORIZED);
        }

        changeStatus(orderId, Status.PENDING_DELIVERY.name());
        log.info("Leaving changeStatusToPendingDelivery()");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeStatusToOrderDelivered(long userId, long orderId) {
        log.info("Entering changeStatusToOrderDelivered()");
        if (!userService.isADeliveryStaff(userId)) {
            throw new CustomException("Access Denied.", HttpStatus.UNAUTHORIZED);
        }

        changeStatus(orderId, Status.ORDER_DELIVERED.name());
        log.info("Leaving changeStatusToOrderDelivered()");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderViewDto> viewCancelledOrders(long orderId) {
        log.info("Entering viewCancelledOrders()");
        if (!userService.isADeliveryStaff(orderId)) {
            throw new CustomException("Access Denied.", HttpStatus.UNAUTHORIZED);
        }

        List<OrderViewDto> cancelledOrders = viewOrderListByStatus(Status.ORDER_CANCELLED.name());
        log.info("Leaving viewCancelledOrders()");
        return cancelledOrders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderViewDto viewCancelledOrder(long userId, long orderId) {
        log.info("Entering viewCancelledOrders()");
        if (!userService.isADeliveryStaff(userId)) {
            throw new CustomException("Access Denied.", HttpStatus.UNAUTHORIZED);
        }

        Order order = viewOrderByStatus(orderId, Status.ORDER_CANCELLED.name());
        log.info("Leaving viewCancelledOrders()");
        return viewOrder(order.getId());
    }


    /**
     * Retrieves an order with the specified status, identified by its unique order ID.
     *
     * @param orderId The unique identifier of the order to retrieve.
     * @param status The desired status to check for.
     * @return The order if it has the specified status.
     * @throws CustomException if no order with the given status is found.
     */
    private Order viewOrderByStatus(long orderId, String status) {
        Order order = findById(orderId);
        if (order.getStatus().equals(status)) {
            return order;
        }

        throw new CustomException("The Order is Not with the Status of " + status, HttpStatus.NOT_FOUND);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderViewDto> viewCompletedOrders(long orderId) {
        log.info("Entering viewCompletedOrders()");
        if (!userService.isADeliveryStaff(orderId)) {
            throw new CustomException("Access Denied.", HttpStatus.UNAUTHORIZED);
        }

        List<OrderViewDto> completedOrders = viewOrderListByStatus(Status.ORDER_DELIVERED.name());
        log.info("Leaving viewCompletedOrders()");
        return completedOrders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderViewDto viewCompletedOrder(long userId, long orderId) {
        log.info("Entering viewCompletedOrder()");
        if (!userService.isADeliveryStaff(userId)) {
            throw new CustomException("Access Denied.", HttpStatus.UNAUTHORIZED);
        }

        Order order = viewOrderByStatus(orderId, Status.ORDER_DELIVERED.name());
        log.info("Leaving viewCompletedOrder()");
        return viewOrder(order.getId());
    }
}
