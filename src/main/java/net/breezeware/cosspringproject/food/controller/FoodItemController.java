package net.breezeware.cosspringproject.food.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.service.api.FoodItemService;

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
@RequestMapping("/api/food-items")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Food Item", description = "Food Item Management APIs")
public class FoodItemController {

    private final FoodItemService foodItemService;

    @Operation(summary = "Get list of Food Items", description = "Get All Food Items")
    @Parameter(allowEmptyValue = false, name = "user-id", description = "User Id To Check", required = true,
            in = ParameterIn.QUERY)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Food Items",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        [
                            {
                                "id": 2,
                                "name": "Idly",
                                "cost": 10.0,
                                "quantity": 50,
                                "createdOn": "2023-11-01T06:01:48.636126Z",
                                "modifiedOn": "2023-11-01T06:01:48.636126Z"
                            },
                            {
                                "id": 4,
                                "name": "Pongal",
                                "cost": 30.0,
                                "quantity": 10,
                                "createdOn": "2023-11-06T11:32:33.986110Z",
                                "modifiedOn": "2023-11-06T11:32:33.986111Z"
                            },
                            {
                                "id": 3,
                                "name": "Poori",
                                "cost": 25.0,
                                "quantity": 20,
                                "createdOn": "2023-11-06T11:32:33.983549Z",
                                "modifiedOn": "2023-11-06T11:32:33.983550Z"
                            },
                            {
                                "id": 1,
                                "name": "Dosa",
                                "cost": 25.0,
                                "quantity": 20,
                                "createdOn": "2023-11-07T12:30:47.037183Z",
                                "modifiedOn": "2023-11-07T12:30:47.037186Z"
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
    @GetMapping
    public List<FoodItem> retrieveFoodItems(@RequestParam(value = "user-id") long userId) {
        log.info("Entering retrieveFoodItems()");
        List<FoodItem> foodItems = foodItemService.findAllFoodItems(userId);
        log.info("Leaving retrieveFoodItems()");
        return foodItems;
    }

    @Operation(summary = "Get Food Item", description = "Get Food Item By Id")
    @Parameter(allowEmptyValue = false, name = "food-item-id", description = "Food Item Extraction Using Food Item Id",
            required = true, in = ParameterIn.QUERY)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Food Item",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                            {
                                "id": 2,
                                "name": "Idly",
                                "cost": 10.0,
                                "quantity": 50,
                                "createdOn": "2023-11-01T06:01:48.636126Z",
                                "modifiedOn": "2023-11-01T06:01:48.636126Z"
                            }
                        """)) }),
        @ApiResponse(responseCode = "400", description = "Invalid Food Item Id",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 400,
                            "message": "INVALID_VALUE",
                            "errorDetails": [
                                "Invalid Food Item Id"
                            ]
                        }
                        """)) }),
        @ApiResponse(responseCode = "404", description = "Food Item Not Found",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 404,
                            "message": "NOT_FOUND",
                            "errorDetails": [
                                "Food Item Not Found"
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
    @GetMapping("/{food-item-id}")
    public FoodItem retrieveFoodItemById(@PathVariable(name = "food-item-id", required = true) Long foodItemId) {
        log.info("Entering retrieveFoodItemById()");
        FoodItem foodItem = foodItemService.findFoodItemById(foodItemId);
        log.info("Leaving retrieveFoodItemById()");
        return foodItem;
    }

    @Operation(summary = "Save Food Item", description = "Create a Food Item")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = FoodItem.class)) })
    @Parameter(allowEmptyValue = false, name = "food-item", description = "Food Item to be Created", required = true,
            in = ParameterIn.QUERY)
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Food Item Created", content = @Content),
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
    public void saveFoodItem(@RequestBody FoodItem foodItem) {
        log.info("Entering saveFoodItem()");
        foodItemService.saveFoodItem(foodItem);
        log.info("Leaving saveFoodItem()");
    }

    @Operation(summary = "Update Food Item", description = "Updating a Food Item")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = FoodItem.class)) })
    @Parameters({
        @Parameter(allowEmptyValue = false, name = "food-item-id", description = "Food Item to be Updated",
                required = true, in = ParameterIn.PATH),
        @Parameter(allowEmptyValue = false, name = "food-item", description = "Updated Food Item", required = true,
                in = ParameterIn.QUERY) })
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Food Item Updated", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid Food Item Id",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 400,
                            "message": "INVALID_VALUE",
                            "errorDetails": [
                                "Invalid Food Item Id"
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
    @PutMapping("/{food-item-id}")
    public void updateFoodItem(@PathVariable(name = "food-item-id", required = true) Long foodItemId,
            @RequestBody FoodItem foodItem) {
        log.info("Entering updateFoodItem()");
        foodItemService.updateFoodItem(foodItemId, foodItem);
        log.info("Leaving updateFoodItem()");
    }

    @Operation(summary = "Delete Food Item", description = "Deleting a Food Item")
    @Parameter(allowEmptyValue = false, name = "food-item-id", description = "Food Item to be Deleted", required = true,
            in = ParameterIn.PATH)
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Food Item Deleted", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid Food Item Id",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 400,
                            "message": "INVALID_VALUE",
                            "errorDetails": [
                                "Invalid Food Item Id"
                            ]
                        }
                        """)) }),
        @ApiResponse(responseCode = "405", description = "Only DELETE Method Allowed",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 405,
                            "message": "METHOD_NOT_ALLOWED",
                            "errorDetails": [
                                "Only DELETE Method Allowed Other Method Not Allowed."
                            ]
                        }
                        """)) }) })
    @DeleteMapping("/{food-item-id}")
    public void deleteFoodItem(@PathVariable(name = "food-item-id", required = true) Long foodItemId) {
        log.info("Entering deleteFoodItem()");
        foodItemService.deleteFoodItemById(foodItemId);
        log.info("Leaving deleteFoodItem()");
    }
}
