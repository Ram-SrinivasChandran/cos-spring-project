package net.breezeware.cosspringproject.order.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.order.dto.FoodItemDto;
import net.breezeware.cosspringproject.order.dto.OrderDto;
import net.breezeware.cosspringproject.order.dto.OrderViewDto;
import net.breezeware.cosspringproject.order.dto.PlaceOrderDto;
import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.order.service.api.OrderService;
import net.breezeware.cosspringproject.user.entity.UserAddressMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/food-menus")
    List<FoodMenuDto> retrieveAvailableFoodMenusForToday() {
        log.info("Entering retrieveAvailableFoodMenusForToday()");
        List<FoodMenuDto> foodMenus = orderService.retrieveAvailableFoodMenusForToday();
        log.info("Leaving retrieveAvailableFoodMenusForToday()");
        return foodMenus;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Order createOrder(@RequestBody OrderDto orderDto) {
        log.info("Entering createOrder()");
        Order order = orderService.createOrder(orderDto);
        log.info("Leaving createOrder()");
        return order;
    }

    @GetMapping("/{order-id}")
    OrderViewDto viewOrder(@PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering viewOrder()");
        OrderViewDto orderView = orderService.viewOrder(orderId);
        log.info("Leaving viewOrder()");
        return orderView;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{order-id}")
    void updateOrder(@PathVariable(name = "order-id", required = true) long orderId,
            @RequestBody List<FoodItemDto> foodItemDtos) {
        log.info("Entering updateOrder()");
        orderService.updateOrder(orderId, foodItemDtos);
        log.info("Leaving updateOrder()");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/address")
    UserAddressMap createAddress(@RequestBody UserAddressMap userAddressMap) {
        log.info("Entering createAddress()");
        UserAddressMap address = orderService.createAddress(userAddressMap);
        log.info("Leaving createAddress()");
        return address;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/place-order/{order-id}")
    OrderViewDto placeOrder(@PathVariable(name = "order-id", required = true) long orderId,
            @RequestBody PlaceOrderDto placeOrderDto) {
        log.info("Entering placeOrder()");
        OrderViewDto orderViewDto = orderService.placeOrder(orderId, placeOrderDto);
        log.info("Leaving placeOrder()");
        return orderViewDto;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/cancel-order/{order-id}")
    void cancelOrder(@PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering cancelOrder()");
        orderService.cancelOrder(orderId);
        log.info("Leaving cancelOrder()");
    }

    @GetMapping("/active-orders")
    List<OrderViewDto> activeOrders(@RequestParam(value = "user-id") long id) {
        log.info("Entering activeOrders()");
        List<OrderViewDto> orderViewDtos = orderService.viewActiveOrders(id);
        log.info("Leaving activeOrders()");
        return orderViewDtos;
    }

    @GetMapping("/received-order/{order-id}")
    OrderViewDto receivedOrder(@RequestParam(value = "user-id") long userId,
            @PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering receivedOrder()");
        OrderViewDto orderViewDto = orderService.viewReceivedOrder(userId, orderId);
        log.info("Leaving receivedOrder()");
        return orderViewDto;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/order-prepared/{order-id}")
    void changeOrderStatusToOrderPrepared(@RequestParam(value = "user-id") long userId,
            @PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering changeOrderStatusToOrderPrepared()");
        orderService.changeStatusToWaitingForDelivery(userId, orderId);
        log.info("Leaving changeOrderStatusToOrderPrepared()");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/pending-delivery/{order-id}")
    void changeOrderStatusToDeliveryPending(@RequestParam(value = "user-id") long userId,
            @PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering changeOrderStatusToDeliveryPending()");
        orderService.changeStatusToPendingDelivery(userId, orderId);
        log.info("Leaving changeOrderStatusToDeliveryPending()");
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/order-delivered/{order-id}")
    void changeOrderStatusToOrderDelivered(@RequestParam(value = "user-id") long userId,
            @PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering changeStatusToOrderDelivered()");
        orderService.changeStatusToOrderDelivered(userId, orderId);
        log.info("Leaving changeStatusToOrderDelivered()");
    }

    @GetMapping("/cancelled-orders")
    List<OrderViewDto> cancelledOrders(@RequestParam(value = "user-id") long id) {
        log.info("Entering cancelledOrders()");
        List<OrderViewDto> orderViewDtos = orderService.viewCancelledOrders(id);
        log.info("Leaving cancelledOrders()");
        return orderViewDtos;
    }

    @GetMapping("/cancelled-order/{order-id}")
    OrderViewDto cancelledOrder(@RequestParam(value = "user-id") long userId,
            @PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering cancelledOrder()");
        OrderViewDto orderViewDto = orderService.viewCancelledOrder(userId, orderId);
        log.info("Leaving cancelledOrder()");
        return orderViewDto;
    }

    @GetMapping("/completed-orders")
    List<OrderViewDto> completedOrders(@RequestParam(value = "user-id") long id) {
        log.info("Entering completedOrders()");
        List<OrderViewDto> orderViewDtos = orderService.viewCompletedOrders(id);
        log.info("Leaving completedOrders()");
        return orderViewDtos;
    }

    @GetMapping("/completed-order/{order-id}")
    OrderViewDto completedOrder(@RequestParam(value = "user-id") long userId,
            @PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering completedOrder()");
        OrderViewDto orderViewDto = orderService.viewCompletedOrder(userId, orderId);
        log.info("Leaving completedOrder()");
        return orderViewDto;
    }
}
