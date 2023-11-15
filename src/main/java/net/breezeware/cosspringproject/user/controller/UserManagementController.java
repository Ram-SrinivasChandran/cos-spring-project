package net.breezeware.cosspringproject.user.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Parameters;
import net.breezeware.cosspringproject.food.entity.FoodItem;
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

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.service.api.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Management", description = "User Management APIs")
public class UserManagementController {

    private final UserService userService;

    @Operation(summary = "Get list of Users", description = "Get All Users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Users",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                            [
                                 {
                                     "id": 5,
                                     "name": "Sathiesh",
                                     "userName": "sathiesh_01",
                                     "password": "breeze125",
                                     "createdOn": "2023-10-27T06:15:18.693414Z",
                                     "modifiedOn": "2023-10-27T06:46:28.393547Z",
                                     "roleId": 0
                                 },
                                 {
                                     "id": 6,
                                     "name": "Seenu",
                                     "userName": "seenu_06",
                                     "password": "breeze123",
                                     "createdOn": "2023-11-02T06:44:47.619102Z",
                                     "modifiedOn": "2023-11-02T06:44:47.619102Z",
                                     "roleId": 0
                                 },
                                 {
                                     "id": 7,
                                     "name": "Akash",
                                     "userName": "akash_28",
                                     "password": "breeze123",
                                     "createdOn": "2023-11-06T12:05:29.117321Z",
                                     "modifiedOn": "2023-11-06T12:05:29.117321Z",
                                     "roleId": 0
                                 },
                                 {
                                     "id": 8,
                                     "name": "Bharath",
                                     "userName": "bharath_01",
                                     "password": "breeze123",
                                     "createdOn": "2023-11-07T06:59:27.521183Z",
                                     "modifiedOn": "2023-11-07T06:59:27.521183Z",
                                     "roleId": 0
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
    public List<User> retrieveUsers() {
        log.info("Entering retrieveUsers()");
        List<User> users = userService.findAll();
        log.info("Leaving retrieveUsers()");
        return users;
    }
    @Operation(summary = "Save User", description = "Create a User")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) })
    @Parameter(allowEmptyValue = false, name = "user", description = "User to be Created", required = true,
            in = ParameterIn.QUERY)
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "User Created",
                            content = @Content),
                    @ApiResponse(responseCode = "405", description = "Only POST Method Allowed",
                            content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                                {
                                    "httpStatusCode": 405,
                                    "message": "METHOD_NOT_ALLOWED",
                                    "errorDetails": [
                                        "Only POST Method Allowed Other Method Not Allowed."
                                    ]
                                }
                                """)) })
            })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createUser(@RequestBody User user) {
        log.info("Entering createUser()");
        userService.save(user);
        log.info("Leaving createUser()");
    }

    @Operation(summary = "Get User", description = "Get User By Id")
    @Parameter(allowEmptyValue = false, name = "user-id", description = "User Extraction Using User Id",
            required = true, in = ParameterIn.QUERY)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the User",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                                   {
                            "id": 5,
                            "name": "Sathiesh",
                            "userName": "sathiesh_01",
                            "password": "breeze125",
                            "createdOn": "2023-10-27T06:15:18.693414Z",
                            "modifiedOn": "2023-10-27T06:46:28.393547Z",
                            "roleId": 0
                        }
                               """)) }),
        @ApiResponse(responseCode = "400", description = "Invalid User Id",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 400,
                            "message": "INVALID_VALUE",
                            "errorDetails": [
                                "Invalid User Id"
                            ]
                        }
                        """)) }),
        @ApiResponse(responseCode = "404", description = "User Not Found",
                content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                        {
                            "httpStatusCode": 404,
                            "message": "NOT_FOUND",
                            "errorDetails": [
                                "User Not Found"
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
    @GetMapping("/{user-id}")
    public User retrieveUser(@PathVariable(name = "user-id", required = true) Long userId) throws CustomException {
        log.info("Entering retrieveUser()");
        User user = userService.findById(userId);
        log.info("Leaving retrieveUser()");
        return user;
    }
    @Operation(summary = "Delete User", description = "Deleting a User")
    @Parameter(allowEmptyValue = false, name = "user-id", description = "User to be Deleted", required = true,
            in = ParameterIn.PATH)
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User Deleted",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Invalid User Id",
                            content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                                {
                                    "httpStatusCode": 400,
                                    "message": "INVALID_VALUE",
                                    "errorDetails": [
                                        "Invalid User Id"
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
                                """)) })
            })
    @DeleteMapping("/{user-id}")
    public void deleteUser(@PathVariable(name = "user-id", required = true) Long userId) {
        log.info("Entering deleteUser()");
        userService.deleteById(userId);
        log.info("Leaving deleteUser()");
    }
    @Operation(summary = "Update User", description = "Updating a User")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) })
    @Parameters({
            @Parameter(allowEmptyValue = false, name = "user-id", description = "User to be Updated", required = true,
                    in = ParameterIn.PATH),
            @Parameter(allowEmptyValue = false, name = "user", description = "Updated User", required = true,
                    in = ParameterIn.QUERY)
    })
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "User Updated",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Invalid User Id",
                            content = { @Content(mediaType = "application/json", schema = @Schema(example = """
                                {
                                    "httpStatusCode": 400,
                                    "message": "INVALID_VALUE",
                                    "errorDetails": [
                                        "Invalid User Id"
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
                                """)) })
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{user-id}")
    public void updateUser(@PathVariable(name = "user-id", required = true) Long userId, @RequestBody User user) {
        log.info("Entering updateUser()");
        userService.update(userId, user);
        log.info("Leaving updateUser()");
    }
}
