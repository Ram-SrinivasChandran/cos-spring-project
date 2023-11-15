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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "Order Management APIs")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Get FoodMenu", description = "Get Today's Food Menus")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Food Menus",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                            [
                                      {
                                          "foodMenu": {
                                              "id": 5,
                                              "name": "Breakfast",
                                              "type": "Veg",
                                              "createdOn": "2023-11-01T10:37:08.913354Z",
                                              "modifiedOn": "2023-11-08T07:09:02.554687Z"
                                          },
                                          "foodItems": [
                                              {
                                                  "id": 2,
                                                  "name": "Idly",
                                                  "cost": 10.0,
                                                  "quantity": 50,
                                                  "createdOn": "2023-11-01T06:01:48.636126Z",
                                                  "modifiedOn": "2023-11-01T06:01:48.636126Z"
                                              },
                                              {
                                                  "id": 3,
                                                  "name": "Poori",
                                                  "cost": 25.0,
                                                  "quantity": 20,
                                                  "createdOn": "2023-11-06T11:32:33.983549Z",
                                                  "modifiedOn": "2023-11-06T11:32:33.983550Z"
                                              }
                                          ],
                                          "availabilityList": [
                                              {
                                                  "id": 1,
                                                  "day": "monday",
                                                  "createdOn": "2023-10-31T10:31:42.842686Z",
                                                  "modifiedOn": "2023-10-31T10:31:42.842686Z"
                                              },
                                              {
                                                  "id": 3,
                                                  "day": "wednesday",
                                                  "createdOn": "2023-10-31T10:32:48.821208Z",
                                                  "modifiedOn": "2023-10-31T10:32:48.821208Z"
                                              }
                                          ]
                                      }
                                  ]
                        """)) }),
        @ApiResponse(responseCode = "405", description = "Only GET Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only GET Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @GetMapping("/food-menus")
    List<FoodMenuDto> retrieveAvailableFoodMenusForToday() {
        log.info("Entering retrieveAvailableFoodMenusForToday()");
        List<FoodMenuDto> foodMenus = orderService.retrieveAvailableFoodMenusForToday();
        log.info("Leaving retrieveAvailableFoodMenusForToday()");
        return foodMenus;
    }

    @Operation(summary = "Save Order", description = "Create a Order")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) })
    @Parameter(allowEmptyValue = false, name = "orderDto", description = "User to be Created", required = true,
            in = ParameterIn.QUERY)
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Order Created", content = @Content),
        @ApiResponse(responseCode = "405", description = "Only POST Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only POST Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Order createOrder(@RequestBody OrderDto orderDto) {
        log.info("Entering createOrder()");
        Order order = orderService.createOrder(orderDto);
        log.info("Leaving createOrder()");
        return order;
    }

    @Operation(summary = "Get Order", description = "Get Order By Id")
    @Parameter(allowEmptyValue = false, name = "order-id", description = "Order Extraction Using Order Id",
            required = true, in = ParameterIn.QUERY)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Order",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                            {
                             "order": {
                                 "id": 17,
                                 "user": {
                                     "id": 6,
                                     "name": "Seenu",
                                     "userName": "seenu_06",
                                     "password": "breeze123",
                                     "createdOn": "2023-11-02T06:44:47.619102Z",
                                     "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                     "roleId": 0
                                 },
                                 "totalCost": 150.0,
                                 "email": "chand2ram@gmail.com",
                                 "phoneNumber": "9677963066",
                                 "userAddress": {
                                     "id": 7,
                                     "user": {
                                         "id": 6,
                                         "name": "Seenu",
                                         "userName": "seenu_06",
                                         "password": "breeze123",
                                         "createdOn": "2023-11-02T06:44:47.619102Z",
                                         "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                         "roleId": 0
                                     },
                                     "doorNumber": "123",
                                     "streetName": "Thirumal Nagar",
                                     "city": "Dindigul",
                                     "district": "Dindigul",
                                     "state": "TamilNadu",
                                     "pincode": 624001,
                                     "landmark": "Ayyapan Kovil",
                                     "createdOn": "2023-11-06T06:29:47.347813Z",
                                     "modifiedOn": "2023-11-06T06:29:47.347813Z"
                                 },
                                 "status": "RECEIVED_ORDER",
                                 "orderOn": "2023-11-06T11:32:33.974881Z",
                                 "deliveryOn": "2023-11-06T12:00:00Z",
                                 "createdOn": "2023-11-03T09:02:05.154467Z",
                                 "modifiedOn": "2023-11-08T10:43:42.533105Z"
                             },
                             "foodItems": [
                                 {
                                     "id": 1,
                                     "name": "Dosa",
                                     "cost": 100.0,
                                     "quantity": 4,
                                     "createdOn": "2023-11-07T12:30:47.037183Z",
                                     "modifiedOn": "2023-11-07T12:30:47.037186Z"
                                 },
                                 {
                                     "id": 2,
                                     "name": "Idly",
                                     "cost": 50.0,
                                     "quantity": 5,
                                     "createdOn": "2023-11-01T06:01:48.636126Z",
                                     "modifiedOn": "2023-11-01T06:01:48.636126Z"
                                 }
                             ]
                         }
                        """)) }),
        @ApiResponse(responseCode = "400", description = "Invalid Order Id",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 400,
                            "message": "INVALID_VALUE",
                            "errorDetails": [
                                "Invalid Order Id"
                            ]
                        }
                        """)) }),
        @ApiResponse(responseCode = "404", description = "User Not Found",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 404,
                            "message": "NOT_FOUND",
                            "errorDetails": [
                                "Order Not Found"
                            ]
                        }
                        """)) }),
        @ApiResponse(responseCode = "405", description = "Only GET Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only GET Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @GetMapping("/{order-id}")
    OrderViewDto viewOrder(@PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering viewOrder()");
        OrderViewDto orderView = orderService.viewOrder(orderId);
        log.info("Leaving viewOrder()");
        return orderView;
    }

    @Operation(summary = "Update Order", description = "Updating a Order")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) })
    @Parameters({
        @Parameter(allowEmptyValue = false, name = "order-id", description = "Order to be Updated", required = true,
                in = ParameterIn.PATH),
        @Parameter(allowEmptyValue = false, name = "order", description = "Updated Order", required = true,
                in = ParameterIn.QUERY) })
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Order Updated", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid Order Id",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 400,
                            "message": "INVALID_VALUE",
                            "errorDetails": [
                                "Invalid Order Id"
                            ]
                        }
                        """)) }),
        @ApiResponse(responseCode = "405", description = "Only PUT Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only PUT Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{order-id}")
    void updateOrder(@PathVariable(name = "order-id", required = true) long orderId,
            @RequestBody List<FoodItemDto> foodItemDtos) {
        log.info("Entering updateOrder()");
        orderService.updateOrder(orderId, foodItemDtos);
        log.info("Leaving updateOrder()");
    }

    @Operation(summary = "Save Address", description = "Create a Address")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = UserAddressMap.class)) })
    @Parameter(allowEmptyValue = false, name = "userAddressMap", description = "Address to be Created", required = true,
            in = ParameterIn.QUERY)
    @ApiResponses(
            value = { @ApiResponse(responseCode = "201", description = "User Address Created", content = @Content),
                @ApiResponse(responseCode = "405", description = "Only POST Method Allowed",
                        content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                                {
                                    "httpStatusCode": 405,
                                    "message": "METHOD_NOT_ALLOWED",
                                    "errorDetails": [
                                        "Only POST Method Allowed Other Method Not Allowed."
                                    ]
                                }
                                """)) }) })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/address")
    UserAddressMap createAddress(@RequestBody UserAddressMap userAddressMap) {
        log.info("Entering createAddress()");
        UserAddressMap address = orderService.createAddress(userAddressMap);
        log.info("Leaving createAddress()");
        return address;
    }

    @Operation(summary = "Place Order", description = "Change the Status of the Order to Placed Order")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) })
    @Parameter(allowEmptyValue = false, name = "order-id", description = "The Order in which the Status to be Changed",
            required = true, in = ParameterIn.PATH)
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Order Placed", content = @Content),
        @ApiResponse(responseCode = "405", description = "Only PUT Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only PUT Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/place-order/{order-id}")
    OrderViewDto placeOrder(@PathVariable(name = "order-id", required = true) long orderId,
            @RequestBody PlaceOrderDto placeOrderDto) {
        log.info("Entering placeOrder()");
        OrderViewDto orderViewDto = orderService.placeOrder(orderId, placeOrderDto);
        log.info("Leaving placeOrder()");
        return orderViewDto;
    }

    @Operation(summary = "Cancel Order", description = "Change the Status of the Order to Order Cancelled")
    @Parameter(allowEmptyValue = false, name = "order-id", description = "The Order in which the Status to be Changed",
            required = true, in = ParameterIn.PATH)
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Order Cancelled", content = @Content),
        @ApiResponse(responseCode = "405", description = "Only PUT Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only PUT Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/cancel-order/{order-id}")
    void cancelOrder(@PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering cancelOrder()");
        orderService.cancelOrder(orderId);
        log.info("Leaving cancelOrder()");
    }

    @Operation(summary = "Get list of Active Orders", description = "Get All Active Order")
    @Parameter(allowEmptyValue = false, name = "user-id", description = "The User to check for the Access",
            required = true, in = ParameterIn.QUERY)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Active Orders",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                            [
                                      {
                                          "order": {
                                              "id": 17,
                                              "user": {
                                                  "id": 6,
                                                  "name": "Seenu",
                                                  "userName": "seenu_06",
                                                  "password": "breeze123",
                                                  "createdOn": "2023-11-02T06:44:47.619102Z",
                                                  "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                                  "roleId": 0
                                              },
                                              "totalCost": 150.0,
                                              "email": "chand2ram@gmail.com",
                                              "phoneNumber": "9677963066",
                                              "userAddress": {
                                                  "id": 7,
                                                  "user": {
                                                      "id": 6,
                                                      "name": "Seenu",
                                                      "userName": "seenu_06",
                                                      "password": "breeze123",
                                                      "createdOn": "2023-11-02T06:44:47.619102Z",
                                                      "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                                      "roleId": 0
                                                  },
                                                  "doorNumber": "123",
                                                  "streetName": "Thirumal Nagar",
                                                  "city": "Dindigul",
                                                  "district": "Dindigul",
                                                  "state": "TamilNadu",
                                                  "pincode": 624001,
                                                  "landmark": "Ayyapan Kovil",
                                                  "createdOn": "2023-11-06T06:29:47.347813Z",
                                                  "modifiedOn": "2023-11-06T06:29:47.347813Z"
                                              },
                                              "status": "ORDER_PLACED",
                                              "orderOn": "2023-11-06T11:32:33.974881Z",
                                              "deliveryOn": "2023-11-06T12:00:00Z",
                                              "createdOn": "2023-11-03T09:02:05.154467Z",
                                              "modifiedOn": "2023-11-08T10:43:42.533105Z"
                                          },
                                          "foodItems": [
                                              {
                                                  "id": 1,
                                                  "name": "Dosa",
                                                  "cost": 100.0,
                                                  "quantity": 4,
                                                  "createdOn": "2023-11-07T12:30:47.037183Z",
                                                  "modifiedOn": "2023-11-07T12:30:47.037186Z"
                                              },
                                              {
                                                  "id": 2,
                                                  "name": "Idly",
                                                  "cost": 50.0,
                                                  "quantity": 5,
                                                  "createdOn": "2023-11-01T06:01:48.636126Z",
                                                  "modifiedOn": "2023-11-01T06:01:48.636126Z"
                                              }
                                          ]
                                      }
                                  ]
                        """)) }),
        @ApiResponse(responseCode = "405", description = "Only GET Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only GET Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @GetMapping("/active-orders")
    List<OrderViewDto> activeOrders(@RequestParam(value = "user-id") long userId) {
        log.info("Entering activeOrders()");
        List<OrderViewDto> orderViewDtos = orderService.viewActiveOrders(userId);
        log.info("Leaving activeOrders()");
        return orderViewDtos;
    }

    @Operation(summary = "Get Received Order", description = "Get the Received Order")
    @Parameters({
        @Parameter(allowEmptyValue = false, name = "user-id", description = "The User to check for the Access",
                required = true, in = ParameterIn.QUERY),
        @Parameter(allowEmptyValue = false, name = "order-id",
                description = "The Order to be checked that it is a Received Order", required = true,
                in = ParameterIn.PATH) })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Received Order",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                            [
                                      {
                                          "order": {
                                              "id": 17,
                                              "user": {
                                                  "id": 6,
                                                  "name": "Seenu",
                                                  "userName": "seenu_06",
                                                  "password": "breeze123",
                                                  "createdOn": "2023-11-02T06:44:47.619102Z",
                                                  "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                                  "roleId": 0
                                              },
                                              "totalCost": 150.0,
                                              "email": "chand2ram@gmail.com",
                                              "phoneNumber": "9677963066",
                                              "userAddress": {
                                                  "id": 7,
                                                  "user": {
                                                      "id": 6,
                                                      "name": "Seenu",
                                                      "userName": "seenu_06",
                                                      "password": "breeze123",
                                                      "createdOn": "2023-11-02T06:44:47.619102Z",
                                                      "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                                      "roleId": 0
                                                  },
                                                  "doorNumber": "123",
                                                  "streetName": "Thirumal Nagar",
                                                  "city": "Dindigul",
                                                  "district": "Dindigul",
                                                  "state": "TamilNadu",
                                                  "pincode": 624001,
                                                  "landmark": "Ayyapan Kovil",
                                                  "createdOn": "2023-11-06T06:29:47.347813Z",
                                                  "modifiedOn": "2023-11-06T06:29:47.347813Z"
                                              },
                                              "status": "RECEIVED_ORDER",
                                              "orderOn": "2023-11-06T11:32:33.974881Z",
                                              "deliveryOn": "2023-11-06T12:00:00Z",
                                              "createdOn": "2023-11-03T09:02:05.154467Z",
                                              "modifiedOn": "2023-11-08T10:43:42.533105Z"
                                          },
                                          "foodItems": [
                                              {
                                                  "id": 1,
                                                  "name": "Dosa",
                                                  "cost": 100.0,
                                                  "quantity": 4,
                                                  "createdOn": "2023-11-07T12:30:47.037183Z",
                                                  "modifiedOn": "2023-11-07T12:30:47.037186Z"
                                              },
                                              {
                                                  "id": 2,
                                                  "name": "Idly",
                                                  "cost": 50.0,
                                                  "quantity": 5,
                                                  "createdOn": "2023-11-01T06:01:48.636126Z",
                                                  "modifiedOn": "2023-11-01T06:01:48.636126Z"
                                              }
                                          ]
                                      }
                                  ]
                        """)) }),
        @ApiResponse(responseCode = "400", description = "Invalid Order Id",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 400,
                            "message": "INVALID_VALUE",
                            "errorDetails": [
                                "Invalid Order Id"
                            ]
                        }
                        """)) }),
        @ApiResponse(responseCode = "405", description = "Only GET Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only GET Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @GetMapping("/received-order/{order-id}")
    OrderViewDto receivedOrder(@RequestParam(value = "user-id") long userId,
            @PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering receivedOrder()");
        OrderViewDto orderViewDto = orderService.viewReceivedOrder(userId, orderId);
        log.info("Leaving receivedOrder()");
        return orderViewDto;
    }

    @Operation(summary = "Order Prepared", description = "Change the Status of the Order to Order Prepared")
    @Parameters({
        @Parameter(allowEmptyValue = false, name = "user-id", description = "The User to check for the Access",
                required = true, in = ParameterIn.QUERY),
        @Parameter(allowEmptyValue = false, name = "order-id",
                description = "The Order in which the Status to be Changed", required = true, in = ParameterIn.PATH) })
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Order Prepared", content = @Content),
        @ApiResponse(responseCode = "405", description = "Only PUT Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only PUT Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/order-prepared/{order-id}")
    void changeOrderStatusToOrderPrepared(@RequestParam(value = "user-id") long userId,
            @PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering changeOrderStatusToOrderPrepared()");
        orderService.changeStatusToWaitingForDelivery(userId, orderId);
        log.info("Leaving changeOrderStatusToOrderPrepared()");
    }

    @Operation(summary = "Delivery Pending", description = "Change the Status of the Order to Delivery Pending")
    @Parameters({
        @Parameter(allowEmptyValue = false, name = "user-id", description = "The User to check for the Access",
                required = true, in = ParameterIn.QUERY),
        @Parameter(allowEmptyValue = false, name = "order-id",
                description = "The Order in which the Status to be Changed", required = true, in = ParameterIn.PATH) })
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Delivery Pending", content = @Content),
        @ApiResponse(responseCode = "405", description = "Only PUT Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only PUT Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/pending-delivery/{order-id}")
    void changeOrderStatusToDeliveryPending(@RequestParam(value = "user-id") long userId,
            @PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering changeOrderStatusToDeliveryPending()");
        orderService.changeStatusToPendingDelivery(userId, orderId);
        log.info("Leaving changeOrderStatusToDeliveryPending()");
    }

    @Operation(summary = "Order Delivered", description = "Change the Status of the Order to Order Delivered")
    @Parameters({
        @Parameter(allowEmptyValue = false, name = "user-id", description = "The User to check for the Access",
                required = true, in = ParameterIn.QUERY),
        @Parameter(allowEmptyValue = false, name = "order-id",
                description = "The Order in which the Status to be Changed", required = true, in = ParameterIn.PATH) })
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Order Delivered", content = @Content),
        @ApiResponse(responseCode = "405", description = "Only PUT Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only PUT Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/order-delivered/{order-id}")
    void changeOrderStatusToOrderDelivered(@RequestParam(value = "user-id") long userId,
            @PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering changeStatusToOrderDelivered()");
        orderService.changeStatusToOrderDelivered(userId, orderId);
        log.info("Leaving changeStatusToOrderDelivered()");
    }

    @Operation(summary = "Get list of Cancelled Orders", description = "Get All Cancelled Order")
    @Parameter(allowEmptyValue = false, name = "user-userId", description = "The User to check for the Access",
            required = true, in = ParameterIn.QUERY)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Cancelled Orders",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                            [
                                      {
                                          "order": {
                                              "userId": 17,
                                              "user": {
                                                  "userId": 6,
                                                  "name": "Seenu",
                                                  "userName": "seenu_06",
                                                  "password": "breeze123",
                                                  "createdOn": "2023-11-02T06:44:47.619102Z",
                                                  "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                                  "roleId": 0
                                              },
                                              "totalCost": 150.0,
                                              "email": "chand2ram@gmail.com",
                                              "phoneNumber": "9677963066",
                                              "userAddress": {
                                                  "userId": 7,
                                                  "user": {
                                                      "userId": 6,
                                                      "name": "Seenu",
                                                      "userName": "seenu_06",
                                                      "password": "breeze123",
                                                      "createdOn": "2023-11-02T06:44:47.619102Z",
                                                      "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                                      "roleId": 0
                                                  },
                                                  "doorNumber": "123",
                                                  "streetName": "Thirumal Nagar",
                                                  "city": "Dindigul",
                                                  "district": "Dindigul",
                                                  "state": "TamilNadu",
                                                  "pincode": 624001,
                                                  "landmark": "Ayyapan Kovil",
                                                  "createdOn": "2023-11-06T06:29:47.347813Z",
                                                  "modifiedOn": "2023-11-06T06:29:47.347813Z"
                                              },
                                              "status": "ORDER_CANCELLED",
                                              "orderOn": "2023-11-06T11:32:33.974881Z",
                                              "deliveryOn": "2023-11-06T12:00:00Z",
                                              "createdOn": "2023-11-03T09:02:05.154467Z",
                                              "modifiedOn": "2023-11-08T10:43:42.533105Z"
                                          },
                                          "foodItems": [
                                              {
                                                  "userId": 1,
                                                  "name": "Dosa",
                                                  "cost": 100.0,
                                                  "quantity": 4,
                                                  "createdOn": "2023-11-07T12:30:47.037183Z",
                                                  "modifiedOn": "2023-11-07T12:30:47.037186Z"
                                              },
                                              {
                                                  "userId": 2,
                                                  "name": "Idly",
                                                  "cost": 50.0,
                                                  "quantity": 5,
                                                  "createdOn": "2023-11-01T06:01:48.636126Z",
                                                  "modifiedOn": "2023-11-01T06:01:48.636126Z"
                                              }
                                          ]
                                      }
                                  ]
                        """)) }),
        @ApiResponse(responseCode = "405", description = "Only GET Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only GET Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @GetMapping("/cancelled-orders")
    List<OrderViewDto> cancelledOrders(@RequestParam(value = "user-userId") long userId) {
        log.info("Entering cancelledOrders()");
        List<OrderViewDto> orderViewDtos = orderService.viewCancelledOrders(userId);
        log.info("Leaving cancelledOrders()");
        return orderViewDtos;
    }

    @Operation(summary = "Get Cancelled Order", description = "Get the Cancelled Order")
    @Parameters({
        @Parameter(allowEmptyValue = false, name = "user-id", description = "The User to check for the Access",
                required = true, in = ParameterIn.QUERY),
        @Parameter(allowEmptyValue = false, name = "order-id",
                description = "The Order to be checked that it is a Cancelled Order", required = true,
                in = ParameterIn.PATH) })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Cancelled Order",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                            [
                                      {
                                          "order": {
                                              "id": 17,
                                              "user": {
                                                  "id": 6,
                                                  "name": "Seenu",
                                                  "userName": "seenu_06",
                                                  "password": "breeze123",
                                                  "createdOn": "2023-11-02T06:44:47.619102Z",
                                                  "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                                  "roleId": 0
                                              },
                                              "totalCost": 150.0,
                                              "email": "chand2ram@gmail.com",
                                              "phoneNumber": "9677963066",
                                              "userAddress": {
                                                  "id": 7,
                                                  "user": {
                                                      "id": 6,
                                                      "name": "Seenu",
                                                      "userName": "seenu_06",
                                                      "password": "breeze123",
                                                      "createdOn": "2023-11-02T06:44:47.619102Z",
                                                      "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                                      "roleId": 0
                                                  },
                                                  "doorNumber": "123",
                                                  "streetName": "Thirumal Nagar",
                                                  "city": "Dindigul",
                                                  "district": "Dindigul",
                                                  "state": "TamilNadu",
                                                  "pincode": 624001,
                                                  "landmark": "Ayyapan Kovil",
                                                  "createdOn": "2023-11-06T06:29:47.347813Z",
                                                  "modifiedOn": "2023-11-06T06:29:47.347813Z"
                                              },
                                              "status": "ORDER_CANCELLED",
                                              "orderOn": "2023-11-06T11:32:33.974881Z",
                                              "deliveryOn": "2023-11-06T12:00:00Z",
                                              "createdOn": "2023-11-03T09:02:05.154467Z",
                                              "modifiedOn": "2023-11-08T10:43:42.533105Z"
                                          },
                                          "foodItems": [
                                              {
                                                  "id": 1,
                                                  "name": "Dosa",
                                                  "cost": 100.0,
                                                  "quantity": 4,
                                                  "createdOn": "2023-11-07T12:30:47.037183Z",
                                                  "modifiedOn": "2023-11-07T12:30:47.037186Z"
                                              },
                                              {
                                                  "id": 2,
                                                  "name": "Idly",
                                                  "cost": 50.0,
                                                  "quantity": 5,
                                                  "createdOn": "2023-11-01T06:01:48.636126Z",
                                                  "modifiedOn": "2023-11-01T06:01:48.636126Z"
                                              }
                                          ]
                                      }
                                  ]
                        """)) }),
        @ApiResponse(responseCode = "400", description = "Invalid Order Id",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 400,
                            "message": "INVALID_VALUE",
                            "errorDetails": [
                                "Invalid Order Id"
                            ]
                        }
                        """)) }),
        @ApiResponse(responseCode = "405", description = "Only GET Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only GET Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @GetMapping("/cancelled-order/{order-id}")
    OrderViewDto cancelledOrder(@RequestParam(value = "user-id") long userId,
            @PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering cancelledOrder()");
        OrderViewDto orderViewDto = orderService.viewCancelledOrder(userId, orderId);
        log.info("Leaving cancelledOrder()");
        return orderViewDto;
    }

    @Operation(summary = "Get list of Completed Orders", description = "Get All Completed Order")
    @Parameter(allowEmptyValue = false, name = "user-userId", description = "The User to check for the Access",
            required = true, in = ParameterIn.QUERY)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Completed Orders",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                            [
                                      {
                                          "order": {
                                              "userId": 17,
                                              "user": {
                                                  "userId": 6,
                                                  "name": "Seenu",
                                                  "userName": "seenu_06",
                                                  "password": "breeze123",
                                                  "createdOn": "2023-11-02T06:44:47.619102Z",
                                                  "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                                  "roleId": 0
                                              },
                                              "totalCost": 150.0,
                                              "email": "chand2ram@gmail.com",
                                              "phoneNumber": "9677963066",
                                              "userAddress": {
                                                  "userId": 7,
                                                  "user": {
                                                      "userId": 6,
                                                      "name": "Seenu",
                                                      "userName": "seenu_06",
                                                      "password": "breeze123",
                                                      "createdOn": "2023-11-02T06:44:47.619102Z",
                                                      "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                                      "roleId": 0
                                                  },
                                                  "doorNumber": "123",
                                                  "streetName": "Thirumal Nagar",
                                                  "city": "Dindigul",
                                                  "district": "Dindigul",
                                                  "state": "TamilNadu",
                                                  "pincode": 624001,
                                                  "landmark": "Ayyapan Kovil",
                                                  "createdOn": "2023-11-06T06:29:47.347813Z",
                                                  "modifiedOn": "2023-11-06T06:29:47.347813Z"
                                              },
                                              "status": "ORDER_DELIVERED",
                                              "orderOn": "2023-11-06T11:32:33.974881Z",
                                              "deliveryOn": "2023-11-06T12:00:00Z",
                                              "createdOn": "2023-11-03T09:02:05.154467Z",
                                              "modifiedOn": "2023-11-08T10:43:42.533105Z"
                                          },
                                          "foodItems": [
                                              {
                                                  "userId": 1,
                                                  "name": "Dosa",
                                                  "cost": 100.0,
                                                  "quantity": 4,
                                                  "createdOn": "2023-11-07T12:30:47.037183Z",
                                                  "modifiedOn": "2023-11-07T12:30:47.037186Z"
                                              },
                                              {
                                                  "userId": 2,
                                                  "name": "Idly",
                                                  "cost": 50.0,
                                                  "quantity": 5,
                                                  "createdOn": "2023-11-01T06:01:48.636126Z",
                                                  "modifiedOn": "2023-11-01T06:01:48.636126Z"
                                              }
                                          ]
                                      }
                                  ]
                        """)) }),
        @ApiResponse(responseCode = "405", description = "Only GET Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only GET Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @GetMapping("/completed-orders")
    List<OrderViewDto> completedOrders(@RequestParam(value = "user-userId") long userId) {
        log.info("Entering completedOrders()");
        List<OrderViewDto> orderViewDtos = orderService.viewCompletedOrders(userId);
        log.info("Leaving completedOrders()");
        return orderViewDtos;
    }

    @Operation(summary = "Get Completed Order", description = "Get the Completed Order")
    @Parameters({
        @Parameter(allowEmptyValue = false, name = "user-id", description = "The User to check for the Access",
                required = true, in = ParameterIn.QUERY),
        @Parameter(allowEmptyValue = false, name = "order-id",
                description = "The Order to be checked that it is a Completed Order", required = true,
                in = ParameterIn.PATH) })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Completed Order",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                            [
                                      {
                                          "order": {
                                              "id": 17,
                                              "user": {
                                                  "id": 6,
                                                  "name": "Seenu",
                                                  "userName": "seenu_06",
                                                  "password": "breeze123",
                                                  "createdOn": "2023-11-02T06:44:47.619102Z",
                                                  "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                                  "roleId": 0
                                              },
                                              "totalCost": 150.0,
                                              "email": "chand2ram@gmail.com",
                                              "phoneNumber": "9677963066",
                                              "userAddress": {
                                                  "id": 7,
                                                  "user": {
                                                      "id": 6,
                                                      "name": "Seenu",
                                                      "userName": "seenu_06",
                                                      "password": "breeze123",
                                                      "createdOn": "2023-11-02T06:44:47.619102Z",
                                                      "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                                      "roleId": 0
                                                  },
                                                  "doorNumber": "123",
                                                  "streetName": "Thirumal Nagar",
                                                  "city": "Dindigul",
                                                  "district": "Dindigul",
                                                  "state": "TamilNadu",
                                                  "pincode": 624001,
                                                  "landmark": "Ayyapan Kovil",
                                                  "createdOn": "2023-11-06T06:29:47.347813Z",
                                                  "modifiedOn": "2023-11-06T06:29:47.347813Z"
                                              },
                                              "status": "ORDER_DELIVERED",
                                              "orderOn": "2023-11-06T11:32:33.974881Z",
                                              "deliveryOn": "2023-11-06T12:00:00Z",
                                              "createdOn": "2023-11-03T09:02:05.154467Z",
                                              "modifiedOn": "2023-11-08T10:43:42.533105Z"
                                          },
                                          "foodItems": [
                                              {
                                                  "id": 1,
                                                  "name": "Dosa",
                                                  "cost": 100.0,
                                                  "quantity": 4,
                                                  "createdOn": "2023-11-07T12:30:47.037183Z",
                                                  "modifiedOn": "2023-11-07T12:30:47.037186Z"
                                              },
                                              {
                                                  "id": 2,
                                                  "name": "Idly",
                                                  "cost": 50.0,
                                                  "quantity": 5,
                                                  "createdOn": "2023-11-01T06:01:48.636126Z",
                                                  "modifiedOn": "2023-11-01T06:01:48.636126Z"
                                              }
                                          ]
                                      }
                                  ]
                        """)) }),
        @ApiResponse(responseCode = "400", description = "Invalid Order Id",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 400,
                            "message": "INVALID_VALUE",
                            "errorDetails": [
                                "Invalid Order Id"
                            ]
                        }
                        """)) }),
        @ApiResponse(responseCode = "405", description = "Only GET Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only GET Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @GetMapping("/completed-order/{order-id}")
    OrderViewDto completedOrder(@RequestParam(value = "user-id") long userId,
            @PathVariable(name = "order-id", required = true) long orderId) {
        log.info("Entering completedOrder()");
        OrderViewDto orderViewDto = orderService.viewCompletedOrder(userId, orderId);
        log.info("Leaving completedOrder()");
        return orderViewDto;
    }
}