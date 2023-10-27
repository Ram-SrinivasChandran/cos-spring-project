package net.breezeware.cosspringproject.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.user.dao.UserRepository;
import net.breezeware.cosspringproject.user.entity.User;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void testFindAllUsers() {
        List<User> mockUsers = List.of(new User(), new User());
        Mockito.when(userRepository.findAll()).thenReturn(mockUsers);
        List<User> users = userService.findAll();
        Assertions.assertThat(users).hasSize(mockUsers.size());
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void testFindUserById() {
        User mockUser= User.builder().id(1).roleId(1).name("Ram").build();
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockUser));
        User user=userService.findById(1L);
        Assertions.assertThat(user).hasFieldOrProperty("name");
        assertEquals(user.getName(),mockUser.getName());
        Mockito.verify(userRepository).findById(anyLong());
    }
    @Test
    void testFindUserByIdThrowsException(){
        CustomException exception =
                assertThrows(CustomException.class, () -> userService.findById(-1L));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }
}