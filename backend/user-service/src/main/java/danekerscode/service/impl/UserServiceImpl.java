package danekerscode.service.impl;

import danekerscode.dto.UserDTO;
import danekerscode.exception.UserNotFoundException;
import danekerscode.mapper.UserMapper;
import danekerscode.model.User;
import danekerscode.repository.UserRepository;
import danekerscode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
        return userRepository
                .save(userMapper.toModel(dto));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(UserDTO dto , Long userId) {
       return userRepository.save(userMapper.update(dto, findById(userId)));
    }
}
