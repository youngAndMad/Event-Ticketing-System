package danekerscode.service;

import danekerscode.dto.UserDTO;
import danekerscode.exception.EmailRegisteredYetException;
import danekerscode.exception.UserNotFoundException;
import danekerscode.mapper.UserMapper;
import danekerscode.model.User;
import danekerscode.repository.UserRepository;
import danekerscode.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private RabbitTemplate rabbitTemplate;
    @InjectMocks private UserServiceImpl userService;

    @Test
    void findByIdWhenUserDoesNotExistThenThrowUserNotFoundException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void findByIdWhenUserExists() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.findById(userId);

        assertEquals(user, result);
        verify(userRepository, times(1)).findById(userId);
    }


    @Test
    public void testRegistrationSuccess() {
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(new User());
        when(userMapper.toModel(any())).thenReturn(new User());

        var dto = new UserDTO(
                "Mark",
                "Smith",
                "mark@gmail.com",
                "password"
        );
        userService.registration(dto);

        verify(userRepository, times(1)).findUserByEmail(any());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testRegistrationEmailAlreadyRegistered() {
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(new User()));

        var dto = new UserDTO(
                "Mark",
                "Smith",
                "mark@gmail.com",
                "password"
        );

        assertThrows(EmailRegisteredYetException.class, () -> userService.registration(dto));

        verify(userRepository, times(1)).findUserByEmail(any());
        verify(userRepository, never()).save(any());
    }

}