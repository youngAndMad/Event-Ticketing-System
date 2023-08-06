package danekerscode.service.impl;

import danekerscode.dto.UserRegistrationDTO;
import danekerscode.exception.UserNotFoundException;
import danekerscode.model.User;
import danekerscode.repository.UserRepository;
import danekerscode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void logout() {

    }
}
