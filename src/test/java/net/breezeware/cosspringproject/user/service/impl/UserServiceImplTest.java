package net.breezeware.cosspringproject.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.user.dao.RoleRepository;
import net.breezeware.cosspringproject.user.dao.UserRepository;
import net.breezeware.cosspringproject.user.dao.UserRoleMapRepository;
import net.breezeware.cosspringproject.user.entity.Role;
import net.breezeware.cosspringproject.user.entity.User;
import net.breezeware.cosspringproject.user.entity.UserRoleMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;
    @Mock
    UserRoleMapRepository userRoleMapRepository;

    @Mock
    Validator validator;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void testFindAllUsers() {
        List<User> mockUsers = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(mockUsers);
        List<User> users = userService.findAll();
        Assertions.assertThat(users).hasSize(mockUsers.size());
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void testFindUserById() {
        User mockUser = User.builder().id(1).roleId(1).name("Ram").build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockUser));
        User user = userService.findById(1L);
        Assertions.assertThat(user).hasFieldOrProperty("name");
        assert mockUser != null;
        assertEquals(user.getName(), mockUser.getName());
        Mockito.verify(userRepository).findById(anyLong());
    }

    @Test
    void testFindUserByIdInvalidUserIdThrowCustomException() {
        CustomException exception =
                assertThrows(CustomException.class, () -> userService.findById(-1L));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("User Id Must be Greater Than Zero.", exception.getMessage());
    }

    @Test
    void testFindUserByIdInvalidUserThrowCustomException() {
        CustomException exception =
                assertThrows(CustomException.class, () -> userService.findById(2L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("The User not Found", exception.getMessage());
        log.info(exception.getMessage());
    }

    @Test
    void testSaveUser() {
        User mockUser = User.builder().id(1).roleId(1).name("Ram").userName("ram_01").password("breeze123").build();
        Role mockRole = Role.builder().id(1).build();
        UserRoleMap mockUserRoleMap = UserRoleMap.builder().id(1).role(mockRole).user(mockUser).build();
        when(roleRepository.findById(1L)).thenReturn(Optional.ofNullable(mockRole));
        when(userRepository.save(any())).thenReturn(mockUser);
        when(userRoleMapRepository.save(any())).thenReturn(mockUserRoleMap);
        User user = userService.save(User.builder().id(1).roleId(1).name("Ram").userName("ram_01").password("breeze123").build());
        assert mockUser != null;
        assertEquals(user.getName(), mockUser.getName());
    }

    @Test
    void update() {
        User user = User.builder().id(1).roleId(1).build();
        User mockUser = User.builder().id(1).roleId(1).name("Ram").build();
        when(userRepository.save(any())).thenReturn(mockUser);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        userService.update(1L, User.builder().id(1).roleId(1).build());
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    void delete() {
        User user = User.builder().id(1).roleId(1).build();
        doNothing().when(userRepository).delete(user);
        userService.delete(user);
        Mockito.verify(userRepository,Mockito.times(1)).delete(user);
    }

    @Test
    void deleteById() {
        User user = User.builder().id(1).roleId(1).build();
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteById(1L);
        Mockito.verify(userRepository,Mockito.times(1)).deleteById(1L);
    }
}