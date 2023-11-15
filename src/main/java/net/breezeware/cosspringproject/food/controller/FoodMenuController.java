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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.service.api.FoodMenuService;

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
@RequestMapping("/api/food-menus")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Food Menu", description = "Food Menu Management APIs")
public class FoodMenuController {
    private final FoodMenuService foodMenuService;

    @Operation(summary = "Get list of Food Menus", description = "Get All Food Menus")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Food Menus",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                            [
                                 {
                                     "id": 5,
                                     "name": "Breakfast",
                                     "type": "Veg",
                                     "createdOn": "2023-11-01T10:37:08.913354Z",
                                     "modifiedOn": "2023-11-08T07:09:02.554687Z"
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
    public List<FoodMenu> retrieveFoodMenus() {
        log.info("Entering retrieveFoodMenus()");
        List<FoodMenu> foodMenus = foodMenuService.findAllFoodMenu();
        log.info("Leaving retrieveFoodMenus()");
        return foodMenus;
    }

    @Operation(summary = "Get Food Menu", description = "Get Food Menu By Id")
    @Parameter(allowEmptyValue = false, name = "food-menu-id", description = "Food Menu Extraction Using Food Menu Id",
            required = true, in = ParameterIn.QUERY)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Food Menu",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                            {
                                         "id": 5,
                                         "name": "Breakfast",
                                         "type": "Veg",
                                         "createdOn": "2023-11-01T10:37:08.913354Z",
                                         "modifiedOn": "2023-11-08T07:09:02.554687Z"
                                     }
                        """)) }),
        @ApiResponse(responseCode = "400", description = "Invalid Food Menu Id",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 400,
                            "message": "INVALID_VALUE",
                            "errorDetails": [
                                "Invalid Food Menu Id"
                            ]
                        }
                        """)) }),
        @ApiResponse(responseCode = "404", description = "Food Menu Not Found",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 404,
                            "message": "NOT_FOUND",
                            "errorDetails": [
                                "Food Menu Not Found"
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
    @GetMapping("/{food-menu-id}")
    public FoodMenu retrieveFoodMenuById(@PathVariable(name = "food-menu-id", required = true) Long foodMenuId) {
        log.info("Entering retrieveFoodMenuById()");
        FoodMenu foodMenu = foodMenuService.findFoodMenuById(foodMenuId);
        log.info("Leaving retrieveFoodMenuById()");
        return foodMenu;
    }

    @Operation(summary = "Save Food Menu", description = "Create a Food Menu")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = FoodMenu.class)) })
    @Parameter(allowEmptyValue = false, name = "food-menu", description = "Food Menu to be Created", required = true,
            in = ParameterIn.QUERY)
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Food Menu Created", content = @Content),
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
    public void createFoodMenu(@RequestBody FoodMenuDto foodMenuDto) {
        log.info("Entering createFoodMenu()");
        foodMenuService.saveFoodMenu(foodMenuDto);
        log.info("Leaving createFoodMenu()");
    }

    @Operation(summary = "Update Food Menu", description = "Updating a Food Menu")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = FoodMenu.class)) })
    @Parameters({
        @Parameter(allowEmptyValue = false, name = "food-menu-id", description = "Food Menu to be Updated",
                required = true, in = ParameterIn.PATH),
        @Parameter(allowEmptyValue = false, name = "food-menu", description = "Updated Food Menu", required = true,
                in = ParameterIn.QUERY) })
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Food Menu Updated", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid Food Menu Id",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 400,
                            "message": "INVALID_VALUE",
                            "errorDetails": [
                                "Invalid Food Menu Id"
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
    @PutMapping("/{food-menu-id}")
    public void updateFoodMenu(@PathVariable(name = "food-menu-id", required = true) Long foodMenuId,
            @RequestBody FoodMenuDto foodMenuDto) {
        log.info("Entering updateFoodMenu()");
        foodMenuService.updateFoodMenu(foodMenuId, foodMenuDto);
        log.info("Leaving updateFoodMenu()");
    }

    @Operation(summary = "Delete Food Menu", description = "Deleting a Food Menu")
    @Parameter(allowEmptyValue = false, name = "food-menu-id", description = "Food Menu to be Deleted", required = true,
            in = ParameterIn.PATH)
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Food Menu Deleted", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid Food Menu Id",
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
    @DeleteMapping("/{food-menu-id}")
    public void deleteFoodMenu(@PathVariable(name = "food-menu-id", required = true) Long foodMenuId) {
        log.info("Entering deleteFoodMenu()");
        foodMenuService.deleteFoodMenuById(foodMenuId);
        log.info("Leaving deleteFoodMenu()");
    }
}
