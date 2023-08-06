package danekerscode.service;

import danekerscode.model.User;

public interface UserService {
    User findById(Long id);

    void logout();
}
