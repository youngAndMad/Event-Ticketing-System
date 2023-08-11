package danekerscode.service;

import danekerscode.dto.UserDTO;
import danekerscode.model.User;

public interface UserService {
    User findById(Long id);

    void logout();

    User registration(UserDTO dto);

    void delete(Long id);

    User update(UserDTO dto , Long userId);
}
