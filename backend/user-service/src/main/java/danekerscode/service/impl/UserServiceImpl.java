package danekerscode.service.impl;

import danekerscode.dto.UserDTO;
import danekerscode.exception.EmailRegisteredYetException;
import danekerscode.exception.UserNotFoundException;
import danekerscode.mapper.UserMapper;
import danekerscode.model.User;
import danekerscode.repository.UserRepository;
import danekerscode.service.UserService;
import danekerscode.utils.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static danekerscode.utils.AppConstants.*;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void logout() {
        // FIXME: 8/11/2023 security context holder clear
    }

    @Override
    public User registration(UserDTO dto) {
        var optional = userRepository.findUserByEmail(dto.email());

        optional.ifPresent(_o -> {
            throw new EmailRegisteredYetException();
        });

        var user = userRepository.save(userMapper.toModel(dto));
        rabbitTemplate.convertAndSend(
                EMAIL_EXCHANGE,
                EMAIL_ROUTING_KEY,
                new Notification("welcome to event ticket system", user.getEmail())
        );

        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(UserDTO dto, Long userId) {
        return userRepository.save(userMapper.update(dto, findById(userId)));
    }



}
